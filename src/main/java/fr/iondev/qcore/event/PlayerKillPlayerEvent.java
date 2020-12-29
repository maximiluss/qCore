package fr.iondev.qcore.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKillPlayerEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Player target;
	private Player damager;

	public PlayerKillPlayerEvent(Player target, Player damager) {
		this.target = target;
		this.damager = damager;
	}

	public Player getTarget() {
		return this.target;
	}

	public Player getDamager() {
		return this.damager;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
