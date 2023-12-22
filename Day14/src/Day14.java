//https://adventofcode.com/2023/day/14

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day14{
	public Day14(){
		List<char[]> inputs = new ArrayList<>();
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine())
				inputs.add(sc.nextLine().toCharArray());
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		//part1(inputs.toArray(char[][]::new));
		part2(inputs.toArray(char[][]::new));
	}

	private void part1(char[][] platform){
		int sum = 0;
		int len = platform.length;
		for(int c = 0; c < platform[0].length; c++){
			int stoneLoad = len;
			for(int r = 0; r < len; r++)
				if(platform[r][c] == '#')
					stoneLoad = len-r-1;
				else if(platform[r][c] == 'O')
					sum += stoneLoad--;
		}
		System.out.println(sum);
	}

	private void part2(char[][] platform){
		int row = platform.length;
		int col = platform[0].length;
		//the rocks move in cycle for every 175 spins,
		//thus only need 1_000_000_000%175 = 125 spins
		for(int i = 1; i <= 125; i++){
			spin(platform, col, row);
		}
		loadSum(platform, row, col);
	}

	private void spin(char[][] platform, int row, int col){
		for(int c = 0; c < col; c++){
			int stopsRock = 0;
			for(int r = 0; r < row; r++)
				if(platform[r][c] == '#')
					stopsRock = r+1;
				else if(platform[r][c] == 'O'){
					platform[r][c] = '.';
					platform[stopsRock++][c] = 'O';
				}
		}
		for(int r = 0; r < row; r++){
			int stopsRock = 0;
			for(int c = 0; c < col; c++)
				if(platform[r][c] == '#')
					stopsRock = c+1;
				else if(platform[r][c] == 'O'){
					platform[r][c] = '.';
					platform[r][stopsRock++] = 'O';
				}
		}
		for(int c = 0; c < col; c++){
			int stopsRock = row-1;
			for(int r = row-1; r >= 0; r--)
				if(platform[r][c] == '#')
					stopsRock = r-1;
				else if(platform[r][c] == 'O'){
					platform[r][c] = '.';
					platform[stopsRock--][c] = 'O';
				}
		}
		for(int r = 0; r < row; r++){
			int stopsRock = col-1;
			for(int c = col-1; c >= 0; c--)
				if(platform[r][c] == '#')
					stopsRock = c-1;
				else if(platform[r][c] == 'O'){
					platform[r][c] = '.';
					platform[r][stopsRock--] = 'O';
				}
		}
	}

	private void loadSum(char[][] platform, int row, int col){
		int sum = 0;
		for(int r = 0; r < row; r++){
			int stoneLoad = row-r;
			for(int c = 0; c < col; c++)
				if(platform[r][c] == 'O')
					sum += stoneLoad;
		}
		System.out.println(sum);
	}

	public static void main(String[] args){
		new Day14();
	}
}