package fr.iondev.qcore.commands.cmds;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.iondev.qcore.commands.QPlayerCommand;
import fr.iondev.qcore.commands.utils.CommandStats;
import fr.iondev.qcore.utils.ItemSerializer;

public class sr extends QPlayerCommand {

	public sr() {
		addCommand("sr");
	}

	@Override
	public CommandStats runCommand(Player player, String... args) {

		String test = ItemSerializer.serialize(player.getItemInHand());
		player.sendMessage(test);
		ItemStack it = ItemSerializer.deserialize(test);
		player.getInventory().addItem(it);
		return CommandStats.SUCCESS;
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return null;
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
