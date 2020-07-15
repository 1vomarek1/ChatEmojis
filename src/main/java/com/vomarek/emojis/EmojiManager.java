package com.vomarek.emojis;

import com.vomarek.ChatEmojis;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class EmojiManager {
    protected final ChatEmojis plugin;

    private final HashMap<String, Emoji> emojis = new HashMap<>();

    /**
     *
     * @see ChatEmojis#getEmojiManager()
     */
    public EmojiManager (@NotNull final ChatEmojis plugin) {
        this.plugin = plugin;

        final ConfigurationSection section = plugin.getConfig().getConfigurationSection("Emojis");

        for (final String id : section.getKeys(false)) {

            createEmoji(id, section.getString(id+".identifier", ""), section.getString(id+".emoji", ""));

        }

    }

    public HashMap<String, Emoji> getEmojis() {
        return emojis;
    }

    @Nullable
    public Emoji createEmoji (@NotNull final String id, @NotNull final String identifier, @NotNull final String emoji) {
        return createEmoji(id, identifier, emoji, true);
    }

    @Nullable
    public Emoji createEmoji (@NotNull final String id, @NotNull final String identifier, @NotNull final String emoji, @NotNull final Boolean saveEmoji) {
        if (emojis.containsKey(id)) return null;

        final Emoji emojiObj = new Emoji(id, identifier, emoji, saveEmoji);

        emojis.put(id, emojiObj);

        return emojiObj;
    }

    @Nullable
    public Emoji getEmojiById(@NotNull final String id) {
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

        @NotNull
        public String getId () {
            return id;
        }

        @NotNull
        public String getIdentifier () {
            return identifier;
        }

        @NotNull
        public String getEmoji () {
            return emoji;
        }

        @NotNull
        public Boolean getSaveEmoji() {
            return saveEmoji;
        }

        public void setIdentifier(@NotNull final String identifier) {
            this.identifier = identifier;

            if (saveEmoji) {
                plugin.getConfig().set("Emojis."+id+".identifier", identifier);
                plugin.getConfig().save();
            }
        }

        public void setEmoji(@NotNull final String emoji) {
            this.emoji = emoji;

            if (saveEmoji) {
                plugin.getConfig().set("Emojis."+id+".emoji", emoji);
                plugin.getConfig().save();
            }
        }
    }

}
