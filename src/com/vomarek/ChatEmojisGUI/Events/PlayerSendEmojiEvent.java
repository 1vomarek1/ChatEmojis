package com.vomarek.ChatEmojisGUI.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager.Emoji;

public class PlayerSendEmojiEvent extends Event implements Cancellable{
	private Player player;
	private Emoji emoji;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
	private Boolean isCancelled;
	
	
	PlayerSendEmojiEvent(Player player, Emoji emoji) {
		this.player = player;
		this.emoji = emoji;
		this.isCancelled = false;
	}
	
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public Player getPlayer() {
		return player;
	}
	
	public Emoji getEmoji() {
		return emoji;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}
	
	public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }	
}
