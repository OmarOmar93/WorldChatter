package WorldChatterCore.Systems;

import WorldChatterCore.Channels.ChannelManager;
import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Features.*;
import WorldChatterCore.Others.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * This Represents the Configuration System
 */
public final class ConfigSystem {

    /**
     * Represents the Configuration System Instance.
     */
    public static ConfigSystem INSTANCE;
    private Configuration chatFormatter, player, security, system, messages, texts, place;
    private final File chatFormatterFile, playerFile, securityFile, systemFile, messagesFile, textsFile, placeFile;

    /**
     * Retrieves the configuration for the chatFormatter config file.
     */
    public ConfigSystem() {
        if(INSTANCE == null) INSTANCE = this;

        final File dataFolder = new File("plugins/WorldChatter");

        if (dataFolder.mkdir()) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.WHITE + "Created WorldChatter Folder!");
        }

        chatFormatterFile = new File(dataFolder.getPath(), "chatFormatter.yml");
        securityFile = new File(dataFolder.getPath(), "security.yml");
        messagesFile = new File(dataFolder.getPath(), "messages.yml");
        playerFile = new File(dataFolder.getPath(), "player.yml");
        systemFile = new File(dataFolder.getPath(), "system.yml");
        textsFile = new File(dataFolder.getPath(), "texts.yml");
        placeFile = new File(dataFolder.getPath(), "place.yml");

        new debugMode();

        new PlayerJoiningQuitting();
        new ChannelManager();
        new ServerOptions();
        new ChatFormatter();
        new Notifications();
        new TextReplacer();
        new UserMention();
        new AntiRepeat();

        new AntiSwear();
        new AntiCaps();
        new AntiSpam();
        new ChatLock();


        new Aliases();
        update();


        new Command();

    }

    /**
     * This reloads all WorldChatter's configuration files
     */
    public void update() {
        updateChatFormatter();
        updateMessages();
        updateSecurity();
        updatePlace();
        updateTexts();
        updatePlayer();
        updateSystem();

        debugMode.INSTANCE.update();

        PlayerJoiningQuitting.INSTANCE.update();
        ChannelManager.INSTANCE.update();
        ChatFormatter.INSTANCE.update();
        Notifications.INSTANCE.update();
        ServerOptions.INSTANCE.update();
        TextReplacer.INSTANCE.update();
        UserMention.INSTANCE.update();
        AntiRepeat.INSTANCE.update();
        AntiSwear.INSTANCE.update();
        ChatLock.INSTANCE.update();
        AntiCaps.INSTANCE.update();
        AntiSpam.INSTANCE.update();
        Aliases.INSTANCE.update();
    }

    /**
     * Retrieves the configuration for the messages config file.
     *
     * @return the {@link Configuration} object representing the messages configuration.
     */
    public Configuration getMessages() {
        return messages;
    }

    /**
     * Retrieves the configuration for the security config file.
     *
     * @return the {@link Configuration} object representing the security configuration.
     */
    public Configuration getSecurity() {
        return security;
    }

    /**
     * Retrieves the configuration for the texts config file.
     *
     * @return the {@link Configuration} object representing the texts configuration.
     */
    public Configuration getTexts() {
        return texts;
    }

    /**
     * Retrieves the configuration for the system config file.
     *
     * @return the {@link Configuration} object representing the system configuration.
     */
    public Configuration getSystem() {
        return system;
    }

    /**
     * Retrieves the configuration for the player config file.
     *
     * @return the {@link Configuration} object representing the player configuration.
     */
    public Configuration getPlayer() {
        return player;
    }

    /**
     * Retrieves the configuration for the chatFormatter config file.
     *
     * @return the {@link Configuration} object representing the chatFormatter configuration.
     */
    public Configuration getChatFormatter() {
        return chatFormatter;
    }

    /**
     * Retrieves the configuration for the place config file.
     *
     * @return the {@link Configuration} object representing the place configuration.
     */
    public Configuration getPlace() {
        return place;
    }

    private void updateSystem() {
        if (!systemFile.exists()) {
            createFile(systemFile);
        }

        try {
            system = ConfigurationProvider.getProvider(YamlConfiguration.class).load(systemFile);
        } catch (final IOException e) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to load system.yml in memory: " + e.getMessage());
        }
    }

    private void updateMessages() {
        if (!messagesFile.exists()) {
            createFile(messagesFile);
        }

        try {
            messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagesFile);
        } catch (final IOException e) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to load messages.yml in memory: " + e.getMessage());
        }
    }

    private void updatePlayer() {
        if (!playerFile.exists()) {
            createFile(playerFile);
        }

        try {
            player = ConfigurationProvider.getProvider(YamlConfiguration.class).load(playerFile);
        } catch (final IOException e) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to load player.yml in memory: " + e.getMessage());
        }
    }

    private void updateTexts() {
        if (!textsFile.exists()) {
            createFile(textsFile);
        }

        try {
            texts = ConfigurationProvider.getProvider(YamlConfiguration.class).load(textsFile);
        } catch (final IOException e) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to load texts.yml in memory: " + e.getMessage());
        }
    }

    private void updatePlace() {
        if (!placeFile.exists()) {
            createFile(placeFile);
        }

        try {
            place = ConfigurationProvider.getProvider(YamlConfiguration.class).load(placeFile);
        } catch (final IOException e) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to load place.yml in memory: " + e.getMessage());
        }
    }

    private void updateChatFormatter() {
        if (!chatFormatterFile.exists()) {
            createFile(chatFormatterFile);
        }

        try {
            chatFormatter = ConfigurationProvider.getProvider(YamlConfiguration.class).load(chatFormatterFile);
        } catch (final IOException e) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to load chatFormatter.yml in memory: " + e.getMessage());
        }
    }

    private void updateSecurity() {
        if (!securityFile.exists()) {
            createFile(securityFile);
        }

        try {
            security = ConfigurationProvider.getProvider(YamlConfiguration.class).load(securityFile);
        } catch (final IOException e) {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to load security.yml in memory: " + e.getMessage());
        }
    }

    private void createFile(final File file) {
        final InputStream inputStream = getResourceAsStream(file.getName());

        if (inputStream != null) {
            try {
                Files.copy(inputStream, file.toPath());
                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.GREEN + file.getName() + " copied successfully.");
            } catch (final IOException e) {
                MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Failed to copy " + file.getName() + ": " + e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (final IOException e) {
                    MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Failed to close input stream: " + e.getMessage());
                }
            }
        } else {
            MainPluginConnector.INSTANCE.getWorldChatter().sendConsoleMessage(ColorSystem.GOLD + "[WorldChatter] " + ColorSystem.RED + "Unable to locate " + file.getName() + " in resources.");
        }
    }

    private InputStream getResourceAsStream(final String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }
}
