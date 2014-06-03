package Som;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

public final class AudioPlayer extends Thread {

    private File mp3;
    private Player player;
    private boolean loop;

    public AudioPlayer(String path, boolean loop) {
        this.mp3 = new File(path);
        this.loop = loop;
    }

    @Override
    public void run() {
        try {
            FileInputStream fis = new FileInputStream(mp3);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);

            do {
                player.play();
                if(player.isComplete()){
                    player.close();
                    
                }
            } while (loop);
        } catch (Exception ex) {
            player.close();
        }
    }

    public void Finish(boolean loop) {
        player.close();
        this.loop = loop;
    }
}