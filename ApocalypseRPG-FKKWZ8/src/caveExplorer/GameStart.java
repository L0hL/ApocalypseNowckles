package caveExplorer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import caveExplorer.maxTracey.Launchpad;

public class GameStart implements Playable {

	public static boolean eventOccurred = false; 
	
	private static final String[] SEQUENCE_1 = {
			"You hear the crunch of paper under your foot.",
			"You look down and see what appears to be a map.",
			" - - - - press enter to pick up the map - - - - "
			};
	
	private static final String[] SEQUENCE_2 = {
			"You are going to have so much fun playing my 2D games.",
			"Take this map!"
			};
	
	public GameStart() {
		
	}
	
	public void play() throws InterruptedException, InvalidMidiDataException, MidiUnavailableException {
		eventOccurred = true;
		if (CaveExplorer.useLaunchpadInput) {
			Launchpad.clearPads(Launchpad.launchpad, 15, 0);
			new Thread() {
	            public void run() {
						try {
							Launchpad.flashImg(Launchpad.launchpad, Launchpad.exclamationMark, 3, 250, 250, 3, 0, 50, 0, 50, false);
						} catch (InterruptedException | InvalidMidiDataException | MidiUnavailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	Thread.yield();
	            	}
	            }.start();               
		}
	}
	
	public static void readSequence(String[] seq, long charDelay) throws InterruptedException, InvalidMidiDataException, MidiUnavailableException{
		for (int s = 0; s < seq.length - 1; s++) {
			CaveExplorer.printDelay(seq[s], charDelay, true);
			System.out.println("- - - press enter - - -");
			if(CaveExplorer.in.nextLine().toLowerCase().indexOf("skip") >= 0){
				System.out.println("Dialogue sequence skipped.");
				return;
			}
		}
		CaveExplorer.printDelay(seq[seq.length - 1], charDelay, true);
		System.out.print("\n");
	}
	
	public static void readSequenceAuto(String[] seq, long charDelay, long stringDelay) throws InterruptedException, InvalidMidiDataException, MidiUnavailableException{
		for (int s = 0; s < seq.length; s++) {
			CaveExplorer.printDelay(seq[s], charDelay, true);
			Thread.sleep(stringDelay);
		}
	}

}

