package com.github.maximiluss.sql.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.github.maximiluss.sql.builder.utils.Condition;
import com.github.maximiluss.sql.builder.utils.Constraint;
import com.github.maximiluss.sql.builder.utils.RequetteType;

public class SqlRequetteDelete extends SqlRequette {

	public SqlRequetteDelete(Connection c, String table, List<String> args, Condition condition, List<Object> values,
			Constraint constraint) {
		super(c, RequetteType.DELETE, table, args, condition, values, constraint);
	}

	public SqlRequetteDelete(Connection c, String table, Condition condition, List<Object> values) {
		super(c, RequetteType.DELETE, table, null, condition, values, null);
	}

	@Override
	public Object send() {
		super.send();
		StringBuilder requette = new StringBuilder("DELETE FROM " + table);

		if (condition != null) {
			requette.append(" WHERE ");
			requette.append(condition.toString());
		}

		logger.log(requette.toString());

		try {
			PreparedStatement q = c.prepareStatement("DELETE FROM commandsStorage WHERE command = ? and uuid = ?");
			int i = 1;
			for (Object o : values) {
				setTypedArgs(q, o, i);
				i++;
			}
			q.executeUpdate();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
