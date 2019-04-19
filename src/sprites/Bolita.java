package sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bolita {

    Image image = new Image("img/bolita20.png");
    double x;
    double y;
    int pts;
    private double WIDTH_BOL;
    private double HEIGHT_BOL;

    public Bolita(Image image, int pts, double x, double y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.pts = pts;
    }

    public Bolita(double x, double y,double WIDTH_BOL, double HEIGHT_BOL) {
        this.x = x;
        this.y = y;
        this.WIDTH_BOL=WIDTH_BOL;
        this.HEIGHT_BOL=HEIGHT_BOL;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x, y, WIDTH_BOL,HEIGHT_BOL);
    }

    public void eatingBol(Packman packman, double width, double height) {


        if ((packman.getWidth() / 2) + (HEIGHT_BOL /2) > packman.distancia(x, y)) {
            System.out.println("################################################################################################################:::");
            x=Math.random()*width  ;
            y=Math.random()*height ;
        }

    }



}
