package me.omaromar93.worldchatter.utils.Others;

import org.bukkit.Bukkit;

public class PAPIDependSystem {
    public static boolean placeholderapisuppport = false;

    public static boolean isPAPIThere() {
        placeholderapisuppport = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        return placeholderapisuppport;
    }
}
