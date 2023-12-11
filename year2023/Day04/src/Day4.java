//https://adventofcode.com/2023/day/4

import java.util.*;
import java.io.*;

public class Day4{
	public Day4(){
		List<int[]> inputs = new ArrayList<>();
		try{
			File file = new File("../test.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				inputs.add(Arrays.stream(sc.nextLine().split("\\D+"))
								 .filter(s -> !s.isEmpty())
								 .skip(1)
								 .mapToInt(Integer::parseInt)
								 .toArray());
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		//part1(inputs);
		part2(inputs);
	}

	private void part1(List<int[]> inputs){
		int sum = 0;
		for(int[] input : inputs){
			Set<Integer> set = new HashSet<>();
			int points = 1;
			for(int i : input){
				if(!set.add(i))
					points <<= 1;
			}
			sum += points>>1;
		}
		System.out.println(sum);
	}

	private void part2(List<int[]> inputs){
		int sum = 0;
		int[] cards = new int[inputs.size()];
		Arrays.fill(cards, 1);
		for(int i = 0; i < inputs.size(); i++){
			Set<Integer> set = new HashSet<>();
			int matching = 0;
			for(int num : inputs.get(i)){
				if(!set.add(num))
					matching++;
			}
			int copies = cards[i];
			while(matching != 0){
				cards[i+matching] += copies;
				matching--;
			}
		}
		for(int card : cards)
			sum += card;
		System.out.println(sum);
	}

	public static void main(String[] args){
		new Day4();
	}
}