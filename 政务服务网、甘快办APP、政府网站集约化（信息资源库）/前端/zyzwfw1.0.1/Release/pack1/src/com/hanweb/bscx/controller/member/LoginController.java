package com.hanweb.bscx.controller.member;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanweb.bscx.context.MySessionContext;
import com.hanweb.bscx.controller.ssqd.BanshizhinanController;
import com.hanweb.bscx.controller.ssqd.BumenshixiangController;
import com.hanweb.bscx.entity.CommonUserInfo;
import com.hanweb.bscx.entity.Member;
import com.hanweb.bscx.service.AppForFrontService;
import com.hanweb.bscx.service.ItemService;
import com.hanweb.bscx.service.UumsService;
import com.hanweb.common.BaseInfo;
import com.hanweb.common.datasource.DataSourceSwitch;
import com.hanweb.common.util.CacheUtil;
import com.hanweb.common.util.Properties;
import com.hanweb.common.util.SpringUtil;
import com.hanweb.common.util.StringUtil;
import com.hanweb.complat.constant.GszwStaticValues;
import com.hanweb.complat.listener.MemberSessionInfo;
import com.hanweb.util.FilterUtil;
import com.zdww.utils.SM4Utils;

@Controller
@RequestMapping({"member/login"})
public class LoginController
{
  @Autowired
  AppForFrontService appServiceforfront;
  @Autowired
  UumsService uumsService;
  public UumsService uumsSer = SpringUtil.getBean("UumsService",UumsService.class);
  @Autowired
  ItemService itemService;
  
  RestTemplate restTemplate = new RestTemplate();
  
  Properties properties = new Properties(BaseInfo.getRealPath() + 
    "/WEB-INF/config/interfaces/interfaces.properties");
  
  private Properties zxyyproperties = new Properties(BaseInfo.getRealPath() + "/WEB-INF/config/zxyy/zxyy.properties");
  
