package me.omaromar93.worldchatter.utils.Others;

import me.omaromar93.worldchatter.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public final class UpdaterSystem {

    public static int newupdate;

    public static Boolean isUpdated() {
        if (CacheSystem.hasCache("update"))
            return (Boolean) CacheSystem.getCache("update");
        try {
            final int update = Integer.parseInt(getUrlAsString("https://api.spigotmc.org/legacy/update.php?resource=101226").replace(".", "")), updateold = Integer.parseInt(Main.INSTANCE.getDescription().getVersion().replace(".", ""));
            CacheSystem.addCache("update", update > updateold);
            CacheSystem.removeCacheAfterSeconds("update", ConfigSystem.getConfig().getInt("CacheTimings.update"));
            newupdate = update;
            return update > updateold;
        } catch (Exception e) {
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
