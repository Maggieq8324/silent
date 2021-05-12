package pms.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateHelper {
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日　HH时mm分ss秒");
	
	public static int dateAdd(int date,int addday) throws BusinessException{
		String str = String.valueOf(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		try {
		    Date myDate = formatter.parse(str);
		    Calendar c = Calendar.getInstance();
		    c.setTime(myDate);
		    c.add(Calendar.DAY_OF_MONTH, addday);
		    myDate = c.getTime();
		    //System.out.println(formatter.format(myDate));
		    
		    return Integer.parseInt(formatter.format(myDate));
		} catch (ParseException e1) {
		    throw new BusinessException("加减日期格式时出现日期解析错误：日期为："+date+",增加天数："+addday);
		} catch (NumberFormatException e1){
			throw new BusinessException("加减日期格式时出现数字转换错误：日期为："+date+",增加天数："+addday);
		}
	}
	
	public static String formatZhDataTime(Date datetime){
		return formatter.format(datetime);
	}
	
	public static Date DateTimeAdd(Date date,int addday) throws BusinessException{
		try {
		    
		    Calendar c = Calendar.getInstance();
		    c.setTime(date);
		    c.add(Calendar.DAY_OF_MONTH, addday);
		    date = c.getTime();
		    
		    return date;
		} catch (NumberFormatException e){
			throw new BusinessException("加减日期格式时出现数字转换错误：日期为："+date+",增加天数："+addday);
		}
	}
}
