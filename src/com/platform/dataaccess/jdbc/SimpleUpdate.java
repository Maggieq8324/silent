package com.platform.dataaccess.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

public final class SimpleUpdate {

	private final JdbcTemplate jdbcTemplate;
	private final String sql;

	public SimpleUpdate(String sql, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
	}

	public int getUpdateCount() {
		return jdbcTemplate.update(sql);
	}
}
