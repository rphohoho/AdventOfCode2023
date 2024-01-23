//https://adventofcode.com/2023/day/19

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day19{
	public Day19(){
		Map<String, List<String[]>> workflows = new HashMap<>();
		List<int[]> ratings = new ArrayList<>();
		try{
			Scanner sc = new Scanner(new File("../input.txt"));
			while(sc.hasNextLine()){
				String[] input = sc.nextLine().split("[\\{\\},]");
				if(input.length == 1)
					break;
				List<String[]> rules = new ArrayList<>();
				for(int i = 1; i < input.length; i++)
					rules.add(input[i].split(":"));
				workflows.put(input[0], rules);
			}
			while(sc.hasNextLine())
				ratings.add(Arrays.stream(sc.nextLine().split("\\D+"))
								  .filter(s -> !s.isEmpty())
								  .mapToInt(Integer::parseInt)
								  .toArray());
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		part1(workflows, ratings);
	}

	private void part1(Map<String, List<String[]>> workflows, List<int[]> ratings){
		int res = 0;
		for(int[] rating : ratings)
			if(sentToWorkflow(rating, "in", workflows))
				for(int r : rating)
					res += r;
		System.out.println(res);
	}

	private boolean sentToWorkflow(int[] rating, String label, Map<String, List<String[]>> workflows){
		if(label.equals("A"))
			return true;
		if(label.equals("R"))
			return false;

		for(String[] rule : workflows.get(label)){
			if(rule.length == 1)
				return sentToWorkflow(rating, rule[0], workflows);

			char category = rule[0].charAt(0);
			boolean needGreater = rule[0].contains(">");
			int tolerance = Integer.parseInt(rule[0].substring(2));
			switch(category){
				case 'x' -> {if(needGreater && rating[0] > tolerance)
								return sentToWorkflow(rating, rule[1], workflows);
							 if(!needGreater && rating[0] < tolerance)
								return sentToWorkflow(rating, rule[1], workflows);}
				case 'm' -> {if(needGreater && rating[1] > tolerance)
								return sentToWorkflow(rating, rule[1], workflows);
							 if(!needGreater && rating[1] < tolerance)
								return sentToWorkflow(rating, rule[1], workflows);}
				case 'a' -> {if(needGreater && rating[2] > tolerance)
								return sentToWorkflow(rating, rule[1], workflows);
							 if(!needGreater && rating[2] < tolerance)
								return sentToWorkflow(rating, rule[1], workflows);}
				case 's' -> {if(needGreater && rating[3] > tolerance)
								return sentToWorkflow(rating, rule[1], workflows);
							 if(!needGreater && rating[3] < tolerance)
								return sentToWorkflow(rating, rule[1], workflows);}
			};
		}
		return false;
	}

	public static void main(String[] args){
		new Day19();
	}
}