package com.github.maximiluss.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.github.maximiluss.utils.EasyComponent;

public class PlayerChatFormatingEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Player recipient;
	private Player sender;
	private String format;
	private String message;
	private String chatMessage;
	private List<EasyComponent> components;

	public PlayerChatFormatingEvent(Player recipient, Player sender, String format, String message,
			String chatMessage) {
		this.recipient = recipient;
		this.sender = sender;
		this.format = format;
		this.message = message;
		this.chatMessage = chatMessage;
		components = new ArrayList<>();
	}

	public Player getRecipient() {
		return this.recipient;
	}

	public Player getSender() {
		return this.sender;
	}

	public String getMessage() {
		return this.message;
	}

	public String getFormat() {
		return this.format;
	}

	public String getChatMessage() {
		return this.chatMessage;
	}

	public List<EasyComponent> getComponent() {
		return this.components;
	}

	public void setComponent(EasyComponent... component) {
		for (EasyComponent cp : component) {
			this.components.add(cp);
		}
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
