package pms.support;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.platform.dataaccess.support.Entity;

import java.lang.reflect.InvocationTargetException;

public final class BeanHelper {

	private BeanHelper() {
	}

	public static void copyBeanProperties(Object dest, Object orig) {
		Field[] origFields = orig.getClass().getDeclaredFields();
		for (Field origField : origFields) {
			Class<?> origFieldClass = origField.getType();
			Object value = getFieldValue(orig, origField.getName());
			setFieldValue(dest, origFieldClass, origField.getName(), value);
		}
	}

	public static <T extends Entity> List<T> entitiesMemoryQuery(T queryEntity, List<T> entityList) {
		List<T> resultEntityList = new ArrayList<T>();
		for (T entity : entityList) {
			int queryCount = 0; // 查询条件有效字段个数
			int count = 0; // 当前实体能匹配的查询条件个数
			Field[] queryFields = queryEntity.getClass().getDeclaredFields();
			for (int i = 0; i < queryFields.length; i++) {
				String queryFieldName = queryFields[i].getName();
				Object queryFieldValue = getFieldValue(queryEntity, queryFieldName);
				if (queryFieldValue != null && !queryFieldValue.equals("")) {
					queryCount++;
					Field field;
					try {
						field = entity.getClass().getDeclaredField(queryFieldName);
					} catch (NoSuchFieldException e) {
						throw new RuntimeException(e);
					} catch (SecurityException e) {
						throw new RuntimeException(e);
					}
					String fieldName = field.getName();
					Object fieldValue = getFieldValue(entity, fieldName);
					if (queryFieldValue.equals(fieldValue)) {
						count++;
					}
				}
			}
			if (queryCount != 0 && queryCount == count) {
				resultEntityList.add(entity);
			}
		}
		return resultEntityList;
	}

	public static <T extends Entity> T entityMemoryQuery(T queryEntity, List<T> entityList) {
		for (T entity : entityList) {
			int queryCount = 0; // 查询条件有效字段个数
			int count = 0; // 当前实体能匹配的查询条件个数
			Field[] queryFields = queryEntity.getClass().getDeclaredFields();
			for (int i = 0; i < queryFields.length; i++) {
				String queryFieldName = queryFields[i].getName();
				Object queryFieldValue = getFieldValue(queryEntity, queryFieldName);
				if (queryFieldValue != null && !queryFieldValue.equals("")) {
					queryCount++;
					Field field;
					try {
						field = entity.getClass().getDeclaredField(queryFieldName);
					} catch (NoSuchFieldException e) {
						throw new RuntimeException(e);
					} catch (SecurityException e) {
						throw new RuntimeException(e);
					}
					String fieldName = field.getName();
					Object fieldValue = getFieldValue(entity, fieldName);
					if (queryFieldValue.equals(fieldValue)) {
						count++;
					}
				}
			}
			if (queryCount != 0 && queryCount == count) {
				return entity;
			}
		}
		return null;
	}

	private static Object getFieldValue(Object orig, String fieldName) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String getter = "get" + firstLetter + fieldName.substring(1);
		Method method;
		try {
			method = orig.getClass().getMethod(getter, new Class[] {});
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		Object value;
		try {
			value = method.invoke(orig, new Object[] {});
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		return value;
	}

	private static void setFieldValue(Object dest, Class<?> fieldClass, String fieldName, Object value) {
		try {
			dest.getClass().getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			return;
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String setter = "set" + firstLetter + fieldName.substring(1);
		Method method;
		try {
			method = dest.getClass().getMethod(setter, new Class[] { fieldClass });
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		try {
			method.invoke(dest, new Object[] { value });
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private static Object getFieldValue(Entity entity, String fieldName) {
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
		return value;
	}

}
