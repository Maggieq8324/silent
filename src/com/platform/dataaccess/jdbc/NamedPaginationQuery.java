package com.platform.dataaccess.jdbc;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.platform.dataaccess.support.Entity;
import com.platform.dataaccess.support.PaginationCondition;
import com.platform.dataaccess.support.PaginationResult;
import com.platform.dataaccess.support.SQLAndArgs;
import com.platform.dataaccess.support.SQLAndArgsGenerator;
import com.platform.support.PlatformException;

public final class NamedPaginationQuery {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final String sql;
	private final PaginationCondition p;
	private final MapSqlParameterSource msps;

	public NamedPaginationQuery(String sql, PaginationCondition p,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.sql = sql;
		this.p = p;
		this.msps = new MapSqlParameterSource();
	}

	public NamedPaginationQuery(String sql, MapSqlParameterSource msps, PaginationCondition p,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.sql = sql;
		this.p = p;
		this.msps = msps;
	}

	public NamedPaginationQuery setNamedParameter(String paramName, Object value) {
		msps.addValue(paramName, value);
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
		SQLAndArgs countSqlAndArgs = SQLAndArgsGenerator
				.getCountSqlAndArgs(new SQLAndArgs(sql, new ArrayList<Object>()));
		result.setTotal(namedParameterJdbcTemplate.queryForObject(countSqlAndArgs.getSql(), msps, Integer.class));
		SQLAndArgs rowsSqlAndArgs = SQLAndArgsGenerator
				.getPaginationSqlAndArgs(new SQLAndArgs(sql, new ArrayList<Object>()), p);
		result.setRows(namedParameterJdbcTemplate.query(rowsSqlAndArgs.getSql(), msps,
				new BeanPropertyRowMapper<T>(requiredType)));
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
		result.setTotal(namedParameterJdbcTemplate.queryForObject(countSqlAndArgs.getSql(), msps, Integer.class));
		SQLAndArgs rowsSqlAndArgs = SQLAndArgsGenerator
				.getPaginationSqlAndArgs(new SQLAndArgs(sql, new ArrayList<Object>()), p);
		result.setRows(namedParameterJdbcTemplate.query(rowsSqlAndArgs.getSql(), msps, new ColumnMapRowMapper()));
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
		result.setTotal(namedParameterJdbcTemplate.queryForObject(countSqlAndArgs.getSql(), msps, Integer.class));
		SQLAndArgs rowsSqlAndArgs = SQLAndArgsGenerator
				.getPaginationSqlAndArgs(new SQLAndArgs(sql, new ArrayList<Object>()), p);
		result.setRows(namedParameterJdbcTemplate.query(rowsSqlAndArgs.getSql(), msps,
				new SingleColumnRowMapper<T>(requiredType)));
		return result;
	}

}
