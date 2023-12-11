//https://adventofcode.com/2023/day/7

import java.util.*;
import java.util.stream.*;
import java.io.*;

public class Day7{
	enum HandType{
		HIGHCARD, ONEPAIR, TWOPAIR, THREEKIND, FULLHOUSE, FOURKIND, FIVEKIND;
	}

	class Hand{
		String cards;
		int bid;
		HandType type;

		public Hand(String[] input, boolean part2){
			cards = convertHand(input[0], part2);
			bid = Integer.parseInt(input[1]);
			type = handValue(input[0].toCharArray(), part2);
		}
		
		private String convertHand(String input, boolean part2){
			String convertedInput = input.replace('T', ':').replace('Q', '<').replace('K', '=').replace('A', '>');
			if(part2)
				return convertedInput.replace('J', '1');
			return convertedInput.replace('J', ';');
		}

		private HandType handValue(char[] input, boolean part2){
			Map<Character, Integer> counts = new HashMap<>();
			for(int i = 0; i < 5; i++)
				counts.merge(input[i], 1, Integer::sum);
			int pair = (int) counts.values().stream().filter(i -> i==2).count();
			int value = counts.values().stream().mapToInt(Integer::intValue).reduce(1, (a,b) -> a*b);
			HandType temp = switch(value){
				case 5 -> HandType.FIVEKIND;
				case 4 -> pair == 2 ? HandType.TWOPAIR : HandType.FOURKIND;
				case 6 -> HandType.FULLHOUSE;
				case 3 -> HandType.THREEKIND;
				case 2 -> HandType.ONEPAIR;
				default -> HandType.HIGHCARD;
			};

			if(part2){
				int joker = counts.getOrDefault('J', 0);
				temp = switch(joker){
					case 4 -> HandType.FIVEKIND;
					case 3 -> pair > 0 ? HandType.FIVEKIND : HandType.FOURKIND;
					case 2 -> switch(value){
						case 2 -> HandType.THREEKIND;
						case 4 -> HandType.FOURKIND;
						default -> HandType.FIVEKIND;
						};
					case 1 -> switch(value){
						case 4 -> pair == 2 ? HandType.FULLHOUSE : HandType.FIVEKIND;
						case 3 -> HandType.FOURKIND;
						case 2 -> HandType.THREEKIND;
						default -> HandType.ONEPAIR;
						};
					default -> temp;
				};
			}
			return temp;
		}

		public int compareTo(Hand that){
			if(this.type == that.type)
				return this.cards.compareTo(that.cards);
			return this.type.compareTo(that.type);
		}
	}

	public Day7(){
		List<Hand> list = new ArrayList<>();
		boolean part2 = true;
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine())
				list.add(new Hand(sc.nextLine().split(" "), part2));
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		Collections.sort(list, (h1, h2) -> h1.compareTo(h2));
		part(list);
	}

	private void part(List<Hand> list){
		int winning = 0;
		int rank = 1;
		for(Hand curr : list){
			winning += curr.bid*rank;
			rank++;
		}
		System.out.println(winning);
	}

	public static void main(String[] args){
		new Day7();
	}
}