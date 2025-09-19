package com.hanweb.zxyy.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.hanweb.bscx.entity.Member;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.util.Properties;
import com.hanweb.util.WorkdayUtil;
import com.hanweb.zxyy.dao.ZxyyItemDao;
import com.hanweb.zxyy.dao.ZxyyMessageDao;
import com.hanweb.zxyy.entity.ItemEntity;
import com.hanweb.zxyy.entity.ZxyyEntity;

public class ZxyyItemService {
	
	private static Properties properties = new Properties(BaseInfo.getRealPath() + "/WEB-INF/config/zxyy/zxyy.properties");
	
	@Autowired
	private ZxyyItemDao itemDao;
	
	@Autowired
	private ZxyyMessageDao messagedao;
	//封装日期的集合
	 private static List<String> list = new ArrayList<String>();
	 //创建日期工具类
	 WorkdayUtil util = new WorkdayUtil(properties.getString("lawHolidays"),properties.getString("extraWorkdays"));
	/**
	 * 根据事项id查询事项数据
	 * @param unid 事项主键
	 * @return
	 */
	public List<ItemEntity> getItem(String unid) {
		return itemDao.getItem(unid);
	}

	/**
	 * 将数据推送到本地项目库
	 * @param itemEntity
	 * @param member
	 * @param date
	 * @param houre
	 * @return
	 * @throws ParseException 
	 */
	public Integer add(ItemEntity itemEntity, Member member, String date, String houre) throws ParseException {
		ZxyyEntity entity = new ZxyyEntity();
		
		//1.封装姓名,个人账号为姓名，法人账号为企业名称
		entity.setName(member.getRealname());
		
		//2.封装手机号
		entity.setLinkphpne(member.getPhone());
		
		//3.封装所属机构
		entity.setDeptcode(itemEntity.getField_3665());
		
		//4.封装预约时间（预约时间是预约的是哪一天）
		entity.setOrderdatetime(new SimpleDateFormat("yyy-MM-dd").parse(date));
		
		//5.封装申请时间（申请时间是指什么时候预约的）
		entity.setAdddatetime(new Date());
		
		String type = member.getType();
		if ("1".equals(type)) {
			//6.封装身份证号
			entity.setCertid(member.getCardid());
		}else {
			//6.法人为企业信用代码
			entity.setCertid(member.getCorpCertNum());
		}
		
		//7.封装预约事项编码
		entity.setServcode(itemEntity.getField_3658());
		
		//8.封装用户名
		entity.setUsername(member.getLoginname());
		
		//9.封装预约来源
		entity.setOrdersource("张掖政务服务网");
		
		//10.封装事项uuid
		entity.setServid(itemEntity.getField_3651());
		
		//11.封装预约时间段
		entity.setAppointmenttime(houre);
		
		//12.封装状态
		entity.setStatus("未取号");
		
		//13.封装预约内容
		entity.setOrdercontext(itemEntity.getVc_name());
		Integer insert = messagedao.insert(entity);
		return insert;
	}
	/**
	 * 判断重复预约
	 * @param unid
	 * @param loginname
	 * @return
	 */
	public Integer zxyyCount(String unid,String loginname) {
		return messagedao.zxyyCount(unid,loginname);
	}
	
	/**
	 * 取消预约
	 * @param id 储存事项的数据库主键
	 * @return
	 */
	public Boolean cancel(ItemEntity itemEntity,Member member) {
		String field_3651 = itemEntity.getField_3651();
		String loginname = member.getLoginname();
		return messagedao.cancel(field_3651,loginname);
	}
	
	/**
	 * 预约次数统计
	 * @param unid 事项主键
	 * @return
	 */
	public Integer zxyyCountByunid(String unid) {
		return messagedao.zxyyCountByunid(unid);
	}
	/**
	 * 统计中午或者下午
	 * @param field_3651
	 * @param mark 标示
	 * @return
	 */
	public List<ZxyyEntity> countNoonOrAfternoon(String field_3651, String mark) {
		//统计中午
		if ("1".equals(mark)) {
		return messagedao.zxyyCountDate(field_3651,properties.getString("noon"));
		}else if("2".equals(mark)) {
		//统计下午
		return messagedao.zxyyCountDate(field_3651,properties.getString("afternoon"));
		}
		return null;
	}
	/**
	 * 获取工作日
	 * @return
	 * @throws Exception 
	 */
	public List<String> getworkdays() throws Exception {
		 Calendar instance = Calendar.getInstance();
    	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	 Date date = new Date();
    	 list.clear();
    	 for (int i = 0; i < 25; i++) {
    		 if (list.size()>6) {
  				break;
  			}
    		 String newdate = format.format(instance.getTime());
    		 if (!util.isHoliday(newdate) && newdate.equals(format.format(date))) {
    			 list.add(newdate);
			}
             //向后推日期
         	instance.add(Calendar.DATE, 1);
         	//封装向后推的日期
         	String format2 = format.format(instance.getTime());
     		if (!util.isHoliday(format2)) {
 				list.add(format2);
 			}	
 		}
    	 return list;
	}
}
