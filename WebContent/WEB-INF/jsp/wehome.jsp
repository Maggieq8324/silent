<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/weinclude.jsp"%>
<title>营销现场作业安全管理</title>
</head>
<body>
<div class="page">
    <div class="page__bd" style="height: 100%;">
        <div class="weui-tab">
            <div id="mainframepanel" class="weui-tab__panel">
            	<div id="mainframe_header"  class="page" style="width:100%;height:5px;display: none;"  style="background-color: #E8F7FF;">
            	
            	</div>
				<iframe scrolling="auto" id="mainframe_navigation" frameborder="0" src="" style="width:100%;height:100%;"></iframe>
				<div id="mainframe_wdtabbar"  class="page" style="width:100%;height:100%;display: none;overflow:auto;">
					<div class="page__hd" style="margin: 5px;">
				    	<h1 class="page__title"style=""></h1>
				        <p class="page__desc" ></p>
				    </div>
				    <div class="page__bd" style="margin: 5px;">
				    	<div class="weui-panel weui-panel_access">
				            <div class="weui-panel__bd">
				                <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
				                    <div class="weui-media-box__hd">
				                        <img class="weui-media-box__thumb" src="${ctx}/we/img/my.png" alt="">
				                    </div>
				                    <div class="weui-media-box__bd">
				                       <h4 class="weui-media-box__title">${loginInfo.czymc}</h4>
				                       <p class="weui-media-box__desc">账号：${loginInfo.dlzh}</p>
				                      
				                    </div>
				                </a>
				            </div>
       					</div>
				    	<div class="weui-cells">
					     	<div class="weui-cell">
					           <div class="weui-cell__bd">
					     			<p>组织机构:</p>
					           </div>
					           <div class="weui-cell__ft">
					           		<p id="wdsz_zzjg" style="font-size: 15px;color: #888888;">${loginInfo.zzmc}</p>
					           </div>
					     	</div>
					     	<div class="weui-cell">
					           <div class="weui-cell__bd">
					     			<p>岗位名称:</p>
					           </div>
					           <div class="weui-cell__ft">
					           		<p id="wdsz_gwmc" style="font-size: 15px;color: #888888;">${loginInfo.gwmc}</p>
					           </div>
					     	</div>
					     </div>
				    </div>
				    <div class="page__hd" style="margin: 5px;">
				    	<h1 class="page__title" style="font-size: 15px;color: #888888;">重置密码：</h1>
				        <p class="page__desc" ></p>
				    </div>
				    <div class="page__bd" style="margin: 5px;">
				    	<form id="wdtabbar_modifyPasswordForm" method="post">
					        <div class="weui-cells">
					            <div class="weui-cell">
					                <div class="weui-cell__bd">
					                    <input class="weui-input" id="modifyPassword_ymm" name="ymm" type="password" placeholder="请输入原密码"/>
					                </div>
					            </div>
					        </div>
					        <div class="weui-cells">
					            <div class="weui-cell">
					                <div class="weui-cell__bd">
					                    <input class="weui-input" id="modifyPassword_xmm" name="xmm" type="password" placeholder="请输入新密码"/>
					                </div>
					            </div>
					        </div>
					        <div class="weui-cells">
					            <div class="weui-cell">
					                <div class="weui-cell__bd">
					                    <input class="weui-input" id="modifyPassword_qrxmm" name="qrxmm" type="password" placeholder="请确认新密码"/>
					                </div>
					            </div>
					        </div>
					        <div class="weui-btn-area">
					            <a class="weui-btn weui-btn_primary" id="modifyPassword_commit" href="javascript:commit_modifyPassword();">提交</a>
					        </div>
				        </form>
				    </div>
				    <div class="page__bd">
				        <div class="weui-btn-area">
				            <a class="weui-btn weui-btn_warn" href="javascript:commit_relogin();">重新登录</a>
				        </div>
				    </div>

				</div>
            </div>
            <div id="frametabbar" class="weui-tabbar" style="background-color: white;">
            	<a href="javascript:tabbaropen('sytabbar');" id="sytabbar" class="weui-tabbar__item weui-bar__item_on" style="background-color: #E8F7FF;">
                    <span style="display: inline-block;position: relative;">
                        <img src="${ctx}/we/img/home.png" alt="" class="weui-tabbar__icon" width="25" height="25">
                    </span>
                    <p class="weui-tabbar__label">首页</p>
                </a>
                <a href="javascript:tabbaropen('xctabbar');" id="xctabbar" class="weui-tabbar__item weui-bar__item_on"">
                    <span style="display: inline-block;position: relative;">
                        <img src="${ctx}/we/img/rw.png" alt="" class="weui-tabbar__icon" width="25" height="25">
                        <!-- <span class="weui-badge" id="rwglBadge" style="position: absolute;top: -2px;right: -14px;"></span> -->
                    </span>
                    <p class="weui-tabbar__label">任务管理</p>
                </a>
                <a href="javascript:tabbaropen('zztabbar');" id="zztabbar" class="weui-tabbar__item weui-bar__item_on">
                	<span style="display: inline-block;position: relative;">
                       	<img src="${ctx}/we/img/update.png" alt="" class="weui-tabbar__icon" width="25" height="25">
                        <!-- <span class="weui-badge" id="wtglBadge" style="position: absolute;top: -2px;right: -14px;"></span> -->
                    </span>
                    
                    <p class="weui-tabbar__label">问题管理</p>
                </a>
                <a href="javascript:tabbaropen('zdtabbar');" id="zdtabbar" class="weui-tabbar__item weui-bar__item_on">
                    <span style="display: inline-block;position: relative;">
                        <img src="${ctx}/we/img/gj.png" alt="" class="weui-tabbar__icon" width="25" height="25">
                        <!-- span class="weui-badge weui-badge_dot" style="position: absolute;top: 0;right: -6px;"></span> -->
                    </span>
                    <p class="weui-tabbar__label">辅助功能</p>
                </a>
                <a href="javascript:tabbaropen('wdtabbar');" id="wdtabbar" class="weui-tabbar__item weui-bar__item_on">
                    <img src="${ctx}/we/img/my.png" alt="" class="weui-tabbar__icon" width="25" height="25">
                    <p class="weui-tabbar__label">我的设置</p>
                </a>
            </div>
        </div>
    </div>
