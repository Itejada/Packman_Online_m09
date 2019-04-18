package escenas;

import javafx.stage.Stage;

public class MyStage {

    static Stage stage;
    public static final int ANCHO = 650;
    public static final int ALTURA = 450;

    public static void setStage(Stage stageR) {
        stage = stageR;
    }

    public static Stage getStage() {
        return stage;
    }
}
