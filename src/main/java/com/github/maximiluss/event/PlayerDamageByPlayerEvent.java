package com.github.maximiluss.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDamageByPlayerEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private Player target;
	private Player damager;
	private double damage;
	private boolean cancel = false;

	public PlayerDamageByPlayerEvent(Player target, Player damager, double damage) {
		this.target = target;
		this.damager = damager;
		this.damage = damage;
	}

	public Player getTarget() {
		return this.target;
	}

	public Player getDamager() {
		return this.damager;
	}

	public double getDamage() {
		return this.damage;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

}
