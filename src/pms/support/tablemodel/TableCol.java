package pms.support.tablemodel;

public class TableCol {
	public static final int CLASS_STRING=1;
	public static final int CLASS_BIGDECIMAL=2;
	public static final int CLASS_INTEGER=3;
	public static final int CLASS_LONG=4;
	public static final int CLASS_DATE=5;
	public static final int CLASS_DATETIME=6;
	public static final int CLASS_YEAR_MOUTH=7;
	
	public static final int TYPE_VARCHAR=1;
	public static final int TYPE_DECIMAL=2;
	public static final int TYPE_INT=3;
	public static final int TYPE_BIGINT=4;
	public static final int TYPE_DATETIME=5;
	
	public static final String STRING_TYPE_VARCHAR="VARCHAR";
	public static final String STRING_TYPE_DECIMAL="DECIMAL";
	public static final String STRING_TYPE_INT="INT";
	public static final String STRING_TYPE_BIGINT="BIGINT";
	public static final String STRING_TYPE_DATETIME="DATETIME";
	
	private String colcode;//数据库英文字段名
	private String colname;//数据库字段中文说明
	private int coltype;//字段在数据库中的类型
	private int collength;//整数位长度
	private int colposition;//小数位长度
	private boolean isnotnull;//是否为空
	private boolean isdm;//是否是代码表
	private boolean ispk;//是否主键
	private boolean isbz;//是否标志
	private int colclass;//字段在java中的类型
	
	
	public boolean isIsbz() {
		return isbz;
	}

	public void setIsbz(boolean isbz) {
		this.isbz = isbz;
	}

	
	public String getUpperCaseDMname(){
		//if (colcode.indexOf("dm") > 0){
		//	return colcode.substring(0, colcode.indexOf("dm")).toUpperCase();
		//} else {
			return colcode.toUpperCase();
		//}
	}
	
	public boolean isString(){
		return (colclass == CLASS_STRING);
	}
	
	public boolean isBigdecimal(){
		return (colclass == CLASS_BIGDECIMAL);
	}
	
	public boolean isInteger(){
		return (colclass == CLASS_INTEGER);
	}
	
	public boolean isLong(){
		return (colclass == CLASS_LONG);
	}	
	
	public boolean isDateTime(){
		return (colclass == CLASS_DATETIME);
	}	
	
	public boolean isYearMouth(){
		return (colclass == CLASS_YEAR_MOUTH);
	}
	
	
	public boolean isDate(){
		return (colclass == CLASS_DATE);
	}	
	
	public String getColcode() {
		return colcode;
	}
	public void setColcode(String colcode) {
		this.colcode = colcode.toLowerCase();
	}
	public String getColname() {
		return colname;
	}
	public void setColname(String colname) {
		this.colname = colname;
	}
	public int getColtype() {
		return coltype;
	}
	public void setColtype(int coltype) {
		this.coltype = coltype;
	}
	public int getCollength() {
		return collength;
	}
	public int getHalfCollength(){
		return collength/2;
	}
	
	public void setCollength(int collenth) {
		this.collength = collenth;
	}
	public int getColposition() {
		return colposition;
	}
	public void setColposition(int colposition) {
		this.colposition = colposition;
	}
	public boolean isIsnotnull() {
		return isnotnull;
	}
	public void setIsnotnull(boolean isnotnull) {
		this.isnotnull = isnotnull;
	}
	public boolean isIsdm() {
		return isdm;
	}
	public void setIsdm(boolean isdm) {
		this.isdm = isdm;
	}
	public boolean isIspk() {
		return ispk;
	}
	public void setIspk(boolean ispk) {
		this.ispk = ispk;
	}
	public int getColclass() {
		return colclass;
	}
	public void setColclass(int colclass) {
		this.colclass = colclass;
	}
	
}
