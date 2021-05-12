package com.platform.dataaccess.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public final class NamedQuery {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final String sql;
	private final MapSqlParameterSource msps;

	public NamedQuery(String sql, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.sql = sql;
		this.msps = new MapSqlParameterSource();
	}

	public NamedQuery(String sql, MapSqlParameterSource msps, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.sql = sql;
		this.msps = msps;
	}

	public NamedQuery setNamedParameter(String paramName, Object value) {
		msps.addValue(paramName, value);
		return this;
	}

	public <T> T getEntity(Class<T> requiredType) {
		List<T> results = namedParameterJdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<T>(requiredType));
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		return results.iterator().next();
	}

	public <T> List<T> getEntityList(Class<T> requiredType) {
		return namedParameterJdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<T>(requiredType));
	}

	public Map<String, Object> getMap() {
		List<Map<String, Object>> results = namedParameterJdbcTemplate.query(sql, msps, new ColumnMapRowMapper());
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		return results.iterator().next();
	}

	public List<Map<String, Object>> getMapList() {
		return namedParameterJdbcTemplate.query(sql, msps, new ColumnMapRowMapper());
	}

	public <T> T getObject(Class<T> requiredType) {
		List<T> results = namedParameterJdbcTemplate.query(sql, msps, new SingleColumnRowMapper<T>(requiredType));
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		return results.iterator().next();
	}

	public <T> List<T> getObjectList(Class<T> requiredType) {
		return namedParameterJdbcTemplate.query(sql, msps, new SingleColumnRowMapper<T>(requiredType));
	}
}
