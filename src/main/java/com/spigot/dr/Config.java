package com.spigot.dr;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class Config {

    private static FileConfiguration cf = null;
    private static FileConfiguration msg = null;
    private static FileConfiguration log = null;
    private static FileConfiguration location = null;

    private static File customCf = null;
    private static File customMsgFile = null;
    private static File customLog = null;
    private static File customLocationFile = null;

    public static void reloadConfig() {
        if (customCf == null && customMsgFile == null && customLog == null && customLocationFile == null) {
            customCf = new File(Main.getPlugin().getDataFolder(), "config.yml");
            customMsgFile = new File(Main.getPlugin().getDataFolder(), "message.yml");
            customLog = new File(Main.getPlugin().getDataFolder(), "deathlog.yml");
            customLocationFile = new File(Main.getPlugin().getDataFolder(), "respawnlocation.yml");
        }
        cf = YamlConfiguration.loadConfiguration(customCf);
        msg = YamlConfiguration.loadConfiguration(customMsgFile);
        log = YamlConfiguration.loadConfiguration(customLog);
        location = YamlConfiguration.loadConfiguration(customLocationFile);
        try {
            Reader defaultConfig = new InputStreamReader(Main.getPlugin().getResource("config.yml"), "UTF8");
            Reader defaultMsgFile = new InputStreamReader(Main.getPlugin().getResource("message.yml"), "UTF8");
            Reader defaultLog = new InputStreamReader(Main.getPlugin().getResource("deathlog.yml"), "UTF8");
            Reader defaultLocal = new InputStreamReader(Main.getPlugin().getResource("respawnlocation.yml"), "UTF8");
            if (defaultConfig != null && defaultMsgFile != null && defaultLog != null && defaultLocal != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defaultConfig);
                YamlConfiguration defMsgFile = YamlConfiguration.loadConfiguration(defaultMsgFile);
                YamlConfiguration defLog = YamlConfiguration.loadConfiguration(defaultLog);
                YamlConfiguration defLocal = YamlConfiguration.loadConfiguration(defaultLocal);
                cf.setDefaults(defConfig);
                msg.setDefaults(defMsgFile);
                log.setDefaults(defLog);
                location.setDefaults(defLocal);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getConfig() {
        if (cf == null) {
            reloadConfig();
        }
        return cf;
    }

    public static FileConfiguration getMessageFile() {
        if (msg == null) {
            reloadConfig();
        }
        return msg;
    }

    public static FileConfiguration getDeathLog() {
        if (log == null) {
            reloadConfig();
        }
        return log;
    }

    public static FileConfiguration getRespawnLocationFile() {
        if (location == null) {
            reloadConfig();
        }
        return location;
    }

    public static void saveConfig() {
        if (cf == null || customCf == null) {
            return;
        }
        try {
            getConfig().save(customCf);
        } catch (IOException e) {
            System.out.println("[DeathRespawn] Could not save config to " + customCf);
        }
    }

    public static void saveMessageFile() {
        if (msg == null || customMsgFile == null) {
            return;
        }
        try {
            getMessageFile().save(customMsgFile);
        } catch (IOException e) {
            System.out.println("[DeathRespawn] Could not save message file to " + customMsgFile);
        }
    }

    public static void saveDeathLog() {
        if (log == null || customLog == null) {
            return;
        }
        try {
            getDeathLog().save(customLog);
        } catch (IOException e) {
            System.out.println("[DeathRespawn] Could not save deathlog file to " + customLog);
        }
    }

    public static void saveRespawnLocationFile() {
        if (location == null || customLocationFile == null) {
            return;
        }
        try {
            getRespawnLocationFile().save(customLocationFile);
        } catch (IOException e) {
            System.out.println("[DeathRespawn] Could not save respawn location file to " + customLocationFile);
        }
    }

    public static void saveDefaultConfig() {
        if (customCf == null) {
            customCf = new File(Main.getPlugin().getDataFolder(), "config.yml");
        }
        if (!customCf.exists()) {
            Main.getPlugin().saveResource("config.yml", false);
        }
    }

    public static void saveDefaultMessageFile() {
        if (customMsgFile == null) {
            customMsgFile = new File(Main.getPlugin().getDataFolder(), "message.yml");
        }
        if (!customMsgFile.exists()) {
            Main.getPlugin().saveResource("message.yml", false);
        }
    }

    public static void saveDefaultLog() {
        if (customLog == null) {
            customLog = new File(Main.getPlugin().getDataFolder(), "deathlog.yml");
        }
        if (!customLog.exists()) {
            Main.getPlugin().saveResource("deathlog.yml", false);
        }
    }

    public static void saveDefaultRespawnLocationFile() {
        if (customLocationFile == null) {
            customLocationFile = new File(Main.getPlugin().getDataFolder(), "respawnlocation.yml");
        }
        if (!customLocationFile.exists()) {
            Main.getPlugin().saveResource("respawnlocation.yml", false);
        }
    }

}
