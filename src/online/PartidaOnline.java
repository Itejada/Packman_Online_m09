package online;

import sprites.*;

import java.io.Serializable;
import java.util.ArrayList;

public class PartidaOnline implements Serializable {

    private static final long serialVersionUID = 0x1b32faab5902bfa9L;

    public enum EstadoPartida {
        ESPERANDO_JUGADORES, EMPEZADA, ACABADA
    }

    private ArrayList<Jugador> playersInGame = new ArrayList<>();
    private ArrayList<FantasmaOnline> fantasmas = new ArrayList<>();
    private ArrayList<PackmanOnline> packmans = new ArrayList<>();
    private BolitaOnline bolita;
    private EstadoPartida estadoPartida = EstadoPartida.ESPERANDO_JUGADORES;
    private boolean firstConection = true;


    public PartidaOnline(Jugador jugador, ArrayList<FantasmaOnline> fantasmas, PackmanOnline packman, BolitaOnline bolita ) {
        this.playersInGame.add(jugador);
        this.fantasmas.addAll(fantasmas);
        this.packmans.add(packman);
        this.bolita = bolita;
    }

    public PartidaOnline() {
        estadoPartida = EstadoPartida.ESPERANDO_JUGADORES;
    }

    public boolean isFirstConection() {
        return firstConection;
    }

    public void setFirstConection(boolean firstConection) {
        this.firstConection = firstConection;
    }

    public void addPlayer(Jugador jugador) {
        this.playersInGame.add(jugador);
        if(this.playersInGame.size()>=2) {
            this.estadoPartida = EstadoPartida.EMPEZADA;
        }
    }

    public EstadoPartida getEstadoPartida() {
        return estadoPartida;
    }

    public void addPacman2(PackmanOnline packmanOnline) {
        this.packmans.add(1,packmanOnline);
    }

    public void setEstadoPartida(EstadoPartida estadoPartida) {
        this.estadoPartida=estadoPartida;
    }

    public ArrayList<Jugador> getPlayersInGame() {
        return playersInGame;
    }

    public void setPlayersInGame(ArrayList<Jugador> playersInGame) {
        this.playersInGame = playersInGame;
    }

    public ArrayList<FantasmaOnline> getFantasmas() {
        return fantasmas;
    }

    public void setFantasmas(ArrayList<FantasmaOnline> fantasmas) {
        this.fantasmas = fantasmas;
    }

    public ArrayList<PackmanOnline> getPackmans() {
        return packmans;
    }

    public void setPackmans(ArrayList<PackmanOnline> packman) {
        this.packmans = packman;
    }

    public BolitaOnline getBolita() {
        return bolita;
    }

    public void setBolita(BolitaOnline bolita) {
        this.bolita = bolita;
    }


}
