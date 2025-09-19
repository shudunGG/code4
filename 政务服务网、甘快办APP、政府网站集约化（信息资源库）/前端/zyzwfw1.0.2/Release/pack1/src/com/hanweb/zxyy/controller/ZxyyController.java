package com.hanweb.zxyy.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hanweb.bscx.entity.Item;
import com.hanweb.bscx.entity.Member;
import com.hanweb.bscx.service.ItemService;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.datasource.DataSourceSwitch;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.Properties;
import com.hanweb.common.util.StringUtil;
import com.hanweb.common.util.mvc.JsonResult;
import com.hanweb.complat.listener.MemberSessionInfo;
import com.hanweb.util.FilterUtil;
import com.hanweb.util.WorkdayUtil;
import com.hanweb.zxyy.entity.ItemEntity;
import com.hanweb.zxyy.entity.ZxyyEntity;
import com.hanweb.zxyy.service.ZxyyItemService;

@Controller
@RequestMapping("zxyy")
public class ZxyyController {
	@Autowired
	private ZxyyItemService zxyyItemService;
	
	@Autowired
	private ItemService itemService;
	
	private Properties properties = new Properties(BaseInfo.getRealPath() + "/WEB-INF/config/zxyy/zxyy.properties");
	
	/**
	 * 预约首页
	 * @param unid 事项主键
	 * @param session 储存用户数据和事项数据
	 * @param response 用于跳转
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index(String webid,String unid,HttpSession session,HttpServletResponse response,HttpServletRequest request) {
		//设置session过期时间
		session.setMaxInactiveInterval(1800);
		ModelAndView modelAndView = new ModelAndView();
		//数据校验
		if (StringUtil.isEmpty(webid) || StringUtil.isEmpty(unid) || !FilterUtil.isSqlOK(unid)) {
			RedirectView redirectView = new RedirectView(BaseInfo.getContextPath()+ "/error/error.do");
			modelAndView.setView(redirectView);
			return modelAndView;
		}
		try {
		Member member = MemberSessionInfo.getCurrentMemberInfo();
		//如果没有登陆，则跳转到登陆页面
		if (member == null) {
			session.setAttribute("zxtsaction", "zxyy");
			session.setAttribute("unid", unid);
			String serverName = request.getServerName();
			int localPort = request.getLocalPort();
			session.setAttribute("zxyyport", String.valueOf(localPort));
			session.setAttribute("zxyyservicename", serverName);
			session.setAttribute("zxyywebid", webid);
			response.sendRedirect(properties.getString("jisurl")+"?backUrl="+"http://"+serverName+":"+localPort+properties.getString("backurl"));
			return null;
		}else {
		//用户登陆，准备数据
			//1.准备事项数据
			DataSourceSwitch.change("2");
			List<ItemEntity> itemlists = zxyyItemService.getItem(unid);
			if (itemlists !=null && itemlists.size() >0) {
				ItemEntity itemEntity = itemlists.get(0);
				//封装事项数据
				modelAndView.addObject("itemEntity",itemEntity);
				//装入session
				session.setAttribute("itemEntity", itemEntity);
				//2.准备预约时间数据开始
				Calendar calendar = Calendar.getInstance();
				//获取工作日
				List<String> getworkdays = WorkdayUtil.getworkdays();
				session.setAttribute("workday", getworkdays);
				//封装日期
				modelAndView.addObject("datelist",getworkdays);
				//封装当前时间的小时到前台提供判断
				modelAndView.addObject("hourofday",calendar.get(Calendar.HOUR_OF_DAY));
				//封装当天日期
				modelAndView.addObject("newday",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				//准备预约时间数据结束
				
				//封装上午时间
				modelAndView.addObject("noon",properties.getString("noon"));
				//封装下午时间
				modelAndView.addObject("afternoon",properties.getString("afternoon"));
				//3.准备用户数据
				modelAndView.addObject("member",member);
				
				//封装头部js开始	
				String top="";
				String banner="";
				String nav="";
				String end="";
				if (NumberUtil.getInt(webid) > 0) {
					Item item=itemService.getBanner(webid,"0");
					if(item !=null){
						top=item.getName();
						session.setAttribute("top", top);
						banner=item.getAuditor();
						session.setAttribute("top", banner);
						nav=item.getTimes();
						session.setAttribute("nav", nav);
						end=item.getDotimes();
						session.setAttribute("end", end);
					}
				}
				modelAndView.addObject("top", top);
				modelAndView.addObject("banner", banner);
				modelAndView.addObject("nav", nav);
				modelAndView.addObject("end", end);
				//封装头部js结束
				
				modelAndView.setViewName("gszw/zxyy/index");
				
			}else {
				RedirectView redirectView = new RedirectView(BaseInfo.getContextPath()+ "/error/error.do");
				modelAndView.setView(redirectView);
				return modelAndView;
			}
			DataSourceSwitch.changeDefault();
		}
		} catch (Exception e) {
		  e.printStackTrace();
		}
		return modelAndView;
	}
	
	@RequestMapping("submit")
	@ResponseBody
	public JsonResult submit(String date,String houre,HttpSession session){
		JsonResult result = JsonResult.getInstance();
		//数据校验
		if (StringUtil.isEmpty(date) || !FilterUtil.isSqlOK(houre)) {
			result.setMessage("参数格式不正确");
		}
		try {
			Object items = session.getAttribute("itemEntity");
			//1.准备用户数据
			Member member = MemberSessionInfo.getCurrentMemberInfo();
			if (items == null || member == null) {
				return result.setMessage("必要参数缺失，请联系管理员");
			}
			//2.准备事项数据
			ItemEntity itemEntity = (ItemEntity)items;
			
			DataSourceSwitch.change("7");
			//查询是否有重复预约
			Integer count = zxyyItemService.zxyyCount(itemEntity.getField_3651(), member.getLoginname());
			if (count >0) {
				return result.setMessage("请勿重复预约");
			}
			//3.数据储存到中间库数据库
			Integer integer = zxyyItemService.add(itemEntity,member,date,houre);
			DataSourceSwitch.changeDefault();
			if (integer >0) {
				//储存到项目库
				zxyyItemService.add(itemEntity,member,date,houre);
				//4.封装数据到前台
				session.setAttribute("id", integer);
				//预约时间段
				session.setAttribute("houre", houre);
				//预约日期
				session.setAttribute("date", date);
				result.setSuccess(true);
			}else {
				return result.setMessage("预约失败");
			}
			} catch (Exception e) {
				e.printStackTrace();
				return result.setMessage("预约失败:"+e.getMessage());
			}
		return result;
	}
	
	/**
	 * 提交成功页面
	 * @param date 日期
	 * @param houre 预约时间段
	 * @return
	 */
	@RequestMapping("success")
	public ModelAndView success(HttpSession session){
		ModelAndView modelAndView = new ModelAndView();
		try {
		Object items = session.getAttribute("itemEntity");
		//1.准备用户数据
		Member member = MemberSessionInfo.getCurrentMemberInfo();
		if (items == null || member == null) {
			RedirectView redirectView = new RedirectView(BaseInfo.getContextPath()+ "/error/error.do");
			modelAndView.setView(redirectView);
			return modelAndView;
		}
		//2.准备事项数据
		ItemEntity itemEntity = (ItemEntity)items;
			//预约时间段
			modelAndView.addObject("houre",session.getAttribute("houre"));
			//预约日期
			modelAndView.addObject("date",session.getAttribute("date"));
			//事项数据
			modelAndView.addObject("itemEntity",itemEntity);
			//封装js
			Object attribute = session.getAttribute("top");
			if (attribute != null) {
				String top = (String)session.getAttribute("top");
				String banner = (String)session.getAttribute("banner");
				String nav = (String)session.getAttribute("nav");
				String end = (String)session.getAttribute("end");
				modelAndView.addObject("top", top);
				modelAndView.addObject("banner", banner);
				modelAndView.addObject("nav", nav);
				modelAndView.addObject("end", end);
			}
			modelAndView.setViewName("gszw/zxyy/success");
		} catch (Exception e) {
			RedirectView redirectView = new RedirectView(BaseInfo.getContextPath()+ "/error/error.do");
			modelAndView.setView(redirectView);
			e.printStackTrace();
		}
		return modelAndView;
	}
	/**
	 * 取消预约
	 * @param session
	 */
	@RequestMapping("cancel")
	@ResponseBody
	public JsonResult cancel(HttpSession session,HttpServletResponse response){
		JsonResult result = JsonResult.getInstance();
		try {
		Object items = session.getAttribute("itemEntity");
		Member member = MemberSessionInfo.getCurrentMemberInfo();
		if (items == null ||member == null) {
		result.setMessage("超30分钟不能取消");
		return result;
		}else {
			ItemEntity itemss = (ItemEntity)session.getAttribute("itemEntity");
			DataSourceSwitch.change("7");
			Boolean falage = zxyyItemService.cancel(itemss,member);
			DataSourceSwitch.changeDefault();
			if (falage) {
				result.setSuccess(true);
				//跳转到预约页面
				result.setMessage(properties.getString("zxyyurl")+"?unid="+itemss.getField_3651());
			}else {
				result.setMessage("取消失败");
			}
		}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 预约次数统计
	 * @param session
	 */
	@RequestMapping("zxyycountByUnid")
	@ResponseBody
	public JsonResult zxyycountByunid(HttpSession session,String unid){
		JsonResult result = JsonResult.getInstance();
		try {
		Object items = session.getAttribute("itemEntity");
		if (items == null) {
			return result.setMessage("必要参数缺失，请联系管理员");
		}
		ItemEntity itemEntity = (ItemEntity)items;
		DataSourceSwitch.change("7");
		if (unid != null) {
			//统计总数
			Integer zxyycount = zxyyItemService.zxyyCountByunid(itemEntity.getField_3651());
			if (zxyycount > 0) {
				StringBuilder builder = new StringBuilder();
				//获取工作日
				List<String> list = WorkdayUtil.getworkdays();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				
				builder.append("<thead><tr class=\"color1\"><th></th>");
				//拼接th
				for (String date : list) {
					builder.append("<th>").append(date).append("</th>");
				}
				
				builder.append("</tr></thead><tbody><tr><td class=\"color1\" id =\"forenoon\">"+properties.getString("noon")+"</td>");
		
				//获取上午的
				List<ZxyyEntity> noonlist = zxyyItemService.countNoonOrAfternoon(itemEntity.getField_3651(), "1");
				
				for (String date : list) {
					String a = "0";
					String number ="";
					if (CollectionUtils.isNotEmpty(noonlist)) {
						//遍历上午的预约次数
						for (ZxyyEntity zxyyEntity : noonlist) {
							String format2 = format.format(zxyyEntity.getOrderdatetime());	
							//当前日期等于统计日期，将预约次数改变
							if (format2.equals(date)) {
								a = zxyyEntity.getCount();
								if (Integer.parseInt(a) >= properties.getInt("zxyynumber")) {
									number="style=\"pointer-events: none; background-color: rgb(233, 242, 251);\"";
								}
								continue;
							}
						}
						String string ="<td "+number+" class=\"book\"><input type=\"hidden\" value=\""+date+"\" /><a>"+a+"</a>/200</td>";
						builder.append(string);
					}else {
						builder.append("<td class=\"book\"><input type=\"hidden\" value=\""+date+"\" /><a>0</a>/200</td>");
					}
				}
				builder.append("</tr><tr><td class=\"color1\" id =\"afternoon\">"+properties.getString("afternoon")+"</td>");
				
				//获取下午的
				List<ZxyyEntity> afterlist = zxyyItemService.countNoonOrAfternoon(itemEntity.getField_3651(), "2");
				for (String date2 : list) {
					String a = "0";
					String number ="";
					if (CollectionUtils.isNotEmpty(afterlist)) {
						//遍历上午的预约次数
						for (ZxyyEntity zxyyEntity : afterlist) {
							String format2 = format.format(zxyyEntity.getOrderdatetime());	
							//当前日期等于统计日期，将预约次数改变
							if (format2.equals(date2)) {
								a = zxyyEntity.getCount();
								if (Integer.parseInt(a) >= properties.getInt("zxyynumber")) {
									number="style=\"pointer-events: none; background-color: rgb(233, 242, 251);\"";
								}
								continue;
							}
						}
						String string ="<td "+number+" class=\"book\"><input type=\"hidden\" value=\""+date2+"\" /><a>"+a+"</a>/200</td>";
						builder.append(string);
					}else {
						builder.append("<td class=\"book\"><input type=\"hidden\" value=\""+date2+"\" /><a>0</a>/200</td>");
					}
				}
				builder.append("</tr></tbody>");
				result.setSuccess(true);
				result.addParam("str", builder);
		}
			}
		DataSourceSwitch.changeDefault();
		} catch (Exception e) {
			result.setMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 打印预约凭证
	 * @param date 预约时间
	 * @param houre 预约时间段
	 * @param session 
	 * @return
	 */
	@RequestMapping("print")
	public ModelAndView print(String date,String houre,HttpSession session){
		ModelAndView modelAndView = new ModelAndView();
		//数据校验
		if (StringUtil.isEmpty(date) || !FilterUtil.isSqlOK(houre)) {
			RedirectView redirectView = new RedirectView(BaseInfo.getContextPath()+ "/error/error.do");
			modelAndView.setView(redirectView);
			return modelAndView;
		}
		try {
			Object items = session.getAttribute("itemEntity");
			//1.准备用户数据
			Member member = MemberSessionInfo.getCurrentMemberInfo();
			if (member == null || items == null) {
			RedirectView redirectView = new RedirectView(BaseInfo.getContextPath()+ "/error/error.do");
			modelAndView.setView(redirectView);
			return modelAndView;
			}
			modelAndView.addObject("itemEntity",(ItemEntity)items);
			modelAndView.addObject("member",member);
			modelAndView.addObject("date",date);
			modelAndView.addObject("houre",houre);
			modelAndView.setViewName("gszw/zxyy/print");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;
	}
}
