package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.EscenaSol;
import model.Sun;

public class Main extends Application {

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }


    public static void main(String[] args) {

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        EscenaSol escenaSol=new EscenaSol();

        escenaSol.start(primaryStage);

    }
}