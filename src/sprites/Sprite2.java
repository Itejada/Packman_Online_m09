package sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.ArrayList;

public class Sprite2 {
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX=5;
    private double velocityY=5;
    private double width;
    private double height;
    private int ALTURA, ANCHO;
    Image[] framesR = new Image[6];
    Image[] framesL = new Image[6];

    private String pathMoveSound = "src/sounds/pacman_chomp.wav";
    private Media media = new Media(new File(pathMoveSound).toURI().toString());
    private MediaPlayer moveSound = new MediaPlayer(media);
    public boolean isPlaying = false;

    public Sprite2(Image image,double positionX, double positionY, double velocityX, double velocityY, int ALTURA, int ANCHO, double width, double height) {
        this.image=image;
        this.positionX=positionX;
        this.positionY=positionY;
        this.velocityX=velocityX;
        this.velocityY=velocityY;
        this.width=width;
        this.height=height;
        this.ALTURA=ALTURA;
        this.ANCHO=ANCHO;
        for (int i = 0; i < 6; i++) {
            framesR[i] = new Image("img/pacbol_" + (i + 1) + ".png");
            framesL[i] = new Image("img/pacbol_" + (i + 7) + ".png");
        }
        moveSound.setCycleCount(MediaPlayer.INDEFINITE);
    }

    // ...
    // methods omitted for brevity
    // ...

    public void update(double time)
    {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, positionX, positionY );
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite2 s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
    public double distancia(double x, double y){
        return Math.sqrt(Math.pow((x - positionX),2) + Math.pow((y - positionY),2) );
    }

    public void movePackman(ArrayList<String> input, Sprite2 sprite2, int anchoSprite, Image up, int altoSprite, Image down,long mcurrentNanoTime) {

        if ((input.contains("LEFT") ||input.contains("A")) !=(input.contains("RIGHT") ||input.contains("D"))){
            if ((input.contains("LEFT") ||input.contains("A"))) {
                if (positionX > 0) {
                    animation(sprite2, "LEFT",mcurrentNanoTime);
                    positionX -= velocityX;
                    isPlaying=true;
                } else {
                    positionX = 0;
                    isPlaying=false;
                }
            }
            else if ((input.contains("RIGHT") ||input.contains("D"))) {
                if (positionX < ANCHO - anchoSprite) {
                    animation(sprite2, "RIGHT",mcurrentNanoTime);
                    positionX += velocityX;
                    isPlaying=true;
                } else {
                    positionX = ANCHO - anchoSprite;
                    isPlaying=false;
                }
            }
            else {
                isPlaying=false;
            }
        }

        //en este if compruebo si el arriba/abajo, no estan pulsados a la vez
        if ((input.contains("UP") ||input.contains("W")) != (input.contains("DOWN")||input.contains("S"))) {
            if ((input.contains("UP") ||input.contains("W"))) {
                if (positionY > 0) {
                    sprite2.setImage(up);
                    positionY -= velocityY;
                    isPlaying=true;
                } else {
                    positionY = 0;
                    isPlaying=false;
                }
            }
            else if ((input.contains("DOWN")||input.contains("S"))) {
                if (positionY < ALTURA - altoSprite) {
                    sprite2.setImage(down);
                    positionY += velocityY;
                    isPlaying=true;
                } else {
                    positionY = ALTURA - altoSprite;
                    isPlaying=false;
                }
            }else {
                isPlaying=false;
            }
        }
        sprite2.setPositionX(positionX);
        sprite2.setPositionY(positionY);
    }


    public void checkCollision(Sprite2 sprite2, Fantasma fantasma) {

        if((sprite2.getWidth()/2)+ (fantasma.getHeight()/2) > sprite2.distancia(fantasma.getPosX(), fantasma.getPosY())){
            System.out.println("x_x");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /* por si no entiendes algo te dejo link
     * https://stackoverflow.com/questions/30146560/how-to-change-animationtimer-speed*/
    private void animation(  Sprite2 sprite2, String dir, long mCurrentNanoTime) {

        //aqui el metodo a actualizar
        int time= (int) ((mCurrentNanoTime/100000000) % 6);
        if (dir.equals("LEFT")) {
            sprite2.setImage(framesL[time]);
        }else{
            sprite2.setImage(framesR[time]);
        }
    }

    public void playSoundPacmanEating() {
        if(isPlaying) {
            moveSound.play();
        }
        else {
            moveSound.stop();
            isPlaying=false;
        }
    }

}