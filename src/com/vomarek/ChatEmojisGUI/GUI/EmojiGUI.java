package com.vomarek.ChatEmojisGUI.GUI;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;
import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager.Emoji;
import com.vomarek.ChatEmojisGUI.util.Replacements;

public class EmojiGUI {
	
	public static void open (Player player, Emoji emoji) {
		Inventory inv = Bukkit.createInventory(null, 54, "ChatEmojisGUI >> "+emoji.getId());
		
		inv.setItem(11, createGUIItem(ChatColor.GREEN+emoji.getId(), new ArrayList<String>(Arrays.asList(ChatColor.GRAY+emoji.getIdentifier(), ChatColor.GRAY+Replacements.replace(emoji.getEmoji(), player) ,ChatColor.GRAY+"Permission:",ChatColor.YELLOW+"ChatEmojisGUI.emoji."+emoji.getId())), Material.SIGN, 1));
		
		inv.setItem(15, createGUIItem(ChatColor.RED+"Delete emoji", new ArrayList<String>(Arrays.asList(ChatColor.RED+"Doubble click"+ChatColor.GRAY+" to delete this emoji")), Material.REDSTONE_BLOCK, 1));
		
		inv.setItem(29, createGUIItem(ChatColor.GREEN+"ID", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+emoji.getId(), ChatColor.GREEN+"Click "+ChatColor.GRAY+"to change emoji ID")), Material.PAPER, 1));
		inv.setItem(31, createGUIItem(ChatColor.GREEN+"Identifier", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+emoji.getIdentifier(), ChatColor.GREEN+"Click "+ChatColor.GRAY+"to change emoji Identifier")), Material.PAPER, 1));
		inv.setItem(33, createGUIItem(ChatColor.GREEN+"Emoji", new ArrayList<String>(Arrays.asList(ChatColor.GRAY+Replacements.replace(emoji.getEmoji(), player), ChatColor.GREEN+"Click "+ChatColor.GRAY+"to change emoji")), Material.PAPER, 1));
		
		
		inv.setItem(49, createGUIItem(ChatColor.RED+"Return", new ArrayList<String>(Arrays.asList(ChatColor.RED+"Click "+ChatColor.GRAY+" to return to main GUI menu")), Material.BARRIER, 1));
		
		new BukkitRunnable() {

			@Override
			public void run() {
				player.openInventory(inv);
			}
			
		}.runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
	}
	
	public static ItemStack createGUIItem(String name, ArrayList<String> lore, Material mat, Integer amount) {
		ItemStack item = new ItemStack(mat);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setDisplayName(name);
		iMeta.setLore(lore);
		item.setItemMeta(iMeta);
		item.setAmount(amount);
		return item;
	}
	
}
