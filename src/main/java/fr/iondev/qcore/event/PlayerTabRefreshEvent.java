package fr.iondev.qcore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import fr.iondev.qcore.QCore;

public class PlayerTabRefreshEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Player player;

	public PlayerTabRefreshEvent(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setListName(String name) {
		player.setPlayerListName(name);
	}

	public void sendTablist(String head, String foot) {

		QCore.plugin.getNMSManager().sendTabListPacket(player, head, foot);
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
