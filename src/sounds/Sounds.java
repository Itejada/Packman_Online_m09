package sounds;

import sprites.Packman;

public class Sounds extends Thread {

    Packman packman;

    public Sounds(Packman packman) {
        this.packman = packman;
    }


    @Override
    public void run() {
        for (; ; ) {
                packman.playSoundPacmanEating();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
