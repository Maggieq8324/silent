package com.platform.dataaccess.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public final class ParameterizedUpdate {

	private final JdbcTemplate jdbcTemplate;
	private final String sql;
	private final List<Object> parameterList;

	public ParameterizedUpdate(String sql, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
		this.parameterList = new ArrayList<Object>();
	}

	public ParameterizedUpdate(String sql, List<Object> parameterList, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
		this.parameterList = parameterList;
	}

	public ParameterizedUpdate setParameter(Object... params) {
		for (Object value : params) {
			parameterList.add(value);
		}
		return this;
	}

	public int getUpdateCount() {
		return jdbcTemplate.update(sql, parameterList.toArray());
	}
}
