package escenas;

import config.ConfigurationGame;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
import java.io.IOException;
import java.util.ArrayList;

public class EscenaCome extends Scene {



    //TODO: Mostrar los datos de la partida jugada, y en la siguiente pantalla el top10.
    //TODO: Crear fichero con mejores partidas. (top 10)
    //TODO: Refactorizar código.

    private double x = 0;
    private double y = WIDTH_SCREEN/9;
    double velocidad = 5;
    public static final int HEIGHT_SCREEN = 650;
    public static final int WIDTH_SCREEN = 450;
    private Group root;
    private MainTheme mainTheme;
    private long mCurrentNanoTime=0;
    private long lastupdate;
    private Hud hud= new Hud(WIDTH_SCREEN);
    private int state = -2;
    private ImageView musicIcon = new ImageView("img/musicon.png");
    private ImageView soundIcon = new ImageView("img/soundon.png");
    private Image musicOn = new Image("img/musicon.png");
    private Image musicOf = new Image("img/musicoff.png");
    private Image soundOn = new Image("img/soundon.png");
    private Image soundOf = new Image("img/soundoff.png");

    public EscenaCome(Group root, MainTheme mainTheme) {
        super(root);
        this.root=root;
        this.mainTheme=mainTheme;
        this.mainTheme.state=false;
        this.start(MyStage.getStage());
        if(ConfigurationGame.isVolumeDisabled()) {
            musicIcon.setImage(musicOf);
        }else {
            musicIcon.setImage(musicOn);
        }
        if(ConfigurationGame.isSoundsDisabled()) {
            soundIcon.setImage(soundOf);
        }else {
            soundIcon.setImage(soundOn);
        }
    }



    public void start(Stage theStage) {

        String pathStartSound = "src/sounds/pacman_beginning.wav";
        Media media = new Media(new File(pathStartSound).toURI().toString());
        MediaPlayer startSound = new MediaPlayer(media);

        startSound.setAutoPlay(true);

        Image fimage = new Image("img/fantasma0_0.png");
        Fantasma fantasma = new Fantasma(fimage, HEIGHT_SCREEN, WIDTH_SCREEN+WIDTH_SCREEN/9, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        Fantasma fantasma2 = new Fantasma(fimage, 100, 200, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        Fantasma fantasma3 = new Fantasma(fimage, 220, 53,velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN,22,22);
        Fantasma fantasma4 = new Fantasma(fimage, HEIGHT_SCREEN, 0,velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN,22,22);

        Image image = new Image("img/pacbol_0.png");
        Packman packman = new Packman(image,x,y,velocidad,velocidad, WIDTH_SCREEN, HEIGHT_SCREEN,25,25, 1);
        PacmanEatingTheme sound= new PacmanEatingTheme(packman);
        packman.setImage(image);

        int anchoSprite = (int) packman.getWIDTH_PACMAN();
        int altoSprite = (int) packman.getHEIGHT_PACMAN();

        theStage.setTitle("Keyboard Example");

        Image front = new Image("img/pacbol_0.png");
        Image up = new Image("img/pacbol_up_1.png");
        Image down = new Image("img/pacbol_down_1.png");

        Canvas canvas = new Canvas(HEIGHT_SCREEN, WIDTH_SCREEN);
        root.getChildren().addAll(canvas,musicIcon,soundIcon);

        musicIcon.setX(WIDTH_SCREEN-100);
        musicIcon.setY(10);
        musicIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!ConfigurationGame.isVolumeDisabled()) {
                    musicIcon.setImage(musicOf);
                    ConfigurationGame.setVolumeDisabled(true);
                    ConfigurationGame.setVolume(0);
                }else {
                    musicIcon.setImage(musicOn);
                    ConfigurationGame.setVolumeDisabled(false);
                    ConfigurationGame.setVolume(0.50);
                }
            }
        });

        soundIcon.setX(WIDTH_SCREEN-150);
        soundIcon.setY(10);
        soundIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!ConfigurationGame.isSoundsDisabled()) {
                    soundIcon.setImage(soundOf);
                    ConfigurationGame.setSoundsDisabled(true);
                }else {
                    soundIcon.setImage(soundOn);
                    ConfigurationGame.setSoundsDisabled(false);
                }
            }
        });

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

        Bolita bolita = new Bolita((Math.random()*(HEIGHT_SCREEN-(100)))+50, (Math.random()*(WIDTH_SCREEN-(100)))+50, 15, 15);

        //########################## Animacion ###########################
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.setFill( Color.RED );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if(!ConfigurationGame.isVolumeDisabled()) {
                    startSound.setVolume(ConfigurationGame.getVolume()/2);
                }else {
                    startSound.setVolume(0);
                }
                mCurrentNanoTime=currentNanoTime;

                // Clear the canvas
                gc.clearRect(0, 0, HEIGHT_SCREEN, WIDTH_SCREEN);
                gc.setFill(Color.BLUE);
                gc.fillRect(0, canvas.getHeight()/10, canvas.getWidth(), canvas.getHeight());

                if(packman.getLives()>=0) {
                    if(startSound.getCurrentTime().equals(startSound.getBufferProgressTime())) {
                        if(state!=0) {
                            if(mCurrentNanoTime-lastupdate>=222222222) {
                                state=0;
                            }else {
                                state=-1;
                            }
                        }
                        mainTheme.state=true;
                        fantasma.move(currentNanoTime);
                        fantasma2.move(currentNanoTime);
                        fantasma3.move(currentNanoTime);
                        fantasma4.move(currentNanoTime);

                        packman.movePackman(input, packman, anchoSprite, up, altoSprite, down,mCurrentNanoTime);
                        try {
                            packman.checkCollision(packman, fantasma,fantasma2,fantasma3,fantasma4, startSound);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        state=-2;
                        mainTheme.state=false;
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
                    hud.renderHud(gc,packman,HEIGHT_SCREEN, WIDTH_SCREEN);

                    //if(state==0) state=-1;
                    if(state ==-2) {
                        gc.setFill( Color.WHITE );
                        gc.fillText( "¿¡PREPARADO!?", HEIGHT_SCREEN/4, WIDTH_SCREEN/2);
                        lastupdate=mCurrentNanoTime;
                    }

                    if(state ==-1) {
                        gc.setFill( Color.WHITE );
                        gc.fillText( "¡YA!", HEIGHT_SCREEN/2, WIDTH_SCREEN/2);
                    }
                }
                }
        }.start();
        theStage.show();
    }

    public static void gameOver() {
            Parent root = null;
            try {
                root = FXMLLoader.load(EscenaCome.class.getResource("../views/MainScreen.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            MyStage.getStage().setTitle("PacMan 2.0");
            MyStage.getStage().setResizable(false);
            MyStage.getStage().setScene(new Scene(root, MyStage.ANCHO, MyStage.ALTURA));
            MyStage.setStage(MyStage.getStage());
            MyStage.getStage().show();
        }
}