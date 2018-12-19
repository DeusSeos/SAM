package hr.seos.sam;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Commands implements CommandExecutor {

    private SAM plugin = SAM.getPlugin(SAM.class);
    public List<String> playerList = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
        if (sender.hasPermission("sam.admin")) {
            if (cmd.getName().equalsIgnoreCase("sam")) {
                if (args.length < 1) {

                    sender.sendMessage(ChatColor.GREEN + "/sam start: " + ChatColor.LIGHT_PURPLE + "Begins Sharks and Minnows Minigame");
                    sender.sendMessage(ChatColor.GREEN + "/sam end: " + ChatColor.LIGHT_PURPLE + "Stops Sharks and Minnows Minigame");
                    sender.sendMessage(ChatColor.GREEN + "/sam setms: " + ChatColor.LIGHT_PURPLE + "Sets your location as Shark Spawn");
                    sender.sendMessage(ChatColor.GREEN + "/sam setss: " + ChatColor.LIGHT_PURPLE + "Sets your location as Minnow Spawn");
                    sender.sendMessage(ChatColor.GREEN + "/sam end: " + ChatColor.LIGHT_PURPLE + "Teleports you to Shark Spawn");
                    sender.sendMessage(ChatColor.GREEN + "/sam tpm: " + ChatColor.LIGHT_PURPLE + "Teleports you to Minnow Spawn");
                    sender.sendMessage(ChatColor.GREEN + "/sam register: " + ChatColor.LIGHT_PURPLE + "Register's Item in your hand into SAM items");
                    sender.sendMessage(ChatColor.GREEN + "/sam item: " + ChatColor.LIGHT_PURPLE + "Gives you the item registered in SAM items");

                }
                if (args.length >= 1) {
                    String arg = args[0].toLowerCase();

                    if (arg.equals("start")) {
                        if (Bukkit.getOnlinePlayers().size() <= 1) {
                            sender.sendMessage(ChatColor.RED + "Not enough players, unable to start SAM");
                            return true;
                        }
                        //int rounds;
                        //sender.sendMessage("How many rounds?");
                        playerList.clear();
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            playerList.add(player.getName());
                        }

                        Integer random = ThreadLocalRandom.current().nextInt(playerList.size());

                        Player shark = Bukkit.getPlayer(playerList.get(random));

                        sender.sendMessage(ChatColor.GREEN + "The Shark is: " + ChatColor.LIGHT_PURPLE + shark.getName());

                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendTitle(ChatColor.GREEN + "Sharks and Minnows", ChatColor.LIGHT_PURPLE + " Starts In: 5", 0, 15, 0);
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HAT, 10, 1);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.sendTitle(ChatColor.GREEN + "Sharks and Minnows", ChatColor.LIGHT_PURPLE + " Starts In: 4", 0, 15, 0), 20);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HAT, 10, 1), 20);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.sendTitle(ChatColor.GREEN + "Sharks and Minnows", ChatColor.LIGHT_PURPLE + " Starts In: 3", 0, 15, 0), 40);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HAT, 10, 1), 40);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.sendTitle(ChatColor.GREEN + "Sharks and Minnows", ChatColor.LIGHT_PURPLE + " Starts In: 2", 0, 15, 0), 60);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HAT, 10, 1), 60);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.sendTitle(ChatColor.GREEN + "Sharks and Minnows", ChatColor.LIGHT_PURPLE + " Starts In: 1", 0, 15, 0), 80);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HAT, 10, 1), 80);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.sendTitle(ChatColor.GREEN + "Sharks and Minnows", ChatColor.LIGHT_PURPLE + "Has Started!", 0, 15, 10), 100);
                            Bukkit.getScheduler().runTaskLater(plugin, () -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_HAT, 10, 1), 100);
                        }
                            /*
                            for (int i = 0; i < playerList.size(); i++) {
                                sender.sendMessage(playerList.get(i));
                                sender.sendMessage("List size is: " + playerList.size());
                            }
                            */
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            @Override
                            public void run() {
                                if (playerList.isEmpty()) {
                                    return;
                                } else {
                                    Iterator iterator = playerList.iterator();
                                    while (iterator.hasNext()) {
                                        Player player = Bukkit.getPlayer(iterator.next().toString());
                                        String som = "m";
                                        if (player == shark) som = "s";

                                        World world = Bukkit.getWorld(plugin.getConfig().getString(som + "spawn.world"));
                                        Double x = plugin.getConfig().getDouble(som + "spawn.x");
                                        Double y = plugin.getConfig().getDouble(som + "spawn.y");
                                        Double z = plugin.getConfig().getDouble(som + "spawn.z");
                                        Double dyaw = plugin.getConfig().getDouble(som + "spawn.yaw");
                                        Float yaw = dyaw.floatValue();
                                        player.teleport(new Location(world, x, y, z, yaw, 0f));
                                    }
                                }
                            }
                        }, 5 * 20);
                        return true;
                    }

                    if (arg.matches("setms|setss|tps|tpm|register|item") && !(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.RED + "This command is only for players.");
                        return true;
                    }

                    if (arg.equals("setms")) {

                        plugin.getConfig().set("mspawn.world", ((Player) sender).getWorld().getName());
                        plugin.getConfig().set("mspawn.x", ((Player) sender).getLocation().getX());
                        plugin.getConfig().set("mspawn.y", ((Player) sender).getLocation().getY());
                        plugin.getConfig().set("mspawn.z", ((Player) sender).getLocation().getZ());
                        plugin.getConfig().set("mspawn.pitch", ((Player) sender).getLocation().getPitch());
                        plugin.getConfig().set("mspawn.yaw", ((Player) sender).getLocation().getYaw());
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Minnow spawn " + ChatColor.GREEN + "has been set.");
                        plugin.saveConfig();

                        return true;
                    }
                    if (arg.equals("setss")) {

                        plugin.getConfig().set("sspawn.world", ((Player) sender).getWorld().getName());
                        plugin.getConfig().set("sspawn.x", ((Player) sender).getLocation().getX());
                        plugin.getConfig().set("sspawn.y", ((Player) sender).getLocation().getY());
                        plugin.getConfig().set("sspawn.z", ((Player) sender).getLocation().getZ());
                        plugin.getConfig().set("sspawn.pitch", ((Player) sender).getLocation().getPitch());
                        plugin.getConfig().set("sspawn.yaw", ((Player) sender).getLocation().getYaw());
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Shark spawn " + ChatColor.GREEN + "has been set.");
                        plugin.saveConfig();

                        return true;
                    }
                    if (arg.equals("tps")) {

                        Player player = (Player) sender;
                        World world = Bukkit.getWorld(plugin.getConfig().getString("mspawn.world"));
                        Double x = plugin.getConfig().getDouble("mspawn.x");
                        Double y = plugin.getConfig().getDouble("mspawn.y");
                        Double z = plugin.getConfig().getDouble("mspawn.z");
                        Double dyaw = plugin.getConfig().getDouble("mspawn.yaw");
                        Float yaw = dyaw.floatValue();
                        player.teleport(new Location(world, x, y, z, yaw, 0f));
                        sender.sendMessage(ChatColor.GREEN + "Sending you to " + ChatColor.LIGHT_PURPLE + "minnow spawn");

                        return true;
                    }
                    if (arg.equals("tpm")) {

                        Player player = (Player) sender;
                        World world = Bukkit.getWorld(plugin.getConfig().getString("sspawn.world"));
                        Double x = plugin.getConfig().getDouble("sspawn.x");
                        Double y = plugin.getConfig().getDouble("sspawn.y");
                        Double z = plugin.getConfig().getDouble("sspawn.z");
                        Double dyaw = plugin.getConfig().getDouble("sspawn.yaw");
                        Float yaw = dyaw.floatValue();
                        player.teleport(new Location(world, x, y, z, yaw, 0f));
                        sender.sendMessage(ChatColor.GREEN + "Sending you to " + ChatColor.LIGHT_PURPLE + "playerList spawn");

                        return true;
                    }
                    if (arg.equals("register")) {

                        Player player = (Player) sender;
                        plugin.getConfig().set("item." + args[1], player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName());
                        plugin.saveConfig();
                        sender.sendMessage(ChatColor.GREEN + "Registering SAM Item: " + ChatColor.LIGHT_PURPLE + args[1]);

                    }
                    if (arg.equals("item")) {

                        String name = plugin.getConfig().getString("item." + args[1]);
                        sender.sendMessage(ChatColor.GREEN + "Giving you SAM item: " + ChatColor.LIGHT_PURPLE + name);

                    }

                    return true;
                }
            }
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have access to this command");
        }
        return true;
    }
}