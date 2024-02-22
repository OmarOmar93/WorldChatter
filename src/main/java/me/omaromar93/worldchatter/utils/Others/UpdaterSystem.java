package me.omaromar93.worldchatter.utils.Others;

import me.omaromar93.worldchatter.WorldChatter;
import me.omaromar93.worldchatter.utils.API.WorldChatterAPI;

public final class UpdaterSystem {

    public static String newupdate;

    public static Boolean isUpdated() {
        if (CacheSystem.hasCache("update")) {
            for (final WorldChatterAPI api : WorldChatter.INSTANCE.getAPICore().getListeners())
                api.updateChecked((Boolean) CacheSystem.getCache("update"));
            return (Boolean) CacheSystem.getCache("update");
        }
        try {
            String verstring = OtherFunctions.getUrlAsString("https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/version");
            final int update = Integer.parseInt(verstring.replace(".", "")), updateold = Integer.parseInt(WorldChatter.INSTANCE.getDescription().getVersion().replace(".", ""));
            CacheSystem.addCache("update", update > updateold);
            CacheSystem.removeCacheAfterSeconds("update", ConfigSystem.getConfig().getInt("CacheTimings.update"));
            newupdate = verstring;
            boolean updatecheck = update > updateold;
            for (final WorldChatterAPI api : WorldChatter.INSTANCE.getAPICore().getListeners())
                api.updateChecked(updatecheck);
            return updatecheck;
        } catch (Exception e) {
            for (final WorldChatterAPI api : WorldChatter.INSTANCE.getAPICore().getListeners())
                api.updateChecked(false);
            return null;
        }
    }
}
