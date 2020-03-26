package com.vomarek.ChatEmojisGUI.Emojis;

import com.vomarek.ChatEmojisGUI.ChatEmojisGUI;
import com.vomarek.ChatEmojisGUI.Files.FileManager;
import java.util.HashMap;

public class EmojiManager {
  private static HashMap<String, Emoji> emojis = new HashMap<>();
  
  public EmojiManager() {
    for (String id : ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).get().getConfigurationSection("Emojis").getKeys(false))
      emojis.put(id, new Emoji(id)); 
  }
  
  public static void reloadEmojis() {
    emojis = new HashMap<>();
    for (String id : ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).get().getConfigurationSection("Emojis").getKeys(false))
      emojis.put(id, new Emoji(id)); 
  }
  
  public static Emoji getEmoji(String id) {
    if (emojis.containsKey(id))
      return emojis.get(id); 
    return null;
  }
  
  public static HashMap<String, Emoji> getEmojis() {
    return emojis;
  }
  
  public static Emoji createEmoji(String id, String identifier, String emoji) {
    ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).set("Emojis." + id + ".identifier", identifier);
    ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).set("Emojis." + id + ".emoji", emoji);
    ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).save();
    ChatEmojisGUI.getFileManager().saveConfig("config.yml");
    Emoji emojiObject = new Emoji(id);
    emojis.put(id, emojiObject);
    return emojiObject;
  }
  
  public static void deleteEmoji(Emoji emoji) {
    ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).set("Emojis." + emoji.id, null);
    ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).save();
    emojis.remove(emoji.getId());
    emoji = null;
  }
  
  public static class Emoji {
    String id;
    
    String identifier;
    
    String emoji;
    
    Emoji(String id) {
      if (((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).get().get("Emojis." + id, null) != null) {
        this.id = id;
        this.identifier = ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).get().getString("Emojis." + id + ".identifier");
        this.emoji = ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).get().getString("Emojis." + id + ".emoji");
      } else {
        throw new IllegalArgumentException("This emoji was not found!");
      } 
    }
    
    public String getId() {
      return this.id;
    }
    
    public String getIdentifier() {
      return this.identifier;
    }
    
    public String getEmoji() {
      return this.emoji;
    }
    
    public void setIdentifier(String identifier) {
      ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).set("Emojis." + this.id + ".identifier", identifier);
      ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).save();
      this.identifier = identifier;
    }
    
    public void setId(String id) {
      ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).set("Emojis." + id + ".identifier", this.identifier);
      ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).set("Emojis." + id + ".emoji", this.emoji);
      ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).set("Emojis." + this.id, null);
      ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).save();
      this.id = id;
      EmojiManager.reloadEmojis();
    }
    
    public void setEmoji(String emoji) {
      ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).set("Emojis." + this.id + ".emoji", emoji);
      ((FileManager.Config)ChatEmojisGUI.getFiles().get("config")).save();
      this.emoji = emoji;
    }
  }
}
