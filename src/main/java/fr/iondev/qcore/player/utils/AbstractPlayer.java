package fr.iondev.qcore.player.utils;

import org.bukkit.entity.Player;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.nms.NMSManager;

public class AbstractPlayer {

	protected Player player;
	protected QCore plugin = QCore.plugin;
	private NMSManager nmsM;

	public AbstractPlayer(Player player) {
		this.player = player;
		nmsM = QCore.plugin.getNMSManager();
	}

	public Player getPlayer() {
		return this.player;
	}

	public int getPing() {
		if (nmsM == null)
			nmsM = QCore.plugin.getNMSManager();
		return nmsM.getPlayerPing(player);
	}

	public Player getLastDamager() {
		// EntityLiving lastDamager = entityPlayer.lastDamager;
		// if(lastDamager == null) return null;

		// if(lastDamager instanceof Player) {
		// System.out.println("test");
		// return (Player) lastDamager;
		// }
		return null;
	}

	public void sendTitle(String message) {
		nmsM.sendTitlePacket(player, message);
	}

	public void sendTitle(String message, int fadin, int delay, int fadout) {
		nmsM.sendTitlePacket(player, message);
		sendTimes(fadin, delay, fadout);
	}

	public void sendSubTitle(String message) {
		nmsM.sendSubTitlePacket(player, message);
	}

	public void sendSubTitle(String message, int fadin, int delay, int fadout) {
		nmsM.sendSubTitlePacket(player, message);
		sendTimes(fadin, delay, fadout);
	}

	public void sendTimes(int fadin, int delay, int fadout) {
		nmsM.sendTimePacket(player, fadin, delay, fadout);
	}

	public void clearTitle() {
		sendTitle(" ", 1, 1, 0);
	}

	public double getBalance() {
		return plugin.getEconomy().getBalance(player);
	}

	public void removeMoney(double somme) {
		plugin.getEconomy().withdrawPlayer(player, somme);
	}

	public void addMoney(double somme) {
		plugin.getEconomy().depositPlayer(player, somme);
	}

}
