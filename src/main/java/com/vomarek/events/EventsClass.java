package com.vomarek.events;

import com.vomarek.ChatEmojis;
import com.vomarek.emojis.EmojiManager;
import com.vomarek.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

public class EventsClass implements Listener {
    public ChatEmojis plugin;

    public EventsClass (final ChatEmojis plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOW)
    public void onMessage(final AsyncPlayerChatEvent event) {

        String message = event.getMessage();
        final Player player = event.getPlayer();


        for (EmojiManager.Emoji emoji : plugin.getEmojiManager().getEmojis().values()) {

            if (!message.contains(emoji.getIdentifier())) continue;

            PlayerSendEmojiEvent sendEvent = new PlayerSendEmojiEvent(player, emoji);

            Bukkit.getPluginManager().callEvent(sendEvent);

            if (sendEvent.isCancelled()) continue;

            message = message.replaceAll("[\\\\]"+emoji.getIdentifier(), "%%DONOTREPLACE%%");

            message = message.replaceAll(emoji.getIdentifier(), StringUtils.replace(player, emoji.getEmoji()));

            message = message.replaceAll("%%DONOTREPLACE%%", emoji.getIdentifier());


            event.setMessage(message);

        }

    }

    @EventHandler (priority = EventPriority.LOW)
    public void onEmojiSend(@NotNull final PlayerSendEmojiEvent event) {
        if (!event.getPlayer().hasPermission("chatemojis.emoji."+event.getEmoji().getId())) event.setCancelled(true);
    }

}
