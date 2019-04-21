package sprites;

import config.ConfigurationGame;
import escenas.EscenaCome;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


import java.io.File;
import java.util.ArrayList;

public class Packman {
    private Image image;

    private int score, lives;
    private double positionX, positionY;
    private double velocityX, velocityY;
    private double WIDTH_PACMAN, HEIGHT_PACMAN;
    private int ALTURA, ANCHO;
    Image[] framesR = new Image[6];
    Image[] framesL = new Image[6];


    private boolean alive = true;

    private String pathMoveSound = "src/sounds/PacmanWakaWaka04.wav";
    private Media media = new Media(new File(pathMoveSound).toURI().toString());
    private MediaPlayer moveSound = new MediaPlayer(media);

    private String pathDeadSound = "src/sounds/pacman_death.wav";
    private Media media2 = new Media(new File(pathDeadSound).toURI().toString());
    private MediaPlayer deadSound = new MediaPlayer(media2);

    public boolean isPlaying = false;

    public Packman(Image image, double positionX, double positionY, double velocityX, double velocityY, int ALTURA, int ANCHO, double WIDTH_PACMAN, double HEIGHT_PACMAN, int lives) {
        this.image = image;
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
        for (int i = 0; i < 6; i++) {
            framesR[i] = new Image("img/pacbol_" + (i + 1) + ".png");
            framesL[i] = new Image("img/pacbol_" + (i + 7) + ".png");
        }
        moveSound.setCycleCount(MediaPlayer.INDEFINITE);
    }

    // ...
    // methods omitted for brevity
    // ...

    public void update(double time) {
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY, WIDTH_PACMAN, HEIGHT_PACMAN);
    }

    public void movePackman(ArrayList<String> input, Packman packman, int anchoSprite, Image up, int altoSprite, Image down, long mcurrentNanoTime) {

        if ((input.contains("LEFT") || input.contains("A")) != (input.contains("RIGHT") || input.contains("D"))) {
            if ((input.contains("LEFT") || input.contains("A"))) {
                if (positionX > 0) {
                    animation(packman, "LEFT", mcurrentNanoTime);
                    positionX -= velocityX;
                    isPlaying = true;
                } else {
                    positionX = 0;
                    isPlaying = false;
                }
            } else if ((input.contains("RIGHT") || input.contains("D"))) {
                if (positionX < ANCHO - anchoSprite) {
                    animation(packman, "RIGHT", mcurrentNanoTime);
                    positionX += velocityX;
                    isPlaying = true;
                } else {
                    positionX = ANCHO - anchoSprite;
                    isPlaying = false;
                }
            } else {
                isPlaying = false;
            }
        }else {
            isPlaying=false;
        }

        //en este if compruebo si el arriba/abajo, no estan pulsados a la vez
        if ((input.contains("UP") || input.contains("W")) != (input.contains("DOWN") || input.contains("S"))) {
            if ((input.contains("UP") || input.contains("W"))) {
                if (positionY > ALTURA / 9) {
                    packman.setImage(up);
                    positionY -= velocityY;
                    isPlaying = true;
                } else {
                    positionY = ALTURA / 9;
                    isPlaying = false;
                }
            } else if ((input.contains("DOWN") || input.contains("S"))) {
                if (positionY < ALTURA - altoSprite) {
                    packman.setImage(down);
                    positionY += velocityY;
                    isPlaying = true;
                } else {
                    positionY = ALTURA - altoSprite;
                    isPlaying = false;
                }
            } else {
                isPlaying = false;
            }
        }
        packman.setPositionX(positionX);
        packman.setPositionY(positionY);
    }

    public void checkCollision(Packman packman, Fantasma fantasma, Fantasma fantasma2, Fantasma fantasma3, Fantasma fantasma4, MediaPlayer startSound) throws InterruptedException {

        Fantasma[] fantasmas = {fantasma, fantasma2, fantasma3, fantasma4};
        for (int i = 0; i < fantasmas.length; i++) {
            alive=true;
            if ((packman.getWIDTH_PACMAN() / 2) + ((fantasmas[i].getHeight()-12) / 2) > packman.distancia(fantasmas[i].getPosX()+4, fantasmas[i].getPosY()+3)) {
                alive=false;
                if(!ConfigurationGame.isVolumeDisabled()) {
                    deadSound.setVolume(ConfigurationGame.getVolume()/2);
                }else {
                    deadSound.setVolume(0);
                }

                deadSound.seek(Duration.ZERO);
                deadSound.play();

                //Si nos matan en la vida 0, iremos a GameOver.
                if(packman.getLives()>=0) {
                    setPositionY(ALTURA / 9);
                    setPositionX(0);
                    for (int j = 0; j < fantasmas.length ; j++) {
                        fantasmas[j].setPosX(100 + (Math.random() * (fantasmas[j].getPosX()% ANCHO)));
                        fantasmas[j].setPosY(100 + (Math.random() * ((fantasmas[j].getPosY()*2) % ALTURA)));
                    }
                    packman.setScore(0);
                    packman.setLives((getLives()>=0) ?  getLives()-1 :(-1));
                    Thread.sleep(1000);
                    if(packman.getLives()<0) {
                        moveSound.stop();
                        deadSound.stop();
                        packman.setPositionX(0);
                        packman.setPositionY(0);
                        EscenaCome.gameOver();
                    }else {
                        startSound.seek(Duration.ZERO);
                        startSound.play();
                    }
                }else {
                    Thread.sleep(1000);
                }
            }
        }
    }

    /* por si no entiendes algo te dejo link
     * https://stackoverflow.com/questions/30146560/how-to-change-animationtimer-speed*/
    private void animation(Packman packman, String dir, long mCurrentNanoTime) {
        //aqui el metodo a actualizar
        int time = (int) ((mCurrentNanoTime / 100000000) % 6);
        if (dir.equals("LEFT")) {
            packman.setImage(framesL[time]);
        } else {
            packman.setImage(framesR[time]);
        }
    }

    public void playSoundPacmanEating() {
        if(alive){
            if(!ConfigurationGame.isSoundsDisabled()) {
                if(!ConfigurationGame.isVolumeDisabled()) {
                    moveSound.setVolume(ConfigurationGame.getVolume()/2);
                }else {
                    moveSound.setVolume(0.25);
                }
            }else {
                moveSound.setVolume(0);
            }

            if (isPlaying) {
                moveSound.play();
            } else {
                moveSound.stop();
                isPlaying = false;
            }
        }else {
            moveSound.stop();
            isPlaying=false;
        }

    }
    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, WIDTH_PACMAN, HEIGHT_PACMAN);
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
        this.lives = lives;
    }
}