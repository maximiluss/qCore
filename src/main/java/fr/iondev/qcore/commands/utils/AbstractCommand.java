package fr.iondev.qcore.commands.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.commands.QPlayerCommandTimer;
import fr.iondev.qcore.commands.commandType.ICommandTimer;
import fr.iondev.qcore.commands.commandType.IPlayerCommand;
import fr.iondev.qcore.commands.commandType.ISenderCommand;
import fr.iondev.qcore.commands.manager.CommandManager;
import fr.iondev.qcore.config.ConfigManager;
import fr.iondev.qcore.utils.TimerBuilder;

public abstract class AbstractCommand {

	protected QCore plugin = QCore.plugin;
	protected CommandManager cmdM;
	protected ConfigManager configM;

	private List<String> commands;
	private List<AbstractCommand> commandArgs;

	public AbstractCommand() {
		commands = new ArrayList<>();
		commandArgs = new ArrayList<>();
		cmdM = plugin.getCommandManager();
		configM = plugin.getConfigManager();
	}

	public List<String> getCommands() {
		return this.commands;
	}

	public void addCommand(String command) {
		this.commands.add(command);
	}

	public List<AbstractCommand> getCommandArgs() {
		return this.commandArgs;
	}

	public void addCommandArgs(AbstractCommand args) {
		this.commandArgs.add(args);
	}

	public void preProcessWithArgs(CommandSender sender, int place, String... args) {
		if (args.length > place) {
			for (AbstractCommand cmd : commandArgs) {
				if (cmd.getCommands().contains(args[place])) {
					cmd.preProcessWithArgs(sender, ++place, args);
					return;
				}
			}
		}
		preProcess(sender, args);
	}

	public void preProcess(CommandSender sender, String... args) {
		if (getPermission() != null && !sender.hasPermission(getPermission())) {
			sender.sendMessage(configM.convertCommandMessage(cmdM.getPermissionError(), "", ""));
			return;
		}

		CommandStats stats = null;
		if (this instanceof IPlayerCommand) {
			if (sender instanceof Player) {
				if (this instanceof ICommandTimer) {
					QPlayerCommandTimer command = (QPlayerCommandTimer) this;
					TimerBuilder t = command.getPlayerTimer((Player) sender);
					if (t != null && !t.hisFished()) {
						sender.sendMessage(
								configM.convertCommandMessage(cmdM.getDelayError(), "", t.getTimeToString()));
						return;
					}
				}
				stats = ((IPlayerCommand) this).runCommand((Player) sender, args);

			} else {
				sender.sendMessage(configM.convertCommandMessage(cmdM.getConsolNoUseError(), "", ""));
			}
		} else if (this instanceof ISenderCommand) {
			stats = ((ISenderCommand) this).runCommand(sender, args);
		}

		if (stats == CommandStats.SYNTAX_ERROR) {
			sender.sendMessage(configM.convertCommandMessage(cmdM.getSyntaxError(), this.getSyntax(), ""));
		} else if (stats == CommandStats.SUCCESS) {
			if (this instanceof QPlayerCommandTimer) {
				QPlayerCommandTimer command = (QPlayerCommandTimer) this;
				command.addPlayer((Player) sender);
			}
		}
	}

	public abstract String getPermission();

	public abstract String getSyntax();

	public abstract String getDescription();

}
