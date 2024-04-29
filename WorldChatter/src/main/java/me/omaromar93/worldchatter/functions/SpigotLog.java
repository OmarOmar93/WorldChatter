package me.omaromar93.worldchatter.functions;

import UniversalFunctions.UniLog;
import org.bukkit.Bukkit;

public class SpigotLog implements UniLog {
    @Override
    public void sendmessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }
}
