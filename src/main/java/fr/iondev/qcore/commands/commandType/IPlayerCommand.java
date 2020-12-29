package fr.iondev.qcore.commands.commandType;

import org.bukkit.entity.Player;

import fr.iondev.qcore.commands.utils.CommandStats;

public interface IPlayerCommand {

	abstract CommandStats runCommand(Player player, String... args);

}
