package com.github.maximiluss.sql.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.Bukkit;

import com.github.maximiluss.sql.builder.utils.Condition;
import com.github.maximiluss.sql.builder.utils.RequetteType;

public class SqlRequetteUpdate extends SqlRequette {

	public SqlRequetteUpdate(Connection c, String table, List<String> args, Condition condition, List<Object> values) {
		super(c, RequetteType.UPDATE, table, args, condition, values, null);
	}

	public SqlRequetteUpdate(Connection c, String table, List<String> args, List<Object> values) {
		super(c, RequetteType.UPDATE, table, args, null, values, null);
	}

	@Override
	public Object send() {
		super.send();
		StringBuilder requette = new StringBuilder("UPDATE " + table);

		requette.append(" SET ");

		for (String arg : args) {
			requette.append(arg).append(" = ?");
			if (args.indexOf(arg) != args.size() - 1) {
				requette.append(", ");
			}
		}

		requette.append(" WHERE ");

		if (condition != null) {
			requette.append(condition.toString());
		}

		logger.log(requette.toString());

		try {
			PreparedStatement q = c.prepareStatement(requette.toString());
			int i = 1;
			for (Object o : values) {
				setTypedArgs(q, o, i);
				i++;
			}
			q.executeUpdate();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(e.getMessage());
		}
		return null;
	}

}
