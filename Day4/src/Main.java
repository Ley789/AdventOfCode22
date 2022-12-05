import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	
	public static boolean PairContains(Pair<Range, Range> r) {
		if (r.first.equals(r.second)) return true;
		if (r.first.contains(r.second)) return true;
		if (r.second.contains(r.first)) return true;
		return false;
	}
	
	
	public static boolean PairOverlaps(Pair<Range, Range> r) {
		if (r.first.equals(r.second)) return true;
		if (r.first.overlap(r.second)) return true;
		if (r.second.overlap(r.first)) return true;
		return false;
	}
	
	public static int booleanToInt(boolean b) {
		if (b) return 1;
		return 0;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 List<Pair<Range,Range>> input = ReadProblem("InputFile.txt");
		 int r1 = input.stream().map(Main::PairContains).map(Main::booleanToInt).reduce(0, Integer::sum);
		 int r2 = input.stream().map(Main::PairOverlaps).map(Main::booleanToInt).reduce(0, Integer::sum);
		 System.out.println(r1);
		 System.out.println(r2);
	}
	
	public static class Pair<T, U> {
		T first;
		U second;
		Pair(T a, U b){
			first = a; second = b;
		}
	}
	
	public static Range rangeFromString(String s) {
		String[] values = s.split("-");
		if(values.length != 2) throw new IllegalArgumentException();
		return new Range(Long.parseLong(values[0]),Long.parseLong(values[1]));
	}
	
	//convention filename is in the same folder as the executable
	public static List<Pair<Range,Range>> ReadProblem (String filename){
        Path p = Paths.get(System.getProperty("user.dir"));
        p = p.resolve(filename);
        List<Pair<Range,Range>> res = new ArrayList<>();
        File f = p.toFile();
	    f.setReadOnly();
	    String[] pairs;
        try (Scanner scan = new Scanner (f)) {
		    while (scan.hasNextLine()) {
		        pairs = scan.nextLine().split(",");
		        res.add(new Pair<>(rangeFromString(pairs[0]), rangeFromString(pairs[1])));
	          }
        } catch (FileNotFoundException ex) {
        	System.out.println("Read conventions bro");
        }
      return res;
	}

}
