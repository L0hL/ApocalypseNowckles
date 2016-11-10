package caveExplorer;

public class GameStartEvent implements Playable {

	private static final String[] SEQUENCE_1 = {
			"[A little yellow mouse with brown stripes and a lightning shaped tail runs up to you.]",
			"Hi. I can see you're not from around here.",
			"Do you like puzzles?"
			};
	
	private static final String[] SEQUENCE_2 = {
			"You are going to have so much fun playing my 2D games.",
			"Take this map!"
			};
	
	public GameStartEvent() {
		
	}
	
	public void play() throws InterruptedException {
		
		readSequence(SEQUENCE_1, 20);
		while (CaveExplorer.in.nextLine().toLowerCase().indexOf("yes") < 0) {
			CaveExplorer.printDelay("C'mon! You know you like puzzles! Say yes!", 20, true);
		}
		readSequence(SEQUENCE_2, 10);
		Thread.sleep(500);
		CaveExplorer.inventory.setHasMap(true);
		CaveExplorer.printDelay("You obtained a map!", 20, true);
		Thread.sleep(2000);
	}
	
	public static void readSequence(String[] seq, long delay) throws InterruptedException{
		for (int s = 0; s < seq.length - 1; s++) {
			CaveExplorer.printDelay(seq[s], delay, true);
			System.out.println("- - - press enter - - -");
			CaveExplorer.in.nextLine();
		}
		CaveExplorer.printDelay(seq[seq.length - 1], delay, true);
		System.out.print("\n");
	}

}
