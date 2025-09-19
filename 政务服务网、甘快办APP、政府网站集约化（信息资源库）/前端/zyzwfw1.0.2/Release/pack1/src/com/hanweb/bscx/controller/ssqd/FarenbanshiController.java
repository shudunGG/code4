package com.hanweb.bscx.controller.ssqd;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hanweb.bscx.controller.member.LoginController;
import com.hanweb.bscx.entity.Dept;
import com.hanweb.bscx.entity.Item;
import com.hanweb.bscx.entity.Member;
import com.hanweb.bscx.entity.Shishiqingdan;
import com.hanweb.bscx.entity.Theme;
import com.hanweb.bscx.entity.Yewubanlixiang;
import com.hanweb.bscx.service.AccessRecordService;
import com.hanweb.bscx.service.ItemService;
import com.hanweb.bscx.service.ShishiqingdanService;
import com.hanweb.bscx.service.UumsService;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.datasource.DataSourceSwitch;
import com.hanweb.common.util.CacheUtil;
import com.hanweb.common.util.NumberUtil;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.constant.GszwStaticValues;
import com.hanweb.complat.listener.MemberSessionInfo;
import com.hanweb.util.FilterUtil;

/**
 * 法人办事列表展示页面
 * 分为部门主题展示和事项列表展示两部分
 * 
 * @author 钟小成
 *
 */
@Controller
@RequestMapping("ssqd/fr")
public class FarenbanshiController {
	@Autowired
	private ShishiqingdanService shishiqingdanService;
	@Autowired
	private AccessRecordService accessRecordService;
	@Autowired
	private ItemService itemService;
	
