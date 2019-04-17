package sounds;

import sprites.Sprite2;

public class Sounds extends Thread {

    Sprite2 sprite2;

    public Sounds(Sprite2 sprite2) {
        this.sprite2 = sprite2;
    }


    @Override
    public void run() {
        for (; ; ) {
                sprite2.playSoundPacmanEating();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
