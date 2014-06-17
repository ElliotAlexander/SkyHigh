package com.elllzman.java.SkyHigh;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;



public final class Skyhigh extends JavaPlugin {


    public static int ticksBetweenDamage;
    public static int damageAmount;

    public static int taskId;
    private static boolean isEnabled = false;
    public static Plugin plugin;

    public void onEnable()
    {
        getLogger().info("Skyhigh has been invoked.");
        plugin = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        ticksBetweenDamage = getConfig().getInt("Ticks between damage");
        damageAmount = getConfig().getInt("Damage amount");
    }



    public static Plugin getPluginInstance()
    {
        return plugin;
    }



    private static boolean getEnabled()
    {
       return isEnabled;
    }

    public static int getTaskId()
    {
        return taskId;
    }

    public void startTask()
    {
       taskId = getPluginInstance().getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
        {
            public void run()
            {
                if (getEnabled()) {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        Location loc = p.getLocation();
                        int y = (int) loc.getY();
                        if (y < 100 && p.getGameMode() == GameMode.SURVIVAL) {
                            p.sendMessage("§9[SkyHigh] §bWarnings were spoken...");
                            double oldHealth = p.getHealth();
                            p.setHealth(oldHealth - damageAmount);
                        }
                    }
                }
            }
        },0L, ticksBetweenDamage);
    }

    public void stopTask()
    {
        Bukkit.getScheduler().cancelTask(getTaskId());
    }




    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("SkyHigh"))
        {
            if(args.length==0)
            {
                skyHighHelp((Player)sender);
                return true;

            }

            if(args[0].equalsIgnoreCase("help"))
            {
                skyHighHelp((Player)sender);
            }

            if(args[0].equalsIgnoreCase("enable")||args[0].equalsIgnoreCase("on"))
            {
                if(sender.isOp()||sender.hasPermission("SkyHigh.Admin")||getEnabled())
                {
                    isEnabled = true;
                    startTask();
                    Bukkit.getServer().broadcastMessage("§9[SkyHigh] §bSkyhigh has been enabled!");
                }
                else if(getEnabled()){
                    sender.sendMessage(ChatColor.RED + "Skyhigh already enabled");
                    return true;
                }


            }
            if(args[0].equalsIgnoreCase("disable")||args[0].equalsIgnoreCase("off"))
            {
                if(sender.isOp()||sender.hasPermission("SkyHigh.Admin")||getEnabled())
                {
                    isEnabled = false;
                    stopTask();
                    Bukkit.getServer().broadcastMessage("§9[SkyHigh] §bSkyhigh has been disabled! The ground is safe, for now...");
                }
                else if(getEnabled()){ sender.sendMessage(ChatColor.RED + "Skyhigh already disabled"); return true; }
            }

        }
        return true;

    }

    public void skyHighHelp(Player p)
    {
        p.sendMessage("§b--------------------- §9[SkyHigh]§b ---------------------");
        p.sendMessage("§bAfter 45 minutes, any player below Y-101 will begin to take half a heart of damage every 30 seconds.\n ");
        p.sendMessage("§bYou must use this 45 minutes wisely to make preparations for surviving the remainder of the game in the.\n ");
        p.sendMessage("§bHint: Cobblestone and snow generators will be very important.");
        p.sendMessage("§bBe sure your tower is close to 0,0 to reach the sky meetup in reasonable time and have encounters with other teams.\n ");
        p.sendMessage("§bYou will be forced to head to meetup by means of ground travel if necessary, another reason why having a way to generate blocks and build bridges is important.\n");
        p.sendMessage("§b--------------------- §9[SkyHigh]§b ---------------------");
    }

}
