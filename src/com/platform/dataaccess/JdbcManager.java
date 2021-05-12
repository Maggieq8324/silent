package com.platform.dataaccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.platform.dataaccess.jdbc.NamedPaginationQuery;
import com.platform.dataaccess.jdbc.NamedQuery;
import com.platform.dataaccess.jdbc.NamedUpdate;
import com.platform.dataaccess.jdbc.ParameterizedBatchUpdate;
import com.platform.dataaccess.jdbc.ParameterizedPaginationQuery;
import com.platform.dataaccess.jdbc.ParameterizedQuery;
import com.platform.dataaccess.jdbc.ParameterizedUpdate;
import com.platform.dataaccess.jdbc.SimplePaginationQuery;
import com.platform.dataaccess.jdbc.SimpleQuery;
import com.platform.dataaccess.jdbc.SimpleUpdate;
import com.platform.dataaccess.support.DataFieldValueGenerator;
import com.platform.dataaccess.support.Entity;
import com.platform.dataaccess.support.PaginationCondition;
import com.platform.dataaccess.support.PaginationResult;
import com.platform.dataaccess.support.SQLAndArgs;
import com.platform.dataaccess.support.SQLAndArgsGenerator;
import com.platform.dataaccess.support.SQLAndBatchArgs;
import com.platform.dataaccess.support.SQLAndBatchArgsGenerator;
import com.platform.dataaccess.support.Sort;
import com.platform.support.PlatformException;

public final class JdbcManager {

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final Map<String, DataFieldValueGenerator> dataFieldValueGeneratorMap;
	private final PlatformTransactionManager transactionManager;

	public JdbcManager(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
		this.dataFieldValueGeneratorMap = new HashMap<String, DataFieldValueGenerator>();
		this.transactionManager = new DataSourceTransactionManager(dataSource);
	}

	public SimpleQuery simpleQuery(String sql) {
		if (sql == null) {
			throw new PlatformException("sql is null");
		}
		return new SimpleQuery(sql, jdbcTemplate);
	}

	public SimplePaginationQuery simpleQuery(String sql, PaginationCondition p) {
		return new SimplePaginationQuery(sql, p, jdbcTemplate);
	}

	public int simpleUpdate(String sql) {
		if (sql == null) {
			throw new PlatformException("sql is null");
		}
		return new SimpleUpdate(sql, jdbcTemplate).getUpdateCount();
	}

	public ParameterizedQuery parameterizedQuery(String sql) {
		if (sql == null) {
			throw new PlatformException("sql is null");
		}
System.out.println(sql);
		return new ParameterizedQuery(sql, jdbcTemplate);
	}

	public ParameterizedPaginationQuery parameterizedQuery(String sql, PaginationCondition p) {
System.out.println(sql);
		return new ParameterizedPaginationQuery(sql, p, jdbcTemplate);
	}

	public ParameterizedUpdate parameterizedUpdate(String sql) {
		if (sql == null) {
			throw new PlatformException("sql is null");
		}
		return new ParameterizedUpdate(sql, jdbcTemplate);
	}

	public NamedQuery namedQuery(String sql) {
		if (sql == null) {
			throw new PlatformException("sql is null");
		}
		return new NamedQuery(sql, namedParameterJdbcTemplate);
	}

	public NamedPaginationQuery namedQuery(String sql, PaginationCondition p) {
		return new NamedPaginationQuery(sql, p, namedParameterJdbcTemplate);
	}

	public NamedUpdate namedUpdate(String sql) {
		if (sql == null) {
			throw new PlatformException("sql is null");
		}
		return new NamedUpdate(sql, namedParameterJdbcTemplate);
	}

