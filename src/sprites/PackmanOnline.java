package sprites;



import java.io.Serializable;
import java.util.ArrayList;

public class PackmanOnline implements Serializable {

    private static final long serialVersionUID = 0x1b32faab5902bfa6L;

    private int score, lives;
    private double positionX, positionY;
    private double velocityX, velocityY;
    private double WIDTH_PACMAN, HEIGHT_PACMAN;
    private int ALTURA, ANCHO;

    private ArrayList<String> input;

    private boolean alive = true;

    public boolean isPlaying = false;

    private int idPlayer;

    public PackmanOnline( ArrayList<String> input,double positionX, double positionY, double velocityX, double velocityY, int ALTURA, int ANCHO, double WIDTH_PACMAN, double HEIGHT_PACMAN, int lives, int idPlayer) {
        this.input=input;
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.WIDTH_PACMAN = WIDTH_PACMAN;
        this.HEIGHT_PACMAN = HEIGHT_PACMAN;
        this.ALTURA = ALTURA;
        this.ANCHO = ANCHO;
        this.lives=lives;
        this.score = 0;
        this.idPlayer=idPlayer;
    }

    public ArrayList<String> getInput() {
        return input;
    }

    public void setInput(ArrayList<String> input) {
        this.input = input;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getWIDTH_PACMAN() {
        return WIDTH_PACMAN;
    }

    public void setWIDTH_PACMAN(double WIDTH_PACMAN) {
        this.WIDTH_PACMAN = WIDTH_PACMAN;
    }

    public double getHEIGHT_PACMAN() {
        return HEIGHT_PACMAN;
    }

    public void setHEIGHT_PACMAN(double HEIGHT_PACMAN) {
        this.HEIGHT_PACMAN = HEIGHT_PACMAN;
    }

    public double distancia(double x, double y) {
        return Math.sqrt(Math.pow((x - positionX), 2) + Math.pow((y - positionY), 2));
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = (lives>5) ? 5:lives;
    }

    public void killP(ArrayList<String> input, Packman packman){
        if ((input.contains("K") || input.contains("k"))) {
            packman.setLives(packman.getLives()-1);
        }

    }
}
