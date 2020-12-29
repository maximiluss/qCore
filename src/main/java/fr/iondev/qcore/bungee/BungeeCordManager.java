package fr.iondev.qcore.bungee;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.config.Setupable;

public class BungeeCordManager extends Setupable {

	private boolean isused;

	public BungeeCordManager(QCore plugin) {
		super(plugin);
	}

	@Override
	public void setup(FileConfiguration config) {
		isused = config.getBoolean("bungee.isUsed");
	}

	public void setupChanel() {
		if (isused) {
			plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
			plugin.log(plugin.getPrefix() + " §aBungeeCordManager Enabled");
		}
	}

	public void sendPlayerConnectionMessage(Player player, BungeeQueue queue) {
		if (!isused)
			return;

		ByteArrayDataOutput message = ByteStreams.newDataOutput();
		message.writeUTF("Connect");
		message.writeUTF(queue.getServerName());

		new BukkitRunnable() {

			@Override
			public void run() {
				send(player, message.toByteArray());

				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (queue.getPlayers().get(0).equals(player) && player.isOnline()) {
					Bukkit.broadcastMessage("§d[§5WyraahLobby§d] §7Le serveur Faction est indisponible");
					queue.clearPlayer();
				}

			}
		}.runTaskLater(plugin, 80);
	}

	private void send(Player player, byte[] m) {
		player.sendPluginMessage(plugin, "BungeeCord", m);
	}

}
