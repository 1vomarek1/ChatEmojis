package com.vomarek.ChatEmojisGUI.Title;

import org.bukkit.entity.Player;

public interface Title {
	public void sendTitle(Player player, String title, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut);
}
