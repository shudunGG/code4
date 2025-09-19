<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="h" uri="/hanweb-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title>打印预约凭证_张掖政务服务网</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv='X-UA-Compatible' content='IE=8' />
<meta http-equiv='X-UA-Compatible' content='IE=9' />
<meta http-equiv='X-UA-Compatible' content='IE=10' />
<meta http-equiv='X-UA-Compatible' content='IE=11' />
<h:head loadmask="true" calendar="true" select="true" ></h:head>
	<meta name="renderer" content="webkit">
	<link href="${contextPath }/resources/zxyy/images/hanweb.css" rel="stylesheet">
	<link href="${contextPath }/resources/zxyy/images/pzbg.css" rel="stylesheet">
	<script src="${contextPath }/resources/zxyy/images/jquery.js"></script>
</head>
<body>
<!--startprint1-->
	<div style="width:100%;margin-top:100px">
		<div class="main">
			<div class="tit">
				<h3>预约凭证</h3>
			</div>
			<table class="tab">
				<tr>
					<td style="width: 120px;">预约事项</td>
					<td style="width: 480px;">${itemEntity.vc_name }</td>
				</tr>
				<tr>
					<td style="width: 120px;">预约日期</td>
					<td style="width: 480px;">${date }</td>
				</tr>
				<tr>
						<td style="width: 120px;">预约时间</td>
						<td style="width: 480px;">${houre }</td>
				</tr>
				<tr>
						<td style="width: 120px;">预约人</td>
						<td style="width: 480px;">${member.realname }</td>
				</tr>
				<tr>
						<c:if test="${member.type == 1 }">
						<td style="width: 120px;">身份证号</td>
						<td style="width: 480px;">${member.cardid }</td>
						</c:if>
						<c:if test="${member.type == 2 }">
						<td style="width: 120px;">信用代码</td>
						<td style="width: 480px;">${member.corpCertNum }</td>
						</c:if>
						
				</tr>
				<tr>
						<td style="width: 120px;">预约机构</td>
						<td style="width: 480px;">${itemEntity.field_3663}</td>
				</tr>
				<tr>
						<td style="width: 120px;">办事地址</td>
						<td style="width: 480px;">${itemEntity.field_3686}</td>
				</tr>
			</table>

		</div>
	</div>
<!--endprint1-->	
	
	<center>
					<a onclick="preview(1)" style="background: #fe8c12 none repeat scroll 0 0;
						border-radius: 3px;
						color: #fff;
						display: inline-block;
						font-size: 16px;
						font-weight: 700;
						height: 35px;
						line-height: 35px;
						text-align: center;
						text-decoration: none;
						width: 100px; margin-top:30px; cursor:pointer">打 印</a> 
					</center>	
	<script>
			function preview(oper) { 
			if (oper < 10){ 
			bdhtml=window.document.body.innerHTML;//获取当前页的html代码 
			sprnstr="<!--startprint"+oper+"-->";//设置打印开始区域 
			eprnstr="<!--endprint"+oper+"-->";//设置打印结束区域 
			prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+18); //从开始代码向后取html 
			
			prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));//从结束代码向前取html 
			window.document.body.innerHTML=prnhtml; 
			window.print(); 
			window.document.body.innerHTML=bdhtml; 
			
			}
			 else{ 
			window.print(); 
			} 
			
			}
			
			 //设置网页打印的页眉页脚为空
			 function pagesetup_null(){
				 try{
					 var RegWsh = new ActiveXObject("WScript.Shell")
					 hkey_key="header"
					 RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"")
					 hkey_key="footer"
					 RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"")
				 }catch(e){}
			 }
			</script> 
</body>
</html>