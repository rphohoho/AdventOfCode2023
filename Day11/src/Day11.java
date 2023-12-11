//https://adventofcode.com/2023/day/11

import java.util.*;
import java.io.*;

public class Day11{
	public Day11(){
		List<String> inputs = new ArrayList<>();
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine())
				inputs.add(sc.nextLine());
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		String[] universe = inputs.toArray(new String[0]);
		//part(universe, 2);
		part(universe, 1_000_000);
	}

	private void part(String[] universe, int scale){
		List<int[]> galaxies = new ArrayList<>();
		List<Integer> rowExpanded = new ArrayList<>();
		List<Integer> colExpanded = fillList(universe[0].length());
		for(int r = 0; r < universe.length; r++){
			String currRow = universe[r];
			int c = currRow.indexOf("#");
			if(c == -1)
				rowExpanded.add(r);
			while(c != -1){
				galaxies.add(new int[]{r,c});
				colExpanded.remove(Integer.valueOf(c));
				c = currRow.indexOf("#", c+1);
			}
		}

		long sum = 0;
		int[][] galaxiesArray = galaxies.toArray(new int[0][0]);
		for(int i = 0; i < galaxiesArray.length; i++){
			int[] currGalaxy = galaxiesArray[i];
			for(int j = i+1; j < galaxiesArray.length; j++){
				sum += Math.abs(currGalaxy[0]-galaxiesArray[j][0])+Math.abs(currGalaxy[1]-galaxiesArray[j][1]);
				sum += checkExpansion(currGalaxy[0], galaxiesArray[j][0], rowExpanded)*(scale-1);
				sum += checkExpansion(currGalaxy[1], galaxiesArray[j][1], colExpanded)*(scale-1);
			}
		}
		System.out.println(sum);
	}

	private int checkExpansion(int m, int n, List<Integer> expansion){
		if(m > n)
			return checkExpansion(n, m, expansion);
		if(m == n)
			return 0;
		return (int) expansion.stream().filter(i -> i > m && i < n).count();
	}

	private List<Integer> fillList(int num){
		List<Integer> res = new ArrayList<>();
		for(int i = 0; i < num; i++)
			res.add(i);
		return res;
	}

	public static void main(String[] args){
		new Day11();
	}
}