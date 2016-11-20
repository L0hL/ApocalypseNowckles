package FifteenPuzzle;



public class FifteenPuzzleEvent implements Playable {

	private static final String[] SEQUENCE_1 = {"Oh shit waddup", "here come dat boi","Do you like puzzles?"};
	private static final String[] SEQUENCE_2 ={"Here's the game","Have fun"};
	
	
	public static void main(String[] args) {
		new FifteenPuzzleEvent().play();
	}
	public void play(){
		readSequence(SEQUENCE_1);
		System.out.println("Come on. Tell me you like puzzles. Say yes.");
		while(FifteenPuzzle.in.nextLine().
				toLowerCase().indexOf("yes") < 0 ){
			FifteenPuzzle.print("C'mon! You know "
					+ "you like puzzles. "
					+ "Say yes!!");
		}
		readSequence(SEQUENCE_2);
		FifteenPuzzle.startGame();
	}
	
	public static void readSequence(String[] seq){
		for(String s : seq){
			FifteenPuzzle.print(s);
			FifteenPuzzle.print("- - - press enter - - -");
			FifteenPuzzle.in.nextLine();
		}
	}


}
