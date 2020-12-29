package com.github.maximiluss.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.github.maximiluss.QCore;

public class MotdListener implements Listener {

	private QCore plugin;
	private List<String> motd;
	private int maxPlayer = 100;

	public MotdListener(QCore plugin) {
		this.plugin = plugin;
		motd = new ArrayList<>();
	}

	@EventHandler
	public void motdEvent(ServerListPingEvent event) {
		event.setMaxPlayers(maxPlayer);
		StringBuilder motdBuilder = new StringBuilder("");
		for (String ligne : motd) {
			motdBuilder.append(ligne).append("\n");
		}
		event.setMotd(motdBuilder.toString());
	}

	public void setMotd(List<String> motd) {
		this.motd = motd;
	}

	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
	}

	public QCore getPlugin() {
		return this.plugin;
	}

}
