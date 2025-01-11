package WorldChatterCore.API;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Systems.ColorSystem;
import WorldChatterCore.Systems.UpdateSystem;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class WCA {

    private static final List<Addon> addons = new CopyOnWriteArrayList<>();
    private static final List<WCListener> listners = new CopyOnWriteArrayList<>();
    public static WCA INSTANCE;

    public WCA() {
        if (INSTANCE == null) INSTANCE = this;
    }

    public Addon createWCAddon(final String name, final String author, final String description, final String signature, final String version, final String updater, final Integer build) {
        if (addons.stream()
                .noneMatch(addon -> addon.getSignature().equalsIgnoreCase(signature))) {
            final Addon temp = (updater != null && build != null)
                    ? new Addon(name, author, description, signature.toLowerCase(), version, updater, build)
                    : new Addon(name, author, description, signature.toLowerCase(), version);

            addons.add(temp);
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(
                    ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "Detected Add-on " + ColorSystem.BLUE + name);
            UpdateSystem.INSTANCE.checkForAddonUpdate(temp, null);
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
     * @param wcListener Listener Class
     */
    public void addListener(final WCListener wcListener) {
        if (wcListener == null) return;
        listners.add(wcListener);
    }

    @Deprecated()
    public void addListener(final Addon addon, final WCListener wcListener) {
        addListener(wcListener);
        if (addon != null) MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(
                ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "Add-on is using the Legacy addListner function which is now deprecated and will be removed in the future \"" + addon.getName() + "\"");
    }

    public List<WCListener> getListeners() {
        return listners;
    }

    public List<Addon> getAddons() {
        return addons;
    }

}