package com.vomarek.ChatEmojisGUI.Events;

import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSendEmojiEvent extends Event implements Cancellable {
  private Player player;
  
  private EmojiManager.Emoji emoji;
  
  private static final HandlerList HANDLERS_LIST = new HandlerList();
  
  private Boolean isCancelled;
  
  PlayerSendEmojiEvent(Player player, EmojiManager.Emoji emoji) {
    this.player = player;
    this.emoji = emoji;
    this.isCancelled = Boolean.valueOf(false);
  }
  
  public boolean isCancelled() {
    return this.isCancelled.booleanValue();
  }
  
  public void setCancelled(boolean isCancelled) {
    this.isCancelled = Boolean.valueOf(isCancelled);
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public EmojiManager.Emoji getEmoji() {
    return this.emoji;
  }
  
  public HandlerList getHandlers() {
    return HANDLERS_LIST;
  }
  
  public static HandlerList getHandlerList() {
    return HANDLERS_LIST;
  }
}
