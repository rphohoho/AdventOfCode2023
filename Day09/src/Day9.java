//https://adventofcode.com/2023/day/9

import java.util.*;
import java.io.*;

public class Day9{
	public Day9(){
		List<int[]> inputs = new ArrayList<>();
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				inputs.add(Arrays.stream(sc.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray());
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		//part1(inputs);
		part2(inputs);
	}

	private void part1(List<int[]> inputs){
		int sum = 0;
		for(int[] input : inputs)
			sum += findNextNum(input);
		System.out.println(sum);
	}

	private void part2(List<int[]> inputs){
		int sum = 0;
		for(int[] input : inputs)
			sum += findPreviousNum(input);
		System.out.println(sum);
	}

	private int findNextNum(int[] arr){
		int[] difference = new int[arr.length-1];
		for(int i = 0; i < arr.length-1; i++)
			difference[i] = arr[i+1]-arr[i];
		if(Arrays.stream(difference).filter(i -> i != 0).count() == 0)
			return arr[arr.length-1];
		return arr[arr.length-1]+findNextNum(difference);
	}

	private int findPreviousNum(int[] arr){
		int[] difference = new int[arr.length-1];
		for(int i = 0; i < arr.length-1; i++)
			difference[i] = arr[i+1]-arr[i];
		if(Arrays.stream(difference).filter(i -> i != 0).count() == 0)
			return arr[0];
		return arr[0]-findPreviousNum(difference);
	}

	public static void main(String[] args){
		new Day9();
	}
}