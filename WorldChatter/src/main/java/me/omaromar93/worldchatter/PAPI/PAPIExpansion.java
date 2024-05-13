package me.omaromar93.worldchatter.PAPI;

import Others.ConfigSystem;
import Others.PlayerSystem;
import chatting.ChattingSystem;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.omaromar93.worldchatter.WorldChatter;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PAPIExpansion extends PlaceholderExpansion {

    public static PAPIExpansion INSTANCE;

    public PAPIExpansion() {
        INSTANCE = this;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "wc";
    }

    @Override
    public @NotNull String getAuthor() {
        return "OmarOmar93";
    }

    @Override
    public @NotNull String getVersion() {
        return WorldChatter.INSTANCE.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        final String param = params.contains("_") ? params.split("_")[0] : params;
        try {


        switch (param.toLowerCase()) {
            case "player":
                if (params.split("_")[1].equals("place"))
                    return PlayerSystem.INSTANCE.getPlayer(player.getUniqueId()).getPlace();
                return null;
            case "lockstats":
                return String.valueOf(ChattingSystem.isChatLock());
            case "config":
                return Objects.requireNonNull(ConfigSystem.INSTANCE.getConfig().get(params.split("_")[1])).toString();
            case "broadcast":
                return Objects.requireNonNull(ConfigSystem.INSTANCE.getBroadcast().get(params.split("_")[1])).toString();
            case "format":
                return Objects.requireNonNull(ConfigSystem.INSTANCE.getFormat().get(params.split("_")[1])).toString();
            case "message":
            case "messages":
                return Objects.requireNonNull(ConfigSystem.INSTANCE.getMessages().get(params.split("_")[1])).toString();
            case "security":
                return Objects.requireNonNull(ConfigSystem.INSTANCE.getSecurity().get(params.split("_")[1])).toString();
            case "texts":
            case "text":
                return Objects.requireNonNull(ConfigSystem.INSTANCE.getTexts().get(params.split("_")[1])).toString();
            default:
                return null;
        }
        } catch (final NullPointerException ignored) {
            return null;
        }
    }
}