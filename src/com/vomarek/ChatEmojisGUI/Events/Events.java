package com.vomarek.ChatEmojisGUI.Events;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;
import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager.Emoji;

public class Events implements Listener{
	
	@EventHandler
	public void chatEvent(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		Player player = event.getPlayer();
		
		for(Map.Entry<String, Emoji> entry : ChatEmojisGUI.getEmojiManager().getEmojis().entrySet()) {
			Emoji emoji = entry.getValue();
			
			if (!message.contains(emoji.getIdentifier())) return;
			
			if (!player.hasPermission("ChatEmojisGUI.emoji."+emoji.getId())) return;
			
			PlayerSendEmojiEvent sendEvent = new PlayerSendEmojiEvent(player, emoji);
			Bukkit.getPluginManager().callEvent(sendEvent);
			
			if (!sendEvent.isCancelled()) {
				message = message.replace(sendEvent.getEmoji().getIdentifier(), sendEvent.getEmoji().getEmoji());
			}
			
		}
		
		event.setMessage(message);
	}
}
