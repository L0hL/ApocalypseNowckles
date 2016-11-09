package caveExplorer;

import javax.sound.midi.*;

public class MIDITest5 {

	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
		MidiDevice.Info[] MidiDeviceInfos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < MidiDeviceInfos.length; i++) {
			System.out.println(MidiDeviceInfos[i]);
		}
		//		find the suitable device number here, based on some criteria
//		MidiDevice MidiOutDevice = MidiSystem.getMidiDevice(MidiDeviceInfos[7]);
//		Receiver MidiOutReceiver = MidiOutDevice.getReceiver();
//		//		Transmitter MidiOutTrans = MidiOutDevice.getTransmitter();
//		Sequencer MidiOutSequencer = MidiSystem.getSequencer();
//		//		Add the new MIDI out device here.
//		MidiOutSequencer.getTransmitter().setReceiver(MidiOutReceiver);
//		MidiOutSequencer.open();
		//		MidiOutReceiver.open();

		//		ShortMessage on = new ShortMessage(ShortMessage.NOTE_ON, 5, 64, 64);
		//		long timeStamp = -1;
		//		Receiver rcvr = MidiSystem.getReceiver();
		//		ShortMessage off = new ShortMessage(ShortMessage.NOTE_OFF, 5, 64, 64);
		//		MidiOutReceiver.send(on, timeStamp);
		//		Thread.sleep(2000);
		//		MidiOutReceiver.send(off, timeStamp);
		//		

		try {
			MidiDevice.Info[] infosA = MidiSystem.getMidiDeviceInfo();
//			for (int i = 0; i < infosA.length; i++) {
//				try {
//				MidiDevice launchpad = MidiSystem.getMidiDevice(infosA[i]);
//				launchpad.open();
//
//					Receiver nrec = launchpad.getReceiver();
//		//				nrec.open();
//					
//					Sequencer sqr = MidiSystem.getSequencer();
//					
//					ShortMessage msg1 = new ShortMessage(ShortMessage.NOTE_ON, 5, 64, 64);
//					ShortMessage msg2 = new ShortMessage(ShortMessage.NOTE_OFF, 5, 64, 64);
//					
//					nrec.send(msg1, -1);
//					Thread.sleep(500);
//					nrec.send(msg2, -1);
//					
//		//				data = {85, 40, 40};
//					
//					launchpad.close();
//				}
//			 catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//			}
			
			MidiDevice launchpad = MidiSystem.getMidiDevice(MidiDeviceInfos[8]);
			launchpad.open();

			Receiver nrec = launchpad.getReceiver();
//			nrec.open();
			
			Sequencer sqr = MidiSystem.getSequencer();
			
			ShortMessage msg1 = new ShortMessage(ShortMessage.NOTE_ON, 2, 64, 3);
			ShortMessage msg2 = new ShortMessage(ShortMessage.NOTE_OFF, 2, 64, 3);
			
			nrec.send(msg1, -1);
			Thread.sleep(5000);
			nrec.send(msg2, -1);
			
//			data = {85, 40, 40};
			
			launchpad.close();
			



		} catch (Exception e)
		{
			e.printStackTrace();
		}


		System.exit(0);



	}

}
