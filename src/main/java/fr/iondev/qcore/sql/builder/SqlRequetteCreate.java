package fr.iondev.qcore.sql.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import fr.iondev.qcore.sql.builder.utils.Constraint;
import fr.iondev.qcore.sql.builder.utils.RequetteType;

public class SqlRequetteCreate extends SqlRequette {

	public SqlRequetteCreate(Connection c, String table, List<String> args, List<Object> values,
			Constraint constraint) {
		super(c, RequetteType.CREATE, table, args, null, values, constraint);
	}

	public SqlRequetteCreate(Connection c, String table, List<String> args, List<Object> values) {
		super(c, RequetteType.CREATE, table, args, null, values, null);
	}

	@Override
	public Object send() {
		super.send();
		StringBuilder requette = new StringBuilder("CREATE TABLE IF NOT EXISTS " + table + "(");

		for (String arg : args) {
			requette.append(arg).append(" ").append(values.get(args.indexOf(arg)));
			if (args.indexOf(arg) != args.size() - 1) {
				requette.append(", ");
			}
		}

		if (constraint != null) {
			requette.append(", CONSTRAINT ").append(constraint.toString());
		}

		requette.append(")");

		logger.log(requette.toString());

		try {

			PreparedStatement q = c.prepareStatement(requette.toString());
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
