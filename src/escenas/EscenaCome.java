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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sounds.MainTheme;
import sounds.PacmanEatingTheme;
import sprites.Bolita;
import sprites.Fantasma;
import sprites.Packman;

import java.io.File;
import java.util.ArrayList;

public class EscenaCome extends Scene {

    private double x = 0;
    private double y = WIDTH_SCREEN/9;
    double velocidad = 5;
    private static final int HEIGHT_SCREEN = 650;
    private static final int WIDTH_SCREEN = 450;
    private Group root;
    private MainTheme mainTheme;

    long mCurrentNanoTime=0;

    public EscenaCome(Group root, MainTheme mainTheme) {
        super(root);
        this.root=root;
        this.mainTheme=mainTheme;
        this.mainTheme.state=false;
        this.start(MyStage.getStage());
    }

    Hud hud= new Hud(WIDTH_SCREEN);

    public void start(Stage theStage) {

        String pathStartSound = "src/sounds/pacman_beginning.wav";
        Media media = new Media(new File(pathStartSound).toURI().toString());
        MediaPlayer startSound = new MediaPlayer(media);
        startSound.setVolume(0.27);
        startSound.setAutoPlay(true);


        Image fimage = new Image("img/fantasma0_0.png");
        Fantasma fantasma = new Fantasma(fimage, HEIGHT_SCREEN, WIDTH_SCREEN+WIDTH_SCREEN/9, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        Fantasma fantasma2 = new Fantasma(fimage, 100, 200, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        Fantasma fantasma3 = new Fantasma(fimage, 220, 53,velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN,22,22);
        Fantasma fantasma4 = new Fantasma(fimage, HEIGHT_SCREEN, 0,velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN,22,22);

        Image image = new Image("img/pacbol_0.png");
        Packman packman = new Packman(image,x,y,velocidad,velocidad, WIDTH_SCREEN, HEIGHT_SCREEN,25,25, 3);
        PacmanEatingTheme sound= new PacmanEatingTheme(packman);
        packman.setImage(image);


        int anchoSprite = (int) packman.getWIDTH_PACMAN();
        int altoSprite = (int) packman.getHEIGHT_PACMAN();

        theStage.setTitle("Keyboard Example");

        Image front = new Image("img/pacbol_0.png");
        Image up = new Image("img/pacbol_up_1.png");
        Image down = new Image("img/pacbol_down_1.png");

        Canvas canvas = new Canvas(HEIGHT_SCREEN, WIDTH_SCREEN);
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

        Bolita bolita=new Bolita((Math.random()*(HEIGHT_SCREEN-(100)))+50, (Math.random()*(WIDTH_SCREEN-(100)))+50, 15, 15);

        //########################## Animacion ###########################
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.setFill( Color.RED );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);



        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                mCurrentNanoTime=currentNanoTime;

                // Clear the canvas
                gc.clearRect(0, 0, HEIGHT_SCREEN, WIDTH_SCREEN);
                gc.setFill(Color.BLUE);
                gc.fillRect(0, canvas.getHeight()/10, canvas.getWidth(), canvas.getHeight());
                hud.renderHud(gc,packman,HEIGHT_SCREEN, WIDTH_SCREEN);


                if(startSound.getCurrentTime().equals(startSound.getBufferProgressTime())) {
                    fantasma.move(currentNanoTime);
                    fantasma2.move(currentNanoTime);
                    fantasma3.move(currentNanoTime);
                    fantasma4.move(currentNanoTime);
                    mainTheme.state=true;

                    packman.movePackman(input, packman, anchoSprite, up, altoSprite, down,mCurrentNanoTime);

                    packman.checkCollision(packman, fantasma,fantasma2,fantasma3,fantasma4, startSound);

                }
                else {
                    fantasma.animation(currentNanoTime,"");
                    fantasma2.animation(currentNanoTime,"");
                    fantasma3.animation(currentNanoTime,"");
                    fantasma4.animation(currentNanoTime,"");
                }
                bolita.eatingBol(packman, HEIGHT_SCREEN, WIDTH_SCREEN);
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