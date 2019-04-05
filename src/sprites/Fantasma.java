package sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sprites.Sprite2;

public class Fantasma extends Sprite2 {

    private Image image;
    private double posX, posY, velX, velY, width, height;
    private int dirX, dirY;
    private int ALTURA, ANCHO;
    private Image[] frameFantasmaR = {new Image("img/fantasma4_0.png"),new Image("img/fantasma4_1.png")};
    private Image[] frameFantasmaL ={new Image("img/fantasma3_0.png"),new Image("img/fantasma3_1.png")};
    private Image[] frameFantasmaD = {new Image("img/fantasma2_0.png"),new Image("img/fantasma2_1.png")};
    private Image[] frameFantasmaU = {new Image("img/fantasma0_0.png"),new Image("img/fantasma0_1.png")};


    public Fantasma(Image _image, double x, double y, int ANCHO, int ALTURA, double width,  double height) {
        this.posX = x;
        this.posY = y;
        this.velX = 0.1f;
        this.velY = 0.1f;
        this.dirX = 1;
        this.dirY = 1;
        this.image=_image;
        this.ALTURA= ALTURA;
        this.ANCHO= ANCHO;
        this.height=height;
        this.width=width;
    }


    public void move(long currentNanoTime) {
        if (dirX == 1) {
            posX += velX;
            if (posX >= ANCHO - width) dirX = (-1) * dirX;
            this.animation(currentNanoTime,"RIGHT");
        } else {
            posX -= velX;
            if (posX <= 0) dirX = (-1) * dirX;
            this.animation(currentNanoTime,"LEFT");
        }
        if (dirY == 1) {
            posY += velY;
            if (posY >= ALTURA - height) dirY = (-1) * dirY;
            this.animation(currentNanoTime,"DOWN");
        } else {
            posY -= velY;
            if (posY <= 0) dirY = (-1) * dirY;
            this.animation(currentNanoTime,"UP");
        }
    }

    private void animation(long mCurrentNanoTime,String dir) {

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

    public void render(GraphicsContext gc) {
        gc.drawImage(image, posX, posY, width,height);
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
