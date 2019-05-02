package online;

import config.ConfigurationGame;

import java.io.Serializable;

public class Jugador implements Serializable {


    private static final long serialVersionUID = 0x1b32faab5902bfa3L;

    private String name;
    private int[] bolitasCapturadas = new int[6];
    private double[] time = new double[6];
    private  double bestTime;
    private double worstTime;
    private int bestCatch;
    private int worstCatch;
    private boolean bestadded;
    private boolean worstadded;
    private boolean host;
    private int id;
    private boolean eliminado;


    public Jugador(boolean host) {
        this.name = ConfigurationGame.getPlayerName();
        this.host=host;
        this.eliminado=false;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public boolean isHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }

    public String getName() {
        return name;
    }

    public void setBolitasCapturadas(int vida, int bolitasCapturadas) {
        this.bolitasCapturadas[vida] = bolitasCapturadas;
    }

    public int[] getBolitasCapturadas() {
        return this.bolitasCapturadas;
    }

    public void setTime(int partida, double tiempo) {
        this.time[partida] = tiempo;
    }

    public double[] getTime() {
        return this.time;
    }

    public  double getBestTime() {
        return this.bestTime;
    }

    public void setBestTime(double bestTime) {
        this.bestTime = bestTime;
    }

    public double getWorstTime() {
        return this.worstTime;
    }

    public void setWorstTime(double worstTime) {
        this.worstTime = worstTime;
    }

    public int getBestCatch() {
        return this.bestCatch;
    }

    public void setBestCatch(int bestCatch) {
        this.bestCatch = bestCatch;
    }

    public int getWorstCatch() {
        return this.worstCatch;
    }

    public void setWorstCatch(int worstCatch) {
        this.worstCatch = worstCatch;
    }

    public boolean isBestadded() {
        return bestadded;
    }

    public void setBestadded(boolean bestadded) {
        this.bestadded = bestadded;
    }

    public boolean isWorstadded() {
        return worstadded;
    }

    public void setWorstadded(boolean worstadded) {
        this.worstadded = worstadded;
    }

    public void restartMatchData() {
        this.bestTime=0;
        this.worstTime=0;
        this.bestCatch=0;
        this.worstCatch=0;
        this.bestadded=false;
        this.worstadded=false;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}
