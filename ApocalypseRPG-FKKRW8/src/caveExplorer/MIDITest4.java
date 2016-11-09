package caveExplorer;

import javax.sound.midi.*;

public class MIDITest4 {

	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
		MidiDevice.Info[] MidiDeviceInfos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < MidiDeviceInfos.length; i++) {
			System.out.println(MidiDeviceInfos[i]);
		}
//		find the suitable device number here, based on some criteria
		MidiDevice MidiOutDevice = MidiSystem.getMidiDevice(MidiDeviceInfos[7]);
		Receiver MidiOutReceiver = MidiOutDevice.getReceiver();
//		Transmitter MidiOutTrans = MidiOutDevice.getTransmitter();
		Sequencer MidiOutSequencer = MidiSystem.getSequencer();
//		Add the new MIDI out device here.
		MidiOutSequencer.getTransmitter().setReceiver(MidiOutReceiver);
		MidiOutSequencer.open();
//		MidiOutReceiver.open();
		
		ShortMessage on = new ShortMessage(ShortMessage.NOTE_ON, 5, 64, 64);
		long timeStamp = -1;
		Receiver rcvr = MidiSystem.getReceiver();
		ShortMessage off = new ShortMessage(ShortMessage.NOTE_OFF, 5, 64, 64);
		MidiOutReceiver.send(on, timeStamp);
		Thread.sleep(2000);
		MidiOutReceiver.send(off, timeStamp);
		
		System.exit(0);
		
		
		
	}

}
