import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	static Clip Audio;
	public static FloatControl audioControl;

	static void play(File SelectAudio) {
		try {
			Audio = AudioSystem.getClip();
			Audio.open(AudioSystem.getAudioInputStream(SelectAudio));
			Audio.start();
			audioControl = (FloatControl) Audio.getControl(FloatControl.Type.MASTER_GAIN);
            audioControl.setValue(-25.0f);
			Audio.loop(20);
		}
		catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	static void stop() {
		Audio.stop();
		Audio.close();
		Audio.drain();
	}
	public static void main(String[] args) {
		//File music = new File(null);
		//play(music);
	}
	
}