</div>
<div class="js_dialog" id="iosDialog" style="display: none;">
    <div class="weui-mask"></div>
    <div class="weui-dialog">
        <div class="weui-dialog__bd" id="iosDialog_content">弹窗内容，告知当前状态、信息和解决方法，描述文字尽量控制在三行内</div>
        <div class="weui-dialog__ft">
            <a href="javascript:;" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
        </div>
    </div>
</div>
<audio src="${ctx}/we/tishi.mp3"  id="mp3player" style="display: none;">
Your browser does not support the audio tag.
</audio>
<script type="text/javascript">

function showDialog(message){
	$('#iosDialog_content').text(message);
	$('#iosDialog').fadeIn(200);
}

function queryDclrw(){
	var home_page=1,home_rows=20,home_sort="tjsj",home_order="desc";
	var _zzbm = '${loginInfo.zzbm}';
	var args = "";
	args = "page="+home_page+"&rows="+home_rows+"&sort="+home_sort+"&order="+home_order+"&zzbm="+_zzbm+"&gzpclzt=3";
	
	$.post('${ctx}/dclrw/GzpxxQuery', args, function(data){
		
 		if(data.error){
 			//showDialog(data.error);

 			return;
         } else {
        	var total = data.total;

        	if (total > 0){
        		var x = document.getElementById("mp3player");
        		x.play();
        	}
         }
	});
}


function initDialog(){
    $('#iosDialog').on('click', '.weui-dialog__btn', function(){
        $(this).parents('.js_dialog').fadeOut(200);
    });
        	
    $("#mainframe_navigation").attr("src", "${ctx}/navigation?link=homePage/homePage");

    
	if ('${loginInfo.gwytdm}' != '2'){
    	window.setInterval(queryDclrw, 60000);
	}
}



function tabbaropen(tabbar){	
	$("#frametabbar").find("a.weui-tabbar__item").css("background-color", "white");	
	$("#"+tabbar).css("background-color", "#E8F7FF");
	
	var url_xctabbar = "${ctx}/navigation?link=dclrw/dclrw";
	var url_zztabbar = "${ctx}/navigation?link=wtcl/aqwtcl";
	var url_zdtabbar = "${ctx}/navigation?link=cxtj/cxtjcd";
	var url_sytabbar = "${ctx}/navigation?link=homePage/homePage";
	
	//mainframe_wdtabbar
	if (tabbar == "wdtabbar"){
		$("#wdsz_tx").attr("src", "${ctx}/we/img/my.png");
		$("#mainframe_navigation").hide();
		$("#mainframe_navigation").attr("src", "about:blank;");
		$("#mainframe_wdtabbar").show();
		
	} else {
		$("#mainframe_wdtabbar").hide();
		$("#mainframe_navigation").show();
		if (tabbar == "xctabbar"){
			$("#mainframe_navigation").attr("src", url_xctabbar);
		}
		
		if (tabbar == "zztabbar"){
			$("#mainframe_navigation").attr("src", url_zztabbar);
		}
		
		if (tabbar == "zdtabbar"){
			$("#mainframe_navigation").attr("src", url_zdtabbar);
		}
		
		if (tabbar == "sytabbar"){
			$("#mainframe_navigation").attr("src", url_sytabbar);
		}
	}
}

