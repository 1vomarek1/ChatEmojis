package com.vomarek.util;

import com.vomarek.ChatEmojis;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StringUtils {

    @NotNull
    public static String chatColors(@NotNull final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    @NotNull
    public static String placeholders(@NotNull final Player player, @NotNull final String text) {
        if (ChatEmojis.getPlugin().usePAPI()) return PlaceholderAPI.setPlaceholders(player, text);
        return text;
    }

    @NotNull
    public static String replace (@NotNull final Player player, @NotNull String text) {
        return chatColors(placeholders(player, text));
    }

}
