package com.github.maximiluss.menu.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import com.github.maximiluss.QCore;
import com.github.maximiluss.logger.Logger;
import com.github.maximiluss.menu.AbstractMenu;
import com.github.maximiluss.menu.event.ItemButtonClickEvent;

public class MenuManager implements Listener {

	private QCore plugin;
	private Logger logger = QCore.getQLogger();

	private List<AbstractMenu> menuList;

	public MenuManager(QCore plugin) {
		this.plugin = plugin;
		menuList = new ArrayList<>();
	}

	public boolean containMenu(AbstractMenu menu) {
		for (AbstractMenu m : menuList) {
			if (m.getName().equals(menu.getName()))
				return true;
		}
		return false;
	}

	public boolean containMenu(String name) {
		for (AbstractMenu m : menuList) {
			if (m.getName().equals(name))
				return true;
		}
		return false;
	}

	public void addMenu(AbstractMenu menu) {
		if (containMenu(menu))
			menuList.remove(menu);

		menuList.add(menu);
	}

	public AbstractMenu getMenu(String name) {
		for (AbstractMenu m : menuList) {
			if (m.getName().equals(name))
				return m;
		}
		return null;
	}

	public AbstractMenu getMenu(int id) {
		for (AbstractMenu m : menuList) {
			if (m.getId() == id)
				return m;
		}
		return null;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		int slot = e.getSlot();
		String invName = e.getInventory().getName();
		if (containMenu(invName)) {
			AbstractMenu menu = getMenu(invName);
			if (menu.hisItemButton(slot)) {
				ItemButtonClickEvent buttonClickEvent = new ItemButtonClickEvent(e.getClick(),
						(Player) e.getWhoClicked(), e.getCurrentItem());
				menu.getItemButton(slot).onClick(buttonClickEvent);
				e.setCancelled(buttonClickEvent.isCancelled());
			} else {
				menu.onClick(e);
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		String invName = e.getInventory().getName();
		if (containMenu(invName)) {
			getMenu(invName).onClose(e);
		}
	}

	@EventHandler
	public void onDrag(InventoryDragEvent e) {
		String invName = e.getInventory().getName();
		if (containMenu(invName)) {
			getMenu(invName).onDrag(e);
		}
	}

	public QCore getQCore() {
		return this.plugin;
	}

	public Logger getLogger() {
		return this.logger;
	}

}
