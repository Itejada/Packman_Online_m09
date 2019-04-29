package online;

import config.ConfigurationGame;
import escenas.Hud;
import escenas.MyStage;
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
import sprites.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class EscenaComeOnline extends Scene {

    private double x = 0;
    private double y = WIDTH_SCREEN/9;
    double velocidad = 5.5;
    public static final int HEIGHT_SCREEN = 650;
    public static final int WIDTH_SCREEN = 450;
    public static final int VIDAS = 1;
    private Group root;
    private MainTheme mainTheme;
    private long mCurrentNanoTime=0;
    private long lastupdate=0;
    private long startPartida=0;
    private long deadTime =0;
    private Hud hud= new Hud(WIDTH_SCREEN);
    private int state = -2;
    private ImageView musicIcon = new ImageView("img/musicon.png");
    private ImageView soundIcon = new ImageView("img/soundon.png");
    private Image musicOn = new Image("img/musicon.png");
    private Image musicOf = new Image("img/musicoff.png");
    private Image soundOn = new Image("img/soundon.png");
    private Image soundOf = new Image("img/soundoff.png");
    private boolean host = true;
    private int result;

    private PartidaOnline partidaOnline;
    private final String IP_SERVER = InetAddress.getLocalHost().getHostAddress();
    private final int PUERTO =5556;

    byte [] receivedData = new byte[2048];
    DatagramPacket packet;
    DatagramSocket socket = new DatagramSocket();


    public EscenaComeOnline(Group root, MainTheme mainTheme) throws IOException {
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

    public void start(Stage theStage) throws IOException {

        //Título de la ventana.
        theStage.setTitle("Pacman V2 - Modo Online");


        //Sonidos del mundo.
        String pathStartSound = "src/sounds/pacman_beginning.wav";
        Media media = new Media(new File(pathStartSound).toURI().toString());
        MediaPlayer startSound = new MediaPlayer(media);
        //startSound.setAutoPlay(true);


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

        //Fantasmas del mundo.
        Image fimage = new Image("img/fantasma0_0.png");
        Fantasma fantasma = new Fantasma(fimage, HEIGHT_SCREEN, WIDTH_SCREEN+WIDTH_SCREEN/9, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        Fantasma fantasma2 = new Fantasma(fimage, 100, 200, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        Fantasma fantasma3 = new Fantasma(fimage, 220, 53,velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN,22,22);
        Fantasma fantasma4 = new Fantasma(fimage, HEIGHT_SCREEN, 0,velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN,22,22);

        final ArrayList<Fantasma> fantasmasByDefault = new ArrayList<>();
        fantasmasByDefault.add(fantasma);
        fantasmasByDefault.add(fantasma2);
        fantasmasByDefault.add(fantasma3);
        fantasmasByDefault.add(fantasma4);

        FantasmaOnline fantasmaOnline = new FantasmaOnline(HEIGHT_SCREEN, WIDTH_SCREEN+WIDTH_SCREEN/9, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        FantasmaOnline fantasmaOnline2 = new FantasmaOnline(100, 200, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        FantasmaOnline fantasmaOnline3 = new FantasmaOnline(220, 53,velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN,22,22);
        FantasmaOnline fantasmaOnline4 = new FantasmaOnline(HEIGHT_SCREEN, 0,velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN,22,22);

        final ArrayList<FantasmaOnline> fantasmasOnlineByDefault = new ArrayList<>();
        fantasmasOnlineByDefault.add(fantasmaOnline);
        fantasmasOnlineByDefault.add(fantasmaOnline2);
        fantasmasOnlineByDefault.add(fantasmaOnline3);
        fantasmasOnlineByDefault.add(fantasmaOnline4);

        //Packman del mundo
        Image image = new Image("img/pacbol_0.png");
        Packman packman = new Packman(image,x,y,velocidad,velocidad, WIDTH_SCREEN, HEIGHT_SCREEN,25,25, VIDAS,0);
        PacmanEatingTheme sound= new PacmanEatingTheme(packman);
        packman.setImage(image);
        sound.start();

        PackmanOnline packmanOnline = new PackmanOnline(x,y,velocidad,velocidad, WIDTH_SCREEN, HEIGHT_SCREEN,25,25, VIDAS,0);


        int anchoSprite = (int) packman.getWIDTH_PACMAN();
        int altoSprite = (int) packman.getHEIGHT_PACMAN();

        //Bolita del mundo.
        Bolita bolitaDefault = new Bolita((Math.random()*(HEIGHT_SCREEN-(100)))+50, (Math.random()*(WIDTH_SCREEN-(100)))+50, 15, 15);

        BolitaOnline bolitaOnline = new BolitaOnline((Math.random()*(HEIGHT_SCREEN-(100)))+50, (Math.random()*(WIDTH_SCREEN-(100)))+50, 15, 15);

        //Jugador del mundo
        Jugador jugador = new Jugador();

        //Partida del mundo.
        partidaOnline = new PartidaOnline(jugador,fantasmasOnlineByDefault,packmanOnline,bolitaOnline);



        Image front = new Image("img/pacbol_0.png");
        Image up = new Image("img/pacbol_up_1.png");
        Image down = new Image("img/pacbol_down_1.png");

        Canvas canvas = new Canvas(HEIGHT_SCREEN, WIDTH_SCREEN);
        root.getChildren().addAll(canvas,musicIcon,soundIcon);

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

        //########################## Animacion ###########################
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.setFill( Color.RED );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);


        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                System.out.println(partidaOnline.getEstadoPartida());
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(os);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    oos.writeObject(partidaOnline);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] missatge = os.toByteArray();

                //creació del paquet a enviar
                try {
                    packet = new DatagramPacket(missatge, missatge.length, InetAddress.getByName(IP_SERVER), PUERTO);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                //creació d'un sòcol temporal amb el qual realitzar l'enviament
                //socket = new DatagramSocket();
                //Enviament del missatge
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //creació del paquet per rebre les dades
                packet = new DatagramPacket(receivedData, 1024);
                //espera de les dades
                try {
                    socket.setSoTimeout(5000);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                try {
                    socket.receive(packet);
                    //processament de les dades rebudes i obtenció de la resposta
                    getDataToRequest(packet.getData(), packet.getLength());
                }catch(SocketTimeoutException e) {
                    System.out.println("El servidor no respòn: " + e.getMessage());
                    result=-2;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                PartidaOnline partidaOnline = getPartidaOnline();
                ArrayList<FantasmaOnline> fantasmas = partidaOnline.getFantasmas();
                for(int i=0;i<fantasmas.size();i++) {
                    fantasmasByDefault.get(i).setPosX(fantasmas.get(i).getPosX());
                    fantasmasByDefault.get(i).setPosY(fantasmas.get(i).getPosY());
                }
                //Packman packmanOnline = partidaOnline.getPackmans().get(1);
                BolitaOnline bolita = partidaOnline.getBolita();
                bolitaDefault.setX(bolitaOnline.getX());
                bolitaDefault.setY(bolitaOnline.getY());

                if(!ConfigurationGame.isVolumeDisabled()) {
                    startSound.setVolume(ConfigurationGame.getVolume()/2);
                }else {
                    startSound.setVolume(0);
                }
                mCurrentNanoTime=currentNanoTime;
                deadTime =mCurrentNanoTime-startPartida;

                // Clear the canvas
                gc.clearRect(0, 0, HEIGHT_SCREEN, WIDTH_SCREEN);
                gc.setFill(Color.BLUE);
                gc.fillRect(0, canvas.getHeight()/10, canvas.getWidth(), canvas.getHeight());

                if(packman.getLives()>=0) {
                    if(partidaOnline.getEstadoPartida() != PartidaOnline.EstadoPartida.ESPERANDO_JUGADORES) {
                        mainTheme.state=false;
                        startSound.play();
                        if(startSound.getCurrentTime().equals(startSound.getBufferProgressTime())) {
                            if(state!=0) {
                                if(mCurrentNanoTime-lastupdate>=222222222) {
                                    state=0;
                                }else {
                                    state=-1;
                                }
                            }
                            mainTheme.state=true;
                            fantasmasByDefault.forEach(f -> f.move(currentNanoTime));
                            packman.movePackman(input, packman, anchoSprite, up, altoSprite, down,mCurrentNanoTime);
                            try {
                                packman.checkCollisionOnline(packman,fantasmasByDefault, startSound, deadTime, jugador);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
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

                        bolita.eatingBol(packmanOnline, HEIGHT_SCREEN, WIDTH_SCREEN);
                        bolitaDefault.render(gc);
                        packman.render(gc);
                        fantasma.render(gc);
                        fantasma2.render(gc);
                        fantasma3.render(gc);
                        fantasma4.render(gc);
                        hud.renderHud(gc,packman,HEIGHT_SCREEN, WIDTH_SCREEN);

                        if(state ==-2) {
                            gc.setFill( Color.WHITE );
                            gc.fillText( "¿¡PREPARADO!?", HEIGHT_SCREEN/4, WIDTH_SCREEN/2);
                            lastupdate=mCurrentNanoTime;
                            startPartida=mCurrentNanoTime;
                        }

                        if(state ==-1) {
                            gc.setFill( Color.WHITE );
                            gc.fillText( "¡YA!", HEIGHT_SCREEN/2, WIDTH_SCREEN/2);
                        }
                    }else {
                        mainTheme.state=true;
                        fantasma.animation(currentNanoTime,"");
                        fantasma2.animation(currentNanoTime,"");
                        fantasma3.animation(currentNanoTime,"");
                        fantasma4.animation(currentNanoTime,"");
                        bolitaDefault.render(gc);
                        packman.render(gc);
                        fantasma.render(gc);
                        fantasma2.render(gc);
                        fantasma3.render(gc);
                        fantasma4.render(gc);
                        hud.renderHud(gc,packman,HEIGHT_SCREEN, WIDTH_SCREEN);
                        gc.setFill( Color.WHITE );
                        int time= (int) ((mCurrentNanoTime/100000000) % 8);
                        if(time> 1) {
                            gc.fillText( "ESPERANDO\nJUGADORES", HEIGHT_SCREEN/4, WIDTH_SCREEN/2);
                        }
                    }
                }
            }
        }.start();
        theStage.show();
    }

    public static void toMatchResumeScreen() {
            Parent root = null;
            try {
                root = FXMLLoader.load(EscenaComeOnline.class.getResource("../views/MatchResumeScreen.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            MyStage.getStage().setTitle("PacMan 2.0 - Resumen de partida");
            MyStage.getStage().setResizable(false);
            MyStage.getStage().setScene(new Scene(root, MyStage.ANCHO, MyStage.ALTURA));
            MyStage.setStage(MyStage.getStage());
            MyStage.getStage().show();
        }

    private PartidaOnline getPartidaOnline() {
        return partidaOnline;
    }

    private void getDataToRequest(byte[] data, int length) {
        setPartida(data);
    }

    private void setPartida(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            partidaOnline = (PartidaOnline) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}