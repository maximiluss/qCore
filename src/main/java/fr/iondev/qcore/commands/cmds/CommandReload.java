package fr.iondev.qcore.commands.cmds;

import org.bukkit.command.CommandSender;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.commands.QSenderCommand;
import fr.iondev.qcore.commands.utils.CommandStats;

public class CommandReload extends QSenderCommand {

	private QCore plugin;

	public CommandReload(QCore plugin) {
		this.plugin = plugin;
		addCommand("reload");
		addCommand("rl");
	}

	@Override
	public CommandStats runCommand(CommandSender player, String... args) {
		plugin.reloadConfig();
		plugin.getConfigManager().reloadConfig();
		player.sendMessage(plugin.getPrefix() + "§6Config Reload Sucess");
		return CommandStats.SUCCESS;
	}

	@Override
	public String getPermission() {
		return "qcore.reload";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
