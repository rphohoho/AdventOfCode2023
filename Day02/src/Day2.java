//https://adventofcode.com/2023/day/2

import java.util.*;
import java.io.*;

public class Day2{
	public Day2(){
		List<String[]> inputs = new ArrayList<>();
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				inputs.add(sc.nextLine().split("\\W+"));
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		part1(inputs);
	}

	private void part1(List<String[]> inputs){
		int sum = 0;
		for(String[] input : inputs){
			//sum += checkValid(input, 12, 13, 14); //part1
			sum += gamePower(input); //part2
		}
		System.out.println(sum);
	}

	private int checkValid(String[] input, int redCubes, int greenCubes, int blueCubes){
		for(int i = 3; i < input.length; i += 2){
			int cubesRevealed = Integer.parseInt(input[i-1]);
			if(cubesRevealed > redCubes && input[i].equals("red"))
				return 0;
			if(cubesRevealed > greenCubes && input[i].equals("green"))
				return 0;
			if(cubesRevealed > blueCubes && input[i].equals("blue"))
				return 0;
		}
		return Integer.parseInt(input[1]);
	}

	private int gamePower(String[] input){
		int minRed = 0, minGreen = 0, minBlue = 0;
		for(int i = 3; i < input.length; i += 2){
			int cubesRevealed = Integer.parseInt(input[i-1]);
			switch(input[i]){
			case "red":
				minRed = Math.max(minRed, cubesRevealed);
				break;
			case "green":
				minGreen = Math.max(minGreen, cubesRevealed);
				break;
			case "blue":
				minBlue = Math.max(minBlue, cubesRevealed);
			}
		}
		return minRed*minGreen*minBlue;
	}

	public static void main(String[] args){
		new Day2();
	}
}