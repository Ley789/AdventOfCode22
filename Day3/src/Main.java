import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {


	//convention filename is in the same folder as the executable
	public static List<String> ReadProblem (String filename){
        Path p = Paths.get(System.getProperty("user.dir"));
        p = p.resolve(filename);
        List<String> res = new ArrayList<>();
        File f = p.toFile();
	    f.setReadOnly();
        try (Scanner scan = new Scanner (f)) {
		    while (scan.hasNextLine()) {
		      res.add(scan.nextLine());
	          }
        } catch (FileNotFoundException ex) {
        	System.out.println("Read conventions bro");
        }
      return res;
	}

   public static int charToInt(char i) {
	  if (Character.isLowerCase(i)) return (int)i - 96;
	  return (int)i - 38;
   }
   
   public static Set<Character> stringToHashSet(String s){
	   HashSet<Character> h = new HashSet<Character>();
	   for (int i = 0; i < s.length(); i++) {
		   h.add(s.charAt(i));
	   }
	   return h;
   }
	
   public static Character checkRucksack(String s) {
	   String first = s.substring(0, s.length()/2);
	   String second = s.substring(s.length()/2);
	   Set<Character> chars = stringToHashSet(first);
	   chars.retainAll(stringToHashSet(second));
	   Character[] res = chars.toArray(new Character[0]);
	   if(res.length != 1) throw new IllegalArgumentException();
	   return res[0];
   }
   
   public static class Triple {
	   String f;
	   String s;
	   String t;
	   Triple(String a, String b, String c){
		   f = a; s = b; t = c;
	   }
   }
   
   public static List<Triple> fromStringList(List<String> l){
	   if(l.size() % 3 != 0) throw new IllegalArgumentException();
	   List<Triple> res = new ArrayList<>();
	   for(int i = 0; i < l.size(); i = i + 3) {
		   res.add(new Triple(l.get(i), l.get(i + 1), l.get(i + 2)));
	   }
	   return res;
   }
   
   public static Character checkRucksackTriple(Triple triple) {
	   Set<Character> chars = stringToHashSet(triple.f);
	   chars.retainAll(stringToHashSet(triple.s));
	   chars.retainAll(stringToHashSet(triple.t));
	   Character[] res = chars.toArray(new Character[0]);
	   if(res.length != 1) throw new IllegalArgumentException();
	   return res[0];
   }
   
   public static void main(String[] args) {
		List<String> input = ReadProblem("InputFile.txt");
		List<Triple> tripleList = fromStringList(input);
		int r = input.stream().map(Main::checkRucksack).map(Main::charToInt).reduce(0,Integer::sum);
        int r2= tripleList.stream().map(Main::checkRucksackTriple).map(Main::charToInt).reduce(0,Integer::sum);
		System.out.println(r);
		System.out.println(r2);
	}

}
