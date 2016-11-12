package caveExplorer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class MaxTraceyMinesweeper implements Playable {

	protected static final String cheatCode = "beatMinesweeper";
	
	protected static boolean gameInProgress;
	
	
	static String[][] grid;
	
//	static Scanner in = new Scanner(System.in);
	static boolean[][] mines;
	static boolean[][] revealed;
	static int shields;
	
	public MaxTraceyMinesweeper() {
		mines = new boolean[12][12];
		revealed = new boolean[12][12];
		shields = 3;
		
		plantMines(mines, 30);
	}
	
	public void play() throws InterruptedException {
		gameInProgress = true;
		if (CaveExplorer.useLaunchpadInput) {
			try {
				Launchpad.clearPads(Launchpad.launchpad, 15, 0);
			} catch (InvalidMidiDataException | MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		boolean[][] mines = new boolean[12][12];
//		mines = new boolean[12][12];
		
		
		while(gameInProgress){
			String[][] field = createField(mines, revealed);
			printField(field);
			
			String input = CaveExplorer.in.nextLine();
			while (!isValidSpace(toGridSpace(input))) {
				input = CaveExplorer.in.nextLine();
			}
			
			
			System.out.println("You have " + shields + " remaining.");
			System.out.println(toGridSpace(input)[0] + " " + toGridSpace(input)[1]);
		}
		
		
	}



	

	private static boolean isValidSpace(int[] inArr) {
		int r = inArr[0];
		int c = inArr[1];
		
		if (!((0 <= r) && (r < mines.length))) {
			if (!((0 <= c) && (c < mines[r].length))) {
				return true;
			}
		}
		return false;
	}
	
	private static int[] toGridSpace(String input) {
		char rowLtr = 0;
		input = input.toUpperCase();
		int count = 0;
		for (int i = 0; i < input.length(); i++) {
			if (((int)(input.charAt(i)) >= 65) && ((int)(input.charAt(i)) <= 90)) {
				rowLtr = input.charAt(i);
				break;
			}
		}
		
//		int numStart;
		int numStartIdx = -1;
		
		for (int i = 0; i < input.length(); i++) {
			if (((int)(input.charAt(i)) >= 49) && ((int)(input.charAt(i)) <= 57)) {
//				numStart = (int)(input.charAt(i));
				numStartIdx = i;
				break;
			}
		}
//		int numEnd;
		int numEndIdx = numStartIdx;
		for (int i = numStartIdx + 1; i < input.length(); i++) {
			if (!(((int)(input.charAt(i)) >= 48) && ((int)(input.charAt(i)) <= 57))) {
				break;
			}
			else {
				numEndIdx++;
			}
		}
		

		int rowNum = 0;
		if (rowLtr != 0) {
			rowNum = (int)(rowLtr - 65);
		}
		else {
			rowNum = -1;
		}
		
		int colNum = 0;
		if (numStartIdx != -1) {
			String colNumStr = input.substring(numStartIdx, numEndIdx + 1);
			colNum = Integer.parseInt(colNumStr);
		}
		else {
			colNum = -1;
		}
		
		return new int[] {rowNum, colNum - 1};
		
//		return ((rowLtr != 0) && (numStartIdx != -1));
//		return true;
		
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
	
	private static String[][] createField(boolean[][] mines, boolean[][] revealed) {
		String[][] field = 
				new String[mines.length][mines[0].length];
		for (int row = 0; row < field.length; row++) {
			for (int col = 0; col < field[row].length; col ++) {
				if (revealed[row][col]) {
					if (mines[row][col]) {
						field[row][col] = "X";
					}
					else {						
						field[row][col] = countNearby(mines,row,col);
					}
				}
				else {
					field[row][col] = "#";
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
	
	protected void revealSpace(int[] space) {
		revealed[space[0]][space[1]] = true;
	}

}
