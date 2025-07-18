package WorldChatterCore.Systems;

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

    private static final int CURRENT_BUILD = 248;

    private static final boolean DEVBUILD = true;
    private static final String VERSION_URL = "https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/version2";

    public UpdateSystem() {
        INSTANCE = this;
    }

    public int checkForUpdates() {
        try {
            final String[] buildInfo = Objects.requireNonNull(Util.getContentfromURl(VERSION_URL)).split(",");
            if (buildInfo.length > 1) {
                buildName = buildInfo[0];
                build = Integer.parseInt(buildInfo[1]);
                buildTitle = buildInfo[2];
                isDev = Boolean.parseBoolean(buildInfo[3].trim());
                final int compare = Integer.compare(CURRENT_BUILD, build);
                if (compare == -1 && (isDev && !ConfigSystem.INSTANCE.getSystem().getBoolean("DevelopmentUpdates"))) {
                    return 0;
                }
                if (CURRENT_BUILD == build) {
                    if (!isDev && DEVBUILD) return -1;
                }
                return compare;
            }
        } catch (Exception e) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Error occurred while checking for updates.");
        }
        return -2;
    }

    public void messageCheck(final CommandSender sender) {
        String message = ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to check for updates.";
        switch (checkForUpdates()) {
            case 0:
                message = ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "You're using the latest version of the plugin!";
                break;
            case -1:
                message = ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "A " + (isDev ? "Development" : "Stable") +
                        " version is available! " + ColorSystem.GREEN + buildTitle + ColorSystem.GRAY + " - " + buildName + ColorSystem.YELLOW + " -> https://modrinth.com/plugin/worldchatter/";
                break;
            case 1:
                message = ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.BLUE + "You're using an " + ColorSystem.AQUA + "Early-Access" +
                        ColorSystem.BLUE + " version of WorldChatter!";
                break;
        }

        if (sender != null) sender.sendMessage(message);
        else MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(message);

        for (final WCListener listener : WCA.INSTANCE.getListeners()) listener.updateChecked(sender);

    }

    public boolean isDev() {
        return isDev;
    }

    public String getBuildName() {
        return buildName;
    }

    public int getCurrentBuild() {
        return CURRENT_BUILD;
    }

    public int getBuild() {
        return build;
    }

    public String getBuildTitle() {
        return buildTitle;
    }
}
