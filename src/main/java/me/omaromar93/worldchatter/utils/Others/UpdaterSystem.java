package me.omaromar93.worldchatter.utils.Others;

import me.omaromar93.worldchatter.WorldChatter;
import me.omaromar93.worldchatter.utils.API.WorldChatterAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public final class UpdaterSystem {

    public static String newupdate;

    public static Boolean isUpdated() {
        if (CacheSystem.hasCache("update")) {
            for (final WorldChatterAPI api : WorldChatter.INSTANCE.getAPICore().getListeners())
                api.updateChecked((Boolean) CacheSystem.getCache("update"));
            return (Boolean) CacheSystem.getCache("update");
        }
        try {
            String verstring = getUrlAsString("https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/version");
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

    public static String getUrlAsString(final String url) throws IOException {
        URL url2 = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(url2.openStream()));
        String line;
        if ((line = in.readLine()) != null)
            return line;
        in.close();
        return url;
    }
}
