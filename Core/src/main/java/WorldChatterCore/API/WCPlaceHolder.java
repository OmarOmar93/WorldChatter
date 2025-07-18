package WorldChatterCore.API;

import WorldChatterCore.Others.debugMode;
import WorldChatterCore.Players.Player;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WCPlaceHolder {
    private final static Map<String, String> placeholders = new ConcurrentHashMap<>();
    private final static Pattern pattern = Pattern.compile("\\{(.*?)}");

    public static void register(final String name) {
        register(name, null);
    }

    public static void register(final String name, final String defvalue) {
        if (placeholders.containsKey(name.toLowerCase())) {
            debugMode.INSTANCE.println("A WorldChatter Placeholder is already registered with that name!", debugMode.printType.WARNING);
            return;
        }
        placeholders.put(name.toLowerCase(), defvalue);
    }

    public static void setResult(final String name, final String value) {
        if (placeholders.containsKey(name)) placeholders.put(name, value);

    }

    public static String formatMessageDoNotUse(String message, final Player player) {
        final Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            if (!placeholders.containsKey(matcher.group(1))) continue;
            for (final WCListener listener : WCA.INSTANCE.getListeners()) listener.customPlaceholderCall(matcher.group(1), message, player);
            message = message.replace("{" + matcher.group(1) + "}", placeholders.get(matcher.group(0)));
        }
        return message;
    }
}
