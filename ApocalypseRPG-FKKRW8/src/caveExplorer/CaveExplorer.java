package caveExplorer;

import java.util.Scanner;

public class CaveExplorer {

	public static CaveRoomPd8[][] caves;
	public static Scanner in;
	public static CaveRoomPd8 currentRoom;
	public static InventoryNockles inventory;

	public static void main(String[] args) throws InterruptedException {
		in = new Scanner(System.in);
		caves = new CaveRoomPd8[5][5];

		for (int row = 0; row < caves.length; row++) {
			for (int col = 0; col < caves[row].length; col++) {
				caves[row][col] = new CaveRoomPd8("This room has coords " + row + ", " + col);
			}
		}

		currentRoom = caves[1][2];
		
		caves[1][3] = new EventRoom("This is where you found the map.", new GameStartEvent());
		
		currentRoom.enter();
		caves[1][2].setConnection(CaveRoomPd8.WEST, caves[1][1], new Door());
		caves[1][2].setConnection(CaveRoomPd8.SOUTH, caves[2][2], new Door());
		caves[1][2].setConnection(CaveRoomPd8.EAST, caves[1][3], new Door());
		
		
		inventory = new InventoryNockles();
		
//		HERE WE GO BOYS!
		startExploring();
	}

	private static void startExploring() throws InterruptedException {
		while (true) {
			print(inventory.getDescription());
			print(currentRoom.getDescription());
			print("What would you like to do?");
			String input = in.nextLine();
			act(input);
		}
	}
	
	private static void act(String input) throws InterruptedException {
		currentRoom.interpretAction(input);
	}

	public static void print(String text) {
		System.out.println(text);
	}
	
	public static void printDelay(String text, long delay) throws InterruptedException{
		char[] charArr = text.toCharArray();
		for (char c : charArr) {
			System.out.print(c);
			Thread.sleep(delay);
		}
		System.out.print("\n");
	}

}
