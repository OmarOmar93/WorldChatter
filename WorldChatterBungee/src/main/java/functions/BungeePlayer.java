package functions;

import UniversalFunctions.Player;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.Sound;
import methods.MoreFormat;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeePlayer implements Player {

    private final ProxiedPlayer player;
    private final ProtocolizePlayer Pplayer;

    public BungeePlayer(final ProxiedPlayer player) {
        this.player = player;
        Pplayer = Protocolize.playerProvider().player(player.getUniqueId());
    }

    @Override
    public boolean isConsole() {
        return false;
    }

    @Override
    public void sendMessage(final String message) {
        player.sendMessage(new TextComponent(MoreFormat.FormatMore(message)));
    }

    @Override
    public boolean hasPermission(final String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void playSound(String soundName, float volume, float pitch) {
        Pplayer.playSound(Sound.valueOf(soundName), SoundCategory.MASTER, 1f, 1f);
    }


    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getPlace() {
        return player.getServer().getInfo().getName();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }
}
