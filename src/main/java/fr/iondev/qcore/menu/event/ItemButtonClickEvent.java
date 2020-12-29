package fr.iondev.qcore.menu.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ItemButtonClickEvent implements Cancellable {

	private ClickType clickType;
	private Player player;
	private ItemStack item;
	private boolean cancel;

	public ItemButtonClickEvent(ClickType clickType, Player player, ItemStack item) {
		this.clickType = clickType;
		this.player = player;
		this.item = item;
		this.cancel = false;
	}

	public ClickType getClickType() {
		return this.clickType;
	}

	public Player getPlayer() {
		return this.player;
	}

	public ItemStack getMenu() {
		return this.item;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;

	}

}
