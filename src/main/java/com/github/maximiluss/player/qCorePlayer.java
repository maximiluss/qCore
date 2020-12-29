package com.github.maximiluss.player;

import java.text.DecimalFormat;

import org.bukkit.entity.Player;

import com.github.maximiluss.player.utils.AbstractPlayer;

public class qCorePlayer extends AbstractPlayer {

	private int tuer;
	private int morts;
	private boolean firstJoin;

	public qCorePlayer(Player player, int tuer, int morts, boolean firstJoin) {
		super(player);
		this.tuer = tuer;
		this.morts = morts;
		this.firstJoin = firstJoin;
	}

	public boolean isFirstJoin() {
		return this.firstJoin;
	}

	public int getTuer() {
		return this.tuer;
	}

	public void killEvent() {
		tuer++;
	}

	public void resetTuer() {
		this.tuer = 0;
	}

	public int getMorts() {
		return this.morts;
	}

	public void deathEvent() {
		morts++;
	}

	public void resetMorts() {
		this.morts = 0;
	}

	public double calculRatio() {
		if (morts == 0 || tuer == 0) {
			return tuer;
		}
		return tuer / morts;
	}

	public double getRatio() {
		return Double.parseDouble(new DecimalFormat("0.00").format(calculRatio()));
	}

	public void sendMessage(String message) {
		player.sendMessage(message);
	}

	public void sendMessage(String... message) {
		player.sendMessage(message);
	}

}
