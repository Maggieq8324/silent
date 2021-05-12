package com.platform.dataaccess.support;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.platform.dataaccess.JdbcManager;
import com.platform.support.PlatformException;

public final class SQLAndArgsGenerator {

	private SQLAndArgsGenerator() {
	}

	public static SQLAndArgs getEntityInsertSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		sb.append(" insert into ");
		sb.append(getTableName(entity, true));
		sb.append(" (");
		Column column;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(AutoIncrement.class) != null) {
				continue;
			}
			column = fields[i].getAnnotation(Column.class);
			if (column != null) {
				sb.append(column.name());
			} else {
				sb.append(fields[i].getName());
			}
			if (i != fields.length - 1) {
				sb.append(",");
			}
		}
		sb.append(") values (");
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(AutoIncrement.class) != null) {
				continue;
			}
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			if (fieldTypeName.equals("java.lang.String") && fieldValue != null) {
				if (!fieldValue.equals("")) {
					sb.append("?");
					args.add(fieldValue);
				} else {
					sb.append("?");
					args.add(null);
				}
			} else {
				sb.append("?");
				args.add(fieldValue);
			}
			if (i != fields.length - 1) {
				sb.append(",");
			}
		}
		sb.append(") ");
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntityInsertSqlAndArgs(JdbcManager jdbcManager, Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		String tableName = getTableName(entity, true);
		sb.append(" insert into ");
		sb.append(tableName);
		sb.append(" (");
		Column column;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(AutoIncrement.class) != null) {
				continue;
			}
			column = fields[i].getAnnotation(Column.class);
			if (column != null) {
				sb.append(column.name());
			} else {
				sb.append(fields[i].getName());
			}
			if (i != fields.length - 1) {
				sb.append(",");
			}
		}
		sb.append(") values (");
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(AutoIncrement.class) != null) {
				continue;
			}
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			if (fieldTypeName.equals("java.lang.String")) {
				if (fieldValue != null && !fieldValue.equals("")) {
					sb.append("?");
					args.add(fieldValue);
				} else {
					if (fields[i].getAnnotation(PrimaryKey.class) != null) {
						Column primaryKeyColumn = fields[i].getAnnotation(Column.class);
						if (primaryKeyColumn != null) {
							sb.append("?");
							args.add(jdbcManager.getNextStringValue(tableName, primaryKeyColumn.name()));
						} else {
							sb.append("?");
							args.add(jdbcManager.getNextStringValue(tableName, fields[i].getName()));
						}
					} else {
						sb.append("?");
						args.add(null);
					}
				}
			} else {
				if (fields[i].getAnnotation(PrimaryKey.class) != null && fieldValue == null) {
					Column primaryKeyColumn = fields[i].getAnnotation(Column.class);
					if (primaryKeyColumn != null) {
						sb.append("?");
						args.add(jdbcManager.getNextStringValue(tableName, primaryKeyColumn.name()));
					} else {
						sb.append("?");
						args.add(jdbcManager.getNextStringValue(tableName, fields[i].getName()));
					}
				} else {
					sb.append("?");
					args.add(fieldValue);
				}
			}
			if (i != fields.length - 1) {
				sb.append(",");
			}
		}
		sb.append(") ");
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntityDeleteSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		sb.append(" delete from ");
		sb.append(getTableName(entity, true));
		sb.append(" where ");
		Column column;
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(PrimaryKey.class) != null) {
				if (j++ > 0) {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
				} else {
					sb.append(fields[i].getName());
				}
				Object fieldValue = getFieldValue(entity, fields[i]);
				String fieldTypeName = fields[i].getType().getName();
				if (fieldValue == null || fieldValue.equals("")) {
					throw new PlatformException(
							"delete error : sql: " + sb.toString() + " is null : primary key is null");
				}
				sb.append(" = ");
				if (fieldTypeName.equals("java.lang.String")) {
					sb.append("?");
					args.add(fieldValue);
				} else {
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntityUpdateSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		sb.append(" update ");
		sb.append(getTableName(entity, true));
		sb.append(" set ");
		Column column;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(AutoIncrement.class) != null
					|| fields[i].getAnnotation(PrimaryKey.class) != null) {
				continue;
			}
			column = fields[i].getAnnotation(Column.class);
			if (column != null) {
				sb.append(column.name());
			} else {
				sb.append(fields[i].getName());
			}
			sb.append(" = ");
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			if (fieldTypeName.equals("java.lang.String") && fieldValue != null) {
				if (!fieldValue.equals("")) {
					sb.append("?");
					args.add(fieldValue);
				} else {
					sb.append("?");
					args.add(null);
				}
			} else {
				sb.append("?");
				args.add(fieldValue);
			}
			if (i != fields.length - 1) {
				sb.append(",");
			}
		}
		sb.append(" where ");
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(PrimaryKey.class) != null) {
				if (j++ > 0) {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
				} else {
					sb.append(fields[i].getName());
				}
				Object fieldValue = getFieldValue(entity, fields[i]);
				String fieldTypeName = fields[i].getType().getName();
				if (fieldValue == null || fieldValue.equals("")) {
					throw new PlatformException(
							"update error : sql: " + sb.toString() + " is null : primary key is null");
				}
				sb.append(" = ");
				if (fieldTypeName.equals("java.lang.String")) {
					sb.append("?");
					args.add(fieldValue);
				} else {
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntityPartUpdateSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		sb.append(" update ");
		sb.append(getTableName(entity, true));
		sb.append(" set ");
		Column column;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(AutoIncrement.class) != null
					|| fields[i].getAnnotation(PrimaryKey.class) != null) {
				continue;
			}
			Object fieldValue = getFieldValue(entity, fields[i]);
			if (fieldValue == null || fieldValue.equals("")) {
				continue;
			}
			column = fields[i].getAnnotation(Column.class);
			if (column != null) {
				sb.append(column.name());
			} else {
				sb.append(fields[i].getName());
			}
			sb.append(" = ");
			String fieldTypeName = fields[i].getType().getName();
			if (fieldTypeName.equals("java.lang.String") && fieldValue != null) {
				if (!fieldValue.equals("")) {
					sb.append("?");
					args.add(fieldValue);
				} else {
					sb.append("?");
					args.add(null);
				}
			} else {
				sb.append("?");
				args.add(fieldValue);
			}
			if (i != fields.length - 1) {
				sb.append(",");
			}
		}
		sb.append(" where ");
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(PrimaryKey.class) != null) {
				if (j++ > 0) {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
				} else {
					sb.append(fields[i].getName());
				}
				Object fieldValue = getFieldValue(entity, fields[i]);
				String fieldTypeName = fields[i].getType().getName();
				if (fieldValue == null || fieldValue.equals("")) {
					throw new PlatformException(
							"update error : sql: " + sb.toString() + " is null : primary key is null");
				}
				sb.append(" = ");
				if (fieldTypeName.equals("java.lang.String")) {
					sb.append("?");
					args.add(fieldValue);
				} else {
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntitySelectSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		sb.append(" select * from ");
		sb.append(getTableName(entity, true));
		sb.append(" where ");
		Column column;
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getAnnotation(PrimaryKey.class) != null) {
				if (j++ > 0) {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
				} else {
					sb.append(fields[i].getName());
				}
				Object fieldValue = getFieldValue(entity, fields[i]);
				String fieldTypeName = fields[i].getType().getName();
				if (fieldValue == null || fieldValue.equals("")) {
					throw new PlatformException(
							"select error : sql: " + sb.toString() + " is null : primary key is null");
				}
				sb.append(" = ");
				if (fieldTypeName.equals("java.lang.String")) {
					sb.append("?");
					args.add(fieldValue);
				} else {
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntitiesSelectSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		sb.append(" select * from ");
		sb.append(getTableName(entity, false));
		Column column;
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			if (fieldValue != null && !fieldValue.equals("")) {
				if (j++ == 0) {
					sb.append(" where ");
				} else {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
				} else {
					sb.append(fields[i].getName());
				}
				if (fieldTypeName.equals("java.lang.String")) {
					sb.append(" = ");
					sb.append("?");
					args.add(fieldValue);
				} else {
					sb.append(" = ");
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntitiesWithZzbmSelectSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		boolean isZzbm = false;
		sb.append(" select * from ");
		sb.append(getTableName(entity, false));
		Column column;
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			String columnName;
			if (fieldValue != null && !fieldValue.equals("")) {
				if (j++ == 0) {
					sb.append(" where ");
				} else {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
					columnName = column.name();
				} else {
					sb.append(fields[i].getName());
					columnName = fields[i].getName();
				}
				if (fieldTypeName.equals("java.lang.String")) {
					if (columnName.equals("zzbm") || columnName.equals("ZZBM")) {
						sb.append(" like ");
						sb.append("?");
						args.add(fieldValue + "%");
						isZzbm = true;
					} else {
						sb.append(" = ");
						sb.append("?");
						args.add(fieldValue);
					}
				} else {
					sb.append(" = ");
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		if (!isZzbm) {
			throw new PlatformException("zzbm field is required");
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntitiesLikeSelectSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		sb.append(" select * from ");
		sb.append(getTableName(entity, false));
		Column column;
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			if (fieldValue != null && !fieldValue.equals("")) {
				if (j++ == 0) {
					sb.append(" where ");
				} else {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
				} else {
					sb.append(fields[i].getName());
				}
				if (fieldTypeName.equals("java.lang.String")) {
					sb.append(" like ");
					sb.append("?");
					args.add("%" + fieldValue + "%");
				} else {
					sb.append(" = ");
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntitiesWithZzbmLikeSelectSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		boolean isZzbm = false;
		sb.append(" select * from ");
		sb.append(getTableName(entity, false));
		Column column;
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			String columnName;
			if (fieldValue != null && !fieldValue.equals("")) {
				if (j++ == 0) {
					sb.append(" where ");
				} else {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
					columnName = column.name();
				} else {
					sb.append(fields[i].getName());
					columnName = fields[i].getName();
				}
				if (fieldTypeName.equals("java.lang.String")) {
					if (columnName.equals("zzbm") || columnName.equals("ZZBM")) {
						sb.append(" like ");
						sb.append("?");
						args.add(fieldValue + "%");
						isZzbm = true;
					} else {
						sb.append(" like ");
						sb.append("?");
						args.add("%" + fieldValue + "%");
					}
				} else {
					sb.append(" = ");
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		if (!isZzbm) {
			throw new PlatformException("zzbm field is required");
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntitiesSelectConditionSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		Column column;
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			if (fieldValue != null && !fieldValue.equals("")) {
				if (j++ == 0) {
					sb.append(" where ");
				} else {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
				} else {
					sb.append(fields[i].getName());
				}
				if (fieldTypeName.equals("java.lang.String")) {
					sb.append(" = ");
					sb.append("?");
					args.add(fieldValue);
				} else {
					sb.append(" = ");
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntitiesWithZzbmSelectConditionSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		boolean isZzbm = false;
		Column column;
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			String columnName;
			if (fieldValue != null && !fieldValue.equals("")) {
				if (j++ == 0) {
					sb.append(" where ");
				} else {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
					columnName = column.name();
				} else {
					sb.append(fields[i].getName());
					columnName = fields[i].getName();
				}
				if (fieldTypeName.equals("java.lang.String")) {
					if (columnName.equals("zzbm") || columnName.equals("ZZBM")) {
						sb.append(" like ");
						sb.append("?");
						args.add(fieldValue + "%");
						isZzbm = true;
					} else {
						sb.append(" = ");
						sb.append("?");
						args.add(fieldValue);
					}
				} else {
					sb.append(" = ");
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		if (!isZzbm) {
			throw new PlatformException("zzbm field is required");
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntitiesLikeSelectConditionSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		Column column;
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			if (fieldValue != null && !fieldValue.equals("")) {
				if (j++ == 0) {
					sb.append(" where ");
				} else {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
				} else {
					sb.append(fields[i].getName());
				}
				if (fieldTypeName.equals("java.lang.String")) {
					sb.append(" like ");
					sb.append("?");
					args.add("%" + fieldValue + "%");
				} else {
					sb.append(" = ");
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getEntitiesWithZzbmLikeSelectConditionSqlAndArgs(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		List<Object> args = new ArrayList<Object>();
		boolean isZzbm = false;
		Column column;
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			Object fieldValue = getFieldValue(entity, fields[i]);
			String fieldTypeName = fields[i].getType().getName();
			String columnName;
			if (fieldValue != null && !fieldValue.equals("")) {
				if (j++ == 0) {
					sb.append(" where ");
				} else {
					sb.append(" and ");
				}
				column = fields[i].getAnnotation(Column.class);
				if (column != null) {
					sb.append(column.name());
					columnName = column.name();
				} else {
					sb.append(fields[i].getName());
					columnName = fields[i].getName();
				}
				if (fieldTypeName.equals("java.lang.String")) {
					if (columnName.equals("zzbm") || columnName.equals("ZZBM")) {
						sb.append(" like ");
						sb.append("?");
						args.add(fieldValue + "%");
						isZzbm = true;
					} else {
						sb.append(" like ");
						sb.append("?");
						args.add("%" + fieldValue + "%");
					}
				} else {
					sb.append(" = ");
					sb.append("?");
					args.add(fieldValue);
				}
			}
		}
		if (!isZzbm) {
			throw new PlatformException("zzbm field is required");
		}
		return new SQLAndArgs(sb.toString(), args);
	}

	public static SQLAndArgs getPaginationSqlAndArgs(SQLAndArgs sqlAndArgs, PaginationCondition p) {
		int orderByIndex = sqlAndArgs.getSql().toLowerCase().indexOf(" order by ");
		if (orderByIndex != -1) {
			throw new PlatformException("order by is not allowed");
		}
		StringBuilder paginationSql = new StringBuilder();
		paginationSql.append(sqlAndArgs.getSql());
		SQLAndArgs sortSqlAndArgs = getSortSqlAndArgs(new Sort(p.getSort(), p.getOrder()));
		paginationSql.append(sortSqlAndArgs.getSql());
		int start = (p.getPage() - 1) * p.getRows();
		paginationSql.append(" limit ").append(start).append(",").append(p.getRows());
		List<Object> args = new ArrayList<Object>();
		args.addAll(sqlAndArgs.getArgs());
		args.addAll(sortSqlAndArgs.getArgs());
		return new SQLAndArgs(paginationSql.toString(), args);
	}

	public static SQLAndArgs getSortSqlAndArgs(Sort sort) {
		if (sort.getSortName() == null || sort.getSortName().equals("")) {
			return new SQLAndArgs("", new ArrayList<Object>());
		}
		String[] sortNameArray = sort.getSortName().split(",");
		String[] sortOrderArray = null;
		if (sort.getSortOrder() != null && !sort.getSortOrder().equals("")) {
			sortOrderArray = sort.getSortOrder().split(",");
			if (sortNameArray.length != sortOrderArray.length) {
				throw new PlatformException("sortOrder and sortName count is not match");
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(" order by ");
		for (int i = 0; i < sortNameArray.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(sortNameArray[i].trim());
			sb.append(" ");
			if (sortOrderArray != null) {
				sb.append(sortOrderArray[i].trim());
			} else {
				sb.append("asc");
			}
		}
		sb.append(" ");
		return new SQLAndArgs(sb.toString(), new ArrayList<Object>());
	}

	public static SQLAndArgs getCountSqlAndArgs(SQLAndArgs sqlAndArgs) {
		if (sqlAndArgs.getSql() == null || sqlAndArgs.getSql().equals("")) {
			throw new PlatformException("sql is null");
		}
		int orderByIndex = sqlAndArgs.getSql().toLowerCase().indexOf(" order by ");
		if (orderByIndex != -1) {
			throw new PlatformException("order by is not allowed");
		}
		return new SQLAndArgs("select count(1) from (" + sqlAndArgs.getSql() + ") c", sqlAndArgs.getArgs());
	}

	private static Object getFieldValue(Entity entity, Field field) {
		String fieldName = field.getName();
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String getter = "get" + firstLetter + fieldName.substring(1);
		Method method;
		try {
			method = entity.getClass().getMethod(getter, new Class[] {});
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		Object value;
		try {
			value = method.invoke(entity, new Object[] {});
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		if (value != null) {
			return value;
		} else {
			return null;
		}
	}

	private static String getTableName(Entity entity, boolean checkTable) {
		Table table = entity.getClass().getAnnotation(Table.class);
		if (table != null) {
			return table.name();
		} else {
			View view = entity.getClass().getAnnotation(View.class);
			if (view != null) {
				if (checkTable) {
					throw new PlatformException("view is not allowed");
				} else {
					return view.name();
				}
			} else {
				String className = entity.getClass().getName();
				return className.substring(className.lastIndexOf('.') + 1);
			}
		}
	}

}
