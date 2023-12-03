//https://adventofcode.com/2023/day/3

import java.util.*;
import java.io.*;

public class Day3{
	public Day3(){
		List<char[]> list = new ArrayList<>();
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine())
				list.add(sc.nextLine().toCharArray());
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		char[][] input = list.toArray(new char[0][0]);
		//part1(input);
		part2(input);
	}

	private void part1(char[][] input){
		int sum = 0;
		int row = input.length, col = input[0].length;
		for(int r = 0; r < row; r++){
			for(int c = 0; c < col; c++){
				char curr = input[r][c];
				if(curr < '0' || curr > '9')
					continue;
				int num = curr-'0';
				int end = c;
				while(end+1 < col && input[r][end+1] >= '0' && input[r][end+1] <= '9'){
					curr = input[r][++end];
					num = num*10+(curr-'0');
				}
				if(checkIsPart(r, c, end, input))
					sum += num;
				c = end+1;
			}
		}
		System.out.println(sum);
	}

	private boolean checkIsPart(int row, int start, int end, char[][] input){
		for(int r = Math.max(0, row-1); r < Math.min(input.length, row+2); r++){
			for(int c = Math.max(0, start-1); c < Math.min(input[0].length, end+2); c++){
				char curr = input[r][c];
				if(curr != '.' && (curr < '0' || curr > '9'))
					return true;
			}
		}
		return false;
	}

	private void part2(char[][] input){
		int sum = 0;
		int row = input.length, col = input[0].length;
		Map<Coordinate, Gear> gears = new HashMap<>();
		for(int r = 0; r < row; r++){
			for(int c = 0; c < col; c++){
				char curr = input[r][c];
				if(curr < '0' || curr > '9')
					continue;
				int num = curr-'0';
				int end = c;
				while(end+1 < col && input[r][end+1] >= '0' && input[r][end+1] <= '9'){
					curr = input[r][++end];
					num = num*10+(curr-'0');
				}
				checkNumber(r, c, end, num, input, gears);
				c = end+1;
			}
		}
		for(Gear g : gears.values())
			if(g.num == 2)
				sum += g.ratio;
		System.out.println(sum);
	}

	private void checkNumber(int row, int start, int end, int val, char[][] input, Map<Coordinate, Gear> gears){
		for(int r = Math.max(0, row-1); r < Math.min(input.length, row+2); r++){
			for(int c = Math.max(0, start-1); c < Math.min(input[0].length, end+2); c++){
				char curr = input[r][c];
				if(curr == '*'){
					Gear temp = gears.getOrDefault(new Coordinate(r,c), new Gear());
					temp.add(val);
					gears.put(new Coordinate(r,c), temp);
				}
			}
		}
	}

	private class Gear{
		int ratio;
		int num;

		public Gear(){
			ratio = 1;
			num = 0;
		}

		public void add(int val){
			ratio *= val;
			num++;
		}
	}

	private class Coordinate{
		final int x, y;
		int hashCode;

		public Coordinate(int x, int y){
			this.x = x;
			this.y = y;
			this.hashCode = Objects.hash(x, y);
		}

		public boolean equals(Object o){
			if(this == o)
				return true;
			if(!(o instanceof Coordinate))
				return false;
			Coordinate c = (Coordinate) o;
			return x == c.x && y == c.y;
		}

		public int hashCode(){
			return this.hashCode;
		}
	}

	public static void main(String[] args){
		new Day3();
	}
}