package com.github.maximiluss.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.maximiluss.config.Setupable;
import com.github.maximiluss.player.qCorePlayer;
import com.google.common.io.Files;

import net.md_5.bungee.api.ChatColor;

public abstract class IPlugin extends JavaPlugin {

	private FileConfiguration config;

	public FileConfiguration getConfig() {
		return this.config;
	}

	private String prefix;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		loadCustomConfig();

		try {
			prefix = config.getString("qcore.prefix").replace("&", "§");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// Custom Configuration force UTF-8 configs files
	public void loadCustomConfig() {
		config = new YamlConfiguration();
		File file = new File(getDataFolder() + File.separator + "config.yml");
		try {
			config.loadFromString(Files.toString(file, Charset.forName("UTF-8")));
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void reloadConfig() {
		saveDefaultConfig();
		loadCustomConfig();
	}

	// Default QCore prefix pour les messages in game
	public String getPrefix() {
		return this.prefix;
	}

	// Default QCore preifx pour les messages en consol
	public abstract String getConsolPrefix();

	public abstract qCorePlayer getCorePlayer(Player player);

	public abstract void registerSetupable(Setupable setupable);

	// Logger

	public abstract void log(Object o);

	public abstract void logWarn(Object o);

	public abstract void logErr(Object o);

	public void preEnable() {
		log(getConsolPrefix() + "Enabling " + ChatColor.YELLOW + getDescription().getVersion());
	}

}
