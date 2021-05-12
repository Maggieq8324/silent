package pms.support.tablemodel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class TableModel {
	
	private List<TableCol> listTableCol = new ArrayList<TableCol>();
	private String tablename;
	private String tablecode;
	private String tableclass;
	
	private String _schema;
	
	private List<TableCol> listDatetime = new ArrayList<TableCol>();
	private List<TableCol> listDate = new ArrayList<TableCol>();
	private List<TableCol> listYearmouth = new ArrayList<TableCol>();
	private List<TableCol> listDM = new ArrayList<TableCol>();
	private List<TableCol> listBZ = new ArrayList<TableCol>();
	private List<String> listPK = new ArrayList<String>();
	
	public TableModel(String schema){
		_schema = schema;
	}
	
	
	public List<TableCol> getListBZ() {
		return listBZ;
	}
	public void setListBZ(List<TableCol> listBZ) {
		this.listBZ = listBZ;
	}
	public List<TableCol> getListTableCol() {
		return listTableCol;
	}
	public void setListTableCol(List<TableCol> listTableCol) {
		this.listTableCol = listTableCol;
	}
	public List<TableCol> getListDatetime() {
		return listDatetime;
	}
	public void setListDatetime(List<TableCol> listDatetime) {
		this.listDatetime = listDatetime;
	}
	public List<TableCol> getListDate() {
		return listDate;
	}
	public void setListDate(List<TableCol> listDate) {
		this.listDate = listDate;
	}
	public List<TableCol> getListYearmouth() {
		return listYearmouth;
	}
	public void setListYearmouth(List<TableCol> listYearmouth) {
		this.listYearmouth = listYearmouth;
	}
	public List<TableCol> getListDM() {
		return listDM;
	}
	public void setListDM(List<TableCol> listDM) {
		this.listDM = listDM;
	}
	public List<String> getListPK() {
		return listPK;
	}
	public void setListPK(List<String> listPK) {
		this.listPK = listPK;
	}



	public String getTableclass() {
		return tableclass;
	}
	public void setTableclass(String tableclass) {
		this.tableclass = tableclass;
	}
	public String getTablecode() {
		return tablecode;
	}
	public void setTablecode(String tablecode) {
		this.tablecode = tablecode;
	}



	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	
	public void addTableCol(TableCol tablecol) {
		listTableCol.add(tablecol);
	}
	
	public List<TableCol> getTableColList(){
		return listTableCol;
	}
	
	public void addPK(String pkname){
		listPK.add(pkname);
	}
	
	public List<String> getPKList(){
		return listPK;
	}
	
	public void parseTableModel(String tablename, Connection connection) throws SQLException {
		String sql = "select * from " + tablename + " where 1 <> 1";
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = connection.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData md = rs.getMetaData();
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		
		int columnCount = md.getColumnCount();
		
		//格式化tableName
		String tableClassName = tablename.toLowerCase();
		tableClassName = tableClassName.substring(0, 1).toUpperCase()
				+ tableClassName.subSequence(1, tableClassName.length());
		tableClassName = dealLine(tableClassName);
		this.setTableclass(tableClassName);
		this.setTablecode(tablename);
		this.setTablename(getTableRemarks(connection, tablename));
		
		
		
		ResultSet tablePkRs = databaseMetaData.getPrimaryKeys(null, null, tablename);
		
		while (tablePkRs.next()) {
			String strPK = tablePkRs.getString(4);
			listPK.add(strPK.toLowerCase());
			//System.out.println("strPK:"+strPK);
		}
		
		for (int i = 1; i <= columnCount; i++){
			String colname = md.getColumnName(i);
			String coltype = md.getColumnTypeName(i);
			String collabel = md.getColumnLabel(i);
			String colcatalog = md.getCatalogName(i);
			String colcolclassname = md.getColumnClassName(i);
			String colschema = md.getSchemaName(i);
			int colcds = md.getColumnDisplaySize(i);
			int colpre = md.getPrecision(i);
			int colScale = md.getScale(i);
//			int colisnull = md.isNullable(i);
			
			String colremark = getColumnRemarks(connection, tablename, colname);
			
//			System.out.println("md.getColumnName(i):"+colname);
//			System.out.println("md.getColumnTypeName(i);:"+coltype);
//			System.out.println("md.getColumnLabel(i):"+collabel);
//			System.out.println("md.getCatalogName(i):"+colcatalog);
//			System.out.println("md.getColumnClassName(i):"+colcolclassname);
//			System.out.println("md.getSchemaName(i):"+colschema);
//			System.out.println("md.getColumnDisplaySize(i):"+colcds);
//			System.out.println("md.getPrecision(i):"+colpre);
//			System.out.println("md.getScale(i):"+colScale);
//			System.out.println("colremark:"+colremark);
//			System.out.println("colisnull:"+colisnull);
			
			TableCol tc = new TableCol();
			listTableCol.add(tc);
			
			tc.setColcode(colname);
			tc.setColname(colremark);
			tc.setCollength(colpre);
			tc.setColposition(colScale);
			tc.setIsnotnull(true);
			tc.setIsbz(false);
			tc.setIsdm(false);
			if (listPK.contains(colname)){
				tc.setIspk(true);
			} else {
				tc.setIspk(false);
			}
			
			switch(coltype){
				case  TableCol.STRING_TYPE_VARCHAR :{
					tc.setColtype(TableCol.TYPE_VARCHAR);
					tc.setColclass(TableCol.CLASS_STRING);
					if (colname.toLowerCase().endsWith("dm") || colname.toLowerCase().equals("zzbm") || colname.toLowerCase().equals("gddwbm") || colname.toLowerCase().equals("sccjbh") || colremark.indexOf("代码") > 0){
						tc.setIsdm(true);
						listDM.add(tc);
						if (colremark.indexOf("代码") > 0){
							String colremark_samp = colremark.substring(0, colremark.indexOf("代码"));
							tc.setColname(colremark_samp);
						}
						
						//System.out.println("colname:"+colname);
					}else if (colname.toLowerCase().endsWith("bz")){
						tc.setIsbz(true);
						
						if (colname.toLowerCase().endsWith("bz") && (colremark.indexOf("（") > 0 || colremark.indexOf("(") > 0)){
							if (colremark.indexOf("（") > 0){
								String colremark_samp = colremark.substring(0, colremark.indexOf("（"));
								tc.setColname(colremark_samp);
							}
							
							if (colremark.indexOf("(") > 0){
								String colremark_samp = colremark.substring(0, colremark.indexOf("("));
								tc.setColname(colremark_samp);
							}
							
						}
						
						listBZ.add(tc);
					}
					break;
				}
				case TableCol.STRING_TYPE_INT : {
					tc.setColtype(TableCol.TYPE_INT);
					tc.setColclass(parseType(colremark, tc));
					if (colname.toLowerCase().endsWith("dm") || colname.toLowerCase().equals("zzbm") || colname.toLowerCase().equals("gddwbm") || colname.toLowerCase().equals("sccjbh") || colremark.indexOf("代码") > 0){
						tc.setIsdm(true);
						listDM.add(tc);
						if (colremark.indexOf("代码") > 0){
							String colremark_samp = colremark.substring(0, colremark.indexOf("代码"));
							tc.setColname(colremark_samp);
						}
						
						//System.out.println("colname:"+colname);
					}else if (colname.toLowerCase().endsWith("bz")){
						tc.setIsbz(true);
						
						if (colname.toLowerCase().endsWith("bz") && (colremark.indexOf("（") > 0 || colremark.indexOf("(") > 0)){
							if (colremark.indexOf("（") > 0){
								String colremark_samp = colremark.substring(0, colremark.indexOf("（"));
								tc.setColname(colremark_samp);
							}
							
							if (colremark.indexOf("(") > 0){
								String colremark_samp = colremark.substring(0, colremark.indexOf("("));
								tc.setColname(colremark_samp);
							}
							
						}
						
						listBZ.add(tc);
					}
					break;
				}
				case TableCol.STRING_TYPE_BIGINT : {
					tc.setColtype(TableCol.TYPE_BIGINT);
					tc.setColclass(parseType(colremark, tc));
					break;
				}
				case TableCol.STRING_TYPE_DECIMAL : {
					tc.setColtype(TableCol.TYPE_DECIMAL);
					tc.setColclass(TableCol.CLASS_BIGDECIMAL);
					break;
				}
				case TableCol.STRING_TYPE_DATETIME : {
					tc.setColtype(TableCol.TYPE_DATETIME);
					tc.setColclass(TableCol.CLASS_DATETIME);
					listDatetime.add(tc);
					break;
				}
			}
		}

		tablePkRs.close();
		rs.close();
		
	}
	
	public void genJsp(VelocityEngine ve, Template t, String jsproot, String modelname, String filename) throws ResourceNotFoundException, ParseErrorException, Exception {
		String jspforldname = jsproot+modelname+"\\";
		
		File jspforld = new File(jspforldname);
		if (!jspforld.exists()){
			jspforld.mkdirs();
		}
		
		String filepath = jspforldname+filename;
		
		VelocityContext ctx = new VelocityContext();
		
		ctx.put("tablemodel", this);
		ctx.put("modelname", modelname);
		
		File file = new File(filepath);
		FileOutputStream osw = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(osw);
		
		t.merge(ctx, pw);
		
		pw.close();
		osw.close();
	}
	
	public void genSql(VelocityEngine ve, Template t, String sqlroot, String modelname, String gnbh, String sjgnbh, String mksxh, String gnsxh, String gnmc) throws ResourceNotFoundException, ParseErrorException, Exception {
		String filepath = sqlroot+this.getTablecode()+".sql";
		
		VelocityContext ctx = new VelocityContext();
		
		ctx.put("tablemodel", this);
		ctx.put("gnbh", gnbh);
		ctx.put("sjgnbh", sjgnbh);
		ctx.put("mksxh", mksxh);
		ctx.put("gnsxh", gnsxh);
		ctx.put("modelname", modelname);
		ctx.put("gnmc", gnmc);
		
		File file = new File(filepath);
		FileOutputStream osw = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(osw);
		
		t.merge(ctx, pw);
		
		pw.close();
		osw.close();
	}
	
	public void genJava(VelocityEngine ve, Template t, String javaroot, String modelname,String forldname, String filename) throws ResourceNotFoundException, ParseErrorException, Exception {
		String jspforldname = javaroot+forldname+"\\";
		
		File jspforld = new File(jspforldname);
		if (!jspforld.exists()){
			jspforld.mkdirs();
		}
		
		String filepath = jspforldname+filename;
		
		VelocityContext ctx = new VelocityContext();
		
		ctx.put("tablemodel", this);
		ctx.put("modelname", modelname);
		
		String upcasemodelname = modelname.toLowerCase();
		upcasemodelname = upcasemodelname.substring(0, 1).toUpperCase()
				+ upcasemodelname.subSequence(1, upcasemodelname.length());
		upcasemodelname = dealLine(upcasemodelname);
		ctx.put("upcasemodelname", upcasemodelname);
		
		String upcasepk = listPK.get(0);
		upcasepk = upcasepk.toLowerCase();
		upcasepk = upcasepk.substring(0, 1).toUpperCase()
				+ upcasepk.subSequence(1, upcasepk.length());
		upcasepk = dealLine(upcasepk);
		ctx.put("upcasepk", upcasepk);
		
		String pk = listPK.get(0);
		ctx.put("pk", pk);
		
		
		File file = new File(filepath);
		FileOutputStream osw = new FileOutputStream(file);
		PrintWriter pw = new PrintWriter(osw);
		
		t.merge(ctx, pw);
		
		pw.close();
		osw.close();
	}
	
	public int parseType(String colremark, TableCol tc){
		if (colremark.indexOf("时间") > -1){
			listDatetime.add(tc);
			return TableCol.CLASS_DATETIME;
		} else if (colremark.indexOf("日期") > -1){
			listDate.add(tc);
			return TableCol.CLASS_DATE;
		} else if (colremark.indexOf("年月") > -1){
			listYearmouth.add(tc);
			return TableCol.CLASS_YEAR_MOUTH;
		} else {
			return TableCol.CLASS_INTEGER;
		}
	}
	
	private String getTableRemarks(Connection connection, String tableName) throws SQLException {
		String returnString = null;
		String sql = "select table_comment" + " from information_schema.tables" + " where table_schema = '"+_schema+"'"
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
			return returnString;
		}
	}
	
	private String getColumnRemarks(Connection connection, String tableName, String columnName)
			throws SQLException {
		String returnString = null;
		String sql = "select column_comment" + " from information_schema.columns" + " where table_schema = '"+_schema+"'"
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
			return returnString;
		}
	}
	
	private String dealLine(String tableName) {
		tableName = dealName(tableName);
		return tableName;
	}

	private String dealName(String columnName) {
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
	
	
}
