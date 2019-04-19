package sounds;

import sprites.Packman;

public class PacmanEatingTheme extends Thread {

    Packman packman;

    public PacmanEatingTheme(Packman packman) {
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