	public int entityInsert(Entity entity) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntityInsertSqlAndArgs(this, entity);
		return new ParameterizedUpdate(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), jdbcTemplate).getUpdateCount();
	}

	public int[] entitiesInsert(List<? extends Entity> entities) {
		if (entities == null || entities.isEmpty()) {
			return new int[0];
		}
		SQLAndBatchArgs sqlAndBatchArgs = SQLAndBatchArgsGenerator.getEntitiesInsertSqlAndBatchArgs(this, entities);
		return new ParameterizedBatchUpdate(sqlAndBatchArgs.getSql(), sqlAndBatchArgs.getArgs(), jdbcTemplate)
				.getBatchUpdateCount();
	}

	public int entityDelete(Entity entity) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntityDeleteSqlAndArgs(entity);
		return new ParameterizedUpdate(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), jdbcTemplate).getUpdateCount();
	}

	public int[] entitiesDelete(List<? extends Entity> entities) {
		if (entities == null || entities.isEmpty()) {
			return new int[0];
		}
		SQLAndBatchArgs sqlAndBatchArgs = SQLAndBatchArgsGenerator.getEntitiesDeleteSqlAndBatchArgs(entities);
		return new ParameterizedBatchUpdate(sqlAndBatchArgs.getSql(), sqlAndBatchArgs.getArgs(), jdbcTemplate)
				.getBatchUpdateCount();
	}

	public int entityUpdate(Entity entity) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntityUpdateSqlAndArgs(entity);
		return new ParameterizedUpdate(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), jdbcTemplate).getUpdateCount();
	}

	public int[] entitiesUpdate(List<? extends Entity> entities) {
		if (entities == null || entities.isEmpty()) {
			return new int[0];
		}
		SQLAndBatchArgs sqlAndBatchArgs = SQLAndBatchArgsGenerator.getEntitiesUpdateSqlAndBatchArgs(entities);
		return new ParameterizedBatchUpdate(sqlAndBatchArgs.getSql(), sqlAndBatchArgs.getArgs(), jdbcTemplate)
				.getBatchUpdateCount();
	}

	public int entityPartUpdate(Entity entity) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntityPartUpdateSqlAndArgs(entity);
		return new ParameterizedUpdate(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), jdbcTemplate).getUpdateCount();
	}

	public <T extends Entity> T entityQuery(T entity, Class<T> requiredType) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitySelectSqlAndArgs(entity);
		return new ParameterizedQuery(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), jdbcTemplate).getEntity(requiredType);
	}

	public <T extends Entity> List<T> entitiesQuery(T entity, Class<T> requiredType) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesSelectSqlAndArgs(entity);
		return new ParameterizedQuery(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), jdbcTemplate)
				.getEntityList(requiredType);
	}

	public <T extends Entity> PaginationResult<T> entitiesQuery(T entity, Class<T> requiredType,
			PaginationCondition p) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesSelectSqlAndArgs(entity);
		return new ParameterizedPaginationQuery(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), p, jdbcTemplate)
				.getPaginationEntities(requiredType);
	}

	public <T extends Entity> List<T> entitiesQuery(T entity, Class<T> requiredType, Sort sort) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesSelectSqlAndArgs(entity);
		SQLAndArgs sortSqlAndArgs = SQLAndArgsGenerator.getSortSqlAndArgs(sort);
		String sql = sqlAndArgs.getSql() + sortSqlAndArgs.getSql();
		List<Object> args = new ArrayList<Object>();
		args.addAll(sqlAndArgs.getArgs());
		args.addAll(sortSqlAndArgs.getArgs());
		return new ParameterizedQuery(sql, args, jdbcTemplate).getEntityList(requiredType);
	}

	public <T extends Entity> List<T> entitiesWithZzbmQuery(T entity, Class<T> requiredType) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesWithZzbmSelectSqlAndArgs(entity);
		return new ParameterizedQuery(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), jdbcTemplate)
				.getEntityList(requiredType);
	}

	public <T extends Entity> PaginationResult<T> entitiesWithZzbmQuery(T entity, Class<T> requiredType,
			PaginationCondition p) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesWithZzbmSelectSqlAndArgs(entity);
		return new ParameterizedPaginationQuery(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), p, jdbcTemplate)
				.getPaginationEntities(requiredType);
	}

	public <T extends Entity> List<T> entitiesWithZzbmQuery(T entity, Class<T> requiredType, Sort sort) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesWithZzbmSelectSqlAndArgs(entity);
		SQLAndArgs sortSqlAndArgs = SQLAndArgsGenerator.getSortSqlAndArgs(sort);
		String sql = sqlAndArgs.getSql() + sortSqlAndArgs.getSql();
		List<Object> args = new ArrayList<Object>();
		args.addAll(sqlAndArgs.getArgs());
		args.addAll(sortSqlAndArgs.getArgs());
		return new ParameterizedQuery(sql, args, jdbcTemplate).getEntityList(requiredType);
	}

	public <T extends Entity> List<T> entitiesLikeQuery(T entity, Class<T> requiredType) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesLikeSelectSqlAndArgs(entity);
		return new ParameterizedQuery(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), jdbcTemplate)
				.getEntityList(requiredType);
	}

	public <T extends Entity> PaginationResult<T> entitiesLikeQuery(T entity, Class<T> requiredType,
			PaginationCondition p) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesLikeSelectSqlAndArgs(entity);
		return new ParameterizedPaginationQuery(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), p, jdbcTemplate)
				.getPaginationEntities(requiredType);
	}

	public <T extends Entity> List<T> entitiesLikeQuery(T entity, Class<T> requiredType, Sort sort) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesLikeSelectSqlAndArgs(entity);
		SQLAndArgs sortSqlAndArgs = SQLAndArgsGenerator.getSortSqlAndArgs(sort);
		String sql = sqlAndArgs.getSql() + sortSqlAndArgs.getSql();
		List<Object> args = new ArrayList<Object>();
		args.addAll(sqlAndArgs.getArgs());
		args.addAll(sortSqlAndArgs.getArgs());
		return new ParameterizedQuery(sql, args, jdbcTemplate).getEntityList(requiredType);
	}

	public <T extends Entity> List<T> entitiesWithZzbmLikeQuery(T entity, Class<T> requiredType) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesWithZzbmLikeSelectSqlAndArgs(entity);
		return new ParameterizedQuery(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), jdbcTemplate)
				.getEntityList(requiredType);
	}

	public <T extends Entity> PaginationResult<T> entitiesWithZzbmLikeQuery(T entity, Class<T> requiredType,
			PaginationCondition p) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesWithZzbmLikeSelectSqlAndArgs(entity);
		return new ParameterizedPaginationQuery(sqlAndArgs.getSql(), sqlAndArgs.getArgs(), p, jdbcTemplate)
				.getPaginationEntities(requiredType);
	}

	public <T extends Entity> List<T> entitiesWithZzbmLikeQuery(T entity, Class<T> requiredType, Sort sort) {
		SQLAndArgs sqlAndArgs = SQLAndArgsGenerator.getEntitiesWithZzbmLikeSelectSqlAndArgs(entity);
		SQLAndArgs sortSqlAndArgs = SQLAndArgsGenerator.getSortSqlAndArgs(sort);
		String sql = sqlAndArgs.getSql() + sortSqlAndArgs.getSql();
		List<Object> args = new ArrayList<Object>();
		args.addAll(sqlAndArgs.getArgs());
		args.addAll(sortSqlAndArgs.getArgs());
		return new ParameterizedQuery(sql, args, jdbcTemplate).getEntityList(requiredType);
	}

	public int getCurrentDateIntValue() {
		return this.simpleQuery(" select date_format(now(),'%Y%m%d') as currentDate ").getObject(Integer.class);
	}

	public String getCurrentDateStringValue() {
		return this.simpleQuery(" select date_format(now(),'%Y-%m-%d') as currentDate ").getObject(String.class);
	}
	
	public String getCurrentNYStringValue() {
		return this.simpleQuery(" select date_format(now(),'%Y%m') as currentDate ").getObject(String.class);
	}

	public int getCurrentTimeIntValue() {
		return this.simpleQuery(" select date_format(now(),'%H%i%s') as currentTime ").getObject(Integer.class);
	}

	public String getCurrentTimeStringValue() {
		return this.simpleQuery(" select date_format(now(),'%H:%i:%s') as currentTime ").getObject(String.class);
	}

	public long getCurrentDateTimeLongValue() {
		return this.simpleQuery(" select date_format(now(),'%Y%m%d%H%i%s') as currentDateTime ").getObject(Long.class);
	}

	public String getCurrentDateTimeStringValue() {
		return this.simpleQuery(" select date_format(now(),'%Y-%m-%d %H:%i:%s') as currentDateTime ")
				.getObject(String.class);
	}

	public Date getCurrentDateTime() {
		return this.simpleQuery(" select now() as currentDateTime ").getObject(Date.class);
	}

	public int getNextIntValue(String tableName, String columnName) {
		return getDataFieldValueGenerator(tableName, columnName).nextIntValue();
	}

	public long getNextLongValue(String tableName, String columnName) {
		return getDataFieldValueGenerator(tableName, columnName).nextLongValue();
	}

	public String getNextStringValue(String tableName, String columnName) {
		return getDataFieldValueGenerator(tableName, columnName).nextStringValue();
	}

	private synchronized DataFieldValueGenerator getDataFieldValueGenerator(String tableName, String columnName) {
		String key = tableName.toLowerCase() + "." + columnName.toLowerCase();
		if (!dataFieldValueGeneratorMap.containsKey(key)) {
			DataFieldValueGenerator dataFieldValueGenerator = new DataFieldValueGenerator(this.jdbcTemplate,
					transactionManager, tableName, columnName);
			dataFieldValueGeneratorMap.put(key, dataFieldValueGenerator);
			return dataFieldValueGenerator;
		} else {
			return dataFieldValueGeneratorMap.get(key);
		}
	}

}
