package com.github.maximiluss.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.maximiluss.QCore;
import com.github.maximiluss.config.Setupable;
import com.github.maximiluss.plugin.IPlugin;

public class SqlConnection extends Setupable {

	// private QCore plugin;
	private Connection connection;
	private String urlbase, host, database, user, pass;

	public SqlConnection(QCore plugin) {
		super(plugin);
	}

	@Override
	public void setup(FileConfiguration config) {
		this.urlbase = config.getString("sql.urlbase");
		this.host = config.getString("sql.host");
		this.database = config.getString("sql.database");
		this.user = config.getString("sql.user");
		this.pass = config.getString("sql.pass");

		connection = null;
		connection();

	}

	public void connection() {
		if (!isConected()) {
			try {
				connection = DriverManager.getConnection(urlbase + host + "/" + database + "?autoReconnect=true", user,
						pass);
				((QCore) plugin).setUseSql(true);
				plugin.log(plugin.getConsolPrefix() + "Conection reussit");
			} catch (SQLException e) {
				e.printStackTrace();
				((QCore) plugin).setUseSql(false);
				plugin.logErr("§c Connection BDD imposible");
			}
		}
	}

	public void disconnect() {
		if (isConected()) {
			try {
				connection.close();
				plugin.log(plugin.getPrefix() + " §aBDD Deconected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isConected() {
		return connection != null;
	}

	public Connection getConnection() {
		if (!isConected())
			connection();

		return this.connection;
	}

	@Override
	public IPlugin getPlugin() {
		return this.plugin;
	}

}
