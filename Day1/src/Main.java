import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static boolean isNumeric(String strNum) {
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
	    if (strNum == null) {
	        return false; 
	    }
	    return pattern.matcher(strNum).matches();
	}
	//convention filename is in the same folder as the executable
	public static List<List<Long>> ReadProblem (String filename){
        Path p = Paths.get(System.getProperty("user.dir"));
        p = p.resolve(filename);
        List<List<Long>> res = new ArrayList<>();
        File f = p.toFile();
	    f.setReadOnly();
	    String line;
        try (Scanner scan = new Scanner (f)) {
		    while (scan.hasNextLine()) {
	          List<Long> currentElfCalories = new ArrayList<>(); 
	          while (scan.hasNextLong()) {
	        	 line = scan.nextLine();
	        	 if(isNumeric(line))  currentElfCalories.add(Long.parseLong(line));
	        	 else break;
	          }
	          //newlines represent elf switches
	          res.add(currentElfCalories);
		    }
        } catch (FileNotFoundException ex) {
        	System.out.println("Read conventions bro");
        }
      return res;
	}

	
	public static void main(String[] args) {
		List<List<Long>> input = ReadProblem("InputFile.txt");
		List<Long> accumulatedCal = input.stream().map(list -> list.stream().reduce((long) 0, Long::sum)).sorted().collect(Collectors.toList());
		Long max = accumulatedCal.get(accumulatedCal.size() - 1);
		Long secondToMax = accumulatedCal.get(accumulatedCal.size() - 2);
		Long thirdToMax = accumulatedCal.get(accumulatedCal.size() - 3);
		System.out.println(max);
		System.out.println(max + secondToMax + thirdToMax);
	}

}
