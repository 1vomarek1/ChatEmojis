package com.vomarek.ChatEmojisGUI.GUI;

import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager;
import com.vomarek.ChatEmojisGUI.util.Replacements;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EmojiGUI implements InventoryHolder {
  private Inventory inventory;
  
  private Player player;
  
  private EmojiManager.Emoji emoji;
  
  public EmojiGUI(Player player, EmojiManager.Emoji emoji) {
    this.player = player;
    this.emoji = emoji;
    this.inventory = Bukkit.createInventory(this, 54, "ChatEmojisGUI >> " + emoji.getId());
    loadInventory();
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public EmojiManager.Emoji getEmoji() {
    return this.emoji;
  }
  
  public Inventory getInventory() {
    loadInventory();
    return this.inventory;
  }
  
  private EmojiGUI loadInventory() {
    this.inventory.clear();
    this.inventory.setItem(11, createGUIItem(ChatColor.GREEN + this.emoji.getId(), new ArrayList<>(Arrays.asList(new String[] { ChatColor.GRAY + this.emoji.getIdentifier(), ChatColor.GRAY + Replacements.replace(this.emoji.getEmoji(), this.player), ChatColor.GRAY + "Permission:", ChatColor.YELLOW + "ChatEmojisGUI.emoji." + this.emoji.getId() })), Material.SIGN, 1));
    this.inventory.setItem(15, createGUIItem(ChatColor.RED + "Delete emoji", new ArrayList<>(Arrays.asList(new String[] { ChatColor.RED + "Doubble click" + ChatColor.GRAY + " to delete this emoji" } )), Material.REDSTONE_BLOCK, 1));
    this.inventory.setItem(29, createGUIItem(ChatColor.GREEN + "ID", new ArrayList<>(Arrays.asList(new String[] { ChatColor.GRAY + this.emoji.getId(), ChatColor.GREEN + "Click " + ChatColor.GRAY + "to change emoji ID" } )), Material.PAPER, 1));
    this.inventory.setItem(31, createGUIItem(ChatColor.GREEN + "Identifier", new ArrayList<>(Arrays.asList(new String[] { ChatColor.GRAY + this.emoji.getIdentifier(), ChatColor.GREEN + "Click " + ChatColor.GRAY + "to change emoji Identifier" } )), Material.PAPER, 1));
    this.inventory.setItem(33, createGUIItem(ChatColor.GREEN + "Emoji", new ArrayList<>(Arrays.asList(new String[] { ChatColor.GRAY + Replacements.replace(this.emoji.getEmoji(), this.player), ChatColor.GREEN + "Click " + ChatColor.GRAY + "to change emoji" })), Material.PAPER, 1));
    this.inventory.setItem(49, createGUIItem(ChatColor.RED + "Return", new ArrayList<>(Arrays.asList(new String[] { ChatColor.RED + "Click " + ChatColor.GRAY + " to return to main GUI menu" } )), Material.BARRIER, 1));
    return this;
  }
  
  private ItemStack createGUIItem(String name, ArrayList<String> lore, Material mat, int amount) {
    ItemStack i = new ItemStack(mat, amount);
    ItemMeta iMeta = i.getItemMeta();
    iMeta.setDisplayName(name);
    iMeta.setLore(lore);
    i.setItemMeta(iMeta);
    return i;
  }
}
