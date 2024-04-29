package API;

import UniversalFunctions.Plugin;
import UniversalFunctions.UniLogHandler;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class APICore {

    public static APICore INSTANCE;
    private final HashMap<Plugin, Addon> addons = new HashMap<>();

    public APICore() {
        INSTANCE = this;
    }

    @SuppressWarnings("ALL")
    public void addListener(final @NotNull Plugin plugin, final @NotNull WorldChatterAPI worldChatterAPI) {
        if (addons.containsKey(plugin)) {
            addons.get(plugin).addListener(worldChatterAPI);
            return;
        }

        final Addon addon = new Addon(plugin);

        addon.addListener(worldChatterAPI);

        addons.put(plugin, addon);

        UniLogHandler.INSTANCE.sendMessage(ChatColor.WHITE + "(WorldChatter Addons) " + ChatColor.YELLOW + "Detected Addon -> " + ChatColor.BLUE + plugin.getName());
    }

    public Collection<Addon> getAddons() {
        return addons.values();
    }

    public List<WorldChatterAPI> getListeners() {
        final List<WorldChatterAPI> list = new ArrayList<>();

        for (final Addon addon : getAddons()) {
            list.addAll(addon.getListeners());
        }

        return list;
    }
}
