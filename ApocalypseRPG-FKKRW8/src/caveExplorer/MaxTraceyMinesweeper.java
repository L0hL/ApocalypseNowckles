package caveExplorer;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Iterator;

public class MaxTraceyMinesweeper implements Playable {

	protected static final String cheatCode = "beatMinesweeper";
	
	protected static boolean gameInProgress;
	
	static String[][] arr2D;
	static int starti;
	static int startj;
	static int treasurei;
	static int treasurej;
	static String[][] grid;
	
	static Scanner in = new Scanner(System.in);
	
	
//	
	public void play() throws InterruptedException {
		gameInProgress = true;
		boolean[][] mines = new boolean[12][12];
		plantMines(mines, 30);
		while(gameInProgress){
			String[][] field = createField(mines);
			printField(field);
			String input = CaveExplorer.in.nextLine();
		}
		// TODO Auto-generated method stub
//		
		
		
//		startGame();
	}



	private static void interpretInput(String input) {
		if (input.indexOf(cheatCode) >= 0) {
			gameInProgress = false;
			return;
		}
		else{
//			verify input is valid
			while (!isValid(input)) {
				System.out.println("Sorry! In this game, you can only use the WASD and launchpad controls.");
				System.out.println("Tell me again what you would like to do.");
				input = in.nextLine();
			}
		}
	}

	private static boolean isValid(String input) {
				
		return false;
	}
	
	private static boolean isGridSpace(String input) {
		input = input.toUpperCase();
		int count = 0;
		for (int i = 0; i < input.length(); i++) {
			if (((int)(input.charAt(i)) < 65) || ((int)(input.charAt(i)) < 65)) {
				
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
