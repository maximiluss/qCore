package fr.iondev.qcore.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.logger.Logger;
import fr.iondev.qcore.runnable.QRunnable;

public class EventWrapper implements Listener, QRunnable {

	private QCore plugin;
	private Logger logger = QCore.getQLogger();

	public EventWrapper(QCore plugin) {
		this.plugin = plugin;
	}

	public void logs() {
		logger.log(plugin.getPrefix() + " §ePlayerDamageByPlayerEvent §aWrapped");
		logger.log(plugin.getPrefix() + " §ePlayerKillPlayerEvent §aWrapped");
		logger.log(plugin.getPrefix() + " §ePlayerServerListPingerEvent §aWrapped");
		logger.log(plugin.getPrefix() + " §ePlayerTabCompletEvent §aWrapped");
		logger.log(plugin.getPrefix() + " §ePlayerTabRefreshEvent §aWrapped");
		logger.log(plugin.getPrefix() + " §ePlayerChatFormatingEvent §aWrapped");
	}

	public void call(Event e) {
		plugin.getServer().getPluginManager().callEvent(e);
	}

	@EventHandler
	public void onPlayerDamageByPlayerEvent(EntityDamageByEntityEvent event) {
		if (event.isCancelled())
			return;
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player target = (Player) event.getEntity();
			Player damager = (Player) event.getDamager();
			double damage = event.getDamage();
			call(new PlayerDamageByPlayerEvent(target, damager, damage));
		}
	}

	@EventHandler
	public void onPlayerKillPlayer(PlayerDeathEvent event) {
		Player target = event.getEntity();
		if (target.getKiller() != null && target instanceof Player) {
			call(new PlayerKillPlayerEvent(target, target.getKiller()));
		}
	}

	@Override
	public void ticks() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			call(new PlayerTabRefreshEvent(player));
		}
	}
}
