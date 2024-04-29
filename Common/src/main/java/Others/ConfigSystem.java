package Others;

import API.APICore;
import API.WorldChatterAPI;
import UniversalFunctions.UniLogHandler;
import chatting.BroadcastSystemConnector;
import chatting.ChattingSystem;
import UniversalFunctions.YMLFile;
import methods.AntiSwear;
import methods.Expression;
import methods.MoreFormat;
import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Timer;
import java.util.logging.Logger;

public final class ConfigSystem {

    private YMLFile config,broadcast,messages,format,security,texts;
    private final Logger logger;
    public static ConfigSystem INSTANCE;

    public ConfigSystem(final Logger logger) {
        INSTANCE = this;

        this.logger = logger;

        new APICore();
        new MoreFormat();
        new PlayerSystem();
        new BroadcastSystemConnector();
    }

    public void update() {
        final File file = new File("plugins/WorldChatter");

        if (file.mkdir()) {
            UniLogHandler.INSTANCE.sendMessage(ChatColor.GREEN + "Created WorldChatter folder!");
        }

        if (texts == null) updateTexts();
        if (config == null) updateConfig();
        if (format == null) updateFormat();
        if (security == null) updateSecurity();
        if (messages == null) updateMessages();
        if (broadcast == null) updateBroadcast();

        texts.update();
        config.update();
        format.update();
        security.update();
        messages.update();
        broadcast.update();


        Expression.update();
        SoundSystem.update();
        BroadcastSystemConnector.INSTANCE.update();

        try {
            AntiSwear.update();
        } catch (final IOException e) {
            ConfigSystem.INSTANCE.getLogger().warning("An error has occurred while updating the profanity list.. If your network is enabled then contact the developer!");
        }

        for (final Timer task : ChattingSystem.cooldowns.values()) {
            task.cancel();
        }

        ChattingSystem.cooldowns.clear();

        for (final WorldChatterAPI api : APICore.INSTANCE.getListeners()) api.configReload(null);
    }

    public Logger getLogger() {
        return logger;
    }

    public YMLFile getConfig() {
        return config;
    }

    public YMLFile getBroadcast() {
        return broadcast;
    }

    public YMLFile getFormat() {
        return format;
    }

    public YMLFile getMessages() {
        return messages;
    }

    public YMLFile getSecurity() {
        return security;
    }

    public YMLFile getTexts() {
        return texts;
    }

    private void updateConfig() {
        config = new YMLFile(copyFromIDE("config.yml").toPath());
    }

    private void updateMessages() {
        messages = new YMLFile(copyFromIDE("messages.yml").toPath());
    }

    private void updateBroadcast() {
        broadcast = new YMLFile(copyFromIDE("broadcast.yml").toPath());
    }

    private void updateFormat() {
        format = new YMLFile(copyFromIDE("format.yml").toPath());
    }

    private void updateSecurity() {
        security = new YMLFile(copyFromIDE("security.yml").toPath());
    }

    private void updateTexts() {
        texts = new YMLFile(copyFromIDE("texts.yml").toPath());
    }

    public File copyFromIDE(final String key) {
        final File file = new File("plugins/WorldChatter/" + key);
        final ClassLoader classloader = getClass().getClassLoader();

        if (!file.exists()) {
            final InputStream is = classloader.getResourceAsStream(key);
            if (is == null) {
                logger.severe("Couldn't find " + key + " inside the plugin files, please report to the developer ASAP!");
                return file;
            }

            try {
                Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (final IOException e) {
                logger.severe("Couldn't copy file from plugin to disk: " + e.getMessage());
                return file;
            }
        }

        return file;
    }

}
