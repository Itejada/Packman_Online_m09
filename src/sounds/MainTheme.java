package sounds;

import config.ConfigurationGame;
import escenas.EscenaCome;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MainTheme extends Thread {

    private String pathMainTheme = "src/sounds/GameCorner-GSC-Remix-PokeRemixStudio.wav";
    private Media media = new Media(new File(pathMainTheme).toURI().toString());
    private MediaPlayer mainTheme = new MediaPlayer(media);
    public boolean state = true;

    @Override
    public void run() {
        for(;;) {
            mainTheme.setVolume(ConfigurationGame.getVolume());
            mainTheme.setAutoPlay(true);
            mainTheme.setCycleCount(MediaPlayer.INDEFINITE);
            if(state) {
                mainTheme.play();
            }else {
                mainTheme.stop();
            }

        }

    }
}
