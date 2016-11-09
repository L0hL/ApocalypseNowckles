package caveExplorer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;

public class MidiTest {
	public static String inputName  = "IACBus2";
	public static String outputName = "StandalonePort";
	public MidiDevice    input;
	public MidiDevice    output;
	public Receiver      rcvr;
	public String  midiFile = "/Users/max/Desktop/midi1.mid";

	public static void main(String[] args) {
		new MidiTest().start();
	}


	public void start() {
		init();

		try {
			File midi = new File(midiFile);
			InputStream ios = new BufferedInputStream(new FileInputStream(midi));
			Sequencer sqr = MidiSystem.getSequencer(false);
			sqr.setSequence(ios);
			sqr.open();
			output.open();
			rcvr = output.getReceiver();
			sqr.getTransmitter().setReceiver(rcvr);
			sqr.start();
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


}