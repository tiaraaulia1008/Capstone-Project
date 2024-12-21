/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #8
 * 1 - 5026231023 - Nadya Luthfiyah Rahma
 * 2 - 5026231094 - Davina Almeira
 * 3 - 5026231148 - Tiara Aulia Azadirachta Indica
 */

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.initGame() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can the static variable SoundEffect.volume to SoundEffect.Volume.MUTE
 *    to mute the sound.
 *
 * For Eclipse, place the audio file under "src", which will be copied into "bin".
 */
public enum SoundEffect {
   BACKSOUND("BackGroundMusic.wav"),
   WIN("win.wav");

   /** Nested enumeration for specifying volume */
   public static enum Volume {
    MUTE, LOW, MEDIUM, HIGH
}

public static Volume volume = Volume.MEDIUM; // Default volume

private Clip clip;

private SoundEffect(String soundFileName) {
    try {
        URL url = this.getClass().getClassLoader().getResource(soundFileName);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
        e.printStackTrace();
    }
}

/** Play sound effect once */
public void play() {
    if (volume != Volume.MUTE) {
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}

/** Loop sound (for background music) */
public void loop() {
    if (volume != Volume.MUTE) {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}

/** Pause sound */
public void pause() {
    if (clip != null && clip.isRunning()) {
        clip.stop();
    }
}

/** Resume sound */
public void resume() {
    if (volume != Volume.MUTE && clip != null) {
        clip.start();
    }
}
}