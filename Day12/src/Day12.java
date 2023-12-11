//

import java.util.*;
import java.io.*;

public class Day12{
	public Day12(){
		try{
			File file = new File("input.txt");
			Scanner sc = new Scanner(file);
			sc.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		new Day12();
	}
}