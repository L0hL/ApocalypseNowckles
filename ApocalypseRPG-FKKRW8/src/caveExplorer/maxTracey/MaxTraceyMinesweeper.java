package caveExplorer.maxTracey;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import caveExplorer.*;

public class MaxTraceyMinesweeper implements Playable {

	public static boolean eventOccurred = false;
	
	protected static final String cheatCode = "beatMinesweeper";
	protected static final String loseCode = "loseMinesweeper";
	
	protected static boolean gameInProgress;
	
	
	static String[][] grid;
	
//	static Scanner in = new Scanner(System.in);
	static boolean[][] mines;
	static boolean[][] revealed;
	static boolean[][] marked;
	static int shields;
	
	public MaxTraceyMinesweeper(int numRows, int numCols, int numMines, int numShields) {
		mines = new boolean[numRows][numCols];
		revealed = new boolean[numRows][numCols];
		shields = numShields;
		
		//number of mines is given to the player
		//each time the player marks a mine, 
		//numOfMines minus 1 and
		
		//if the marked array is equal to the mines array then the player wins
		
		// marked = new boolean[numRows][numCols];
		
		plantMines(mines, numMines);
		marked = new boolean[mines.length][mines[0].length];
	}
	
	public void play() throws InterruptedException {
		this.eventOccurred = true;
//		Scanner inMS = new Scanner(System.in); 
		gameInProgress = true;
		if (CaveExplorer.useLaunchpadInput) {
			try {
				Launchpad.clearPads(Launchpad.launchpad, 15, 0);
			} catch (InvalidMidiDataException | MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (CaveExplorer.useLaunchpadInput) {
			new Thread() {
	            public void run() {
						try {
							Launchpad.fillPads(Launchpad.launchpad, 5, "solid", 0, 25);
							Launchpad.clearPads(Launchpad.launchpad, 0, 25);
						} catch (InterruptedException | InvalidMidiDataException | MidiUnavailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	Thread.yield();
	            	}
	            }.start();               
		}
//		readSequence(SEQUENCE_1, 20);
		
		while(gameInProgress){
			if (checkArraysEqual(mines, marked)) {
				gameInProgress = false;
				return;
			}
			
//			boolean[][] temp = new boolean[mines.length][mines[0].length];
//			for (int i = 0; i < mines.length; i++) {
//				for (int j = 0; j < mines[i].length; j++) {
//					temp[i][j] = true;
//				}
//			}
//			String[][] field = createField(mines, temp);
			
			String[][] field = createField(mines, revealed);
			printField(field);
			
			System.out.println("enter");
			String input = CaveExplorer.in.nextLine();
//			while (!isValidSpace(toGridSpace(input))) {
			while (!validateAndMarkInput(input, true)) {
				if (input.indexOf(cheatCode) < 0 && input.indexOf(loseCode) < 0) {
					System.out.println("Invalid input. Try again");
					input = CaveExplorer.in.nextLine();
				}
				else {
					System.out.println("Cheat code entered. Exiting.");
//					for (int i = 0; i < revealed.length; i++) {
//						for (int j = 0; j < field.length; j++) {
//							
//						}
//						revealSpace([, true);
//					}
					gameInProgress = false;
					if (input.indexOf(cheatCode) >= 0) {
						return;
					}
					else {
						loseGame();
//						input = "A1";
						break;
					}
				}
			}
			if (isValidSpace(toGridSpace(input))) {
				
				int[] enteredSpace = toGridSpace(input);
				int enteredR = toGridSpace(input)[0];
				int enteredC = toGridSpace(input)[1];
				System.out.println(enteredR + " " + enteredC);
				
				revealSpace(enteredSpace, new int[] {-1,-1}, false);
			
			}
			
			if (shields < 0) {
				loseGame();
				break;
			}
			
			System.out.println("You have " + shields + " shields remaining.");
		}
		
		for(int r=0; r<mines.length; r++){
			for(int c=0; c<mines[r].length; c++){
				if(mines[r][c]){
//					revealed[r][c]=true;
					revealSpace(new int[] {r, c}, new int[] {-1,-1}, true);
				}
			}
		}
		
		String[][] field = createField(mines, revealed);
		printField(field);
		
	}

	private static boolean validateAndMarkInput(String input, boolean markOnRun){
		input = input.toLowerCase();
		String input2 = new String();
		input2 = input;
		if(input.indexOf("mark") >= 0 && markOnRun){
			input2 = input.replace("mark", "").trim();
			System.out.println(input2);
			
//			if (isValidSpace(toGridSpace(input2))) {
//				int mR = toGridSpace(input2)[0];
//				int mC = toGridSpace(input2)[1];
//				marked[mR][mC] = !marked[mR][mC];
//				return true;
//			}
			
			if (isValidSpace(toGridSpace(input2))) {
				markSpace(toGridSpace(input2));
				return true;
			}
			else {
				return false;
			}
		}
		
		else {
			return isValidSpace(toGridSpace(input));
		}
			
		
//		return false;
	}
	
	private static void markSpace(int[] space) {
	
		if (isValidSpace(space)) {
			int mR = space[0];
			int mC = space[1];
			if (revealed[mR][mC]) {
				marked[mR][mC] = mines[mR][mC];
			}
			else {
				marked[mR][mC] = !marked[mR][mC];
				String toOut = "Space " + toLtr(mR) + "" + (mC + 1) + " ";
				if (!marked[mR][mC]) {
					toOut += "un";
				}
				toOut += "marked.";
				System.out.println(toOut);
			}
		}
		
	}
	
	private static char toLtr(int mC) {
		return (char) (65+mC);
	}

	private static boolean checkArraysEqual(boolean[][] arr1, boolean[][] arr2){
		
		for (int r = 0; r < arr1.length; r++) {
			for (int c = 0; c < arr1[r].length; c++) {
				if (arr1[r][c] != arr2[r][c]) {
					return false;
				}
			}
		}
		return true;
	}

	private static void loseGame() {
		gameInProgress = false;
		eventOccurred = false;
		CaveExplorer.msRoom.eventHappened = false;
//		MaxTraceyMinesweeper.eventOccurred = false;
	}

	private static boolean isValidSpace(int[] inArr) {
		int r = inArr[0];
		int c = inArr[1];
		
		if ((0 <= r) && (r < mines.length)) {
			if ((0 <= c) && (c < mines[0].length)) {
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
						field[row][col] = ""+countNearby(mines,row,col);
					}
				}
				else if (marked[row][col]) {
					field[row][col] = "&";
				}
				else {
					field[row][col] = "#";
				}
			}
		}
		
		
		return field;
	}

	private static int countNearby(boolean[][] mines, int row, int col) {
		/* THIS METHOD ALLOWS YOU TO BE MOST SPECIFIC
		 * FOR EXAMPLE, IF YOU ONLY WANT NORTH AND EAST
		 */		
		
		
		int count = 0;
		
//		horizontal & vertical adjacents
		count += isValidAndTrue(mines, row-1, col);
		count += isValidAndTrue(mines, row+1, col);
		count += isValidAndTrue(mines, row, col-1);
		count += isValidAndTrue(mines, row, col+1);
		
//		diagonal adjacents
		count += isValidAndTrue(mines, row-1, col-1);
		count += isValidAndTrue(mines, row-1, col+1);
		count += isValidAndTrue(mines, row+1, col-1);
		count += isValidAndTrue(mines, row+1, col+1);
		
		
		return count;
	}
	
	private static int isValidAndTrue(boolean[][] mines, int i, int j) {
		if (i >= 0 && i < mines.length && j >= 0 && j < mines[0].length) {
			if (mines[i][j]) {
				return 1;
			}
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
	
	protected void revealSpace(int[] space, int[] oldSpace, boolean safety) {
		int r = space[0];
		int c = space[1];
		if (revealed[r][c]) {
			return;
		}
		
		revealed[r][c] = true;
		if (mines[r][c] == true && !safety) {
			explodeMine(space);
			markSpace(space);
		}
		else {
			if (countNearby(mines, r, c) == 0) {
				int[][] newSpaces = new int[][] {
					{r-1, c-1},
					{r-1, c},
					{r-1, c+1},
					{r, c+1},
					{r+1, c+1},
					{r+1, c},
					{r+1, c-1},
					{r, c-1}
				};
				
				for (int i = 0; i < newSpaces.length; i++) {
					int[] newSpace = newSpaces[i];
					int nR = newSpace[0];
					int nC = newSpace[1];
					if (isValidSpace(newSpace)) {
						if (!revealed[nR][nC]) {
							revealSpace(newSpace, space, safety);
						}
					}
				}
				
			}
		}
	}
	
	protected void explodeMine(int[] mine){
		int mR = mine[0];
		int mC = mine[1];
		
		shields--;
//		if(shields>0) shields--;
//		else{
//			for(int r=0; r<mines.length; r++){
//				for(int c=0; c<mines[r].length; c++){
//					if(mines[c][r]){
//						revealed[c][r]=true;
//					}
//				}
//			}
//			System.out.println("You've lost.");
//		}
		
	}
	
	private int match2dArrs(boolean[][] inArr1, boolean[][] inArr2, boolean matchType) {
		int count = 0;
		if (inArr1.length == inArr2.length) {
			for (int i = 0; i < inArr1.length; i++) {
				for (int j = 0; j < inArr1.length; j++) {
					if ((inArr1[i][j] == inArr2[i][j]) == matchType) {
						count++;
					}
				}
			}
		}
		
		return count;
	}

}
