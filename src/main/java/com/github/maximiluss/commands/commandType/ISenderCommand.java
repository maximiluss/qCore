package com.github.maximiluss.commands.commandType;

import org.bukkit.command.CommandSender;

import com.github.maximiluss.commands.utils.CommandStats;

public interface ISenderCommand {

	abstract CommandStats runCommand(CommandSender sender, String... args);

}
