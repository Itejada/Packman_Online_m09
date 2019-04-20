package controllers;


import config.ConfigurationGame;
import escenas.MyStage;
import javafx.collections.FXCollections;
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

    public void initialize() {
        playerName.setText(ConfigurationGame.getPlayerName());
        volume.setValue(ConfigurationGame.getVolume()*100);
        CBVolume.setSelected(ConfigurationGame.isVolumeDisabled());
        selectSkin.setItems(FXCollections.observableArrayList("Default Skin","Blue Skin"));
        selectSkin.setValue("Default Skin");
    }

    public void back() throws IOException {
        saveChanges();
        mystage = MyStage.getStage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/MainScreen.fxml"));
        mystage.setTitle("PacMan 2.0");
        mystage.setResizable(false);
        mystage.setScene(new Scene(root, MyStage.ANCHO, MyStage.ALTURA));
        MyStage.setStage(mystage);
        mystage.show();
    }

    public void setVolume(){
        if(CBVolume.isSelected()) {
            CBVolume.setSelected(false);
        }
        double volume2 = volume.getValue() / 100;
        ConfigurationGame.setVolume(volume2);
    }

    public void disableVolume() {
        if(!CBVolume.isSelected()) {
            volume.setValue(50);
            ConfigurationGame.setVolume(0.5);
        }
        else {
            volume.setValue(0);
            ConfigurationGame.setVolume(0);
        }
    }

    public void saveChanges() {
        ConfigurationGame.setVolumeDisabled(CBVolume.isSelected());
        ConfigurationGame.setPlayerName(playerName.getText());
        ConfigurationGame.setSkin(selectSkin.getValue().toString());
    }

}
