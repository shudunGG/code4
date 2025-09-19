<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="h" uri="/hanweb-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title>在线预约_张掖政务服务网</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv='X-UA-Compatible' content='IE=8' />
<meta http-equiv='X-UA-Compatible' content='IE=9' />
<meta http-equiv='X-UA-Compatible' content='IE=10' />
<meta http-equiv='X-UA-Compatible' content='IE=11' />
<h:head loadmask="true" calendar="true" select="true" ></h:head>
	<meta name="renderer" content="webkit">
	<link href="${contextPath }/resources/zxyy/images/hanweb.css" rel="stylesheet">
	<link href="${contextPath }/resources/zxyy/images/yysx.css" rel="stylesheet">
	<link rel="stylesheet" href="${contextPath }/resources/zxyy/images/kalendae.css">
	<script type="text/javascript" src="${contextPath }/resources/zxyy/images/jquery.js"></script>
	<link rel="stylesheet" href="${contextPath }/resources/zxyy/images/smohan.pop&share.css">
</head>
<body>
	<script src="/script/${top}"></script>
	<script src="/script/${banner }"></script>
	<script src="/script/${nav }"></script>
	<div class="main">
		<div class="clearfix" style="width:1000px;margin: 0 auto">
			<div class="side bt-left">
				<div class="side_lt">
					<a href="/">首页</a>>在线预约
				</div>
			</div>
			<div class="xqcon">
				<div class="xqtitle">
					<div class="htitlt clearfix">
						<h1>${itemEntity.vc_name }</h1>
						<div class="bt-left">
							事项编码：${itemEntity.field_3658 }
						</div>
					</div>
				</div>
				<div style="display: block;" class="section-content">
					<div class="bsxqtit">
						<h1>
							预约时间
						</h1>
					</div>
					<div style="line-height:30px">预约人数/可预约总数</div>
					<div class="time-table" id="time-table">
							<table id="yytable">
								<thead>
									<tr class="color1">
										<th></th>
										<c:forEach items="${datelist}" var="date">
										<th>${date}</th>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="color1" id ="forenoon">${noon }</td>
										 <c:forEach items="${datelist}" var="date">
										 <td class="book">
											 <input type="hidden" value="${date}" /><a>0</a>/200
										</td>
										</c:forEach>
									</tr>
									<tr>
										<td class="color1" id ="afternoon">${afternoon }</td>
										 <c:forEach items="${datelist}" var="date">
										 <td class="book">
											 <input type="hidden" value="${date}" /><a>0</a>/200
										</td>
										</c:forEach>
									</tr>
								</tbody>
							</table>
						</div>
				</div>
				<div style="display: block;" class="section-content">
					<div class="bsxqtit">
						<h1>
							预约人员
						</h1>
					</div>
					<div class="section">
						<div class="yyry">
						<c:if test="${member.type == 1 }">
						<div class="clearfix">
								<span style="margin-left: 13px;">申报人：</span> <input readonly="readonly" id="sbr" class="sbr ss" type="text" value="${member.realname }">
							</div>
							<div class="clearfix" style="margin-top: 15px;">
							   <!-- 脱敏 -->
								<span>身份证号：</span> <input readonly="readonly" id="sfz" class="sbr ss" type="text" value='<c:if test="${fn:length(member.cardid)>10}">${fn:replace(member.cardid,fn:substring(member.cardid, 3, fn:length(member.cardid)-3), "************")}</c:if><c:if test="${fn:length(member.cardid)<10}">${member.cardid }</c:if>'>
							</div>
							<div class="clearfix" style="margin-top: 15px;">
								<span>联系电话：</span> <input readonly="readonly" id="phone" class="sbr ss" type="text" value='<c:if test="${fn:length(member.phone) gt 10}">${fn:replace(member.phone,fn:substring(member.phone, 3, fn:length(member.phone)-3),"******")}</c:if><c:if test="${fn:length(member.phone) lt 10}">${member.phone }</c:if>'>
							</div>
						</c:if>
						
						<c:if test="${member.type == 2 }">
						<div class="clearfix">
								<span >申报主体：</span> <input readonly="readonly" id="sbr" class="sbr ss" type="text" value="${member.realname }">
							</div>
							<div  class="clearfix" style="margin-top: 15px;">
								<span style="margin-right: 0px;">信用代码：</span> <input readonly="readonly" id="sfz" class="sbr ss" type="text" value='<c:if test="${fn:length(member.corpCertNum) gt 10}">${fn:replace(member.corpCertNum,fn:substring(member.corpCertNum, 3, fn:length(member.corpCertNum)-3), "************")}</c:if><c:if test="${fn:length(member.corpCertNum) lt 10}">${member.corpCertNum }</c:if>'>
							</div>
							<div class="clearfix" style="margin-top: 15px;">
							<span>联系电话：</span> <input readonly="readonly" id="phone" class="sbr ss" type="text" value='<c:if test="${fn:length(member.phone) gt 10}">${fn:replace(member.phone,fn:substring(member.phone, 3, fn:length(member.phone)-3),"******")}</c:if><c:if test="${fn:length(member.phone) lt 10}">${member.phone }</c:if>'>
                            </div>
						</c:if>
							<a id="tj" style="cursor:pointer;">提交申请</a>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="moudel hide" style="position: relative; height: 100vh; width: 100vh;">
			<div class="moudel-bg"></div>
			<div id="moudel">
				<div class="xq_moudel">
						<a href="javascript:;" id="close_moudel"></a>
						<div class="main2">
								<p id = "moudel_date">日期：2020-04-10</p>
								<p id = "moudel_houro">时间：09:00~12:00<span class="recommend">(此时段从大数据推算较为空闲)</span></p>
								<p>预约事项：${itemEntity.vc_name }</p>
								<div class="btns clearfix">
										<span class="choose left">选择时段</span>
										<span class="think right">我再考虑一下</span>
								</div>
						</div>
				</div>
			</div>
		</div>
	<script src="/script/${end}"></script>
	<script src="${contextPath }/resources/zxyy/images/jquery.min.js" type="text/javascript"></script>
	<script src="${contextPath }/resources/zxyy/images/smohan.pop&share.js" type="text/javascript"></script>
	<script>
	$(function() {
		//根据时间判断是否可以预约当天
		 var a =${hourofday};
		var strB = new String("${newday}");
		var strA = new String($("#yytable").find("th:eq(1)").html());
	  if(a>8 && a <14 && strA.indexOf(strB) == 0){
		  var a = $("#yytable").find("tr:eq(1)").children("td:eq(1)").css("pointer-events","none").css("background-color","#e9f2fb");
	  }else if(a >=14 && strA.indexOf(strB) == 0){
		  $("#yytable").find("tr:eq(1)").children("td:eq(1)").css("pointer-events","none").css("background-color","#e9f2fb");
		  $("#yytable").find("tr:eq(2)").children("td:eq(1)").css("pointer-events","none").css("background-color","#e9f2fb");
	  } 
	  count('${itemEntity.field_3651 }');
	});
			/*选择时间段  */
			/* $(".book").click(function () { */
			$(".book").live("click",function(){
			//弹出选择框
			$(".moudel").removeClass("hide");
			//日期
			$("#moudel_date").html('时间：'+$(this).children('input').val());
			//小时
		    $("#moudel_houro").html('时间：'+$(this).parent().find("td:eq(0)").text()+'<span class="recommend">(此时段从大数据推算较为空闲)</span>');
		//改变样式
		$(".checked").find('a').html(Number($(".checked").find('a').html())-1)
		$(".checked").attr("class","book");
		$(this).attr("class","checked");
			});
			//关闭选择框
			$("#close_moudel").click(function() {
				$(".moudel").addClass("hide");
				$(".checked").attr("class","book");
			});
			//我再考虑一下
			$(".think").click(function() {
				 $(".moudel").addClass("hide");
				 $(".checked").attr("class","book");
			});
			//选择时间段
			$(".choose").click(function() {
				 $(".moudel").addClass("hide");
				 $(".checked").find('a').html(Number($(".checked").find('a').html())+1)
			});
			
			//提交申请
			$("#tj").click(function(){
			//获取预约时间
			var date = $(".checked").children('input').val();
			var houre =$(".checked").parent().children("td:eq(0)").text();
			if(date == undefined || houre ==''){
				alert('请选择预约时间');
			}else{
				$.ajax({
				    url: "submit.do",
				    data:"date="+date+"&houre="+houre,
				    type: "GET",
				    success: function(data) {
						if(data.success){
							window.location.href= "success.do";
						}else{
							alert(data.message);
						}
				    }
				});
			}
			});
		
			function count(unid){
                   $.ajax({
				  url: "zxyycountByUnid.do?unid="+unid,
				  type: "GET",
				  cache: false,
				  success: function(data){
					if(data.success){
						$("#yytable").empty();
						$("#yytable").html(data.params.str);
						//console.log(data.params.str);
						 var a =${hourofday};
							var strB = new String("${newday}");
							var strA = new String($("#yytable").find("th:eq(1)").html());
						  if(a>8 && a <14 && strA.indexOf(strB) == 0){
							  var a = $("#yytable").find("tr:eq(1)").children("td:eq(1)").css("pointer-events","none").css("background-color","#e9f2fb");
						  }else if(a >=14 && strA.indexOf(strB) == 0){
							  $("#yytable").find("tr:eq(1)").children("td:eq(1)").css("pointer-events","none").css("background-color","#e9f2fb");
							  $("#yytable").find("tr:eq(2)").children("td:eq(1)").css("pointer-events","none").css("background-color","#e9f2fb");
						  } 
					}
				  }
				});
			};
	</script>
</body>
</html>