//https://adventofcode.com/2023/day/12

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12{
	private class Pair{
		String row;
		String arrangement;
		int hashCode;

		public Pair(String row, String arrangement){
			this.row = row;
			this.arrangement = arrangement;
			this.hashCode = Objects.hash(row, arrangement);
		}

		public boolean equals(Object o){
			if(this == o)
				return true;
			if(!(o instanceof Pair))
				return false;
			Pair p = (Pair) o;
			return row.equals(p.row) && arrangement.equals(p.arrangement);
		}

		public int hashCode(){
			return this.hashCode;
		}
	}

	public Day12(){
		ArrayList<String> springs = new ArrayList<>();
		ArrayList<String> springArrangement = new ArrayList<>();
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

	private void part1(ArrayList<String> springs, ArrayList<String> springArrangement){
		int sum = 0;
		for(int i = 0; i < springs.size(); i++){
			sum += count(springs.get(i), new ArrayList<Integer>(Arrays.stream(springArrangement.get(i)
																	   .split(","))
																	   .mapToInt(Integer::parseInt)
																	   .boxed()
																	   .collect(Collectors.toList())));
		}
		System.out.println(sum);
	}

	private void part2(ArrayList<String> springs, ArrayList<String> springArrangement){
		long sum = 0;
		HashMap<Pair, Long> cache = new HashMap<>();
		for(int i = 0; i < springs.size(); i++){
			String row = unfold("?", springs.get(i));
			String arrangement =  unfold(",", springArrangement.get(i));
			sum += count(row, new ArrayList<Integer>(Arrays.stream(arrangement
															.split(","))
															.mapToInt(Integer::parseInt)
															.boxed()
															.collect(Collectors.toList())), cache);
		}
		System.out.println(sum);
	}

	private int count(String row, ArrayList<Integer> springArrangement){
		if(row.isEmpty())
			return springArrangement.isEmpty() ? 1 : 0;
		if(springArrangement.isEmpty())
			return row.contains("#") ? 0 : 1;

		int sum = 0;
		if(row.charAt(0) != '#')
			sum += count(row.substring(1), springArrangement);
		int damagedSprings = springArrangement.remove(0);
		if(row.charAt(0) != '.' && damagedSprings <= row.length() && !row.substring(0, damagedSprings).contains(".")){
			if(damagedSprings == row.length())
				sum += count(row.substring(damagedSprings), springArrangement);
			else if(row.charAt(damagedSprings) != '#')
				sum += count(row.substring(damagedSprings+1), springArrangement);
		}
		springArrangement.add(0, damagedSprings);

		return sum;
	}

	private long count(String row, ArrayList<Integer> springArrangement, HashMap<Pair, Long> cache){
		if(row.isEmpty())
			return springArrangement.isEmpty() ? 1 : 0;
		if(springArrangement.isEmpty())
			return row.contains("#") ? 0 : 1;

		Pair key = new Pair(row, springArrangement.toString());
		if(cache.containsKey(key))
			return cache.get(key);

		long sum = 0;
		if(row.charAt(0) != '#')
			sum += count(row.substring(1), springArrangement, cache);
		int damagedSprings = springArrangement.remove(0);
		if(row.charAt(0) != '.' && damagedSprings <= row.length() && !row.substring(0, damagedSprings).contains(".")){
			if(damagedSprings == row.length())
				sum += count(row.substring(damagedSprings), springArrangement, cache);
			else if(row.charAt(damagedSprings) != '#')
				sum += count(row.substring(damagedSprings+1), springArrangement, cache);
		}
		springArrangement.add(0, damagedSprings);

		cache.put(key, sum);
		return sum;
	}

	private String unfold(String delimiter, String s){
		return String.join(delimiter, s, s, s, s, s);
	}

	public static void main(String[] args){
		new Day12();
	}
}