package online;

import sprites.*;

import java.io.Serializable;
import java.util.ArrayList;

public class PartidaOnline implements Serializable {

    private static final long serialVersionUID = 0x1b32faab5902bfa9L;

    public enum EstadoPartida {
        ESPERANDO_JUGADORES, EMPEZADA, ACABADA
    }

    public  ArrayList<Jugador> playersInGame = new ArrayList<>();
    private ArrayList<FantasmaOnline> fantasmas = new ArrayList<>();
    private ArrayList<PackmanOnline> packmans = new ArrayList<>();
    private BolitaOnline bolita;
    private int bolitasJugadas = 0;
    private EstadoPartida estadoPartida = EstadoPartida.ESPERANDO_JUGADORES;
    private boolean firstConection = true;
    private int idEmisor;


    public PartidaOnline(Jugador jugador, ArrayList<FantasmaOnline> fantasmas, PackmanOnline packman, BolitaOnline bolita, int idEmisor ) {
        this.playersInGame.add(jugador);
        this.fantasmas.addAll(fantasmas);
        this.packmans.add(packman);
        this.bolita = bolita;
        this.idEmisor=idEmisor;
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

    public void setJugador(Jugador jugador,int id) {
        playersInGame.set(id,jugador);
    }

    public int getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(int idEmisor) {
        this.idEmisor = idEmisor;
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

    public void setPackman(PackmanOnline packman, int player) {
        packmans.set(player,packman);
    }

    public BolitaOnline getBolita() {
        return bolita;
    }

    public void setBolita(BolitaOnline bolita) {
        this.bolita = bolita;
    }

    public int getBolitasJugadas() {
        return bolitasJugadas;
    }

    public void setBolitasJugadas(int bolitasJugadas) {
        this.bolitasJugadas = bolitasJugadas;
    }
}
