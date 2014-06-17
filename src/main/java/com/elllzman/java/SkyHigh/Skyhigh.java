package com.elllzman.java.SkyHigh;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
                if (getEnabled() == true) {
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
}
