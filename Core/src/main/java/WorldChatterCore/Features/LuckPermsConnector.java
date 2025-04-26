package WorldChatterCore.Features;

import WorldChatterCore.Others.debugMode;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.UUID;

public final class LuckPermsConnector {

    public static LuckPermsConnector INSTANCE;
    final LuckPerms api = LuckPermsProvider.get();

    public LuckPermsConnector() {
        INSTANCE = this;
    }


    public CachedMetaData getMetaData(final UUID uuid) {
        try {
            final User user = api.getUserManager().getUser(uuid);
            if (user == null) {
                debugMode.INSTANCE.println("couldn't find the user's data with UUID \"" + uuid + "\"", debugMode.printType.WARNING);
                return null; // User not found, return the original message
            }

            return user.getCachedData().getMetaData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getPrefix(final CachedMetaData metaData) {
        return metaData.getPrefix() != null ? metaData.getPrefix() : "";
    }

    public String getSuffix(final CachedMetaData metaData) {
        return metaData.getSuffix() != null ? metaData.getSuffix() : "";
    }


    public boolean hasPermission(final UUID uuid, final String prem) {
        return Objects.requireNonNull(api.getUserManager().getUser(uuid)).getCachedData().getPermissionData().checkPermission(prem).asBoolean();
    }

}