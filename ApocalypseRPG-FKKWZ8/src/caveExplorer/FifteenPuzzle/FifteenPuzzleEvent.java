package caveExplorer.FifteenPuzzle;

import caveExplorer.CaveExplorer;
import caveExplorer.Playable;

public class FifteenPuzzleEvent implements Playable {

	private static final String[] SEQUENCE_1 = {"Welcome!", "This room contains the Fifteen Puzzle!", "In order to leave this room, you must complete the puzzle."};
	private static final String[] SEQUENCE_2 ={"Here's the game","Have fun"};
	
	
	public static void main(String[] args) {
		new FifteenPuzzleEvent().play();
	}
	public void play(){
		readSequence(SEQUENCE_1);
		System.out.println("Come on. Say yes!");
		while(CaveExplorer.in.nextLine().
				toLowerCase().indexOf("yes") < 0 ){
			CaveExplorer.print("You must say yes!. If you don't, you can't leave.");
		}
		readSequence(SEQUENCE_2);
		FifteenPuzzle.startGame();
	}
	
	public static void readSequence(String[] seq){
		for(String s : seq){
			CaveExplorer.print(s);
			CaveExplorer.print("- - - press enter - - -");
			CaveExplorer.in.nextLine();
		}
	}

}
