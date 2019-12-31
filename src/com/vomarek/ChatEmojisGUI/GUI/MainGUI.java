package com.vomarek.ChatEmojisGUI.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;
import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager.Emoji;
import com.vomarek.ChatEmojisGUI.Files.FileManager.Config;

import net.md_5.bungee.api.ChatColor;

public class MainGUI {
	public static HashMap<Player, Integer>  pg = new HashMap<Player, Integer>();
	
	public static void open (Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, "ChatEmojisGUI");
		
		if (!pg.containsKey(player)) pg.put(player, 0);
		
		Config config = ChatEmojisGUI.getFiles().get("config");
		
		int i = 0;
		int pos = 10;
		for (String s : config.get().getConfigurationSection("Emojis").getKeys(false)) {
			Emoji emoji = ChatEmojisGUI.getEmojiManager().getEmoji(s);
			
			if (pos == 17) pos += 2;
			if (pos == 26) pos += 2;
			if (pos == 35) pos += 2;
			
			if (i >= (28 * pg.get(player)) && i < (28 * (pg.get(player) +1))) {
				inv.setItem(pos, createGUIItem(ChatColor.GREEN+""+emoji.getId(), new ArrayList<String>(Arrays.asList(ChatColor.GRAY+emoji.getIdentifier(),ChatColor.GRAY+emoji.getEmoji(),ChatColor.GREEN+"Click "+ChatColor.GRAY+"to manage emoji")), Material.EMPTY_MAP, 1));
				pos++;
			}
			i++;
			
		}
		
		inv.setItem(49, createGUIItem(ChatColor.RED+"Close menu", new ArrayList<String>(Arrays.asList(ChatColor.RED+"Click "+ChatColor.GRAY+"to close this menu")), Material.BARRIER, 1));
		inv.setItem(48, createGUIItem(ChatColor.GREEN+"Previous page", new ArrayList<String>(Arrays.asList(ChatColor.GREEN+"Click "+ChatColor.GRAY+"to go to previous page")), Material.ARROW, 1));
		inv.setItem(50, createGUIItem(ChatColor.GREEN+"Next page", new ArrayList<String>(Arrays.asList(ChatColor.GREEN+"Click "+ChatColor.GRAY+"to go to next page")), Material.ARROW, 1));
		
		inv.setItem(52, createGUIItem(ChatColor.GREEN+"New emoji", new ArrayList<String>(Arrays.asList(ChatColor.GREEN+"Click "+ChatColor.GRAY+"to go to create new emoji")), Material.ANVIL, 1));
		
		
		new BukkitRunnable() {

			@Override
			public void run() {
				player.openInventory(inv);
			}
		}.runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
	}

	private static ItemStack createGUIItem(String name, ArrayList<String> lore, Material mat, Integer amount) {
		ItemStack item = new ItemStack(mat);
		ItemMeta iMeta = item.getItemMeta();
		iMeta.setLore(lore);
		iMeta.setDisplayName(name);
		item.setItemMeta(iMeta);
		item.setAmount(amount);
		return item;
	}
	
}
