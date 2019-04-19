package escenas;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sprites.Packman;


public class Hud {


    private double WIDTH_HUD;
    private Image hudBg= new Image("img/background.jpg");




    public Hud(int WIDTH_SCREEN){

        WIDTH_HUD=WIDTH_SCREEN/12;

    }

    public void renderHud(GraphicsContext gc, Packman packman, double HEIGHT_SCREEN, double WIDTH_SCREEN) {

        gc.drawImage(hudBg, 0, 0,HEIGHT_SCREEN, WIDTH_SCREEN/9 );
        gc.setFill( Color.WHITE );
        gc.fillText( packman.getScore()+"", HEIGHT_SCREEN-50, 40 );
        gc.setFill( Color.RED );
        gc.fillText( packman.getLives()+"", 50, 40 );


    }

    public double getWIDTH_HUD() {
        return WIDTH_HUD;
    }

    public void setWIDTH_HUD(double WIDTH_HUD) {
        this.WIDTH_HUD = WIDTH_HUD;
    }


}
