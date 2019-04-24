package controllers;

import escenas.EscenaCome;
import escenas.MyStage;
import escenas.Partida;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Top10ScreenController {


    @FXML
    private Text nombre1,nombre2,nombre3,nombre4,nombre5,nombre6,nombre7,nombre8,nombre9,nombre10;

    @FXML
    private Text capturas1,capturas2,capturas3,capturas4,capturas5,capturas6,capturas7,capturas8,capturas9,capturas10;

    @FXML
    private Text tiempo1,tiempo2,tiempo3,tiempo4,tiempo5,tiempo6,tiempo7,tiempo8,tiempo9,tiempo10;

    @FXML
    private Text[] jugadoresTop10= new Text[10];

    @FXML
    private Text[] capturasTop10 = new Text[10];

    @FXML
    private Text[] tiemposTop10 = new Text[10];

    private DecimalFormat df = new DecimalFormat("#0.00");


    public void initialize() {
        ArrayList<String> top10 = Partida.getPartidasTop10();
        jugadoresTop10 = new Text[]{ nombre1,nombre2,nombre3,nombre4,nombre5,nombre6,nombre7,nombre8,nombre9,nombre10};
        capturasTop10 = new Text[]{capturas1,capturas2,capturas3,capturas4,capturas5,capturas6,capturas7,capturas8,capturas9,capturas10};
        tiemposTop10 = new Text[] {tiempo1,tiempo2,tiempo3,tiempo4,tiempo5,tiempo6,tiempo7,tiempo8,tiempo9,tiempo10};
        for (int i=0;i<=9;i++) {
            String[] linea = top10.get(i).split(",");
            String number = df.format(Float.valueOf(linea[2])/60);
            String time = number;
            time = time.replace(","," minutos ");
            time = time + " segundos";
            jugadoresTop10[i].setText(linea[0]);
            capturasTop10[i].setText(linea[1]);
            tiemposTop10[i].setText(time);
        }
    }

    public void toMainMenu() throws IOException {
        Parent root = FXMLLoader.load(EscenaCome.class.getResource("../views/MainScreen.fxml"));
        MyStage.getStage().setTitle("PacMan 2.0");
        MyStage.getStage().setResizable(false);
        MyStage.getStage().setScene(new Scene(root, MyStage.ANCHO, MyStage.ALTURA));
        MyStage.setStage(MyStage.getStage());
        MyStage.getStage().show();
    }

}