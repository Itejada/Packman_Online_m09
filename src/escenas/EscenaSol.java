package escenas;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Sun;

public class EscenaSol extends Application {
    @Override
    public void start(Stage theStage) {
        theStage.setTitle("Timeline Example");

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(1400, 900);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image earth = new Image("img/sun.png");
        Image sun = new Image("img/sun.png");
        Image space = new Image("img/space.jpg");
        Sun sun1= new Sun(sun,220,225);

        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 4000000000.0;
                double t2 = (currentNanoTime - startNanoTime) / 2000000000.0;



                double x = sun1.getPosX() +(260 +sun.getWidth())/2 * Math.cos(t) ;
                double y = sun1.getPosY() +(130+sun.getHeight()/2) * Math.sin(t);

                double xd = 220+ 100 * Math.cos(t2) ;
                double yd =  550+ 100 * Math.sin(t2);


                // background image clears canvas
                gc.drawImage(space, 0, 0);
                gc.drawImage(earth, x, y);

                sun1.setPosX(yd);
                sun1.setPosY(xd);
                sun1.render(gc);


                // gc.drawImage(sun, 550, 225);
            }
        }.start();

        theStage.show();
    }
}
