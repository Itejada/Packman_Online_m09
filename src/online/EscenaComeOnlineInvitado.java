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

public class EscenaComeOnlineInvitado extends Scene {

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
    private boolean firstConection = true;
    //Detección de teclas pulsadas.
    ArrayList<String> input = new ArrayList<>();

    private PartidaOnline partidaOnline;
    private  InetAddress IP_SERVER;
    private final int PUERTO =5556;

    private MulticastSocket multisocket;
    private InetAddress multicastIP;

    byte [] receivedData = new byte[4096];
    DatagramPacket packet;
    DatagramSocket socket = new DatagramSocket();


    public EscenaComeOnlineInvitado(Group root, MainTheme mainTheme, InetAddress ip) throws IOException {
        super(root);
        this.root=root;
        this.mainTheme=mainTheme;
        this.mainTheme.state=false;
        this.IP_SERVER = ip;
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
        try {
            multisocket = new MulticastSocket(5557);
            multicastIP = InetAddress.getByName("224.0.0.10");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.start(MyStage.getStage());
    }

    public void start(Stage theStage) throws IOException {

        //Título de la ventana.
        theStage.setTitle("Pacman V2 - Modo Online");


        //Sonidos del mundo.
        String pathStartSound = "src/sounds/pacman_beginning.wav";
        Media media = new Media(new File(pathStartSound).toURI().toString());
        MediaPlayer startSound = new MediaPlayer(media);

        //Configuración del botón de música en el HUD.
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

        //Configuración del botón de sonidos en el HUD.
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

        //ArrayList de fantasmas por default (Son los que se dibujarán por pantalla)
        final ArrayList<Fantasma> fantasmasByDefault = new ArrayList<>();
        fantasmasByDefault.add(fantasma);
        fantasmasByDefault.add(fantasma2);
        fantasmasByDefault.add(fantasma3);
        fantasmasByDefault.add(fantasma4);

        //Fantasmas que se enviarán a la partida Online.
        FantasmaOnline fantasmaOnline = new FantasmaOnline(HEIGHT_SCREEN, WIDTH_SCREEN+WIDTH_SCREEN/9, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        FantasmaOnline fantasmaOnline2 = new FantasmaOnline(100, 200, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        FantasmaOnline fantasmaOnline3 = new FantasmaOnline(220, 53,velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN,22,22);
        FantasmaOnline fantasmaOnline4 = new FantasmaOnline(HEIGHT_SCREEN, 0,velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN,22,22);

        //ArrayList de fantasmas que se enviarán a partida online.
        final ArrayList<FantasmaOnline> fantasmasOnlineByDefault = new ArrayList<>();
        fantasmasOnlineByDefault.add(fantasmaOnline);
        fantasmasOnlineByDefault.add(fantasmaOnline2);
        fantasmasOnlineByDefault.add(fantasmaOnline3);
        fantasmasOnlineByDefault.add(fantasmaOnline4);

        //Packman del mundo
        Image image = new Image("img/pacbol_0.png");
        Packman myPacman = new Packman(image,x,y,velocidad,velocidad, WIDTH_SCREEN, HEIGHT_SCREEN,25,25, VIDAS,1);
        PacmanEatingTheme sound= new PacmanEatingTheme(myPacman);
        myPacman.setImage(image);
        sound.start();

        Packman rivalPacman = new Packman(image,x,y,velocidad,velocidad, WIDTH_SCREEN, HEIGHT_SCREEN,25,25, VIDAS,0);
        PacmanEatingTheme soundRival= new PacmanEatingTheme(rivalPacman);
        rivalPacman.setImage(image);
        soundRival.start();

        //Pacman Online 1.
        PackmanOnline myPacmanOnline = new PackmanOnline(input,x,y,velocidad,velocidad, WIDTH_SCREEN, HEIGHT_SCREEN,25,25, VIDAS,1);
        int anchoSprite = (int) myPacman.getWIDTH_PACMAN();
        int altoSprite = (int) myPacman.getHEIGHT_PACMAN();

        PackmanOnline rivalPacmanOnline = new PackmanOnline(input,x,y,velocidad,velocidad, WIDTH_SCREEN, HEIGHT_SCREEN,25,25, VIDAS,0);

        //Bolita del mundo.
        Bolita bolitaDefault = new Bolita((Math.random()*(HEIGHT_SCREEN-(100)))+50, (Math.random()*(WIDTH_SCREEN-(100)))+50, 15, 15);

        //Bolita que se enviará por partida Online.
        BolitaOnline bolitaOnline = new BolitaOnline((Math.random()*(HEIGHT_SCREEN-(100)))+50, (Math.random()*(WIDTH_SCREEN-(100)))+50, 15, 15);

        //Jugador del mundo
        Jugador jugador = new Jugador(false);
        partidaOnline = new PartidaOnline(jugador,fantasmasOnlineByDefault,myPacmanOnline,bolitaOnline);
        //Imagenes del pacman por default.
        Image front = new Image("img/pacbol_0.png");
        Image up = new Image("img/pacbol_up_1.png");
        Image down = new Image("img/pacbol_down_1.png");

        //Canvas
        Canvas canvas = new Canvas(HEIGHT_SCREEN, WIDTH_SCREEN);
        root.getChildren().addAll(canvas,musicIcon,soundIcon);



        this.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();

                    // only add once... prevent duplicates
                    if (!input.contains(code))
                        input.add(code);
                        myPacmanOnline.setInput(input);
                });

        this.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    myPacman.isPlaying=false;
                    input.remove(code);
                    myPacman.setImage(front);
                    myPacmanOnline.setInput(input);
                });


