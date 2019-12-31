package com.vomarek.ChatEmojisGUI.Events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;
import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager.Emoji;
import com.vomarek.ChatEmojisGUI.GUI.EmojiGUI;
import com.vomarek.ChatEmojisGUI.GUI.MainGUI;

import net.md_5.bungee.api.ChatColor;

public class oldEvents implements Listener{
	HashMap<Player, String> RPlayer = new HashMap<Player, String>();
	
	@EventHandler
	public void chatEvent(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		Player player = event.getPlayer();
		for(Map.Entry<String, Emoji> entry : ChatEmojisGUI.getEmojiManager().getEmojis().entrySet()) {
			Emoji emoji = entry.getValue();
			
			if (!message.contains(emoji.getIdentifier())) continue;
			
			
			if (!player.hasPermission("ChatEmojisGUI.emoji."+emoji.getId()) && !player.hasPermission("ChatEmojisGUI.emoji.*")) continue;
			
			PlayerSendEmojiEvent sendEvent = new PlayerSendEmojiEvent(player, emoji);
			Bukkit.getScheduler().runTask(ChatEmojisGUI.getPlugin(), () -> Bukkit.getPluginManager().callEvent(sendEvent));
			if (!sendEvent.isCancelled()) {
				message = message.replace(sendEvent.getEmoji().getIdentifier(), sendEvent.getEmoji().getEmoji());
			}
			
		}
		
		event.setMessage(message);
	}
	
	public void callEvent(Event event) {
		Bukkit.getScheduler().runTask(ChatEmojisGUI.getPlugin(), () -> Bukkit.getPluginManager().callEvent(event));
	}
	
