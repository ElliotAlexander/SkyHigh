package com.elllzman.java.SkyHigh;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Created by Elliot on 16/06/2014.
 */
public final class Skyhigh extends JavaPlugin {

    private static boolean isEnabled = true;

    public void onEnable()
    {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (getEnabled()) {
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        Location loc = p.getLocation();
                        int y = (int) loc.getY();
                        if (y > 100 && p.getGameMode() == GameMode.SURVIVAL) {
                            p.sendMessage("§9[SkyHigh] §bWarnings were spoken...");
                            double oldHealth = p.getHealth();
                            p.setHealth(oldHealth - 1);
                        }
                    }
                }
            }
        },0L, 600L);
    }


    private static boolean getEnabled()
    {
       return isEnabled;
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
                if(sender.isOp()||sender.hasPermission("SkyHigh.Admin")||getEnabled()==false)
                {
                    isEnabled = true;
                    Bukkit.getServer().broadcastMessage("§9[SkyHigh] §bSkyhigh has been enabled!");
                }
                else if(getEnabled()){ sender.sendMessage(ChatColor.RED + "Skyhigh already enabled"); return true; }
                else;  sender.sendMessage(ChatColor.RED + "No Permission"); return true;

            }
            if(args[0].equalsIgnoreCase("disable")||args[0].equalsIgnoreCase("off"))
            {
                if(sender.isOp()||sender.hasPermission("SkyHigh.Admin")||getEnabled())
                {
                    isEnabled = false;
                    Bukkit.getServer().broadcastMessage("§9[SkyHigh] §bSkyhigh has been disabled! The ground is safe, for now...");
                }
                else if(getEnabled()){ sender.sendMessage(ChatColor.RED + "Skyhigh already disabled"); return true; }
                else;  { sender.sendMessage(ChatColor.RED + "No Permission"); return true; }

            }

        }
        return true;

    }

    public void skyHighHelp(Player p)
    {
        p.sendMessage("§b--------------------- §9[SkyHigh]§b ---------------------");
        p.sendMessage("§bAfter 45 minutes, any player below Y-101 will begin to take half a heart of damage every 30 seconds.");
        p.sendMessage(" ");
        p.sendMessage("§bYou must use this 45 minutes wisely to make preparations for surviving the remainder of the game in the sky.");
        p.sendMessage(" ");
        p.sendMessage("§bHint: Cobblestone and snow generators will be very important.");
        p.sendMessage("§bBe sure your tower is close to 0,0 to reach the sky meetup in reasonable time and have encounters with other teams.\n ");
        p.sendMessage("§bYou will be forced to head to meetup by means of ground travel if necessary, another reason why having a way to generate blocks and build bridges is important.\n");
        p.sendMessage("§b--------------------- §9[SkyHigh]§b ---------------------");
    }

}
