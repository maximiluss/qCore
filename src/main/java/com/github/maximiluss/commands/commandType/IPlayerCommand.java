package com.github.maximiluss.commands.commandType;

import org.bukkit.entity.Player;

import com.github.maximiluss.commands.utils.CommandStats;

public interface IPlayerCommand {

	abstract CommandStats runCommand(Player player, String... args);

}
