package com.platform.dataaccess.jdbc;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public final class ParameterizedBatchUpdate {

	private final JdbcTemplate jdbcTemplate;
	private final String sql;
	private final List<Object[]> allArgs;

	public ParameterizedBatchUpdate(String sql, List<Object[]> allArgs, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
		this.allArgs = allArgs;
	}

	public int[] getBatchUpdateCount() {
		return jdbcTemplate.batchUpdate(sql, allArgs);
	}
}
