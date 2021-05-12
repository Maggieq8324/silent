package com.platform.dataaccess.support;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.platform.dataaccess.JdbcManager;
import com.platform.support.PlatformException;

public final class SQLAndBatchArgsGenerator {

	private SQLAndBatchArgsGenerator() {
	}

	public static SQLAndBatchArgs getEntitiesInsertSqlAndBatchArgs(List<? extends Entity> entities) {
		if (entities == null || entities.size() == 0) {
			throw new PlatformException("get entities insert sql error : entities is null");
		}
		StringBuilder sb = new StringBuilder();
		List<Object[]> allArgs = new ArrayList<Object[]>();
		List<String> fieldNames = new ArrayList<String>();
		for (int k = 0; k < entities.size(); k++) {
			if (k == 0) {
				Field[] fields = entities.get(k).getClass().getDeclaredFields();
				List<Object> args = new ArrayList<Object>();
				sb.append(" insert into ");
				sb.append(getTableName(entities.get(k), true));
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
					Object fieldValue = getFieldValue(entities.get(k), fields[i]);
					String fieldTypeName = fields[i].getType().getName();
					if (fieldTypeName.equals("java.lang.String") && fieldValue != null && fieldValue.equals("")) {
						sb.append("?");
						fieldNames.add(fields[i].getName());
						args.add(null);
					} else {
						sb.append("?");
						fieldNames.add(fields[i].getName());
						args.add(fieldValue);
					}
					if (i != fields.length - 1) {
						sb.append(",");
					}
				}
				sb.append(") ");
				allArgs.add(args.toArray());
			} else {
				List<Object> args = new ArrayList<Object>();
				for (String fieldName : fieldNames) {
					Field field;
					try {
						field = entities.get(k).getClass().getDeclaredField(fieldName);
					} catch (NoSuchFieldException e) {
						throw new RuntimeException(e);
					} catch (SecurityException e) {
						throw new RuntimeException(e);
					}
					Object fieldValue = getFieldValue(entities.get(k), field);
					String fieldTypeName = field.getType().getName();
					if (fieldTypeName.equals("java.lang.String") && fieldValue != null && fieldValue.equals("")) {
						args.add(null);
					} else {
						args.add(fieldValue);
					}
				}
				allArgs.add(args.toArray());
			}
		}
		return new SQLAndBatchArgs(sb.toString(), allArgs);
	}

	public static SQLAndBatchArgs getEntitiesInsertSqlAndBatchArgs(JdbcManager jdbcManager,
			List<? extends Entity> entities) {
		if (entities == null || entities.size() == 0) {
			throw new PlatformException("get entities insert sql error : entities is null");
		}
		StringBuilder sb = new StringBuilder();
		List<Object[]> allArgs = new ArrayList<Object[]>();
		List<String> fieldNames = new ArrayList<String>();
		for (int k = 0; k < entities.size(); k++) {
			if (k == 0) {
				Field[] fields = entities.get(k).getClass().getDeclaredFields();
				List<Object> args = new ArrayList<Object>();
				String tableName = getTableName(entities.get(k), true);
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
					Object fieldValue = getFieldValue(entities.get(k), fields[i]);
					String fieldTypeName = fields[i].getType().getName();
					if (fieldTypeName.equals("java.lang.String")) {
						if (fieldValue != null && !fieldValue.equals("")) {
							sb.append("?");
							fieldNames.add(fields[i].getName());
							args.add(fieldValue);
						} else {
							if (fields[i].getAnnotation(PrimaryKey.class) != null) {
								Column primaryKeyColumn = fields[i].getAnnotation(Column.class);
								if (primaryKeyColumn != null) {
									sb.append("?");
									fieldNames.add(fields[i].getName());
									args.add(jdbcManager.getNextStringValue(tableName, primaryKeyColumn.name()));
								} else {
									sb.append("?");
									fieldNames.add(fields[i].getName());
									args.add(jdbcManager.getNextStringValue(tableName, fields[i].getName()));
								}
							} else {
								sb.append("?");
								fieldNames.add(fields[i].getName());
								args.add(null);
							}
						}
					} else {
						if (fields[i].getAnnotation(PrimaryKey.class) != null && fieldValue == null) {
							Column primaryKeyColumn = fields[i].getAnnotation(Column.class);
							if (primaryKeyColumn != null) {
								sb.append("?");
								fieldNames.add(fields[i].getName());
								args.add(jdbcManager.getNextStringValue(tableName, primaryKeyColumn.name()));
							} else {
								sb.append("?");
								fieldNames.add(fields[i].getName());
								args.add(jdbcManager.getNextStringValue(tableName, fields[i].getName()));
							}
						} else {
							sb.append("?");
							fieldNames.add(fields[i].getName());
							args.add(fieldValue);
						}
					}
					if (i != fields.length - 1) {
						sb.append(",");
					}
				}
				sb.append(") ");
				allArgs.add(args.toArray());
			} else {
				List<Object> args = new ArrayList<Object>();
				String tableName = getTableName(entities.get(k), true);
				for (String fieldName : fieldNames) {
					Field field;
					try {
						field = entities.get(k).getClass().getDeclaredField(fieldName);
					} catch (NoSuchFieldException e) {
						throw new RuntimeException(e);
					} catch (SecurityException e) {
						throw new RuntimeException(e);
					}
					Object fieldValue = getFieldValue(entities.get(k), field);
					String fieldTypeName = field.getType().getName();
					if (fieldTypeName.equals("java.lang.String")) {
						if (fieldValue != null && !fieldValue.equals("")) {
							args.add(fieldValue);
						} else {
							if (field.getAnnotation(PrimaryKey.class) != null) {
								Column primaryKeyColumn = field.getAnnotation(Column.class);
								if (primaryKeyColumn != null) {
									args.add(jdbcManager.getNextStringValue(tableName, primaryKeyColumn.name()));
								} else {
									args.add(jdbcManager.getNextStringValue(tableName, field.getName()));
								}
							} else {
								args.add(null);
							}
						}
					} else {
						if (field.getAnnotation(PrimaryKey.class) != null && fieldValue == null) {
							Column primaryKeyColumn = field.getAnnotation(Column.class);
							if (primaryKeyColumn != null) {
								args.add(jdbcManager.getNextStringValue(tableName, primaryKeyColumn.name()));
							} else {
								args.add(jdbcManager.getNextStringValue(tableName, field.getName()));
							}
						} else {
							args.add(fieldValue);
						}
					}
				}
				allArgs.add(args.toArray());
			}
		}
		return new SQLAndBatchArgs(sb.toString(), allArgs);
	}

	public static SQLAndBatchArgs getEntitiesDeleteSqlAndBatchArgs(List<? extends Entity> entities) {
		if (entities == null || entities.size() == 0) {
			throw new PlatformException("get entities delete sql error : entities is null");
		}
		StringBuilder sb = new StringBuilder();
		List<Object[]> allArgs = new ArrayList<Object[]>();
		List<String> fieldNames = new ArrayList<String>();
		for (int k = 0; k < entities.size(); k++) {
			if (k == 0) {
				Field[] fields = entities.get(k).getClass().getDeclaredFields();
				List<Object> args = new ArrayList<Object>();
				sb.append(" delete from ");
				sb.append(getTableName(entities.get(k), true));
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
						Object fieldValue = getFieldValue(entities.get(k), fields[i]);
						String fieldTypeName = fields[i].getType().getName();
						if (fieldValue == null || fieldValue.equals("")) {
							throw new PlatformException(
									"delete error : sql: " + sb.toString() + " is null : primary key is null");
						}
						sb.append(" = ");
						if (fieldTypeName.equals("java.lang.String")) {
							sb.append("?");
							fieldNames.add(fields[i].getName());
							args.add(fieldValue);
						} else {
							sb.append("?");
							fieldNames.add(fields[i].getName());
							args.add(fieldValue);
						}
					}
				}
				allArgs.add(args.toArray());
			} else {
				List<Object> args = new ArrayList<Object>();
				for (String fieldName : fieldNames) {
					Field field;
					try {
						field = entities.get(k).getClass().getDeclaredField(fieldName);
					} catch (NoSuchFieldException e) {
						throw new RuntimeException(e);
					} catch (SecurityException e) {
						throw new RuntimeException(e);
					}
					Object fieldValue = getFieldValue(entities.get(k), field);
					String fieldTypeName = field.getType().getName();
					if (field.getAnnotation(PrimaryKey.class) != null) {
						if (fieldValue == null || fieldValue.equals("")) {
							throw new PlatformException(
									"delete error : sql: " + sb.toString() + " is null : primary key is null");
						}
					}
					if (fieldTypeName.equals("java.lang.String") && fieldValue != null && fieldValue.equals("")) {
						args.add(null);
					} else {
						args.add(fieldValue);
					}
				}
				allArgs.add(args.toArray());
			}
		}
		return new SQLAndBatchArgs(sb.toString(), allArgs);
	}

	public static SQLAndBatchArgs getEntitiesUpdateSqlAndBatchArgs(List<? extends Entity> entities) {
		if (entities == null || entities.size() == 0) {
			throw new PlatformException("get entities update sql error : entities is null");
		}
		StringBuilder sb = new StringBuilder();
		List<Object[]> allArgs = new ArrayList<Object[]>();
		List<String> fieldNames = new ArrayList<String>();
		for (int k = 0; k < entities.size(); k++) {
			if (k == 0) {
				Field[] fields = entities.get(k).getClass().getDeclaredFields();
				List<Object> args = new ArrayList<Object>();
				sb.append(" update ");
				sb.append(getTableName(entities.get(k), true));
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
					Object fieldValue = getFieldValue(entities.get(k), fields[i]);
					String fieldTypeName = fields[i].getType().getName();
					if (fieldTypeName.equals("java.lang.String") && fieldValue != null) {
						if (!fieldValue.equals("")) {
							sb.append("?");
							fieldNames.add(fields[i].getName());
							args.add(fieldValue);
						} else {
							sb.append("?");
							fieldNames.add(fields[i].getName());
							args.add(null);
						}
					} else {
						sb.append("?");
						fieldNames.add(fields[i].getName());
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
						Object fieldValue = getFieldValue(entities.get(k), fields[i]);
						String fieldTypeName = fields[i].getType().getName();
						if (fieldValue == null || fieldValue.equals("")) {
							throw new PlatformException(
									"update error : sql: " + sb.toString() + " is null : primary key is null");
						}
						sb.append(" = ");
						if (fieldTypeName.equals("java.lang.String")) {
							sb.append("?");
							fieldNames.add(fields[i].getName());
							args.add(fieldValue);
						} else {
							sb.append("?");
							fieldNames.add(fields[i].getName());
							args.add(fieldValue);
						}
					}
				}
				allArgs.add(args.toArray());
			} else {
				List<Object> args = new ArrayList<Object>();
				for (String fieldName : fieldNames) {
					Field field;
					try {
						field = entities.get(k).getClass().getDeclaredField(fieldName);
					} catch (NoSuchFieldException e) {
						throw new RuntimeException(e);
					} catch (SecurityException e) {
						throw new RuntimeException(e);
					}
					Object fieldValue = getFieldValue(entities.get(k), field);
					String fieldTypeName = field.getType().getName();
					if (field.getAnnotation(PrimaryKey.class) != null) {
						if (fieldValue == null || fieldValue.equals("")) {
							throw new PlatformException(
									"update error : sql: " + sb.toString() + " is null : primary key is null");
						}
					}
					if (fieldTypeName.equals("java.lang.String") && fieldValue != null && fieldValue.equals("")) {
						args.add(null);
					} else {
						args.add(fieldValue);
					}
				}
				allArgs.add(args.toArray());
			}
		}
		return new SQLAndBatchArgs(sb.toString(), allArgs);
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
