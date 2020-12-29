package com.github.maximiluss.sql.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.github.maximiluss.sql.builder.utils.RequetteType;

public class SqlRequetteDrop extends SqlRequette {

	public SqlRequetteDrop(Connection c, String table) {
		super(c, RequetteType.DROP, table, null, null, null, null);
	}

	@Override
	public Object send() {
		super.send();
		try {
			PreparedStatement q = c.prepareStatement("DROP TABLE " + table);
			q.execute();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
