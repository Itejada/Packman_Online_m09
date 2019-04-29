package sprites;

import java.io.Serializable;

public class FantasmaOnline implements Serializable {

    private static final long serialVersionUID = 0x1b32faab5902bfa5L;

    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;
    private int ALTURA, ANCHO;


    public FantasmaOnline(double x, double y,double velX,double velY,int ANCHO, int ALTURA, double width,  double height){
        this.posX = x;
        this.posY = y;
        this.velX= (velX+ Math.random()*4)/2.5;
        this.velY=(velY+ Math.random()*4)/2.5;
        this.width=width;
        this.height=height;
        this.dirX = 1;
        this.dirY = 1;
        this.ALTURA= ALTURA;
        this.ANCHO= ANCHO;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getDirX() {
        return dirX;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public int getDirY() {
        return dirY;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    public int getALTURA() {
        return ALTURA;
    }

    public void setALTURA(int ALTURA) {
        this.ALTURA = ALTURA;
    }

    public int getANCHO() {
        return ANCHO;
    }

    public void setANCHO(int ANCHO) {
        this.ANCHO = ANCHO;
    }
}
