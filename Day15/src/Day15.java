//https://adventofcode.com/2023/day/15

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day15{
	public Day15(){
		String[] inputs = new String[0];
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			inputs = sc.nextLine().split(",");
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		//part1(inputs);
		part2(inputs);
	}

	private void part1(String[] inputs){
		int res = 0;
		for(String input : inputs)
			res += hash(input);
		System.out.println(res);
	}

	private void part2(String[] inputs){
		Map<String, Integer> labelsToLenses = new HashMap<>();
		Map<Integer, List<String>> boxes = new HashMap<>();
		for(String input : inputs){
			String label = input.split("[=-]")[0];
			int boxNum = hash(label);
			if(input.contains("-")){
				if(labelsToLenses.containsKey(label)){
					labelsToLenses.remove(label);
					boxes.get(boxNum).remove(label);
				}
			}
			else{
				int lense = input.charAt(input.length()-1)-'0';
				labelsToLenses.put(label, lense);
				if(!labelsToLenses.containsKey(label)){
					if(!boxes.containsKey(boxNum))
						boxes.put(boxNum, new ArrayList<>());
					boxes.get(boxNum).add(label);
				}
			}
		}

		int res = 0;
		for(Map.Entry<Integer, List<String>> entry : boxes.entrySet()){
			int boxNum = entry.getKey()+1;
			int slot = 1;
			for(String label : entry.getValue())
				res += boxNum*slot++*labelsToLenses.get(label);
		}
		System.out.println(res);
	}

	private int hash(String s){
		int hashcode = 0;
		for(char c : s.toCharArray()){
			hashcode += c;
			hashcode *= 17;
			hashcode %= 256;
		}
		return hashcode;
	}

	public static void main(String[] args){
		new Day15();
	}
}