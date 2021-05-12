package com.platform.dataaccess.jdbc;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public final class NamedUpdate {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final String sql;
	private final MapSqlParameterSource msps;

	public NamedUpdate(String sql, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.sql = sql;
		this.msps = new MapSqlParameterSource();
	}

	public NamedUpdate(String sql, MapSqlParameterSource msps, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.sql = sql;
		this.msps = msps;
	}

	public NamedUpdate setNamedParameter(String paramName, Object value) {
		msps.addValue(paramName, value);
		return this;
	}

	public int getUpdateCount() {
		return namedParameterJdbcTemplate.update(sql, msps);
	}
}
