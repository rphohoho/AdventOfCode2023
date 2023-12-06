//https://adventofcode.com/2023/day/6

import java.util.*;
import java.io.*;

public class Day6{
	public Day6(){
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			//part1(sc);
			part2(sc);
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}

	private void part1(Scanner sc){
		int[] time = new int[0], distance = new int[0];
		time = Arrays.stream(sc.nextLine().split("\\D+"))
					 .filter(s -> !s.isEmpty())
					 .mapToInt(Integer::parseInt)
					 .toArray();
		distance = Arrays.stream(sc.nextLine().split("\\D+"))
						 .filter(s -> !s.isEmpty())
						 .mapToInt(Integer::parseInt)
						 .toArray();
		int value = 1;
		for(int i = time.length-1; i >= 0; i--){
			int win = 0;
			int remainingTime = time[i]/2+1;
			int holdTime = remainingTime-1;
			int recordDistance = distance[i];
			if(time[i]%2 == 0){
				if((remainingTime-1)*(remainingTime-1) <= recordDistance)
					continue;
				win++;
				holdTime--;
			}
			for(; holdTime >= 0; holdTime--, remainingTime++){
				if(holdTime*remainingTime <= recordDistance)
					break;
				win += 2;
			}
			value *= win;
		}
		System.out.println(value);
	}

	private void part2(Scanner sc){
		String buffer = "";
		for(String s : sc.nextLine().split("\\D+"))
			buffer = buffer.concat(s);
		long time = Long.parseLong(buffer);
		buffer = "";
		for(String s : sc.nextLine().split("\\D+"))
			buffer = buffer.concat(s);
		long distance = Long.parseLong(buffer);
		int win = 0;
		long remainingTime = time/2+1;
		long holdTime = remainingTime-1;
		if(time%2 == 0){
			if((remainingTime-1)*(remainingTime-1) > distance)
				win++;
			holdTime--;
		}
		for(; holdTime >= 0; holdTime--, remainingTime++){
			if(holdTime*remainingTime <= distance)
				break;
			win += 2;
		}
		System.out.println(win);
	}

	public static void main(String[] args){
		new Day6();
	}
}