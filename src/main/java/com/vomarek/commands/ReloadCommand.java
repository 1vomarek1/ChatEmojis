package com.vomarek.commands;

import com.vomarek.ChatEmojis;
import com.vomarek.data.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Date;

public class ReloadCommand implements CommandExecutor {
    private final ChatEmojis plugin;

    public ReloadCommand(final ChatEmojis plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("chatemojis")) {

            if (!sender.hasPermission("")) return true;

            if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {

                Date start = new Date();
                final Date finish = reloadConfig();
                long time = finish.getTime() - start.getTime();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfig reloaded in " + time + "ms!"));
            }

        }

        return true;
    }

    private Date reloadConfig() {
        Runnable runnable = () -> {
            Configuration config = plugin.getConfig();
            config.reload();

            if (config.getConfigurationSection("").getKeys(true).isEmpty()) {
                config.saveDefault();
                config.reload();
            }

            final ConfigurationSection section = plugin.getConfig().getConfigurationSection("Emojis");

            for (final String id : section.getKeys(false)) {

                plugin.getEmojiManager().createEmoji(id, section.getString(id+".identifier", ""), section.getString(id+".emoji", ""));

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