        //GraficContext para renderizar gráficos.
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //########################## Animacion ###########################
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.setFill( Color.RED );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);

        //AnimationTimer encargado de dibujar los gráficos por pantalla.
        new AnimationTimer() {
            public void handle(long currentNanoTime) {

                    //Enviamos la partida online al servidor.
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    ObjectOutputStream oos = null;
                    try {
                        oos = new ObjectOutputStream(os);
                        oos.writeObject(partidaOnline);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    byte[] missatge = os.toByteArray();

                    //creació del paquet a enviar
                    packet = new DatagramPacket(missatge, missatge.length, IP_SERVER, PUERTO);
                    //creació d'un sòcol temporal amb el qual realitzar l'enviament
                    //socket = new DatagramSocket();
                    //Enviament del missatge
                    try {
                        socket.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                //Creamos paquete para recibir información de la partida del servidor.
                packet = new DatagramPacket(receivedData, receivedData.length);
                //espera de les dades
                try {
                    socket.setSoTimeout(5000);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                try {
                    socket.receive(packet);
                    ////processament de les dades rebudes i obtenció de la resposta
                    partidaOnline=getDataToRequest(packet.getData(), packet.getLength());
                    if(partidaOnline.getPlayersInGame().size()==1) {
                        partidaOnline.addPlayer(jugador);
                    }
                   partidaOnline.setEstadoPartida(PartidaOnline.EstadoPartida.EMPEZADA);

                }catch(SocketTimeoutException e) {
                    System.out.println("El servidor no respòn: " + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //ArrayList de fantasmasOnline (Son los que recibimos de la partida)
                ArrayList<FantasmaOnline> fantasmas = partidaOnline.getFantasmas();

                //Seteamos los fantasmas que se dibujan por pantalla a los que importamos de la partida.
                for(int i=0;i<fantasmas.size();i++) {
                    fantasmasByDefault.get(i).setPosX(fantasmas.get(i).getPosX());
                    fantasmasByDefault.get(i).setPosY(fantasmas.get(i).getPosY());
                }

                //Configuración del volumen del juego (Por si lo hemos desactivado ingame)
                if(!ConfigurationGame.isVolumeDisabled()) {
                    startSound.setVolume(ConfigurationGame.getVolume()/2);
                }else {
                    startSound.setVolume(0);
                }
                //Actualizamos tiempo, y seteamos el tiempo de muerte (Por si muere nuestro packman saber en que tiempo)
                mCurrentNanoTime=currentNanoTime;
                deadTime =mCurrentNanoTime-startPartida;

                // Clear the canvas
                gc.clearRect(0, 0, HEIGHT_SCREEN, WIDTH_SCREEN);
                gc.setFill(Color.BLUE);
                gc.fillRect(0, canvas.getHeight()/10, canvas.getWidth(), canvas.getHeight());

                //Si nuestro packman tiene >= 0 vidas,podremos seguir jugando.
                if(myPacman.getLives()>=0) {
                    //Si el estado de la partida, no es Esperando jugadores.
                    System.out.println(partidaOnline.getEstadoPartida());
                    if(partidaOnline.getEstadoPartida() != PartidaOnline.EstadoPartida.ESPERANDO_JUGADORES) {
                        //Pararemos el sonido del maintheme. (Para hacer sonar el startSound)
                        mainTheme.state=false;
                        startSound.play();

                        //Comprobamos si el startSound ya ha finalizado.
                        if(startSound.getCurrentTime().equals(startSound.getBufferProgressTime())) {
                            //Si ha finalizado, miraremos el estado para mostrar un aviso u otro.
                            if(state!=0) {
                                if(mCurrentNanoTime-lastupdate>=222222222) {
                                    state=0;
                                }else {
                                    state=-1;
                                }
                            }
                            //Volvemos a reproducir el mainTheme.
                            mainTheme.state=true;

                            //Movemos todos los fantasmas por pantalla.
                            fantasmasByDefault.forEach(f -> f.move(currentNanoTime));

                            //Actualizamos los fantasmas Online y le seteamos los valores obtenidos del servidor.
                            for(int i=0;i<fantasmasByDefault.size();i++) {
                                fantasmasOnlineByDefault.get(i).setPosX(fantasmasByDefault.get(i).getPosX());
                                fantasmasOnlineByDefault.get(i).setPosY(fantasmasByDefault.get(i).getPosY());
                                fantasmasOnlineByDefault.get(i).setDirX(fantasmasByDefault.get(i).getDirX());
                                fantasmasOnlineByDefault.get(i).setDirY(fantasmasByDefault.get(i).getDirY());
                            }

                            //Movemos packman por pantalla.
                            myPacman.movePackman(input, myPacman, anchoSprite, up, altoSprite, down,mCurrentNanoTime);
                            rivalPacman.movePackman(partidaOnline.getPackmans().get(0).getInput(),rivalPacman,anchoSprite,up,altoSprite,down,mCurrentNanoTime);

                            //Actualizamos el packman online con las nuevas posiciones/estado.
                            myPacmanOnline.setPositionX(myPacman.getPositionX());
                            myPacmanOnline.setPositionY(myPacman.getPositionY());
                            myPacmanOnline.setLives(myPacman.getLives());
                            myPacmanOnline.setScore(myPacman.getScore());

                            rivalPacmanOnline.setPositionX(partidaOnline.getPackmans().get(0).getPositionX());
                            rivalPacmanOnline.setPositionY(partidaOnline.getPackmans().get(0).getPositionX());
                            rivalPacmanOnline.setLives(partidaOnline.getPackmans().get(0).getLives());
                            rivalPacmanOnline.setScore(partidaOnline.getPackmans().get(0).getScore());

                            //Comprobamos si el packman colisiona con algun objeto.
                            try {
                                myPacman.checkCollisionOnline(myPacman,fantasmasByDefault, startSound, deadTime, jugador);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        //Si la música startSound no ha acabado, seteamos el state a -2, paramos el mainTheme
                        //Y creamos la animacion de los fantasmas parados.
                        else {
                            state=-2;
                            mainTheme.state=false;
                            fantasmasByDefault.forEach(f -> f.animation(currentNanoTime,""));
                        }

                        //Comprobamos si el packman se ha comido a la bolita.
                        if(bolitaOnline.eatingBol(myPacmanOnline)) {

                            //Si nos la hemos comido, le pondremos un nuevo respawn.
                           bolitaOnline.setRespawn(HEIGHT_SCREEN, WIDTH_SCREEN);

                           //A la bolita gráfica, le pondremos los nuevos  valores X/Y.
                           bolitaDefault.setX(bolitaOnline.getX());
                           bolitaDefault.setY(bolitaOnline.getY());
                           myPacman.setScore(myPacman.getScore()+1);

                            //Comprobaremos si hemos sumado 10 capturas, para ganar una vida.
                            if ((myPacman.getScore()%10)==0){
                                myPacman.setLives(myPacman.getLives()+1);
                            }
                        }

                        //Renderizamos todos los objetos por pantalla.
                        bolitaDefault.render(gc);
                        myPacman.render(gc);
                        fantasmasByDefault.forEach(f -> f.render(gc));
                        hud.renderHud(gc,myPacman,HEIGHT_SCREEN, WIDTH_SCREEN);

                        //Comprobamos el estado de la partida, para mostrar un mensaje u otro.
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
                    }
                    //Si el estado de la partida es esperando jugadores...
                    else {
                        //Reproduciremos el mainTheme, la animación de los fantasmas.
                        mainTheme.state=true;

                        //Renderizaremos todos los elementos por pantalla.
                        fantasmasByDefault.forEach(f -> f.animation(currentNanoTime,""));
                        bolitaDefault.render(gc);
                        myPacman.render(gc);
                        fantasma.render(gc);
                        fantasma2.render(gc);
                        fantasma3.render(gc);
                        fantasma4.render(gc);
                        hud.renderHud(gc,myPacman,HEIGHT_SCREEN, WIDTH_SCREEN);

                        //Mostraremos el mensaje de esperando jugadores.
                        gc.setFill( Color.WHITE );
                        int time= (int) ((mCurrentNanoTime/100000000) % 8);
                        if(time> 1) {
                            gc.fillText( "ESPERANDO\nJUGADORES", HEIGHT_SCREEN/4, WIDTH_SCREEN/2);
                        }
                    }

                    //Actualizamos toda la partida para enviarla al servidor.

                    partidaOnline.setFantasmas(fantasmasOnlineByDefault);
                    partidaOnline.addPacman2(myPacmanOnline);
                    partidaOnline.getPackmans().get(0).setPositionX(myPacmanOnline.getPositionX());
                    partidaOnline.getPackmans().get(0).setPositionY(myPacmanOnline.getPositionY());
                    partidaOnline.getPackmans().get(0).setLives(myPacmanOnline.getLives());
                    partidaOnline.getPackmans().get(0).setScore(myPacmanOnline.getScore());
                    partidaOnline.getPackmans().get(1).setPositionX(rivalPacmanOnline.getPositionX());
                    partidaOnline.getPackmans().get(1).setPositionY(rivalPacmanOnline.getPositionY());
                    partidaOnline.getPackmans().get(1).setLives(rivalPacmanOnline.getLives());
                    partidaOnline.getPackmans().get(1).setScore(rivalPacmanOnline.getScore());
                    partidaOnline.setBolita(bolitaOnline);
                }
            }
        }.start();
        theStage.show();
    }

    public static void toMatchResumeScreen() {
            Parent root = null;
            try {
                root = FXMLLoader.load(EscenaComeOnlineInvitado.class.getResource("../views/MatchResumeScreen.fxml"));
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

    private PartidaOnline getDataToRequest(byte[] data, int length) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            partidaOnline = (PartidaOnline) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return partidaOnline;
    }
}