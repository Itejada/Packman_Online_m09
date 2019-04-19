package sounds;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MainTheme extends Thread {

    String pathMainTheme = "src/sounds/GameCorner-GSC-Remix-PokeRemixStudio.wav";
    Media media = new Media(new File(pathMainTheme).toURI().toString());
    MediaPlayer mainTheme = new MediaPlayer(media);

    @Override
    public void run() {
        mainTheme.setVolume(0.50);
        mainTheme.setAutoPlay(true);
        mainTheme.setCycleCount(MediaPlayer.INDEFINITE);
        mainTheme.play();
    }
}
