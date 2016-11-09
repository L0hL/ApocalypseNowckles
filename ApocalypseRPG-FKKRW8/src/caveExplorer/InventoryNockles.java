package caveExplorer;

public class InventoryNockles {
	
	private boolean hasMap;
	private String map;

	public InventoryNockles() {
		hasMap = true;
		updateMap();
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