function commit_modifyPassword(){
	var ymm = $("#modifyPassword_ymm").val();
	var xmm = $("#modifyPassword_xmm").val();
	var qrxmm = $("#modifyPassword_qrxmm").val();
	
	if (ymm == ""){
		showDialog("原密码不能为空！");
		return false;
	}
	
	if (xmm == ""){
		showDialog("新密码不能为空！");
		return false;
	}
	
	if (qrxmm == ""){
		showDialog("确认新密码不能为空！");
		return false;
	}
	
	if (xmm != qrxmm){
		showDialog("新密码与确认密码不一致，请检查！");
		return false;
	}


	$("#modifyPassword_commit").attr("disabled",true);
	$("#modifyPassword_commit").addClass("weui-btn_disabled");

	var args = "ymm="+ymm+"&xmm="+xmm+"&qrxmm="+qrxmm;
	$.post('${ctx}/modifyPassword', args, function(data){
		if(data.error){
			showDialog(data.error);
        } else {
			showDialog(data.message);
			$("#modifyPassword_ymm").val("");
			$("#modifyPassword_xmm").val("");
			$("#modifyPassword_qrxmm").val("");
        }

		$("#modifyPassword_commit").removeAttr("disabled");
		$("#modifyPassword_commit").removeClass("weui-btn_disabled");
	});
	
}

function commit_relogin(){
	window.location.href = "${ctx}/welogin.jsp";
	//navigator.app.exitApp();
	
}

function commit_weui(){
	//window.location.href = "${ctx}/welogin.jsp";
	//navigator.app.exitApp();
	window.location.href = "http://weui.io";
}

$(document).ready(function(){
	var mHeight = $(window).height();
	var mWidth = $(window).width();
	
	var u = navigator.userAgent; 
	if((u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1) && u.indexOf('x5app') > -1){
		$("#mainframe_header").css({height:22});
		$("#mainframe_header").css({display:'block'});
		$("#mainframe_navigation").css({height:mHeight-66});
	}
	//queryBadge();
	initDialog();
});

/* function queryBadge(){
	queryWtglBadge();
	quertDclrwBadge();
}

function assignmentrwglBadge(rwglBadge){
	document.getElementById("rwglBadge").innerHTML=rwglBadge;
}

function assignmentwtglBadge(wtglBadge){
	document.getElementById("wtglBadge").innerHTML=wtglBadge;
}

function queryWtglBadge(){
	$.post('${ctx}/gzpxx/queryWtglBadge', function(data){
		//alert(JSON.stringify(data));
 		if(data.error){
 			showDialog(data.error);
 			return;
         } else {
        	var wtglBadge = data.wtbh;
        	if(wtglBadge == "0"){
        		$("#wtglBadge").hide();
        	}else{
        		$("#wtglBadge").show();
        		assignmentwtglBadge(wtglBadge);
        	}
         }
 	});
}

function quertDclrwBadge(){
	var args = "page="+1+"&rows="+1000+"&sort="+"tjsj"+"&order="+"desc"+"&zzbm="+'${loginInfo.zzbm}'+"&gzpclzt="+"0";
	$.post('${ctx}/dclrw/GzpxxQuery', args, function(data){
		//alert(JSON.stringify(data));
 		if(data.error){
 			showDialog(data.error);
 			return;
         } else {
        	var total = data.total;
        	if(total == 0){
        		$("#rwglBadge").hide();
        	}else{
        		$("#rwglBadge").show();
        		assignmentrwglBadge(total);
        	}
         }
 	});
} */

//$(document).on('deviceready', function () {
//    alert("txt:----cordova.plugins.backgroundMode.isEnabled()");
//    alert(cordova.plugins.backgroundMode);
    
//    cordova.plugins.backgroundMode.enable();
    
//    alert(cordova.plugins.backgroundMode.isEnabled());
//}, false);


</script>
</body>
</html>
