package sounds;

import sprites.Sprite2;

public class Sounds extends Thread {

    Sprite2 sprite2;

    public Sounds(Sprite2 sprite2) {
        this.sprite2 = sprite2;
    }

    public Sounds() {
    }

    @Override
    public void run() {
        for (; ; ) {
            //while (!sprite2.isPlaying) {
                sprite2.playSoundPacmanEating();
                System.out.println(sprite2.isPlaying);
            //}
        }
    }

//    public void waitSound(){
//        try {
//            this.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

}
