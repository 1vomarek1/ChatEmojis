package com.vomarek.ChatEmojisGUI.Title;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;

public class Title_1_8_R1 implements Title{

	@Override
	public void sendTitle(Player player, String titlestring, String subtitlestring, Integer fadeIn, Integer stay, Integer fadeOut) {
		
		IChatBaseComponent title = ChatSerializer.a("{\"text\": \""+titlestring+"\"}");
		IChatBaseComponent subtitle = ChatSerializer.a("{\"text\": \""+subtitlestring+"\"}");
		
		PacketPlayOutTitle times = new PacketPlayOutTitle(EnumTitleAction.TIMES, title, fadeIn, stay, fadeOut);
		PacketPlayOutTitle TitlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, title, fadeIn, stay, fadeOut);
		PacketPlayOutTitle SubTitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitle, fadeIn, stay, fadeOut);
		
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(times);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(TitlePacket);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(SubTitlePacket);
	}

}
