package com.vomarek;

import com.vomarek.commands.ReloadCommand;
import com.vomarek.data.Configuration;
import com.vomarek.emojis.EmojiManager;
import com.vomarek.events.EventsClass;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatEmojis extends JavaPlugin {
    private static ChatEmojis plugin;

    private Configuration config;
    private EmojiManager emojiManager;

    private Boolean usePAPI = true;

    public void onEnable() {
        plugin = this;

        new Metrics(this, 8203);

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            usePAPI = false;
            getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&lChatEmojis &7|&c Couldn't&f find&3 PlaceholderAPI&f! You won't be able to use placeholders in emojis!"));
        }

        config = new Configuration("config", this);
        emojiManager = new EmojiManager(this);

        getServer().getPluginManager().registerEvents(new EventsClass(this), this);
        getCommand("chatemojis").setExecutor(new ReloadCommand(this));

        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&lChatEmojis &7|&f ChatEmojis have been&a enabled&f!"));
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&lChatEmojis &7|&f ChatEmojis have been&c disabled&f!"));
    }

    /**
     * Returns instance of ChatEmojis main class
     * @return Main class of ChatEmojis plugin
     */
    public static ChatEmojis getPlugin () {
        return plugin;
    }

    public Boolean usePAPI () {
        return usePAPI;
    }

    @Override
    public Configuration getConfig () {
        return config;
    }

    /**
     * Returns emoji manager - emoji manager is where you can create and manage emojis
     * @return instance of EmojiManager
     */
    public EmojiManager getEmojiManager () {
        return emojiManager;
    }

}
