package com.github.maximiluss.commands.utils;

import com.github.maximiluss.commands.QPlayerCommandTimer;
import com.github.maximiluss.utils.PlayerTimerBuilder;

public class CommandDataTimer extends PlayerTimerBuilder {

	private QPlayerCommandTimer command;

	public CommandDataTimer(PlayerTimerBuilder timer, QPlayerCommandTimer command) {
		super(timer.getPlayer(), timer.getCurrent(), timer.getDelay());
		this.command = command;
	}

	public QPlayerCommandTimer getCommand() {
		return this.command;
	}

}
