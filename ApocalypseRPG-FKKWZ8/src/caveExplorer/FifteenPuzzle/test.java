package FifteenPuzzle;

import java.util.Scanner;

public class test {
	public static Scanner in = new Scanner(System.in);
	static String[][] puzzle;
	static String[][] pic;
	static int starti;
	static int startj;
	static String k;

	
	
	public static void main(String[] args) {
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
			System.out.println("Enter a your direction.");
			String input = in.nextLine();
			
			int[] newCoordinates = interpretInput(input);
			starti = newCoordinates[0];
			startj = newCoordinates[1];
			
		}
	}

	
	private static int[] interpretInput(String input) {
		//verify input is valid
		while(!isValid(input)){
			System.out.println("Sorry, in this game, "
					+ "you can only use the "
					+ "w, a, s, d controls.");
			System.out.println("Tell me again what you "
					+ "would like to do.");
			input = in.nextLine();
		}
		int currenti = starti;
		int currentj = startj;
		input = input.toLowerCase();
		if(input.equals("w")) {
			currenti++;
			puzzle[starti][startj] = puzzle[starti+1][startj];
			puzzle[starti+1][startj]= "";
		}
		if(input.equals("s")) {
			currenti--;	
			
			puzzle[starti][startj] = puzzle[starti-1][startj];
			puzzle[starti-1][startj] = "";				

		}
		if(input.equals("a")) {
			currentj++;
			puzzle[starti][startj] = puzzle[starti][startj+1];
			puzzle[starti][startj+1] = "";
		}
		if(input.equals("d")) {
			currentj--;
			puzzle[starti][startj] = puzzle[starti][startj-1];
			puzzle[starti][startj-1] = "";

		}
		
		int[] newCoordinates = {starti,startj};
		if(currenti>=0 && currenti < puzzle.length &&
				currentj >=0 && currentj < puzzle[0].length){
			newCoordinates[0]=currenti;
			newCoordinates[1]=currentj;
		}else{
			System.out.println("Sorry, you've reach the edge "
					+ "of the known universe. You may go no "
					+ "farther in that direction.");
		}
		return newCoordinates;
	}
	
	private static void shiftTiles(String input) {
		
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
	
	
	public static void print(String string) {
		System.out.println(string);// taking this out
	}
	//http://stackoverflow.com/questions/12845208/how-to-print-two-dimensional-array-like-table
}
