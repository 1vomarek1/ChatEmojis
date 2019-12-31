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
		
		if (cmd.getName().equalsIgnoreCase("emojis")) {
			
			if (args.length < 1) {
				if (sender instanceof Player) {
					if (sender.hasPermission("ChatEmojisGUI.admin")) {
						Player player = (Player) sender;
						MainGUI.open(player);
					}
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				Date now = new Date();
				Date executed = reloadConfig();
				Long time = executed.getTime() - now.getTime();
				sender.sendMessage(ChatColor.GREEN+"Config reloaded in "+time+"ms!");
			}
			
			
		}
		
		return true;
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
