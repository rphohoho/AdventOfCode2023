//https://adventofcode.com/2023/day/18

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.LinkedList;
import java.util.Scanner;

public class Day18{
	public Day18(){
		LinkedList<String[]> plan =  new LinkedList<>();
		try{
			Scanner sc = new Scanner(new File("../input.txt"));
			while(sc.hasNextLine())
				plan.add(sc.nextLine().split(" "));
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		part1(plan);
	}

	private void part1(LinkedList<String[]> plan){
		int res = 0;
		int distFromOrigin = 0;
		int exterior = 0;
		String lastDirection = "U";

		while(!plan.isEmpty()){
			String direction = plan.peek()[0];
			int width = Integer.parseInt(plan.poll()[1]);
			switch(direction){
				case "L" -> {exterior += width; 
							 if(lastDirection.equals("D")) exterior++; 
							 distFromOrigin -= width;}
				case "R" -> distFromOrigin += width;
			}
			lastDirection = direction;
			
			direction = plan.peek()[0];
			int height = Integer.parseInt(plan.poll()[1]);
			switch(direction){
				case "D" -> {exterior += height; 
							 if(lastDirection.equals("L")) exterior--;
							 res += distFromOrigin*height;} 
				case "U" ->	res += distFromOrigin*-height;
			};
			lastDirection = direction;
		}

		System.out.println(Math.abs(res)+exterior);
	}

	public static void main(String[] args){
		new Day18();
	}
}