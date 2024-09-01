package me.omaromar93.wcvelocity.Parent;


import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;

public class VelocitySound {

    private final ProxyServer proxyServer;

    public VelocitySound(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    public void playSoundForPlayer(Player player, String soundName, float volume, float pitch) {
        if (player.getProtocolVersion().getProtocol() == 47) { // Protocol version 47 corresponds to Minecraft 1.8.8
            try {
                // Get the server the player is connected to
                RegisteredServer server = player.getCurrentServer().get().getServer();

                // Build the packet manually
                Class<?> packetPlayOutNamedSoundEffectClass = getNMSClass("PacketPlayOutNamedSoundEffect");
                Constructor<?> constructor = packetPlayOutNamedSoundEffectClass.getConstructor(String.class, double.class, double.class, double.class, float.class, float.class);

                Object packet = constructor.newInstance(soundName, player.getPosition().x(), player.getPosition().y(), player.getPosition().z(), volume, pitch);

                sendPacket(player, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPacket(Player player, Object packet) throws Exception {
        Class<?> entityPlayerClass = getNMSClass("EntityPlayer");
        Class<?> playerConnectionClass = getNMSClass("PlayerConnection");

        Object handle = getHandle(player);

        Field playerConnectionField = entityPlayerClass.getDeclaredField("playerConnection");
        Object playerConnection = playerConnectionField.get(handle);

        Method sendPacketMethod = playerConnectionClass.getMethod("sendPacket", getNMSClass("Packet"));
        sendPacketMethod.invoke(playerConnection, packet);
    }

    private Object getHandle(Player player) throws Exception {
        Method getHandleMethod = player.getClass().getMethod("getHandle");
        return getHandleMethod.invoke(player);
    }

    private Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + getServerVersion() + "." + name);
    }

    private String getServerVersion() {
        return "v1_8_R3"; // Version string for 1.8.8
    }
}
