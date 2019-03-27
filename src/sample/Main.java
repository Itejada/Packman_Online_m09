package sample;

import escenas.EscenaCome;
import javafx.application.Application;
import javafx.stage.Stage;
import escenas.EscenaSol;

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
        EscenaCome escenaCome= new EscenaCome();

        escenaCome.start(primaryStage);

    }
}