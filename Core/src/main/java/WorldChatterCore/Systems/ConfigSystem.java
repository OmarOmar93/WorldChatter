package WorldChatterCore.Systems;

import WorldChatterCore.Connectors.InterfaceConnectors.MainPluginConnector;
import WorldChatterCore.Features.*;
import WorldChatterCore.Others.Configuration;
import WorldChatterCore.Others.ConfigurationProvider;
import WorldChatterCore.Others.ServerOptions;
import WorldChatterCore.Others.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigSystem {

    public static ConfigSystem INSTANCE;
    private Configuration chatFormatter, player, security, system, messages, texts, place;
    private final File chatFormatterFile, playerFile, securityFile, systemFile, messagesFile, textsFile, placeFile;

    public ConfigSystem() {
        INSTANCE = this;

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


        new PlayerJoiningQuitting();
        new BroadcastSystem();
        new ServerOptions();
        new ChatFormatter();
        new Notifications();
        new TextReplacer();
        new UserMention();
        new AntiSwear();
        new AntiCaps();
        new AntiSpam();
        new ChatLock();
        update();


        new Command();

    }

    public void update() {


        updateChatFormatter();
        updateMessages();
        updateSecurity();
        updatePlace();
        updateTexts();
        updatePlayer();
        updateSystem();


        PlayerJoiningQuitting.INSTANCE.update();
        BroadcastSystem.INSTANCE.update();
        ChatFormatter.INSTANCE.update();
        Notifications.INSTANCE.update();
        ServerOptions.INSTANCE.update();
        TextReplacer.INSTANCE.update();
        UserMention.INSTANCE.update();
        AntiSwear.INSTANCE.update();
        ChatLock.INSTANCE.update();
        AntiCaps.INSTANCE.update();
        AntiSpam.INSTANCE.update();

    }


    public Configuration getMessages() {
        return messages;
    }

    public Configuration getSecurity() {
        return security;
    }

    public Configuration getTexts() {
        return texts;
    }

    public Configuration getSystem() {
        return system;
    }

    public Configuration getPlayer() {
        return player;
    }

    public Configuration getChatFormatter() {
        return chatFormatter;
    }

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
