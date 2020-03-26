package com.vomarek.ChatEmojisGUI.util;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Replacements {
  public static String ChatColor(String string) {
    return ChatColor.translateAlternateColorCodes('&', string);
  }
  
  public static String placeholders(String string, Player player) {
    if (ChatEmojisGUI.getHooks().get("PlaceholderAPI") != null && ((Boolean)ChatEmojisGUI.getHooks().get("PlaceholderAPI")).booleanValue())
      return PlaceholderAPI.setPlaceholders(player, string); 
    return string;
  }
  
  public static String replace(String string, Player player) {
    return ChatColor(placeholders(string, player));
  }
}
