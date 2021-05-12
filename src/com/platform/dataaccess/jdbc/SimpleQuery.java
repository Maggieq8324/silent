package com.platform.dataaccess.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

public final class SimpleQuery {

	private final JdbcTemplate jdbcTemplate;
	private final String sql;

	public SimpleQuery(String sql, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
	}

	public <T> T getEntity(Class<T> requiredType) {
		List<T> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(requiredType));
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		return results.iterator().next();
	}

	public <T> List<T> getEntityList(Class<T> requiredType) {
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(requiredType));
	}

	public Map<String, Object> getMap() {
		List<Map<String, Object>> results = jdbcTemplate.query(sql, new ColumnMapRowMapper());
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		return results.iterator().next();
	}

	public List<Map<String, Object>> getMapList() {
		return jdbcTemplate.query(sql, new ColumnMapRowMapper());
	}

	public <T> T getObject(Class<T> requiredType) {
		List<T> results = jdbcTemplate.query(sql, new SingleColumnRowMapper<T>(requiredType));
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		return results.iterator().next();
	}

	public <T> List<T> getObjectList(Class<T> requiredType) {
		return jdbcTemplate.query(sql, new SingleColumnRowMapper<T>(requiredType));
	}
}
