package model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimatedImage {
    public Image[] frames;
    private Image image;
    public double duration;
    private int index = 0;
    private int size;
    private double width, height;

    public AnimatedImage(int size) {
        this.size = size;
        index = 0;


    }

    public void setImage(Image i) {
        image = i;
        width = image.getWidth();
        height = image.getHeight();


    }

    public Image getFrame()
    {
        //int i = (int)(index % (width / size));

        ImageView imgView = new ImageView(image);
        imgView.setViewport(new Rectangle2D(index*(width/size),0,(width/size),height));

        index++;
        return imgView.getImage();
    }
}