package com.github.maximiluss.bungee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.maximiluss.player.qCorePlayer;
import com.github.maximiluss.plugin.QPlugin;
import com.github.maximiluss.runnable.QRunnable;

import net.md_5.bungee.api.ChatColor;

public class BungeeQueue implements QRunnable, Listener {

	private QPlugin plugin;

	private String serverName;

	public String getServerName() {
		return this.serverName;
	}

	private List<Player> players;

	public BungeeQueue(QPlugin plugin, String serverName) {
		plugin.registerRunnable(this);
		plugin.registerListener(this);
		this.plugin = plugin;
		this.serverName = serverName;
		players = new ArrayList<>();
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public Player getPlayer() {
		return this.players.get(0);
	}

	public void removePlayer(Player player) {
		qCorePlayer cp = plugin.getCorePlayer(player);
		cp.clearTitle();
		this.players.remove(player);
	}

	public void addPlayer(Player player) {
		if (players.contains(player)) {
			sendPlayerPosition(player);
		} else {
			players.add(player);
			System.out.println(players.size() + " " + players.indexOf(player));
			sendPlayerPosition(player);
		}
	}

	public void sendPlayerPosition(Player player) {
		qCorePlayer cp = plugin.getCorePlayer(player);
		int position = players.indexOf(player) + 1;
		cp.sendTitle(ChatColor.YELLOW + "Position " + ChatColor.GRAY + "#" + ChatColor.GOLD + position, 10, 20, 10);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (!players.contains(player))
			return;

		int place = players.indexOf(player);
		players.remove(player);

		if (++place <= 10) {

			int tour = players.size();

			if (tour >= 10)
				tour = 10;

			for (int i = 0; i < tour; i++) {
				sendPlayerPosition(players.get(i));
			}
		}
	}

	@Override
	public void ticks() {
		Iterator<Player> player = players.iterator();
		while (player.hasNext()) {
			Player p = player.next();
			int pos = players.indexOf(p);
			if (pos == 0) {
				plugin.connectPlayer(p, this);
			}
		}
	}

	public void clearPlayer() {
		players.clear();
	}
}
