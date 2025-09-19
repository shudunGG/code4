<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="h" uri="/hanweb-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title>预约成功_张掖政务服务网</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv='X-UA-Compatible' content='IE=8' />
<meta http-equiv='X-UA-Compatible' content='IE=9' />
<meta http-equiv='X-UA-Compatible' content='IE=10' />
<meta http-equiv='X-UA-Compatible' content='IE=11' />
<h:head loadmask="true" calendar="true" select="true" ></h:head>
	<meta name="renderer" content="webkit">
	<link href="${contextPath }/resources/zxyy/images/hanweb.css" rel="stylesheet">
	 <link href="${contextPath }/resources/zxyy/images/yyxq.css" type="text/css" rel="stylesheet">
	<link rel="stylesheet" href="${contextPath }/resources/zxyy/images/kalendae.css">
	<script type="text/javascript" src="${contextPath }/resources/zxyy/images/jquery.js"></script>
	<link rel="stylesheet" href="${contextPath }/resources/zxyy/images/smohan.pop&share.css">
	  <style>
        #section1 table td {
            font-size: 17px;
        }

        #section3 table td {
            font-size: 17px;
        }

        .side {
            height: 67px;
            width: 1000px;
            margin: 0 auto;
        }

        .side_lt {
            font-size: 17px;
            line-height: 67px;
        }

        .list-paddingleft-2 li {
            width: 100%;
            height: 45px;
            line-height: 45px;
            border-bottom: 1px solid #d4d4d4;
            padding-left: 34px;
            font-size: 15px;
        }

        .cl1 {
            float: left;
            display: block;
            text-align: center;
        }

        .cl2 {
            float: left;
            display: block;
            text-align: center;
            color: #e11317;
            width: 125px;
        }

        .cl3 {
            float: left;
            display: block;
            text-align: left;
            width: 535px;
        }

        .cl4 {
            float: left;
            display: block;
            text-align: center;
            width: 165px;
        }

        .cl5 {
            float: left;
            display: block;
            text-align: right;
            width: 75px
        }
        #define {
            margin-left: 20px;
        }
        #section3 #clsb  td,#section3 #clsb  th{font-size:15px;padding:5px}
        #section3 #clsb  th{line-height:35px;background: #f5f5f5;}
    </style>
</head>
<body>
   <script src="/script/${top}"></script>
	<script src="/script/${banner}"></script>
	<script src="/script/${nav}"></script>

    <div class="warp cf">
        <div class="warp_con">
            <div class="side">
                <div class="side_lt">
                    <a href="/">首页</a>>在线预约
                </div>
            </div>
            <div class="xqcon">
                <div class="xqtitle">
                    <div class="htitlt">
                        <h1>
                        ${itemEntity.vc_name}
                        </h1>
                        <div class="bt-left">
                            事项编码：${itemEntity.field_3658}
                         
                        </div>
                    </div>
                </div>

                <div style="float: left;width:950px;">
                    <div style="display: block;" class="sdyj_tit section-content cf">
                        <div class="bsxqtit">
                            <h1>
                                预约提醒
                            </h1>
                        </div>
                        <div class="section fl" id="section1">
                            <img src="${contextPath }/resources/zxyy/images/success.png" width="58" height="58">
                            <p>您已成功完成预约，预约信息如下。请在预约时间内前往大厅进行取号。</p>
                            <p>请保持您的联系电话畅通，我们会在您的预约时间前给您发送您的预约提醒，请注意关注。</p>
                        </div>
                        <div class="dy fl">
                            <a class="dybtn" href="print.do?date=${date}&houre=${houre}" target="_blank">打印预约凭证</a>
                        </div>
                    </div>
                    <div style="display: block;" class="sdyj_tit section-content">
                        <div class="bsxqtit">
                            <h1>
                                预约信息
                            </h1>
                        </div>
                        <div class="section" id="section2">
                            <ul>
                                <li><span>预约日期：${date }</span></li>
                                <li><span>预约时间：${houre }</span></li>
                                <li><span>办事地址：${itemEntity.field_3686 }</span></li>
                            </ul>
                        </div>
                    </div>
                    <div style="display: block;" class="sdyj_tit section-content">
                        <div class="bsxqtit">
                            <h1>
                                办件提示
                            </h1>
                            <span>根据您所选择的情形，办理该事项，您需要携带如下材料</span>
                        </div>
                        <div class="section" id="section3">
                          <c:if test="${empty itemEntity.field_3723 }">
                          暂无提示</c:if>
                            <c:if test="${not empty itemEntity.field_3723 }">
                            ${itemEntity.field_3723 }
                             </c:if>
                        </div>
                    </div>
                    <div class="tjbd">
                        <form class="cf">
                            <a class="fl"  id="cancel" style="cursor:pointer;" onclick="showBanshizhinan('${itemEntity.i_id}','${itemEntity.i_cataid}','${itemEntity.c_createtime}')">我明白了</a>
                            <a class="fl" id="define" style="cursor:pointer;">取消预约</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

  <script src="/script/${end}"></script>
    <script src="${contextPath }/resources/zxyy/images/jquery.min.js" type="text/javascript"></script>
    <script src="${contextPath }/resources/zxyy/images/smohan.pop&share.js" type="text/javascript"></script>
<script type="text/javascript">
$("#define").click(function(){
	$.ajax({
	    url: "cancel.do",
	    type: "POST",
	    dataType: "json",
	    success: function(data) {
			var a = data.success;
			if(data.success){
				window.location.href= data.message;
			}else{
				alert(data.message);
			}
	    }
	});
})

function showBanshizhinan(i_id,i_cataid,c_createtime){
	$.ajax({
	  url: "${contextPath}/banshizhinan/showBanshizhinan.do",
	  data:"c_createtime=" + c_createtime,
	  cache: false,
	  success: function(dateOfUrl){
		  var url = "${zxyyservicename}/art"+dateOfUrl+"art_"+i_cataid+"_"+i_id+".html";
		  window.location.href = url;
	  }
	});
}
</script>
</body>
</html>