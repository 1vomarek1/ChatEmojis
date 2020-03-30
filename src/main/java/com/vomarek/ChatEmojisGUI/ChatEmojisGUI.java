package com.vomarek.ChatEmojisGUI;

import com.vomarek.ChatEmojisGUI.Commands.Commands;
import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager;
import com.vomarek.ChatEmojisGUI.Events.EventsClass;
import com.vomarek.ChatEmojisGUI.Files.FileManager;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatEmojisGUI extends JavaPlugin {
    private static ChatEmojisGUI plugin;

    private static FileManager fileManager;

    private static HashMap<String, FileManager.Config> files;

    private static HashMap<String, Boolean> hooks = new HashMap<>();

    public static void sendTitle(Player player, String title, String subtitle, Integer fadein, Integer stay, Integer fadeout) {
        player.sendTitle(title, subtitle, fadein, stay, fadeout);
    }

    public void onEnable() {
        plugin = this;
        fileManager = new FileManager(this);
        files = fileManager.loadFiles();
        new EmojiManager();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "ChatEmojisGUI" + ChatColor.DARK_AQUA + "]" + ChatColor.GREEN + " PlaceholderAPI detected! You'll be able to use placeholders!");
            hooks.put("PlaceholderAPI", true);
        } else {
            getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "ChatEmojisGUI" + ChatColor.DARK_AQUA + "]" + ChatColor.RED + " PlaceholderAPI not detected! If you want to use placeholders consider downloading PlaceholderAPI https://www.spigotmc.org/resources/placeholderapi.6245/");
            hooks.put("PlaceholderAPI", false);
        }
        getServer().getPluginManager().registerEvents(new EventsClass(), this);
        getCommand("chatemojisgui").setExecutor(new Commands());
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "ChatEmojisGUI" + ChatColor.DARK_AQUA + "]" + ChatColor.GREEN + " ChatEmojisGUI plugin has been enabled!");
    }

    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "ChatEmojisGUI" + ChatColor.DARK_AQUA + "]" + ChatColor.RED + " ChatEmojisGUI plugin has been enabled!");
    }

    

    public static HashMap<String, FileManager.Config> getFiles() {
        return files;
    }

    public static ChatEmojisGUI getPlugin() {
        return plugin;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static HashMap<String, Boolean> getHooks() {
        return hooks;
    }
}
