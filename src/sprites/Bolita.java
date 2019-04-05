package sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bolita {

    Image image;
    int x;
    int y;
    int pts;

    public Bolita(Image image, int pts, int x, int y) {
        this.image=image;
        this.x=x;
        this.y=y;
        this.pts=pts;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, x, y);
    }

}
