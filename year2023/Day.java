package year2023;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public abstract class Day{

	public final Stream<String> getInput(int day){
		final String inputPath = String.format("../year2023/day%d/input.txt", day);
		List<String> inputs = new ArrayList<>();
		try{
			Scanner sc = new Scanner(new File(inputPath));
			while(sc.hasNextLine())
				inputs.add(sc.nextLine());
			sc.close();
			return inputs.stream();
		}
		catch(FileNotFoundException e){
			throw new RuntimeException(e);
		}
	}

	public abstract void part1();

	public abstract void part2();

	public final void run(int num){
		if(num == 1)
			part1();
		else
			part2();
	}
}