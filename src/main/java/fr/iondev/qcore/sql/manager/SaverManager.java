package fr.iondev.qcore.sql.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.logger.Logger;
import fr.iondev.qcore.sql.Data;

public class SaverManager {

	private QCore plugin;
	private Logger logger = QCore.getQLogger();

	private List<Data> modules = new ArrayList<>();

	public SaverManager(QCore plugin) {
		this.plugin = plugin;
	}

	public void addModules(Data data) {
		if (!plugin.getUseSql()) {
			plugin.logErr(plugin.getConsolPrefix() + "Impossible d'utiliser le SaverManager");
		}
		modules.add(data);
	}

	public void save() {
		for (Data module : modules) {
			module.save();
			logger.log(ChatColor.YELLOW + module.getClass().getName() + ChatColor.GREEN + " saved");
		}
	}

	public QCore getPlugin() {
		return this.plugin;
	}

}
