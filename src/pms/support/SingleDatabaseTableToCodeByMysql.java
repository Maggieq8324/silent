package pms.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import pms.support.tablemodel.TableModel;

public class SingleDatabaseTableToCodeByMysql {
	private static final String JSP_ROOT_FORLD = "D:"+File.separator+"dev"+File.separator+"workspace"+File.separator+"yxaqgl"+File.separator+"WebContent"+File.separator+"WEB-INF"+File.separator+"jsp"+File.separator;
	private static final String JAVA_ROOT_FORLD = "D:"+File.separator+"dev"+File.separator+"workspace"+File.separator+"yxaqgl"+File.separator+"src"+File.separator;
	private static final String SQL_ROOT_FORLD = "D:"+File.separator+"dev"+File.separator+"workspace"+File.separator+"yxaqgl"+File.separator;

	
//	private static final String JSP_ROOT_FORLD = "D:\\dev\\workspace\\pmspro_temp\\";
//	private static final String JAVA_ROOT_FORLD = "D:\\dev\\workspace\\pmspro_temp\\";
//	private static final String SQL_ROOT_FORLD = "D:\\dev\\workspace\\pmspro_temp\\";
	
	public static void main(String[] args) throws Exception {
		 VelocityEngine ve = new VelocityEngine();
		 ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		 ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		 
         Properties prop = new Properties();        
         prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
         prop.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
         prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");  
		 
		 ve.init(prop);
		 

		String jdbcString = "jdbc:mysql://localhost/yxaqgl";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(jdbcString, "root", "sunday");
		try {

		//小心不要覆盖已有程序		
		String modelname = "wzjlcx";
		
		String upcasemodelname = modelname.toLowerCase();

		upcasemodelname = upcasemodelname.substring(0, 1).toUpperCase()
						+ upcasemodelname.subSequence(1, upcasemodelname.length());
				
		TableModel tm = new TableModel("yxaqgl");
		tm.parseTableModel("wz_wzjl", con);
		
		Template t_service = ve.getTemplate("vm/singleDb/singleDb2Service.vm");
		tm.genJava(ve, t_service, JAVA_ROOT_FORLD, modelname,"pms\\service\\", upcasemodelname+"Service.java");
		
		Template t_controller = ve.getTemplate("vm/singleDb/singleDb2Controller.vm");
		tm.genJava(ve, t_controller, JAVA_ROOT_FORLD, modelname,"pms\\controller\\", upcasemodelname+"Controller.java");
		
		Template t_manager = ve.getTemplate("vm/singleDb/singleDb2Jsp_manager.vm");
		tm.genJsp(ve, t_manager, JSP_ROOT_FORLD, modelname, modelname+"Manager.jsp");
		
		Template t_add = ve.getTemplate("vm/singleDb/singleDb2Jsp_add.vm");
		tm.genJsp(ve, t_add, JSP_ROOT_FORLD, modelname, "add"+tm.getTableclass()+".jsp");
		
		Template t_update = ve.getTemplate("vm/singleDb/singleDb2Jsp_update.vm");
		tm.genJsp(ve, t_update, JSP_ROOT_FORLD, modelname, "update"+tm.getTableclass()+".jsp");
	
		Template t_view = ve.getTemplate("vm/singleDb/singleDb2Jsp_view.vm");
		tm.genJsp(ve, t_view, JSP_ROOT_FORLD, modelname, "view"+tm.getTableclass()+".jsp");
		
		Template t_sql = ve.getTemplate("vm/singleDb/singleDb2SQL.vm");
		tm.genSql(ve, t_sql, SQL_ROOT_FORLD, modelname, "1105","11","11","5","违章记录查询");
		
		} finally {
			con.close();
		}
		
	}
}
