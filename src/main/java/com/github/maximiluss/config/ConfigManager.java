package com.github.maximiluss.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.github.maximiluss.QCore;
import com.github.maximiluss.plugin.IPlugin;

public class ConfigManager {

	private QCore plugin;

	private List<IPlugin> update;

	private List<Setupable> configurable;

	public ConfigManager(QCore plugin) {
		this.plugin = plugin;
		configurable = new ArrayList<>();
		update = new ArrayList<>();
	}

	public void reloadConfig() {

		for (IPlugin plugin : update) {
			plugin.reloadConfig();
			System.out.println(plugin.getName());
		}

		for (Setupable module : configurable) {
			System.out.println(module.getClass().getName());
			module.setup(module.getPlugin().getConfig());
		}
	}

	public void addConfigurable(Setupable module) {
		if (module == null) {
			System.out.println("module null");
		}

		if (module.getPlugin() == null) {
			System.out.println("plugin module == null");
		}
		if (!containIPlugin(module.getPlugin())) {
			update.add(module.getPlugin());
		}

		configurable.add(module);
	}

	private boolean containIPlugin(IPlugin plugin) {
		for (IPlugin pl : update) {
			if (pl.getName().equals(plugin.getName()))
				return true;
		}
		return false;
	}

	public String convertCommandMessage(String message, String SYNTAXE, String DELAY) {
		message = convertMessage(message).replace("{PREFIX}", plugin.getPrefix()).replace("{SYNTAXE}", SYNTAXE)
				.replace("{DELAY}", DELAY);
		return message;
	}

	public String convertMessage(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

}
