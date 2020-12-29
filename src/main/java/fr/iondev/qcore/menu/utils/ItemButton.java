package fr.iondev.qcore.menu.utils;

import org.bukkit.inventory.ItemStack;

import fr.iondev.qcore.menu.AbstractItem;
import fr.iondev.qcore.menu.event.ItemButtonClickEvent;

public abstract class ItemButton extends AbstractItem {

	public ItemButton(ItemStack item) {
		super(item);
	}

	public abstract void onClick(ItemButtonClickEvent event);

}
