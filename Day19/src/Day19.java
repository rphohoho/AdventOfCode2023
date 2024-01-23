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
	Map<String, List<String[]>> workflows;

	public Day19(){
		workflows = new HashMap<>();
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
		//part1(ratings);
		part2();
	}

	private void part1(List<int[]> ratings){
		int res = 0;
		for(int[] rating : ratings)
			if(sentToWorkflow(rating, "in"))
				for(int r : rating)
					res += r;
		System.out.println(res);
	}

	private boolean sentToWorkflow(int[] rating, String label){
		if(label.equals("A"))
			return true;
		if(label.equals("R"))
			return false;

		for(String[] rule : workflows.get(label)){
			if(rule.length == 1)
				return sentToWorkflow(rating, rule[0]);

			char category = rule[0].charAt(0);
			boolean needGreater = rule[0].contains(">");
			int val = Integer.parseInt(rule[0].substring(2));
			switch(category){
				case 'x' -> {if(needGreater && rating[0] > val)
								return sentToWorkflow(rating, rule[1]);
							 if(!needGreater && rating[0] < val)
								return sentToWorkflow(rating, rule[1]);}
				case 'm' -> {if(needGreater && rating[1] > val)
								return sentToWorkflow(rating, rule[1]);
							 if(!needGreater && rating[1] < val)
								return sentToWorkflow(rating, rule[1]);}
				case 'a' -> {if(needGreater && rating[2] > val)
								return sentToWorkflow(rating, rule[1]);
							 if(!needGreater && rating[2] < val)
								return sentToWorkflow(rating, rule[1]);}
				case 's' -> {if(needGreater && rating[3] > val)
								return sentToWorkflow(rating, rule[1]);
							 if(!needGreater && rating[3] < val)
								return sentToWorkflow(rating, rule[1]);}
			};
		}
		return false;
	}

	private void part2(){
		System.out.println(findAcceptedCombination(1, 4000, 1, 4000, 1, 4000, 1, 4000, "in"));
	}

	private long findAcceptedCombination(int xMin, int xMax, int mMin, int mMax, int aMin, int aMax, int sMin, int sMax, String label){
		if(label.equals("A"))
			return (long)(xMax-xMin+1)*(mMax-mMin+1)*(aMax-aMin+1)*(sMax-sMin+1);
		if(label.equals("R"))
			return 0;

		long res = 0;
		for(String[] rule : workflows.get(label)){
			if(rule.length == 1){
				res += findAcceptedCombination(xMin, xMax, mMin, mMax, aMin, aMax, sMin, sMax, rule[0]);
				continue;
			}

			char category = rule[0].charAt(0);
			boolean needGreater = rule[0].contains(">");
			int val = Integer.parseInt(rule[0].substring(2));
			switch(category){
				case 'x' -> {if(needGreater && xMax > val){
								res += findAcceptedCombination(val+1, xMax, mMin, mMax, aMin, aMax, sMin, sMax, rule[1]);
								xMax = val;
							 }
							 else if(!needGreater && xMin < val){
							 	res += findAcceptedCombination(xMin, val-1, mMin, mMax, aMin, aMax, sMin, sMax, rule[1]);
							 	xMin = val;
							 }}
				case 'm' -> {if(needGreater && mMax > val){
								res += findAcceptedCombination(xMin, xMax, val+1, mMax, aMin, aMax, sMin, sMax, rule[1]);
								mMax = val;
							 }
							 else if(!needGreater && mMin < val){
							 	res += findAcceptedCombination(xMin, xMax, mMin, val-1, aMin, aMax, sMin, sMax, rule[1]);
							 	mMin = val;
							 }}
				case 'a' -> {if(needGreater && aMax > val){
								res += findAcceptedCombination(xMin, xMax, mMin, mMax, val+1, aMax, sMin, sMax, rule[1]);
								aMax = val;
							 }
							 else if(!needGreater && aMin < val){
							 	res += findAcceptedCombination(xMin, xMax, mMin, mMax, aMin, val-1, sMin, sMax, rule[1]);
							 	aMin = val;
							 }}
				case 's' -> {if(needGreater && sMax > val){
								res += findAcceptedCombination(xMin, xMax, mMin, mMax, aMin, aMax, val+1, sMax, rule[1]);
								sMax = val;
							 }
							 else if(!needGreater && sMin < val){
							 	res += findAcceptedCombination(xMin, xMax, mMin, mMax, aMin, aMax, sMin, val-1, rule[1]);
							 	sMin = val;
							 }}
			};
		}
		return res;
	}

	public static void main(String[] args){
		new Day19();
	}
}