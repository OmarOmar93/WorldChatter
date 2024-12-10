package WorldChatterCore.Features;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.UUID;

public class LuckPermsConnector {

    public static LuckPermsConnector INSTANCE;
    final LuckPerms api = LuckPermsProvider.get();

    public LuckPermsConnector() {
        INSTANCE = this;
    }

    public String formatMessage(final UUID uuid, final String message) {
        if (uuid == null || message == null) {
            return message; // Return original message if inputs are invalid
        }

        try {
            final User user = api.getUserManager().getUser(uuid);
            if (user == null) {
                return message; // User not found, return the original message
            }

            final @NonNull CachedMetaData metaData = user.getCachedData().getMetaData();
            final String prefix = metaData.getPrefix() != null ? metaData.getPrefix() : "";
            final String suffix = metaData.getSuffix() != null ? metaData.getSuffix() : "";

            return message.replace("{player_prefix}", prefix)
                    .replace("{player_suffix}", suffix);
        } catch (final Exception e) {
            return message; // Return the original message in case of any errors
        }
    }


    public boolean hasPermission(final UUID uuid, final String prem) {
        return Objects.requireNonNull(api.getUserManager().getUser(uuid)).getCachedData().getPermissionData().checkPermission(prem).asBoolean();
    }

}
