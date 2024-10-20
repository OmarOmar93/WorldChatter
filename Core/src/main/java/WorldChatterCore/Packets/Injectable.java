package WorldChatterCore.Packets;


import WorldChatterCore.Packets.Sound.SoundManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Injectable {

    private boolean pitch;
    private double x, y, z;
    private final Channel channel;
    private final int protocolVersion;
    private final List<Integer> positionPacketIDs;

    public Injectable(final Channel channel, final String baseName, final String name, final int protocolVersion) {
        this.channel = channel;
        this.protocolVersion = protocolVersion;

        positionPacketIDs = new CopyOnWriteArrayList<>();
        final int soundPacketID = init(protocolVersion);

        channel.pipeline().addBefore(baseName, name, new ChannelDuplexHandler() {
            @Override
            public void channelRead(final ChannelHandlerContext ctx, final Object packet) throws Exception {
                if (packet instanceof ByteBuf) {
                    decodeByteArray(((ByteBuf) packet).copy());
                }
                super.channelRead(ctx, packet);
            }
        });

        channel.pipeline().addLast("sound_packet_encoder", new MessageToByteEncoder<SoundPacket>() {
            @Override
            protected void encode(final ChannelHandlerContext ctx, final SoundPacket msg, final ByteBuf out) {
                out.writeByte(soundPacketID);

                if (protocolVersion >= 761) {
                    writeVarInt(out, 0);
                }

                final byte[] soundBytes = msg.getName().getBytes(StandardCharsets.UTF_8);
                out.writeByte(soundBytes.length);
                out.writeBytes(soundBytes);

                if (protocolVersion >= 761) {
                    out.writeBoolean(false);
                }

                if (soundPacketID != 0x29) writeVarInt(out, msg.getCategory());

                out.writeInt((int) (x * 8.0));
                out.writeInt((int) (y * 8.0));
                out.writeInt((int) (z * 8.0));

                out.writeFloat(msg.getVolume());
                if (pitch) {
                    out.writeFloat((float) (1.5 * msg.getPitch() + 0.5));
                } else {
                    out.writeByte((int) (msg.getPitch() * 63));
                }

                if (protocolVersion >= 759) {
                    out.writeLong(0L);
                }
            }
        });
    }

    protected void sendSoundPacket(final String soundName, final float volume, final float pitch) {
        channel.writeAndFlush(new SoundPacket(SoundManager.getSound(soundName, protocolVersion), volume, pitch, 0));
    }

    private void decodeByteArray(final ByteBuf byteBuf) {
        try {
            final int packetId = readVarInt(byteBuf);

            if (positionPacketIDs.contains(packetId)) {
                x = byteBuf.readDouble();
                y = byteBuf.readDouble();
                z = byteBuf.readDouble();
            }
        } finally {
            byteBuf.release();
        }
    }

    private int readVarInt(final ByteBuf buf) {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = buf.readByte();
            result |= (read & 0b01111111) << (7 * numRead);
            numRead++;
        } while ((read & 0b10000000) != 0 && numRead <= 5);
        if (numRead > 5) {
            throw new Error("VarInt is too big");
        }
        return result;
    }

    private void writeVarInt(final ByteBuf out, int value) {
        while ((value & 0xFFFFFF80) != 0L) {
            out.writeByte((value & 0x7F) | 0x80);
            value >>>= 7;
        }

        out.writeByte(value & 0x7F);
    }

    private int init(final int protocolVersion) {
        pitch = protocolVersion > 110;

        if (protocolVersion <= 47) { // 1.7 and 1.8
            positionPacketIDs.addAll(Arrays.asList(0x04, 0x06));
            return 0x29;
        }


        // POSITIONS

        if ((protocolVersion <= 316 && protocolVersion >= 107)) { // 1.9 -> 1.11.2
            positionPacketIDs.addAll(Arrays.asList(0x0C, 0x0D));
        }

        if ((protocolVersion <= 340 && protocolVersion >= 335)) { // 1.12 -> 1.12.2
            positionPacketIDs.add(0x0D);
        }

        if (protocolVersion <= 404 && protocolVersion >= 393) { // 1.13 -> 1.13.2
            positionPacketIDs.add(0x10);
        }

        if ((protocolVersion <= 578 && protocolVersion >= 477) || (protocolVersion <= 758 && protocolVersion >= 755)) { // 1.14 -> 1.15.2 and 1.17 -> 1.18.2
            positionPacketIDs.add(0x11);
        }

        if (protocolVersion <= 754 && protocolVersion >= 735) { // 1.16 -> 1.16.5
            positionPacketIDs.add(0x12);
        }

        if (protocolVersion == 759 || protocolVersion == 761) { // 1.19 somehow 1.19.3
            positionPacketIDs.add(0x13);
        }

        if (protocolVersion == 760 || protocolVersion == 762 || protocolVersion == 763) { // 1.19.1 -> 1.19.2 somehow 1.19.4 and somehow 1.20 -> 1.20.1
            positionPacketIDs.add(0x14);
        }

        if (protocolVersion == 764) { // 1.20.2
            positionPacketIDs.add(0x16);
        }

        if (protocolVersion == 765) { // 1.20.3 -> 1.20.4
            positionPacketIDs.add(0x17);
        }

        if (protocolVersion >= 766) { // 1.20.5 -> latest
            positionPacketIDs.add(0x1A);
        }

        // SOUNDS

        // 1.9 -> 1.12.2 and 1.17 -> 1.18.2
        if ((protocolVersion <= 340 && protocolVersion >= 107) || (protocolVersion <= 758 && protocolVersion >= 755)) {
            return 0x19;
        }

        if (protocolVersion <= 578 && protocolVersion >= 393) { // 1.13 -> 1.15.2
            return 0x1A;
        }

        if (protocolVersion <= 754 && protocolVersion >= 735) { // 1.16 -> 1.16.5
            return 0x18;
        }

        if (protocolVersion == 759) { // 1.19
            return 0x16;
        }

        if (protocolVersion == 760) { // 1.19.1 -> 1.19.2
            return 0x17;
        }

        if (protocolVersion == 761) { // 1.19.3
            return 0x5E;
        }

        if (protocolVersion == 763 || protocolVersion == 762) { // 1.19.4 -> 1.20.1
            return 0x62;
        }

        if (protocolVersion == 764) { // 1.20.2
            return 0x64;
        }

        if (protocolVersion == 765) { // 1.20.3 -> 1.20.4
            return 0x66;
        }

        if (protocolVersion >= 766) { // 1.20.5 -> latest
            return 0x68;
        }

        return 0;
    }

}
