package com.vomarek.ChatEmojisGUI.Emojis;

import java.util.HashMap;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;

public class EmojiManager {
    private HashMap<String, Emoji> emojis = new HashMap<String, Emoji>();
	
	public EmojiManager() {
		for (String id : ChatEmojisGUI.getFiles().get("config").get().getConfigurationSection("Emojis").getKeys(false)) {
			emojis.put(id, new Emoji(id));
		}
	}
	
	public Emoji getEmoji(String id) {
		if (emojis.containsKey(id)) {
			return (emojis.get(id));
		} else {
			return null;
		}
	}
	
	public HashMap<String, Emoji> getEmojis() {
		return emojis;
	}
	
	public Emoji createEmoji(String id, String identifier, String emoji) {
		ChatEmojisGUI.getFiles().get("config").set("Emojis."+id+".identifier", identifier);
		ChatEmojisGUI.getFiles().get("config").set("Emojis."+id+".emoji", emoji);
		ChatEmojisGUI.getFiles().get("config").save();
		ChatEmojisGUI.getFileManager().saveConfig("config.yml");
		
		Emoji emojiObject = new Emoji(id);
		emojis.put(id, emojiObject);
		return emojiObject;
	}
    
	public class Emoji {
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
		
		public void setEmoji(String emoji) {
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+this.id+".emoji",emoji);
			ChatEmojisGUI.getFiles().get("config").save();
			this.emoji = emoji;
		}
		
		public void deleteEmoji() {
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+this.id,null);
			ChatEmojisGUI.getFiles().get("config").save();
			this.emoji = null;
			this.id = null;
			this.identifier = null;
		}
		
	}
}
