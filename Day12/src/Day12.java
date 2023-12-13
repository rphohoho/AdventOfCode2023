//https://adventofcode.com/2023/day/12

import java.util.*;
import java.io.*;

public class Day12{
	public Day12(){
		List<String> springs = new ArrayList<>();
		List<String> springArrangement = new ArrayList<>();
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String[] input = sc.nextLine().split(" ");
				springs.add(input[0]);
				springArrangement.add(input[1]);
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		//part1(springs, springArrangement);
		part2(springs, springArrangement);
	}

	private void part1(List<String> springs, List<String> springArrangement){
		long sum = 0;
		for(int i = 0; i < springs.size(); i++)
			sum += count(springs.get(i), Arrays.stream(springArrangement.get(i).split(",")).mapToInt(Integer::parseInt).toArray(), 0);
		System.out.println(sum);
	}

	private void part2(List<String> springs, List<String> springArrangement){
		long sum = 0;
		for(int i = 0; i < springs.size(); i++){
			String row = unfold("?", springs.get(i));
			String arrangement =  unfold(",", springArrangement.get(i));
			sum += count(row, Arrays.stream(arrangement.split(",")).mapToInt(Integer::parseInt).toArray(), 0);
		}
		System.out.println(sum);
	}

	private long count(String row, int[] springArrangement, int index){
		if(row.isEmpty())
			return index == springArrangement.length ? 1 : 0;
		if(index == springArrangement.length)
			return row.contains("#") ? 0 : 1;
		long sum = 0;
		if(row.charAt(0) != '#')
			sum += count(row.substring(1), springArrangement, index);
		if(row.charAt(0) != '.' && springArrangement[index] <= row.length() && !row.substring(0, springArrangement[index]).contains(".")){
			if(springArrangement[index] == row.length())
				sum += count(row.substring(springArrangement[index]), springArrangement, index+1);
			else if(row.charAt(springArrangement[index]) != '#')
				sum += count(row.substring(springArrangement[index]+1), springArrangement, index+1);
		}
		return sum;
	}

	private String unfold(String delimiter, String s){
		return String.join(delimiter, s, s, s, s, s);
	}

	public static void main(String[] args){
		new Day12();
	}
}