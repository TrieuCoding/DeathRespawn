package com.spigot.dr;

import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;
import net.milkbowl.vault.economy.EconomyResponse;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Events implements Listener {
    public static Main plugin;
    public Events(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = e.getEntity();
            Location loc = p.getLocation();
            double health = p.getMaxHealth();
            TitleManagerAPI api = (TitleManagerAPI) plugin.getServer().getPluginManager().getPlugin("TitleManager");
            p.setHealth(health);
            p.setFoodLevel(20);
            List<String> worlds = Config.getConfig().getStringList("disable-in-worlds");
            String world = p.getWorld().getName();
            DeathLog.saveDeathLog(p);
            if (p.hasPermission("dr.bypass")) {
                p.setHealth(health);
                p.setFoodLevel(20);
                p.setGameMode(GameMode.valueOf(Config.getConfig().getString("countdown.respawn-gamemode")));
                if (Config.getConfig().getBoolean("respawn.enable")) {
                    for (String s : Config.getConfig().getStringList("respawn.location")) {
                        try {
                            p.setOp(true);
                            plugin.getServer().dispatchCommand(p, s
                                    .replace("{player}", p.getName())
                                    .replace("&", "§"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            p.setOp(false);
                        }
                    }
                }
                return;
            }
            if (p.isOp()) {
                return;
            }
            if (worlds.contains(world)) {
                return;
            }
            p.setGameMode(GameMode.valueOf(Config.getConfig().getString("countdown.countdown-gamemode")));
            if (Config.getConfig().getBoolean("countdown.blindness")) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 255));
            }
            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                int cd1 = Config.getConfig().getInt("respawn-cooldown");
                int cd2 = Config.getConfig().getInt("respawn-cooldown");

                @Override
                public void run() {
                    if (this.cd1 > 0) {
                        if (Config.getMessageFile().getBoolean("message.enable")) {
                            p.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                                    + Config.getMessageFile().getString("message.countdown").replace("&", "§")
                                    .replace("{seconds}", String.valueOf(cd1)));
                        }
                        if (Config.getMessageFile().getBoolean("title.enable")) {
                            if (plugin.getServer().getPluginManager().isPluginEnabled("TitleManager")) {
                                api.sendTitle(p, Config.getMessageFile().getString("title.title.countdown")
                                        .replace("&", "§"), 0, 60, 0);
                                api.sendSubtitle(p, Config.getMessageFile().getString("title.subtitle.countdown")
                                        .replace("&", "§")
                                        .replace("{seconds}", String.valueOf(cd1)),
                                        0, 60, 0
                                );
                            } else {
                                System.out.println(plugin.cslprefix + "TitleManager wasn't found!");
                                Config.getMessageFile().set("message.enable", Boolean.valueOf(true));
                                Config.getMessageFile().set("title.enable", Boolean.valueOf(false));
                                Config.saveMessageFile();
                            }
                        }
                        if (Config.getConfig().getBoolean("sound.enable")) {
                            p.playSound(p.getLocation(), Sound.valueOf(Config.getConfig().getString("sound.countdown")),
                                    4F, 1F);
                        }
                        this.cd1--;
                    } else {
                        Config.getDeathLog().set(p.getUniqueId().toString() + ".isDead", Boolean.valueOf(false));
                        Config.saveDeathLog();
                        if (Config.getMessageFile().getBoolean("message.enable")) {
                            p.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                                    + Config.getMessageFile().getString("message.respawn").replace("&", "§"));
                        }
                        if (Config.getMessageFile().getBoolean("title.enable")) {
                            if (plugin.getServer().getPluginManager().isPluginEnabled("TitleManager")) {
                                api.sendTitle(p, Config.getMessageFile().getString("title.title.respawn")
                                        .replace("&", "§"), 0, 60, 0);
                                api.sendSubtitle(p, Config.getMessageFile().getString("title.subtitle.respawn")
                                                .replace("&", "§"), 0, 60, 0
                                );
                            } else {
                                System.out.println(plugin.cslprefix + "TitleManager wasn't found!");
                                Config.getMessageFile().set("message.enable", Boolean.valueOf(true));
                                Config.getMessageFile().set("title.enable", Boolean.valueOf(false));
                                Config.saveMessageFile();
                            }
                        }
                        if (Config.getConfig().getBoolean("sound.enable")) {
                            p.playSound(p.getLocation(), Sound.valueOf(Config.getConfig().getString("sound.respawn")),
                                    4F, 1F);
                        }
                        if (Config.getConfig().getBoolean("particle.enable")) {
                            if (ServerVersion.isMC112()) {
                                Particles packet = new Particles(EnumParticle.valueOf(
                                        Config.getConfig().getString("particle.type")),
                                        loc, 0.5f, 0.5f, 0.5f, 0.07f, 80);
                                packet.sendToPlayer(p);
                                p.playSound(p.getLocation(), Sound.valueOf(
                                        Config.getConfig().getString("particle.sound")
                                ), 4F, 1F);
                            } else {
                                System.out.println(plugin.cslprefix + "Particle mode only support 1.12 version!");
                                Config.getConfig().set("particle.enable", Boolean.valueOf(false));
                                Config.saveConfig();
                            }
                        }
                        if (Config.getConfig().getBoolean("respawn.enable")) {
                            for (String s : Config.getConfig().getStringList("respawn.location")) {
                                try {
                                    p.setOp(true);
                                    plugin.getServer().dispatchCommand(p, s
                                            .replace("{player}", p.getName())
                                            .replace("&", "§"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    p.setOp(false);
                                }
                            }
                        } else {
                            String w = Config.getDeathLog().getString(p.getUniqueId().toString() + ".world");
                            World world = plugin.getServer().getWorld(w);
                            p.teleport(new Location(world
                                    , Config.getDeathLog().getDouble(p.getUniqueId().toString() + ".x"),
                                    Config.getDeathLog().getDouble(p.getUniqueId().toString() + ".y"),
                                    Config.getDeathLog().getDouble(p.getUniqueId().toString() + ".z")));
                        }
                        p.removePotionEffect(PotionEffectType.BLINDNESS);
                        p.setGameMode(GameMode.valueOf(Config.getConfig().getString("countdown.respawn-gamemode")));
                        plugin.getServer().getScheduler().cancelAllTasks();
                        cd1 += cd2;
                    }
                }
            }, 0L, 20L);
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("dr.bypass")) { return; }
        if (p.isOp()) {
            return;
        }
        if (Config.getDeathLog().getBoolean(p.getUniqueId().toString() + ".isDead")) {
            plugin.getServer().getScheduler().cancelAllTasks();
        }
    }
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (p.hasPermission("dr.bypass")) { return; }
            if (p.isOp()) {
                return;
            }
            if (Config.getDeathLog().getBoolean(p.getUniqueId().toString() + ".isDead")) {
                if (!Config.getConfig().getBoolean("countdown.damage")) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("dr.bypass")) { return; }
        if (p.isOp()) {
            return;
        }
        if (Config.getDeathLog().getBoolean(p.getUniqueId().toString() + ".isDead")) {
            if (!Config.getConfig().getBoolean("countdown.move")) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("dr.bypass")) { return; }
        if (p.isOp()) {
            return;
        }
        if (Config.getDeathLog().getBoolean(p.getUniqueId().toString() + ".isDead")) {
            if (!Config.getConfig().getBoolean("countdown.chat")) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onUseCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("dr.bypass")) { return; }
        if (p.isOp()) {
            return;
        }
        if (Config.getDeathLog().getBoolean(p.getUniqueId().toString() + ".isDead")) {
            if (!Config.getConfig().getBoolean("countdown.use-command")) {
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        EconomyResponse r;
        if (p.hasPermission("dr.bypass")) { return; }
        if (p.isOp()) {
            return;
        }
        if (Config.getDeathLog().getBoolean(p.getUniqueId().toString() + ".isDead")) {

            p.removePotionEffect(PotionEffectType.BLINDNESS);
            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(20);
            plugin.getServer().getPlayer(p.getName())
                    .setGameMode(GameMode.valueOf(Config.getConfig().getString("countdown.respawn-gamemode")));
            plugin.getServer().getScheduler().cancelAllTasks();

            if (Config.getConfig().getBoolean("vault.enable")) {
                double m = plugin.econ.getBalance(p);
                if (m >= Config.getConfig().getDouble("vault.amount")) {
                    r = plugin.econ.withdrawPlayer(p, Config.getConfig().getDouble("vault.amount"));
                    if (r.transactionSuccess()) {
                        p.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                                + Config.getMessageFile().getString("vault-notify").replace("&", "§")
                                .replace("{amount}", Config.getConfig().getString("vault.amount")));
                    } else {
                        System.out.print(plugin.cslprefix + "§cAn error occured: " + r.errorMessage);
                    }
                } else {
                    r = plugin.econ.withdrawPlayer(p, m);
                    if (r.transactionSuccess()) {
                        p.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                                + Config.getMessageFile().getString("vault-notify").replace("&", "§")
                                .replace("{amount}", String.valueOf(r.amount)));
                    } else {
                        System.out.print(plugin.cslprefix + "§cAn error occured: " + r.errorMessage);
                    }
                }
            }
            if (Config.getConfig().getBoolean("punish.enable")) {
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(),
                        Config.getConfig().getString("punish.command").replace("{player}", p.getName()));
                p.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                        + Config.getMessageFile().getString("punish-notify").replace("&", "§"));
            }

            Config.getDeathLog().set(p.getUniqueId().toString() + ".isDead", Boolean.valueOf(false));
            Config.saveDeathLog();

        }
    }
}
