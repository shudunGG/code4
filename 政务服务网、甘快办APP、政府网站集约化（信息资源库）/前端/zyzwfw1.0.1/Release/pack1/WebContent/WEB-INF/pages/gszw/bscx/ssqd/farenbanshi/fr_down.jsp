<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="h" uri="/hanweb-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv='X-UA-Compatible' content='IE=8' />
<meta http-equiv='X-UA-Compatible' content='IE=9' />
<meta http-equiv='X-UA-Compatible' content='IE=10' />
<meta http-equiv='X-UA-Compatible' content='IE=11' />
<title>法人办事</title>
<link rel="stylesheet" type="text/css" href="/gszw/resources/bscx/ssqd/images/bs_style.css">
<script src="/gszw/resources/bscx/ssqd/images/function.js" type="text/javascript" charset="utf-8"></script>
<style>
.childRtem span,.lists a{cursor: pointer;}
</style>
<script>
$(function(){
	  document.getElementById("count").innerText = '${count3}';
});
//跳转在线办理
function panduanshifoudengluAndTiaozhuan(taskCode,rowGuid,linkAddr,spbs,serverType,webid,taskname){
	var src=window.location.href;
	src=encodeURIComponent(src);
	var hostName = window.location.host;
	$.ajax({
	  url: "panduanshifoudengluAndTiaozhuan.do",
	  data:"taskCode=" + taskCode + "&rowGuid=" + rowGuid +"&linkAddr=" + encodeURIComponent(encodeURIComponent(linkAddr))+ "&spbs=" + 
	  spbs+ "&serverType=" + serverType+ "&webid=" + webid+"&taskname="+encodeURIComponent(encodeURIComponent(taskname)),
	  cache: false,
	  success: function(tiaozhuanAddr){
		  if("needlogin"==tiaozhuanAddr){
			  window.open('http://www.gszwfw.gov.cn/gsjis/front/corlogin?backUrl=http://'+hostName+'/gszw/member/login/getfrinfo.do');
			  }else if("gaishixianggerenbunengbanli" == tiaozhuanAddr){
				alert('该事项个人不能办理');
				}else{
					window.open(tiaozhuanAddr);
			}
	  }
	});
}
//展示办事指南
function showBanshizhinan(i_id,i_cataid,c_createtime){
	$.ajax({
	  url: "/gszw/banshizhinan/showBanshizhinan.do",
	  data:"c_createtime=" + c_createtime,
	  cache: false,
	  success: function(dateOfUrl){
		  var url = "/art"+dateOfUrl+"art_"+i_cataid+"_"+i_id+".html";
		  window.open(url);
	  }
	});
}
//跳转到上一页或者下一页
function gopage(number){
	var pageNum="${pageNum}";
	if(parseInt(number)>parseInt(pageNum)){
		return;
		}
	if(parseInt(number) <= 0){
		return;
		}
	var farenthemeId = document.getElementById("farenthemeId").innerText;
	var deptCode = document.getElementById("deptCode").innerText;
	var isonline = document.getElementById("isonline").innerText;
	var typeId = document.getElementById("typeId").innerText;
	var keyword = document.getElementById("keyword").value;
	var webid = $('#webid').val();
	$.ajax({
		  url: "frdown.do",
		  data:"farenthemeId=" + farenthemeId + "&deptCode=" + deptCode+"&isonline=" + isonline + "&keyword=" 
		  + encodeURIComponent(encodeURIComponent(keyword))
		   +"&number="+number+"&numOfOnePage=10"+"&webid="+webid+"&typeId="+typeId,
		  cache: false,	  
		  success: function(html){
		  $("#down").html(html);
		  }
		});
}
//跳转到指定页数
function goNumberPage(){
	var pageNum="${pageNum}";
	var numberOfPage = document.getElementById("numberOfPage").value;
	var farenthemeId = document.getElementById("farenthemeId").innerText;
	var deptCode = document.getElementById("deptCode").innerText;
	var isonline = document.getElementById("isonline").innerText;
	var typeId = document.getElementById("typeId").innerText;
	var keyword = document.getElementById("keyword").value;
	var webid = $('#webid').val();
	if(parseInt(numberOfPage)>parseInt(pageNum)){
		alert("您输入的页码超过上限！");
		return;
		}
	if(parseInt(numberOfPage) <= 0){
		alert("您输入的页码超过下限！");
		return;
		}
	$.ajax({
		  url: "frdown.do",
		  data:"farenthemeId=" + farenthemeId + "&deptCode=" + deptCode+"&isonline=" + isonline + 
		  "&keyword=" + encodeURIComponent(encodeURIComponent(keyword))
		   +"&number="+numberOfPage+"&numOfOnePage=10"+"&webid="+webid+"&typeId="+typeId,
		  cache: false,	  
		  success: function(html){
		  $("#down").html(html);
		  }
		});
}
fun();
function fun(){
   $(".childHtem_name").mouseover(function(){
   	$(".childHtem").hide();
       $(this).nextSibling.show();
   });
}
/**
 * 咨询投诉
 * @param webid
 * @param type
 * @param itemcode
 * @param orgcode
 * @param centercode
 * @return
 */
