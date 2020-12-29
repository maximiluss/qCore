package fr.iondev.qcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.iondev.qcore.commands.commandType.ICommandTimer;
import fr.iondev.qcore.utils.PlayerTimerBuilder;

public abstract class QPlayerCommandTimer extends QPlayerCommand implements ICommandTimer {

	private Long cmdTimer;

	public Long getCmdTimer() {
		return this.cmdTimer;
	}

	private List<PlayerTimerBuilder> timer = new ArrayList<>();

	public List<PlayerTimerBuilder> getTimer() {
		return timer;
	}

	public void setTimer(List<PlayerTimerBuilder> timer) {
		this.timer = timer;
	}

	public QPlayerCommandTimer(long cmdTimer) {
		this.cmdTimer = cmdTimer;
	}

	public void addPlayer(Player player) {
		if (getPlayerTimer(player) != null) {
			removePlayer(player);
		}

		timer.add(new PlayerTimerBuilder(player.getUniqueId().toString(), System.currentTimeMillis(), cmdTimer));
	}

	public void removePlayer(Player player) {
		for (int i = 0; i < timer.size(); i++) {
			PlayerTimerBuilder t = timer.get(i);
			if (t.getPlayer().equals(player.getUniqueId().toString()))
				timer.remove(t);
		}
	}

	public PlayerTimerBuilder getPlayerTimer(Player player) {
		for (PlayerTimerBuilder t : timer) {
			if (t.getPlayer().equals(player.getUniqueId().toString()))
				return t;
		}
		return null;
	}

}
