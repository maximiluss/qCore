package com.github.maximiluss.sql.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.github.maximiluss.sql.builder.utils.Condition;
import com.github.maximiluss.sql.builder.utils.RequetteType;
import com.github.maximiluss.utils.ObjectBuilder;

public class SqlRequetteSelect extends SqlRequette {

	public SqlRequetteSelect(Connection c, String table, List<String> args, Condition condition, List<Object> values) {
		super(c, RequetteType.SELECT, table, args, condition, values, null);
	}

	public SqlRequetteSelect(Connection c, String table, List<String> args) {
		super(c, RequetteType.SELECT, table, args, null, null, null);
	}

	@Override
	public Object send() {
		super.send();
		List<ObjectBuilder> listBuilder = new ArrayList<>();

		StringBuilder requette = new StringBuilder("SELECT ");

		for (String arg : args) {
			requette.append(arg);
			if (args.indexOf(arg) != args.size() - 1) {
				requette.append(", ");
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
			if (values != null) {
				for (Object o : values) {
					setTypedArgs(q, o, i);
					i++;
				}
			}
			ResultSet rs = q.executeQuery();
			while (rs.next()) {
				ObjectBuilder objectBuilder = new ObjectBuilder();
				for (String arg : args) {
					Object objrecup = rs.getObject(arg);
					objectBuilder.addObject(objrecup);
				}
				listBuilder.add(objectBuilder);
			}
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listBuilder;
	}

}
