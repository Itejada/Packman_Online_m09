package sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
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

        if (input.contains("LEFT") != input.contains("RIGHT")) {
            if (input.contains("LEFT")) {
                if (positionX > 0) {
                    animation(sprite2, "LEFT",mcurrentNanoTime);
                    positionX -= velocityX;
                    //playSoundPacmanEating();
                } else {
                    positionX = 0;
                }
            }

            if (input.contains("RIGHT")) {
                if (positionX < ANCHO - anchoSprite) {
                    animation(sprite2, "RIGHT",mcurrentNanoTime);
                    positionX += velocityX;
                    //playSoundPacmanEating();
                } else {
                    positionX = ANCHO - anchoSprite;
                }
            }
        }

        //en este if compruebo si el arriba/abajo, no estan pulsados a la vez
        if (input.contains("UP") != input.contains("DOWN")) {
            if (input.contains("UP")) {
                if (positionY > 0) {
                    sprite2.setImage(up);
                    positionY -= velocityY;
                    //playSoundPacmanEating();
                } else {
                    positionY = 0;
                }
            }
            if (input.contains("DOWN")) {
                if (positionY < ALTURA - altoSprite) {
                    sprite2.setImage(down);
                    positionY += velocityY;
                    //playSoundPacmanEating();
                } else {
                    positionY = ALTURA - altoSprite;
                }
            }
        }
        sprite2.setPositionX(positionX);
        sprite2.setPositionY(positionY);
    }

    public void checkCollision(Sprite2 sprite2, Fantasma fantasma) {

        if((sprite2.getWidth()/2)+ (fantasma.getHeight()/2) > sprite2.distancia(fantasma.getPosX(), fantasma.getPosY())){
            System.out.println("x_x");
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

    public MediaPlayer getMoveSound() {
        return moveSound;
    }

    public void playSoundPacmanEating() {
        /*if(moveSound.getCurrentTime().greaterThanOrEqualTo(moveSound.getBufferProgressTime())) {
            moveSound.seek(Duration.millis(20));
        }
        moveSound.play();*/
        moveSound.setCycleCount(MediaPlayer.INDEFINITE);
        moveSound.play();
    }

    public void stopSoundPacmanEating(MediaPlayer moveSound) {
        moveSound.stop();
    }
}