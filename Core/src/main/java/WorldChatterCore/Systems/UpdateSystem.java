package WorldChatterCore.Systems;

import WorldChatterCore.API.WCA;
import WorldChatterCore.API.WCListener;
import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Connectors.Interfaces.CommandSender;
import WorldChatterCore.Others.Util;

public final class UpdateSystem {

    public static UpdateSystem INSTANCE;

    private String buildName, buildTitle;

    private int build;

    private boolean isDev;

    public UpdateSystem() {
        INSTANCE = this;
    }

    public int checkforUpdates() {
        final String[] buildInfo = Util.getContentfromURl("https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/version2").split(",");
        if (buildInfo.length > 1) {
            buildName = buildInfo[0];
            build = Integer.parseInt(buildInfo[1]);
            buildTitle = buildInfo[2];
            isDev = Boolean.parseBoolean(buildInfo[3]);
            final int currentBuild = 206;
            if (currentBuild == build) {
                return 0;
            }
            if (currentBuild < build) {
                return 1;
            }
            return 2;
        }
        return -1;
    }


    public void messageCheck(final CommandSender sender) {
        if (sender != null) {
            switch (checkforUpdates()) {
                case 0:
                    sender.sendMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "You're using the latest version of the plugin!");
                    return;
                case 1:
                    sender.sendMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "There is the a " + ColorSystem.GOLD + (isDev ? "Development" : "Stable") + ColorSystem.YELLOW + " Version Available!");
                    sender.sendMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + buildTitle + " - " + buildName);
                    sender.sendMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "-> https://www.spigotmc.org/resources/worldchatter-1-1-1-21.101226/updates");
                    return;
                case 2:
                    sender.sendMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.BLUE + "You're using an " + ColorSystem.AQUA + "Early-Access" + ColorSystem.BLUE + " version of WorldChatter!");
                    return;
                case -1:
                    sender.sendMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to check for updates.");
                    return;
            }
        }
        switch (checkforUpdates()) {
            case 0:
                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + "You're using the latest version of the plugin!");
                return;
            case 1:
                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "There is the a " + ColorSystem.GOLD + (isDev ? "Development" : "Stable") + ColorSystem.YELLOW + " Version Available!");
                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + buildTitle + " - " + buildName);
                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + "-> https://www.spigotmc.org/resources/worldchatter-1-1-1-21.101226/updates");
                return;
            case 2:
                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.BLUE + "You're using an " + ColorSystem.AQUA + "Early-Access" + ColorSystem.BLUE + " version of WorldChatter!");
                return;
            case -1:
                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to check for updates.");
        }
        if(WCA.INSTANCE != null) for(final WCListener listener: WCA.INSTANCE.getListeners()) {
            listener.updateChecked(sender);
        }
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