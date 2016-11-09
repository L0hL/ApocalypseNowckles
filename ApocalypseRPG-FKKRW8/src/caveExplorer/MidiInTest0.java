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



public class MidiInTest0 {

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
				if(cursize >= 3) {
					sequencer.stopRecording();
				}
			}
			
			sequencer.recordDisable(currentTrack);
			Sequence tmp = sequencer.getSequence();
			
//			OutputStream outStrm = null;
			
			 
			MidiSystem.write(tmp, 0, new File("MidiTemp1.mid"));
			
//			System.out.println(outStrm.toString());
			
			System.out.println(currentTrack.get(0).getMessage());
			System.out.println(currentTrack.get(1).getMessage());
			System.out.println(currentTrack.get(2).getMessage());
			System.out.println("-----------------------------------------------------------------------");
			
			Sequence sequence = MidiSystem.getSequence(new File("MidiTemp1.mid"));

	        int trackNumber = 0;
	        for (Track track :  sequence.getTracks()) {
	            trackNumber++;
	            System.out.println("Track " + trackNumber + ": size = " + track.size());
	            System.out.println();
	            for (int i=0; i < track.size(); i++) { 
	                MidiEvent event = track.get(i);
	                System.out.print("@" + event.getTick() + " ");
	                MidiMessage message = event.getMessage();
	                if (message instanceof ShortMessage) {
	                    ShortMessage sm = (ShortMessage) message;
	                    System.out.print("Channel: " + sm.getChannel() + " ");
	                    if (sm.getCommand() == NOTE_ON) {
	                        int key = sm.getData1();
	                        int octave = (key / 12)-1;
	                        int note = key % 12;
	                        String noteName = NOTE_NAMES[note];
	                        int velocity = sm.getData2();
	                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	                    } else if (sm.getCommand() == NOTE_OFF) {
	                        int key = sm.getData1();
	                        int octave = (key / 12)-1;
	                        int note = key % 12;
	                        String noteName = NOTE_NAMES[note];
	                        int velocity = sm.getData2();
	                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	                    } else {
	                        System.out.println("Command:" + sm.getCommand());
	                    }
	                } else {
	                    System.out.println("Other message: " + message.getClass());
	                }
	            }

	            System.out.println();
	        }
			
		}
		
	}

}
