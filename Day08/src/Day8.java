//https://adventofcode.com/2023/day/8

import java.util.*;
import java.io.*;

public class Day8{
	public Day8(){
		char[] instructions = new char[0];
		Map<String, String[]> map = new HashMap<>();
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			instructions = sc.nextLine().toCharArray();
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
		//System.out.println(part1(instructions, map, "AAA", "ZZZ"));
		part2(instructions, map, map.keySet().stream().filter(s->s.matches("\\w{2}A")).toArray(String[]::new));
	}

	private int part1(char[] instructions, Map<String, String[]> map, String currentNode, String targetNode){
		int steps = 0;
		int ind = 0;
		while(!currentNode.matches(targetNode)){
			if(instructions[ind] == 'L')
				currentNode = map.get(currentNode)[0];
			else
				currentNode = map.get(currentNode)[1];
			ind = ind+1 == instructions.length ? 0 : ind+1;
			steps++;
		}
		return steps;
	}

	private void part2(char[] instructions, Map<String, String[]> map, String[] currentNodes){
		//Original idea is to simulate each node until all reach the target,
		//however the result is too large for the simulation.
		/*
		int ind = 0;
		while(!allNodesInZ(currentNodes)){
			if(instructions[ind] == 'L')
				for(int i = 0; i < currentNodes.length; i++)
					currentNodes[i] = map.get(currentNodes[i])[0];
			else
				for(int i = 0; i < currentNodes.length; i++)
					currentNodes[i] = map.get(currentNodes[i])[1];
			ind = ind+1 == instructions.length ? 0 : ind+1;
			steps++;
		}
		*/

		//Through observation, all nodes reach the target at the end of the instructions, 
		//meaning each node requires (n*instructions.length) steps till the target,
		//and the target node of each starting node is unique and no other starting nodes can reach one's target node,
		//thus LCM can be used to calculate the result.
		long res = instructions.length;
		long[] steps = new long[currentNodes.length];
		for(int i = 0; i < currentNodes.length; i++)
			steps[i] = (long)part1(instructions, map, currentNodes[i], "\\w{2}Z")/res;
		for(long step : steps)
			res *= step;
		System.out.println(res);
	}

	//The result is too large to use this function to check each node after each steps
	private boolean allNodesInZ(String[] currentNodes){
		for(String node : currentNodes)
			if(!node.matches("\\w{2}Z"))
				return false;
		return true;
		//return Arrays.stream(currentNodes).filter(s -> s.matches("\\w{2}Z")).count() == currentNodes.length;
	}

	public static void main(String[] args){
		new Day8();
	}
}