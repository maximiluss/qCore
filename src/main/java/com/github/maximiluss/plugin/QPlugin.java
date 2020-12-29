package com.github.maximiluss.plugin;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import com.comphenix.protocol.PacketType.Sender;
import com.github.maximiluss.QCore;
import com.github.maximiluss.bungee.BungeeQueue;
import com.github.maximiluss.commands.utils.AbstractCommand;
import com.github.maximiluss.config.Setupable;
import com.github.maximiluss.mcp.packets.APacket;
import com.github.maximiluss.mcp.packets.server.SPacket;
import com.github.maximiluss.menu.AbstractMenu;
import com.github.maximiluss.player.qCorePlayer;
import com.github.maximiluss.runnable.QRunnable;
import com.github.maximiluss.sql.Data;

import net.milkbowl.vault.economy.Economy;

@SuppressWarnings("deprecation")
public abstract class QPlugin extends IPlugin {

	protected QCore plugin;

	@Override
	public void onEnable() {
		super.onEnable();
		this.plugin = QCore.plugin;
	}

	/*
	 * 
	 * qCorePlayer Utils
	 * 
	 */

	public qCorePlayer getCorePlayer(Player player) {
		return plugin.getCorePlayer(player);
	}

	public List<qCorePlayer> getCorePlayers() {
		return plugin.getPlayerManager().getCorePlayers();
	}

	/*
	 * 
	 * Vault Eco Hook qCorePlayer have a hook of vault (target player !online!)
	 * 
	 */

	public Economy getEco() {
		return plugin.getEconomy();
	}

	public double getPlayerBalance(String playerName) {
		return getEco().getBalance(playerName);
	}

	public void addMoneyToPlayer(String playerName, double balance) {
		getEco().depositPlayer(playerName, balance);
	}

	public void removeMoneyToPlayer(String playerName, double balance) {
		getEco().withdrawPlayer(playerName, balance);
	}

	/*
	 * 
	 * PacketCustom suport MCP 1.8.8
	 * 
	 */

	public void registerPacket(Class<? extends APacket> packetClass, int packetId, Sender sender) {
		plugin.getPacketsManager().registerPacket(packetClass, packetId, sender);
	}

	public void sendPacket(SPacket packet, Player player) {
		plugin.getPacketsManager().sendPacket(player, packet);
	}

	public void sendPacket(SPacket packet, Player... player) {
		for (Player p : player) {
			plugin.getPacketsManager().sendPacket(p, packet);
		}
	}

	public void sendPacket(SPacket packet, List<Player> player) {
		for (Player p : player) {
			plugin.getPacketsManager().sendPacket(p, packet);
		}
	}

	public void sendPacket(SPacket packet, Collection<? extends Player> player) {
		for (Player p : player) {
			plugin.getPacketsManager().sendPacket(p, packet);
		}
	}

	/*
	 * 
	 * Register somme class to the QCore (Listener, AbstractCommand, Data,
	 * QRunnable, Setupable, AbstractMenu)
	 * 
	 */

	public void registerData(Data data) {
		plugin.addSubData(data);
	}

	public void registerListener(Listener listener) {
		plugin.addSubListener(listener);
	}

	public void registerCommand(String name, AbstractCommand cmd) {
		plugin.addSubCommand(this, name, cmd);
	}

	public void registerRunnable(QRunnable runnable) {
		plugin.addSubRunnable(runnable);
	}

	public void registerAsyncRunnable(QRunnable runnable) {
		plugin.addSubAsyncRunnable(runnable);
	}

	public void registerSetupable(Setupable setupable) {
		plugin.registerSetupable(setupable);
	}

	public void registerMenu(AbstractMenu menu) {
		plugin.addSubMenu(menu);
	}

	/*
	 * 
	 * Menu Utils
	 * 
	 */

	public AbstractMenu getMenu(String name) {
		return plugin.getMenuManager().getMenu(name);
	}

	public AbstractMenu getMenu(int id) {
		return plugin.getMenuManager().getMenu(id);
	}

	public void openMenu(Player player, String name) {
		openMenu(player, getMenu(name));
	}

	public void openMenu(Player player, AbstractMenu menu) {
		player.openInventory(menu.toInventory());
	}

	/*
	 * 
	 * Data SQL Utils
	 * 
	 */

	public Connection getConnection() {
		return plugin.getConnection();
	}

	/*
	 * 
	 * Spigot event dispatcher
	 * 
	 */

	public void dispatchEvent(Event event) {
		plugin.getEventWrapper().call(event);
	}

	/*
	 * 
	 * BungeeCord Utils
	 * 
	 */

	public void connectPlayer(Player player, BungeeQueue queue) {
		plugin.getBungeeCordManager().sendPlayerConnectionMessage(player, queue);
	}

	/*
	 * 
	 * CoreLogger Utils
	 * 
	 */

	@Override
	public void log(Object o) {
		plugin.log(o);
	}

	@Override
	public void logWarn(Object o) {
		plugin.logWarn(o);
	}

	@Override
	public void logErr(Object o) {
		plugin.logErr(o);
	}

	// Instance of QCore
	public QCore getPlugin() {
		return this.plugin;
	}

	// Plugin prefix
	public abstract String getPrefix();

}
