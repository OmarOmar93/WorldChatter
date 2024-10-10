package WorldChatterCore.Systems;

import WorldChatterCore.API.Addon;
import WorldChatterCore.API.WCA;
import WorldChatterCore.API.WCListener;
import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Others.Util;

import java.util.Objects;

public final class UpdateSystem {

    public static UpdateSystem INSTANCE;

    private String buildName, buildTitle;
    private int build;
    private boolean isDev;

    private static final int CURRENT_BUILD = 213;
    private static final String VERSION_URL = "https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/version2";

    public UpdateSystem() {}

    public int checkForUpdates() {
        try {
            final String[] buildInfo = Objects.requireNonNull(Util.getContentfromURl(VERSION_URL)).split(",");
            if (buildInfo.length > 1) {
                buildName = buildInfo[0];
                build = Integer.parseInt(buildInfo[1]);
                buildTitle = buildInfo[2];
                isDev = Boolean.parseBoolean(buildInfo[3]);
                return Integer.compare(CURRENT_BUILD, build);
            }
        } catch (Exception e) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Error occurred while checking for updates.");
        }
        return -1;
    }

    public void checkForAddonUpdates(final CommandSender sender) {
        if (WCA.INSTANCE == null) return;
        for (final Addon addon : WCA.INSTANCE.getAddons()) {
            if (addon.getUpdater() == null) continue;

            try {
                final String[] buildInfo = Objects.requireNonNull(Util.getContentfromURl(addon.getUpdater())).split(",");
                if (buildInfo.length > 1) {
                    final int addonBuild = Integer.parseInt(buildInfo[1]);
                    if (addon.getBuild() < addonBuild) {
                        final String message = ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW +
                                addon.getName() + " has a " + (Boolean.parseBoolean(buildInfo[2]) ? "Development" : "Stable") +
                                " Update available! " + buildInfo[0];
                        sender.sendMessage(message);
                    }
                } else {
                    sendConsoleUpdateError(addon.getName());
                }
            } catch (Exception e) {
                sendConsoleUpdateError(addon.getName());
            }
        }
    }

    public void messageCheck(final CommandSender sender) {
        final int updateStatus = checkForUpdates();
        String message;
        switch (updateStatus) {
            case 0:
                message = ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "You're using the latest version of the plugin!";
                break;
            case 1:
                message = ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "A " + (isDev ? "Development" : "Stable") +
                    " version is available! " + buildTitle + " - " + buildName + " -> https://www.spigotmc.org/resources/worldchatter-1-1-1-21.101226/updates";
                break;
            case 2:
                message = ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.BLUE + "You're using an " + ColorSystem.AQUA + "Early-Access" +
                    ColorSystem.BLUE + " version of WorldChatter!";
                break;
            default:
                message = ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to check for updates.";
        }

        if (sender != null) {
            sender.sendMessage(message);
        } else {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(message);
        }

        if (WCA.INSTANCE != null) {
            for (final WCListener listener : WCA.INSTANCE.getListeners()) {
                listener.updateChecked(sender);
            }
        }
    }

    private void sendConsoleUpdateError(String addonName) {
        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(
                ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GRAY + "Unable to check for updates for " + addonName);
    }

    public boolean isDev() {
        return isDev;
    }

    public String getBuildName() {
        return buildName;
    }

    public int getBuild() {
        return build;
    }

    public String getBuildTitle() {
        return buildTitle;
    }
}
