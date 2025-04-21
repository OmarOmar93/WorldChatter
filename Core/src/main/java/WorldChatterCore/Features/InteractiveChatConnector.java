package WorldChatterCore.Features;



import WorldChatterCore.API.WCA;
import WorldChatterCore.API.WCListener;
import WorldChatterCore.API.WCPlaceHolder;
import WorldChatterCore.Players.Player;
import com.loohp.interactivechat.InteractiveChat;
import com.loohp.interactivechat.objectholders.ICPlaceholder;

public class InteractiveChatConnector implements WCListener {

    public static InteractiveChatConnector INSTANCE;

    public InteractiveChatConnector() {
        INSTANCE = this;
        WCA.INSTANCE.addListener(this);
    }

    @Override
    public void customPlaceholderCall(String name, String message, Player player) {
        for (ICPlaceholder icplaceholder : InteractiveChat.placeholderList.values()) {
            WCPlaceHolder.getPlaceholders().put("ic_" + icplaceholder.getName(), icplaceholder.getRawKeyword().matcher(message).group());
        }
    }

}
