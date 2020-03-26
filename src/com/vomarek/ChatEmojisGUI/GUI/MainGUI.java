package com.vomarek.ChatEmojisGUI.GUI;

import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager;
import com.vomarek.ChatEmojisGUI.util.Replacements;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainGUI implements InventoryHolder {
  private Player player;
  
  private Inventory inventory;
  
  private Integer page;
  
  private HashMap<Integer, EmojiManager.Emoji> emojisOnPage;
  
  public MainGUI(Player player) {
    this.player = player;
    this.inventory = Bukkit.createInventory(this, 54, "ChatEmojisGUI");
    this.page = Integer.valueOf(0);
    this.emojisOnPage = new HashMap<>();
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public Inventory getInventory() {
    loadInventory();
    return this.inventory;
  }
  
  public MainGUI nextPage() {
    if (EmojiManager.getEmojis().size() <= (this.page.intValue() + 1) * 28) {
      this.page = Integer.valueOf(0);
    } else {
      this.page = Integer.valueOf(this.page.intValue() + 1);
    } 
    return this;
  }
  
  public MainGUI previousPage() {
    if (this.page.intValue() == 0) {
      this.page = Integer.valueOf((int)Math.ceil((EmojiManager.getEmojis().size() / 28)));
    } else {
      this.page = Integer.valueOf(this.page.intValue() - 1);
    } 
    return this;
  }
  
  public HashMap<Integer, EmojiManager.Emoji> getEmojisOnPage() {
    return this.emojisOnPage;
  }
  
  private MainGUI loadInventory() {
    this.inventory.clear();
    this.emojisOnPage.clear();
    int i = 0;
    int pos = 10;
    HashMap<String, EmojiManager.Emoji> emojis = EmojiManager.getEmojis();
    for (EmojiManager.Emoji emoji : emojis.values()) {
      if (pos == 17)
        pos += 2; 
      if (pos == 26)
        pos += 2; 
      if (pos == 35)
        pos += 2; 
      if (i >= 28 * this.page.intValue() && i < 28 * (this.page.intValue() + 1)) {
        this.emojisOnPage.put(Integer.valueOf(pos), emoji);
        this.inventory.setItem(pos, createGUIItem(ChatColor.GREEN + emoji.getId(), new ArrayList<>(Arrays.asList(new String[] { ChatColor.GRAY + emoji.getIdentifier(), ChatColor.GRAY + Replacements.replace(emoji.getEmoji(), this.player), ChatColor.GREEN + "Click " + ChatColor.GRAY + "to manage emoji" })), Material.EMPTY_MAP, 1));
        pos++;
      } 
      i++;
    } 
    this.inventory.setItem(49, createGUIItem(ChatColor.RED + "Close menu", new ArrayList<>(Arrays.asList(new String[] { ChatColor.RED + "Click " + ChatColor.GRAY + "to close this menu" } )), Material.BARRIER, 1));
    this.inventory.setItem(48, createGUIItem(ChatColor.GREEN + "Previous page", new ArrayList<>(Arrays.asList(new String[] { ChatColor.GREEN + "Click " + ChatColor.GRAY + "to go to previous page" } )), Material.ARROW, 1));
    this.inventory.setItem(50, createGUIItem(ChatColor.GREEN + "Next page", new ArrayList<>(Arrays.asList(new String[] { ChatColor.GREEN + "Click " + ChatColor.GRAY + "to go to next page" } )), Material.ARROW, 1));
    this.inventory.setItem(52, createGUIItem(ChatColor.GREEN + "New emoji", new ArrayList<>(Arrays.asList(new String[] { ChatColor.GREEN + "Click " + ChatColor.GRAY + "to go to create new emoji" } )), Material.ANVIL, 1));
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
