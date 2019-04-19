package config;

public class ConfigurationGame {

    private static String playerName="";
    private static double volume=0.5;
    private static boolean volumeDisabled = false;
    private static String skin = null;

    public static String getPlayerName() {
        return playerName;
    }

    public static void setPlayerName(String playerNameR) {
        playerName = playerNameR;
    }

    public static double getVolume() {
        return volume;
    }

    public static void setVolume(double volumeR) {
        volume = volumeR;
    }

    public static boolean isVolumeDisabled() {
        return volumeDisabled;
    }

    public static void setVolumeDisabled(boolean volumeDisabledR) {
        volumeDisabled = volumeDisabledR;
    }

    public static String getSkin() {
        return skin;
    }

    public static void setSkin(String skinR) {
        skin = skinR;
    }


}
