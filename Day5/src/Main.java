
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main {
	
	public static class CranCommand {
		int magnitude;
		int fromStack;
		int toStack;
		
		CranCommand(int m, int f, int t){
			magnitude = m;
			fromStack = f;
			toStack = t;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Stack<Character>> startState = ReadStartState("StartState.txt");
		List<CranCommand> input = ReadProblem("InputFile.txt");
		Stack<Character> tmp = new Stack<>();
		for (var i : input) {
			Stack<Character> from = startState.get(i.fromStack);
			Stack<Character> to = startState.get(i.toStack);
			for (int j = 0; j < i.magnitude; j++)
			  tmp.push(from.pop());
			for (int j = 0; j < i.magnitude; j++)
	          to.push(tmp.pop());
		}
		startState.stream().forEachOrdered(e -> System.out.print(e.peek()));
	}
	
	public static List<String> transpose(List<String> list){
		int size = list.stream().map(String::length).reduce(0, Integer::max);
		List<StringBuilder> tmp = new ArrayList<>(size);
		String line;
		for (int i = 0; i < size; i++)  tmp.add(new StringBuilder());
		for (int i = 0; i < list.size(); i++) {
			line = list.get(i);
			for (int j = 0; j < line.length(); j++) {
				tmp.get(j).append(line.charAt(j));
			}
		}
		return tmp.stream().map(StringBuilder::toString).collect(Collectors.toCollection(ArrayList::new));
	}

	//convention filename is in the same folder as the executable
	public static List<Stack<Character>> ReadStartState (String filename){
        Path p = Paths.get(System.getProperty("user.dir"));
        p = p.resolve(filename);
        List<Stack<Character>> startState = null;
        List<String> input = null;
		try {
			input = transpose(Files.readAllLines(p));
		    input = input.stream().filter(e -> !(e.matches("\\s*\\[+|\\s*\\]+")))
		    		.filter(e -> e.matches("\\s*\\w+"))
		    		.map(e -> e.strip())
		    		.collect(Collectors.toCollection(ArrayList::new));
			startState = new ArrayList<>(input.size());
			//create stacks
			for (int i = 0; i < input.size(); i++) startState.add(new Stack<>());
			//fill the stacks
		 	for (int i = 0; i < input.size(); i++) {
		 		String s = input.get(i);
		 		for (int j = s.length() - 1; j >= 0; j--) {
		 		  	startState.get(i).push(s.charAt(j));
		 		}
			}
		} catch (IOException e) {}
        return startState;
	}
	
	//convention filename is in the same folder as the executable
	public static List<CranCommand> ReadProblem (String filename){
        Path p = Paths.get(System.getProperty("user.dir"));
        p = p.resolve(filename);
        List<CranCommand> res = new ArrayList<>();
        File f = p.toFile();
	    f.setReadOnly();
	    String[] line;
        try (Scanner scan = new Scanner (f)) {
		    while (scan.hasNextLine()) {
		        line = scan.nextLine().split("move|from|to");
		        res.add(new CranCommand(Integer.parseInt(line[1].trim()),
		        		Integer.parseInt(line[2].trim()) - 1,
		        		Integer.parseInt(line[3].trim()) -1));
	          }
        } catch (FileNotFoundException ex) {
        	System.out.println("Read conventions bro");
        }
      return res;
	}

}
