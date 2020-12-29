package com.github.maximiluss.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.maximiluss.plugin.IPlugin;

public abstract class Setupable {

	protected IPlugin plugin;

	public Setupable(IPlugin plugin) {
		this.plugin = plugin;
		plugin.registerSetupable(this);
		setup(plugin.getConfig());
	}

	public String convertColor(String message) {
		if (message == null) {
			System.out.println("message null");
			return null;
		}
		System.out.println(message);
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public List<String> convertColor(List<String> data) {
		List<String> datamaped = new ArrayList<>();

		for (String d : data) {
			datamaped.add(convertColor(d));
		}
		return datamaped;
	}

	public abstract void setup(FileConfiguration config);

	public IPlugin getPlugin() {
		return this.plugin;
	}

}
