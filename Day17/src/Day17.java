//https://adventofcode.com/2023/day/17

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Scanner;

public class Day17{
	private class State{
		final int r, c, dr, dc, steps;
		final int heatLoss;
		private final int hashCode;

		public State(int r, int c, int dr, int dc, int steps, int heatLoss){
			this.r = r;
			this.c = c;
			this.dr = dr;
			this.dc = dc;
			this.steps = steps;
			this.heatLoss = heatLoss;
			this.hashCode = Objects.hash(r, c, dr, dc, steps);
		}

		public boolean equals(Object o){
			if(this == o)
				return true;
			if(!(o instanceof State))
				return false;
			State s = (State) o;
			return s.r==r && s.c==c && s.dr==dr && s.dc==dc && s.steps==steps;
		}

		public int hashCode(){
			return this.hashCode;
		}
	}

	public Day17(){
		List<int[]> input = new ArrayList<>();
		try{
			Scanner sc = new Scanner(new File("../input.txt"));
			while(sc.hasNextLine())
				input.add(Arrays.stream(sc.nextLine().split("")).mapToInt(Integer::parseInt).toArray());
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		int[][] map = input.toArray(int[][]::new);
		part1(map);
	}

	private void part1(int[][] map){
		Set<State> visited = new HashSet<>();
		PriorityQueue<State> queue = new PriorityQueue<>((a,b) -> a.heatLoss-b.heatLoss);
		queue.add(new State(0, 1, 0, 1, 1, map[0][1]));
		queue.add(new State(1, 0, 1, 0, 1, map[1][0]));

		int row = map.length, col = map[0].length;

		while(!queue.isEmpty()){
			State curr = queue.poll();

			if(curr.r == row-1 && curr.c == col-1){
				System.out.println(curr.heatLoss);
				break;
			}

			if(visited.contains(curr))
				continue;
			visited.add(curr);

			if(curr.steps != 3){
				int newR = curr.r+curr.dr;
				int newC = curr.c+curr.dc;
				if(newR >= 0 && newR < row && newC >= 0 && newC < col)
					queue.add(new State(newR, newC, curr.dr, curr.dc, curr.steps+1, curr.heatLoss+map[newR][newC]));
			}

			if(curr.dr != 0){
				int newC = curr.c-1;
				if(newC >= 0 && newC < col)
					queue.add(new State(curr.r, newC, 0, -1, 1, curr.heatLoss+map[curr.r][newC]));
				newC = curr.c+1;
				if(newC >= 0 && newC < col)
					queue.add(new State(curr.r, newC, 0, 1, 1, curr.heatLoss+map[curr.r][newC]));
			}

			if(curr.dc != 0){
				int newR = curr.r-1;
				if(newR >= 0 && newR < row)
					queue.add(new State(newR, curr.c, -1, 0, 1, curr.heatLoss+map[newR][curr.c]));
				newR = curr.r+1;
				if(newR >= 0 && newR < row)
					queue.add(new State(newR, curr.c, 1, 0, 1, curr.heatLoss+map[newR][curr.c]));
			}
		}
	}

	public static void main(String[] args){
		new Day17();
	}
}