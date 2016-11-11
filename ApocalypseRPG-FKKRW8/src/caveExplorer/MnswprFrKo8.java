package caveExplorer;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Iterator;

public class MnswprFrKo8 implements Playable {

	static String[][] arr2D;
	static int starti;
	static int startj;
	static int treasurei;
	static int treasurej;
	static String[][] grid;
	
	static Scanner in = new Scanner(System.in);
	
//	
	public void play() throws InterruptedException {
		// TODO Auto-generated method stub
		boolean[][] mines = new boolean[12][12];
		plantMines(mines, 30);
//		
		String[][] field = createField(mines);
		
		printField(field);
		
//		startGame();
	}



	private static int[] interpretInput(String input) {
//		verify input is valid
		while (!isValid(input)) {
			System.out.println("Sorry! In this game, you can only use the WASD and launchpad controls.");
			System.out.println("Tell me again what you would like to do.");
			input = in.nextLine();
		}
		
		int currenti = starti;
		int currentj = startj;
		
		input = input.toLowerCase();
		
		if(input.equals("w")){
			currenti--;
		}
		else if (input.equals("s")) {
			currenti++;
		}
		else if (input.equals("a")) {
			currentj--;
		}
		else if (input.equals("d")) {
			currentj++;
		}
		
		int[] newCoordinates = {starti, startj};
		if (currenti >= 0 && currenti < arr2D.length && currentj >= 0 && currentj < arr2D[0].length) {
			newCoordinates[0] = currenti;
			newCoordinates[1] = currentj;
		}
		else {
			System.out.println("Sorry, you've reached the edge of the defined universe.");
			System.out.println("Mr. Nockles did not offer extra points for coding areas outside the defined universe.");
			System.out.println("Therefore, you may go no farther in this direction.");
			System.out.println("Address all complaints to Mr. Nockles.");
		}
			return newCoordinates;
	}

	private static boolean isValid(String input) {
		String[] validKeys = {"w", "a", "s", "d"};
		for (String key : validKeys) {
			if (input.toLowerCase().equals(key)) {
				return true;
			}
		}
		
		return false;
	}
	
	private static void plantMines(boolean[][] mines, int numMines) {
		while (numMines > 0) {
			int row = (int)(Math.random() * mines.length);
			int col = (int)(Math.random() * mines[0].length);
			
//			prevents selection of existing mine
			if (!mines[row][col]) {
				mines[row][col] = true;
				numMines--;
			}
		}
	}
	
	private static String[][] createField(boolean[][] mines) {
		String[][] field = 
				new String[mines.length][mines[0].length];
		for(int row = 0; row < field.length; row++){
			for(int col = 0; col < field[row].length; col ++){
				if(mines[row][col])field[row][col]="X";
				else{
					field[row][col] = countNearby(mines,row,col);
				}
			}
		}
		
		
		return field;
	}

	private static String countNearby(boolean[][] mines, int row, int col) {
//		for(int r = row - 1; r <= row +1; r ++){
//			for(int c = col -1; c <= col+1; c++){
//				//check that this element exists
//				if(r >=0 && r < mines.length &&
//						c >=0 && c < mines[0].length){
//					
//				}
//			}
//		}
		
////		THIS METHOD ONLY CONSIDERS ACTUAL ELEMENTS
//		int count = 0;
//		for (int r = 0; r < mines.length; r++) {
//			for (int c = 0; c < mines[r].length; c++) {
//				if (Math.abs(r-row) + Math.abs(c-col) == 1 && mines[r][c]) {
//					count++;
//				}
//			}
//		}
//		return "" + count;
		
		
//		THIS METHOD ALLOWS YOU TO BE MOST SPECIFIC
//		FOR EXAMPLE, IF YOU ONLY WANT NORTH AND EAST
		int count = 0;
		count += isValidAndTrue(mines, row-1, col);
		count += isValidAndTrue(mines, row+1, col);
		count += isValidAndTrue(mines, row, col-1);
		count += isValidAndTrue(mines, row, col+1);
		
		
		
		return ""+count;
	}
	
	private static int isValidAndTrue(boolean[][] mines, int i, int j) {
		if (i >= 0 && i < mines.length && j >= 0 && j < mines[0].length && mines[i][j]) {
			return 1;
		}
		return 0;
	}
	
	public static void printField(String[][] pic) {
		System.out.print("    ");
		for (int i = 0; i < pic[0].length; i++) {
			System.out.print(i+1 + " ");
			if (i<9) {
				System.out.print(" ");
			}
		}
		System.out.print("\n   ");
		for (int i = 0; i < pic[0].length; i++) {
			System.out.print("---");
		}
		System.out.print("\n");
		for (int i = 0; i < pic.length; i++) {
//		for (String[] row : pic) {
			System.out.print(((char)(65 + i) + " | ").toString());
			for (String col : pic[i]){
				System.out.print(col + "  ");
			}
			System.out.print("\n");
		}
	}
	
	

}