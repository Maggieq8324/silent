package com.platform.dataaccess.jdbc;

import java.util.ArrayList;
import java.util.List;
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

public final class ParameterizedPaginationQuery {

	private final JdbcTemplate jdbcTemplate;
	private final String sql;
	private final PaginationCondition p;
	private final List<Object> parameterList;

	public ParameterizedPaginationQuery(String sql, PaginationCondition p, JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
		this.p = p;
		this.parameterList = new ArrayList<Object>();
	}

	public ParameterizedPaginationQuery(String sql, List<Object> parameterList, PaginationCondition p,
			JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.sql = sql;
		this.p = p;
		this.parameterList = parameterList;
	}

	public ParameterizedPaginationQuery setParameter(Object... params) {
		for (Object value : params) {
			parameterList.add(value);
		}
		return this;
	}

	public <T extends Entity> PaginationResult<T> getPaginationEntities(Class<T> requiredType) {
		if (p.getPage() == null) {
			throw new PlatformException("Pagination page is null");
		}
		if (p.getRows() == null) {
			throw new PlatformException("Pagination rows is null");
		}
		PaginationResult<T> result = new PaginationResult<T>();
		SQLAndArgs countSqlAndArgs = SQLAndArgsGenerator.getCountSqlAndArgs(new SQLAndArgs(sql, parameterList));
System.out.println(countSqlAndArgs.getSql());
		result.setTotal(jdbcTemplate.queryForObject(countSqlAndArgs.getSql(), Integer.class,
				countSqlAndArgs.getArgs().toArray()));
		SQLAndArgs rowsSqlAndArgs = SQLAndArgsGenerator.getPaginationSqlAndArgs(new SQLAndArgs(sql, parameterList), p);
System.out.println(rowsSqlAndArgs.getSql());
		result.setRows(jdbcTemplate.query(rowsSqlAndArgs.getSql(), new BeanPropertyRowMapper<T>(requiredType),
				rowsSqlAndArgs.getArgs().toArray()));
		
		
		
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
		SQLAndArgs countSqlAndArgs = SQLAndArgsGenerator.getCountSqlAndArgs(new SQLAndArgs(sql, parameterList));
		result.setTotal(jdbcTemplate.queryForObject(countSqlAndArgs.getSql(), Integer.class,
				countSqlAndArgs.getArgs().toArray()));
		SQLAndArgs rowsSqlAndArgs = SQLAndArgsGenerator.getPaginationSqlAndArgs(new SQLAndArgs(sql, parameterList), p);
		result.setRows(jdbcTemplate.query(rowsSqlAndArgs.getSql(), new ColumnMapRowMapper(),
				rowsSqlAndArgs.getArgs().toArray()));
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
		SQLAndArgs countSqlAndArgs = SQLAndArgsGenerator.getCountSqlAndArgs(new SQLAndArgs(sql, parameterList));
		result.setTotal(jdbcTemplate.queryForObject(countSqlAndArgs.getSql(), Integer.class,
				countSqlAndArgs.getArgs().toArray()));
		SQLAndArgs rowsSqlAndArgs = SQLAndArgsGenerator.getPaginationSqlAndArgs(new SQLAndArgs(sql, parameterList), p);
		result.setRows(jdbcTemplate.query(rowsSqlAndArgs.getSql(), new SingleColumnRowMapper<T>(requiredType),
				rowsSqlAndArgs.getArgs().toArray()));
		return result;
	}

}
