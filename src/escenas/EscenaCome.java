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
import sounds.Sound;
import sounds.Sounds;
import sprites.Fantasma;
import sprites.Sprite2;

import java.io.File;
import java.util.ArrayList;

public class EscenaCome extends Application {
    //hola
    private double x = 0;
    private double y = 0;
    double velocidad = 5;
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
        startSound.setVolume(0.27);
        startSound.setAutoPlay(true);



        Image fimage = new Image("img/fantasma0_0.png");
        Fantasma fantasma = new Fantasma(fimage, ANCHO, ALTURA, velocidad, velocidad,ANCHO, ALTURA, 24,25);
        Fantasma fantasma2 = new Fantasma(fimage, 100, 200, velocidad, velocidad, ANCHO, ALTURA, 24,25);
        Fantasma fantasma3 = new Fantasma(fimage, 220, 33,velocidad, velocidad, ANCHO, ALTURA,24,25);
        Fantasma fantasma4 = new Fantasma(fimage, ANCHO, 0,velocidad, velocidad, ANCHO, ALTURA,24,25);

        Image image = new Image("img/pacbol_0.png");

        Sprite2 sprite2 = new Sprite2(image,x,y,velocidad,velocidad,ALTURA,ANCHO,24,24);
       Sounds sound= new Sounds(sprite2);


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

        //Sounds sound=new Sounds();
//#################################  Detectar teclas ########################################
        ArrayList<String> input = new ArrayList<>();

        theScene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();

                    // only add once... prevent duplicates
                    if (!input.contains(code))
                        input.add(code);

                });

        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    sprite2.isPlaying=false;
                    input.remove(code);
                    sprite2.setImage(front);
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();
        sound.start();

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

                    sprite2.checkCollision(sprite2, fantasma, startSound);
                    sprite2.checkCollision(sprite2, fantasma2,startSound);
                    sprite2.checkCollision(sprite2, fantasma3,startSound);
                    sprite2.checkCollision(sprite2, fantasma4,startSound);
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
    }      ;
}