package sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fantasma {

    private Image image;
    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;
    private int ALTURA, ANCHO;
    private Image[] frameFantasmaR = {new Image("img/fantasma4_0.png"),new Image("img/fantasma4_1.png")};
    private Image[] frameFantasmaL ={new Image("img/fantasma3_0.png"),new Image("img/fantasma3_1.png")};
    private Image[] frameFantasmaD = {new Image("img/fantasma2_0.png"),new Image("img/fantasma2_1.png")};
    private Image[] frameFantasmaU = {new Image("img/fantasma0_0.png"),new Image("img/fantasma0_1.png")};


    public Fantasma(Image _image, double x, double y,double velX,double velY,int ANCHO, int ALTURA, double width,  double height) {
        this.image=_image;
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

    public Fantasma() {}

    public static ArrayList<Fantasma> defaultFantasma() {
        Image fimage = new Image("img/fantasma0_0.png");
        int HEIGHT_SCREEN = 650;
        int WIDTH_SCREEN = 450;
        double velocidad = 5.5;
        Fantasma fantasma = new Fantasma(fimage, HEIGHT_SCREEN, WIDTH_SCREEN+WIDTH_SCREEN/9, velocidad, velocidad/2, HEIGHT_SCREEN, WIDTH_SCREEN, 22,22);
        ArrayList<Fantasma> fantasmas = new ArrayList<>();
        fantasmas.add(fantasma);
        return fantasmas;
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

    public double getVelocityX() {
        return velX;
    }

    public void setVelocityX(double velocityX) {
        this.velX = velocityX;
    }

    public double getVelocityY() {
        return velY;
    }

    public void setVelocityY(double velocityY) {
        this.velY = velocityY;
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

    public void move(long currentNanoTime) {

        if (dirX == 1) {
            posX += velX+(Math.random()*(posX/95));
            if (posX >= ANCHO - width) dirX = (-1) * dirX;
            this.animation(currentNanoTime,"RIGHT");
        } else {
            posX -= velX+(Math.random()*(posX/95));
            if (posX <= 0) dirX = (-1) * dirX;
            this.animation(currentNanoTime,"LEFT");
        }
        if (dirY == 1) {
            posY += velY+(Math.random()*(posY/95));
            if (posY >= ALTURA - height) dirY = (-1) * dirY;
            this.animation(currentNanoTime,"DOWN");
        } else {
            posY -= velY+(Math.random()*(posY/95));
            if (posY <= ALTURA/9) dirY = (-1) * dirY;
            this.animation(currentNanoTime,"UP");
        }

    }

    public void animation(long mCurrentNanoTime,String dir) {

        //aqui el metodo a actualizar
        int time= (int) ((mCurrentNanoTime/100000000) % 2);
        if (dir.equals("LEFT")) {
            this.setImage(frameFantasmaL[time]);
        }else if(dir.equals("RIGHT")){
            this.setImage(frameFantasmaR[time]);
        }else if(dir.equals("UP")) {
            this.setImage(frameFantasmaU[time]);
        }else {
            this.setImage(frameFantasmaD[time]);
        }
    }

    public void setImage(Image i) {
        image = i;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY, width,height);
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(posX,posY,width,height);
    }

    public boolean intersects(Packman s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }
}
