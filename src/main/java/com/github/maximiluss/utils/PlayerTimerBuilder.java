package com.github.maximiluss.utils;

public class PlayerTimerBuilder extends TimerBuilder {

	private String player;

	public PlayerTimerBuilder(String player, Long current, Long delay) {
		super(current, delay);
		this.player = player;
	}

	public String getPlayer() {
		return this.player;
	}

}
