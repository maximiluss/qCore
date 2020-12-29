package fr.iondev.qcore.sql;

import java.sql.Connection;

import fr.iondev.qcore.QCore;
import fr.iondev.qcore.logger.Logger;

public abstract class Data {

	private QCore plugin;

	private Connection c;
	protected Logger logger = QCore.getQLogger();

	public Data() {
		plugin = QCore.plugin;
		c = plugin.getConnection();
		createTable();
	}

	public void setConnection() {
		c = plugin.getConnection();
	}

	public Connection getC() {
		return c;
	}

	protected abstract void createTable();

	protected abstract void load();

	public abstract void save();

	protected abstract <T> boolean contain(T val);

}
