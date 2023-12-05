//https://adventofcode.com/2023/day/5

import java.util.*;
import java.io.*;

public class Day5{
	long[] seeds;
	TreeMap<Long, long[]> seedToSoil = new TreeMap<>();
	TreeMap<Long, long[]> soilToFertilizer = new TreeMap<>();
	TreeMap<Long, long[]> fertilizerToWater = new TreeMap<>();
	TreeMap<Long, long[]> waterToLight = new TreeMap<>();
	TreeMap<Long, long[]> lightToTemperature = new TreeMap<>();
	TreeMap<Long, long[]> temperatureToHumidity = new TreeMap<>();
	TreeMap<Long, long[]> humidityToLocation = new TreeMap<>();

	public Day5(){
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			seeds = Arrays.stream(sc.nextLine().split("\\D+"))
						  .filter(s -> !s.isEmpty())
						  .mapToLong(Long::parseLong)
						  .toArray();
			sc.skip("\\D*");
			while(sc.hasNextLine()){
				long[] input = Arrays.stream(sc.nextLine().split("\\D+"))
									.filter(s -> !s.isEmpty())
									.mapToLong(Long::parseLong)
									.toArray();
				if(input.length == 0)
					break;
				seedToSoil.put(input[1], new long[]{input[0], input[2]});
			}
			sc.nextLine();
			while(sc.hasNextLine()){
				long[] input = Arrays.stream(sc.nextLine().split("\\D+"))
									.filter(s -> !s.isEmpty())
									.mapToLong(Long::parseLong)
									.toArray();
				if(input.length == 0)
					break;
				soilToFertilizer.put(input[1], new long[]{input[0], input[2]});
			}
			sc.nextLine();
			while(sc.hasNextLine()){
				long[] input = Arrays.stream(sc.nextLine().split("\\D+"))
									.filter(s -> !s.isEmpty())
									.mapToLong(Long::parseLong)
									.toArray();
				if(input.length == 0)
					break;
				fertilizerToWater.put(input[1], new long[]{input[0], input[2]});
			}
			sc.nextLine();
			while(sc.hasNextLine()){
				long[] input = Arrays.stream(sc.nextLine().split("\\D+"))
									.filter(s -> !s.isEmpty())
									.mapToLong(Long::parseLong)
									.toArray();
				if(input.length == 0)
					break;
				waterToLight.put(input[1], new long[]{input[0], input[2]});
			}
			sc.nextLine();
			while(sc.hasNextLine()){
				long[] input = Arrays.stream(sc.nextLine().split("\\D+"))
									.filter(s -> !s.isEmpty())
									.mapToLong(Long::parseLong)
									.toArray();
				if(input.length == 0)
					break;
				lightToTemperature.put(input[1], new long[]{input[0], input[2]});
			}
			sc.nextLine();
			while(sc.hasNextLine()){
				long[] input = Arrays.stream(sc.nextLine().split("\\D+"))
									.filter(s -> !s.isEmpty())
									.mapToLong(Long::parseLong)
									.toArray();
				if(input.length == 0)
					break;
				temperatureToHumidity.put(input[1], new long[]{input[0], input[2]});
			}
			sc.nextLine();
			while(sc.hasNextLine()){
				long[] input = Arrays.stream(sc.nextLine().split("\\D+"))
									.filter(s -> !s.isEmpty())
									.mapToLong(Long::parseLong)
									.toArray();
				if(input.length == 0)
					break;
				humidityToLocation.put(input[1], new long[]{input[0], input[2]});
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		for(long k : humidityToLocation.keySet()){
			System.out.print(k + " ");
			for(long v : humidityToLocation.get(k))
				System.out.print(v + " ");
			System.out.println();
		}
		//part1();
	}

	private void part1(){
		long minLocation = Long.MAX_VALUE;
		for(long seed : seeds){
		}
	}

	public static void main(String[] args){
		new Day5();
	}
}