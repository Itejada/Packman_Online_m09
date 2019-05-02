package controllers;

import escenas.MyStage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import online.EscenaComeOnline;
import online.PartidaOnline;
import online.ServidorAdivinaUDP_Obj;
import sounds.MainTheme;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;


public class MainScreenOnlineController {


    private Stage mystage;
    private static MainTheme mainTheme = new MainTheme();


    public void crearPartida() throws IOException {
        Group root = new Group();
        mystage = MyStage.getStage();
        mystage.setTitle("PacMan V2");
        mystage.setScene(new EscenaComeOnline(root,mainTheme,true,null));
        MyStage.setStage(mystage);
        mystage.show();
    }

    public void unirsePartida() throws IOException {
        TextInputDialog textInputDialog = new TextInputDialog("");
        textInputDialog.setTitle("IP del servidor");
        textInputDialog.setHeaderText("Introduce ip delservidor");
        textInputDialog.setContentText("IP: ");

        Optional<String> result = textInputDialog.showAndWait();
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(result.get());
        }catch (Exception ex) {
            unirsePartida();
        }

        Group root = new Group();
        mystage = MyStage.getStage();
        mystage.setTitle("PacMan V2");
        mystage.setScene(new EscenaComeOnline(root,mainTheme,false,ip));
        MyStage.setStage(mystage);
        mystage.show();
    }

    public void volver() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        mystage = MyStage.getStage();
        mystage.setTitle("PacMan 2.0");
        MyStage.getStage().setScene(new Scene(root, MyStage.ANCHO, MyStage.ALTURA));
        MyStage.setStage(mystage);
        mystage.show();
    }
}
