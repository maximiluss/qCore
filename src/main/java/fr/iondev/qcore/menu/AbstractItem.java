package fr.iondev.qcore.menu;

import org.bukkit.inventory.ItemStack;

public class AbstractItem {

	private ItemStack item;

	public AbstractItem(ItemStack item) {
		this.item = item;
	}

	public ItemStack getItem() {
		return this.item;
	}

}
