package fr.iondev.qcore.player.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.event.PlayerKillPlayerEvent;
import fr.iondev.qcore.logger.Logger;
import fr.iondev.qcore.player.data.PlayerData;
import fr.iondev.qcore.player.manager.PlayerManager;

public class PlayerListener implements Listener {

	private QCore plugin;

	public QCore getPlugin() {
		return this.plugin;
	}

	private Logger logger = QCore.getQLogger();

	// private BossBarAPI bossBarAPI;

	private PlayerManager playerM;
	private PlayerData playerD;

	public static String chatFormating = "default";

	public PlayerListener(QCore plugin, PlayerManager playerM, PlayerData playerD) {
		this.plugin = plugin;
		// this.bossBarAPI = plugin.getBossBarAPI();
		this.playerM = playerM;
		this.playerD = playerD;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		logger.log("§6Loading Player : §e" + player.getName());
		playerD.load(player);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		// if(bossBarAPI.getBossBar().have(player)) {
		// bossBarAPI.getBossBar().remove(player);
		// }
		logger.log("§6Save Player : §e" + player.getName());
		playerD.save(player);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		if (plugin.hasProtocolManager())
			event.setFormat("[chatMessage]" + event.getFormat());
		chatFormating = event.getFormat();

	}

	@EventHandler(priority = EventPriority.LOW)
	public void PlayerKillPlayerEvent(PlayerKillPlayerEvent event) {
		Player damager = event.getDamager();
		Player target = event.getTarget();
		if (playerM.containPlayer(damager))
			playerM.getCorePlayer(damager).killEvent();

		if (playerM.containPlayer(target))
			playerM.getCorePlayer(target).deathEvent();
	}

}
