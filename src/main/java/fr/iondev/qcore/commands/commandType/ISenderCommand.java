package fr.iondev.qcore.commands.commandType;

import org.bukkit.command.CommandSender;

import fr.iondev.qcore.commands.utils.CommandStats;

public interface ISenderCommand {

	abstract CommandStats runCommand(CommandSender sender, String... args);

}
