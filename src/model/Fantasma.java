package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sprites.Sprite2;

public class Fantasma extends Sprite2 {

    private Image image;
    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;
     int ALTURA, ANCHO;

    public Fantasma() {
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.velX = 0.5f;
        this.velY = 0.5f;
        this.dirX = 1;
        this.dirY = 1;
    }

    public Fantasma(Image _image, double x, double y, int ANCHO, int ALTURA) {
        this.posX = x;
        this.posY = y;
        this.velX = 4.0f;
        this.velY = 4.0f;
        this.dirX = 1;
        this.dirY = 1;
        this.image=_image;
        this.ALTURA= ALTURA;
        this.ANCHO= ANCHO;
    }


    public void move() {
        if (dirX == 1) {
            posX += velX;
            if (posX >= ANCHO - width) dirX = (-1) * dirX;
        } else {
            posX -= velX;
            if (posX <= 0) dirX = (-1) * dirX;
        }
        if (dirY == 1) {
            posY += velY;
            if (posY >= ALTURA - height) dirY = (-1) * dirY;
        } else {
            posY -= velY;
            if (posY <= 0) dirY = (-1) * dirY;
        }
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY);
    }

    public void setImage(Image i) {
        image = i;
        width = image.getWidth();
        height = image.getHeight();
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(posX,posY,width,height);
    }

    public boolean intersects(Sprite2 s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }
}
