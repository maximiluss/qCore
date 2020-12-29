package fr.iondev.qcore.runnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.logger.Levels;
import fr.iondev.qcore.logger.Logger;

public class SyncRunnable extends BukkitRunnable {

	private QCore plugin;

	public QCore getPlugin() {
		return this.plugin;
	}

	private Logger logger = QCore.getQLogger();

	private List<QRunnable> tickables = new ArrayList<>();

	public void addTickable(QRunnable tickable) {
		this.tickables.add(tickable);
	}

	public SyncRunnable(QCore plugin) {
		this.plugin = plugin;
		runTaskTimer(plugin, 0L, 20L);
	}

	@Override
	public void run() {
		for (QRunnable tickable : tickables) {
			try {
				tickable.ticks();
				// logger.log(tickable.getClass().getName() + " ticks()");
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Levels.ERR, e.getMessage());
			}
		}
	}

}
