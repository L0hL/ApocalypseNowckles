package caveExplorer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import caveExplorer.maxTracey.Launchpad;

public class MidiInChangeRunnable implements Runnable {

	public static int inData = 0;
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			inData = Launchpad.getInput();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}