function frzxts(webid,type, itemcode, orgcode,centercode,itemname) {
	var hostName = window.location.host;
	var itemname = encodeURIComponent(encodeURIComponent(itemname));
	$.ajax( {
				url : "/gszw/ssqd/bmsx/bmsxzxts.do",
				context : document.body,
				datetype : "text",
				async : false,
				success : function(text) {
					if("needlogin" == text){
						window.open('http://www.gszwfw.gov.cn/gsjis/front/corlogin?backUrl=http://'+hostName+'/gszw/member/login/getfrinfo.do');
					}
					if("keyitiaozhuan" == text){
						if("0" == type){
							window.open("http://www.gszwfw.gov.cn/gszwfw/zxts/myconsult/add_notice.do?webid="+webid+"&orgcode="+orgcode+"&itemcode="+itemcode+"&centercode="+centercode+"&itemname="+itemname);
							}
						if("1" == type){
							window.open("http://www.gszwfw.gov.cn/gszwfw/zxts/mycomplain/add_notice.do?webid="+webid+"&orgcode="+orgcode+"&itemcode="+itemcode+"&centercode="+centercode+"&itemname="+itemname);
						}
						if("2" == type){
							window.open("http://www.gszwfw.gov.cn/gszwfw/zxts/mysuggest/add_notice.do?webid="+webid+"&orgcode="+orgcode+"&itemcode="+itemcode+"&centercode="+centercode+"&itemname="+itemname);
						}
					}
				}
			});
}
/**
 * 收藏
 * @param itemcode
 * @param itemname
 * @param webid
 * @return
 */
