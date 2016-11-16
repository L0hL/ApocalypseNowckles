package caveExplorer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

public class EventRoom extends CaveRoomPd8 {

	public boolean eventHappened;
	private Playable event;
	
	public EventRoom(String description, boolean exists, Playable event) {
		super(description, exists);
		eventHappened = false;
		this.event = event;
	}
	
	public void enter() throws InterruptedException, InvalidMidiDataException, MidiUnavailableException {
//		make all the normal things happen
		super.enter();
		if (!eventHappened) {
			eventHappened = true;
			event.play();
		}
	}

}
