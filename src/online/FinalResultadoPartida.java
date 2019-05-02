package online;

import java.util.ArrayList;

public class FinalResultadoPartida {

    public static ArrayList<Jugador> getPlayersInGame() {
        return playersInGame;
    }

    public static void setPlayersInGame(ArrayList<Jugador> playersInGame) {
        FinalResultadoPartida.playersInGame = playersInGame;
    }

    public static ArrayList<Jugador> playersInGame;


}
