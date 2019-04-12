package sounds;

import sprites.Sprite2;

public class sound implements Runnable {

    Sprite2 sprite2;

    public sound(Sprite2 sprite2) {
        this.sprite2=sprite2;
    }

    @Override
    public synchronized void run() {
        sprite2.playSoundPacmanEating();
    }
}
