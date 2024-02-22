package me.omaromar93.worldchatter.utils.API;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Addon {

    private final List<WorldChatterAPI> listeners;
    private final String name, description, authors;

    public Addon(final Plugin plugin) {
        name = plugin.getName();
        description = plugin.getDescription().getDescription();
        authors = String.join(", ", plugin.getDescription().getAuthors());
        listeners = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public void addListener(final WorldChatterAPI listener) {
        if (!listeners.contains(listener)) listeners.add(listener);
    }

    public List<WorldChatterAPI> getListeners() {
        return listeners;
    }
}