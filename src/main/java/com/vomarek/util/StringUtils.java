package com.vomarek.util;

import com.vomarek.ChatEmojis;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StringUtils {

    public static String chatColors(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String placeholders(final Player player, final String text) {
        if (ChatEmojis.getPlugin().usePAPI()) return PlaceholderAPI.setPlaceholders(player, text);
        return text;
    }

    public static String replace (final Player player, String text) {
        return chatColors(placeholders(player, text));
    }

}
