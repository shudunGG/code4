package com.hanweb.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 获取工作日的工具类
 * @author linzhibo
 *
 */
public class WorkdayUtil {
	
	//法律规定的放假日期
    private String lawHolidays;
    //由于放假需要额外工作的周末
    private String extraWorkdays;
    
	public WorkdayUtil(String lawHolidays, String extraWorkdays) {
		super();
		this.lawHolidays = lawHolidays;
		this.extraWorkdays = extraWorkdays;
	}

	/**
    * @author  lzb
    * @description  判断是否是法定假日
    * @date Created in 21:03 2019/1/31
    **/
    public boolean isLawHoliday(String calendar) throws Exception {
    	
    	WorkdayUtil.isValidDate(calendar);
        if (lawHolidays.contains(calendar)) {
            return true;
        }
        return false;
    }
 
    /**
    * @author lzb
    * @description  判断是否是周末
    * @date Created in 21:03 2019/1/31
    **/
    public boolean isWeekends(String calendar) throws Exception {
    	WorkdayUtil.isValidDate(calendar);
        // 先将字符串类型的日期转换为Calendar类型
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
        Date  date = sdf.parse(calendar);
        Calendar         ca   = Calendar.getInstance();
        ca.setTime(date);
        if (ca.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || ca.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }
 
    /**
    * @author  lzb
    * @description  判断是否是需要额外补班的周末
    * @date Created in 21:06 2019/1/31
    **/
    public boolean isExtraWorkday(String calendar) throws Exception {
    	WorkdayUtil.isValidDate(calendar);
        if (extraWorkdays.contains(calendar)) {
            return true;
        }
        return false;
    }
 
    /**
    * @author  lzb
    * @description  判断是否是休息日（包含法定节假日和不需要补班的周末）
    * @date Created in 21:06 2019/1/31
    **/
    public boolean isHoliday(String calendar) throws Exception {
    	WorkdayUtil.isValidDate(calendar);
        // 首先法定节假日必定是休息日
        if (this.isLawHoliday(calendar)) {
            return true;
        }
        // 排除法定节假日外的非周末必定是工作日
        if (!this.isWeekends(calendar)) {
            return false;
        }
        // 所有周末中只有非补班的才是休息日
        if (this.isExtraWorkday(calendar)) {
            return false;
        }
        return true;
    }
 
    /**
    * @author  lzb
    * @description  校验字符串是否为指定的日期格式,含逻辑严格校验,2007/02/30返回false
    * @date Created in 21:06 2019/1/31
    **/
    private static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy-MM-dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (Exception e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }
  
}
