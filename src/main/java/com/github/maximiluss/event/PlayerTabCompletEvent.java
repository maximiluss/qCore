package com.github.maximiluss.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerTabCompletEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private String message;
	private boolean cancel;

	public PlayerTabCompletEvent(Player player, String message) {
		this.player = player;
		this.message = message;
		cancel = false;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getMessage() {
		return this.message;
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
