package com.github.maximiluss.sql.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.github.maximiluss.QCore;
import com.github.maximiluss.logger.Logger;
import com.github.maximiluss.sql.builder.utils.Condition;
import com.github.maximiluss.sql.builder.utils.Constraint;
import com.github.maximiluss.sql.builder.utils.RequetteType;

public abstract class SqlRequette {

	protected Connection c;
	protected Logger logger = QCore.getQLogger();
	protected RequetteType type;
	protected String table;
	protected List<String> args;
	protected Condition condition;
	protected List<Object> values;
	protected Constraint constraint;

	public SqlRequette(Connection c, RequetteType type, String table, List<String> args, Condition condition,
			List<Object> values, Constraint constraint) {
		this.c = c;
		this.type = type;
		this.table = table;
		this.args = args;
		this.condition = condition;
		this.values = values;
		this.constraint = constraint;
	}

	public Object send() {
		if (!QCore.plugin.getSqlC().isConected()) {
			QCore.plugin.getSqlC().disconnect();
			QCore.plugin.getSqlC().connection();
			c = QCore.plugin.getConnection();
		}
		return null;
	}

	public Connection getC() {
		return this.c;
	}

	public RequetteType getType() {
		return this.type;
	}

	public String getTable() {
		return this.table;
	}

	public List<String> getArgs() {
		return this.args;
	}

	public Condition getCondition() {
		return this.condition;
	}

	public List<Object> getValues() {
		return this.values;
	}

	public void setCondition(Condition c) {
		this.condition = c;
	}

	public Constraint getConstrainte() {
		return this.constraint;
	}

	public void setConstraint(Constraint c) {
		this.constraint = c;
	}

	// Utils
	public void setTypedArgs(PreparedStatement q, Object o, int pos) throws SQLException {
		if (o.getClass().equals(Integer.class)) {
			q.setInt(pos, (int) o);
		} else if (o.getClass().equals(String.class)) {
			q.setString(pos, (String) o);
		} else if (o.getClass().equals(Double.class)) {
			q.setDouble(pos, (double) o);
		} else if (o.getClass().equals(Long.class)) {
			q.setLong(pos, (Long) o);
		}
	}
}
