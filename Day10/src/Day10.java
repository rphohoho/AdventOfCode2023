//https://adventofcode.com/2023/day/10

import java.util.*;
import java.io.*;

public class Day10{
	public Day10(){
		List<char[]> inputs = new ArrayList<>();
		int row = 0;
		int[] start = new int[]{0,0};
		try{
			File file = new File("../input.txt");
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				String input = sc.nextLine();
				if(input.indexOf('S') != -1)
					start = new int[]{row, input.indexOf('S')};
				inputs.add(input.toCharArray());
				row++;
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		part1(inputs.toArray(new char[0][0]), start);
	}

	private void part1(char[][] map, int[] currCoordinate){
		//System.out.println(currCoordinate[0] + " " + currCoordinate[1]);
		char previousDirection = findConnectedDirection(map, currCoordinate);
		char currPipe = updatePipe(map, previousDirection, currCoordinate);
		currCoordinate = updateCoordinate(previousDirection, currCoordinate);
		int totalSteps = 1;
		while(currPipe != 'S'){
			//System.out.println("Curr: " + currPipe + " preDir: " + previousDirection + " Coord: " + currCoordinate[0] + " " + currCoordinate[1]);
			previousDirection = findNextDirection(currPipe, previousDirection);
			currPipe = updatePipe(map, previousDirection, currCoordinate);
			currCoordinate = updateCoordinate(previousDirection, currCoordinate);
			totalSteps++;
		}
		System.out.println(totalSteps/2);
	}

	private char findConnectedDirection(char[][] map, int[] currCoordinate){
		int row = currCoordinate[0], col = currCoordinate[1];
		char nextPipe = map[row-1][col];
		if(nextPipe == '7' || nextPipe == 'F')
			return 'U';
		nextPipe = map[row+1][col];
		if(nextPipe == 'J' || nextPipe == 'L')
			return 'D';
		nextPipe = map[row][col-1];
		if(nextPipe == 'L' || nextPipe == 'F')
			return 'L';
		return 'R';
	}

	private char findNextDirection(char currPipe, char previousDirection){
		return switch(currPipe){
			case '|' -> previousDirection == 'U' ? 'U' : 'D';
			case '-' -> previousDirection == 'L' ? 'L' : 'R';
			case 'L' -> previousDirection == 'D' ? 'R' : 'U';
			case 'F' -> previousDirection == 'U' ? 'R' : 'D';
			case 'J' -> previousDirection == 'D' ? 'L' : 'U';
			case '7' -> previousDirection == 'U' ? 'L' : 'D';
			default  -> throw new IllegalStateException("This message should not appear");
		};
	}

	private char updatePipe(char[][] map, char previousDirection, int[] currCoordinate){
		int row = currCoordinate[0], col = currCoordinate[1];
		return switch(previousDirection){
			case 'U' -> map[row-1][col];
			case 'D' -> map[row+1][col];
			case 'L' -> map[row][col-1];
			default  -> map[row][col+1];
		};
	}

	private int[] updateCoordinate(char previousDirection, int[] currCoordinate){
		return switch(previousDirection){
			case 'U' -> new int[]{currCoordinate[0]-1, currCoordinate[1]};
			case 'D' -> new int[]{currCoordinate[0]+1, currCoordinate[1]};
			case 'L' -> new int[]{currCoordinate[0], currCoordinate[1]-1};
			default  -> new int[]{currCoordinate[0], currCoordinate[1]+1};
		};
	}

	public static void main(String[] args){
		new Day10();
	}
}