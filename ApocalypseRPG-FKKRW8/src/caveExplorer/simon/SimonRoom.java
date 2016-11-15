package caveExplorer.simon;

import java.util.Arrays;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import caveExplorer.CaveExplorer;
import caveExplorer.Playable;

public class SimonRoom implements Playable {
	String[] keys = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t"};
	public int points = 0;
	public static int[] flippedCards=new int[20];
	static String cardArray[][];
	static String[][] pic;
	static boolean isPlaying;
	public static String grid[][];
	static int[] cards;
	public static String[] alphaId=new String[20];
	@Override
	public void play() throws InterruptedException, InvalidMidiDataException, MidiUnavailableException {
		// TODO Auto-generated method stub
		for(int i = 0;i < flippedCards.length;i++){
			flippedCards[i]=-2;
		}
		makeGame();

	}

	public void makeGame() {
		
		makeCards();
		grid=newGrid(2, 10);
		printPic(grid);
		while(points<10){
			interpretAction("x");
		}
	}

	private void makeCards() {
		cards = new int[20];
		int key[] = { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10 };

		for (int i = 0; i < cards.length; i++) {

			int tempInt = -1;
			boolean inLoop = true;
			while (inLoop == true) {
				// printArrayLinear(outArray);
				tempInt = (int) ((10) * Math.random()) + 1;
				if (searchUnsorted(key, tempInt) >= 0) {
					cards[i] = key[tempInt];
					key[tempInt] = -2;
					inLoop = false;
				}
			}
		}

	}

	private static String[][] newGrid(int i, int j) {
		String alphaId[]={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t"};
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
				arr[topLeftY + 6][topLeftX + 3] = ""+alphaId[((a)*10)+b];
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

	public static int searchUnsorted(int[] arrayToSearch, int key) {

		for (int i = 0; i < (arrayToSearch.length - 1); i++) {
			if (arrayToSearch[i] == key) {
				return i;
			}
		}
		return -1;
	}
	private static boolean isValid(String input) {
		int tempAlpha = 0;
		String[] keys = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t"};
		for (int i=0;i<keys.length;i++) {
			if (input.toLowerCase().equals(keys[i])){
				tempAlpha = i;
				break;
				}
			else tempAlpha=-1;
		}
		for (int i=0;i<keys.length;i++) {
			if ( tempAlpha!=flippedCards[i]&&tempAlpha!=-1) {
				return true;
			}
			
		}
		
		return false;
	}
	public void interpretAction(String input) {
		
		while (!isValid(input.toLowerCase())) {
			CaveExplorer.print("Please pick a card");
			input = CaveExplorer.in.nextLine();
		}
		
		
		int card1=searchUnsortedStrings(keys,input);
		String card1H=keys[card1];
		flippedCards[card1]=card1;
		flipCard(card1);
		keys[card1]="z";
		while (!isValid(input.toLowerCase())) {
			CaveExplorer.print("Please pick another card");
			input = CaveExplorer.in.nextLine();
		}
		int card2=searchUnsortedStrings(keys,input);
		String card2H=keys[card2];
		flippedCards[card2]=card2;
		flipCard(card2);
		keys[card1]="z";
	if(cards[card1]==cards[card2]){
	points+=2;
	CaveExplorer.print("Congrats! you now have "+points+" points");
	}
	else{
		keys[card1]=card1H;
		keys[card2]=card2H;
		flippedCards[card1]=0;
		flippedCards[card2]=0;
		flipCardBack(card1);
		flipCardBack(card2);
	}

	}

	private int searchUnsortedStrings(String[] arrayToSearch, String key) {
		
			for (int i = 0; i < arrayToSearch.length; i++) {
				if (arrayToSearch[i].equalsIgnoreCase(key)) {
					return i;
				}
			}
			return -1;
		}
	

	private void flipCard(int i) {
		int temp=(i/10)+(i%10);
		grid[((i/10)*8)+ 4][(i%10)+6]=""+cards[temp];
		printPic(grid);
		
	}
	private void flipCardBack(int i) {
		grid[((i/10)*8)+ 4][(i%10)+6]=" ";
		printPic(grid);
		
	}
	public static void printPic(String[][] pic) {
		for (String[] row : pic) {
			for (String col : row) {
				System.out.print(col);
			}
			System.out.print("\n");
		}
	}
}
