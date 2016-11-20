package caveExplorer.FifteenPuzzle;

import java.util.Scanner;

import caveExplorer.CaveExplorer;

public class FifteenPuzzle {
	public static Scanner in = new Scanner(System.in);
	static String[][] puzzle;
	static String[][] pic;
	static int starti;
	static int startj;
	static int currenti;
	static int currentj;

	
	public static void startGame() {
		int x = 1;
		puzzle = new String[4][4];

		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				puzzle[i][j] = ""+x;
				x++;
			}
		}
		starti = 3;
		startj = 3;
		puzzle[starti][startj] = "";
		
		playGame();
		
	}
	
	
	private static void playGame() {
		while (true) {
			printPuzzle(puzzle);
			CaveExplorer.print("Enter a direction to slide the tiles.");
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
			CaveExplorer.print("Please enter a valid direction to slide: w = up, s = down, a  = left, d = right.");
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