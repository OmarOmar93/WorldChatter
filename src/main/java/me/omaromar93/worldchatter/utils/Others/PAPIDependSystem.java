package me.omaromar93.worldchatter.utils.Others;

import me.omaromar93.worldchatter.Main;

public class PAPIDependSystem {
    public static boolean placeholderapisuppport = false;

    public static boolean isPAPIThere(){
        placeholderapisuppport = Main.INSTANCE.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        return placeholderapisuppport;
    }
}
