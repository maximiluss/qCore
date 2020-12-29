package fr.iondev.qcore.runnable;

import fr.iondev.qcore.QCore;

public class QCoreRunnable {

	private QCore plugin;

	private SyncRunnable runnable;
	private AsyncRunnable asyncRunnable;

	public QCoreRunnable(QCore plugin) {
		this.plugin = plugin;
		runnable = new SyncRunnable(plugin);
		asyncRunnable = new AsyncRunnable(plugin);
	}

	public QCore getQCore() {
		return this.plugin;
	}

	public void addTickable(QRunnable tickable) {
		this.runnable.addTickable(tickable);
	}

	public void addAsyncTickable(QRunnable tickable) {
		this.asyncRunnable.addTickable(tickable);
	}

}
