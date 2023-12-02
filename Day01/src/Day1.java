//https://adventofcode.com/2023/day/1

import java.util.*;
import java.util.stream.*;
import java.io.*;

public class Day1{
	public Day1(){
		//part1();
		part2();
	}

	private void part1(){
		int sum = 0;
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String[] filteredNumber = Arrays.stream(sc.nextLine().split("\\D*"))
												.filter(s -> !s.isEmpty())
												.toArray(String[]::new);
				sum += Integer.parseInt(filteredNumber[0])*10;
				sum += Integer.parseInt(filteredNumber[filteredNumber.length-1]);
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		System.out.println(sum);
	}

	class Number{
		String stringNum;
		int intNum;
		int index;

		public Number(String stringNum, int intNum){
			this.stringNum = stringNum;
			this.intNum = intNum;
		}

		public Number setFirstIndex(String input){
			index = input.indexOf(stringNum);
			return this;
		}

		public Number setLastIndex(String input){
			index = input.lastIndexOf(stringNum);
			return this;
		}
	}

	private void part2(){
		int sum = 0;
		Number[] nums = new Number[]{new Number("one", 1), new Number("two", 2), new Number("three", 3), new Number("four", 4), 
									 new Number("five", 5), new Number("six", 6), new Number("seven", 7), new Number("eight", 8), new Number("nine", 9), 
									 new Number("1", 1), new Number("2", 2), new Number("3", 3), new Number("4", 4), 
									 new Number("5", 5), new Number("6", 6), new Number("7", 7), new Number("8", 8), new Number("9", 9)};
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String input = sc.nextLine();
				sum += Arrays.stream(nums)
							 .map(n -> n.setFirstIndex(input))
							 .filter(n -> n.index >= 0)
							 .sorted((n1, n2) -> n1.index-n2.index)
							 .findFirst().get().intNum*10;
				sum += Arrays.stream(nums)
							 .map(n -> n.setLastIndex(input))
							 .filter(n -> n.index >= 0)
							 .sorted((n1, n2) -> n2.index-n1.index)
							 .findFirst().get().intNum;
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		System.out.println(sum);
	}
	
	public static void main(String[] args){
		new Day1();
	}
}