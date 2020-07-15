package com.vomarek.data;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Configuration extends YamlConfiguration {
    private final String name;
    private final JavaPlugin plugin;

    public Configuration (final String name, final JavaPlugin plugin) {
        this.name = name;
        this.plugin = plugin;


        final File file = new File(plugin.getDataFolder(), getFileName());

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            super.load(file);


            final InputStream resource = plugin.getResource(getFileName());

            if (resource != null) {

                final YamlConfiguration defaults = YamlConfiguration.loadConfiguration(new InputStreamReader(resource));

                super.setDefaults(defaults);

            }

            if (super.getConfigurationSection("").getKeys(true).isEmpty()) {

                plugin.saveResource(getFileName(), true);

                super.load(file);
            }

        } catch (final IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }


    }

    public String getName () {
        return name;
    }

    public String getFileName() {
        return name+".yml";
    }

    public void reload() {

        final File file = new File(plugin.getDataFolder(), getFileName());

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }


            super.load(file);
        } catch (final IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void save() {
        final File file = new File(plugin.getDataFolder(), getFileName());

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            super.save(file);
            super.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void saveDefault() {

        final File file = new File(plugin.getDataFolder(), getFileName());

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            plugin.saveResource(getFileName(), true);

            super.load(file);

        } catch (final IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void copyDefaults(final Boolean force) {
        super.options.copyDefaults(force);
    }

}
