import javax.sound.sampled.*;

import java.io.IOException;
import java.io.*;

public class Audio
{
    private Clip clip;
    public Audio(final String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        File file = new File(filename);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    public Clip getClip()
    {
        return this.clip;
    }
}