package fr.iondev.qcore.commands.utils;

import fr.iondev.qcore.commands.QPlayerCommandTimer;
import fr.iondev.qcore.utils.PlayerTimerBuilder;

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
