<!DOCTYPE html>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/weinclude.jsp"%>
<title>营销现场作业安全管理</title>
</head>
<body>
<!-- #008AD5 -->
<div class="page">
    <div id="mainframe_header"  class="page" style="width:100%;height:22px;display: none;"  style="background-color: #E8F7FF;">
    </div>
    <div class="page__hd" style="margin: 0px;" id="gzpqr_header">
    	<h1 class="page__title" style="height:38px;background-color:#008AD5;">
    		<table style="width:100%;height:100%;">
	        	<tr>
	        		<td valign="middle" style="font-size: 18px;color: #ffffff; width:100%; height:100%;">
    					<img style="vertical-align: middle;" width="30px" src="${ctx}/img/yxaq.png" />营销现场作业安全管理系统
    				</td>
    			</tr>
    		</table>
    	</h1>
    </div>
    <div class="page__bd" style="margin: 10px;" id="gzpqr_page">
    	<p class="page__desc"  align="center">
    		<table style="width:100%;height:100%;">
	        	<tr>
	        		<td width="100px" valign="middle" align="center" style="font-size: 22px;color: #888888;">
    					登录
    				</td>
    			</tr>
    		</table>
    	</p>
    	<form id="reloginForm" action="${ctx}/weHome" method="post">
    	<div class="weui-cells">
    	
            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__bd">
                    <select class="weui-select"  id="gzpqr_selectgdsxm_zzbm" name="zzbm" placeholder="请选择所属机构">
                    	
                    </select>
                </div>
            </div>
            <div class="weui-cell weui-cell_select weui-cell_select-after">
                <div class="weui-cell__bd">
                    <select class="weui-select"  id="gzpqr_selectgdsxm_subzzbm" name="subzzbm" placeholder="请选择所属子机构">
                    
                    </select>
                </div>
            </div>
			<div class="weui-cell weui-cell_vcode">
                <div class="weui-cell__bd">
                    <input class="weui-input" type="text" id="input_dlzh" name="dlzh" placeholder="请输入或选择用户名"/>
                </div>
                <div class="weui-cell__ft">
                    <button type="button" onclick="javascript:gzpqr_showSelectjlfzrmc();" class="weui-vcode-btn" >选择</button>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <input class="weui-input" autocomplete="off"  name="dlmm" id="input_dlmm" type="password" placeholder="请输入密码" value="" validType="length[1,16]"/>
                </div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <input class="weui-input" name="captcha"  id="input_captcha" type="url" placeholder="请输入校验码"/>
                </div>
               &ensp;
               <img id="reCaptchaImg" style="vertical-align: middle;" src="captcha" />
               &ensp;
			   <a href="#" onclick="reChangeCaptchaImg()"><img style="vertical-align: middle;" src="${ctx}/img/refresh.png" /></a>
            </div>
        </div> 
        <div class="button-sp-area" align="center">
	        <table style="width:100%; height:100px;">
	        	<tr>
	        		<td colspan="2" style="text-align: center; font-size: 12px;"><font color="#FF0000">&ensp;${error}&ensp;</font></td>
	        	</tr>
	        	<tr>
	        		<td width="40%">
	        			<a class="weui-btn weui-btn_default" href="javascript:reloginFormClear();">重置</a>
	        		</td>
	        		<td>
	        			<a class="weui-btn weui-btn_primary" href="javascript:relogin();">登录</a>
	        		</td>
	        	</tr>
	        </table>
        </div>
        </form>
    </div>
 	<div class="page__bd" id="gzpqr_selectjlfzrmc" style="display:none; overflow:hidden;">
        <div class="weui-panel weui-panel_access">
            <div class="weui-cells__title">
				<a href="javascript:gzpqr_hideSelectjlfzrmc();" class="item"><img style="vertical-align: middle;text-align:left;margin:5px;" width="30px" src="${ctx}/we/img/ht.png" /></a>选择登录名
			</div>
            <div class="weui-cells weui-cells_form">
                <div class="weui-cell">
                    <div class="weui-cell__hd"><label for="" class="weui-label">登录名</label></div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" id="gzpqr_selectjlfzrmc_czymc" name="jlfzrmc_czymc" type="text" placeholder="请输入关键字"/>
                    </div>
                </div>
                <div class="weui-btn-area">
                    <a class="weui-btn weui-btn_primary" href="javascript:gzpqr_selectjlfzrmc_query();" id="gzpqr_selectjlfzrmc_showTooltips">查询</a>
                </div>
			 </div>
			<div class="weui-cells weui-cells_radio" id="gzpqr_selectjlfzrmc_checkbox" style="position:relative; width:100%; overflow:auto;">
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
<script type="text/javascript">
function showDialog(message){
	$('#iosDialog_content').text(message);
	$('#iosDialog').fadeIn(200);
}
function initDialog(){
    $('#iosDialog').on('click', '.weui-dialog__btn', function(){
        $(this).parents('.js_dialog').fadeOut(200);
    });
}

function gzpqr_showSelectjlfzrmc(){
	$("#gzpqr_page").hide();
	//$("#gzpqr_header").hide();
	$('#gzpqr_selectjlfzrmc').fadeIn(200);
	
	gzpqr_selectjlfzrmc_query();
}

function gzpqr_hideSelectjlfzrmc(){
	$("#gzpqr_page").fadeIn(200);
	//$("#gzpqr_header").fadeIn(200);
	$("#gzpqr_selectjlfzrmc").hide();
}

