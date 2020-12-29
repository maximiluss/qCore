package com.github.maximiluss.logger;

import com.github.maximiluss.QCore;

public class Logger {

	// Class logger Beta 0.1
	// By azefgh_456

	// Field
	private QCore plugin;
	private Levels level;

	// Constructeur
	public Logger(QCore plugin, Levels level) {
		this.plugin = plugin;
		this.level = level;
	}

	public Logger(QCore plugin) {
		this.plugin = plugin;
		this.level = Levels.INFO;
	}

	// Getter
	public QCore getPlugin() {
		return this.plugin;
	}

	public Levels getLevel() {
		return this.level;
	}

	// Seter
	public void setLevel(Levels level) {
		this.level = level;
	}

	// Methode de class
	public void log(Levels level, String... logs) {
		for (String message : logs) {
			log(level.getDesc() + " " + message);
		}
	}

	public void log(String... logs) {
		for (String message : logs) {
			log(level.getDesc() + " " + message);
		}
	}

	private void log(String message) {
		plugin.getServer().getConsoleSender().sendMessage(message);
	}

}
