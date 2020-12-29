package fr.iondev.qcore.sql.builder.utils;

import java.util.List;

public class Constraint {

	private ConstraintValues contrainte;
	private List<String> values;

	public Constraint(ConstraintValues contrainte, List<String> values) {
		this.contrainte = contrainte;
		this.values = values;
	}

	public String toString() {
		StringBuilder bd = new StringBuilder(contrainte.getValues() + "(");
		for (String v : values) {
			bd.append(v);
			if (values.indexOf(v) != values.size() - 1) {
				bd.append(", ");
			}
		}
		bd.append(")");
		return bd.toString();
	}

	public static enum ConstraintValues {

		PRIMARY("Primary Key"), FOREIGN("Foreign Key"), NOT_NULL("Not Null");

		private String values;

		ConstraintValues(String values) {
			this.values = values;
		}

		public String getValues() {
			return this.values;
		}

	}

}
