package com.github.maximiluss.player.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.maximiluss.QCore;
import com.github.maximiluss.player.qCorePlayer;

public class PlayerManager {

	@SuppressWarnings("unused")
	private QCore plugin;
	private List<qCorePlayer> qCorePlayer;

	public PlayerManager(QCore plugin) {
		this.plugin = plugin;
		qCorePlayer = new ArrayList<>();
	}

	public void initPlayer(Player player) {
		loadPlayer(player, 0, 0, true);
	}

	public void loadPlayer(Player player, int tuer, int morts, boolean firstJoin) {
		qCorePlayer.add(new qCorePlayer(player, tuer, morts, firstJoin));
	}

	public boolean containPlayer(Player player) {
		for (qCorePlayer cP : qCorePlayer) {
			if (cP.getPlayer().equals(player))
				return true;
		}
		return false;
	}

	public qCorePlayer getCorePlayer(Player player) {
		for (qCorePlayer cP : qCorePlayer) {
			if (cP.getPlayer().equals(player))
				return cP;
		}
		return null;
	}

	public void removePlayer(qCorePlayer cP) {
		qCorePlayer.remove(cP);
	}

	public List<qCorePlayer> getCorePlayers() {
		return qCorePlayer;
	}

}