function grcollect(itemcode,itemname,webid) {
	var rand = Math.random();
	var hostName = window.location.host;
	$.ajax({
				url : "/gszw/ssqd/collect/collectitem.do?itemcode="
						+ itemcode
						+ "&itemname="
						+ encodeURIComponent(encodeURIComponent(itemname))
						+ "&webid="
						+ webid,
				async : false,
				success : function(text) {
					if (text == "qingxiandenglu") {
						window.open('http://www.gszwfw.gov.cn/gsjis/front/perlogin?backUrl=http://'+hostName+'/gszw/member/login/getgrinfo.do');
					} else {
						if(text == "shoucangchenggong"){
							alert("收藏成功");
							}
						if(text == "yishoucang"){
							alert("已收藏");
							}
						if(text == "shoucangshibai"){
							alert("收藏失败");
							}
					}
				}
			});
}
</script>
</head>
<body>
		<ul class="lists">
		<c:forEach var="shishiqingdan" items="${shishiqingdanHavaYewubanlixianglist}">
		<c:if test="${empty shishiqingdan.yewubanlixiangList}">
			<li class="child_1">
	
				<div class="childRtem">
				<c:if test="${fn:length(shishiqingdan.taskname)<=29}">
					<span onclick="showBanshizhinan('${shishiqingdan.i_id}',
						'${shishiqingdan.i_cataid}',
						'${shishiqingdan.c_createtime }')" style="font-size: 19px;">${shishiqingdan.taskname}</span>
				</c:if>
				<c:if test="${fn:length(shishiqingdan.taskname)>29}">
					<span onclick="showBanshizhinan('${shishiqingdan.i_id}',
						'${shishiqingdan.i_cataid}',
						'${shishiqingdan.c_createtime }')" style="font-size: 19px;" title="${shishiqingdan.taskname }">${fn:substring(shishiqingdan.taskname,0,29)}...</span>
				</c:if>
					<div class="BL">
					<c:if test="${shishiqingdan.linkAddr != ''}">
            			<a class="bak1" onclick=
            			"panduanshifoudengluAndTiaozhuan('${shishiqingdan.taskCode}','${shishiqingdan.rowGuid}',
            			'${shishiqingdan.linkAddr}',
            			'tysb','2','${shishiqingdan.i_webid}','${shishiqingdan.taskname}');">在线办理</a>
           			</c:if>
						<a class="bak2" onclick="showBanshizhinan('${shishiqingdan.i_id}',
						'${shishiqingdan.i_cataid}',
						'${shishiqingdan.c_createtime }')">办事指南</a>
						</div>
					
				</div>
				<div class="childItem">
					<a class="bak4" onclick="grcollect('${shishiqingdan.rowGuid}','${shishiqingdan.taskname}','${shishiqingdan.i_webid}')">我要收藏</a>
					<a class="bak5" onclick="frzxts('${shishiqingdan.i_webid}','0','','','001002003002','${shishiqingdan.taskname}')">我要咨询</a>								
					<c:if test="${not empty shishiqingdan.rowGuid}">
				<a class="bak5" onclick=
               			"panduanshifoudengluAndTiaozhuan('${shishiqingdan.taskCode}','${shishiqingdan.rowGuid}',
	           			'/gszw/zxyy/index.do?webid=${webid}&unid=${shishiqingdan.rowGuid}',
	           			'tysb',
	           			'2','${shishiqingdan.i_webid}','${shishiqingdan.taskname}');"  target="_blank">我要预约</a>								
				</c:if>
					<!-- <a class="bak3">查看评价</a><a class="bak4">我要收藏</a><a class="bak5">我要咨询</a> -->								
				</div>
			</li>
		</c:if>
		<c:if test="${!empty shishiqingdan.yewubanlixiangList}">
		<li class="child_2">
			<div class="childRtem childTtem">
				<c:if test="${fn:length(shishiqingdan.taskname)<=25}">
					<span>${shishiqingdan.taskname}</span>
				</c:if>
				<c:if test="${fn:length(shishiqingdan.taskname)>25}">
					<span title="${shishiqingdan.taskname }">${fn:substring(shishiqingdan.taskname,0,25)}...</span>
				</c:if>						
				<a class="show_child"></a>								
			</div>
			<ul class="child_2h">
			<c:forEach var="yewubanlixiang" items="${shishiqingdan.yewubanlixiangList}">
				<li>
					<span class="childHtem_name">${yewubanlixiang.taskname}</span>
					<div class="childHtem">
						<span>办理机构：${yewubanlixiang.deptName} </span>
						<div class="childHtem2">
						<c:if test="${yewubanlixiang.linkAddr != ''}">
						<a class="ico"
						 onclick=
            			"panduanshifoudengluAndTiaozhuan('${yewubanlixiang.taskCode}','${yewubanlixiang.rowGuid}',
            			'${yewubanlixiang.linkAddr}',
            			'tysb','2',
            			'${shishiqingdan.i_webid}','${yewubanlixiang.taskname}');">在线办理</a></c:if>
						<a class="ico" onclick="showBanshizhinan('${yewubanlixiang.i_id}',
						'${yewubanlixiang.i_cataid}',
						'${yewubanlixiang.c_createtime }')">办事指南</a>
						<a class="ico" onclick="grcollect('${yewubanlixiang.rowGuid}','${yewubanlixiang.taskname}','${shishiqingdan.i_webid}')">我要收藏</a>
						<a class="ico" onclick="frzxts('${shishiqingdan.i_webid}','0','','','001002003002','${yewubanlixiang.taskname}')">我要咨询</a>
						<c:if test="${not empty yewubanlixiang.rowGuid}">
				<a class="ico" onclick=
               			"panduanshifoudengluAndTiaozhuan('${yewubanlixiang.taskCode}','${yewubanlixiang.rowGuid}',
	           			'/gszw/zxyy/index.do?webid=${webid}&unid=${yewubanlixiang.rowGuid}',
	           			'tysb',
	           			'${yewubanlixiang.xiugaiServerType}','${yewubanlixiang.i_webid}','${yewubanlixiang.taskname}');"  target="_blank">我要预约</a>								
				</c:if>
						<!--<a class="ico">查看评价</a><a class="ico">我要收藏</a><a class="ico">我要咨询</a>-->
					</div>																		
					</div>
				</li>
			</c:forEach>
			</ul>
		</li>
		</c:if>
		</c:forEach>
		</ul>
		<div class="pages">
		<c:if test="${!empty shishiqingdanHavaYewubanlixianglist}">
			<span>共<a>${pageNum}</a>页</span>
			<span>到第<input style="border:1px solid #ccc" id="numberOfPage" type="text"
			 oninput='this.value=this.value.replace(/^[0]/gi,"")'  onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="4"
			 value="${number}"/>页</span><button onclick="goNumberPage()" class="sure">确定</button>
			<button onclick="gopage('${number-1}')">上一页</button>
			<button onclick="gopage('${number+1}')">下一页</button>
		</c:if>
		</div>
</body>
</html>