package fr.iondev.qcore.commands.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.commands.utils.AbstractCommand;
import fr.iondev.qcore.config.Setupable;

public class CommandManager extends Setupable implements CommandExecutor {

	private QCore plugin;

	private List<AbstractCommand> commands = new ArrayList<>();
	private String argumentError, syntaxError, permissionError, consolNoUseError, delayError;

	public CommandManager(QCore plugin) {
		super(plugin);
		this.plugin = plugin;
	}

	@Override
	public void setup(FileConfiguration config) {
		argumentError = config.getString("commands.argumentError");
		syntaxError = config.getString("commands.syntaxError");
		permissionError = config.getString("commands.permissionError");
		consolNoUseError = config.getString("commands.consolNoUseError");
		delayError = config.getString("commands.delayError");
	}

	public List<AbstractCommand> getCommands() {
		return commands;
	}

	public List<AbstractCommand> getAllCommands() {
		List<AbstractCommand> coreCommands = new ArrayList<>();
		for (AbstractCommand primary : commands) {
			coreCommands.add(primary);
			coreCommands.addAll(primary.getCommandArgs());
		}
		return coreCommands;
	}

	public String getArgumentError() {
		return argumentError;
	}

	public String getSyntaxError() {
		return syntaxError;
	}

	public String getPermissionError() {
		return permissionError;
	}

	public String getConsolNoUseError() {
		return consolNoUseError;
	}

	public String getDelayError() {
		return delayError;
	}

	public void addCommand(AbstractCommand cmd) {
		this.commands.add(cmd);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String commandName = cmd.getName();
		for (AbstractCommand command : commands) {
			if (command.getCommands().contains(commandName)) {
				plugin.log("§6Command preProcess §e" + commandName + " §6By §e" + sender.getName());
				command.preProcessWithArgs(sender, 0, args);
				return true;
			}
		}

		return false;
	}
}
