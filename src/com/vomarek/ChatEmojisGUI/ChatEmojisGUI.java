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
import com.vomarek.ChatEmojisGUI.Title.Title_1_8_R3;
import com.vomarek.ChatEmojisGUI.Title.Title_1_9;

/** 
* Main class of ChatEmojisGUI. 
* 
* @author 1vomarek1
*/
public class ChatEmojisGUI extends JavaPlugin {
	private static ChatEmojisGUI plugin;
	private static FileManager fileManager;
	private static HashMap<String, Config> Files;
	private static Integer version;
	private static HashMap<String, Boolean> hooks = new HashMap<String, Boolean>();
	
	private static Title title;
	
	/**
	 *  Method inherited by JavaPlugin
	 */	
	public void onEnable () {
		plugin = this;
		fileManager = new FileManager();
		Files = fileManager.loadFiles();
		new EmojiManager();
		
		version = (Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].split("_")[1]));
		
		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"["+ChatColor.DARK_GREEN+"ChatEmojisGUI"+ChatColor.DARK_AQUA+"]"+ChatColor.GREEN+" PlaceholderAPI detected! You'll be able to use placeholders!");	
			hooks.put("PlaceholderAPI", true);
		} else {
			getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"["+ChatColor.DARK_GREEN+"ChatEmojisGUI"+ChatColor.DARK_AQUA+"]"+ChatColor.RED+" PlaceholderAPI not detected! If you want to use placeholders consider downloading PlaceholderAPI https://www.spigotmc.org/resources/placeholderapi.6245/");	
			hooks.put("PlaceholderAPI", false);
		}
		
		if (!setupTitle()) {
			this.setEnabled(false);
			getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"["+ChatColor.DARK_GREEN+"ChatEmojisGUI"+ChatColor.DARK_AQUA+"]"+ChatColor.RED+" This version is probably not supported!");	
		}
		
		if (version <= 12) {
			getServer().getPluginManager().registerEvents(new oldEvents(), this);
		} else {
			getServer().getPluginManager().registerEvents(new Events(), this);
		}
		
		
		this.getCommand("chatemojisgui").setExecutor(new Commands());
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"["+ChatColor.DARK_GREEN+"ChatEmojisGUI"+ChatColor.DARK_AQUA+"]"+ChatColor.GREEN+" ChatEmojisGUI plugin has been enabled!");	
	}
	
	/**
	 *  Method inherited by JavaPlugin
	 */	
	public void onDisable () {
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA+"["+ChatColor.DARK_GREEN+"ChatEmojisGUI"+ChatColor.DARK_AQUA+"]"+ChatColor.RED+" ChatEmojisGUI plugin has been enabled!");

	}
	
	/**
	 * Get list of files ChatEmojisGUI store data in
	 * 
	 * @return list of files ChatEmojisGUI store data in
	 */
	public static HashMap<String, Config> getFiles() {
		return Files;
	}
	
	/**
	 * Get instance of ChatEmojisGUI
	 * 
	 * @return instance of ChatEmojisGUI
	 */
	public static ChatEmojisGUI getPlugin() {
		return plugin;
	}
	
	public static FileManager getFileManager() {
		return fileManager;
	}
	
	/**
	 * A method used to send titles (works on 1.8_R1,R2,R3 and any 1.9+ version)
	 * 
	 * @param player player that you want to send the title to
	 * @param Title Text you want to have in title you are sending 
	 * @param subtitle Text you want to have as subtitle
	 * @param fadeIn Length of fade in of the title and subtitle
	 * @param stay Time how long the title and subtitle should stay on the screen
	 * @param fadeOut Length of fade out of the title and subtitle
	 */ 
	
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
		} else if (version.contentEquals("v1_8_R3")) {
			title = new Title_1_8_R3();
		} else {
			title = new Title_1_9();
		}
		
		return title != null;
	}
	
	/**
	 * Gets HashMap of plugins that ChatEmojisGUI are using
	 * 
	 *  @return HashMap of plugins that ChatEmojisGUI are using
	 */
	public static HashMap<String, Boolean> getHooks() {
		return hooks;
	}
}
