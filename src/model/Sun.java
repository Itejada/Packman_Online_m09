package model;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sun {

    private Image image;
    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;

    public Sun() {
        this.posX = 0.0f;
        this.posY = 0.0f;
        this.velX = 1.0f;
        this.velY = 1.0f;
        this.dirX = 1;
        this.dirY = 1;
    }

    public Sun(Image _image, double x, double y) {
        this.posX = x;
        this.posY = y;
        this.velX = 4.0f;
        this.velY = 4.0f;
        this.dirX = 1;
        this.dirY = 1;
        this.image=_image;
    }


    public void move() {

    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY);
        //System.out.println(posX + ":" + posY);
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
    public void setWidth(int _whidth){
        this.width= _whidth;
    }
    public void setHeight(int _height){
        this.width= _height;
    }

}
