package com.vomarek.ChatEmojisGUI;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.vomarek.ChatEmojisGUI.Commands.Commands;
import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager;
import com.vomarek.ChatEmojisGUI.Events.Events;
import com.vomarek.ChatEmojisGUI.Events.oldEvents;
import com.vomarek.ChatEmojisGUI.Files.FileManager;
import com.vomarek.ChatEmojisGUI.Files.FileManager.Config;
import com.vomarek.ChatEmojisGUI.Title.Title;
import com.vomarek.ChatEmojisGUI.Title.Title_1_8_R1;
import com.vomarek.ChatEmojisGUI.Title.Title_1_8_R2;
import com.vomarek.ChatEmojisGUI.Title.Title_1_9;

public class ChatEmojisGUI extends JavaPlugin {
	private static ChatEmojisGUI plugin;
	private static FileManager fileManager;
	private static HashMap<String, Config> Files;
	private static EmojiManager emojiManager;
	public static Integer version;
	
	private static Title title;
	
	public void onEnable () {
		plugin = this;
		fileManager = new FileManager();
		Files = fileManager.loadFiles();
		emojiManager = new EmojiManager();
		
		version = (Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].split("_")[1]));
		
		if (!setupTitle()) {
			this.setEnabled(false);
			getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"["+ChatColor.DARK_GREEN+"ChatEmojisGUI"+ChatColor.DARK_AQUA+"]"+ChatColor.RED+" This version is probably not supported!");	
		}
		
		if (version <= 12) {
			getServer().getPluginManager().registerEvents(new oldEvents(), this);
		} else {
			getServer().getPluginManager().registerEvents(new Events(), this);
		}
		
		
		this.getCommand("emojis").setExecutor(new Commands());
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"["+ChatColor.DARK_GREEN+"ChatEmojisGUI"+ChatColor.DARK_AQUA+"]"+ChatColor.GREEN+" ChatEmojisGUI plugin has been enabled!");	
	}
	
	public void onDisable () {
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"["+ChatColor.DARK_GREEN+"ChatEmojisGUI"+ChatColor.DARK_AQUA+"]"+ChatColor.RED+" ChatEmojisGUI plugin has been enabled!");

	}
	
	public static HashMap<String, Config> getFiles() {
		return Files;
	}
	
	public static ChatEmojisGUI getPlugin() {
		return plugin;
	}
	
	public static FileManager getFileManager() {
		return fileManager;
	}
	
	public static EmojiManager getEmojiManager() {
		return emojiManager;
	}
	
	public static void sendTitle(Player player, String Title, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut) {
		title.sendTitle(player, Title, subtitle, fadeIn, stay, fadeOut);
	}
	
	private boolean setupTitle() {
		
		String version;
		
		try {
			version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		} catch (ArrayIndexOutOfBoundsException err) {
			return false;
		}
		
		if (version.equals("v1_8_R1")) {
			title = new Title_1_8_R1();
		} else if (version.contentEquals("v1_8_R2")) {
			title = new Title_1_8_R2();
		} else {
			title = new Title_1_9();
		}
		
		return title != null;
	}
}
