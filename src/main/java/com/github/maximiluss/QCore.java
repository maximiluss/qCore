package com.github.maximiluss;

import java.sql.Connection;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.maximiluss.bungee.BungeeCordManager;
import com.github.maximiluss.commands.cmds.CommandReload;
import com.github.maximiluss.commands.cmds.sr;
import com.github.maximiluss.commands.data.CommandData;
import com.github.maximiluss.commands.manager.CommandManager;
import com.github.maximiluss.commands.utils.AbstractCommand;
import com.github.maximiluss.config.ConfigManager;
import com.github.maximiluss.config.Setupable;
import com.github.maximiluss.event.EventProtocolLibWrapper;
import com.github.maximiluss.event.EventWrapper;
import com.github.maximiluss.listener.MotdListener;
import com.github.maximiluss.logger.Levels;
import com.github.maximiluss.logger.Logger;
import com.github.maximiluss.mcp.packets.manager.PacketsManager;
import com.github.maximiluss.menu.AbstractMenu;
import com.github.maximiluss.menu.manager.MenuManager;
import com.github.maximiluss.nms.NMSManager;
import com.github.maximiluss.player.qCorePlayer;
import com.github.maximiluss.player.data.PlayerData;
import com.github.maximiluss.player.listener.PlayerListener;
import com.github.maximiluss.player.manager.PlayerManager;
import com.github.maximiluss.plugin.IPlugin;
import com.github.maximiluss.runnable.QCoreRunnable;
import com.github.maximiluss.runnable.QRunnable;
import com.github.maximiluss.sql.Data;
import com.github.maximiluss.sql.SqlConnection;
import com.github.maximiluss.sql.manager.SaverManager;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class QCore extends IPlugin {

	public static QCore plugin;

	private static Logger logger;

	public static Logger getQLogger() {
		return logger;
	}

	private ConfigManager configManager;

	public ConfigManager getConfigManager() {
		return this.configManager;
	}

	private SqlConnection sqlC;
	private boolean useSql = false;

	public boolean getUseSql() {
		return this.useSql;
	}

	public void setUseSql(boolean useSql) {
		this.useSql = useSql;
	}

	public SqlConnection getSqlC() {
		return this.sqlC;
	}

	public Connection getConnection() {
		return this.sqlC.getConnection();
	}

	private SaverManager saverManager;

	public SaverManager getSaverManager() {
		return this.saverManager;
	}

	private Economy economy;

	public Economy getEconomy() {
		return this.economy;
	}

	private boolean useVault = false;

	public boolean getUseVault() {
		return this.useVault;
	}

	private CommandManager commandManager;

	public CommandManager getCommandManager() {
		return this.commandManager;
	}

	private CommandData commandData;

	public CommandData getCommandData() {
		return this.commandData;
	}

	private EventWrapper eventWrapper;

	public EventWrapper getEventWrapper() {
		return this.eventWrapper;
	}

	private EventProtocolLibWrapper eventProtocolLibWrapper;

	public EventProtocolLibWrapper getEventProtocolLibWrapper() {
		return this.eventProtocolLibWrapper;
	}

	private QCoreRunnable qCoreRunnable;

	public QCoreRunnable getCoreRunnable() {
		return this.qCoreRunnable;
	}

	private MenuManager menuManager;

	public MenuManager getMenuManager() {
		return this.menuManager;
	}

	private PlayerManager playerManager;

	public PlayerManager getPlayerManager() {
		return this.playerManager;
	}

	private PlayerData playerData;

	public PlayerData getPlayerData() {
		return this.playerData;
	}

	private NMSManager nmsManager;

	public NMSManager getNMSManager() {
		return this.nmsManager;
	}

	private ProtocolManager protocolManager;

	public ProtocolManager getProtocolManager() {
		return this.protocolManager;
	}

	private boolean hasProtocolManager = false;

	public boolean hasProtocolManager() {
		return this.hasProtocolManager;
	}

	private MotdListener motdListener;

	public MotdListener getMotdListener() {
		return this.motdListener;
	}

	private BungeeCordManager bungeeCordManager;

	public BungeeCordManager getBungeeCordManager() {
		return this.bungeeCordManager;
	}

	private PacketsManager packetsManager;

	public PacketsManager getPacketsManager() {
		return this.packetsManager;
	}

	@Override
	public void preEnable() {
		preEnable();
	}
	
	@Override
	public void onEnable() {
		// Instance QCore
		plugin = this;

		// Enabling the CoreLogger
		logger = new Logger(this);
		log(getConsolPrefix() + "Enabling " + ChatColor.DARK_PURPLE + getDescription().getVersion());

		// load config system
		super.onEnable();
		useSql = getConfig().getBoolean("qcore.sql");

		// Enabling ConfigFile
		configManager = new ConfigManager(this);
		log(getConsolPrefix() + "Config Enabled");

		// Enabling SqlStorage
		if (useSql) {
			sqlC = new SqlConnection(this);
		} else {
			logWarn(getConsolPrefix() + "Sql is not use");
		}

		// Enabling SaverManager
		if (useSql) {
			saverManager = new SaverManager(this);
			log(getConsolPrefix() + "SaverManager Enabled");
		}

		// Enabling Economy
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
			log(getConsolPrefix() + "Economy Hooked " + ChatColor.LIGHT_PURPLE + economy.getName());
		} else {
			logErr(getConsolPrefix() + ChatColor.RED + "Economy failed to hook !");

		}

		// Enabling MCP Packets
		packetsManager = new PacketsManager(this);
		log(getConsolPrefix() + "MCP Packet Support Enabled");

		// Enabling NMS Manager
		nmsManager = new NMSManager(this);
		log(getConsolPrefix() + "NMSManager Enabled");
		nmsManager.logsVersion();

		// Enabling CommandManager
		commandManager = new CommandManager(this);
		log(getConsolPrefix() + "CommandManager Enabled");
		addSubData(commandData = new CommandData(this));
		log(getConsolPrefix() + "CommandData Enabled");

		// Enabling QCoreRunnable
		qCoreRunnable = new QCoreRunnable(this);
		log(getConsolPrefix() + "QCoreRunnable Enabled");

		// Enabling MenuManager
		menuManager = new MenuManager(this);
		addSubListener(menuManager);
		log(getConsolPrefix() + "MenuManager Enabled");

		// Enabling ProtocolManager
		if (getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
			protocolManager = ProtocolLibrary.getProtocolManager();
			hasProtocolManager = true;
			log(getConsolPrefix() + "ProtocolManager Enabled");
		} else {
			logWarn("§6Softdepend §eProtocolLib §6reduction des fonctions du " + getConsolPrefix());
		}

		// Enabling EventWrapper || EnventProtocolLibWrapper
		eventWrapper = new EventWrapper(this);
		addSubListener(eventWrapper);
		addSubRunnable(eventWrapper);
		if (hasProtocolManager) {
			eventProtocolLibWrapper = new EventProtocolLibWrapper(this, eventWrapper);
			addSubListener(eventProtocolLibWrapper);
			eventProtocolLibWrapper.setupProtocolManager();
		}
		log(getConsolPrefix() + "EventWrapper Enabled");
		eventWrapper.logs();

		// Enabling PlayerManager
		playerManager = new PlayerManager(this);
		log(getConsolPrefix() + "PlayerManager Enabled");

		// Enabling PlayerData
		playerData = new PlayerData(sqlC.getConnection(), playerManager);
		addSubData(playerData);
		log(getConsolPrefix() + "PlayerData Enabled");

		// Enabling PlayerListener
		addSubListener(new PlayerListener(this, playerManager, playerData));
		log(getConsolPrefix() + "PlayerListener Enabled");

		bungeeCordManager = new BungeeCordManager(this);
		bungeeCordManager.setupChanel();

		// Enabling Basic Command
		addSubCommand(this, "reload", new CommandReload(this));
		log(getConsolPrefix() + "ConfigRelaod Enabled");
		addSubListener(motdListener = new MotdListener(this));
		addSubCommand(this, "sr", new sr());
		log(getConsolPrefix() + "Enabled");
	}

	@Override
	public void onDisable() {
		if (useSql) {
			saverManager.save();
			sqlC.disconnect();
		}

		log(getConsolPrefix() + "Disabled");
	}

	@Override
	public String getPrefix() {
		if (super.getPrefix() == null || super.getPrefix().isEmpty()) {
			return ChatColor.YELLOW + "q" + ChatColor.GOLD + "Core";
		}
		return super.getPrefix();
	}

	@Override
	public String getConsolPrefix() {
		return ChatColor.YELLOW + "q" + ChatColor.GOLD + "Core " + ChatColor.GREEN;
	}

	public void setServerDescritpion(int maxPlayer, List<String> motd) {
		motdListener.setMaxPlayer(maxPlayer);
		motdListener.setMotd(motd);
	}

	public void addSubCommand(JavaPlugin plugin, String name, AbstractCommand cmd) {
		plugin.getCommand(name).setExecutor(commandManager);
		cmd.addCommand(name);
		commandManager.addCommand(cmd);
	}

	public void addSubEvent(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	public void registerSetupable(Setupable module) {
		configManager.addConfigurable(module);
	}

	public void addSubData(Data data) {
		saverManager.addModules(data);
	}

	public void addSubRunnable(QRunnable tickable) {
		qCoreRunnable.addTickable(tickable);
	}

	public void addSubAsyncRunnable(QRunnable tickable) {
		qCoreRunnable.addAsyncTickable(tickable);
	}

	public void addSubListener(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	public void addSubMenu(AbstractMenu menu) {
		menuManager.addMenu(menu);
	}

	public qCorePlayer getCorePlayer(Player player) {
		return playerManager.getCorePlayer(player);
	}

	@Override
	public void log(Object o) {
		getQLogger().log((String) o);
	}

	@Override
	public void logWarn(Object o) {
		getQLogger().log(Levels.WARN, (String) o);
	}

	@Override
	public void logErr(Object o) {
		getQLogger().log(Levels.ERR, (String) o);
	}
}
