package caveExplorer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class InventoryNockles {
	
	protected boolean hasMap;
	private String map;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;

	public InventoryNockles() {
		hasMap = false;
		if (hasMap) {
			updateMap();
		}
	}

	public void updateMap() {
		CaveRoomPd8[][] caves = CaveExplorer.caves;
		
		map = " ";
		for (int i = 0; i < caves.length - 1; i++) {
			map += "____";
		}
		map += "___\n";
		
//		each room
		for (CaveRoomPd8[] row : caves) {
//			3 rows of text
			for (int r = 0; r < 3; r++) {
				for (CaveRoomPd8 cr : row) {
					String str = "|   ";
					String contents = cr.getContents();
					if (r == 1) {
						if(cr.getDoor(CaveRoomPd8.WEST) != null && cr.getDoor(CaveRoomPd8.WEST).isOpen()) {
							str = "  " + contents + " ";
						}
						else {
							str = "| " + contents + " ";
						}
					}
					else if (r == 2){
						if (cr.getDoor(CaveRoomPd8.SOUTH) != null && cr.getDoor(CaveRoomPd8.SOUTH).isOpen()) {
							str = "|_ _";
						}
						else {
							str = "|___";
						}
					}
					map += str;
				} //end of row of caverooms
				map += "|\n";
			}
//			map += "|";
		}
		
//		System.out.print(map);
	}
	
	public void updateMapLP() {
		
	}
	
	public static void printAdjLP(int[] center, boolean[][] dirs) throws InterruptedException, InvalidMidiDataException, MidiUnavailableException {
		Launchpad.display(Launchpad.launchpad, center, 21, "pulse");
		
		int[][] modPxls = {
				{center[0]-1, center[1]}, //NORTH
				{center[0], center[1]+1}, //EAST
				{center[0]+1, center[1]}, //SOUTH
				{center[0], center[1]-1}, //WEST
		};
		
		for (int i = 0; i < dirs[0].length; i++) {
			if (dirs[0][i]) {
				Launchpad.display(Launchpad.launchpad, modPxls[i], 3, "solid");
			}
			else if (dirs[1][i]) {
				Launchpad.display(Launchpad.launchpad, modPxls[i], 5, "solid");
			}
		}
	}
	
	public static void printLargeAdjLP(boolean[][] dirs) throws InterruptedException, InvalidMidiDataException, MidiUnavailableException {
		int[] center = {3,3};
		Launchpad.display(Launchpad.launchpad, Launchpad.make2x2Square(center), 21, "pulse");
		
		int[][] modPxls = {
				{center[0]-2, center[1]}, //NORTH
				{center[0], center[1]+2}, //EAST
				{center[0]+2, center[1]}, //SOUTH
				{center[0], center[1]-2}, //WEST
		};
		
		for (int i = 0; i < dirs[0].length; i++) {
			if (dirs[0][i]) {
				Launchpad.display(Launchpad.launchpad, Launchpad.make2x2Square(modPxls[i]), 3, "solid");
			}
			else if (dirs[1][i]) {
				Launchpad.display(Launchpad.launchpad, Launchpad.make2x2Square(modPxls[i]), 5, "solid");
			}
		}
	}

	public String getDescription() {
		if (hasMap) {
			return map;
		}
		else {
			return "You have no inventory.";
		}
	}

	public void setHasMap(boolean b) {
		hasMap = b;
	}
	

}
