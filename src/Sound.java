import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl;


public class Sound {
	private Clip sound;
	private int valid;
	
	
	/**
	 * Create Clip object from sound file
	 * @param filename
	 */
	public Sound(String filename) {
		File soundFile = new File(filename);
		valid = 0;

	    try {
	    	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

			try {
				Clip clip = AudioSystem.getClip();

				try {
					clip.open(audioInputStream);
					sound = clip;
					valid = 1;

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * play the sound
	 */
	public void play() {
		if (valid == 1) {
			sound.start();
		}
	}

	/**
	 * loop the sound
	 */
	public void loop() {
        if (valid == 1) {
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

	/**
	 * stop the sound from playing
	 */
    public void stop() {
        if (valid == 1) {
            sound.stop();
        }
    }

    /**
     * Set the volume for this sound
     * @param volume
     */
    public void setVolume(float volume) {
        if (valid == 1) {
            FloatControl volumeControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
            // Volume range: -80.0 (mute) to 6.0 (max)
            volumeControl.setValue(volume);
        }
    }
}
