package caveExplorer;

//import javax.sound.midi.ShortMessage;
import java.io.*;
import javax.sound.midi.*;
import javax.sound.midi.MidiDevice.Info;

public class MIDITest3 {

	public static String inputName  = "IACBus1";
	public static String outputName = "IACBus1";
	public MidiDevice    input;
	public MidiDevice    output;
	public Receiver      rcvr;
	public String  midiFile = "/Users/max/Desktop/midi1.mid";

	public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException {

		new MIDITest3().start();
		
		//		Receiver novationLPP = MidiSystem.getReceiver();

//		MidiDevice.Info[] devices = MidiSystem.getMidiDeviceInfo();

		


	}
	
	public void start() {
		init();

		try {
//			File midi = new File(midiFile);
//			InputStream ios = new BufferedInputStream(new FileInputStream(midi));
//			Sequencer sqr = MidiSystem.getSequencer(false);
//			sqr.setSequence(ios);
//			sqr.open();
			input.open();
			output.open();
			rcvr = output.getReceiver();
			output.getTransmitter().setReceiver(rcvr);
//			sqr.start();
			
			ShortMessage on = new ShortMessage(ShortMessage.NOTE_ON, 5, 64, 64);
			long timeStamp = -1;
			Receiver rcvr = MidiSystem.getReceiver();
			ShortMessage off = new ShortMessage(ShortMessage.NOTE_OFF, 5, 64, 64);
			rcvr.send(on, timeStamp);
			Thread.sleep(2000);
			rcvr.send(off, timeStamp);
		}
		catch (Exception e) {
			//         e.printStackTrace();
		}

	}

	public void init() {
		try {
			Info[] info = MidiSystem.getMidiDeviceInfo();
			for (Info inf : info) {
				String name = inf.getName().replace(" ", "");
				System.out.println("\"" + name + "\"");
				            if (name.equals(inputName)) {
				               input = MidiSystem.getMidiDevice(inf);
				            }
				if (name.equals(outputName)) {
					output = MidiSystem.getMidiDevice(inf);
				}
			}
		}
		catch (Exception e) {
			//         e.printStackTrace();
		}
	}

	public static void addTrack(Sequence s, int instrument, int tempo, char[  ] notes) throws InvalidMidiDataException {
		Track track = s.createTrack( );  // Begin with a new track

		// Set the instrument on channel 0
		ShortMessage sm = new ShortMessage( );
		sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
		track.add(new MidiEvent(sm, 0));

		ShortMessage on = new ShortMessage( );
		on.setMessage(ShortMessage.NOTE_ON,  0, 64, 64);
		ShortMessage off = new ShortMessage( );
		off.setMessage(ShortMessage.NOTE_OFF, 0, 64, 64);
		long now = System.currentTimeMillis();
		track.add(new MidiEvent(on, now));
		track.add(new MidiEvent(off, now + 2000));

	}

}
