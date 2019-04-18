package config;

public class ConfigurationGame {

    String playerName;
    float volume;
    boolean volumeDisabled;
    String skin;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isVolumeDisabled() {
        return volumeDisabled;
    }

    public void setVolumeDisabled(boolean volumeDisabled) {
        this.volumeDisabled = volumeDisabled;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }


}
