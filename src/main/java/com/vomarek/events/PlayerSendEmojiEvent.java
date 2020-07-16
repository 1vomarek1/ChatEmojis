package com.vomarek.events;

import com.vomarek.emojis.EmojiManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerSendEmojiEvent extends Event implements Cancellable {
    private boolean cancelled;
    private final EmojiManager.Emoji emoji;
    private final Player player;

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public PlayerSendEmojiEvent(final Player player, final EmojiManager.Emoji emoji) {
        super(true);
        this.player = player;
        this.emoji = emoji;
        this.cancelled = false;
    }

    /**
     * Return the emoji involved in this event
     * @return Emoji which is involved in this event
     */
    @NotNull
    public EmojiManager.Emoji getEmoji () {
        return emoji;
    }

    /**
     * Returns the player involved in this event
     *
     * @return Player who is involved in this event
     */
    @NotNull
    public Player getPlayer () {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not
     * be executed in the server, but will still pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
