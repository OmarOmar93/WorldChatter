package WorldChatterCore.Packets;

public final class SoundPacket {

    private final long seed;
    private final String name;
    private final int category;
    private final float volume, pitch;

    public SoundPacket(final String name, final float volume, final float pitch, final int category, final long seed) {
        this.name = name;

        this.volume = volume;
        this.pitch = pitch;

        this.category = category;

        this.seed = seed;
    }

    public String getName() {
        return name;
    }

    public float getPitch() {
        return pitch;
    }

    public float getVolume() {
        return volume;
    }

    public int getCategory() {
        return category;
    }

    public long getSeed() {
        return seed;
    }
}

