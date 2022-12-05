import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static enum RockPaperSicc {
		ROCK,
		PAPER,
		SICCOR,
	}
	
	public static enum GameResult {
		WIN,
		LOSE,
		DRAW,
	}
	
	public static RockPaperSicc fromChar(char i) {
		switch(i) {
		case 'A': return RockPaperSicc.ROCK;
		case 'X': return RockPaperSicc.ROCK;

		case 'B': return RockPaperSicc.PAPER;
		case 'Y': return RockPaperSicc.PAPER;

		case 'C': return RockPaperSicc.SICCOR;
		case 'Z': return RockPaperSicc.SICCOR;

		default: throw new IllegalArgumentException();
		}
	}
	
	public static Game fromCharPart2(char i, char j) {
		RockPaperSicc player1 = fromChar(i);
		switch(j) {
		case 'X':
			if (player1 == RockPaperSicc.ROCK)  return new Game(player1, RockPaperSicc.SICCOR);
			else if (player1 == RockPaperSicc.PAPER)  return new Game(player1, RockPaperSicc.ROCK);
			else return new Game(player1, RockPaperSicc.PAPER);
		case 'Y': return new Game(player1, player1);

		case 'Z': 
			if (player1 == RockPaperSicc.ROCK)  return new Game(player1, RockPaperSicc.PAPER);
	     	else if (player1 == RockPaperSicc.PAPER)  return new Game(player1, RockPaperSicc.SICCOR);
		    else return new Game(player1, RockPaperSicc.ROCK);
		default: throw new IllegalArgumentException();
		}
	}
	
	public static class Game {
		public RockPaperSicc player1;
		public RockPaperSicc player2;
		
		Game(RockPaperSicc f, RockPaperSicc s){
			player1 = f;
			player2 = s;
		}
	}
	
	//convention filename is in the same folder as the executable
	public static List<Game> ReadProblem (String filename){
        Path p = Paths.get(System.getProperty("user.dir"));
        p = p.resolve(filename);
        List<Game> res = new ArrayList<>();
        File f = p.toFile();
	    f.setReadOnly();
	    String line;
        try (Scanner scan = new Scanner (f)) {
		    while (scan.hasNextLine()) {
		      line = scan.nextLine();
		      res.add(fromCharPart2(line.charAt(0), line.charAt(2)));
	          }
        } catch (FileNotFoundException ex) {
        	System.out.println("Read conventions bro");
        }
      return res;
	}

	public static GameResult GamePlayer2Result(Game g) {
		if (g.player1 == g.player2) return GameResult.DRAW;
		switch (g.player2) {
		case ROCK:
			if (g.player1 == RockPaperSicc.SICCOR) return GameResult.WIN;
			break;
		case PAPER:
			if (g.player1 == RockPaperSicc.ROCK) return GameResult.WIN;
			break;
		case SICCOR:
			if (g.player1 == RockPaperSicc.PAPER) return GameResult.WIN;
			break;
		}
		return GameResult.LOSE;
	}
	
	public static long RockPaperSiccToPoints(RockPaperSicc c) {
		long r = 0;
		switch (c) {
		  case ROCK:   r = 1;
		    break;
		  case PAPER:  r = 2;
		    break;
		  case SICCOR: r = 3;
		    break;
		}
		return r;
	}
	
	//Computes the points associated to player 2
	//To compute the points for player 1 simply swap the arguments in parameter Game g
	public static long GameToPoints(Game g) {
		GameResult result = GamePlayer2Result(g);
		long r = RockPaperSiccToPoints(g.player2);
		switch (result) {
		  case WIN:  r += 6;
		    break;
		  case DRAW: r += 3;
		    break;
		default:
			break;
		}
		return r;
	}
	
	public static void main(String[] args) {
		List<Game> input = ReadProblem("InputFile.txt");
		System.out.println(GamePlayer2Result(new Game(RockPaperSicc.PAPER,RockPaperSicc.ROCK)));
		long r = input.stream().map(Main::GameToPoints).reduce((long)0, Long::sum);
		System.out.println(r);
	}

}
