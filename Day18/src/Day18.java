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
			Scanner sc = new Scanner(new File("../test.txt"));
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
		LinkedList<int[]> corners = new LinkedList<>();
		int r = 0, c = 0;
		while(!plan.isEmpty()){
			corners.add(new int[]{r,c});
			String direction = plan.peek()[0];
			int meter = Integer.parseInt(plan.poll()[1]);
			switch(direction){
				case "U" -> c -= meter;
				case "D" -> c += meter;
				case "L" -> r -= meter;
				case "R" -> r += meter;
			};
		}

		int res = 0;
		int[] lastCorner = corners.poll();
		corners.add(lastCorner);
		int width = 0;
		while(!corners.isEmpty()){
			int[] currCorner = corners.poll();
			width += (currCorner[0]-lastCorner[0])+(currCorner[1]+lastCorner[1]);
			lastCorner = currCorner;
			currCorner = corners.poll();
			res += width*((currCorner[0]-lastCorner[0])+(currCorner[1]+lastCorner[1]));
			lastCorner = currCorner;
		}
		System.out.println(Math.abs(res));
	}

	public static void main(String[] args){
		new Day18();
	}
}