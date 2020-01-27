package com.vomarek.ChatEmojisGUI.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;

import me.clip.placeholderapi.PlaceholderAPI;

public class Replacements {
	
	
	public static String ChatColor(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public static String placeholders(String string, Player player) {
		if (ChatEmojisGUI.getHooks().get("PlaceholderAPI") != null && ChatEmojisGUI.getHooks().get("PlaceholderAPI")) {
			return PlaceholderAPI.setPlaceholders(player, string);			
		} else {
			return string;
		}
	}
	
	public static String replace(String string, Player player) {
		return ChatColor(placeholders(string, player));
	}
}
