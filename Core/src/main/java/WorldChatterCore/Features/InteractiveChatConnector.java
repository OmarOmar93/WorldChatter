package WorldChatterCore.Features;



import WorldChatterCore.API.WCA;
import WorldChatterCore.API.WCListener;
import WorldChatterCore.API.WCPlaceHolder;
import WorldChatterCore.Others.debugMode;
import WorldChatterCore.Players.Player;
import com.loohp.interactivechat.InteractiveChat;
import com.loohp.interactivechat.api.InteractiveChatAPI;
import com.loohp.interactivechat.objectholders.ICPlaceholder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InteractiveChatConnector implements WCListener {

    public static InteractiveChatConnector INSTANCE;

    public InteractiveChatConnector() {
        INSTANCE = this;
        WCA.INSTANCE.addListener(this);
    }

    @Override
    public void customPlaceholderCall(final String name, final String message, final Player player) {
        if (name.startsWith("ic_")) {
            for (final ICPlaceholder icplaceholder : InteractiveChatAPI.getICPlaceholderList()) {
                final Pattern placeholder = icplaceholder.getRawKeyword();
                final Matcher matcher = placeholder.matcher(message);
                if (matcher.find()) {
                    WCPlaceHolder.getPlaceholders().put("ic_" + icplaceholder.getName(), matcher.group(1));
                    debugMode.INSTANCE.println(matcher.group(1), debugMode.printType.INFO);
                    continue;
                }
                debugMode.INSTANCE.println("Couldn't find matching ICPlaceholder: " + icplaceholder.getName(), debugMode.printType.WARNING);
            }
        }
    }

}