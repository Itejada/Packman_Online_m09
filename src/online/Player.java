package online;

public class Player {


    int score, lives,X,Y;
    String image, id;


    @Override
    public boolean equals(Object obj) {
        return id == ((Player) obj).id;
    }
}
