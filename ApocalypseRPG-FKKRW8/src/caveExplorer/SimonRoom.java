package caveExplorer;

import java.util.Arrays;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class SimonRoom implements Playable {
	static String cardArray[][];
	static String[][] pic;
	static boolean isPlaying;
	static String grid[][];
	static int[] cards;

	@Override
	public void play() throws InterruptedException, InvalidMidiDataException, MidiUnavailableException {
		// TODO Auto-generated method stub
		isPlaying = true;
		makeGame();

	}

	public void makeGame() {
		cards = new int[20];
		makeCards();
		newGrid(2, 10);
	}

	private void makeCards() {
		int key[] = { 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10 };

		for (int i = 0; i < cards.length; i++) {

			int tempInt = -1;
			boolean inLoop = true;
			while (inLoop == true) {
				// printArrayLinear(outArray);
				tempInt = (int) ((10) * Math.random()) + 1;
				if (searchUnsorted(key, tempInt) >= 0) {
					cards[i] = tempInt;
					key[tempInt] = -2;
					inLoop = false;
				}
			}
		}

	}

	private static String[][] newGrid(int i, int j) {
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
				arr[topLeftY + 6][topLeftX + 3] = "_";
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
}
