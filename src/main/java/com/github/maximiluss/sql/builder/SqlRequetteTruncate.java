package com.github.maximiluss.sql.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.github.maximiluss.sql.builder.utils.RequetteType;

public class SqlRequetteTruncate extends SqlRequette {

	public SqlRequetteTruncate(Connection c, String table) {
		super(c, RequetteType.TRUNCATE, table, null, null, null, null);
	}

	@Override
	public Object send() {
		super.send();
		try {
			PreparedStatement q = c.prepareStatement("TRUNCATE TABLE " + table);
			q.executeUpdate();
			q.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
