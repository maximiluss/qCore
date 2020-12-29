package com.github.maximiluss.sql.builder.utils;

import java.util.List;

public class Condition {

	private List<String> args;
	private List<Separator> separator;

	public Condition(List<String> args, List<Separator> separator) {
		this.args = args;
		this.separator = separator;
	}

	public String toString() {

		StringBuilder str = new StringBuilder("");

		for (String arg : args) {
			str.append(arg).append(" = ? ");
			if (args.indexOf(arg) != args.size() - 1) {
				str.append(separator.get(args.indexOf(arg))).append(" ");
			}
		}
		return str.toString();
	}

}
