package caveExplorer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public interface Playable {

	public void play() throws InterruptedException, InvalidMidiDataException, MidiUnavailableException;
}
