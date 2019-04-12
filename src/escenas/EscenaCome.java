package escenas;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sprites.Fantasma;
import sprites.Sprite2;

import java.io.File;
import java.util.ArrayList;

public class EscenaCome extends Application {

    private double x = 0;
    private double y = 0;
    double velocidad = 3;
    private static final int ANCHO = 650;
    private static final int ALTURA = 450;
    Image[] framesR = new Image[6];
    Image[] framesL = new Image[6];

    long mCurrentNanoTime=0;

    @Override
    public void start(Stage theStage) {

        String pathStartSound = "src/sounds/pacman_beginning.wav";
        Media media = new Media(new File(pathStartSound).toURI().toString());
        MediaPlayer startSound = new MediaPlayer(media);
        startSound.setAutoPlay(true);


        Image fimage = new Image("img/fantasma0_0.png");
        Fantasma fantasma = new Fantasma(fimage, ANCHO, ALTURA, velocidad, velocidad,ANCHO, ALTURA, 32,32);

        Fantasma fantasma2 = new Fantasma(fimage, 100, 200, velocidad, velocidad, ANCHO, ALTURA, 32,32);

        Fantasma fantasma3 = new Fantasma(fimage, 20, 33,velocidad, velocidad, ANCHO, ALTURA,32,32);

        Fantasma fantasma4 = new Fantasma(fimage, 45, 120,velocidad, velocidad, ANCHO, ALTURA,32,32);

        Image image = new Image("img/pacbol_0.png");


        Sprite2 sprite2 = new Sprite2(image,x,y,velocidad,velocidad,ALTURA,ANCHO,28,28);
        sprite2.setHeight(28);
        sprite2.setWidth(29);
        int anchoSprite = (int) sprite2.getWidth();
        int altoSprite = (int) sprite2.getHeight();

        sprite2.setImage(new Image("img/pacbol_0.png"));
        theStage.setTitle("Keyboard Example");

        Image front = new Image("img/pacbol_0.png");
        Image up = new Image("img/pacbol_up_1.png");
        Image down = new Image("img/pacbol_down_1.png");

// Escena #########################################################################
        Group root = new Group();
        Scene theScene = new Scene(root, ANCHO, ALTURA);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(ANCHO, ALTURA);
        root.getChildren().add(canvas);


        for (int i = 0; i < 6; i++) {
            framesR[i] = new Image("img/pacbol_" + (i + 1) + ".png");
            framesL[i] = new Image("img/pacbol_" + (i + 7) + ".png");
        }

        ArrayList<String> input = new ArrayList<>();

        theScene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    //sprite2.playSoundPacmanEating();

                    // only add once... prevent duplicates
                    if (!input.contains(code))
                        input.add(code);

                });

        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    //sprite2.stopSoundPacmanEating(sprite2.getMoveSound());
                    input.remove(code);
                    sprite2.setImage(front);
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                mCurrentNanoTime=currentNanoTime;
                // Clear the canvas
                gc.clearRect(0, 0, ANCHO, ALTURA);
                gc.setFill(Color.BLUE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                if(startSound.getCurrentTime().equals(startSound.getBufferProgressTime())) {
                    fantasma.move(currentNanoTime);
                    fantasma2.move(currentNanoTime);
                    fantasma3.move(currentNanoTime);
                    fantasma4.move(currentNanoTime);

                    sprite2.movePackman(input, sprite2, anchoSprite, up, altoSprite, down,mCurrentNanoTime);

                    sprite2.checkCollision(sprite2, fantasma);
                    sprite2.checkCollision(sprite2, fantasma2);
                    sprite2.checkCollision(sprite2, fantasma3);
                    sprite2.checkCollision(sprite2, fantasma4);
                }
                else {
                    fantasma.animation(currentNanoTime,"");
                    fantasma2.animation(currentNanoTime,"");
                    fantasma3.animation(currentNanoTime,"");
                    fantasma4.animation(currentNanoTime,"");
                }

                sprite2.render(gc);
                fantasma.render(gc);
                fantasma2.render(gc);
                fantasma3.render(gc);
                fantasma4.render(gc);

            }
        }.start();

        theStage.show();
    }
}