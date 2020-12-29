package com.github.maximiluss.menu.utils;

import org.bukkit.inventory.ItemStack;

import com.github.maximiluss.menu.AbstractItem;
import com.github.maximiluss.menu.event.ItemButtonClickEvent;

public abstract class ItemButton extends AbstractItem {

	public ItemButton(ItemStack item) {
		super(item);
	}

	public abstract void onClick(ItemButtonClickEvent event);

}
