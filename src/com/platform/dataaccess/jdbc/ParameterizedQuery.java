package com.platform.dataaccess.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

public final class ParameterizedQuery {

	private final JdbcTemplate jdbcTemplate;
	private final String sql;
	private final List<Object> parameterList;

	public ParameterizedQuery(String sql, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
		this.parameterList = new ArrayList<Object>();
	}

	public ParameterizedQuery(String sql, List<Object> parameterList, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
		this.parameterList = parameterList;
	}

	public ParameterizedQuery setParameter(Object... params) {
		for (Object value : params) {
			parameterList.add(value);
		}
		return this;
	}

	public <T> T getEntity(Class<T> requiredType) {
		List<T> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(requiredType), parameterList.toArray());
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		return results.iterator().next();
	}

	public <T> List<T> getEntityList(Class<T> requiredType) {
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(requiredType), parameterList.toArray());
	}

	public Map<String, Object> getMap() {
		List<Map<String, Object>> results = jdbcTemplate.query(sql, new ColumnMapRowMapper(), parameterList.toArray());
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		return results.iterator().next();
	}

	public List<Map<String, Object>> getMapList() {
		return jdbcTemplate.query(sql, new ColumnMapRowMapper(), parameterList.toArray());
	}

	public <T> T getObject(Class<T> requiredType) {
		List<T> results = jdbcTemplate.query(sql, new SingleColumnRowMapper<T>(requiredType), parameterList.toArray());
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		return results.iterator().next();
	}

	public <T> List<T> getObjectList(Class<T> requiredType) {
		return jdbcTemplate.query(sql, new SingleColumnRowMapper<T>(requiredType), parameterList.toArray());
	}
}
