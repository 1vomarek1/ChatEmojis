package com.vomarek.ChatEmojisGUI.Commands;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;
import com.vomarek.ChatEmojisGUI.GUI.MainGUI;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("chatemojisgui")) {
			
			if (args.length < 1) {
				if (sender instanceof Player) {
					if (sender.hasPermission("ChatEmojisGUI.manage")) {
						Player player = (Player) sender;
						MainGUI.open(player);
					} else {
						sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Hey! "+ChatColor.GRAY+"You can't use this command!");
					}
				} else {
					sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Hey! "+ChatColor.GRAY+"Only players can use this command!");
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("ChatEmojisGUI.reload")) {
				Date now = new Date();
				Date executed = reloadConfig();
				Long time = executed.getTime() - now.getTime();
				sender.sendMessage(ChatColor.GREEN+"Config reloaded in "+time+"ms!");
				} else {
					sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"Hey! "+ChatColor.GRAY+"You can't use this command!");
				}
			} else {
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	public Date reloadConfig(){
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				ChatEmojisGUI.getFiles().get("config").reload();
				ChatEmojisGUI.getEmojiManager().reloadEmojis();
			}
			
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