  @RequestMapping({"login"})
  public ModelAndView showLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response, String src, String url, String url1, String domain)
    throws UnsupportedEncodingException
  {
    ModelAndView modelAndView = new ModelAndView();
    if (StringUtil.isNotEmpty(domain)) {
      domain = URLDecoder.decode(domain, "UTF-8");
      System.out.println("获取到的host"+domain);
    }
    if ((!FilterUtil.isUrlOK(src)) || (!FilterUtil.isUrlOK(url)) || (!FilterUtil.isUrlOK(url1)) || (!FilterUtil.isUrlOK(domain)))
    {
    	System.out.println("进去到了错误页面1111");
      RedirectView redirectView = new RedirectView(
        BaseInfo.getContextPath() + 
        "/error/error.do");
      modelAndView.setView(redirectView);
      return modelAndView;
    }
    String referer = request.getHeader("Referer");
    System.out.println("获取到的referer"+referer);
    /*if (!FilterUtil.isRefererOK(referer))
    {
    	System.out.println("进去到了错误页面2222");
      RedirectView redirectView = new RedirectView(
        BaseInfo.getContextPath() + 
        "/error/error.do");
      modelAndView.setView(redirectView);
      return modelAndView;
    }*/
    modelAndView = new ModelAndView("gszw/bscx/member/login");
    if (src.indexOf("gsjis") > -1) {
      src = "http://" + domain;
    }
    if (StringUtil.isEmpty(src)) {
      src = "http://" + domain;
    } else {
      src = URLDecoder.decode(URLDecoder.decode(src, "UTF-8"), "UTF-8");
    }
    session.setAttribute("src", src);
    if (StringUtil.isEmpty(url)) {
      url = this.properties.getString("loginurl") + "&domain=" + domain + "&gotoUrl=";
    }
    if (StringUtil.isNotEmpty(url1)) {
      url = this.properties.getString("frloginurl") + "&domain=" + domain + "&gotoUrl=";
    }
    modelAndView.addObject("loginurl", url);
    return modelAndView;
  }
  
  @RequestMapping({"getgrinfo"})
  public ModelAndView getGrInfo(HttpSession session, String tokenSNO,String ticket, String gotoUrl,
		  HttpServletRequest request, HttpServletResponse response,String ussToken,String authCode)
    throws Exception
  {
	  
	  System.out.println("回调地址传过来的ticket="+ticket+"传过来的authCode"+authCode);
	  session.setAttribute("authCode", authCode);
	  String secretKey = properties.getString("secretKey");
	  String appmark = properties.getString("appmarkSSO");
	  ticket = SM4Utils.sm4EncryptECB(ticket, secretKey);
	  System.out.println("登录接受到加密后的参数为"+ticket);
	  authCode = SM4Utils.sm4EncryptECB(authCode, secretKey);
	  System.out.println("登录受到加密后的参数为"+authCode);
	  String accessToken = uumsService.getAccessToken(appmark);
	  System.out.println("登录接受到的授权得accessToken"+accessToken);
	  HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer "+accessToken);
      System.out.println("在响应头里面设置了accessToken了");
      //使用票据获取token
      HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
      ResponseEntity<String> tokenData = restTemplate.exchange("http://61.178.65.73/api/sso/getGsCityTokenWithTicket?ticketNo={1}", HttpMethod.GET, httpEntity, String.class,ticket);
      System.out.print("登录接口响应为："+tokenData);
      JSONObject obj = JSON.parseObject(tokenData.getBody());
      Member member = new Member();
      if(obj!= null && obj.getIntValue("code") == 6000){
		  String token = obj.getString("data");
		  session.setAttribute("token", token);
		  //使用token获取用户信息
		  ResponseEntity<String> userData;
		  //自然人用户
		   userData = restTemplate.exchange("http://61.178.65.73/api/sso/getNaturalInfoByToken?authCode={1}&tokenSNO={2}",HttpMethod.GET, httpEntity,String.class,authCode,SM4Utils.sm4EncryptECB(token, secretKey));
		   System.out.print("登录获取用户信息响应为："+userData);
		  JSONObject tokenObj = JSON.parseObject(userData.getBody());
		  if(tokenObj!=null && tokenObj.getIntValue("code") == 6000){
			  //CommonUserInfo.class为自然人、法人共用字段实体。
			  
			  CommonUserInfo userInfo = JSON.parseObject(JSON.toJSONString(tokenObj.get("data")),CommonUserInfo.class);
			  System.out.println(userInfo);
			  member.setType(userInfo.getUserType());
			  member.setLoginname(userInfo.getLoginName());
			  if(("10").equals(member.getType())){
				  member.setType("1");
				  member.setRealname(userInfo.getUserName());
				  //封装手机号
				  member.setPhone(userInfo.getUserMobile());
				  //封装身份证号
				  member.setCardid(userInfo.getUserCertNum());
				  //封装登陆名
				  member.setLoginname(userInfo.getLoginName());
			  }else{
				  member.setType("2");
				  member.setRealname(userInfo.getCorpName());
				  //企业信用代码
				  member.setCorpCertNum(userInfo.getCorpCertNum());
				  //封装手机号
				  member.setPhone(userInfo.getUserMobile());
				  //封装登陆名
				  member.setLoginname(userInfo.getLoginName());
			  }
			 
			  System.out.println("解析完毕的用户信息为"+userInfo);
			  CacheUtil.setValue(token, member, GszwStaticValues.USERINFO);
			  MemberSessionInfo.setCurrentMemberInfo(member);
			  System.out.println("将获取到的用户信息设置到session中完成了登录！！");
		  }else{
			  session.setAttribute("success",false);
		  }
	  }else{
		  session.setAttribute("success",false);
	  }
	  
    ModelAndView modelandview = new ModelAndView("gszw/bscx/member/redirect");
    String message = "";
    if (StringUtil.isNotEmpty(gotoUrl)) {
      session.setAttribute("src", gotoUrl);
    }
    if (member != null)
    {
      member.setType("1");
      member.setTicket(ticket);
      member.setResult("success");
      MemberSessionInfo.setCurrentMemberInfo(member);//保存用户信息到session中
      
      String action = (String)session.getAttribute("action");
	  String taskCode = (String)session.getAttribute("taskCode");
	  String linkAddr = (String)session.getAttribute("linkAddr");
	  String spbs = StringUtil.getString(session.getAttribute("spbs"));
	  String taskname = (String)session.getAttribute("taskname");
	  String webid = (String)session.getAttribute("webid");
	  String rowGuid = (String)session.getAttribute("rowGuid");
	  String serverType = StringUtil.getString(session.getAttribute("serverType"));
      
      session.setAttribute("isauth", member.getIsauth());//Isauth 是否实名认证
      String domain = (String)session.getAttribute("domain");
      String bmfwaction = (String)session.getAttribute("bmfwaction");
      String zxtsaction = (String)session.getAttribute("zxtsaction");
      String itemcode = (String)session.getAttribute("itemcode");
      String deptid = StringUtil.getString(session.getAttribute("deptid"));
      String centercode = StringUtil.getString(session.getAttribute("centercode"));
      String bmfwitemcode = (String)session.getAttribute("bmfwitemcode");
      if ("zxbl".equals(action))
      {
    	  modelandview = new ModelAndView("gszw/bscx/member/redirectTYSB");
    	  System.out.println("登录成功需要跳转统一申报页面");
    	  message = new BumenshixiangController().bmsxPanduanshifoudengluAndTiaozhuan(session, taskCode, rowGuid,linkAddr, spbs,serverType,webid,taskname);
    	  modelandview.addObject("message", message);
    	  return modelandview;
      }
      else if ("bmfwzxbl".equals(bmfwaction))
      {
        message = this.itemService.getBsznZxblmessage(member, bmfwitemcode);
      }
      else if ("zx".equals(zxtsaction))
      {
        message = "http://" + domain + "/zyzwfw/zxts/myconsult/add_notice.do?webid=" + webid + "&orgcode=" + deptid + "&itemcode=" + itemcode + "&centercode=" + centercode;
      }
      else if ("ts".equals(zxtsaction))
      {
        message = "http://" + domain + "/zyzwfw/zxts/mycomplain/add_notice.do?webid=" + webid + "&orgcode=" + deptid + "&itemcode=" + itemcode + "&centercode=" + centercode;
      }
      else if ("jy".equals(zxtsaction))
      {
        message = "http://" + domain + "/zyzwfw/zxts/mysuggest/add_notice.do?webid=" + webid + "&orgcode=" + deptid + "&itemcode=" + itemcode + "&centercode=" + centercode;
      }
    }
    HttpSession sessionhave = SpringUtil.getRequest().getSession(false);
    String sessionId = sessionhave.getId();
    System.out.println("sessionId======"+sessionId);
    //restTemplate.getForObject("http://61.178.65.73/gsjis/natural/acceptNaturalSessionId?tokenSNO="+tokenSNO+"&sessionId="+sessionId, String.class );
    Cookie JSESSIONID = new Cookie("JSESSIONIDKUAYU", sessionhave.getId());//解决跨域问题的代码
    JSESSIONID.setDomain(".gszwfw.gov.cn");
    JSESSIONID.setPath("/gszw");
    response.addCookie(JSESSIONID);
    modelandview.addObject("message", message);
    return modelandview;
  }
  
  @RequestMapping({"getfrinfo"})
  public ModelAndView getFrInfo(HttpSession session, String tokenSNO,String ticket, String gotoUrl,
		  HttpServletResponse response,String ussToken,String authCode)
    throws Exception
  {
	  
	  System.out.println("回调地址传过来的ticket="+ticket+"传过来的authCode"+authCode);
	  session.setAttribute("authCode", authCode);
	  String secretKey = properties.getString("secretKey");
	  String appmark = properties.getString("appmarkSSO");
	  ticket = SM4Utils.sm4EncryptECB(ticket, secretKey);
	  System.out.println("登录接受到加密后的参数为"+ticket);
	  authCode = SM4Utils.sm4EncryptECB(authCode, secretKey);
	  System.out.println("登录受到加密后的参数为"+authCode);
	  String accessToken = uumsService.getAccessToken(appmark);
	  System.out.println("登录接受到的授权得accessToken"+accessToken);
	  HttpHeaders headers = new HttpHeaders();
      headers.add("Authorization", "Bearer "+accessToken);
      System.out.println("在响应头里面设置了accessToken了");
      //使用票据获取token
      HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
      ResponseEntity<String> tokenData = restTemplate.exchange("http://61.178.65.73/api/sso/getGsCityTokenWithTicket?ticketNo={1}", HttpMethod.GET, httpEntity, String.class,ticket);
      System.out.print("登录接口响应为："+tokenData);
      JSONObject obj = JSON.parseObject(tokenData.getBody());
      Member member = new Member();
      if(obj!= null && obj.getIntValue("code") == 6000){
		  String token = obj.getString("data");
		  session.setAttribute("token", token);
		  //使用token获取用户信息
		  ResponseEntity<String> userData;
		  //自然人用户
		   userData = restTemplate.exchange("http://61.178.65.73/api/sso/getCorpInfoByToken?authCode={1}&tokenSNO={2}",HttpMethod.GET, httpEntity,String.class,authCode,SM4Utils.sm4EncryptECB(token, secretKey));
		   System.out.print("登录获取用户信息响应为："+userData);
		  JSONObject tokenObj = JSON.parseObject(userData.getBody());
		  if(tokenObj!=null && tokenObj.getIntValue("code") == 6000){
			  //CommonUserInfo.class为自然人、法人共用字段实体。
			  
			  CommonUserInfo userInfo = JSON.parseObject(JSON.toJSONString(tokenObj.get("data")),CommonUserInfo.class);
			  member.setType(userInfo.getUserType());
			  member.setLoginname(userInfo.getLoginName());
			  if(("10").equals(member.getType())){
				  member.setRealname(userInfo.getUserName());
				  member.setType("1");
				  //封装手机号
				  member.setPhone(userInfo.getUserMobile());
				  //封装身份证号
				  member.setCardid(userInfo.getUserCertNum());
				  //封装登陆名
				  member.setLoginname(userInfo.getLoginName());
			  }else{
				  member.setType("2");
				  //企业名称,原代码，不要动
				  member.setRealname(userInfo.getCorpName());
				  //企业信用代码
				  member.setCorpCertNum(userInfo.getCorpCertNum());
				  //封装手机号
				  member.setPhone(userInfo.getUserMobile());
				  //封装登陆名
				  member.setLoginname(userInfo.getLoginName());
			  }
			 
			  System.out.println("解析完毕的用户信息为"+userInfo);
			  CacheUtil.setValue(token, member, GszwStaticValues.USERINFO);
			  MemberSessionInfo.setCurrentMemberInfo(member);
			  System.out.println("将获取到的用户信息设置到session中完成了登录！！");
		  }else{
			  session.setAttribute("success",false);
		  }
	  }else{
		  session.setAttribute("success",false);
	  }
	  
    ModelAndView modelandview = new ModelAndView("gszw/bscx/member/redirect");
    String message = "";
    if (StringUtil.isNotEmpty(gotoUrl)) {
      session.setAttribute("src", gotoUrl);
    }
    if (member != null)
    {
      member.setType("2");
      member.setTicket(ticket);
      member.setResult("success");
      String action = (String)session.getAttribute("action");
	  String taskCode = (String)session.getAttribute("taskCode");
	  String linkAddr = (String)session.getAttribute("linkAddr");
	  String spbs = StringUtil.getString(session.getAttribute("spbs"));
	  String taskname = (String)session.getAttribute("taskname");
	  String webid = (String)session.getAttribute("webid");
	  String rowGuid = (String)session.getAttribute("rowGuid");
	  String serverType = StringUtil.getString(session.getAttribute("serverType"));
      
      MemberSessionInfo.setCurrentMemberInfo(member);
      String domain = (String)session.getAttribute("domain");
      String bmfwaction = (String)session.getAttribute("bmfwaction");
      String bmfwitemcode = (String)session.getAttribute("bmfwitemcode");
      String itemcode = (String)session.getAttribute("itemcode");
      String centercode = StringUtil.getString(session.getAttribute("centercode"));
      String zxtsaction = (String)session.getAttribute("zxtsaction");
      String deptid = StringUtil.getString(session.getAttribute("deptid"));
      if ("zxbl".equals(action))
      {
    	  modelandview = new ModelAndView("gszw/bscx/member/redirectTYSB");
    	  System.out.println("登录成功需要跳转统一申报页面");
    	  message = new BumenshixiangController().bmsxPanduanshifoudengluAndTiaozhuan(session, taskCode, rowGuid,linkAddr, spbs,serverType,webid,taskname);
    	  modelandview.addObject("message", message);
    	  return modelandview;
      }
      else if ("bmfwzxbl".equals(bmfwaction))
      {
        message = this.itemService.getBsznZxblmessage(member, bmfwitemcode);
      }
      else if ("zx".equals(zxtsaction))
      {
        message = "http://" + domain + "/gszw/zxts/myconsult/add_notice.do?webid=" + webid + "&orgcode=" + deptid + "&itemcode=" + itemcode + "&centercode=" + centercode;
      }
      else if ("ts".equals(zxtsaction))
      {
        message = "http://" + domain + "/gszw/zxts/mycomplain/add_notice.do?webid=" + webid + "&orgcode=" + deptid + "&itemcode=" + itemcode + "&centercode=" + centercode;
      }
      else if ("jy".equals(zxtsaction))
      {
        message = "http://" + domain + "/gszw/zxts/mysuggest/add_notice.do?webid=" + webid + "&orgcode=" + deptid + "&itemcode=" + itemcode + "&centercode=" + centercode;
      }
  
    }
    HttpSession sessionhave = SpringUtil.getRequest().getSession(false);
    String sessionId = sessionhave.getId();
    //restTemplate.getForObject("http://61.178.65.73/gsjis/corp/acceptCorpSessionId?tokenSNO="+tokenSNO+"&sessionId="+sessionId, String.class );
    Cookie JSESSIONID = new Cookie("JSESSIONIDKUAYU", sessionhave.getId());
    JSESSIONID.setDomain(".gszwfw.gov.cn");
    JSESSIONID.setPath("/gszw");
    response.addCookie(JSESSIONID);
    modelandview.addObject("message", message);
    return modelandview;
  }
  
  /**
   * 信用传递
   * @param ticket
   * @param msg
   * @param authCode
   * @param session
   * @return
 * @throws Exception 
 * @throws RestClientException 
   */
  @RequestMapping({"loginCallBack"})
  public void forwordLoginCallBack(String ticket, String msg, String authCode,boolean sbFlag, HttpSession session,HttpServletResponse response) throws RestClientException, Exception{
	 // ModelAndView modelandview = new ModelAndView("gszw/bscx/member/redirectSSO");
	  String secretKey = properties.getString("secretKey");
	  String appmark = properties.getString("appmarkSSO");
	System.out.print("信任传递接受到的参数为："+ticket+"----"+authCode);
	System.out.println("读取到的配置文件得密钥和系统标识是"+secretKey+"-------------"+appmark);
	  //需要对ticket做判空处理
		if(StringUtil.isEmpty(ticket) || StringUtil.isEmpty(authCode)){
			return;
		}
		  session.setAttribute("authCode", authCode);
		  ticket = SM4Utils.sm4EncryptECB(ticket, secretKey);
		  System.out.println("信任传递接受到加密后的参数为"+ticket);
		  authCode = SM4Utils.sm4EncryptECB(authCode, secretKey);
		  System.out.println("信任传递接受到加密后的参数为"+authCode);
		  
		  String accessToken = uumsService.getAccessToken(appmark);
		  System.out.println("信任传递接受到的授权得accessToken"+accessToken);
		  
		  HttpHeaders headers = new HttpHeaders();
          headers.set("Authorization", "Bearer "+accessToken);
          System.out.println("在响应头里面设置了accessToken了");
          
          //使用票据获取token
          HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
          ResponseEntity<String> tokenData = restTemplate.exchange("http://61.178.65.73/api/sso/getGsCityTokenWithTicket?ticketNo={1}", HttpMethod.GET, httpEntity, String.class,ticket);
          System.out.print("信任传递接口响应为："+tokenData);
          JSONObject obj = JSON.parseObject(tokenData.getBody());
		  if(obj!= null && obj.getIntValue("code") == 6000){
			  String token = obj.getString("data");
			  session.setAttribute("token", token);
			  //使用token获取用户信息
			  ResponseEntity<String> userData;
			  //自然人用户
			  if (StringUtil.contains(token,"naturalToken")) {
				  userData = restTemplate.exchange("http://61.178.65.73/api/sso/getNaturalInfoByToken?authCode={1}&tokenSNO={2}",HttpMethod.GET, httpEntity,String.class,authCode,SM4Utils.sm4EncryptECB(token, secretKey));
			 
			  } else {
				  //法人用户
				  userData = restTemplate.exchange("http://61.178.65.73/api/sso/getCorpInfoByToken?authCode={1}&tokenSNO={2}",HttpMethod.GET, httpEntity,String.class,authCode,SM4Utils.sm4EncryptECB(token, secretKey));
			  }
			   System.out.print("信任传递获取用户信息响应为："+userData);
			  JSONObject tokenObj = JSON.parseObject(userData.getBody());
			  if(tokenObj!=null && tokenObj.getIntValue("code") == 6000){
				  //CommonUserInfo.class为自然人、法人共用字段实体。
				  Member member = new Member();
				  CommonUserInfo userInfo = JSON.parseObject(JSON.toJSONString(tokenObj.get("data")),CommonUserInfo.class);
				  member.setType(userInfo.getUserType());
				  member.setLoginname(userInfo.getLoginName());
				  if(("10").equals(member.getType())){
					  member.setRealname(userInfo.getUserName());
					  member.setType("1");
					  //封装手机号
					  member.setPhone(userInfo.getUserMobile());
					  //封装身份证号
					  member.setCardid(userInfo.getUserCertNum());
					  //封装登陆名
					  member.setLoginname(userInfo.getLoginName());
				  }else{
					  member.setType("2");
					  //企业名称,原代码，不要动
					  member.setRealname(userInfo.getCorpName());
					  //企业信用代码
					  member.setCorpCertNum(userInfo.getCorpCertNum());
					  //封装手机号
					  member.setPhone(userInfo.getUserMobile());
					  //封装登陆名
					  member.setLoginname(userInfo.getLoginName());
				  }
				  System.out.println("解析完毕的用户信息为"+userInfo);
				  CacheUtil.setValue(token, member, GszwStaticValues.USERINFO);
				  MemberSessionInfo.setCurrentMemberInfo(member);
				  System.out.println("将获取到的用户信息设置到session中完成了登录！！");
			  }else{
				  session.setAttribute("success",false);
			  }
		  }else{
			  session.setAttribute("success",false);
			  System.out.println(msg);
		  }
	
	try {
		response.getWriter().print("<script> top.window.location.reload();</script>");
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
  
  
  
  @RequestMapping({"islogin"})
  @ResponseBody
  public String isLogin()
  {
    Member member = MemberSessionInfo.getCurrentMemberInfo();
    String name = "";
    if (member != null) {
      name = member.getLoginname();
    }
    return name;
  }
  
  @RequestMapping({"logOutSSO"})
  public ModelAndView logOutSSO(String code,HttpServletRequest request,HttpSession session) throws MalformedURLException{
	  ModelAndView modelandview = new ModelAndView("gszw/bscx/member/logout");
	  System.out.println("登出获取到的code是"+code);
	  if("90000".equals(code)){
		  if(session.getAttribute("token") != null && !"".equals(session.getAttribute("token"))){
			  String token = session.getAttribute("token").toString();
			  CacheUtil.setValue(token, null, GszwStaticValues.USERINFO);
			  MemberSessionInfo.removeCurrentMemberInfo();
			  System.out.println("已经统一登出-------------------------------");
		  }
	  }
	  String url = request.getRequestURL().toString();
	  System.out.println("获取到的当前的地址是"+url);
	  URL hostUrl = new URL(url);
	  String logout = "http://"+hostUrl.getHost();
	  System.out.println("截取到的登出地址是"+logout);
	  modelandview.addObject("logout", logout);
	  return modelandview;
  }
  
  @RequestMapping({"hideLogout"})
  public ModelAndView logOutHide(String code,String token,HttpServletRequest request) throws MalformedURLException{
	  ModelAndView modelandview = new ModelAndView("gszw/bscx/member/logout");
	  System.out.println("登出获取到的code和token是"+code+"    "+token);
	  if(StringUtil.isNotEmpty(token)){
		  CacheUtil.setValue(token, null, GszwStaticValues.USERINFO);
		  Member member = (Member) CacheUtil.getValue(token, GszwStaticValues.USERINFO);
		  if(member == null){
			  System.out.println("这里已经隐性登出了!!");
		  }
	  }
	  String url = request.getRequestURL().toString();
	  System.out.println("获取到的当前的地址是"+url);
	  URL hostUrl = new URL(url);
	  String logout = "http://"+hostUrl.getHost();
	  modelandview.addObject("logout", logout);
	  return modelandview;
  }
  
  @RequestMapping({"logout"})
  public ModelAndView grLogout(String src, String domain,HttpServletRequest request) throws MalformedURLException
  {
    Member member = MemberSessionInfo.getCurrentMemberInfo();
    if (StringUtil.isNotEmpty(domain)) {
      try
      {
        domain = URLDecoder.decode(domain, "UTF-8");
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    }
    String logout = "";
    String type = "";
    if (member != null) {
      type = member.getType();
    }
    String url = request.getRequestURL().toString();
	System.out.println("获取到的当前的地址是"+url);
	URL hostUrl = new URL(url);
	logout = "http://"+hostUrl.getHost();
    System.out.println("获取到的logout的地址是"+logout);
    MemberSessionInfo.removeCurrentMemberInfo();
    ModelAndView modelandview = new ModelAndView("gszw/bscx/member/logout");
    modelandview.addObject("name", "");
    modelandview.addObject("logout", logout);
    modelandview.addObject("src", src);
    modelandview.addObject("index", "http://www.gszwfw.gov.cn");
    return modelandview;
  }
  
  @RequestMapping({"logouttest"})
  @ResponseBody
  public String logouttest(String sessionid)
  {
	System.out.println("我来啦！！！！！！！");
	System.out.println("sessionid444444444======="+sessionid);
	if(sessionid!=null&&sessionid.length()!=0&&sessionid!=""){
		System.out.println("sessionid======="+sessionid);
		removeCurrentMemberInfo(sessionid);
	}else{
		HttpSession sessionhave = SpringUtil.getRequest().getSession(false);
		if(sessionhave!=null){
			removeCurrentMemberInfo(sessionhave.getId());
		}
	}
    return "综合查询已经退出登录";
  }
  
  /**
	 * 删除用户信息 ---从session和在线用户中.
	 * 
	 * @param request HttpServletRequest.
	 */
	public static synchronized void removeCurrentMemberInfo(String sessionid) {
		MySessionContext myc= MySessionContext.getInstance();
	    Member member = null;
	    HttpSession sess = myc.getSession(sessionid);
		if ((sess != null) && (sess.getAttribute("memberinfo") != null)) {
		      member = (Member)sess.getAttribute("memberinfo");
		    }
		if(member!=null){
			sess.invalidate();
		}
		SpringUtil.getRequest().getSession().invalidate();
	}
  
  @RequestMapping({"userresult"})
  @ResponseBody
  public String showUserResult(String webId, String callback, String domain,HttpSession session)
  {
    if ((!FilterUtil.isSqlOK(webId)) || (!FilterUtil.isSqlOK(callback)) || (!FilterUtil.isUrlOK(domain))) {
      return "参数有误！";
    }
    DataSourceSwitch.change("2");
    Member member = null;
    if(session.getAttribute("token") != null && !"".equals(session.getAttribute("token"))){
		String token = session.getAttribute("token").toString();
		member = (Member) CacheUtil.getValue(token,GszwStaticValues.USERINFO);
	}
    String grregediturl = this.properties.getString("regediturl");
    String grloginurl =  this.properties.getString("loginurl") + "&domain=" + domain + "&gotoUrl=";
    String grinfo = this.properties.getString("grinfo");
    String frregediturl = this.properties.getString("frregediturl");
    String frloginurl = this.properties.getString("frloginurl") + "&domain=" + domain + "&gotoUrl=";
    String frinfo = this.properties.getString("frinfo");
    String logout = this.properties.getString("logout") + domain;
    String frlogout =this.properties.getString("frlogout") + domain;
    String html = "";
    String cachehtml = (String)CacheUtil.getValue("htmls", "html");
    if (member != null)
    {
      String name = member.getRealname();
      String type = member.getType();
      String subName = "";
      if (name.length() > 15) {
        subName = name.substring(0, 14) + "...";
      } else {
        subName = name;
      }
      html = 
      


        html + "<table border='0' cellpadding='0' cellspacing='0'><tr><td style=\"font-size: 13px;text-align:center;\" id='userinfo' >&nbsp;欢迎您：<a style=\"text-decoration:none; font-size:13px; margin:0;\" title='" + name + "'>" + subName + "</a></td><td style=\"text-align:center;width:20px;\" >|</td><td class='fenx' style=\"text-align:center;cursor: pointer;font-size: 13px;\" onClick=\"showgrzx();\" onmouseover='showuserinfo();' onmouseout='hideuserinfo();'>用户中心</td></tr></table> ";
      
      html = html + "<script>var webid='" + webId + "';" + "var type='" + type + "';" + "var logout='" + logout + "';" + "var frlogout='" + frlogout + "';" + "var grinfo='" + grinfo + "';" + "var frinfo='" + frinfo + "';" + "</script>";
      html = html + cachehtml;
    }
    else
    {
      html = "<table border='0' cellpadding='0' cellspacing='0' ><tr><td  id='loginbtn' onmouseout='hidelogin();'  onmouseover='showlogin();'><a  style=\"cursor:pointer; font-size:13px; text-align:center;margin:0px;\" >登录</a><td style=\"text-align:center;width:20px;\" >|</td></td><td  onmouseover='showregedit();' onmouseout='hideregedit();' align='center' id='regeditbtn'><a style=\"cursor:pointer;text-decoration:none; font-size:13px; margin:0; \">注册</a></td></tr></table>";
      html = html + "<script>var grloginurl='" + grloginurl + "&backUrl=http://"+domain+"/gszw/member/login/getgrinfo.do"+"';" + "var grregediturl='" + 
        grregediturl + "?backUrl=http://"+domain+"';" + "var frloginurl='" + frloginurl + "&backUrl=http://"+domain+"/gszw/member/login/getfrinfo.do"+"';" + 
        "var frregediturl='" + frregediturl + "?backUrl=http://"+domain+"';" + "var grinfo='" + grinfo + "';" + "var frinfo='" + frinfo + "';" + "</script>";
      html = html + cachehtml;
    }
    return 
      callback + "({\"html\":\"" + html.replaceAll("[\\t\\n\\r]", " ").replaceAll("[\"]", "'") + "\"});";
  }
  
  @RequestMapping({"userresult_index"})
  @ResponseBody
  public String showUserResultIndex(String webId, String domain, String callback)
  {
    if ((!FilterUtil.isSqlOK(webId)) || (!FilterUtil.isSqlOK(callback)) || (!FilterUtil.isSqlOK(domain))) {
      return "参数有误！";
    }
    DataSourceSwitch.change("2");
    Member member = MemberSessionInfo.getCurrentMemberInfo();
    String grregediturl = this.properties.getString("regediturl");
    String grloginurl = this.properties.getString("loginurl") + "&domain=" + domain + "&gotoUrl=";
    String grinfo =  this.properties.getString("grinfo");
    String frregediturl = this.properties.getString("frregediturl");
    String frloginurl = this.properties.getString("frloginurl") + "&domain=" + domain + "&gotoUrl=";
    String frinfo = this.properties.getString("frinfo");
    String logout = this.properties.getString("logout") + domain;
    String frlogout = this.properties.getString("frlogout") + domain;
    String html = "";
    String cachehtml = (String)CacheUtil.getValue("htmls", "html");
    if (member != null)
    {
      String name = member.getRealname();
      String type = member.getType();
      String subName = "";
      if (name.length() > 15) {
        subName = name.substring(0, 14) + "...";
      } else {
        subName = name;
      }
      html = 
      


        html + "<table border='0' cellpadding='0' cellspacing='0'><tr><td style=\"font-size: 13px;text-align:center; line-height:32px; color:#fff;\" id='userinfo' >&nbsp;欢迎您：<a style=\"text-decoration:none; font-size:13px; margin:0;\" title='" + name + "'>" + subName + "</a></td><td style=\"text-align:center;width:20px; color:#fff;\" >|</td><td class='fenx' style=\"text-align:center;cursor: pointer;font-size: 13px; color:#fff;\" onClick=\"showgrzx();\" onmouseover='showuserinfo();' onmouseout='hideuserinfo();'>用户中心</td></tr></table> ";
      
      html = html + "<script>var webid='" + webId + "';" + "var type='" + type + "';" + "var logout='" + logout + "';" + "var frlogout='" + frlogout + "';" + "var grinfo='" + grinfo + "';" + "var frinfo='" + frinfo + "';" + "</script>";
      html = html + cachehtml;
    }
    else
    {
      html = "<table border='0' cellpadding='0' cellspacing='0' ><tr><td  id='loginbtn' onmouseout='hidelogin();' style=\"line-height:32px;\"  onmouseover='showlogin();'><a  style=\"cursor:pointer; font-size:13px; text-align:center;margin:0px;\" >登录</a><td style=\"text-align:center;width:20px;color:#fff;\" >|</td></td><td  onmouseover='showregedit();' onmouseout='hideregedit();' align='center' id='regeditbtn'><a style=\"cursor:pointer;text-decoration:none; font-size:13px; margin:0; \">注册</a></td></tr></table>";
      html = html + "<script>var grloginurl='" + grloginurl + "';" + "var grregediturl='" + 
        grregediturl + "';" + "var frloginurl='" + frloginurl + "';" + 
        "var frregediturl='" + frregediturl + "';" + "var grinfo='" + grinfo + "';" + "var frinfo='" + frinfo + "';" + "</script>";
      html = html + cachehtml;
    }
    return 
      callback + "({\"html\":\"" + html.replaceAll("[\\t\\n\\r]", " ").replaceAll("[\"]", "'") + "\"});";
  }
  
  @RequestMapping({"gotoIsauth"})
  public ModelAndView gotoIsauth()
  {
    ModelAndView m = new ModelAndView("gszw/bscx/member/smrz");
    Member member = MemberSessionInfo.getCurrentMemberInfo();
    String smrz = "";
    if (("1".equals(member.getType())) || ("个人".equals(member.getType()))) {
      smrz = this.properties.getString("grsmrz");
    } else if (("2".equals(member.getType())) || ("法人".equals(member.getType()))) {
      smrz = this.properties.getString("frsmrz");
    }
    if (smrz.indexOf("http://") == -1) {
      smrz = "http://" + smrz;
    }
    m.addObject("smrz", smrz);
    return m;
  }
  
  @RequestMapping({"fgwlogin"})
  public ModelAndView showLogin(HttpSession session, String url, String projectCode, String areacode, String domain, String sysapp, String urlflag)
  {
    ModelAndView modelAndView = new ModelAndView("gszw/bscx/member/login");
    session.setAttribute("action", "fgw");
    session.setAttribute("itemcode", projectCode);
    session.setAttribute("areacode", areacode);
    session.setAttribute("urlflag", urlflag);
    session.setAttribute("sysapp", sysapp);
    session.setAttribute("url2", url);
    url = this.properties.getString("frloginurl") + "&domain=" + domain + "&gotoUrl=";
    modelAndView.addObject("loginurl", url);
    return modelAndView;
  }
  
  @RequestMapping({"syjlogin"})
  public ModelAndView showlogin(HttpSession session, String domain, String sysapp)
  {
    ModelAndView modelAndView = new ModelAndView("gszw/bscx/member/login");
    if ((!FilterUtil.isUrlOK(domain)) || (!"yjxzsp".equals(sysapp)))
    {
      RedirectView redirectView = new RedirectView(
        BaseInfo.getContextPath() + 
        "/error/error.do");
      modelAndView.setView(redirectView);
      return modelAndView;
    }
    String url = "";
    Member member = MemberSessionInfo.getCurrentMemberInfo();
    if (member == null)
    {
      session.setAttribute("action", "syj");
      session.setAttribute("sysapp", sysapp);
      url = this.properties.getString("frloginurl") + "&domain=" + domain + "&gotoUrl=";
    }
    else
    {
      String usename = member.getLoginname();
      String type = member.getType();
      String src1 = this.properties.getString("sysp");
      String ticket1 = this.uumsService.getCorStByToken(member.getToken(), sysapp);
      url = src1 + "&ticket=" + ticket1 + "&spbs=" + sysapp + "&usename=" + usename + "&userType=" + type;
    }
    modelAndView.addObject("loginurl", url);
    return modelAndView;
  }
  
  @RequestMapping({"sltLogin"})
  public ModelAndView shuiLiLogin(HttpSession session, String url, String type, String domain, String sysapp)
  {
    ModelAndView modelAndView = new ModelAndView("gszw/bscx/member/login");
    String url1 = "";
    
    session.setAttribute("action", "slt");
    session.setAttribute("url2", url);
    session.setAttribute("sysapp", sysapp);
    session.setAttribute("domain", domain);
    if ("1".equals(type)) {
      url1 = "/gsjis/front/per/interface.do?action=ticketLogin&appmark=gszw&domain=" + domain + "&gotoUrl=";
    } else {
      url1 = "/gsjis/front/cor/interface.do?action=ticketLogin&appmark=gszw&domain=" + domain + "&gotoUrl=";
    }
    modelAndView.addObject("loginurl", url1);
    return modelAndView;
  }
  
  @RequestMapping(value="getTokenAndType")
  @ResponseBody
  public String getTokenAndType(HttpSession session) {
	  
	  String message = "";
	  String token = "";
	  String userType ="";
	  String ussToken = (String) session.getAttribute("ussToken");
	  String authCode =(String) session.getAttribute("authCode");
	  Member member = MemberSessionInfo.getCurrentMemberInfo();
	  if(member!=null) {
		  token = member.getToken();
		  userType = member.getType();
		  message = token + "hanweb" + userType + "hanweb" + ussToken + "hanweb" + authCode;
		  System.out.println("------获取到的token和用户类型为---------"+message);
	  }
	  return message;
  }
  
  /**
	 * 调用万维统一身份认证接口获取ticket,传给第三方
	 * 因为有些第三方还没有对接信任传递，为了业务平滑过渡，于是暂时由统一身份认证提供获取票据的接口
	 * 用于给第三方提供票据
	 * @return
	 */
	public  String getticketfromjis(HttpSession session){
		String appmark = this.properties.getString("appmarkSSO");
		String secretKey = properties.getString("secretKey");
		System.out.println("票据接口获取到的appmark是"+appmark);
		System.out.println("票据接口获取到的secretKey是"+secretKey);
		System.out.println("票据接口获取到的authCode是"+session.getAttribute("authCode"));
		HttpHeaders headers = new HttpHeaders();
		if(uumsSer == null){
			System.out.println("gddquwdquwdwuqdfwuqdffydwuwqf");
		}
		String accessToken = uumsSer.
		getAccessToken(appmark);
		System.out.println("accessToken======="+accessToken);
		headers.add("Authorization", "Bearer "+accessToken);
    HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		ResponseEntity<String> ticketjson;
		String ticket = "";
		try {
			ticketjson = restTemplate.exchange("http://61.178.65.73/api/sso/genCityTicket"
					+"?authCode="
					+SM4Utils.sm4EncryptECB(session.getAttribute("authCode").toString(), secretKey),
					HttpMethod.GET, 
					httpEntity,
					String.class);
			System.out.println("ticketjson========="+ticketjson);
			ticket = jiexiticketjson(ticketjson.getBody());
			System.out.println("ticket111========="+ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}
	public static String jiexiticketjson(String ticketjson){
		JSONObject  jsontokenObject = JSON.parseObject(ticketjson);
		System.out.println("jsontokenObject========="+jsontokenObject);
		String ticket = "";
		if(jsontokenObject.containsKey("data")){
			ticket = jsontokenObject.getString("data");
			System.out.println("ticket222========="+ticket);
		}
		return ticket;
	}
}
