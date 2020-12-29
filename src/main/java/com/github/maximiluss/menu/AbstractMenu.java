package com.github.maximiluss.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.maximiluss.QCore;
import com.github.maximiluss.menu.utils.ItemButton;

public abstract class AbstractMenu {

	private int id;
	private String name;
	private int invSize;
	private int pageNbr;
	private Map<Integer, AbstractItem> itemInventory;

	public AbstractMenu(int id, String name, int invSize, int pageNbr, Map<Integer, AbstractItem> itemInventory) {
		this.id = id;
		this.name = name;
		this.invSize = invSize;
		this.pageNbr = pageNbr;
		this.itemInventory = itemInventory;
		QCore.plugin.addSubMenu(this);
	}

	public AbstractMenu(int id, String name, int invSize) {
		this.id = id;
		this.name = name;
		this.invSize = invSize;
		this.pageNbr = 1;
		this.itemInventory = new HashMap<>();
		QCore.plugin.addSubMenu(this);
	}

	public int getId() {
		return this.id;
	}

	public void addItem(int place, AbstractItem item) {
		itemInventory.put(place, item);
	}

	public void removeItem(int place) {
		itemInventory.remove(place);
	}

	public void removeItem(ItemStack item) {
		for (Entry<Integer, AbstractItem> entry : itemInventory.entrySet()) {
			if (entry.getValue().getItem().equals(item))
				itemInventory.remove(entry.getKey());
		}
	}

	public boolean hisItemButton(int slot) {
		if (itemInventory.get(slot) instanceof ItemButton)
			return true;

		return false;
	}

	public ItemButton getItemButton(int slot) {

		return (ItemButton) itemInventory.get(slot);
	}

	public Inventory toInventory() {
		Inventory inv = Bukkit.createInventory(null, invSize, name);
		for (Entry<Integer, AbstractItem> entry : itemInventory.entrySet()) {
			inv.setItem(entry.getKey(), entry.getValue().getItem());
		}
		return inv;
	}

	public String getName() {
		return this.name;
	}

	public int getPageNbr() {
		return this.pageNbr;
	}

	public abstract void onClick(InventoryClickEvent e);

	public abstract void onClose(InventoryCloseEvent e);

	public abstract void onDrag(InventoryDragEvent e);

}
