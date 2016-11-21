package caveExplorer.FifteenPuzzle;

import java.util.Scanner;

public class test {
	public static Scanner in = new Scanner(System.in);
	static String[][] puzzle;
	static String[][] puzzleWin;
	static int starti;
	static int startj;
	static int currenti;
	static int currentj;
	static int x = 1;
	
	public static void main(String[] args) {
	
		puzzle = new String[4][4];

		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				puzzle[i][j] = ""+x;
				x++;
			}
		}
	
		randomize.solution(puzzle);
		createNull(puzzle);
		playGame();
	}
	
	private static void createNull(String[][] puzzle) {
		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				if (puzzle[i][j].equals ("16")) {
					puzzle[i][j] = "";
					starti = i;
					startj = j;
				}
				
			}
		}
	}
	
	private static void playGame() {
		while (true) {
			winGame(puzzle);
			printPuzzle(puzzle);
			System.out.println("Enter a direction to slide the tiles.");
			String input = in.nextLine();
			
			int[] newCoordinates = interpretInput(input);
			starti = newCoordinates[0];
			startj = newCoordinates[1];
			puzzle[starti][startj] = "";
		}
	}
	private static int[] interpretInput(String input) {
		//verify input is valid
		
		while(!isValid(input)){
			System.out.println("Please enter a valid direction to slide: w = up, s = down, a  = left, d = right.");
			input = in.nextLine();
		}
		currenti = starti;
		currentj = startj;
		input = input.toLowerCase();
		checkSpace(input);
		
		int[] newCoordinates = {starti,startj};
		
		if(currenti>=0 && currenti < puzzle.length &&
				currentj >=0 && currentj < puzzle[0].length){
			newCoordinates[0]=currenti;
			newCoordinates[1]=currentj;
		}else{
			System.out.println("Sorry, you cannot slide tiles in this direction anymore.");
		}
		return newCoordinates;
	}
	


	private static void checkSpace(String input) {
		if(input.equals("w")) {
			currenti++;
			if (currenti < 4) {
				puzzle[starti][startj] = puzzle[starti+1][startj];
			}
		}
		if(input.equals("s")) {
			currenti--;	
			if (currenti > -1) {
				puzzle[starti][startj] = puzzle[starti-1][startj];
			}	
		}
		if(input.equals("a")) {
			currentj++;
			if (currentj < 4) {
				puzzle[starti][startj] = puzzle[starti][startj+1];
			}	
		}
		if(input.equals("d")) {
			currentj--;
			if (currentj > -1) {
				puzzle[starti][startj] = puzzle[starti][startj-1];
			}	
		}
	}
	
	private static void winGame(String[][] puzzle) {
		
		if (puzzle[0][0].equals("1") && puzzle[0][1].equals("2") && puzzle[0][2].equals("3") && puzzle[0][3].equals("4")
				 && puzzle[1][0].equals("5") && puzzle[1][1].equals("6") && puzzle[1][2].equals("7")
				 && puzzle[1][3].equals("8") && puzzle[2][0].equals("9") && puzzle[2][1].equals("10")
				 && puzzle[2][2].equals("11") && puzzle[2][3].equals("12") && puzzle[3][0].equals("13")
				 && puzzle[3][1].equals("14") && puzzle[3][2].equals("15")) {
			System.out.println("You have completed the game! You may escape.");
			System.out.println("The door opens and you leave the room.");
		}
	}
	//
	private static boolean isValid(String in) {
		String[] validKeys = {"w","a","s","d"};
		for(String key: validKeys){
			if(in.toLowerCase().equals(key)){
				return true;
			}
		}
		return false;
	}
	
	
	
	private static void printPuzzle(String[][] puzzle) {
		for(String[] row : puzzle) {
			for (String i : row) {
	            System.out.print(i);
	            System.out.print("\t");
	        }
	        System.out.println();

        }
	}
}
