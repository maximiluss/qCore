package fr.iondev.qcore.sql.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import fr.iondev.qcore.logger.Levels;
import fr.iondev.qcore.sql.builder.utils.Condition;
import fr.iondev.qcore.sql.builder.utils.RequetteType;

public class SqlRequetteInsert extends SqlRequette {

	public SqlRequetteInsert(Connection c, String table, List<String> args, Condition condition, List<Object> values) {
		super(c, RequetteType.INSERT, table, args, condition, values, null);
	}

	public SqlRequetteInsert(Connection c, String table, List<String> args, List<Object> values) {
		super(c, RequetteType.INSERT, table, args, null, values, null);
	}

	@Override
	public Object send() {
		super.send();
		StringBuilder requette = new StringBuilder("INSERT INTO " + table + "(");

		for (String arg : args) {
			requette.append(arg);
			if (!(args.indexOf(arg) == args.size() - 1)) {
				requette.append(",");
			}
		}
		requette.append(") VALUES(");

		for (int i = 0; i < args.size(); i++) {
			requette.append("?");
			if (!(i == args.size() - 1)) {
				requette.append(",");
			} else {
				requette.append(")");
			}
		}

		logger.log(requette.toString());

		try {
			PreparedStatement q = c.prepareStatement(requette.toString());
			int i = 1;
			for (Object o : values) {
				setTypedArgs(q, o, i);
				i++;
			}
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
			for (String a : args) {
				logger.log(Levels.ERR, a);
			}
			for (Object a : values) {
				logger.log(Levels.ERR, a.toString());
			}

		}
		return null;
	}

}
