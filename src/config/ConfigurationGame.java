package config;

public class ConfigurationGame {

    private String playerName;
    private float volume;
    private boolean volumeDisabled;
    private String skin;

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
