package com.platform.dataaccess.jdbc;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.platform.dataaccess.support.Entity;
import com.platform.dataaccess.support.PaginationCondition;
import com.platform.dataaccess.support.PaginationResult;
import com.platform.dataaccess.support.SQLAndArgs;
import com.platform.dataaccess.support.SQLAndArgsGenerator;
import com.platform.support.PlatformException;

public final class SimplePaginationQuery {

	private final JdbcTemplate jdbcTemplate;
	private final String sql;
	private final PaginationCondition p;

	public SimplePaginationQuery(String sql, PaginationCondition p, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
		this.p = p;
	}

	public <T extends Entity> PaginationResult<T> getPaginationEntities(Class<T> requiredType) {
		if (p.getPage() == null) {
			throw new PlatformException("Pagination page is null");
		}
		if (p.getRows() == null) {
			throw new PlatformException("Pagination rows is null");
		}
		PaginationResult<T> result = new PaginationResult<T>();
		SQLAndArgs countSqlAndArgs = SQLAndArgsGenerator
				.getCountSqlAndArgs(new SQLAndArgs(sql, new ArrayList<Object>()));
		result.setTotal(jdbcTemplate.queryForObject(countSqlAndArgs.getSql(), Integer.class));
		SQLAndArgs rowsSqlAndArgs = SQLAndArgsGenerator
				.getPaginationSqlAndArgs(new SQLAndArgs(sql, new ArrayList<Object>()), p);
		result.setRows(jdbcTemplate.query(rowsSqlAndArgs.getSql(), new BeanPropertyRowMapper<T>(requiredType)));
		return result;
	}

	public PaginationResult<Map<String, Object>> getPaginationMaps() {
		if (p.getPage() == null) {
			throw new PlatformException("Pagination page is null");
		}
		if (p.getRows() == null) {
			throw new PlatformException("Pagination rows is null");
		}
		PaginationResult<Map<String, Object>> result = new PaginationResult<Map<String, Object>>();
		SQLAndArgs countSqlAndArgs = SQLAndArgsGenerator
				.getCountSqlAndArgs(new SQLAndArgs(sql, new ArrayList<Object>()));
		result.setTotal(jdbcTemplate.queryForObject(countSqlAndArgs.getSql(), Integer.class));
		SQLAndArgs rowsSqlAndArgs = SQLAndArgsGenerator
				.getPaginationSqlAndArgs(new SQLAndArgs(sql, new ArrayList<Object>()), p);
		result.setRows(jdbcTemplate.query(rowsSqlAndArgs.getSql(), new ColumnMapRowMapper()));
		return result;
	}

	public <T> PaginationResult<T> getPaginationObjects(Class<T> requiredType) {
		if (p.getPage() == null) {
			throw new PlatformException("Pagination page is null");
		}
		if (p.getRows() == null) {
			throw new PlatformException("Pagination rows is null");
		}
		PaginationResult<T> result = new PaginationResult<T>();
		SQLAndArgs countSqlAndArgs = SQLAndArgsGenerator
				.getCountSqlAndArgs(new SQLAndArgs(sql, new ArrayList<Object>()));
		result.setTotal(jdbcTemplate.queryForObject(countSqlAndArgs.getSql(), Integer.class));
		SQLAndArgs rowsSqlAndArgs = SQLAndArgsGenerator
				.getPaginationSqlAndArgs(new SQLAndArgs(sql, new ArrayList<Object>()), p);
		result.setRows(jdbcTemplate.query(rowsSqlAndArgs.getSql(), new SingleColumnRowMapper<T>(requiredType)));
		return result;
	}

}
