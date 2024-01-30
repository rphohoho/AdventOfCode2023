//https://adventofcode.com/2023/day/20

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day20{
	class Module{
		/*
		  Base class for all modules
		  Also act as the broadcaster module which sends the same the pulse it receives to all output modules
		*/
		String name;
		int inputPulse;
		List<String> outputModules;
		Map<String, Integer> inputModules;

		Module(String[] outputs){
			name = outputs[0];
			inputPulse = 0;
			outputModules = new ArrayList<>();
			for(int i = 1; i < outputs.length; i++)
				outputModules.add(outputs[i]);
			inputModules = new HashMap<>();
		}

		void setInputPulse(String inputName, int pulse){
			inputPulse = pulse;
		}

		int getOutputPulse(){
			return 0;
		}

		List<String> getOutputModules(){
			return outputModules;
		}

		void addInputModule(String inputName){
			inputModules.put(inputName, 0);
		}

		boolean isInitialState(){
			return true;
		}

		void resetState(){}
	}

	class FlipFlop extends Module{
		/*
		  Initial state : off
		  When receiving low pulse, switches on/off and sends a pulse accordingly(on: high, off: low)
		  Does nothing when receiving high pulse
		*/
		boolean on;

		FlipFlop(String[] outputs){
			super(outputs);
			name = outputs[0].substring(1);
			on = false;
		}

		void setInputPulse(String inputName, int pulse){
			inputPulse = pulse;
			if(inputPulse == 0)
				on = !on;
		}

		int getOutputPulse(){
			return on ? 1 : 0;
		}

		List<String> getOutputModules(){
			return inputPulse == 0 ? outputModules : new ArrayList<String>();
		}

		boolean isInitialState(){
			return on==false;
		}

		void resetState(){
			on = false;
		}
	}

	class Conjunction extends Module{
		/*
		  Initial state : all input modules are sending low pulse
		  Sends low pulse when all input modules are sending high pulse
		  Sends high pulse at other conditions
		*/
		Conjunction(String[] outputs){
			super(outputs);
			name = outputs[0].substring(1);
		}

		void setInputPulse(String inputName, int pulse){
			inputModules.put(inputName, pulse);
		}

		int getOutputPulse(){
			for(int p : inputModules.values())
				if(p != 1)
					return 1;
			return 0;
		}

		boolean isInitialState(){
			for(int p : inputModules.values())
				if(p != 0)
					return false;
			return true;
		}

		void resetState(){
			inputModules.replaceAll((k,v) -> 0);
		}
	}

	class Handler{
		int inputPulse;
		String from, to;

		Handler(String from, int inputPulse, String to){
			this.from = from;
			this.inputPulse = inputPulse;
			this.to = to;
		}

		int processPulse(Map<String, Module> modules, LinkedList<Handler> queue){
			if(modules.containsKey(to)){
				Module curr = modules.get(to);
				curr.setInputPulse(from, inputPulse);
				int outputPulse = curr.getOutputPulse();
				for(String outputModule : curr.getOutputModules())
					queue.add(new Handler(to, outputPulse, outputModule));
			}
			return inputPulse;
		}

		boolean checkModule(String name, int pulse){
			return from.equals(name) && inputPulse==pulse;
		}
	}

	public Day20(){
		Map<String, Module> modules = new HashMap<>();

		try{
			Scanner sc = new Scanner(new File("../input.txt"));
			while(sc.hasNextLine()){
				String[] input = sc.nextLine().split("[\\W&&[^%&]]+");
				char c = input[0].charAt(0);
				if(c == '%')
					modules.put(input[0].substring(1), new FlipFlop(input));
				else if(c == '&')
					modules.put(input[0].substring(1), new Conjunction(input));
				else
					modules.put(input[0], new Module(input));
			}
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}

		//set connection to input modules, typically for conjunction modules
		for(Module m : modules.values()){
			String name = m.name;
			for(String outputModule : m.getOutputModules())
				if(modules.containsKey(outputModule))
					modules.get(outputModule).addInputModule(name);
		}

		//part1(modules);
		part2(modules);
	}

	private void part1(Map<String, Module> modules){
		LinkedList<Handler> queue = new LinkedList<>();
		int[] pulseCount = new int[2];
		int cycleLength = 0;
		do{
			cycleLength++;
			queue.add(new Handler("button", 0, "broadcaster"));
			while(!queue.isEmpty()){
				Handler handler = queue.pop();
				pulseCount[handler.processPulse(modules, queue)]++;
			}
		}while(!allInitialState(modules.values()) && cycleLength < 1000);

		//System.out.println(pulseCount[0]+" "+pulseCount[1]+" "+cycleLength);
		System.out.println(pulseCount[0]*(1000/cycleLength)*pulseCount[1]*(1000/cycleLength));
	}

	private void part2(Map<String, Module> modules){
		/*
		  Result is too large to obtain through simulation
		  Instead of finding when a low pulse is sent to rx, find when the previous module, zh, will send a low pulse
		  zh is a conjunction module and it receives inputs from four modules: xc, th, pd, bp
		  For each of the module, find when will it send a high pulse
		  Multiply the numbers will result to the time zh receives high pulse from all four modules
		*/
		String[] modulesToBeChecked = new String[]{"xc", "th", "pd", "bp"};
		LinkedList<Handler> queue = new LinkedList<>();
		long res = 1;
		for(String m : modulesToBeChecked){
			int buttonPressed = 0;
			boolean nextModule = false;
			while(!nextModule){
				buttonPressed++;
				queue.add(new Handler("button", 0, "broadcaster"));
				while(!queue.isEmpty()){
					Handler handler = queue.pop();
					if(handler.checkModule(m, 1)){
						//System.out.println(buttonPressed);
						res *= buttonPressed;
						nextModule = true;
						break;
					}
					handler.processPulse(modules, queue);
				}
			}
			modules.forEach((k,v) -> v.resetState());
		}
		System.out.println(res);
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