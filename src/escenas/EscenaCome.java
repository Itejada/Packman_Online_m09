package escenas;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sprites.Sprite2;

import java.util.ArrayList;

public class EscenaCome extends Application {

    private double x = 0;
    private double y = 0;
    double velocidad = 0.5;



    @Override
    public void start(Stage theStage) {
        Sprite2 sun= new Sprite2();
        sun.setImage(new Image("img/sun.png"));
        theStage.setTitle("Keyboard Example");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(1112 - 64, 956);
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
                gc.clearRect(0, y-10, 1112, 512);

                if (input.contains("LEFT") != input.contains("RIGHT")) {
                    if (input.contains("LEFT")) {
                        x -= velocidad;
                        sun.update(2);
                    }
                    if (input.contains("RIGHT")) {
                        x += velocidad;
                        sun.update(1);
                    }
                }
                if (input.contains("UP") != input.contains("DOWN")) {
                    if (input.contains("UP")) {
                        y -= velocidad;
                        sun.update(1);
                    }
                    if (input.contains("DOWN")) {
                        y += velocidad;
                        sun.update(1);
                    }
                }
                    sun.render(gc);
                sun.update(0);
                sun.setPositionX(x);
                sun.setPositionY(y);
                   // gc.drawImage(leftG, x, y);

            }
        }.start();

        theStage.show();
    }

}
