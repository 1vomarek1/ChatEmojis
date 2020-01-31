package com.vomarek.ChatEmojisGUI.Emojis;

import java.util.HashMap;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;

/** 
* EmojiManager class, Place where emojis can be added, removed searched and more
* 
* @author 1vomarek1
*/
public class EmojiManager {
    private static HashMap<String, Emoji> emojis = new HashMap<String, Emoji>();
	
    
    /**
     * Constructor used to load all emojis
     */
	public EmojiManager() {
		for (String id : ChatEmojisGUI.getFiles().get("config").get().getConfigurationSection("Emojis").getKeys(false)) {
			emojis.put(id, new Emoji(id));
		}
	}
	
	/**
	 * Method used to remove all remojis from cache and re-add them 
	 */
	public static void reloadEmojis() {
		emojis = new HashMap<String, Emoji>();
		for (String id : ChatEmojisGUI.getFiles().get("config").get().getConfigurationSection("Emojis").getKeys(false)) {
			emojis.put(id, new Emoji(id));
		}
	}
	
	/**
	 * Method used to get specific Emoji by its ID
	 * 
	 * @return Emoji that was found
	 */
	public static Emoji getEmoji(String id) {
		if (emojis.containsKey(id)) {
			return (emojis.get(id));
		} else {
			return null;
		}
	}
	
	/**
	 * Method used to get all Emojis
	 * 
	 * @return Hash map containing all emojis
	 */
	public static HashMap<String, Emoji> getEmojis() {
		return emojis;
	}
	
	/**
	 * Method used to create new emoji.
	 * 
	 * @param id Id of emoji that this method creates
	 * @param identifier Identifier of emoji that this method creates
	 * @param emoji The actual content of emoji that this method creates 
	 * 
	 * @return Emoji that was created
	 */
	public static Emoji createEmoji(String id, String identifier, String emoji) {
		ChatEmojisGUI.getFiles().get("config").set("Emojis."+id+".identifier", identifier);
		ChatEmojisGUI.getFiles().get("config").set("Emojis."+id+".emoji", emoji);
		ChatEmojisGUI.getFiles().get("config").save();
		ChatEmojisGUI.getFileManager().saveConfig("config.yml");
		
		Emoji emojiObject = new Emoji(id);
		emojis.put(id, emojiObject);
		return emojiObject;
	}
	
	/**
	 * Method used to delete specific emoji
	 * 
	 * @param Emoji that you want to delete 
	 */
	
	public static void deleteEmoji(Emoji emoji) {
		ChatEmojisGUI.getFiles().get("config").set("Emojis."+emoji.id,null);
		ChatEmojisGUI.getFiles().get("config").save();
		emojis.remove(emoji.getId());
		emoji = null;
	}
    
	/** 
	* Class with Emoji data stored inside.
	* 
	* @author 1vomarek1
	*/
	public static class Emoji {
		String id;
		String identifier;
		String emoji;
		
		/**
		 * Emoji constructor (emoji object is created by using this constructor, It's not being created!)
		 */
		Emoji(String id) {
			if(ChatEmojisGUI.getFiles().get("config").get().get("Emojis."+id, null) != null) {
				this.id = id;
				this.identifier = ChatEmojisGUI.getFiles().get("config").get().getString("Emojis."+id+".identifier");
				this.emoji = ChatEmojisGUI.getFiles().get("config").get().getString("Emojis."+id+".emoji");
			} else {
				throw new IllegalArgumentException("This emoji was not found!");
			}
		}
		
		/**
		 * Get ID of emoji
		 * 
		 * @return ID of emoji
		 */
		public String getId() {
			return id;
		}
		
		/**
		 * Get Identifier of emoji
		 * 
		 * @return Identifier of emoji
		 */
		public String getIdentifier() {
			return identifier;
		}
		
		/**
		 * Get actual emoji content
		 * 
		 * @return actual emoji content
		 */
		public String getEmoji() {
			return emoji;
		}
		
		/**
		 * Change Identifier of emoji
		 * 
		 * @param identifier New Identifier of this emoji
		 * 
		 */
		public void setIdentifier(String identifier) {
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+this.id+".identifier",identifier);
			ChatEmojisGUI.getFiles().get("config").save();
			this.identifier = identifier;
		}
		
		/**
		 * Change ID of emoji
		 * 
		 * @param id New ID of this emoji
		 * 
		 */
		public void setId(String id) {
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+id+".identifier",this.identifier);
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+id+".emoji",this.emoji);
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+this.id,null);
			ChatEmojisGUI.getFiles().get("config").save();
			this.id = id;
			EmojiManager.reloadEmojis();
		}
		
		/**
		 * Change actual emoji content of this emoji
		 * 
		 * @param emoji New actual emoji content
		 * 
		 */
		public void setEmoji(String emoji) {
			ChatEmojisGUI.getFiles().get("config").set("Emojis."+this.id+".emoji",emoji);
			ChatEmojisGUI.getFiles().get("config").save();
			this.emoji = emoji;
		}
		
	}
}
