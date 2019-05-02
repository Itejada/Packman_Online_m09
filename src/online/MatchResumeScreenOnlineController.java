package online;

import escenas.EscenaCome;
import escenas.MyStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MatchResumeScreenOnlineController {

    @FXML
    TextArea bestMatch,worstMatch,winner;

    @FXML
    Button BMainMenu;

    private int bolitasJ1;
    private int bolitasJ2;

    public void initialize() {
        ArrayList<Jugador> playersInGame;
        ArrayList<Double> bestTime = new ArrayList<>();
        ArrayList<Double> worstTime = new ArrayList<>();
        ArrayList<Integer> bestCatch = new ArrayList<>();
        ArrayList<Integer> worstCatch = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#0.00");

        playersInGame=FinalResultadoPartida.getPlayersInGame();
        bestCatch.add(0,playersInGame.get(0).getBestCatch());
        bestCatch.add(1,playersInGame.get(1).getBestCatch());
        bestTime.add(0,playersInGame.get(0).getBestTime());
        bestTime.add(1,playersInGame.get(1).getBestTime());
        worstCatch.add(0,playersInGame.get(0).getWorstCatch());
        worstCatch.add(1,playersInGame.get(1).getWorstCatch());
        worstTime.add(0,playersInGame.get(0).getWorstTime());
        worstTime.add(1,playersInGame.get(1).getWorstTime());

        //Player1 stats
        String playerName1 = playersInGame.get(0).getName();
        String bestTimeMinutesFormatJ1 = df.format(bestTime.get(0)/60);
        String bestTimeMinutesJ1 = bestTimeMinutesFormatJ1;
        bestTimeMinutesJ1 = bestTimeMinutesJ1.replace(","," minutos ");
        bestTimeMinutesJ1 = bestTimeMinutesJ1 + " segundos";
        String worstTimeMinutesFormatJ1 = df.format(worstTime.get(0)/60);
        String worstTimeMinutesJ1 = worstTimeMinutesFormatJ1;
        worstTimeMinutesJ1 = worstTimeMinutesJ1.replace(","," minutos ");
        worstTimeMinutesJ1 = worstTimeMinutesJ1 + " segundos";


        //Player2 stats
        String playerName2 = playersInGame.get(1).getName();
        String bestTimeMinutesFormatJ2 = df.format(bestTime.get(1)/60);
        String bestTimeMinutesJ2 = bestTimeMinutesFormatJ2;
        bestTimeMinutesJ2 = bestTimeMinutesJ2.replace(","," minutos ");
        bestTimeMinutesJ2 = bestTimeMinutesJ2 + " segundos";
        String worstTimeMinutesFormat = df.format(worstTime.get(1)/60);
        String worstTimeMinutesJ2 = worstTimeMinutesFormat;
        worstTimeMinutesJ2 = worstTimeMinutesJ2.replace(","," minutos ");
        worstTimeMinutesJ2 = worstTimeMinutesJ2 + " segundos";

        if(bestCatch.get(0)>bestCatch.get(1)) {
            bestMatch.setText("Nombre jugador: "+playerName1+"\n\nTotal bolitas capturadas: "+bestCatch.get(0)+"\n\nTiempo realizado: "+bestTimeMinutesJ1);
        }else if(bestCatch.get(0) == bestCatch.get(1)) {
            if(bestTime.get(0)<bestTime.get(1)) {
                bestMatch.setText("Nombre jugador: "+playerName1+"\n\nTotal bolitas capturadas: "+bestCatch.get(0)+"\n\nTiempo realizado: "+bestTimeMinutesJ1);
            }else {
                bestMatch.setText("Nombre jugador: "+playerName2+"\n\nTotal bolitas capturadas: "+bestCatch.get(1)+"\n\nTiempo realizado: "+bestTimeMinutesJ2);
            }
        }else {
            bestMatch.setText("Nombre jugador: "+playerName2+"\n\nTotal bolitas capturadas: "+bestCatch.get(1)+"\n\nTiempo realizado: "+bestTimeMinutesJ2);
        }
        bestMatch.setEditable(false);

        if(worstCatch.get(0)<worstCatch.get(1)) {
            worstMatch.setText("Nombre jugador: "+playerName1+"\n\nTotal bolitas capturadas: "+worstCatch.get(0)+"\n\nTiempo realizado: "+worstTimeMinutesJ1);
        }else if(worstCatch.get(0) == worstCatch.get(1)) {
            if(worstCatch.get(0)>worstCatch.get(1)) {
                worstMatch.setText("Nombre jugador: "+playerName1+"\n\nTotal bolitas capturadas: "+worstCatch.get(0)+"\n\nTiempo realizado: "+worstTimeMinutesJ1);
            }else {
                worstMatch.setText("Nombre jugador: "+playerName2+"\n\nTotal bolitas capturadas: "+worstCatch.get(1)+"\n\nTiempo realizado: "+worstTimeMinutesJ2);
            }
        }else {
            worstMatch.setText("Nombre jugador: "+playerName2+"\n\nTotal bolitas capturadas: "+worstCatch.get(1)+"\n\nTiempo realizado: "+worstTimeMinutesJ2);
        }
        worstMatch.setEditable(false);


        for(int i=0;i<=EscenaComeOnline.VIDAS;i++) {
            bolitasJ1 = bolitasJ1+playersInGame.get(0).getBolitasCapturadas()[i];
            bolitasJ2 = bolitasJ2+playersInGame.get(1).getBolitasCapturadas()[i];
        }

        System.out.println(bolitasJ1);
        System.out.println(bolitasJ2);
        if(bolitasJ1>bolitasJ2) {
            winner.setText("El ganador es "+playerName1+" con "+bolitasJ1+" capturadas." );
        }else if(bolitasJ1==bolitasJ2) {
            winner.setText("EMPATE.\nAmbos jugadoes han capturado las mismas bolitas.");
        }else {
            winner.setText("El ganador es "+playerName2+" con "+bolitasJ2+" capturadas." );
        }
        winner.setEditable(false);
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
