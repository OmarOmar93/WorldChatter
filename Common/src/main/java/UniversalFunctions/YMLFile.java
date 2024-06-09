package UniversalFunctions;

import Others.ConfigSystem;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@SuppressWarnings("unchecked")
public final class YMLFile {

    private final Path filePath;
    private List<String> lines;
    private Map<String, Object> data;

    public YMLFile(final @NotNull Path filePath) {
        this.filePath = filePath;
        this.data = new HashMap<>();
    }

    public void update() {
        try {
            lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            data = loadYamlData(lines);
        } catch (final IOException e) {
            ConfigSystem.INSTANCE.getLogger().severe("Couldn't load yaml from file " + filePath.toFile().getName() + ": " + e.getMessage());
            lines = new ArrayList<>();
            data = new HashMap<>();
        }
    }

    private Map<String, Object> loadYamlData(List<String> lines) {
        Map<String, Object> yamlData = new HashMap<>();
        try {
            String content = String.join("\n", lines);
            yamlData = new Yaml().load(content);
        } catch (Exception e) {
            ConfigSystem.INSTANCE.getLogger().severe("Error parsing YAML data: " + e.getMessage());
        }
        return yamlData;
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
        return o instanceof HashMap<?, ?> ? (HashMap<String, HashMap<String, Object>>) o : new HashMap<>();
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

    public Set<String> getKeys() {
        return data.keySet();
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
        if (lines == null) {
            update();
        }

        String[] keys = key.split("\\.");
        boolean found = false;
        StringBuilder currentPath = new StringBuilder();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String trimmedLine = line.trim();

            if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                continue;
            }

            String currentKey = trimmedLine.split(":")[0].trim();
            if (currentPath.length() > 0) {
                currentPath.append(".");
            }
            currentPath.append(currentKey);

            if (currentPath.toString().equals(key)) {
                int colonIndex = line.indexOf(":");
                if (colonIndex != -1) {
                    String comment = "";
                    int hashIndex = line.indexOf("#", colonIndex);
                    if (hashIndex != -1) {
                        comment = line.substring(hashIndex);
                    }
                    String newValue = " " + value;
                    lines.set(i, line.substring(0, colonIndex + 1) + newValue + " " + comment);
                }
                found = true;
                break;
            }

            if (line.endsWith(":")) {
                currentPath.append(".");
            } else if (line.endsWith(",")) {
                currentPath.setLength(currentPath.lastIndexOf("."));
            }
        }

        if (!found) {
            // Add new key if not found
            String newLine = key + ": " + value;
            lines.add(newLine);
        }

        // Update the in-memory data representation
        setInMemoryData(data, keys, value);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            ConfigSystem.INSTANCE.getLogger().severe("Error while writing in file " + filePath + ": " + e.getMessage());
        }
    }

    private void setInMemoryData(Map<String, Object> data, String[] keys, Object value) {
        Map<String, Object> current = data;
        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.computeIfAbsent(keys[i], k -> new HashMap<>());
        }
        current.put(keys[keys.length - 1], value);
    }
}