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
		part1(inputs.toArray(char[][]::new));
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

	public static void main(String[] args){
		new Day14();
	}
}