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
import sprites.Fantasma;
import sprites.Sprite2;

import java.io.File;
import java.util.ArrayList;

public class EscenaCome extends Application {

    private double x = 0;
    private double y = 0;
    double velocidad = 4;
    private static final int ANCHO = 650;
    private static final int ALTURA = 450;
    Image[] framesR = new Image[6];
    Image[] framesL = new Image[6];

    long mCurrentNanoTime=0;

    @Override
    public void start(Stage theStage) {

        String pathStartSound = "../sounds/pacman_beginning.wav";
        Media media = new Media(new File(pathStartSound).toURI().toString());
        MediaPlayer startSound = new MediaPlayer(media);
        startSound.setAutoPlay(true);

        Image fimage = new Image("img/fantasma0_0.png");
        Fantasma fantasma = new Fantasma(fimage, ANCHO, ALTURA, ANCHO, ALTURA, 50,50);

        Fantasma fantasma2 = new Fantasma(fimage, 100, 200, ANCHO, ALTURA, 50,50);

        Fantasma fantasma3 = new Fantasma(fimage, 20, 33, ANCHO, ALTURA,50,50);

        Fantasma fantasma4 = new Fantasma(fimage, 45, 120, ANCHO, ALTURA,50,50);



        Sprite2 sprite2 = new Sprite2();
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

        ArrayList<String> input = new ArrayList<String>();

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

                fantasma.move(currentNanoTime);
                fantasma2.move(currentNanoTime);
                fantasma3.move(currentNanoTime);
                fantasma4.move(currentNanoTime);

                movePackman(input, sprite2, anchoSprite, up, altoSprite, down);

                checkCollision(sprite2, fantasma);
                checkCollision(sprite2, fantasma2);
                checkCollision(sprite2, fantasma3);
                checkCollision(sprite2, fantasma4);

                fantasma.render(gc);
                fantasma2.render(gc);
                fantasma3.render(gc);
                fantasma4.render(gc);
                sprite2.render(gc);


            }
        }.start();

        theStage.show();
    }


    private void movePackman(ArrayList<String> input, Sprite2 sprite2, int anchoSprite, Image up, int altoSprite, Image down) {

        if (input.contains("LEFT") != input.contains("RIGHT")) {
            if (input.contains("LEFT")) {
                if (x > 0) {
                    x -= velocidad;
                    animation(sprite2, "LEFT");
                } else {
                    x = 0;
                }
            }

            if (input.contains("RIGHT")) {
                if (x < ANCHO - anchoSprite) {
                    x += velocidad;
                    animation( sprite2, "RIGHT");
                } else {
                    x = ANCHO - anchoSprite;
                }
            }
        }

        //en este if compruebo si el arriba/abajo, no estan pulsados a la vez
        if (input.contains("UP") != input.contains("DOWN")) {
            if (input.contains("UP")) {
                if (y > 0) {
                    y -= velocidad;
                    sprite2.setImage(up);
                } else {
                    y = 0;
                }
            }
            if (input.contains("DOWN")) {
                if (y < ALTURA - altoSprite) {
                    y += velocidad;
                    sprite2.setImage(down);
                } else {
                    y = ALTURA - altoSprite;
                }

            }
        }
        sprite2.setPositionX(x);
        sprite2.setPositionY(y);

    }


    private void checkCollision(Sprite2 sprite2, Fantasma fantasma) {
//        if (sprite2.getBoundary().intersects(fantasma.getBoundary())) {
//            System.out.println("x_x");
//        }

        if((sprite2.getWidth()/2)+ (fantasma.getHeight()/2) > sprite2.distancia(fantasma.getPosX(), fantasma.getPosY())){
            System.out.println("x_x");
        }

    }



    /* por si no entiendes algo te dejo link
     * https://stackoverflow.com/questions/30146560/how-to-change-animationtimer-speed*/
    private void animation(  Sprite2 sprite2, String dir) {

            //aqui el metodo a actualizar
            int time= (int) ((mCurrentNanoTime/100000000) % 6);
            if (dir == "LEFT") {
                sprite2.setImage(framesL[time]);
            }else{
                sprite2.setImage(framesR[time]);
            }
    }


}