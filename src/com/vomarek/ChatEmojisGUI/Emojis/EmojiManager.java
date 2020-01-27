package com.vomarek.ChatEmojisGUI.Emojis;

import java.util.HashMap;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;

public class EmojiManager {
    private static HashMap<String, Emoji> emojis = new HashMap<String, Emoji>();
	
	public EmojiManager() {
		for (String id : ChatEmojisGUI.getFiles().get("config").get().getConfigurationSection("Emojis").getKeys(false)) {
			emojis.put(id, new Emoji(id));
		}
	}
	
	public static void reloadEmojis() {
		emojis = new HashMap<String, Emoji>();
		for (String id : ChatEmojisGUI.getFiles().get("config").get().getConfigurationSection("Emojis").getKeys(false)) {
			emojis.put(id, new Emoji(id));
		}
	}
	
	public static Emoji getEmoji(String id) {
		if (emojis.containsKey(id)) {
			return (emojis.get(id));
		} else {
			return null;
		}
	}
	
	public static HashMap<String, Emoji> getEmojis() {
		return emojis;
	}
	
	public static Emoji createEmoji(String id, String identifier, String emoji) {
		ChatEmojisGUI.getFiles().get("config").set("Emojis."+id+".identifier", identifier);
		ChatEmojisGUI.getFiles().get("config").set("Emojis."+id+".emoji", emoji);
		ChatEmojisGUI.getFiles().get("config").save();
		ChatEmojisGUI.getFileManager().saveConfig("config.yml");
		
		Emoji emojiObject = new Emoji(id);
		emojis.put(id, emojiObject);
		return emojiObject;
	}
	
	public static void deleteEmoji(Emoji emoji) {
		ChatEmojisGUI.getFiles().get("config").set("Emojis."+emoji.id,null);
		ChatEmojisGUI.getFiles().get("config").save();
		emojis.remove(emoji.getId());
		emoji = null;
	}
    
	public static class Emoji {
		String id;
		String identifier;
		String emoji;
		
		Emoji(String id) {
			if(ChatEmojisGUI.getFiles().get("config").get().get("Emojis."+id, null) != null) {
				this.id = id;
				this.identifier = ChatEmojisGUI.getFiles().get("config").get().getString("Emojis."+id+".identifier");
				this.emoji = ChatEmojisGUI.getFiles().get("config").get().getString("Emojis."+id+".emoji");
			} else {
				throw new IllegalArgumentException("This emoji was not found");
			}
		}
		
		public String getId() {
			return id;
		}
		
		public String getIdentifier() {
			return identifier;
		}
		
		public String getEmoji() {
			return emoji;
		}
		
		public void setIdentifier(String identifier) {
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+this.id+".identifier",identifier);
			ChatEmojisGUI.getFiles().get("config").save();
			this.identifier = identifier;
		}
		
		public void setId(String id) {
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+id+".identifier",this.identifier);
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+id+".emoji",this.emoji);
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+this.id,null);
			ChatEmojisGUI.getFiles().get("config").save();
			this.id = id;
			EmojiManager.reloadEmojis();
		}
		
		public void setEmoji(String emoji) {
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+this.id+".emoji",emoji);
			ChatEmojisGUI.getFiles().get("config").save();
			this.emoji = emoji;
		}
		
	}
}
