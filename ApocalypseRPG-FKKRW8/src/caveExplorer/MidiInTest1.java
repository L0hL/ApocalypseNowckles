package caveExplorer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;



/**
 * @author Max Friedman
 * MidiMessage interpreter code by Sami Koivu
 *
 */

public class MidiInTest1 {

	static MidiDevice.Info[] infosA = MidiSystem.getMidiDeviceInfo();
	static String displaysAs = "LivePort";
	
	static MidiDevice launchpadIn;
	
	
	
	
	
	public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	
	public static void main(String[] args) throws MidiUnavailableException, InterruptedException, InvalidMidiDataException, IOException {
		int launchpadDeviceNumber = -1;
		int	launchpadInNumber = -1;
		
		for (int i = 0; i < infosA.length; i++) {
			Info inf = infosA[i];
			String name = inf.getName().replace(" ", "");
			System.out.println("\"" + name + "\"");
			if (name.equals(displaysAs)) {
				launchpadDeviceNumber = i;
			}
			
		}
		for (int i = 0; i < infosA.length; i++) {
			Info inf = infosA[i];
			String name = inf.getName().replace(" ", "");
//			System.out.println("\"" + name + "\"");
			if (name.equals(displaysAs)) {
				launchpadInNumber = i;
				break;
			}
			
		}
		
		System.out.println(launchpadInNumber);
		System.out.println(launchpadDeviceNumber);
		
		launchpadIn = MidiSystem.getMidiDevice(infosA[launchpadInNumber]);
		launchpadIn.open();
		
		Sequencer sequencer = MidiSystem.getSequencer();
		
		Transmitter transmitter;
		Receiver receiver;

		// Open a connection to the default sequencer (as specified by MidiSystem)
		sequencer.open();
		// Get the transmitter class from your input device
		transmitter = launchpadIn.getTransmitter();
		// Get the receiver class from your sequencer
		receiver = sequencer.getReceiver();
		// Bind the transmitter to the receiver so the receiver gets input from the transmitter
		transmitter.setReceiver(receiver);
		
		while(true){

			Sequence seq = new Sequence(Sequence.PPQ, 1);
			Track currentTrack = seq.createTrack();
			sequencer.setSequence(seq);
			sequencer.setTickPosition(1);
			sequencer.recordEnable(currentTrack, -1);
			sequencer.startRecording();
			
			int cursize = currentTrack.size();
			while(sequencer.isRecording()){ 
				if(currentTrack.size() != cursize){
					cursize = currentTrack.size();
//					System.out.println(currentTrack.size());
				}
				if(cursize >= 2) {
					sequencer.stopRecording();
				}
			}
			
			sequencer.recordDisable(currentTrack);
			Sequence sequence = sequencer.getSequence();

			int trackNumber = 0;
			for (Track track :  sequence.getTracks()) {
				trackNumber++;
				for (int i=0; i < track.size(); i++) { 
					MidiEvent event = track.get(i);
					MidiMessage message = event.getMessage();
					if (message instanceof ShortMessage) {
						ShortMessage sm = (ShortMessage) message;
						if (sm.getCommand() == NOTE_ON) {
							int key = sm.getData1();
							int velocity = sm.getData2();
							if (velocity > 0) {
								System.out.println(key);
							}
						}
//	                    else if (sm.getCommand() == NOTE_OFF) {
//	                        int key = sm.getData1();
//	                        int velocity = sm.getData2();
//	                        System.out.println(key);
//	                    }
	                }
	            }
	        }
		}
	}
}
