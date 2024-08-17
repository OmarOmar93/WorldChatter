package WorldChatterCore.API;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Systems.ColorSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class WCA {

    private final HashMap<Addon, ArrayList<WCListener>> addonsAndListeners;
    public static WCA INSTANCE;


    public WCA() {
        if (INSTANCE == null) INSTANCE = this;
        addonsAndListeners = new HashMap<>();
    }

    /**
     *
     * @param name Addon's name
     * @param author Addon's Author
     * @param description Description of the Addon
     * @param signature Addon's Signature (Can't be duplicated)
     * @param version Addon's version
     */
    public void createWCAddon(final String name, final String author, final String description, final String signature, final String version) {
        boolean b = false;
        for (Addon addon : addonsAndListeners.keySet()) {
            if (addon.getSignature().equals(signature.toLowerCase())) {
                b = true;
                break;
            }
        }
        if (!b) {
            addonsAndListeners.put(new Addon(name, author, description, signature.toLowerCase(), version),new ArrayList<>());
            MainPluginConnector.INSTANCE.getWorldChatter()
                    .sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "Detected Addon " + ColorSystem.BLUE + name);
            return;
        }
        MainPluginConnector.INSTANCE.getWorldChatter()
                .sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "Addon detected with same signature \"" + signature.toLowerCase() + "\" "
                        + ColorSystem.YELLOW + "Try changing it into a unique signature!");
    }

    /**
     *
     * @param wcListener Listener Class
     */
    public void addListener(final Addon addon, final WCListener wcListener) {
        if (!addonsAndListeners.containsKey(addon)) {
            MainPluginConnector.INSTANCE.getWorldChatter()
                    .sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Addon does not exist");
            return;
        }
        if(!addonsAndListeners.get(addon).contains(wcListener)) {
            addonsAndListeners.get(addon).add(wcListener);
            return;
        }
        MainPluginConnector.INSTANCE.getWorldChatter()
                .sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + wcListener + ColorSystem.YELLOW + " already exists!");
    }

    public ArrayList<WCListener> getListeners() {
        final ArrayList<WCListener> list = new ArrayList<>();
        for(Addon addon: addonsAndListeners.keySet()) {
            list.addAll(addonsAndListeners.get(addon));
        }
        return list;
    }

    public Set<Addon> getAddons() {
        return addonsAndListeners.keySet();
    }

}