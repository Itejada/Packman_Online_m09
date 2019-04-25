package sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bolita {

    Image image = new Image("img/bolita20.png");
    double x;
    double y;
    int score;
    private double WIDTH_BOL;
    private double HEIGHT_BOL;

    public Bolita(Image image, int score, double x, double y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.score = score;
    }

    public Bolita(double x, double y,double WIDTH_BOL, double HEIGHT_BOL) {
        this.x = x;
        this.y = y;
        this.WIDTH_BOL=WIDTH_BOL;
        this.HEIGHT_BOL=HEIGHT_BOL;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, WIDTH_BOL, HEIGHT_BOL);
    }

    public void eatingBol(Packman packman, double WIDTH_SCREEN, double HEIGHT_SCREEN) {


        if (((packman.getHEIGHT_PACMAN() / 2)) + ((HEIGHT_BOL /2)) > packman.distancia(x-6, y-6)) {
    //        Aqui dererminamos el rango del Math.random, nunca sera 50 pixeles mas ancho y alto que el inicio de
    //        la pantalla y 50 mas bajo y alto que el final de esta
            x=(Math.random()*(WIDTH_SCREEN-(100 +(WIDTH_SCREEN/9))))+50 +(WIDTH_SCREEN/9) ;
            y=(Math.random()*(HEIGHT_SCREEN-(100)))+50  ;
            packman.setScore(packman.getScore()+1);

            //aqui controlamos las vidas bonus
            if ((packman.getScore()%10)==0){
                packman.setLives(packman.getLives()+1);
                System.out.println(packman.getLives());
            }
        }

    }



}
