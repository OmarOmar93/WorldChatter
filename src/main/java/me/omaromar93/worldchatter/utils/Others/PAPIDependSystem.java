package me.omaromar93.worldchatter.utils.Others;

import me.omaromar93.worldchatter.WorldChatter;

public class PAPIDependSystem {
    public static boolean placeholderapisuppport = false;

    public static boolean isPAPIThere(){
        placeholderapisuppport = WorldChatter.INSTANCE.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        return placeholderapisuppport;
    }
}
