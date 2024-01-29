//https://adventofcode.com/2023/day/20

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day20{
	abstract class Module{
		String name;
		int inputPulse;
		List<String> outputModules;

		Module(String[] outputs){
			inputPulse = 0;
			outputModules = new ArrayList<>();
			for(int i = 1; i < outputs.length; i++)
				outputModules.add(outputs[i]);
		}

		void setInputPulse(String inputName, int pulse){inputPulse = pulse;}

		int outputPulse(){return 0;}

		List<String> getOutputModules(){return outputModules;}

		boolean isInitialState(){return true;}

	}

	class Broadcaster extends Module{
		Broadcaster(String[] outputs){
			super(outputs);
			name = outputs[0];
		}
	}

	class FlipFlop extends Module{
		boolean on;

		FlipFlop(String[] outputs){
			super(outputs);
			name = outputs[0].substring(1);
			on = false;
		}

		int outputPulse(){
			if(inputPulse == 0)
				on = !on;
			return on ? 1 : 0;
		}

		List<String> getOutputModules(){return inputPulse == 0 ? outputModules : new ArrayList<String>(0);}

		boolean isInitialState(){return on==false;}
	}

	class Conjunction extends Module{
		Map<String, Integer> inputModules;

		Conjunction(String[] outputs){
			super(outputs);
			name = outputs[0].substring(1);
			inputModules = new HashMap<>();
			for(int i = 1; i < outputs.length; i++)
				inputModules.put(outputs[i], 0);
		}

		void setInputPulse(String inputName, int pulse){inputModules.put(inputName, pulse);}

		int outputPulse(){return allHighPulses() ? 0 : 1;}

		boolean allHighPulses(){
			for(int p : inputModules.values())
				if(p != 1)
					return false;
			return true;
		}

		boolean isInitialState(){
			for(int p : inputModules.values())
				if(p != 0)
					return false;
			return true;
		}
	}

	public Day20(){
		List<String[]> inputs = new ArrayList<>();
		try{
			Scanner sc = new Scanner(new File("../test1.txt"));
			while(sc.hasNextLine())
				inputs.add(sc.nextLine().split("[\\W&&[^%&]]+"));
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		part1(inputs);
	}

	private void part1(List<String[]> inputs){
		Map<String, Module> modules = new HashMap<>();
		for(String[] input : inputs){
			char c = input[0].charAt(0);
			if(c == '%')
				modules.put(input[0].substring(1), new FlipFlop(input));
			else if(c == '&')
				modules.put(input[0].substring(1), new Conjunction(input));
			else
				modules.put(input[0], new Broadcaster(input));
		}

		LinkedList<Module> queue = new LinkedList<>();
		int[] pulses = new int[2];
		int cycleLength = 0;
		//do{
			cycleLength++;
			queue.add(modules.get("broadcaster"));
			Module curr;
			while(!queue.isEmpty()){
				curr = queue.poll();
				int output = curr.outputPulse();
				for(String name : curr.getOutputModules()){
					if(!modules.containsKey(name))
						continue;
					Module outputModule = modules.get(name);
					outputModule.setInputPulse(curr.name, output);
					queue.add(outputModule);
					pulses[output]++;
				}
			}
		//}while(!allInitialState(modules.values()));
		System.out.println(pulses[0]+" "+pulses[1]+" "+cycleLength);
	}

	private boolean allInitialState(Collection<Module> modules){
		for(Module m : modules)
			if(!m.isInitialState())
				return false;
		return true;
	}

	public static void main(String[] args){
		new Day20();
	}
}