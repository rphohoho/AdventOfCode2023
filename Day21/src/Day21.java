//https://adventofcode.com/2023/day/21

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day21{
	public Day21(){
		List<char[]> input = new ArrayList<>();
		int r = 0, c = 0;
		try{
			Scanner sc = new Scanner(new File("../input.txt"));
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				if(line.indexOf("S") != -1){
					r = input.size();
					c = line.indexOf("S");
					line.replace('S', '.');
				}
				input.add(line.toCharArray());
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		char[][] map = input.toArray(char[][]::new);
		part1(map, r, c);
	}

	private void part1(char[][] map, int startR, int startC){
		LinkedList<int[]> queue = new LinkedList<>();
		queue.add(new int[]{startR, startC});
		int row = map.length, col = map[0].length;
		boolean[][] visited = new boolean[row][col];
		visited[startR][startC] = true;
		int[][] directions = new int[][]{{0,-1},{-1,0},{0,1},{1,0}};
		int count = 0;

		for(int steps = 64; steps >= 0; steps--){
			int size = queue.size();

			if(steps%2 == 0)
				count += size;

			if(steps == 0)
				break;

			for(int i = 0; i < size; i++){
				int r = queue.peek()[0], c = queue.poll()[1];
				for(int[] dir : directions){
					int newR = r+dir[0], newC = c+dir[1];
					if(newR >= 0 && newR < row && newC >= 0 && newC < col && map[newR][newC] == '.' && !visited[newR][newC]){
						visited[newR][newC] = true;
						queue.add(new int[]{newR, newC});
					}
				}
			}
		}
		System.out.println(count);
	}

	public static void main(String[] args){
		new Day21();
	}
}