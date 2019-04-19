package controllers;


import config.ConfigurationGame;
import escenas.MyStage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfigurationScreenController {

    private Stage mystage;

    @FXML
    TextField playerName;

    @FXML
    Slider volume;

    @FXML
    CheckBox CBVolume;

    @FXML
    ChoiceBox selectSkin;


    public void init() {
        //volume.setValue(ConfigurationGame.getVolume());
        System.out.println(ConfigurationGame.getVolume());
    }

    public void saveChanges() {

    }

    public void back() throws IOException {
        mystage = MyStage.getStage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        mystage.setTitle("PacMan 2.0");
        mystage.setResizable(false);
        mystage.setScene(new Scene(root, MyStage.ANCHO, MyStage.ALTURA));
        MyStage.setStage(mystage);
        mystage.show();
    }

    public void setVolume(){
        double volume2 = volume.getValue() / 100;
        ConfigurationGame.setVolume(volume2);
    }

    public void disableVolume() {
        volume.setValue(0);
    }


}