function fkyj_commit_upload(){
	var fbmc = $("#fkyj_commit_fbmc").val();
	var fbnr = $("#fkyj_commit_fbnr").val();
	
	if (fbmc == ""){
		showDialog("意见标题不能为空，请检查！");
		return;
	}
	
	if (fbnr == ""){
		showDialog("意见内容不能为空，请检查！");
		return;
	}
	
	$('#fkyj_commit_uploadyywj').submit();
}


function gzpqr_selectjlfzrmc_query(){
	//var subselectedoption = $("#gzpqr_selectgdsxm_subzzbm").find("option").not(function(){ return !this.selected });
	var _zzbm = $("#gzpqr_selectgdsxm_subzzbm").val();
	
	
	if (!_zzbm || _zzbm == "") {
		//var selectedoption = $("#gzpqr_selectgdsxm_zzbm").find("option").not(function(){ return !this.selected });
		_zzbm = $("#gzpqr_selectgdsxm_zzbm").val();
	}
	
	
	var zzbm = _zzbm;

	var czymc = $("#gzpqr_selectjlfzrmc_czymc").val();
	
	var args = "czymc="+czymc+"&zzbm="+zzbm;
		
	$.post('${ctx}/xtgl/queryZzbmCzy', encodeURI(args), function(data){
		if(data.error){
			showDialog(data.error);
			return;
        } else {
			var html="";
			$("#gzpqr_selectjlfzrmc_checkbox").empty();
        	$.each(data,function(j,tq){
        		
        		html += '			    <label class="weui-cell weui-check__label" for="jlfzrmc'+j+'">';
        		html += '			        <div class="weui-cell__bd">';
        		html += '			            <p>'+tq.dlzh+'('+tq.czymc+')</p>';
        		html += '			        </div>';
        		html += '			        <div class="weui-cell__ft">';
        		html += '			            <input type="radio" class="weui-check" name="gzpqr_radio_jsbh" id="jlfzrmc'+j+'" onclick="gzpqr_jlfzrmc_radio_click(\'jlfzrmc'+j+'\')" value="'+tq.czybh+'" data-dlzh="'+tq.dlzh+'"/>';
        		html += '			            <span class="weui-icon-checked"></span>';
        		html += '			        </div>';
        		html += '			    </label>';
        	});
			$("#gzpqr_selectjlfzrmc_checkbox").append(html);
        }
	});
}

function gzpqr_jlfzrmc_radio_click(id){
	var czybh = $("#"+id).val();
	var dlzh = $("#"+id).data("dlzh");

	$("#input_dlzh").val(dlzh);
	
	gzpqr_hideSelectjlfzrmc();
}

function iniData(){
	var u = navigator.userAgent; 
	if((u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1) && u.indexOf('x5app') > -1){
		$("#mainframe_header").css({height:22});
		$("#mainframe_header").css({display:'block'});
	}
	
	initDialog();	
	
	var message = "${message}";
	
	if (message != ""){
		showDialog(message);
	}
	
    var args = "zzbm=0502";
    $.post('${ctx}/xtgl/queryXjZzbm', args, function(data){
        if(data.error){
            showDialog(data.error);
            return;
        } else {
            $.each(data,function(j,zzDm){
                //subdata[zzDm.zzbm]=zzDm.zzmc;
                //<option value="1">中国</option>
                if (zzDm.zzbm == '${loginInfo.zzbm}') {
                    $("#gzpqr_selectgdsxm_zzbm").append('<option value="'+zzDm.zzbm+'" selected="selected">'+zzDm.zzmc+'</option>');
                    //alert($("#gzpqr_selectgdsxm_zzbm").html());
                } else {
                    $("#gzpqr_selectgdsxm_zzbm").append('<option value="'+zzDm.zzbm+'">'+zzDm.zzmc+'</option>');
                }
            });
        }
    });
    
    $("#gzpqr_selectgdsxm_zzbm").on('change', function(){
                
        var selectedoption = $("#gzpqr_selectgdsxm_zzbm").find("option").not(function(){ return !this.selected });
        var zzbm = selectedoption.val();
        
        if (zzbm.length == 6) {
            var args = "zzbm=" + zzbm;
            $.post('${ctx}/xtgl/queryXjZzbm', args, function(data){
                if(data.error){
                    showDialog(data.error);
                    return;
                } else {
                    $("#gzpqr_selectgdsxm_subzzbm").empty();
                    $("#gzpqr_selectgdsxm_subzzbm").append('<option value="" selected="selected">选择供电所</option>');
                    $.each(data,function(j,zzDm){
                        if (zzDm.zzbm != zzbm) {
                            $("#gzpqr_selectgdsxm_subzzbm").append('<option value="'+zzDm.zzbm+'">'+zzDm.zzmc+'</option>');
                        }
                    });
                }
            });
        } else {
            $("#gzpqr_selectgdsxm_subzzbm").empty();
        }
        //
    });
}



function relogin() {
	//var result = $('#reloginForm').form('validate');
	//if (!result) {
	//	return false;
	//}
	/* var dlmm = $('#input_dlmm').val();
	$('#input_dlmm').val(encrypt(Md5Jm("copyright." + dlmm + "pms@2016"))); */
	$('#reloginForm').submit();
}


function reloginFormClear() {
	//$('#reloginForm').form('clear');
	$("#input_dlzh").val("");
	$("#input_dlmm").val("");
}

//md5算法
function Md5Jm(pwd){
	return $.md5(pwd);
}

//前端加密
function encrypt(word){
    var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}

iniData();
</script>
</body>
</html>
