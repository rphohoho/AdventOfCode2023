//https://adventofcode.com/2023/day/16

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day16{
	private char[][] map;
	private int[][] directionMap; //record the direction the beam enters from
								  //direction as follows: N=1, E=2, S=4, W=8

	public Day16(){
		List<char[]> input = new ArrayList<>();
		try{
			Scanner sc = new Scanner(new File("../input.txt"));
			while(sc.hasNextLine())
				input.add(sc.nextLine().toCharArray());
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		map = input.toArray(char[][]::new);
		directionMap = new int[map.length][map[0].length];
		part1();
	}

	private void part1(){
		LinkedList<int[]> queue = new LinkedList<>();
		queue.add(new int[]{0, 0, 2});
		while(!queue.isEmpty()){
			int r = queue.peek()[0], c = queue.peek()[1], dir = queue.poll()[2];

			if(r < 0 || r >= map.length || c < 0 || c >= map[0].length)
				continue;

			if((directionMap[r][c] & dir) > 0)
				continue;

			directionMap[r][c] |= dir;

			switch(map[r][c]){
			case '\\' -> {switch(dir){
						  case 1 -> queue.add(new int[]{r, c-1, 8});
						  case 2 -> queue.add(new int[]{r+1, c, 4});
						  case 4 -> queue.add(new int[]{r, c+1, 2});
			 			  case 8 -> queue.add(new int[]{r-1, c, 1});
						 };}
			case '/'  -> {switch(dir){
						  case 1 -> queue.add(new int[]{r, c+1, 2});
						  case 2 -> queue.add(new int[]{r-1, c, 1});
						  case 4 -> queue.add(new int[]{r, c-1, 8});
						  case 8 -> queue.add(new int[]{r+1, c, 4});
						 };}
			case '|'  -> {if(dir != 4)
							queue.add(new int[]{r-1, c, 1});
						  if(dir != 1)
							queue.add(new int[]{r+1, c, 4});
						 }
			case '-'  -> {if(dir != 8)
							queue.add(new int[]{r, c+1, 2});
						  if(dir != 2)
							queue.add(new int[]{r, c-1, 8});
						 }
			case '.'  -> {switch(dir){
						  case 1 -> queue.add(new int[]{r-1, c, 1});
						  case 2 -> queue.add(new int[]{r, c+1, 2});
						  case 4 -> queue.add(new int[]{r+1, c, 4});
						  case 8 -> queue.add(new int[]{r, c-1, 8});
						 };}
			};
		}

		int res = 0;
		for(int[] r : directionMap)
			for(int c : r)
				if(c > 0)
					res++;
		System.out.println(res);
	}

	public static void main(String[] args){
		new Day16();
	}
}