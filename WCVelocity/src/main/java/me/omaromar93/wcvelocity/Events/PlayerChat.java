package me.omaromar93.wcvelocity.Events;

import WorldChatterCore.Connectors.InterfaceConnectors.ChatEventConnector;
import WorldChatterCore.Players.PlayerHandler;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;


public final class PlayerChat {
    @Subscribe(order = PostOrder.LAST)
    public void onPlayerChat(final PlayerChatEvent event) {
        if (!event.getResult().isAllowed()) return;
        new ChatEventConnector(PlayerHandler.INSTANCE.getPlayerUUID(event.getPlayer().getUniqueId()), event.getMessage());
        event.setResult(PlayerChatEvent.ChatResult.denied());
    }
}
