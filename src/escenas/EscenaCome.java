package escenas;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sounds.Sounds;
import sprites.Bolita;
import sprites.Fantasma;
import sprites.Packman;

import java.io.File;
import java.util.ArrayList;

public class EscenaCome extends Scene {

    private double x = 0;
    private double y = 0;
    double velocidad = 5;
    private static final int ANCHO = 650;
    private static final int ALTURA = 450;
    private Group root;

    long mCurrentNanoTime=0;

    public EscenaCome(Group root) {
        super(root);
        this.root=root;
        this.start(MyStage.getStage());
    }

    public void start(Stage theStage) {

        String pathStartSound = "src/sounds/pacman_beginning.wav";
        Media media = new Media(new File(pathStartSound).toURI().toString());
        MediaPlayer startSound = new MediaPlayer(media);
        startSound.setVolume(0.27);
        startSound.setAutoPlay(true);



        Image fimage = new Image("img/fantasma0_0.png");
        Fantasma fantasma = new Fantasma(fimage, ANCHO, ALTURA, velocidad, velocidad/2,ANCHO, ALTURA, 22,22);
        Fantasma fantasma2 = new Fantasma(fimage, 100, 200, velocidad, velocidad/2, ANCHO, ALTURA, 22,22);
        Fantasma fantasma3 = new Fantasma(fimage, 220, 33,velocidad, velocidad/2, ANCHO, ALTURA,22,22);
        Fantasma fantasma4 = new Fantasma(fimage, ANCHO, 0,velocidad, velocidad/2, ANCHO, ALTURA,22,22);

        Image image = new Image("img/pacbol_0.png");
        Packman packman = new Packman(image,x,y,velocidad,velocidad,ALTURA,ANCHO,22,22);
        Sounds sound= new Sounds(packman);
        packman.setImage(image);


        int anchoSprite = (int) packman.getWidth();
        int altoSprite = (int) packman.getHeight();

        theStage.setTitle("Keyboard Example");

        Image front = new Image("img/pacbol_0.png");
        Image up = new Image("img/pacbol_up_1.png");
        Image down = new Image("img/pacbol_down_1.png");

        Canvas canvas = new Canvas(ANCHO, ALTURA);
        root.getChildren().add(canvas);

//#################################  Detectar teclas ########################################
        ArrayList<String> input = new ArrayList<>();

        this.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();

                    // only add once... prevent duplicates
                    if (!input.contains(code))
                        input.add(code);

                });

        this.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    packman.isPlaying=false;
                    input.remove(code);
                    packman.setImage(front);
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();
        sound.start();

        Bolita bolita=new Bolita(Math.random()*ANCHO, Math.random()*ALTURA, 15, 15);

        //########################## Animacion ###########################

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

                    packman.movePackman(input, packman, anchoSprite, up, altoSprite, down,mCurrentNanoTime);

                    packman.checkCollision(packman, fantasma, startSound);
                    packman.checkCollision(packman, fantasma2,startSound);
                    packman.checkCollision(packman, fantasma3,startSound);
                    packman.checkCollision(packman, fantasma4,startSound);
                }
                else {
                    fantasma.animation(currentNanoTime,"");
                    fantasma2.animation(currentNanoTime,"");
                    fantasma3.animation(currentNanoTime,"");
                    fantasma4.animation(currentNanoTime,"");
                }
                bolita.eatingBol(packman, ANCHO, ALTURA);
                bolita.render(gc);
                packman.render(gc);
                fantasma.render(gc);
                fantasma2.render(gc);
                fantasma3.render(gc);
                fantasma4.render(gc);

            }
        }.start();

        theStage.show();
    }
}