	public UumsService uumsService = SpringUtil.getBean("UumsService",UumsService.class);
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "frlist")
	public ModelAndView frlist(String webid,String farentheme) {
		ModelAndView modelAndView = new ModelAndView();
		if(!FilterUtil.isSqlOK(webid) || NumberUtil.getInt(webid) <= 0 || !FilterUtil.isNumeric(webid)||!FilterUtil.isSqlOK(farentheme)){
            RedirectView redirectView = new RedirectView(BaseInfo.getContextPath() +"/error/error.do");
            modelAndView.setView(redirectView);
            return modelAndView;
        }
		 List<Theme> themelist = (List) CacheUtil.getValue("farentheme",
				  GszwStaticValues.FARENTHEM);
		DataSourceSwitch.change("2");
		List<Dept> deptlist = shishiqingdanService.getCorDeptsNameAndCode(webid);
		
		List<Shishiqingdan> typelist=shishiqingdanService.getCorTypeIdAanName(webid);
		//获取js
		String top="";
		String banner="";
		String nav="";
		String end="";
		if (NumberUtil.getInt(webid) > 0) {
			Item item=itemService.getBanner(webid,"0");
			if(item !=null){
				top=item.getName();
				banner=item.getAuditor();
				nav=item.getTimes();
				end=item.getDotimes();
			}
		}
		modelAndView = new ModelAndView("gszw/bscx/ssqd/farenbanshi/fr_list");
		modelAndView.addObject("themelist", themelist);
		modelAndView.addObject("deptlist", deptlist);
		modelAndView.addObject("webid", webid);
		modelAndView.addObject("top", top);
		modelAndView.addObject("banner", banner);
		modelAndView.addObject("nav", nav);
		modelAndView.addObject("end", end);
		modelAndView.addObject("farentheme", farentheme);
		modelAndView.addObject("typelist", typelist);
		return modelAndView;
	}
	/**
	 * @param themeid 主题id
	 * @param deptCode 部门统一社会信用代码
	 * @param number 表示第几页
	 * @param numOfOnePage 每页展示的数量
	 * 
	 * @return
	 */
	@RequestMapping(value = "frdown")
	public ModelAndView frdown(String farenthemeId,String deptCode,String isonline,String keyword,
			String number,String numOfOnePage,String webid,String typeId) {
		if(StringUtil.isEmpty(webid)){
			return null;
		}
		try {
			if(StringUtil.isNotEmpty(keyword)){
				 keyword= URLDecoder.decode(keyword, "UTF-8");
		         keyword = keyword.replaceAll("%(?![0-9a-fA-F]{2})", "100");
		           keyword = keyword.replaceAll("\\+", "500");
		           keyword = keyword.replace("@", "200");
		           keyword = keyword.replace(" ", "");
		           keyword = keyword.replace("$", "300");
		           keyword = keyword.replace("&", "400");
		           keyword = keyword.replace("(", "（");
		           keyword = keyword.replace(")", "）");
		           keyword = keyword.replace(">", "600");
		           keyword = keyword.replace("<", "700");
		           keyword = keyword.replace("//", "800");
		           keyword = keyword.replace("||", "900");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!FilterUtil.isSqlOK(farenthemeId)||!FilterUtil.isSqlOK(deptCode)||!FilterUtil.isSqlOK(isonline)
				||!FilterUtil.isSqlOK(keyword)||
				!FilterUtil.isSqlOK(number)
				||!FilterUtil.isSqlOK(numOfOnePage)||!FilterUtil.isSqlOK(webid)||!FilterUtil.isSqlOK(typeId)){
			Date date  =new Date();
			System.out.println("date: "+date.toString()+"farenthemeId: "+farenthemeId+"typeId:"+typeId+"deptCode: "+deptCode+
					"isonline: "+isonline+"keyword: "+keyword+
					"number: "+number+"numOfOnePage: "+numOfOnePage);
			return null;
		}
		int startNumber = 0;
		int pageNum = 0;
		if(StringUtil.isEmpty(number)){
			number = 1+"";
		}
		if(StringUtil.isEmpty(numOfOnePage)){
			numOfOnePage = 10+"";
		}
		if(StringUtil.isNotEmpty(number)&&Integer.parseInt(number)>0){
			startNumber = (Integer.parseInt(number)-1)*10;
		}
		ModelAndView modelAndView = new ModelAndView();
		DataSourceSwitch.change("2");
		List<Shishiqingdan> shishiqingdanlist = shishiqingdanService.getFarenShishiqingdanList(farenthemeId,typeId,deptCode,
				isonline,keyword,startNumber,Integer.parseInt(numOfOnePage),webid);
		List<Shishiqingdan> shishiqingdanHavaYewubanlixianglist = new ArrayList<Shishiqingdan>();
		for(Shishiqingdan shishiqingdan:shishiqingdanlist){
			List<Yewubanlixiang> yewubanlixiangList = shishiqingdanService.getFarenYewubanlixiangList(shishiqingdan.getTaskCode());
			shishiqingdan.setYewubanlixiangList(yewubanlixiangList);
			shishiqingdanHavaYewubanlixianglist.add(shishiqingdan);
		}
		int count = shishiqingdanService.getFarenShishiqingdanCount(farenthemeId,deptCode,isonline,keyword,webid,typeId);
		if(count>0){
			if(count%Integer.parseInt(numOfOnePage)==0){
				pageNum = count/Integer.parseInt(numOfOnePage);
			}else{
				pageNum = count/Integer.parseInt(numOfOnePage)+1;
			}
		}
		int count1 = shishiqingdanService.getFarenShishiqingdanCount1(farenthemeId,deptCode,isonline,keyword,webid,typeId);
		int count2 = shishiqingdanService.getFarenShishiqingdanCount2(farenthemeId,deptCode,isonline,keyword,webid,typeId);
		int count3 = count1-count2;
		int farenShixiangqingdanAndYewubanlixiangcount = shishiqingdanService.getFarenShishiqingdanAndYewubanlixiangCount(farenthemeId,deptCode,isonline,keyword,webid);
		modelAndView = new ModelAndView("gszw/bscx/ssqd/farenbanshi/fr_down");
		modelAndView.addObject("shishiqingdanHavaYewubanlixianglist", shishiqingdanHavaYewubanlixianglist);
		modelAndView.addObject("count", count);
		modelAndView.addObject("number", number);
		modelAndView.addObject("pageNum", pageNum);
		modelAndView.addObject("count3", count3);
		modelAndView.addObject("webid", webid);
		modelAndView.addObject("farenShixiangqingdanAndYewubanlixiangcount", farenShixiangqingdanAndYewubanlixiangcount);
		return modelAndView;
	}
	
	/**
	 * 法人判断是否登录并拼接要跳转的地址
	 * @return
	 */
	@RequestMapping({"panduanshifoudengluAndTiaozhuan"})
	  @ResponseBody
	  public String panduanshifoudengluAndTiaozhuan(HttpSession session, String taskCode,String rowGuid, String linkAddr, 
			  String spbs,String serverType,String webid,String taskname)
	  {
	    if ((!(FilterUtil.isSqlOK(taskCode))) || (!(FilterUtil.isUrlOK(linkAddr))) || (!(FilterUtil.isSqlOK(spbs)))) {
	      Date date = new Date();
	      System.out.println(date.toString() + ": 输入的参数：" + "taskCode: " + taskCode + " linkAddr: " + linkAddr + 
	        " spbs: " + spbs);
	      return "您输入的参数有问题";
	    }
	    try {
			if(StringUtil.isNotEmpty(taskname)){
			taskname= URLDecoder.decode(taskname, "UTF-8");
			}
			if(StringUtil.isNotEmpty(linkAddr)){
				linkAddr= URLDecoder.decode(linkAddr, "UTF-8");
				}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    Member member  = null;
		if(session.getAttribute("token") != null && !"".equals(session.getAttribute("token"))){
			member = (Member) CacheUtil.getValue(session.getAttribute("token").toString(),
					GszwStaticValues.USERINFO);
		}
	    String tiaozhuanAddr = "needlogin";
	    if (member != null)
	    {
	      String ticket = new LoginController().getticketfromjis(session);
	      //记录我的足迹
	      accessRecordService.accessRecord(member.getLoginname(), member.getType(), rowGuid, taskname, webid, "法人办事点击在线办理");
	      if("2".equals(serverType)&&("20".equals(member.getType())||"2".equals(member.getType()))){
				if(linkAddr.indexOf("?")>-1){
					tiaozhuanAddr = linkAddr+"&sid="+taskCode+"&spbs="+spbs+"&ticket="+ticket;
				}else{
					tiaozhuanAddr = linkAddr+"&sid="+taskCode+"&spbs="+spbs+"&ticket="+ticket;
				}
			}else{
				tiaozhuanAddr = "gaishixianggerenbunengbanli";
			}
	    } else {
	      tiaozhuanAddr = "needlogin";
	      session.setAttribute("action", "zxbl");
	      session.setAttribute("taskCode", taskCode);
	      session.setAttribute("rowGuid", rowGuid);
	      session.setAttribute("linkAddr", linkAddr);
	      session.setAttribute("spbs", spbs);
	      session.setAttribute("serverType", "2");
	      session.setAttribute("webid", webid);
	      session.setAttribute("taskname", taskname);
	    }
	    return tiaozhuanAddr;
	  }
}
