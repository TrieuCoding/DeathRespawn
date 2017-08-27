package com.spigot.dr;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Commands implements CommandExecutor {
    public static Main plugin;
    public Commands(Main plugin) {
        this.plugin = plugin;
    }

    HashMap<String, Location> location = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length < 1 || args.length == 1 && args[0].equalsIgnoreCase("help")) {

            if (!sender.hasPermission("dr.help")) {
                sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                + Config.getMessageFile().getString("no-permissions").replace("&", "§"));
                return true;
            }

            if (sender instanceof Player) {
                sender.sendMessage("/deathrespawn reload");
                sender.sendMessage("/deathrespawn setlocation [name]");
                sender.sendMessage("/deathrespawn tplocation [name]");
                sender.sendMessage("/deathrespawn removelocation [name]");
                sender.sendMessage("/deathrespawn location [name]");
                sender.sendMessage("/deathrespawn locations");
                sender.sendMessage("Aliases: /dr");
            } else {
                sender.sendMessage(plugin.cslprefix + "/deathrespawn reload");
                sender.sendMessage(plugin.cslprefix + "/deathrespawn removelocation [name]");
                sender.sendMessage(plugin.cslprefix + "/deathrespawn tplocation [name]");
                sender.sendMessage(plugin.cslprefix + "/deathrespawn location [name]");
                sender.sendMessage(plugin.cslprefix + "/deathrespawn locations");
                sender.sendMessage(plugin.cslprefix + "Aliases: /dr");
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {

            if (!sender.hasPermission("dr.reload")) {
                sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                        + Config.getMessageFile().getString("no-permissions").replace("&", "§"));
                return true;
            }

            Config.reloadConfig();
            if (sender instanceof Player) {
                sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                        + Config.getMessageFile().getString("reload").replace("&", "§"));
            } else {
                sender.sendMessage(plugin.cslprefix + ChatColor.GREEN + "Reload config!");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("setlocation")) {

            if (!sender.hasPermission("dr.setlocation")) {
                sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                        + Config.getMessageFile().getString("no-permissions").replace("&", "§"));
                return true;
            }

            if (sender instanceof Player) {

                if (args.length == 1 || args.length > 2) {
                    sender.sendMessage(ChatColor.RED + "Please use /deathrespawn setlocation [name]!");
                    return true;
                }

                if (location.containsKey(args[1].toLowerCase())) {
                    sender.sendMessage(ChatColor.RED + args[1] + " is already exist!");
                    return true;
                } else {
                    try {
                        location.put(args[1].toLowerCase(), ((Player)sender).getLocation());
                        Location loc = ((Player) sender).getLocation();
                        Config.getRespawnLocationFile().createSection(args[1].toLowerCase());
                        ConfigurationSection cs = Config.getRespawnLocationFile().getConfigurationSection(args[1].toLowerCase());
                        cs.set("world", loc.getWorld().getName());
                        cs.set("x", loc.getX());
                        cs.set("y", loc.getY());
                        cs.set("z", loc.getZ());
                        cs.set("yaw", loc.getYaw());
                        cs.set("pitch", loc.getPitch());
                        Config.saveRespawnLocationFile();
                        sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                                + ChatColor.GREEN + "Successfully set respawn location at your current location!");
                    } catch (Exception e) {
                        System.out.print(plugin.cslprefix + "An error occurred: " + e.getCause());
                    }
                }
            } else {
                sender.sendMessage(plugin.cslprefix + ChatColor.RED + "Please use this command ingame!");
                return true;
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("removelocation")) {

            if (!sender.hasPermission("dr.removelocation")) {
                sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                        + Config.getMessageFile().getString("no-permissions").replace("&", "§"));
                return true;
            }

            if (sender instanceof Player) {

                ConfigurationSection cs = Config.getRespawnLocationFile().getConfigurationSection(args[1].toLowerCase());

                if (args.length == 1 || args.length > 2) {
                    sender.sendMessage(ChatColor.RED + "Please use /deathrespawn setlocation [name]!");
                    return true;
                }

                if (cs == null) {
                    sender.sendMessage(ChatColor.RED + args[1] + " does not exist!");
                    return true;
                }

                if (cs != null) {
                    try {
                        location.remove(args[1].toLowerCase());
                        Config.getRespawnLocationFile().set(args[1].toLowerCase(), null);
                        cs.set("world", null);
                        cs.set("x", null);
                        cs.set("y", null);
                        cs.set("z", null);
                        cs.set("yaw", null);
                        cs.set("pitch", null);
                        Config.saveRespawnLocationFile();
                        sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                                + ChatColor.GREEN + "Successfully remove respawn location: " + args[1]);
                    } catch (Exception e) {
                        System.out.println(plugin.cslprefix + "An error occurred: " + e.getCause());
                    }
                }

            } else {

                ConfigurationSection cs = Config.getRespawnLocationFile().getConfigurationSection(args[1].toLowerCase());

                if (args.length == 1 || args.length > 2) {
                    sender.sendMessage(plugin.cslprefix + ChatColor.RED + "Please use /deathrespawn setlocation [name]!");
                    return true;
                }

                if (cs == null) {
                    sender.sendMessage(ChatColor.RED + args[1] + " does not exist!");
                    return true;
                }

                if (cs != null) {
                    try {
                        Config.getRespawnLocationFile().set(args[1].toLowerCase(), null);
                        cs.set("world", null);
                        cs.set("x", null);
                        cs.set("y", null);
                        cs.set("z", null);
                        cs.set("yaw", null);
                        cs.set("pitch", null);
                        Config.saveRespawnLocationFile();
                        sender.sendMessage(plugin.cslprefix
                                + ChatColor.GREEN + "Successfully remove respawn location: " + args[1]);
                    } catch (Exception e) {
                        System.out.println(plugin.cslprefix + "An error occurred: " + e.getCause());
                    }
                }

            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("tplocation")) {

            if (!sender.hasPermission("dr.tplocation")) {
                sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                        + Config.getMessageFile().getString("no-permissions").replace("&", "§"));
                return true;
            }

            if (sender instanceof Player) {

                ConfigurationSection cs = Config.getRespawnLocationFile().getConfigurationSection(args[1].toLowerCase());

                if (cs == null) {
                    sender.sendMessage(ChatColor.WHITE + args[1]
                            + ChatColor.RED + " is not a valid location!");
                    sender.sendMessage(ChatColor.RED + "Valid location(s): ");
                    for (String key : Config.getRespawnLocationFile().getKeys(false)) {
                        sender.sendMessage(ChatColor.WHITE + key);
                    }
                    return true;
                }

                if (cs != null) {
                    String w = cs.getString("world");
                    World world = plugin.getServer().getWorld(w);
                    float yaw = (float) cs.getDouble("yaw");
                    float pitch = (float) cs.getDouble("pitch");
                    Location loc;
                    loc = new Location(world,
                            cs.getDouble("x"),
                            cs.getDouble("y"),
                            cs.getDouble("z"),
                            yaw, pitch);

                    ((Player) sender).teleport(loc);
                }

            } else {
                sender.sendMessage(plugin.cslprefix + ChatColor.RED + "Please use this command ingame!");
                return true;
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("locations")) {

            if (!sender.hasPermission("dr.locations")) {
                sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                        + Config.getMessageFile().getString("no-permissions").replace("&", "§"));
                return true;
            }

            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + "Valid location(s): ");
                for (String key : Config.getRespawnLocationFile().getKeys(false)) {
                    sender.sendMessage(ChatColor.WHITE + key);
                }
            } else {
                sender.sendMessage(plugin.cslprefix + ChatColor.RED + "Valid location(s): ");
                for (String key : Config.getRespawnLocationFile().getKeys(false)) {
                    sender.sendMessage(ChatColor.WHITE + key);
                }
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("location")) {

            ConfigurationSection cs = Config.getRespawnLocationFile().getConfigurationSection(args[1].toLowerCase());

            if (!sender.hasPermission("dr.locationinfo")) {
                sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                        + Config.getMessageFile().getString("no-permissions").replace("&", "§"));
                return true;
            }

            if (cs == null) {
                sender.sendMessage(ChatColor.WHITE + args[1]
                        + ChatColor.RED + " is not a valid location!");
                sender.sendMessage(ChatColor.RED + "Valid location(s): ");
                for (String key : Config.getRespawnLocationFile().getKeys(false)) {
                    sender.sendMessage(ChatColor.WHITE + key);
                }
                return true;
            }

            if (cs != null) {
                if (sender instanceof Player) {
                    sender.sendMessage(ChatColor.GREEN + "Location: " + ChatColor.WHITE + args[1]);
                    sender.sendMessage(ChatColor.GRAY + " World name: "
                            + ChatColor.WHITE + cs.getString("world"));
                    sender.sendMessage(ChatColor.GRAY + " X: "
                            + ChatColor.WHITE + cs.getDouble("x"));
                    sender.sendMessage(ChatColor.GRAY + " Y: "
                            + ChatColor.WHITE + cs.getDouble("y"));
                    sender.sendMessage(ChatColor.GRAY + " Z: "
                            + ChatColor.WHITE + cs.getDouble("z"));
                    sender.sendMessage(ChatColor.GRAY + " Yaw: "
                            + ChatColor.WHITE + cs.get("yaw"));
                    sender.sendMessage(ChatColor.GRAY + " Pitch: "
                            + ChatColor.WHITE + cs.get("pitch"));
                } else {
                    sender.sendMessage(plugin.cslprefix + ChatColor.GREEN + "Location: " + ChatColor.WHITE + args[1]);
                    sender.sendMessage(ChatColor.GRAY + " World:"
                            + ChatColor.WHITE + cs.getString("world"));
                    sender.sendMessage(ChatColor.GRAY + " X: "
                            + ChatColor.WHITE + cs.getDouble("x"));
                    sender.sendMessage(ChatColor.GRAY + " Y: "
                            + ChatColor.WHITE + cs.getDouble("y"));
                    sender.sendMessage(ChatColor.GRAY + " Z: "
                            + ChatColor.WHITE + cs.getDouble("z"));
                    sender.sendMessage(ChatColor.GRAY + " Yaw: "
                            + ChatColor.WHITE + cs.get("yaw"));
                    sender.sendMessage(ChatColor.GRAY + " Pitch: "
                            + ChatColor.WHITE + cs.get("pitch"));
                }
            }

        } else {

            if (sender instanceof Player) {
                sender.sendMessage(Config.getMessageFile().getString("prefix").replace("&", "§")
                        + ChatColor.RED + "Unknow args! Please use /deathrespawn for help!");
            } else {
                sender.sendMessage(plugin.cslprefix + ChatColor.RED + "Unknow args! Please use /deathrespawn for help!");
            }
            return true;
        }
        return true;
    }
}
