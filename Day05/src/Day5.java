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
		//part1();
		part2();
	}

	private void part1(){
		long minLocation = Long.MAX_VALUE;
		for(long seed : seeds){
			long soil = findDestination(seed, seedToSoil);
			long fertilizer = findDestination(soil, soilToFertilizer);
			long water = findDestination(fertilizer, fertilizerToWater);
			long light = findDestination(water, waterToLight);
			long temperature = findDestination(light, lightToTemperature);
			long humidity = findDestination(temperature, temperatureToHumidity);
			long location = findDestination(humidity, humidityToLocation);
			minLocation = Math.min(minLocation, location);
		}
		System.out.println(minLocation);
	}

	private void part2(){
		long minLocation = Long.MAX_VALUE;
		for(int i = 0; i < seeds.length; i += 2){
			long seed = seeds[i];
			for(long range = seeds[i+1]; range > 0 ; range--){
				long soil = findDestination(seed, seedToSoil);
				long fertilizer = findDestination(soil, soilToFertilizer);
				long water = findDestination(fertilizer, fertilizerToWater);
				long light = findDestination(water, waterToLight);
				long temperature = findDestination(light, lightToTemperature);
				long humidity = findDestination(temperature, temperatureToHumidity);
				long location = findDestination(humidity, humidityToLocation);
				minLocation = Math.min(minLocation, location);
				seed++;
			}
		}
		System.out.println(minLocation);
	}

	private long findDestination(long num, TreeMap<Long, long[]> map){
		long source;
		try{
			source = map.floorKey(num);
		}
		catch(NullPointerException e){
			return num;
		}
		long destination = map.get(source)[0];
		long range = map.get(source)[1];
		if(num < source+range)
			return destination+(num-source);
		return num;
	}

	private void checkInputValid(){
		for(long k : seeds)
			System.out.print(k + " ");
		System.out.println();
		for(long k : seedToSoil.keySet()){
			System.out.print(k + " ");
			for(long v : seedToSoil.get(k))
				System.out.print(v + " ");
			System.out.println();
		}
		for(long k : soilToFertilizer.keySet()){
			System.out.print(k + " ");
			for(long v : soilToFertilizer.get(k))
				System.out.print(v + " ");
			System.out.println();
		}
		for(long k : fertilizerToWater.keySet()){
			System.out.print(k + " ");
			for(long v : fertilizerToWater.get(k))
				System.out.print(v + " ");
			System.out.println();
		}
		for(long k : waterToLight.keySet()){
			System.out.print(k + " ");
			for(long v : waterToLight.get(k))
				System.out.print(v + " ");
			System.out.println();
		}
		for(long k : lightToTemperature.keySet()){
			System.out.print(k + " ");
			for(long v : lightToTemperature.get(k))
				System.out.print(v + " ");
			System.out.println();
		}
		for(long k : temperatureToHumidity.keySet()){
			System.out.print(k + " ");
			for(long v : temperatureToHumidity.get(k))
				System.out.print(v + " ");
			System.out.println();
		}
		for(long k : humidityToLocation.keySet()){
			System.out.print(k + " ");
			for(long v : humidityToLocation.get(k))
				System.out.print(v + " ");
			System.out.println();
		}
	}

	public static void main(String[] args){
		new Day5();
	}
}