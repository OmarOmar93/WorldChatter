package Others;

import API.APICore;
import API.WorldChatterAPI;

public final class UpdaterSystem {

    public static String newupdate, updatetitle;

    public static Boolean isUpdated() {
        if (CacheSystem.hasCache("update")) {
            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners())
                api.updateChecked((Boolean) CacheSystem.getCache("update"));
            return (Boolean) CacheSystem.getCache("update");
        }
        try {
            String[] verstring = OtherFunctions.getUrlAsString("https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/version2").split(",");
            final int update = Integer.parseInt(verstring[1].replace(".", ""));
            CacheSystem.addCache("update", update > 117);
            CacheSystem.removeCacheAfterSeconds("update", ConfigSystem.INSTANCE.getConfig().getInt("CacheTimings.update"));
            newupdate = verstring[0];
            updatetitle = verstring[2];
            boolean updatecheck = update > 117;
            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners())
                api.updateChecked(updatecheck);
            return updatecheck;
        } catch (Exception e) {
            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners())
                api.updateChecked(false);
            return null;
        }
    }
}