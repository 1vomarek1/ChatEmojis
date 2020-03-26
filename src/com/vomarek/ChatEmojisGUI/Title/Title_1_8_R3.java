package com.vomarek.ChatEmojisGUI.Title;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title_1_8_R3 implements Title {
  public void sendTitle(Player player, String titlestring, String subtitlestring, Integer fadeIn, Integer stay, Integer fadeOut) {
    IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + titlestring + "\"}");
    IChatBaseComponent subtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitlestring + "\"}");
    PacketPlayOutTitle times = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, title, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
    PacketPlayOutTitle TitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, title, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
    PacketPlayOutTitle SubTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitle, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(times);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(TitlePacket);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(SubTitlePacket);
  }
}
