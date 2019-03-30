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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sprites.Sprite2;

import java.util.ArrayList;

public class EscenaCome extends Application {

    private double x = 0;
    private double y = 0;
    double velocidad = 7;
    private final int ALTURA = 650;
    private final int ANCHO = 450;


    @Override
    public void start(Stage theStage) {
        Sprite2 sun = new Sprite2();
        sun.setImage(new Image("img/pacbol_0.png"));
        theStage.setTitle("Keyboard Example");
        ImageView iv = new ImageView("img/pacbol_0.png");

        Image left = new Image("img/pacbol_2.png");
        Image rigth = new Image("img/pacbol_1.png");
        Image up = new Image("img/pacbol_3.png");
        Image down = new Image("img/pacbol_4.png");


        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(ALTURA, ANCHO);
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


        Image leftG = new Image("img/sun.png");


        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // Clear the canvas
                gc.clearRect(0, y - 10, 1112, 512);

                    if (input.contains("LEFT") != input.contains("RIGHT")) {
                        if (input.contains("LEFT")) {
                            x -= velocidad;
                            sun.setImage(left);

                            //iv.setRotate(-90);

                        }
                        if (input.contains("RIGHT")) {
                            x += velocidad;
                            sun.setImage(rigth);

                            // iv.setRotate(90);

                        }
                    }
                    if (input.contains("UP") != input.contains("DOWN")) {
                        if (input.contains("UP")) {
                            y -= velocidad;
                            sun.setImage(up);
                            // iv.setRotate(0);

                        }
                        if (input.contains("DOWN")) {
                            y += velocidad;
                            sun.setImage(down);
                            //iv.setRotate(180);

                        }
                    }

                sun.render(gc);

                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                Image rotatedImage = iv.snapshot(params, null);
                //gc.drawImage(rotatedImage,x,y, 50,50);
                sun.update(0);
                sun.setPositionX(x);
                sun.setPositionY(y);
                // gc.drawImage(leftG, x, y);

            }
        }.start();

        theStage.show();
    }

}
