package caveExplorer.simon;

//import java.util.Arrays;
//import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import caveExplorer.CaveExplorer;
import caveExplorer.Playable;

public class SimonRoom implements Playable {
	static String[] keys = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t" };
	static String[] keysH = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r",
			"s", "t" };
	static int[] special = { 8, 9, 10 };
	static int points;
	static int[] flippedCards = new int[20];
	public static boolean[] powerUps = { false, false, false };
	// private static String cardArray[][];
	// private static String[][] pic;
	// private static boolean isPlaying;
	private static String grid[][];
	static int[] cards;
	// private static String[] alphaId=new String[20];
	private int lives;

	@Override
	public void play() throws InterruptedException, InvalidMidiDataException, MidiUnavailableException {
		// TODO Auto-generated method stub

		makeGame();

	}

	public void makeGame() throws InterruptedException, InvalidMidiDataException, MidiUnavailableException {
		System.out.println("to win you must get 16 points by finding pairs of matching cards.\n"
				+ " you have 25 tries before you have to restart the game.\n each card can be flipped by typing the corresponding letter bottom of the card and pressing enter.");
		System.out.println("---press enter to start---");
		CaveExplorer.in.nextLine();
		lives = 25;
		points = 0;
		for (int i = 0; i < keys.length; i++) {
			keys[i] = keysH[i];
		}
		for (int i = 0; i < flippedCards.length; i++) {
			flippedCards[i] = -2;
		}
		for (int i = 0; i < powerUps.length; i++) {
			powerUps[i] = false;
		}
		makeCards();
		grid = newGrid(2, 10);
		printPic(grid);
		while (points < 16) {
			// if(points>=16){
			// break;
			// }
			if (lives <= 0) {
				System.out.println("You ran out of tries!");
				System.out.println("---press enter to restart---");
				CaveExplorer.in.nextLine();

			}
			interpretAction();
		}
	}

	private void makeCards() {
		cards = new int[20];
		int cCount[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int key[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		for (int i = 0; i < cards.length; i++) {

			int tempInt = -1;
			boolean inLoop = true;
			while (inLoop == true) {
				// printArrayLinear(outArray);
				tempInt = (int) ((10) * Math.random()) + 1;
				if (searchUnsorted(key, tempInt) >= 0 && cCount[(tempInt - 1)] < 2) {
					cards[i] = key[searchUnsorted(key, tempInt)];
					// key[searchUnsorted(key, tempInt)] = -5;
					cCount[(tempInt - 1)]++;
					// System.out.println(""+cards[i]);
					inLoop = false;

				}
			}
		}

	}

	private static String[][] newGrid(int i, int j) {
		// String
		// alphaId[]={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t"};
		int height = 6 * i + 2;
		int width = 8 * j + 2;

		String[][] arr = new String[height][width];

		for (int k = 0; k < arr.length; k++) {
			for (int k2 = 0; k2 < arr[k].length; k2++) {
				arr[k][k2] = " ";
			}
		}

		for (int k = 0; k < height - 1; k++) {
			arr[k][0] = "|";
		}

		for (int k = 0; k < width - 1; k++) {
			arr[0][k] = "_";
		}

		for (int a = 0; a < i; a++) {
			int topLeftY = (6 * a);
			for (int b = 0; b < j; b++) {
				int topLeftX = (8 * b + 1);

				arr[topLeftY + 6][topLeftX] = "_";
				arr[topLeftY + 6][topLeftX + 1] = "_";
				arr[topLeftY + 6][topLeftX + 2] = "_";
				arr[topLeftY + 6][topLeftX + 3] = "" + keysH[((a) * 10) + b];
				arr[topLeftY + 6][topLeftX + 4] = "_";
				arr[topLeftY + 6][topLeftX + 5] = "_";
				arr[topLeftY + 6][topLeftX + 6] = "_";
				arr[topLeftY + 6][topLeftX + 7] = "_";
				arr[height - 2][0] = "|";

				arr[topLeftY + 1][topLeftX + 7] = "|";
				arr[topLeftY + 2][topLeftX + 7] = "|";
				arr[topLeftY + 3][topLeftX + 7] = "|";
				arr[topLeftY + 4][topLeftX + 7] = "|";
				arr[topLeftY + 5][topLeftX + 7] = "|";
				arr[topLeftY + 6][topLeftX + 7] = "|";

			}
		}

		return arr;
	}

	public static int searchUnsorted(int[] arrayToSearch, int term) {

		for (int i = 0; i < (arrayToSearch.length); i++) {
			if (arrayToSearch[i] == term) {
				return i;
			}
		}
		return -1;
	}

	private static boolean isValid(String input) {
		int tempAlpha = -1;
		for (int i = 0; i < keys.length; i++) {
			if (input.toLowerCase().equals("skip")) {
				points = 100;
				break;
			}
			if (input.toLowerCase().equals("power")) {
				powerUps[0] = true;
				powerUps[1] = true;
				powerUps[2] = true;
				break;
			}
			if (input.toLowerCase().equals(keys[i])) {
				tempAlpha = i;

				if (tempAlpha != flippedCards[i]) {
					return true;
				} else if (i == 0 && flippedCards[i] == -2 && tempAlpha == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void interpretAction() {
		String inpt = "";
		String input = "";
		inpt = CaveExplorer.in.nextLine();
		while (!isValid(inpt.toLowerCase())) {
			CaveExplorer.print("Please pick a card");
			inpt = CaveExplorer.in.nextLine();
		}
		input = inpt;

		int card1 = searchUnsortedStrings(keys, input);
		// if (card1 >= 0 && card1<=19){
		int card1H = card1;
		flippedCards[card1] = card1;
		if (isSpecial(cards[card1])) {
			flipCardSpecial(card1);
		}

		else {
			flipCard(card1);
		}
		printPic(grid);
		// keys[card1]="";

		input = CaveExplorer.in.nextLine();
		while (!isValid(input.toLowerCase())) {
			CaveExplorer.print("Please pick another card");
			input = CaveExplorer.in.nextLine();
		}
		int card2 = searchUnsortedStrings(keys, input);
		int card2H = card2;
		flippedCards[card2] = card2;
		if (isSpecial(cards[card2])) {
			flipCardSpecial(card2);
		}

		else {
			flipCard(card2);
		}

		printPic(grid);
		// keys[card2]="";
		if (cards[card1] == cards[card2]) {
			points += 2;
			if (isSpecial(cards[card1]) && isSpecial(cards[card2])) {
				CaveExplorer.print("you found a power up bonus!");
				powerUps[searchUnsorted(special, (cards[card1]))] = true;
			}
			CaveExplorer.print("Congrats! you now have " + points + " points");

		}
		// }
		else {
			lives--;
			System.out.println("They didn't match, you have " + lives + " tries left");
			System.out.println("---press enter to continue---");
			CaveExplorer.in.nextLine();
			keys[card1] = keysH[card1H];
			keys[card2] = keysH[card2H];
			flippedCards[card1] = -2;
			flippedCards[card2] = -2;
			if (isSpecial(cards[card1])) {
				flipCardBackSpecial(card1);
			}

			else {
				flipCardBack(card1);
			}
			if (isSpecial(cards[card2])) {
				flipCardBackSpecial(card2);
			}

			else {
				flipCardBack(card2);
			}
			printPic(grid);

		}

	}

	private int searchUnsortedStrings(String[] arrayToSearch, String ref) {

		for (int i = 0; i < arrayToSearch.length; i++) {
			if (arrayToSearch[i].equals(ref.toLowerCase())) {
				return i;

			}
		}
		return -1;
	}

	private void flipCard(int i) {

		int rPrint = ((i / 10) * 6) + 3;
		int cPrint = ((i % 10) * 8) + 5;
		// grid[((i/10)*6)+ 4][((i%10)*8)+6]=""+cards[i];
		grid[((i / 10) * 6) + 6][((i % 10) * 8) + 4] = "_";
		if (cards[i] > 9) {
			cPrint--;
			int digit1 = cards[i] / 10;
			int digit2 = cards[i] % 10;
			grid[rPrint][cPrint] = "" + digit1;
			grid[rPrint][cPrint + 1] = "" + digit2;
		} else {
			grid[rPrint][cPrint - 1] = "" + cards[i];
		}

	}

	private void flipCardSpecial(int i) {

		int rPrint = ((i / 10) * 6);
		int cPrint = ((i % 10) * 8);
		grid[((i / 10) * 6) + 6][((i % 10) * 8) + 4] = "_";
		if (cards[i] == 10) {
			grid[rPrint + 1][cPrint + 2] = "_";
			grid[rPrint + 1][cPrint + 3] = "_";
			grid[rPrint + 1][cPrint + 4] = "_";
			grid[rPrint + 1][cPrint + 5] = "_";

			grid[rPrint + 2][cPrint + 1] = "/";
			grid[rPrint + 2][cPrint + 2] = "_";
			grid[rPrint + 2][cPrint + 3] = "_";
			grid[rPrint + 2][cPrint + 6] = "\\";

			grid[rPrint + 3][cPrint + 4] = "\\";
			grid[rPrint + 3][cPrint + 7] = "|";

			grid[rPrint + 4][cPrint + 2] = "_";
			grid[rPrint + 4][cPrint + 3] = "_";
			grid[rPrint + 4][cPrint + 4] = "/";
			grid[rPrint + 4][cPrint + 7] = "/";

			grid[rPrint + 5][cPrint + 1] = "\\";
			grid[rPrint + 5][cPrint + 2] = "_";
			grid[rPrint + 5][cPrint + 3] = "_";
			grid[rPrint + 5][cPrint + 4] = "_";
			grid[rPrint + 5][cPrint + 5] = "_";
			grid[rPrint + 5][cPrint + 6] = "/";

		}
		if (cards[i] == 9) {
			grid[rPrint + 1][cPrint + 2] = "<";
			grid[rPrint + 1][cPrint + 3] = "(";
			grid[rPrint + 1][cPrint + 4] = "o";
			grid[rPrint + 1][cPrint + 5] = ")";
			grid[rPrint + 1][cPrint + 6] = ">";

			grid[rPrint + 2][cPrint + 3] = "/";
			grid[rPrint + 2][cPrint + 5] = "\\";

			grid[rPrint + 3][cPrint + 2] = "/";
			grid[rPrint + 3][cPrint + 6] = "\\";

			grid[rPrint + 4][cPrint + 1] = "/";
			grid[rPrint + 4][cPrint + 2] = "_";
			grid[rPrint + 4][cPrint + 3] = "_";
			grid[rPrint + 4][cPrint + 4] = "_";
			grid[rPrint + 4][cPrint + 5] = "_";
			grid[rPrint + 4][cPrint + 6] = "_";
			grid[rPrint + 4][cPrint + 7] = "\\";
		}

		if (cards[i] == 8) {

			grid[rPrint + 1][cPrint + 2] = "_";
			grid[rPrint + 1][cPrint + 3] = "_";
			grid[rPrint + 1][cPrint + 4] = "_";
			grid[rPrint + 1][cPrint + 5] = "_";
			grid[rPrint + 1][cPrint + 6] = "_";

			grid[rPrint + 2][cPrint + 1] = "|";
			grid[rPrint + 2][cPrint + 2] = "\\";
			grid[rPrint + 2][cPrint + 3] = "_";
			grid[rPrint + 2][cPrint + 4] = "_";
			grid[rPrint + 2][cPrint + 5] = "_";
			grid[rPrint + 2][cPrint + 6] = "_";
			grid[rPrint + 2][cPrint + 7] = "\\";

			grid[rPrint + 3][cPrint + 1] = "|";
			grid[rPrint + 3][cPrint + 2] = "|";
			grid[rPrint + 3][cPrint + 7] = "|";

			grid[rPrint + 4][cPrint + 1] = "\\";
			grid[rPrint + 4][cPrint + 2] = "|";
			grid[rPrint + 4][cPrint + 3] = "_";
			grid[rPrint + 4][cPrint + 4] = "_";
			grid[rPrint + 4][cPrint + 5] = "_";
			grid[rPrint + 4][cPrint + 6] = "_";
			grid[rPrint + 4][cPrint + 7] = "|";
		}
	}

	private void flipCardBack(int i) {
		int rPrint = ((i / 10) * 6) + 3;
		int cPrint = ((i % 10) * 8) + 5;
		grid[((i / 10) * 6) + 6][((i % 10) * 8) + 4] = "" + keys[i];
		// grid[((i/10)*6)+ 4][((i%10)*8)+6]=" ";

		grid[rPrint][cPrint - 1] = " ";
		grid[rPrint][cPrint] = " ";
		grid[rPrint][cPrint + 1] = " ";

	}

	private void flipCardBackSpecial(int i) {
		int rPrint = ((i / 10) * 6);
		int cPrint = ((i % 10) * 8);
		grid[((i / 10) * 6) + 6][((i % 10) * 8) + 4] = "" + keys[i];
		// grid[((i/10)*6)+ 4][((i%10)*8)+6]=" ";
		if (cards[i] == 10) {
			grid[rPrint + 1][cPrint + 2] = " ";
			grid[rPrint + 1][cPrint + 3] = " ";
			grid[rPrint + 1][cPrint + 4] = " ";
			grid[rPrint + 1][cPrint + 5] = " ";

			grid[rPrint + 2][cPrint + 1] = " ";
			grid[rPrint + 2][cPrint + 2] = " ";
			grid[rPrint + 2][cPrint + 3] = " ";
			grid[rPrint + 2][cPrint + 6] = " ";

			grid[rPrint + 3][cPrint + 4] = " ";
			grid[rPrint + 3][cPrint + 7] = " ";

			grid[rPrint + 4][cPrint + 2] = " ";
			grid[rPrint + 4][cPrint + 3] = " ";
			grid[rPrint + 4][cPrint + 4] = " ";
			grid[rPrint + 4][cPrint + 7] = " ";

			grid[rPrint + 5][cPrint + 1] = " ";
			grid[rPrint + 5][cPrint + 2] = " ";
			grid[rPrint + 5][cPrint + 3] = " ";
			grid[rPrint + 5][cPrint + 4] = " ";
			grid[rPrint + 5][cPrint + 5] = " ";
			grid[rPrint + 5][cPrint + 6] = " ";

		}
		if (cards[i] == 9) {
			grid[rPrint + 1][cPrint + 2] = " ";
			grid[rPrint + 1][cPrint + 3] = " ";
			grid[rPrint + 1][cPrint + 4] = " ";
			grid[rPrint + 1][cPrint + 5] = " ";
			grid[rPrint + 1][cPrint + 6] = " ";

			grid[rPrint + 2][cPrint + 3] = " ";
			grid[rPrint + 2][cPrint + 5] = " ";

			grid[rPrint + 3][cPrint + 2] = " ";
			grid[rPrint + 3][cPrint + 6] = " ";

			grid[rPrint + 4][cPrint + 1] = " ";
			grid[rPrint + 4][cPrint + 2] = " ";
			grid[rPrint + 4][cPrint + 3] = " ";
			grid[rPrint + 4][cPrint + 4] = " ";
			grid[rPrint + 4][cPrint + 5] = " ";
			grid[rPrint + 4][cPrint + 6] = " ";
			grid[rPrint + 4][cPrint + 7] = " ";
		}

		if (cards[i] == 8) {

			grid[rPrint + 1][cPrint + 2] = " ";
			grid[rPrint + 1][cPrint + 3] = " ";
			grid[rPrint + 1][cPrint + 4] = " ";
			grid[rPrint + 1][cPrint + 5] = " ";
			grid[rPrint + 1][cPrint + 6] = " ";

			grid[rPrint + 2][cPrint + 1] = " ";
			grid[rPrint + 2][cPrint + 2] = " ";
			grid[rPrint + 2][cPrint + 3] = " ";
			grid[rPrint + 2][cPrint + 4] = " ";
			grid[rPrint + 2][cPrint + 5] = " ";
			grid[rPrint + 2][cPrint + 6] = " ";
			grid[rPrint + 2][cPrint + 7] = " ";

			grid[rPrint + 3][cPrint + 1] = " ";
			grid[rPrint + 3][cPrint + 2] = " ";
			grid[rPrint + 3][cPrint + 7] = " ";

			grid[rPrint + 4][cPrint + 1] = " ";
			grid[rPrint + 4][cPrint + 2] = " ";
			grid[rPrint + 4][cPrint + 3] = " ";
			grid[rPrint + 4][cPrint + 4] = " ";
			grid[rPrint + 4][cPrint + 5] = " ";
			grid[rPrint + 4][cPrint + 6] = " ";
			grid[rPrint + 4][cPrint + 7] = " ";
		}
	}

	public static void printPic(String[][] pic) {
		for (String[] row : pic) {
			for (String col : row) {
				System.out.print(col);
			}
			System.out.print("\n");
		}
	}

	boolean isSpecial(int value) {
		for (int i = 0; i < special.length; i++) {
			if (value == special[i]) {
				return true;
			}

		}
		return false;
	}

}
