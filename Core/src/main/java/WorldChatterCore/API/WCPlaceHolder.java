package WorldChatterCore.API;

import WorldChatterCore.Players.Player;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WCPlaceHolder {
    private final static Map<String, String> placeholders = new ConcurrentHashMap<>();
    private final static Pattern pattern = Pattern.compile("\\{(.*?)}");

    public static Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public static String formatMessage(String message, final Player player) {
        final Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            if (WCA.INSTANCE != null) {
                for (final WCListener listener : WCA.INSTANCE.getListeners()) {
                    listener.customPlaceholderCall(matcher.group(1), message, player);
                }
            }
            message = message.replace(matcher.group(1), placeholders.getOrDefault(matcher.group(0),""));
        }
        return message;
    }
}
