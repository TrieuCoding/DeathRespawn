package com.spigot.dr;

import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Plugin plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    public static Main instance;

    public static Economy econ = null;

    public String cslprefix = "[DeathRespawn] ";

    @Override
    public void onEnable() {
        ConsoleCommandSender console = getServer().getConsoleSender();
        plugin = this;
        instance = this;
        Events.plugin = this;
        Commands.plugin = this;
        Config.getConfig().options().copyDefaults(true);
        Config.getMessageFile().options().copyDefaults(true);
        Config.saveMessageFile();
        Config.getDeathLog().options().copyDefaults(true);
        Config.saveDefaultLog();
        Config.getRespawnLocationFile().options().copyDefaults(true);
        Config.saveRespawnLocationFile();
        registerCommands();
        registerListeners();
        console.sendMessage(cslprefix + "Plugin has been enabled!");
        console.sendMessage(cslprefix + "Current version: " + getDescription().getVersion());
        if (setupEconomy()) {
            console.sendMessage(cslprefix + "Economy was found! Enable vault mode in config!");
            Config.getConfig().set("vault.enable", Boolean.valueOf(true));
            Config.saveConfig();
        } else {
            console.sendMessage(cslprefix + "Economy wasn't found! Enable punish mode in config!");
            Config.getConfig().set("punish.enable", Boolean.valueOf(true));
            Config.saveConfig();
        }
        try {
            if (ServerVersion.isMC19() || ServerVersion.isMC110() || ServerVersion.isMC111() || ServerVersion.isMC112()) {
                Config.getConfig().set("sound.countdown", "BLOCK_NOTE_PLING");
                Config.getConfig().set("sound.respawn", "ENTITY_PLAYER_LEVELUP");
                Config.saveConfig();
            } else if (ServerVersion.isMC18() || ServerVersion.isMC17()){
                Config.getConfig().set("sound.countdown", "NOTE_PLING");
                Config.getConfig().set("sound.respawn", "LEVEL_UP");
                Config.saveConfig();
            } else {
                System.out.println(cslprefix + "An error occured while checking version!");
            }
        } catch (Exception e) {
            System.out.println(cslprefix + "An error occured: " + e.getCause());
        }
        if (getServer().getPluginManager().isPluginEnabled("TitleManager")) {
            console.sendMessage(cslprefix + "TitleManager was found!");
            Config.getMessageFile().set("title.enable", Boolean.valueOf(true));
            Config.saveMessageFile();
        } else {
            console.sendMessage(cslprefix + "TitleManager wasn't found!");
            Config.getMessageFile().set("message.enable", Boolean.valueOf(true));
            Config.saveMessageFile();
        }
        if (Config.getConfig().getString("countdown.countdown-gamemode") == null) {
            Config.getConfig().set("countdown.countdown-gamemode", "SPECTATOR");
            Config.saveConfig();
            System.out.println(cslprefix + "Please type correct gamemode!");
        }
        if (Config.getConfig().getString("countdown.respawn-gamemode") == null) {
            Config.getConfig().set("countdown.respawn-gamemode", "SURVIVAL");
            Config.saveConfig();
            System.out.println(cslprefix + "Please type correct gamemode!");
        }
    }

    private void registerListeners() { getServer().getPluginManager().registerEvents(new Events(this), this); }

    private void registerCommands() {
        getCommand("deathrespawn").setExecutor(new Commands(this));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

}
