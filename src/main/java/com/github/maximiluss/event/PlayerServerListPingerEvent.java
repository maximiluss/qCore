package com.github.maximiluss.event;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerServerListPingerEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private String message;
	private int pingVersion;
	private String pingVersionName;

	public PlayerServerListPingerEvent(Player player, int pingVersion, String pingVersionName) {
		this.player = player;
		this.pingVersion = pingVersion;
		this.pingVersionName = pingVersionName;
		this.message = "";
	}

	public int getPingVersion() {
		return this.pingVersion;
	}

	public Player getPlayer() {
		return this.player;
	}

	public String getMessage() {
		return this.message;
	}

	public String getPingVersionName() {
		return this.pingVersionName;
	}

	public void setPingVersionName(String pingVersionName) {
		this.pingVersionName = pingVersionName;
	}

	public void setPingVersion(int pingVersion) {
		this.pingVersion = pingVersion;
	}

	public void setMessage(List<String> ligne) {
		StringBuilder messageBuilder = new StringBuilder("");
		for (String l : ligne) {
			messageBuilder.append(l).append("\n");
		}
		message = messageBuilder.toString();
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
