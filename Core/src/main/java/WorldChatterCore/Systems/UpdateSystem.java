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

    public UpdateSystem() {
        INSTANCE = this;
    }

    public int checkforUpdates() {
        final String[] buildInfo = Objects.requireNonNull(Util.getContentfromURl("https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/version2")).split(",");
        if (buildInfo.length > 1) {
            buildName = buildInfo[0];
            build = Integer.parseInt(buildInfo[1]);
            buildTitle = buildInfo[2];
            isDev = Boolean.parseBoolean(buildInfo[3]);
            final int currentBuild = 213;
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

    public void checkforAddonUpdates(final CommandSender sender) {
        if (WCA.INSTANCE != null) {
            for (final Addon addon : WCA.INSTANCE.getAddons()) {
                if (addon.getUpdater() != null) {
                    try {
                        final String[] buildInfo = Objects.requireNonNull(Util.getContentfromURl(addon.getUpdater())).split(",");
                        if (buildInfo.length > 1) {
                            final String addonBuildName = buildInfo[0];
                            final int addonBuild = Integer.parseInt(buildInfo[1]);
                            final boolean addonDev = Boolean.parseBoolean(buildInfo[3]);
                            final int currentBuild = addon.getBuild();
                            if (currentBuild < addonBuild) {
                                sender.sendMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + addon.getName() + " Has a " + ColorSystem.GOLD + (addonDev ? "Development" : "Stable") + ColorSystem.YELLOW + " Update available!");
                                sender.sendMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.YELLOW + addonBuildName);
                            }
                            continue;
                        }
                        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GRAY + "Unable to check for updates for " + addon.getName());
                    } catch (Exception ignored) {
                        MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Error occurred while checking " + addon.getName());
                    }
                }
            }
        }
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