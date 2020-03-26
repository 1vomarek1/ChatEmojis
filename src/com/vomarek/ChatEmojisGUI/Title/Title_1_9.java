package com.vomarek.ChatEmojisGUI.Title;

import org.bukkit.entity.Player;

public class Title_1_9 implements Title {
  public void sendTitle(Player player, String title, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut) {
    player.sendTitle(title, subtitle, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
  }
}
