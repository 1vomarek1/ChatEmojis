package com.vomarek.ChatEmojisGUI;

import com.vomarek.ChatEmojisGUI.Commands.Commands;
import com.vomarek.ChatEmojisGUI.Events.Events;
import com.vomarek.ChatEmojisGUI.Files.FileManager;
import com.vomarek.ChatEmojisGUI.Title.Title;
import com.vomarek.ChatEmojisGUI.Title.Title_1_8_R1;
import com.vomarek.ChatEmojisGUI.Title.Title_1_8_R2;
import com.vomarek.ChatEmojisGUI.Title.Title_1_8_R3;
import com.vomarek.ChatEmojisGUI.Title.Title_1_9;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatEmojisGUI extends JavaPlugin {
  private static ChatEmojisGUI plugin;
  
  private static FileManager fileManager;
  
  private static HashMap<String, FileManager.Config> Files;
  
  private static HashMap<String, Boolean> hooks = new HashMap<>();
  
  private static Title title;
  
  public void onEnable() {
    plugin = this;
    fileManager = new FileManager();
    Files = fileManager.loadFiles();
    if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
      getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "ChatEmojisGUI" + ChatColor.DARK_AQUA + "]" + ChatColor.GREEN + " PlaceholderAPI detected! You'll be able to use placeholders!");
      hooks.put("PlaceholderAPI", Boolean.valueOf(true));
    } else {
      getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "ChatEmojisGUI" + ChatColor.DARK_AQUA + "]" + ChatColor.RED + " PlaceholderAPI not detected! If you want to use placeholders consider downloading PlaceholderAPI https://www.spigotmc.org/resources/placeholderapi.6245/");
      hooks.put("PlaceholderAPI", Boolean.valueOf(false));
    } 
    if (!setupTitle()) {
      setEnabled(false);
      getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "ChatEmojisGUI" + ChatColor.DARK_AQUA + "]" + ChatColor.RED + " This version is probably not supported!");
    } 
    getServer().getPluginManager().registerEvents((Listener)new Events(), (Plugin)this);
    getCommand("chatemojisgui").setExecutor((CommandExecutor)new Commands());
    getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "ChatEmojisGUI" + ChatColor.DARK_AQUA + "]" + ChatColor.GREEN + " ChatEmojisGUI plugin has been enabled!");
  }
  
  public void onDisable() {
    getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.DARK_GREEN + "ChatEmojisGUI" + ChatColor.DARK_AQUA + "]" + ChatColor.RED + " ChatEmojisGUI plugin has been enabled!");
  }
  
  public static HashMap<String, FileManager.Config> getFiles() {
    return Files;
  }
  
  public static ChatEmojisGUI getPlugin() {
    return plugin;
  }
  
  public static FileManager getFileManager() {
    return fileManager;
  }
  
  public static void sendTitle(Player player, String str1, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut) {
    title.sendTitle(player, str1, subtitle, fadeIn, stay, fadeOut);
  }
  
  private boolean setupTitle() {
    String version;
    try {
      version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    } catch (ArrayIndexOutOfBoundsException err) {
      return false;
    } 
    if (version.equals("v1_8_R1")) {
      title = (Title)new Title_1_8_R1();
    } else if (version.contentEquals("v1_8_R2")) {
      title = (Title)new Title_1_8_R2();
    } else if (version.contentEquals("v1_8_R3")) {
      title = (Title)new Title_1_8_R3();
    } else {
      title = (Title)new Title_1_9();
    } 
    return (title != null);
  }
  
  public static HashMap<String, Boolean> getHooks() {
    return hooks;
  }
}
