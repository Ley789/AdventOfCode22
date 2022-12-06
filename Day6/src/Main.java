
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {

	public static Integer checkMarker(String signal, int distinctChars) {
		MultiSet<Character> mset = new MultiSet<>();
		if (signal.length() < distinctChars + 1) throw new IllegalArgumentException();
		//fill the multiset
		for (int i = 0; i < distinctChars; i++) mset.add(signal.charAt(i));
		//check if we find the start of the marker
		for (int i = distinctChars; i < signal.length(); i++) {
			//we encountered {@param distinctChars} different characters
			if(mset.distinctElements() == distinctChars) {
				return i;
			}
			mset.remove(signal.charAt(i - distinctChars));
			mset.add(signal.charAt(i));
		}
		return null;
	}
	
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = ReadProblem("InputFile.txt");
		MultiSet<Character> mset = new MultiSet<>();
		//First part
		System.out.println(checkMarker(input, 4));
		//second part
		System.out.println(checkMarker(input, 14));
	}
	


	public static String ReadProblem (String filename){
        Path p = Paths.get(System.getProperty("user.dir"));
        p = p.resolve(filename);
        File f = p.toFile();
	    f.setReadOnly();
	    String line = null;
        try (Scanner scan = new Scanner (f)) {
		    if (scan.hasNextLine()) {
		        line = scan.nextLine();
	          }
        } catch (FileNotFoundException ex) {
        	System.out.println("Read conventions bro");
        }
      return line;
	}

}
