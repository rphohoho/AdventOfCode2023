//https://adventofcode.com/2023/day/13

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13{
	public Day13(){
		List<String[]> inputs = new ArrayList<>();
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			ArrayList<String> temp = new ArrayList<>();
			while(sc.hasNextLine()){
				String input = sc.nextLine();
				if(input.equals("")){
					inputs.add(temp.toArray(String[]::new));
					temp = new ArrayList<>();
				}
				else
					temp.add(input);
			}
			inputs.add(temp.toArray(String[]::new));
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		//part1(inputs);
		part2(inputs);
	}

	private void part1(List<String[]> inputs){
		int sum = 0;
		for(String[] input : inputs)
			sum += reflectionLine(input, 100);
		System.out.println(sum);
	}

	private void part2(List<String[]> inputs){
		int sum = 0;
		for(String[] input : inputs)
			sum += reflectionLine2(input, 100);
		System.out.println(sum);
	}

	//part1
	private int reflectionLine(String[] input, int multiplier){
		int len = input.length;
		for(int i = 0; i < len-1; i++){
			if(input[i].equals(input[i+1])){
				int l = i, r = i+1;
				while(--l >= 0 && ++r < len && input[l].equals(input[r])){}
				if(l == -1 || r == len)
					return multiplier*(i+1);
			}
		}
		return reflectionLine(transpose(input), 1);
	}

	//part2
	private int reflectionLine2(String[] input, int multiplier){
		int len = input.length;
		for(int i = 0; i < len-1; i++){
			int num = checkSmudge(input[i].toCharArray(), input[i+1].toCharArray());
			if(num != -1){
				int smudges = num;
				int l = i, r = i+1;
				while(--l >= 0 && ++r < len){
					num = checkSmudge(input[l].toCharArray(), input[r].toCharArray());
					if(num == -1 || smudges > 1) break;
					smudges += num;
				}
				if((l == -1 || r == len) && smudges == 1)
					return multiplier*(i+1);
			}
		}
		return reflectionLine2(transpose(input), 1);
	}

	private String[] transpose(String[] s){
		int row = s.length, col = s[0].length();

		char[][] sNew = new char[row][];
		for(int i = 0; i < row; i++)
			sNew[i] =  s[i].toCharArray();

		char[][] sNewPrime = new char[col][row];
		for(int r = 0; r < row; r++)
			for(int c = 0; c < col; c++)
				sNewPrime[c][r] = sNew[r][c];

		String[] sPrime = new String[col];
		for(int i = 0; i < col; i++)
			sPrime[i] = new String(sNewPrime[i]);

		return sPrime;
	}

	private int checkSmudge(char[] s1, char[] s2){
		int len = s1.length;
		boolean fixed = false;
		for(int i = 0; i < len; i++)
			if(s1[i] != s2[i])
				if(!fixed)
					fixed = true;
				else
					return -1;
		return fixed ? 1 : 0;
	}

	public static void main(String[] args){
		new Day13();
	}
}