package WorldChatterCore.API;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.UpdateSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class WCA {

    private static final Map<Addon, List<WCListener>> addonsAndListeners = new ConcurrentHashMap<>();
    public static WCA INSTANCE;

    public WCA() {
        if (INSTANCE == null) INSTANCE = this;
    }

    public Addon createWCAddon(final String name, final String author, final String description, final String signature, final String version, final String updater, final Integer build) {
        if (addonsAndListeners.keySet().stream()
                .noneMatch(addon -> addon.getSignature().equalsIgnoreCase(signature))) {
            final Addon temp = (updater != null && build != null)
                    ? new Addon(name, author, description, signature.toLowerCase(), version, updater, build)
                    : new Addon(name, author, description, signature.toLowerCase(), version);

            addonsAndListeners.put(temp, new CopyOnWriteArrayList<>());
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(
                    ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "Detected Add-on " + ColorSystem.BLUE + name);
            UpdateSystem.INSTANCE.checkForAddonUpdate(temp,null);
            return temp;
        }

        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(
                ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "Add-on detected with same signature \""
                        + signature.toLowerCase() + "\" " + ColorSystem.YELLOW + "Try changing it into a unique signature!");
        return null;
    }

    @Deprecated()
    public Addon createWCAddon(final String name, final String author, final String description, final String signature, final String version) {
        return createWCAddon(name, author, description, signature, version, null, null);
    }


    /**
     *
     * @param wcListener Listener Class
     */
    public void addListener(final Addon addon, final WCListener wcListener) {
        if (!addonsAndListeners.containsKey(addon)) {
            MainPluginConnector.INSTANCE.getWorldChatter()
                    .sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Add-on does not exist");
            return;
        }
        if(!addonsAndListeners.get(addon).contains(wcListener)) {
            addonsAndListeners.get(addon).add(wcListener);
            return;
        }
        MainPluginConnector.INSTANCE.getWorldChatter()
                .sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + wcListener + ColorSystem.YELLOW + " already exists!");
    }

    public List<WCListener> getListeners() {
        final List<WCListener> list = new ArrayList<>();
        for(final Addon addon: addonsAndListeners.keySet()) {
            list.addAll(addonsAndListeners.get(addon));
        }
        return list;
    }

    public Set<Addon> getAddons() {
        return addonsAndListeners.keySet();
    }

}