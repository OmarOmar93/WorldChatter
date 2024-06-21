package Others;

import API.APICore;
import API.WorldChatterAPI;

public final class UpdaterSystem {

    public static String newupdate, updatetitle;

    public static Boolean needsUpdate, isDev;

    public static Boolean isUpdated() {
        if (newupdate != null && updatetitle != null && needsUpdate != null && isDev != null) {
            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners())
                api.updateChecked(needsUpdate,isDev);
            return needsUpdate;
        }

        try {
            final String[] verstring = OtherFunctions.getUrlAsString("https://raw.githubusercontent.com/OmarOmar93/WCVersion/main/version2").split(",");
            final int update = Integer.parseInt(verstring[1].replace(".", ""));

            newupdate = verstring[0];
            updatetitle = verstring[2];
            isDev = Boolean.parseBoolean(verstring[3]);
            needsUpdate = update > 120;

            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners())
                api.updateChecked(needsUpdate,isDev);
            return needsUpdate;
        } catch (final Exception ignored) {
            for (final WorldChatterAPI api : APICore.INSTANCE.getListeners())
                api.updateChecked(false,false);
            return null;
        }
    }
}