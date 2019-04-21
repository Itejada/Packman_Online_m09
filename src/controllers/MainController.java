package controllers;

import escenas.EscenaCome;
import escenas.MyStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sounds.MainTheme;

import java.io.IOException;


public class MainController {


    @FXML
    Button botonEmpezarPartida,botonOpciones,botonSalir;

    private Stage mystage;
    private static MainTheme mainTheme = new MainTheme();


    public static void run() {
        mainTheme.start();
    }

    public void empezarPartida() {
        Group root = new Group();
        mystage = MyStage.getStage();
        mystage.setTitle("PacMan V2 - En Partida");
        mystage.setScene(new EscenaCome(root,mainTheme));
        MyStage.setStage(mystage);
        mystage.show();
    }

    public void configurarOpciones() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/ConfigurationScreen.fxml"));
        mystage = MyStage.getStage();
        mystage.setTitle("PacMan 2.0 - Configuración");
        MyStage.getStage().setScene(new Scene(root, MyStage.ANCHO, MyStage.ALTURA));
        MyStage.setStage(mystage);
        mystage.show();
    }

    public void salirJuego(){
        mystage = MyStage.getStage();
        mystage.close();
    }
}
