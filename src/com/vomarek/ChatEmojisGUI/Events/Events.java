package com.vomarek.ChatEmojisGUI.Events;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;
import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager;
import com.vomarek.ChatEmojisGUI.GUI.EmojiGUI;
import com.vomarek.ChatEmojisGUI.GUI.MainGUI;
import com.vomarek.ChatEmojisGUI.util.Replacements;
import java.util.HashMap;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Events implements Listener {
  HashMap<Player, String> RPlayer = new HashMap<>();
  
  @EventHandler(priority = EventPriority.LOW)
  public void NewChatEvent(AsyncPlayerChatEvent event) {
    String message = event.getMessage();
    Player player = event.getPlayer();
    for (EmojiManager.Emoji emoji : EmojiManager.getEmojis().values()) {
      if (!message.contains(emoji.getIdentifier()))
        continue; 
      PlayerSendEmojiEvent sendEvent = new PlayerSendEmojiEvent(player, emoji);
      Bukkit.getScheduler().runTask((Plugin)ChatEmojisGUI.getPlugin(), () -> Bukkit.getPluginManager().callEvent(sendEvent));
      if (!sendEvent.isCancelled()) {
        Boolean bool = Boolean.valueOf(false);
        String restOfMessage = message;
        message = "";
        while (!bool.booleanValue()) {
          if (restOfMessage.indexOf(emoji.getIdentifier()) != -1) {
            if (restOfMessage.indexOf(emoji.getIdentifier()) == 0 || restOfMessage.charAt(restOfMessage.indexOf(emoji.getIdentifier()) - 1) != '\\') {
              message = String.valueOf(message) + restOfMessage.substring(0, restOfMessage.indexOf(emoji.getIdentifier())) + Replacements.replace(emoji.getEmoji(), player);
              restOfMessage = restOfMessage.substring(restOfMessage.indexOf(emoji.getIdentifier()) + Replacements.replace(emoji.getEmoji(), player).length());
              continue;
            } 
            message = String.valueOf(message) + restOfMessage.substring(0, restOfMessage.indexOf(emoji.getIdentifier()) - 1) + emoji.getIdentifier();
            restOfMessage = restOfMessage.substring(restOfMessage.indexOf(emoji.getIdentifier()) + emoji.getIdentifier().length());
            continue;
          } 
          bool = Boolean.valueOf(true);
        } 
      } 
    } 
    event.setMessage(Replacements.ChatColor(message));
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onCommand(PlayerCommandPreprocessEvent event) {
    String message = event.getMessage();
    Player player = event.getPlayer();
    for (EmojiManager.Emoji emoji : EmojiManager.getEmojis().values()) {
      if (!message.contains(emoji.getIdentifier()))
        continue; 
      PlayerSendEmojiEvent sendEvent = new PlayerSendEmojiEvent(player, emoji);
      Bukkit.getScheduler().runTask((Plugin)ChatEmojisGUI.getPlugin(), () -> Bukkit.getPluginManager().callEvent(sendEvent));
      if (!sendEvent.isCancelled()) {
        Boolean bool = Boolean.valueOf(false);
        String restOfMessage = message;
        message = "";
        while (!bool.booleanValue()) {
          if (restOfMessage.indexOf(emoji.getIdentifier()) != -1) {
            if (restOfMessage.indexOf(emoji.getIdentifier()) == 0 || restOfMessage.charAt(restOfMessage.indexOf(emoji.getIdentifier()) - 1) != '\\') {
              message = String.valueOf(message) + restOfMessage.substring(0, restOfMessage.indexOf(emoji.getIdentifier())) + Replacements.replace(emoji.getEmoji(), player);
              restOfMessage = restOfMessage.substring(restOfMessage.indexOf(emoji.getIdentifier()) + Replacements.replace(emoji.getEmoji(), player).length());
              continue;
            } 
            message = String.valueOf(message) + restOfMessage.substring(0, restOfMessage.indexOf(emoji.getIdentifier()) - 1) + emoji.getIdentifier();
            restOfMessage = restOfMessage.substring(restOfMessage.indexOf(emoji.getIdentifier()) + emoji.getIdentifier().length());
            continue;
          } 
          bool = Boolean.valueOf(true);
        } 
      } 
    } 
    event.setMessage(Replacements.ChatColor(message));
  }
  
  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
  public void RequestMessage(AsyncPlayerChatEvent event) {
    final Player player = event.getPlayer();
    if (!this.RPlayer.containsKey(player))
      return; 
    event.setCancelled(true);
    String request = this.RPlayer.get(player);
    this.RPlayer.remove(player);
    ChatEmojisGUI.sendTitle(player, "", "", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0));
    if (request.startsWith("create")) {
      if ((request.split(">><<")).length == 1) {
        this.RPlayer.put(player, "create>><<" + event.getMessage());
        ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Please type its new Identifier", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
        (new BukkitRunnable() {
            public void run() {
              if (!Events.this.RPlayer.containsKey(player) || (((String)Events.this.RPlayer.get(player)).split(">><<")).length != 2)
                return; 
              ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Type " + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
            }
          }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 200L);
      } else if ((request.split(">><<")).length == 2) {
        this.RPlayer.put(player, "create>><<" + request.split(">><<")[1] + ">><<" + event.getMessage());
        ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Please type its new Emoji", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
        (new BukkitRunnable() {
            public void run() {
              if (!Events.this.RPlayer.containsKey(player))
                return; 
              ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Type cancel" + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
            }
          }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 200L);
      } else if ((request.split(">><<")).length >= 3) {
        final EmojiManager.Emoji emoji = EmojiManager.createEmoji(request.split(">><<")[1], request.split(">><<")[2], event.getMessage());
        (new BukkitRunnable() {
            public void run() {
              player.openInventory((new EmojiGUI(player, emoji)).getInventory());
            }
          }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
      } 
    } else if (request.startsWith("edit")) {
      if (request.split(">><<")[1].equals("id")) {
        final EmojiManager.Emoji emoji = EmojiManager.getEmoji(request.split(">><<")[2]);
        emoji.setId(event.getMessage());
        (new BukkitRunnable() {
            public void run() {
              player.openInventory((new EmojiGUI(player, emoji)).getInventory());
            }
          }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
      } else if (request.split(">><<")[1].equals("identifier")) {
        final EmojiManager.Emoji emoji = EmojiManager.getEmoji(request.split(">><<")[2]);
        emoji.setIdentifier(event.getMessage());
        (new BukkitRunnable() {
            public void run() {
              player.openInventory((new EmojiGUI(player, emoji)).getInventory());
            }
          }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
      } else if (request.split(">><<")[1].equals("emoji")) {
        final EmojiManager.Emoji emoji = EmojiManager.getEmoji(request.split(">><<")[2]);
        emoji.setEmoji(event.getMessage());
        (new BukkitRunnable() {
            public void run() {
              player.openInventory((new EmojiGUI(player, emoji)).getInventory());
            }
          }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
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
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L); 
    if (event.getSlot() == 48) {
      holder.previousPage();
      (new BukkitRunnable() {
          public void run() {
            player.openInventory(holder.getInventory());
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
    } 
    if (event.getSlot() == 50) {
      holder.nextPage();
      (new BukkitRunnable() {
          public void run() {
            player.openInventory(holder.getInventory());
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
    } 
    if (event.getSlot() == 52) {
      this.RPlayer.put(player, "create");
      ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Please type its new Id", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
      (new BukkitRunnable() {
          public void run() {
            player.closeInventory();
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
      (new BukkitRunnable() {
          public void run() {
            if (!Events.this.RPlayer.containsKey(player))
              return; 
            ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Creating emoji", ChatColor.GRAY + "Type cancel" + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 200L);
    } 
    if (holder.getEmojisOnPage().containsKey(Integer.valueOf(event.getSlot())))
      (new BukkitRunnable() {
          public void run() {
            player.openInventory((new EmojiGUI(player, (EmojiManager.Emoji)holder.getEmojisOnPage().get(Integer.valueOf(event.getSlot())))).getInventory());
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L); 
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
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
    } 
    if (event.getSlot() == 29) {
      this.RPlayer.put(player, "edit>><<id>><<" + emoji.getId());
      ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Please type its new Id", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
      (new BukkitRunnable() {
          public void run() {
            player.closeInventory();
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
      (new BukkitRunnable() {
          public void run() {
            if (!Events.this.RPlayer.containsKey(player))
              return; 
            ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Type cancel" + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 200L);
    } 
    if (event.getSlot() == 31) {
      this.RPlayer.put(player, "edit>><<identifier>><<" + emoji.getId());
      ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Please type its new Identifier", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
      (new BukkitRunnable() {
          public void run() {
            player.closeInventory();
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
      (new BukkitRunnable() {
          public void run() {
            if (!Events.this.RPlayer.containsKey(player))
              return; 
            ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Type cancel" + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 200L);
    } 
    if (event.getSlot() == 33) {
      this.RPlayer.put(player, "edit>><<emoji>><<" + emoji.getId());
      ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Please type new Emoji", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
      (new BukkitRunnable() {
          public void run() {
            player.closeInventory();
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L);
      (new BukkitRunnable() {
          public void run() {
            if (!Events.this.RPlayer.containsKey(player))
              return; 
            ChatEmojisGUI.sendTitle(player, ChatColor.AQUA + "Editing emoji", ChatColor.GRAY + "Type cancel" + ChatColor.RED + "cancel" + ChatColor.GRAY + " to cancel", Integer.valueOf(0), Integer.valueOf(2100000000), Integer.valueOf(0));
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 200L);
    } 
    if (event.getSlot() == 49)
      (new BukkitRunnable() {
          public void run() {
            player.openInventory((new MainGUI(player)).getInventory());
          }
        }).runTaskLater((Plugin)ChatEmojisGUI.getPlugin(), 1L); 
  }
}
