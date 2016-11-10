package caveExplorer;

public class EventRoom extends CaveRoomPd8 {

	private boolean eventHappened;
	private Playable event;
	
	public EventRoom(String description, boolean exists, Playable event) {
		super(description, exists);
		eventHappened = false;
		this.event = event;
	}
	
	public void enter() throws InterruptedException {
//		make all the normal things happen
		super.enter();
		if (!eventHappened) {
			eventHappened = true;
			event.play();
		}
	}

}
