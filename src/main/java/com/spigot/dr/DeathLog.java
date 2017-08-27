package com.spigot.dr;

import org.bukkit.entity.Player;

import java.util.List;

public class DeathLog {

    public static void saveDeathLog(Player p) {

        List<String> worlds = Config.getConfig().getStringList("disable-in-worlds");
        String w = p.getWorld().getName();

        if (p.hasPermission("dr.bypass")) {
            return;
        }

        if (worlds.contains(w)) {
            return;
        }

        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();

        Config.getDeathLog().set(p.getUniqueId().toString() + ".world", w);
        Config.getDeathLog().set(p.getUniqueId().toString() + ".x", x);
        Config.getDeathLog().set(p.getUniqueId().toString() + ".y", y);
        Config.getDeathLog().set(p.getUniqueId().toString() + ".z", z);
        Config.getDeathLog().set(p.getUniqueId().toString() + ".isDead", Boolean.valueOf(true));

        Config.saveDeathLog();

    }

}
