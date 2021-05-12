package com.platform.dataaccess.support;

import java.util.List;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.platform.support.PlatformException;

public final class DataFieldValueGenerator {

	private final JdbcTemplate jdbcTemplate;
	private final TransactionTemplate transactionTemplate;
	private final String tableName;
	private final String columnName;
	private int cacheSize = 10;
	private long nextId = 0;
	private long maxId = 0;

	public DataFieldValueGenerator(JdbcTemplate jdbcTemplate, PlatformTransactionManager transactionManager,
			String tableName, String columnName) {
		this.jdbcTemplate = jdbcTemplate;
		this.transactionTemplate = new TransactionTemplate(transactionManager);
		this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		this.tableName = tableName;
		this.columnName = columnName;
	}

	public int nextIntValue() {
		return (int) this.nextLongValue();
	}

	public synchronized long nextLongValue() {
		if (this.maxId == this.nextId) {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					try {
						List<Long> results = jdbcTemplate.query(
								"select dqz from xt_blz" + " where lower(bmc) = '" + tableName.toLowerCase()
										+ "' and lower(lmc) = '" + columnName.toLowerCase() + "' for update",
								new SingleColumnRowMapper<Long>());
						if (results == null || results.isEmpty()) {
							jdbcTemplate.query("select " + columnName.toLowerCase() + " from " + tableName.toLowerCase()
									+ " where 1 = 2", new ColumnMapRowMapper());
							jdbcTemplate.update("insert into xt_blz (bmc,lmc,dqz,qzlsh,dqbm,cjrbh,cjsj,czrbh,czsj) "
									+ "values ('" + tableName.toLowerCase() + "','" + columnName.toLowerCase() + "',"
									+ cacheSize + ",1,'0','0',0,'0',0)");
							maxId = cacheSize;
							nextId = 1;
						} else {
							Long tmpId = results.iterator().next();
							jdbcTemplate.update("update xt_blz" + " set dqz = dqz + " + cacheSize
									+ ", qzlsh = qzlsh + 1 where lower(bmc) = '" + tableName.toLowerCase()
									+ "' and lower(lmc) = '" + columnName.toLowerCase() + "'");
							maxId = tmpId + cacheSize;
							nextId = tmpId + 1;
						}
					} catch (Throwable t) {
						status.setRollbackOnly();
						throw new PlatformException(t);
					}
				}
			});
		} else {
			this.nextId++;
		}
		return this.nextId;
	}

	public String nextStringValue() {
		return this.nextStringValue(0);
	}

	private String nextStringValue(int valueLength) {
		String s = Long.toString(this.nextLongValue());
		int len = s.length();
		if (len < valueLength) {
			StringBuilder sb = new StringBuilder(valueLength);
			for (int i = 0; i < valueLength - len; i++) {
				sb.append('0');
			}
			sb.append(s);
			s = sb.toString();
		}
		return s;
	}

}
