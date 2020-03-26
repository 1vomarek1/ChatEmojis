package com.vomarek.ChatEmojisGUI.Title;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Title_1_8_R1 implements Title {
  public void sendTitle(Player player, String titlestring, String subtitlestring, Integer fadeIn, Integer stay, Integer fadeOut) {
    IChatBaseComponent title = ChatSerializer.a("{\"text\": \"" + titlestring + "\"}");
    IChatBaseComponent subtitle = ChatSerializer.a("{\"text\": \"" + subtitlestring + "\"}");
    PacketPlayOutTitle times = new PacketPlayOutTitle(EnumTitleAction.TIMES, title, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
    PacketPlayOutTitle TitlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, title, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
    PacketPlayOutTitle SubTitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitle, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(times);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(TitlePacket);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(SubTitlePacket);
  }
}
