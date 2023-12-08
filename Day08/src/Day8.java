//https://adventofcode.com/2023/day/8

import java.util.*;
import java.io.*;

public class Day8{
	public Day8(){
		Queue<Character> instructions = new LinkedList<>();
		Map<String, String[]> map = new HashMap<>();
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			for(char c : sc.nextLine().toCharArray())
				instructions.offer(c);
			sc.nextLine();
			while(sc.hasNextLine()){
				String[] input = sc.nextLine().split("\\W+");
				map.put(input[0], new String[]{input[1], input[2]});
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		part1(instructions, map);
	}

	private void part1(Queue<Character> instructions, Map<String, String[]> map){
		int steps = 0;
		String currentNode = "AAA";
		while(!currentNode.equals("ZZZ")){
			if(instructions.peek() == 'L')
				currentNode = map.get(currentNode)[0];
			else
				currentNode = map.get(currentNode)[1];
			instructions.offer(instructions.poll());
			steps++;
		}
		System.out.println(steps);
	}

	public static void main(String[] args){
		new Day8();
	}
}