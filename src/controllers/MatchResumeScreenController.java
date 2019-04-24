package controllers;

import escenas.EscenaCome;
import escenas.MyStage;
import escenas.Partida;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.text.DecimalFormat;

public class MatchResumeScreenController {

    private double bestTime;
    private double worstTime;
    private int bestCatch;
    private int worstCatch;
    private DecimalFormat df = new DecimalFormat("#.00");


    @FXML
    TextArea bestMatch,worstMatch;

    @FXML
    Button BMainMenu,BTop10;

    public void initialize() {
        this.bestCatch = Partida.getBestCatch();
        this.bestTime = Partida.getBestTime();
        this.worstCatch = Partida.getWorstCatch();
        this.worstTime = Partida.getWorstTime();
        bestMatch.setText("Total bolitas capturadas: "+bestCatch+"\n\nTiempo realizado: "+df.format(bestTime)+" segundos");
        bestMatch.setEditable(false);
        worstMatch.setText("Total bolitas capturadas: "+worstCatch+"\n\nTiempo realizado: "+df.format(worstTime)+" segundos");
        worstMatch.setEditable(false);
    }

    public void toMainMenu() {
        Parent root = null;
        try {
            root = FXMLLoader.load(EscenaCome.class.getResource("../views/MainScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyStage.getStage().setTitle("PacMan 2.0");
        MyStage.getStage().setResizable(false);
        MyStage.getStage().setScene(new Scene(root, MyStage.ANCHO, MyStage.ALTURA));
        MyStage.setStage(MyStage.getStage());
        MyStage.getStage().show();
    }

    public void toTop10() {
        Parent root = null;
        try {
            root = FXMLLoader.load(EscenaCome.class.getResource("../views/Top10Screen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyStage.getStage().setTitle("PacMan 2.0");
        MyStage.getStage().setResizable(false);
        MyStage.getStage().setScene(new Scene(root, MyStage.ANCHO, MyStage.ALTURA));
        MyStage.setStage(MyStage.getStage());
        MyStage.getStage().show();
    }
}
