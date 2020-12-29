package com.github.maximiluss.sql.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.bukkit.Bukkit;

import com.github.maximiluss.sql.builder.utils.Condition;
import com.github.maximiluss.sql.builder.utils.Constraint;
import com.github.maximiluss.sql.builder.utils.RequetteType;

public class SqlRequetteContain extends SqlRequette {

	public SqlRequetteContain(Connection c, String table, List<String> args) {
		super(c, RequetteType.UPDATE, table, args, null, null, null);
	}

	public SqlRequetteContain(Connection c, String table, List<String> args, Condition condition, List<Object> values) {
		super(c, RequetteType.UPDATE, table, args, condition, values, null);
	}

	public SqlRequetteContain(Connection c, String table, List<String> args, Condition condition, List<Object> values,
			Constraint constraint) {
		super(c, RequetteType.UPDATE, table, args, condition, values, constraint);
	}

	@Override
	public Object send() {
		super.send();
		StringBuilder requette = new StringBuilder("SELECT ");

		for (String arg : args) {
			requette.append(arg);
			if (!(args.indexOf(arg) == args.size() - 1)) {
				requette.append(",");
			}
		}

		requette.append(" FROM " + table);
		if (condition != null) {
			requette.append(" WHERE ");
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
			ResultSet resultat = q.executeQuery();
			boolean hasAccount = resultat.next();
			return hasAccount;
		} catch (SQLException e) {
			e.printStackTrace();
			Bukkit.broadcastMessage(e.getMessage());
		}
		return false;
	}

}
