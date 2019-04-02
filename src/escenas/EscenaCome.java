package escenas;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Fantasma;
import sprites.Sprite2;

import java.io.File;
import java.util.ArrayList;

public class EscenaCome extends Application {

    private double x = 0;
    private double y = 0;
    double velocidad = 4;
    private static final int ANCHO = 650;
    private static final int ALTURA = 450;
    Image[] FrameRigth = new Image[6];



    @Override
    public void start(Stage theStage) {

        for(int i=1;i<7;i++) {
            FrameRigth[i-1] = new Image("img/pacbol_0"+i+".png");
        }

        String pathStartSound = "../sounds/pacman_beginning.wav";
        Media media = new Media(new File(pathStartSound).toURI().toString());
        MediaPlayer startSound = new MediaPlayer(media);
        startSound.setAutoPlay(true);

        Image fimage = new Image("img/fantasma0_0.png");
        Fantasma fantasma = new Fantasma(fimage,0,0, ANCHO,ALTURA );
        Fantasma fantasma2 = new Fantasma(fimage,ANCHO,ALTURA, ANCHO,ALTURA );


        Sprite2 sprite2 = new Sprite2();
        sprite2.setHeight(28);
        sprite2.setWidth(29);
        int anchoSprite = (int) sprite2.getWidth();
        int altoSprite = (int) sprite2.getHeight();

        sprite2.setImage(new Image("img/pacbol_00.png"));
        theStage.setTitle("Keyboard Example");
        //  ImageView iv = new ImageView("img/pacbol_0.png");

        Image front = new Image("img/pacbol_00.png");
        Image left = new Image("img/pacbol_07.png");
        Image rigth = new Image("img/pacbol_01.png");
        Image up = new Image("img/pacbol_up_1.png");
        Image down = new Image("img/pacbol_down_1.png");


        Group root = new Group();
        Scene theScene = new Scene(root, ANCHO, ALTURA);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(ANCHO, ALTURA);
        root.getChildren().add(canvas);

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
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();



        int i=0;

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                long residuo = ( currentNanoTime / 100000000) %6;
                // Clear the canvas
                gc.clearRect(0, 0, ANCHO, ALTURA);
                gc.setFill(Color.BLUE);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                sprite2.setImage(front);
                fantasma.setImage(fimage);
                fantasma2.setImage(fimage);

                sprite2.setPositionX(x);
                sprite2.setPositionY(y);
                fantasma.move();


                if(sprite2.getBoundary().intersects(fantasma.getBoundary())) {

                }
                if (input.contains("LEFT") != input.contains("RIGHT")) {
                    if (input.contains("LEFT")) {
                        if (x > 0) {
                            x -= velocidad;
                            System.out.println(residuo);
                            sprite2.setImage(FrameRigth[(int)residuo]);
                        } else {
                            x = 0;
                        }
                    }

                    if (input.contains("RIGHT")) {
                        if (x < ANCHO - anchoSprite) {
                            x += velocidad;
                            sprite2.setImage(rigth);
                            // iv.setRotate(90);
                        } else {
                            x = ANCHO - anchoSprite;
                        }
                    }
                }//hasta qui el if de izquierda/derecha


                //en este if compruebo si el arriba/abajo, no estan pulsados a la vez
                if (input.contains("UP") != input.contains("DOWN")) {
                    if (input.contains("UP")) {
                        if (y > 0) {
                            y -= velocidad;
                            sprite2.setImage(up);
                            // iv.setRotate(0);
                        } else {
                            y = 0;
                        }
                    }
                    if (input.contains("DOWN")) {
                        if (y < ALTURA - altoSprite) {
                            y += velocidad;
                            sprite2.setImage(down);
                            //iv.setRotate(180);
                        } else {
                            y = ALTURA - altoSprite;
                        }

                    }
                }//hasta aqui el if de arriba/abajo


                sprite2.render(gc);
                fantasma.render(gc);


                // gc.drawImage(leftG, x, y);

            }
        }.start();

        theStage.show();

    }



}
