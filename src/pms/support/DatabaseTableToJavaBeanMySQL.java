package pms.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DatabaseTableToJavaBeanMySQL {

	private static final String LINE = "\r\n";
	private static final String TAB = "\t";
	private static final String PACKAGE = "pms.entity";
	private static final String VIEW_PACKAGE = "pms.entity.view";
	private static final String ENTITY = "import com.platform.dataaccess.support.Entity;";
	private static final String TABLE = "import com.platform.dataaccess.support.Table;";
	private static final String VIEW = "import com.platform.dataaccess.support.View;";
	private static final String PRIMARY_KEY = "import com.platform.dataaccess.support.PrimaryKey;";
	private static final String AUTO_INCREMENT = "import com.platform.dataaccess.support.AutoIncrement;";
	private static final Map<String, String> MAP = new HashMap<String, String>();

	static {
		// MYSQL
		MAP.put("VARCHAR", "String");
		MAP.put("DECIMAL", "Integer");
		MAP.put("DECIMALL", "Long");
		MAP.put("DECIMAL.", "BigDecimal");
		MAP.put("INT", "Integer");
		MAP.put("BIGINT", "Long");
		MAP.put("DATE", "Date");
		MAP.put("TIME", "Date");
		MAP.put("DATETIME", "Date");
		// ORACLE
		MAP.put("NUMBER", "Integer");
		MAP.put("NUMBERL", "Long");
		MAP.put("NUMBER.", "BigDecimal");
		MAP.put("VARCHAR2", "String");
		// SQLSERVER
		MAP.put("numeric", "Integer");
		MAP.put("numericL", "Long");
		MAP.put("numeric.", "BigDecimal");
		MAP.put("varchar", "String");
		MAP.put("nvarchar", "String");
		MAP.put("decimal", "BigDecimal");
		MAP.put("int", "Integer");
		MAP.put("tinyint", "Integer");
		MAP.put("bigint", "Long");
		MAP.put("varbinary", "byte[]");
	}

	private static void tableToBean(Connection connection, String schemaPattern, String tableName, String schema) throws SQLException {
		String sql = "select * from " + tableName + " where 1 <> 1";
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet tablePkRs = null;
		try {
		ps = connection.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		StringBuffer sb = new StringBuffer();
		// String tableClassName = tableName;// .toLowerCase();
		String tableClassName = tableName.toLowerCase();
		tableClassName = tableClassName.substring(0, 1).toUpperCase()
				+ tableClassName.subSequence(1, tableClassName.length());
		tableClassName = dealLine(tableClassName);
		sb.append("package " + PACKAGE + ";");
		sb.append(LINE);
		importPackage(md, columnCount, sb);
		sb.append(LINE);
		if (sb.toString().indexOf(ENTITY) == -1) {
			sb.append(ENTITY);
			sb.append(LINE);
		}
		if (sb.toString().indexOf(TABLE) == -1) {
			sb.append(TABLE);
			sb.append(LINE);
		}
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		tablePkRs = databaseMetaData.getPrimaryKeys(null, schemaPattern, tableName);
		List<String> ls = new ArrayList<String>();
		while (tablePkRs.next()) {
			if (sb.toString().indexOf(PRIMARY_KEY) == -1) {
				sb.append(PRIMARY_KEY);
				sb.append(LINE);
			}
			ls.add(tablePkRs.getString(4));
		}
		if (ls.isEmpty()) {
			System.out.println("WARN:  " + tableClassName + "没有主键");
		}
		for (int i = 1; i <= columnCount; i++) {
			if (md.isAutoIncrement(i)) {
				if (sb.toString().indexOf(AUTO_INCREMENT) == -1) {
					sb.append(AUTO_INCREMENT);
					sb.append(LINE);
				}
				break;
			}
		}
		sb.append(LINE);
		sb.append("@Table(name = \"" + tableName + "\")" + getTableRemarks(connection, tableName, schema));
		sb.append(LINE);
		sb.append("public class " + tableClassName + " implements Entity {");
		sb.append(LINE);
		sb.append(LINE);
		defProperty(connection, tableName, md, columnCount, ls, sb, "Table", schema);
		defConstructor(tableClassName, sb);
		genGetSet(md, columnCount, sb);
		sb.append(LINE);
		sb.append("}");
		sb.append(LINE);
		String paths = System.getProperty("user.dir");
		String endPath = paths +File.separator+ "src"+File.separator + (PACKAGE.replace("/", "\\")).replace(".", "\\");
		buildJavaFile(endPath +File.separator + tableClassName + ".java", sb.toString());
		System.out.println("生成" + tableClassName + ".java文件成功");
		} finally {
			if(tablePkRs != null){tablePkRs.close();}
			if(rs != null ){rs.close();}
			
		}
	}

	private static void viewToBean(Connection connection, String schemaPattern, String viewName, String schema) throws SQLException {
		String sql = "select * from " + viewName + " where 1 <> 1";
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = connection.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		StringBuffer sb = new StringBuffer();
		// String viewClassName = viewName;// .toLowerCase();
		String viewClassName = viewName.toLowerCase();
		viewClassName = viewClassName.substring(0, 1).toUpperCase()
				+ viewClassName.subSequence(1, viewClassName.length());
		viewClassName = dealLine(viewClassName);
		sb.append("package " + VIEW_PACKAGE + ";");
		sb.append(LINE);
		importPackage(md, columnCount, sb);
		sb.append(LINE);
		if (sb.toString().indexOf(ENTITY) == -1) {
			sb.append(ENTITY);
			sb.append(LINE);
		}
		if (sb.toString().indexOf(VIEW) == -1) {
			sb.append(VIEW);
			sb.append(LINE);
		}
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet viewPkRs = databaseMetaData.getPrimaryKeys(null, schemaPattern, viewName);
		List<String> ls = new ArrayList<String>();
		while (viewPkRs.next()) {
			if (sb.toString().indexOf(PRIMARY_KEY) == -1) {
				sb.append(PRIMARY_KEY);
				sb.append(LINE);
			}
			ls.add(viewPkRs.getString(4));
		}
		for (int i = 1; i <= columnCount; i++) {
			if (md.isAutoIncrement(i)) {
				if (sb.toString().indexOf(AUTO_INCREMENT) == -1) {
					sb.append(AUTO_INCREMENT);
					sb.append(LINE);
				}
				break;
			}
		}
		sb.append(LINE);
		sb.append("@View(name = \"" + viewName + "\")");
		sb.append(LINE);
		sb.append("public class " + viewClassName + " implements Entity {");
		sb.append(LINE);
		sb.append(LINE);
		defProperty(connection, viewName, md, columnCount, ls, sb, "View", schema);
		defConstructor(viewClassName, sb);
		genGetSet(md, columnCount, sb);
		sb.append(LINE);
		sb.append("}");
		sb.append(LINE);
		String paths = System.getProperty("user.dir");
		String endPath = paths + File.separator+"src"+File.separator + (VIEW_PACKAGE.replace("/", "\\")).replace(".", "\\");
		buildJavaFile(endPath + File.separator + viewClassName + ".java", sb.toString());
		viewPkRs.close();
		rs.close();
		System.out.println("生成视图" + viewClassName + ".java文件成功");
	}

	private static void importPackage(ResultSetMetaData md, int columnCount, StringBuffer sb) throws SQLException {
		boolean isImport = false;
		for (int i = 1; i <= columnCount; i++) {
			String columnType = md.getColumnTypeName(i);
			if (md.getScale(i) != 0 && (columnType.equals("NUMBER") || columnType.equals("NUMERIC")
					|| columnType.equals("numeric") || columnType.equals("DECIMAL") || columnType.equals("decimal"))) {
				columnType = columnType + ".";
			} else if (md.getPrecision(i) > 9 && (columnType.equals("NUMBER") || columnType.equals("NUMERIC")
					|| columnType.equals("numeric") || columnType.equals("DECIMAL") || columnType.equals("decimal"))) {
				columnType = columnType + "L";
			}
			String im = getPojoType(columnType);
			if (im != null && !isImport) {
				if (im.equals("BigDecimal") || im.equals("Date")) {
					sb.append(LINE);
					isImport = true;
				}
			}
		}
		for (int i = 1; i <= columnCount; i++) {
			String columnType = md.getColumnTypeName(i);
			if (md.getScale(i) != 0 && (columnType.equals("NUMBER") || columnType.equals("NUMERIC")
					|| columnType.equals("numeric") || columnType.equals("DECIMAL") || columnType.equals("decimal"))) {
				columnType = columnType + ".";
			} else if (md.getPrecision(i) > 9 && (columnType.equals("NUMBER") || columnType.equals("NUMERIC")
					|| columnType.equals("numeric") || columnType.equals("DECIMAL") || columnType.equals("decimal"))) {
				columnType = columnType + "L";
			}
			String im = getPojoType(columnType);
			if (im != null && im.equals("BigDecimal")) {
				if (sb.toString().indexOf("import java.math.BigDecimal;") == -1) {
					sb.append("import java.math.BigDecimal;");
					sb.append(LINE);
				}
			}
			if (im != null && im.equals("Date")) {
				if (sb.toString().indexOf("import java.util.Date;") == -1) {
					sb.append("import java.util.Date;");
					sb.append(LINE);
				}
			}
		}
	}

	private static void defProperty(Connection connection, String tableName, ResultSetMetaData md, int columnCount,
			List<String> ls, StringBuffer sb, String type, String schema) throws SQLException {
		for (int i = 1; i <= columnCount; i++) {
			for (String pkColumn : ls) {
				if (pkColumn.equals(md.getColumnName(i))) {
					sb.append(TAB);
					sb.append("@PrimaryKey");
					sb.append(LINE);
					break;
				}
			}
			if (md.isAutoIncrement(i)) {
				sb.append(TAB);
				sb.append("@AutoIncrement");
				sb.append(LINE);
			}
			sb.append(TAB);
			String columnName = dealLine(md, i);
			String columnType = md.getColumnTypeName(i);
			if (md.getScale(i) != 0 && (columnType.equals("NUMBER") || columnType.equals("NUMERIC")
					|| columnType.equals("numeric") || columnType.equals("DECIMAL") || columnType.equals("decimal"))) {
				columnType = columnType + ".";
			} else if (md.getPrecision(i) > 9 && (columnType.equals("NUMBER") || columnType.equals("NUMERIC")
					|| columnType.equals("numeric") || columnType.equals("DECIMAL") || columnType.equals("decimal"))) {
				columnType = columnType + "L";
			}
			if (type.equals("Table")) {
				sb.append("private " + getPojoType(columnType) + " " + columnName + ";"
						+ getColumnRemarks(connection, tableName, md.getColumnName(i),schema));
			} else {
				sb.append("private " + getPojoType(columnType) + " " + columnName + ";");
			}
			sb.append(LINE);
		}
	}

	private static void defConstructor(String tableClassName, StringBuffer sb) {
		sb.append(LINE);
		sb.append(TAB);
		sb.append("public " + tableClassName + "() {");
		sb.append(LINE);
		sb.append(TAB);
		sb.append("}");
		sb.append(LINE);
	}

	private static void genGetSet(ResultSetMetaData md, int columnCount, StringBuffer sb) throws SQLException {
		for (int i = 1; i <= columnCount; i++) {
			String columnType = md.getColumnTypeName(i);
			if (md.getScale(i) != 0 && (columnType.equals("NUMBER") || columnType.equals("NUMERIC")
					|| columnType.equals("numeric") || columnType.equals("DECIMAL") || columnType.equals("decimal"))) {
				columnType = columnType + ".";
			} else if (md.getPrecision(i) > 9 && (columnType.equals("NUMBER") || columnType.equals("NUMERIC")
					|| columnType.equals("numeric") || columnType.equals("DECIMAL") || columnType.equals("decimal"))) {
				columnType = columnType + "L";
			}
			String pojoType = getPojoType(columnType);
			String columnName = dealLine(md, i);
			String getName = null;
			String setName = null;
			if (columnName.length() > 1) {
				getName = "public " + pojoType + " get" + columnName.substring(0, 1).toUpperCase()
						+ columnName.substring(1, columnName.length()) + "() {";
				setName = "public void set" + columnName.substring(0, 1).toUpperCase()
						+ columnName.substring(1, columnName.length()) + "(" + pojoType + " " + columnName + ") {";
			} else {
				getName = "public get" + columnName.toUpperCase() + "() {";
				setName = "public set" + columnName.toUpperCase() + "(" + pojoType + " " + columnName + ") {";
			}
			sb.append(LINE).append(TAB).append(getName);
			sb.append(LINE).append(TAB).append(TAB);
			sb.append("return this." + columnName + ";");
			sb.append(LINE).append(TAB).append("}");
			sb.append(LINE);
			sb.append(LINE).append(TAB).append(setName);
			sb.append(LINE).append(TAB).append(TAB);
			sb.append("this." + columnName + " = " + columnName + ";");
			sb.append(LINE).append(TAB).append("}");
			sb.append(LINE);
		}
	}

	private static String getPojoType(String dataType) {
		if (MAP.get(dataType) == null || MAP.get(dataType).equals("")) {
			return null;
		} else {
			return MAP.get(dataType);
		}
	}

	private static String dealLine(ResultSetMetaData md, int i) throws SQLException {
		String columnName = md.getColumnName(i);
		// columnName = columnName.toLowerCase();
		columnName = columnName.toLowerCase();
		columnName = dealName(columnName);
		return columnName;
	}

	private static String dealLine(String tableName) {
		tableName = dealName(tableName);
		return tableName;
	}

	private static String dealName(String columnName) {
		if (columnName.contains("_")) {
			StringBuffer names = new StringBuffer();
			String arrayName[] = columnName.split("_");
			names.append(arrayName[0]);
			for (int i = 1; i < arrayName.length; i++) {
				String arri = arrayName[i];
				String tmp = arri.substring(0, 1).toUpperCase() + arri.substring(1, arri.length());
				names.append(tmp);
			}
			columnName = names.toString();
		}
		return columnName;
	}

	private static void buildJavaFile(String filePath, String fileContent) {
		try {
			File file = new File(filePath);
			FileOutputStream osw = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(osw);
			pw.print(fileContent);
			pw.close();
			osw.close();
		} catch (Exception e) {
			System.out.println("生成java文件出错：" + e.getMessage());
		}
	}

	private static String getTableRemarks(Connection connection, String tableName, String schema) throws SQLException {
		String returnString = null;
		String sql = "select table_comment" + " from information_schema.tables" + " where table_schema = '"+schema+"'"
				+ " and table_name = '" + tableName + "'";
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		while (rs.next()) {
			returnString = rs.getString(1);
		}
		rs.close();
		stat.close();
		if (returnString == null || returnString.equals("") || returnString.equals("null")
				|| returnString.equals("NULL")) {
			return "";
		} else {
			return " // " + returnString.replaceAll("\n", "\n	// ");
		}
	}

	private static String getColumnRemarks(Connection connection, String tableName, String columnName, String schema)
			throws SQLException {
		String returnString = null;
		String sql = "select column_comment" + " from information_schema.columns" + " where table_schema = '"+schema+"'"
				+ " and table_name = '" + tableName + "' and column_name = '" + columnName + "'";
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		while (rs.next()) {
			returnString = rs.getString(1);
		}
		rs.close();
		stat.close();
		if (returnString == null || returnString.equals("") || returnString.equals("null")
				|| returnString.equals("NULL")) {
			return "";
		} else {
			return " // " + returnString.replaceAll("\n", "\n	// ");
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// MYSQL
		String jdbcString = "jdbc:mysql://localhost/silent";
		String schema = "silent";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(jdbcString, "root", "sunday");
		DatabaseMetaData databaseMetaData = con.getMetaData();
		ResultSet rsTable = databaseMetaData.getTables(null, null, "%", new String[] { "TABLE" });
		while (rsTable.next()) {
			String tableName = rsTable.getString(3).toString();
			DatabaseTableToJavaBeanMySQL.tableToBean(con, null, tableName, schema);
		}
		rsTable.close();
		ResultSet rsView = databaseMetaData.getTables(null, null, "%", new String[] { "VIEW" });
		while (rsView.next()) {
			String viewName = rsView.getString(3).toString();
			DatabaseTableToJavaBeanMySQL.viewToBean(con, null, viewName, schema);
		}
		rsView.close();
		con.close();
	}

}
