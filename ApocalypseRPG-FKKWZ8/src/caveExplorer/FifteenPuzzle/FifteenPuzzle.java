package FifteenPuzzle;

import java.util.Scanner;
//puzzle[0][0] = 1;
//puzzle[0][1]= 2;
//puzzle[0][2] = 3;
//puzzle[0][3] = 4;
//puzzle[1][0]= 5;
//puzzle[1][1] = 6;
//puzzle[1][2] = 7;
//puzzle[1][3] = 8;
//puzzle[2][0]= 9;
//puzzle[2][1] = 10;
//puzzle[2][2] = 11;
//puzzle[2][3] = 12;
//puzzle[3][0] = 13;
//puzzle[3][1]= 14;
//puzzle[3][2]= 15;
//puzzle[3][3] = 0;
public class FifteenPuzzle {
	public static Scanner in = new Scanner(System.in);
	static String[][] puzzle;
	static String[][] pic;
	static int starti;
	static int startj;


	
	
	public static void startGame() {
		int x = 1;
		puzzle = new String[4][4];

		for (int i = 0; i < puzzle.length; i++) {
			for (int j = 0; j < puzzle[i].length; j++) {
				puzzle[i][j] = ""+x;
				x++;
			}
		}
		puzzle[3][3] = "";
		
		starti = 3;
		startj = 3;
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
			currenti--;
		}
		if(input.equals("s"))currenti++;
		if(input.equals("a"))currentj--;
		if(input.equals("d"))currentj++;
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
		System.out.println(string);
	}
	//http://stackoverflow.com/questions/12845208/how-to-print-two-dimensional-array-like-table
}
