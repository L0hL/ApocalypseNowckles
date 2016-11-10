package caveExplorer;

import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class CaveExplorer {

	public static CaveRoomPd8[][] caves;
	public static Scanner in;
	public static CaveRoomPd8 currentRoom;
	public static InventoryNockles inventory;
	
	protected static boolean[][] cavesHidden;

	public static void main(String[] args) throws InterruptedException, MidiUnavailableException, InvalidMidiDataException {
		Launchpad.main(null);
		Launchpad.clearPads(Launchpad.launchpad, 0, 0);
		in = new Scanner(System.in);
		
		caves = new CaveRoomPd8[6][6];
		cavesHidden = new boolean[caves.length][caves[0].length];
		
//		for (int i = 0; i < cavesHidden.length; i++) {
//			for (int j = 0; j < cavesHidden[i].length; j++) {
//				cavesHidden[i][j] = false;
//			}
//		}
		
		cavesHidden[2][4] = true;

		for (int row = 0; row < caves.length; row++) {
			for (int col = 0; col < caves[row].length; col++) {
				caves[row][col] = new CaveRoomPd8("This room has coords " + row + ", " + col, cavesHidden[row][col]);
			}
		}

		currentRoom = caves[1][2];
		
		caves[1][3] = new EventRoom("This is where you found the map.", true, new GameStartEvent());
		
		currentRoom.enter();
		caves[1][2].setConnection(CaveRoomPd8.WEST, caves[1][1], new Door());
		caves[1][2].setConnection(CaveRoomPd8.SOUTH, caves[2][2], new Door());
		caves[1][2].setConnection(CaveRoomPd8.EAST, caves[1][3], new Door());
		
		
		currentRoom.getAdjRooms();
		
		
		inventory = new InventoryNockles();
		
//		HERE WE GO BOYS!
//		int[] pxl = {2,3};
//		Launchpad.display(Launchpad.launchpad, pxl, 21, "solid");
		startExploring();
	}

	private static void startExploring() throws InterruptedException, InvalidMidiDataException, MidiUnavailableException {
		int[] center = {1,1};
		InventoryNockles.printAdjLP(center, currentRoom.getAdjRooms());
		while (true) {
			print(inventory.getDescription());
			print(currentRoom.getDescription());
//			print("What would you like to do?");
			
//			printDelay(inventory.getDescription(), 1);
//			printDelay(currentRoom.getDescription(), 10);
			printDelay("What would you like to do?", 30, false);
			
			boolean inputConstant = false;
			String inputToUse = "";
			String input = in.nextLine();
			System.out.print("\n");
//			int LastKeyPressed = Launchpad.lastKeyPressed;
//			Launchpad.getInput();
			act(input);

		}
	}
	
	private static void act(String input) throws InterruptedException {
		currentRoom.interpretAction(input);
	}

	public static void print(String text) {
		System.out.println(text);
	}
	
	public static void printDelay(String text, long delay, boolean lineBreak) throws InterruptedException{
		char[] charArr = text.toCharArray();
		for (char c : charArr) {
			System.out.print(c);
			Thread.sleep(delay);
		}
		if (lineBreak){
			System.out.print("\n");
		}
	}

}
