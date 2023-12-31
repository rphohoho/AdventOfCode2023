//https://adventofcode.com/2023/day/15

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day15{
	public Day15(){
		String[] inputs = new String[0];
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			inputs = sc.nextLine().split(",");
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		part1(inputs);
	}

	private void part1(String[] inputs){
		int res = 0;
		for(String input : inputs)
			res += hash(input);
		System.out.println(res);
	}

	private int hash(String s){
		int hashcode = 0;
		for(char c : s.toCharArray()){
			hashcode += c;
			hashcode *= 17;
			hashcode %= 256;
		}
		return hashcode;
	}

	public static void main(String[] args){
		new Day15();
	}
}