package com.vomarek.ChatEmojisGUI.Commands;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;
import com.vomarek.ChatEmojisGUI.Emojis.EmojiManager;
import com.vomarek.ChatEmojisGUI.Files.FileManager.Config;
import com.vomarek.ChatEmojisGUI.GUI.MainGUI;

public class Commands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("chatemojisgui")) {
            if (args.length < 1) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("ChatEmojisGUI.manage")) {
                        final Player player = (Player)sender;
                        (new BukkitRunnable() {
                            public void run() {
                                player.openInventory((new MainGUI(player)).getInventory());
                            }
                        }).runTaskLater(ChatEmojisGUI.getPlugin(), 1L);
                    } else {
                        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Hey! " + ChatColor.GRAY + "You can't use this command!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "" +ChatColor.BOLD + "Hey! " + ChatColor.GRAY + "Only players can use this command!");
                }
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("ChatEmojisGUI.reload")) {
                    Date now = new Date();
                    Date executed = reloadConfig();
                    long time = executed.getTime() - now.getTime();
                    sender.sendMessage(ChatColor.GREEN + "Config reloaded in " + time + "ms!");
                } else {
                    sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Hey! " + ChatColor.GRAY + "You can't use this command!");
                }
            } else {
                return false;
            }
            return true;
        }
        return false;
    }

    public Date reloadConfig() {
        Runnable runnable = () -> {
            Config config = ChatEmojisGUI.getFiles().get("config");
            config.reload();

            if (config.get().getConfigurationSection("").getKeys(true).isEmpty()) {
                config.saveDefaultConfig();
                config.reload();
            }
            EmojiManager.reloadEmojis();
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
            return new Date();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