	@EventHandler
	public void RequestMessage(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (!RPlayer.containsKey(player)) return;
		event.setCancelled(true);
		String request = RPlayer.get(player);
		RPlayer.remove(player);
		ChatEmojisGUI.sendTitle(player, "","", 0, 0, 0);
		
		if (request.startsWith("create")) {
			if (request.split(">><<").length == 1) {
				RPlayer.put(player, "create>><<"+event.getMessage());
				
				ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Creating emoji", ChatColor.GRAY+"Please type its new Identifier", 0, 2100000000, 0);
				
					new BukkitRunnable() {
						
						@Override
						public void run() {
							if (!RPlayer.containsKey(player) || RPlayer.get(player).split(">><<").length != 2) return;
							ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Creating emoji", ChatColor.GRAY+"Type "+ChatColor.RED+"cancel"+ChatColor.GRAY+" to cancel", 0, 2100000000, 0);
						}
					}.runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
			} else if (request.split(">><<").length == 2) {
				RPlayer.put(player, "create>><<"+request.split(">><<")[1]+">><<"+event.getMessage());
				
				ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Creating emoji", ChatColor.GRAY+"Please type its new Emoji", 0, 2100000000, 0);
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						if (!RPlayer.containsKey(player)) return;
						ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Creating emoji", ChatColor.GRAY+"Type cancel"+ChatColor.RED+"cancel"+ChatColor.GRAY+" to cancel", 0, 2100000000, 0);	
					}
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
			} else if (request.split(">><<").length >= 3) {
				Emoji emoji = ChatEmojisGUI.getEmojiManager().createEmoji(request.split(">><<")[1], request.split(">><<")[2], event.getMessage());
				EmojiGUI.open(player, emoji);
			}
		} else if (request.startsWith("edit")) {
			if (request.split(">><<")[1].equals("id")) {
				Emoji emoji = ChatEmojisGUI.getEmojiManager().getEmoji(request.split(">><<")[2]);
				emoji.setId(event.getMessage());
				EmojiGUI.open(player, emoji);
			} else if (request.split(">><<")[1].equals("identifier")) {
				Emoji emoji = ChatEmojisGUI.getEmojiManager().getEmoji(request.split(">><<")[2]);
				emoji.setIdentifier(event.getMessage());
				EmojiGUI.open(player, emoji);
			} else if (request.split(">><<")[1].equals("emoji")) {
				Emoji emoji = ChatEmojisGUI.getEmojiManager().getEmoji(request.split(">><<")[2]);
				emoji.setEmoji(event.getMessage());
				EmojiGUI.open(player, emoji);
			}
		}
		
		
	}
	
	@EventHandler
	public void InventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		Player player = (Player) event.getWhoClicked();
		
		if (event.getInventory().getName().equalsIgnoreCase("ChatEmojisGUI")) {
			event.setCancelled(true);
			
			if (event.getSlot() == 49) {
				new BukkitRunnable () {

					@Override
					public void run() {
						player.closeInventory();					
					}
					
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
			} else if (event.getSlot() == 48) {
				if (!MainGUI.pg.containsKey(player)) MainGUI.pg.put(player, 0);
				
				if (MainGUI.pg.get(player) == 0) {
					MainGUI.pg.put(player, (int) (Math.floor(ChatEmojisGUI.getFiles().get("config").get().getConfigurationSection("Emojis").getKeys(false).size() / 24)));
				} else {
					MainGUI.pg.put(player, MainGUI.pg.get(player) -1);
				}
				
				MainGUI.open(player);
			} else if (event.getSlot() == 50) {
				if (!MainGUI.pg.containsKey(player)) MainGUI.pg.put(player, 0);
				
				if (MainGUI.pg.get(player) == (int) (Math.floor(ChatEmojisGUI.getFiles().get("config").get().getConfigurationSection("Emojis").getKeys(false).size() / 24))) {
					MainGUI.pg.put(player, 0);
				} else {
					MainGUI.pg.put(player, MainGUI.pg.get(player) + 1);
				}
				
				MainGUI.open(player);
			} else if (event.getSlot() == 52) {
				RPlayer.put(player, "create");
				
				ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Creating emoji", ChatColor.GRAY+"Please type its new Id", 0, 2100000000, 0);
				
				new BukkitRunnable () {

					@Override
					public void run() {
						player.closeInventory();						
					}
					
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						if (!RPlayer.containsKey(player) || RPlayer.get(player).split(">><<").length != 1) return;
						ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Creating emoji", ChatColor.GRAY+"Type "+ChatColor.RED+"cancel"+ChatColor.GRAY+" to cancel", 0, 2100000000, 0);
					}
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
				
			} else if (event.getSlot() >= 10 && event.getSlot() <= 43) {
				if (event.getInventory().getItem(event.getSlot()) == null) return;
				
				String emojiid = event.getInventory().getItem(event.getSlot()).getItemMeta().getDisplayName().replace("§a", "");
				Emoji emoji = ChatEmojisGUI.getEmojiManager().getEmoji(emojiid);
				
				if (emoji == null) return;
				
				EmojiGUI.open(player, emoji);
			}
		} else if (event.getInventory().getName().startsWith("ChatEmojisGUI >> ")) {
			String emojiid = event.getInventory().getName().split(" >> ")[1];
			Emoji emoji = ChatEmojisGUI.getEmojiManager().getEmoji(emojiid);
			if (emoji == null) {
				new BukkitRunnable() {

					@Override
					public void run() {
						MainGUI.open(player);
					}
					
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
			}
			event.setCancelled(true);
			
			
			
			if (event.getSlot() == 49) {
				new BukkitRunnable() {

					@Override
					public void run() {
						MainGUI.open(player);
					}
					
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
			} else if (event.getSlot() == 15) {
				if (event.getClick().equals(ClickType.DOUBLE_CLICK)) {
					ChatEmojisGUI.getEmojiManager().deleteEmoji(emoji);
					new BukkitRunnable() {

						@Override
						public void run() {
							MainGUI.open(player);
						}
						
					}.runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
				}
			} else if (event.getSlot() == 29) {
				RPlayer.put(player, "edit>><<id>><<"+emoji.getId());
				
				ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Editing emoji", ChatColor.GRAY+"Please type its new Id", 0, 2100000000, 0);
				
				new BukkitRunnable () {

					@Override
					public void run() {
						player.closeInventory();						
					}
					
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						if (!RPlayer.containsKey(player)) return;
						ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Editing emoji", ChatColor.GRAY+"Type "+ChatColor.RED+"cancel"+ChatColor.GRAY+" to cancel", 0, 2100000000, 0);
					}
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
			}else if (event.getSlot() == 31) {
				RPlayer.put(player, "edit>><<identifier>><<"+emoji.getId());
				
				ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Editing emoji", ChatColor.GRAY+"Please type its new Identifier", 0, 2100000000, 0);
				
				new BukkitRunnable () {

					@Override
					public void run() {
						player.closeInventory();						
					}
					
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						if (!RPlayer.containsKey(player)) return;
						ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Editing emoji", ChatColor.GRAY+"Type "+ChatColor.RED+"cancel"+ChatColor.GRAY+" to cancel", 0, 2100000000, 0);
					}
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
			}else if (event.getSlot() == 33) {
				RPlayer.put(player, "edit>><<emoji>><<"+emoji.getId());
				
				ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Editing emoji", ChatColor.GRAY+"Please type new Emoji", 0, 2100000000, 0);
				
				new BukkitRunnable () {

					@Override
					public void run() {
						player.closeInventory();						
					}
					
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
				
				new BukkitRunnable() {
					
					@Override
					public void run() {
						if (!RPlayer.containsKey(player)) return;
						ChatEmojisGUI.sendTitle(player, ChatColor.AQUA+"Editing emoji", ChatColor.GRAY+"Type "+ChatColor.RED+"cancel"+ChatColor.GRAY+" to cancel", 0, 2100000000, 0);
					}
				}.runTaskLater(ChatEmojisGUI.getPlugin(), 200L);
			}
			
		}
		
	}
	
}
