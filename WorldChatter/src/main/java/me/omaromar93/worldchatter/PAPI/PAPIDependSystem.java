package me.omaromar93.worldchatter.PAPI;

import org.bukkit.Bukkit;

public class PAPIDependSystem {
    public boolean placeholderapisuppport = false;

    public static PAPIDependSystem INSTANCE;


    public PAPIDependSystem(){
        INSTANCE = this;
    }

    public boolean isPAPIThere() {
        placeholderapisuppport = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        return placeholderapisuppport;
    }
}
