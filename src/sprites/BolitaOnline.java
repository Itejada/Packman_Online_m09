package sprites;

import java.io.Serializable;

public class BolitaOnline implements Serializable {

    private static final long serialVersionUID = 0x1b32faab5902bfa4L;

    double x;
    double y;
    private double WIDTH_BOL;
    private double HEIGHT_BOL;


    public BolitaOnline(double x, double y, double WIDTH_BOL, double HEIGHT_BOL) {
        this.x = x;
        this.y = y;
        this.WIDTH_BOL=WIDTH_BOL;
        this.HEIGHT_BOL=HEIGHT_BOL;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void eatingBol2(PackmanOnline packman, double WIDTH_SCREEN, double HEIGHT_SCREEN) {
        if (((packman.getHEIGHT_PACMAN() / 2)) + ((HEIGHT_BOL /2)) > packman.distancia(x-6, y-6)) {
    //        Aqui dererminamos el rango del Math.random, nunca sera 50 pixeles mas ancho y alto que el inicio de
    //        la pantalla y 50 mas bajo y alto que el final de esta
            x=(Math.random()*(WIDTH_SCREEN-(100 +(WIDTH_SCREEN/9))))+50 +(WIDTH_SCREEN/9) ;
            y=(Math.random()*(HEIGHT_SCREEN-(100)))+50;
            packman.setScore(packman.getScore()+1);

            //aqui controlamos las vidas bonus
            if ((packman.getScore()%10)==0){
                packman.setLives(packman.getLives()+1);
            }
        }
    }

    public boolean eatingBol(Packman packman) {
        if (((packman.getHEIGHT_PACMAN() / 2)) + ((HEIGHT_BOL /2)) > packman.distancia(x-6, y-6)) {
           return true;
        }else {
            return false;
        }
    }

    public void setRespawn( double WIDTH_SCREEN, double HEIGHT_SCREEN) {
        this.x=(Math.random()*(WIDTH_SCREEN-(100 +(WIDTH_SCREEN/9))))+50 +(WIDTH_SCREEN/9) ;
        this.y=(Math.random()*(HEIGHT_SCREEN-(100)))+50;
    }
}
