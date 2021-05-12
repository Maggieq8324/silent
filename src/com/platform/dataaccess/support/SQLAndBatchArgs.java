package com.platform.dataaccess.support;

import java.util.List;

public class SQLAndBatchArgs {

	private final String sql;
	private final List<Object[]> args;

	public SQLAndBatchArgs(String sql, List<Object[]> args) {
		this.sql = sql;
		this.args = args;
	}

	public String getSql() {
		return sql;
	}

	public List<Object[]> getArgs() {
		return args;
	}

}
