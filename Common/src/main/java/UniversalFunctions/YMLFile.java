package UniversalFunctions;

import Others.ConfigSystem;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public final class YMLFile {

    private final Yaml yaml;
    private final Path filePath;
    private HashMap<String, Object> data;

    public YMLFile(final @NotNull Path filePath) {
        this.filePath = filePath;

        final DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        yaml = new Yaml(options);
    }

    public void update() {
        try {
            try (InputStream inputStream = Files.newInputStream(filePath)) {
                data = yaml.load(inputStream);
            }

            if (data == null) data = new HashMap<>();
        } catch (final IOException e) {
            ConfigSystem.INSTANCE.getLogger().severe("Couldn't load yaml from file " + filePath.toFile().getName() + ": " + e.getMessage());
            data = new HashMap<>();
        }
    }

    public boolean getBoolean(final @NotNull String key) {
        final Object o = get(key);

        return o != null && Boolean.parseBoolean(o.toString());
    }

    public boolean getBoolean(final @NotNull String key, final boolean def) {
        final Object o = get(key);

        return o != null ? Boolean.parseBoolean(o.toString()) : def;
    }

    public int getInt(final @NotNull String key) {
        final Object o = get(key);

        return o instanceof Integer ? Integer.parseInt(o.toString()) : 0;
    }

    public int getInt(final @NotNull String key, final int def) {
        final Object o = get(key);

        return o instanceof Integer ? Integer.parseInt(o.toString()) : def;
    }

    public float getFloat(final @NotNull String key, final float def) {
        final Object o = get(key);

        return o instanceof Float ? Float.parseFloat(o.toString()) : def;
    }

    public HashMap<String, HashMap<String, Object>> getConfigurationSection(final String key) {
        final Object o = get(key);

        return o instanceof HashMap<?,?> ? (HashMap<String, HashMap<String, Object>>) o : new HashMap<>();
    }

    public List<String> getStringList(final @NotNull String key) {
        final Object o = get(key);

        return o instanceof List<?> ? (List<String>) o : new ArrayList<>();
    }

    public Object get(final @NotNull String key) {
        Map<String, Object> cache = new HashMap<>(data);

        for (final String k : key.split("\\.")) {
            if (cache.containsKey(k)) {
                final Object value = cache.get(k);
                if (value instanceof Map) {
                    cache = (Map<String, Object>) value;
                    continue;
                }

                return value;
            }

            return null;
        }

        return cache;
    }

    public Object get(final @NotNull String key, final @NotNull Object def) {
        Map<String, Object> cache = new HashMap<>(data);

        for (final String k : key.split("\\.")) {
            if (cache.containsKey(k)) {
                final Object value = cache.get(k);
                if (value instanceof Map) {
                    cache = (Map<String, Object>) value;
                    continue;
                }

                return value;
            }

            return def;
        }
        return cache;
    }



    public void set(final @NotNull String key, final Object value) {
        final String[] keys = key.split("\\.");
        Map<String, Object> cache = data;

        for (int i = 0; i < keys.length - 1; i++) {
            final String k = keys[i];
            if (!cache.containsKey(k) || !(cache.get(k) instanceof Map)) {
                cache.put(k, new LinkedHashMap<>());
            }

            cache = (Map<String, Object>) cache.get(k);
        }

        if (value == null) {
            cache.remove(keys[keys.length - 1]);
        } else {
            cache.put(keys[keys.length - 1], value);
        }

        try (final Writer writer = new OutputStreamWriter(Files.newOutputStream(filePath), StandardCharsets.UTF_8)) {
            yaml.dump(data, writer);
        } catch (final IOException e) {
            ConfigSystem.INSTANCE.getLogger().severe("Error while writing in file " + filePath + ": " + e.getMessage());
        }
    }

}