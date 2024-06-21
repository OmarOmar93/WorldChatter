package UniversalFunctions;

import Others.ConfigSystem;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

@SuppressWarnings("unchecked")
public final class YMLFile {

    private final File filePath;
    private final InputStream is;
    private Map<String, Object> data;
    private final Map<String, Object> defaultData;
    private final Map<String, String> comments;
    private final Map<String, String> inlineComments;
    private final boolean quoteKeys;
    private final Set<String> emptyModeKeys;

    public YMLFile(final File file, final InputStream is, boolean quoteKeys, String emptyMode) {
        this.is = is;
        this.filePath = file;
        this.quoteKeys = quoteKeys;
        this.emptyModeKeys = new HashSet<>(Arrays.asList(emptyMode.split(",")));

        data = new HashMap<>();
        comments = new LinkedHashMap<>();
        inlineComments = new LinkedHashMap<>();
        try {
            defaultData = loadYamlData(getISLines());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        try {
            if (filePath.exists()) {
                List<String> lines = readAllLines(filePath);
                data = loadYamlData(lines);
            } else {
                data = new HashMap<>();
            }

            updateIfNeeded(defaultData, data, "");

            saveYamlData(data, filePath);
        } catch (final IOException e) {
            ConfigSystem.INSTANCE.getLogger().severe("Couldn't load yaml from file " + filePath.getName() + ": " + e.getMessage());
        }
    }

    public boolean getBoolean(final String key) {
        final Object o = get(key);
        return o != null && Boolean.parseBoolean(o.toString());
    }

    public boolean getBoolean(final String key, final boolean def) {
        final Object o = get(key);
        return o != null ? Boolean.parseBoolean(o.toString()) : def;
    }

    public int getInt(final String key) {
        final Object o = get(key);
        return o instanceof Integer ? Integer.parseInt(o.toString()) : 0;
    }

    public int getInt(final String key, final int def) {
        final Object o = get(key);
        return o instanceof Integer ? Integer.parseInt(o.toString()) : def;
    }

    public float getFloat(final String key, final float def) {
        final Object o = get(key);
        return o instanceof Float ? Float.parseFloat(o.toString()) : def;
    }

    public HashMap<String, HashMap<String, Object>> getConfigurationSection(final String key) {
        final Object o = get(key);
        return o instanceof HashMap<?, ?> ? (HashMap<String, HashMap<String, Object>>) o : new HashMap<>();
    }

    public List<String> getStringList(final String key) {
        final Object o = get(key);
        return o instanceof List<?> ? (List<String>) o : new ArrayList<>();
    }

    public Object get(final String key) {
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

    public Object get(final String key, final Object def) {
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

    public void set(final String key, final Object value) {
        if (filePath == null) return;

        final String[] keys = key.split("\\.");

        setInMemoryData(data, keys, value);

        try {
            saveYamlData(data, filePath);
        } catch (final IOException e) {
            ConfigSystem.INSTANCE.getLogger().severe("Error while writing to file " + filePath + ": " + e.getMessage());
        }
    }

    private List<String> getISLines() throws IOException {
        final List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private List<String> readAllLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private void setInMemoryData(final Map<String, Object> data, final String[] keys, final Object value) {
        Map<String, Object> current = data;
        for (int i = 0; i < keys.length - 1; i++) {
            current = (Map<String, Object>) current.computeIfAbsent(keys[i], k -> new HashMap<>());
        }
        current.put(keys[keys.length - 1], value);
    }

    private void updateIfNeeded(Map<String, Object> defaultData, Map<String, Object> data, String parentKey) {
        for (String key : defaultData.keySet()) {
            String fullKey = parentKey.isEmpty() ? key : parentKey + "." + key;

            if (!data.containsKey(key)) {
                set(fullKey, defaultData.get(key));
            } else if (defaultData.get(key) instanceof Map && data.get(key) instanceof Map) {
                if (!emptyModeKeys.contains(fullKey)) {
                    updateIfNeeded((Map<String, Object>) defaultData.get(key), (Map<String, Object>) data.get(key), fullKey);
                }
            }
        }
    }

    private Map<String, Object> loadYamlData(final List<String> lines) {
        Yaml yaml = new Yaml();
        Map<String, Object> result = new LinkedHashMap<>();
        StringBuilder yamlContent = new StringBuilder();
        StringBuilder currentComment = new StringBuilder();
        String currentSection = "";
        for (String line : lines) {
            if (line.trim().startsWith("#")) {
                currentComment.append(line).append("\n");
            } else if (!line.trim().isEmpty()) {
                String[] parts = line.split("#", 2);
                String yamlPart = parts[0];
                yamlContent.append(yamlPart).append("\n");

                if (yamlPart.contains(":")) {
                    String key = yamlPart.split(":")[0].trim();
                    String fullKey = currentSection.isEmpty() ? key : currentSection + "." + key;

                    if (currentComment.length() > 0) {
                        comments.put(fullKey, currentComment.toString());
                        currentComment.setLength(0);
                    }

                    if (parts.length > 1) {
                        inlineComments.put(fullKey, parts[1].trim());
                    }

                    if (yamlPart.endsWith(":")) {
                        currentSection = fullKey;
                    } else {
                        currentSection = "";
                    }
                }
            }
        }
        if (yamlContent.length() > 0) {
            Object yamlPart = yaml.load(yamlContent.toString());
            if (yamlPart instanceof Map) {
                result.putAll((Map<String, Object>) yamlPart);
            } else {
                throw new IllegalArgumentException("Invalid YAML structure.");
            }
        }
        return result;
    }

    private void saveYamlDataWithComments(Map<String, Object> data, BufferedWriter writer, Yaml yaml, String parentKey, int indentLevel) throws IOException {
        Set<String> writtenKeys = new HashSet<>();
        String indent = repeat(indentLevel);

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = parentKey.isEmpty() ? entry.getKey() : parentKey + "." + entry.getKey();

            if (writtenKeys.contains(key)) {
                continue;
            }

            // Write comments if present
            if (comments.containsKey(key) && !writtenKeys.contains(key + "_comment")) {
                writer.write(comments.get(key));
                writtenKeys.add(key + "_comment");
            }

            // Write the key
            String formattedKey = quoteKeys ? "\"" + entry.getKey() + "\"" : entry.getKey();
            writer.write(indent + formattedKey + ":");

            // If the value is a map, recursively write the map with increased indentation
            if (entry.getValue() instanceof Map) {
                writer.write("\n");
                saveYamlDataWithComments((Map<String, Object>) entry.getValue(), writer, yaml, key, indentLevel + 1);
            } else if (entry.getValue() instanceof List) {
                // Handle lists properly
                writer.write("\n");
                for (Object item : (List<?>) entry.getValue()) {
                    if (item instanceof String) {
                        writer.write(indent + "  - \"" + item + "\"\n");
                    } else {
                        writer.write(indent + "  - " + item + "\n");
                    }
                }
            } else {
                // Handle different types of non-map values
                String dumpedYaml;
                if (entry.getValue() instanceof String) {
                    // Escape newlines in strings
                    String value = ((String) entry.getValue()).replace("\n", "\\n");
                    dumpedYaml = " \"" + value + "\"";
                } else if (entry.getValue() instanceof Boolean || entry.getValue() instanceof Number) {
                    dumpedYaml = " " + entry.getValue();
                } else {
                    // For other types, serialize using YAML
                    dumpedYaml = " " + yaml.dump(entry.getValue()).trim();
                }
                // Add inline comments if present
                if (inlineComments.containsKey(key)) {
                    dumpedYaml += " # " + inlineComments.get(key);
                }
                dumpedYaml += "\n";
                writer.write(dumpedYaml);
            }

            writtenKeys.add(key);
        }
    }

    private void saveYamlData(final Map<String, Object> data, final File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8))) {
            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);

            saveYamlDataWithComments(data, writer, yaml, "", 0);
        }
    }

    private String repeat(final int times) {
        return new String(new char[times]).replace("\0", "  ");
    }
}