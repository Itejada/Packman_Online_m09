package escenas;

import config.ConfigurationGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainScreen extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigurationGame configuration = new ConfigurationGame();
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        primaryStage.setTitle("PacMan 2.0");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, MyStage.ANCHO, MyStage.ALTURA));
        MyStage.setStage(primaryStage);
        primaryStage.show();
    }
}
