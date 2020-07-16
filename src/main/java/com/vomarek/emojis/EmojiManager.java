package com.vomarek.emojis;

import com.vomarek.ChatEmojis;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class EmojiManager {
    protected final ChatEmojis plugin;

    private final HashMap<String, Emoji> emojis = new HashMap<>();

    /**
     * Constructor of emoji manager (use only in onEnable)
     * @see ChatEmojis#getEmojiManager()
     */
    public EmojiManager (final ChatEmojis plugin) {
        this.plugin = plugin;

        final ConfigurationSection section = plugin.getConfig().getConfigurationSection("Emojis");

        for (final String id : section.getKeys(false)) {

            createEmoji(id, section.getString(id+".identifier", ""), section.getString(id+".emoji", ""));

        }

    }
    /**
     * Returns hashmap with all emojis
     * @return hashmap with all emojs
     */
    public HashMap<String, Emoji> getEmojis() {
        return emojis;
    }

    /**
     * Creates emoji that will be saved to config
     * @see EmojiManager#createEmoji(String, String, String, Boolean)
     * @param id Id of new emoji (Id is used in permissions)
     * @param identifier Identifier is text in chat that will be replaced with emoji
     * @param emoji Emoji is text that will replace identifier
     * @return Emoji that was created
     */
    public Emoji createEmoji (final String id, final String identifier, final String emoji) {
        return createEmoji(id, identifier, emoji, true);
    }

    /**
     * Creates emoji that will be saved to config
     * @see EmojiManager#createEmoji(String, String, String, Boolean)
     * @param id Id of new emoji (Id is used in permissions)
     * @param identifier Identifier is text in chat that will be replaced with emoji
     * @param emoji Emoji is text that will replace identifier
     * @param saveEmoji Should emoji be saved when it is edited
     * @return Emoji that was created
     */
    public Emoji createEmoji (final String id, final String identifier, final String emoji, final Boolean saveEmoji) {
        if (emojis.containsKey(id)) return null;


        if (plugin.getConfig().getString("Emojis."+id+".identifier", null) == null) return null;

        plugin.getConfig().set("Emojis."+id+".identifier", identifier);
        plugin.getConfig().set("Emojis."+id+".emoji", emoji);
        plugin.getConfig().save();

        final Emoji emojiObj = new Emoji(id, identifier, emoji, saveEmoji);

        emojis.put(id, emojiObj);

        return emojiObj;
    }

    /**
     * Returns emoji by parameter id
     * @param id Id of emoji you want to get
     * @return emoji that was found or null
     */
    public Emoji getEmojiById(final String id) {
        if (emojis.containsKey(id)) return emojis.get(id);
        return null;
    }

    public class Emoji {
        private final String id;
        private String identifier;
        private String emoji;

        private final Boolean saveEmoji;

        protected Emoji (final String id, final String identifier, final String emoji, final Boolean saveEmoji) {

            this.id = id;
            this.identifier = identifier;
            this.emoji = emoji;

            this.saveEmoji = saveEmoji;
        }

        /**
         * Returns id of emoji
         * @return id of emoji
         */
        public String getId () {
            return id;
        }

        /**
         * Returns part of emoji that will be replaced
         * @return part of emoji that will be replaced
         */
        public String getIdentifier () {
            return identifier;
        }

        /**
         * Returns text that replaces emojis identifier
         * @return text that replaces emojis identifier
         */
        public String getEmoji () {
            return emoji;
        }

        /**
         * Returns if emoji is being saved
         * @return wether emoji is being saved after it is edited
         */
        public Boolean getSaveEmoji() {
            return saveEmoji;
        }

        /**
         * Changes identifier of emoji - part of emoji that is replaced with the emoji
         * @param identifier part of emoji that is replaced with the emoji
         */
        public void setIdentifier(final String identifier) {
            this.identifier = identifier;

            if (saveEmoji) {
                plugin.getConfig().set("Emojis."+id+".identifier", identifier);
                plugin.getConfig().save();
            }
        }

        /**
         * Changes emoji - emojis content / what is shown when player types its identifier
         * @param emoji emojis content / what is shown when player types its identifier
         */
        public void setEmoji(final String emoji) {
            this.emoji = emoji;

            if (saveEmoji) {
                plugin.getConfig().set("Emojis."+id+".emoji", emoji);
                plugin.getConfig().save();
            }
        }
    }

}
