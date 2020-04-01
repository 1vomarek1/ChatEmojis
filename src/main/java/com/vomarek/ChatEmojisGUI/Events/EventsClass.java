package com.vomarek.ChatEmojisGUI.Events;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;
import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager;
import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager.Emoji;
import com.vomarek.ChatEmojisGUI.GUI.EmojiGUI;
import com.vomarek.ChatEmojisGUI.GUI.MainGUI;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.vomarek.ChatEmojisGUI.util.Replacements;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EventsClass implements Listener {
    HashMap<Player, String> RPlayer = new HashMap<>();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void emoji(AsyncPlayerChatEvent event) {


        String message = event.getMessage();
        final Player player = event.getPlayer();


        for (Emoji emoji : EmojiManager.getEmojis().values()) {

            if (!message.contains(emoji.getIdentifier())) continue;

            PlayerSendEmojiEvent sendEvent = new PlayerSendEmojiEvent(player, emoji);

            Bukkit.getScheduler().runTask(ChatEmojisGUI.getPlugin(), () -> Bukkit.getPluginManager().callEvent(sendEvent));

            if (event.isCancelled()) continue;

            message =message.replaceAll("[\\\\]"+emoji.getIdentifier(), "%%DONOTREPLACE%%");

            message = message.replaceAll(emoji.getIdentifier(), Replacements.replace(emoji.getEmoji(), player));

            message =message.replaceAll("%%DONOTREPLACE%%", emoji.getIdentifier());


            event.setMessage(message);
        }

    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void command(PlayerCommandPreprocessEvent event) {

        String message = event.getMessage();
        final Player player = event.getPlayer();


        for (Emoji emoji : EmojiManager.getEmojis().values()) {

            if (!message.contains(emoji.getIdentifier())) continue;

            PlayerSendEmojiEvent sendEvent = new PlayerSendEmojiEvent(player, emoji);

            Bukkit.getScheduler().runTask(ChatEmojisGUI.getPlugin(), () -> Bukkit.getPluginManager().callEvent(sendEvent));

            if (event.isCancelled()) continue;

            message =message.replaceAll("[\\\\]"+emoji.getIdentifier(), "%%DONOTREPLACE%%");

            message = message.replaceAll(emoji.getIdentifier(), Replacements.replace(emoji.getEmoji(), player));

            message =message.replaceAll("%%DONOTREPLACE%%", emoji.getIdentifier());


            event.setMessage(message);
        }

    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void RequestMessage(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (!this.RPlayer.containsKey(player))
            return;
        event.setCancelled(true);
        String request = this.RPlayer.get(player);
        this.RPlayer.remove(player);
        ChatEmojisGUI.sendTitle(player, "", "", 0, 0, 0);
        if (request.startsWith("create")) {
            if ((request.split(">><<")).length == 1) {
                this.RPlayer.put(player, "create>><<" + event.getMessage());
                ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Please type its new Identifier", 0, 2100000000, 0);
                (new BukkitRunnable() {
                    public void run() {
                        if (RPlayer.containsKey(player) || (RPlayer.get(player).split(">><<")).length != 2)
                            return;
                        ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Type " + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", 0, 2100000000, 0);
                    }
                }).runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
            } else if ((request.split(">><<")).length == 2) {
                this.RPlayer.put(player, "create>><<" + request.split(">><<")[1] + ">><<" + event.getMessage());
                ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Please type its new Emoji", 0, 2100000000, 0);
                (new BukkitRunnable() {
                    public void run() {
                        if (!RPlayer.containsKey(player))
                            return;
                        ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Type cancel" + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", 0, 2100000000, 0);
                    }
                }).runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
            } else if ((request.split(">><<")).length >= 3) {
                final EmojiManager.Emoji emoji = EmojiManager.createEmoji(request.split(">><<")[1], request.split(">><<")[2], event.getMessage());
                (new BukkitRunnable() {
                    public void run() {
                        player.openInventory((new EmojiGUI(player, emoji)).getInventory());
                    }
                }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
            }
        } else if (request.startsWith("edit")) {
            switch (request.split(">><<")[1]) {
                case "id": {
                    final EmojiManager.Emoji emoji = EmojiManager.getEmoji(request.split(">><<")[2]);
                    emoji.setId(event.getMessage());
                    (new BukkitRunnable() {
                        public void run() {
                            player.openInventory((new EmojiGUI(player, emoji)).getInventory());
                        }
                    }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
                    break;
                }
                case "identifier": {
                    final EmojiManager.Emoji emoji = EmojiManager.getEmoji(request.split(">><<")[2]);
                    emoji.setIdentifier(event.getMessage());
                    (new BukkitRunnable() {
                        public void run() {
                            player.openInventory((new EmojiGUI(player, emoji)).getInventory());
                        }
                    }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
                    break;
                }
                case "emoji": {
                    final EmojiManager.Emoji emoji = EmojiManager.getEmoji(request.split(">><<")[2]);
                    emoji.setEmoji(event.getMessage());
                    (new BukkitRunnable() {
                        public void run() {
                            player.openInventory((new EmojiGUI(player, emoji)).getInventory());
                        }
                    }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void MainGUIClick(final InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null)
            return;
        if (!(event.getInventory().getHolder() instanceof MainGUI))
            return;
        if (!(event.getWhoClicked() instanceof Player))
            return;
        final Player player = (Player)event.getWhoClicked();
        final MainGUI holder = (MainGUI)event.getInventory().getHolder();
        event.setCancelled(true);
        if (event.getSlot() == 49)
            (new BukkitRunnable() {
                public void run() {
                    player.closeInventory();
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
        if (event.getSlot() == 48) {
            holder.previousPage();
            (new BukkitRunnable() {
                public void run() {
                    player.openInventory(holder.getInventory());
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
        }
        if (event.getSlot() == 50) {
            holder.nextPage();
            (new BukkitRunnable() {
                public void run() {
                    player.openInventory(holder.getInventory());
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
        }
        if (event.getSlot() == 52) {
            this.RPlayer.put(player, "create");
            ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Please type its new Id", 0, 2100000000, 0);
            (new BukkitRunnable() {
                public void run() {
                    player.closeInventory();
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
            (new BukkitRunnable() {
                public void run() {
                    if (!RPlayer.containsKey(player))
                        return;
                    ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Type cancel" + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", 0, 2100000000, 0);
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
        }
        if (holder.getEmojisOnPage().containsKey(event.getSlot()))
            (new BukkitRunnable() {
                public void run() {
                    player.openInventory((new EmojiGUI(player, holder.getEmojisOnPage().get(event.getSlot()))).getInventory());
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
    }

    @EventHandler
    public void EmojiGUIClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null)
            return;
        if (!(event.getInventory().getHolder() instanceof EmojiGUI))
            return;
        if (!(event.getWhoClicked() instanceof Player))
            return;
        final Player player = (Player)event.getWhoClicked();
        EmojiGUI holder = (EmojiGUI)event.getInventory().getHolder();
        EmojiManager.Emoji emoji = holder.getEmoji();
        event.setCancelled(true);
        if (event.getSlot() == 15 && event.getClick().equals(ClickType.DOUBLE_CLICK)) {
            EmojiManager.deleteEmoji(emoji);
            (new BukkitRunnable() {
                public void run() {
                    player.openInventory((new MainGUI(player)).getInventory());
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
        }
        if (event.getSlot() == 29) {
            this.RPlayer.put(player, "edit>><<id>><<" + emoji.getId());
            ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Please type its new Id", 0, 2100000000, 0);
            (new BukkitRunnable() {
                public void run() {
                    player.closeInventory();
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
            (new BukkitRunnable() {
                public void run() {
                    if (!RPlayer.containsKey(player))
                        return;
                    ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Type cancel" + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", 0, 2100000000, 0);
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
        }
        if (event.getSlot() == 31) {
            this.RPlayer.put(player, "edit>><<identifier>><<" + emoji.getId());
            ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Please type its new Identifier", 0, 2100000000, 0);
            (new BukkitRunnable() {
                public void run() {
                    player.closeInventory();
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
            (new BukkitRunnable() {
                public void run() {
                    if (!RPlayer.containsKey(player))
                        return;
                    ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Type cancel" + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", 0, 2100000000, 0);
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
        }
        if (event.getSlot() == 33) {
            this.RPlayer.put(player, "edit>><<emoji>><<" + emoji.getId());
            ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Please type new Emoji", 0, 2100000000, 0);
            (new BukkitRunnable() {
                public void run() {
                    player.closeInventory();
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
            (new BukkitRunnable() {
                public void run() {
                    if (!RPlayer.containsKey(player))
                        return;
                    ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Type cancel" + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", 0, 2100000000, 0);
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
        }
        if (event.getSlot() == 49)
            (new BukkitRunnable() {
                public void run() {
                    player.openInventory((new MainGUI(player)).getInventory());
                }
            }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
    }
}
