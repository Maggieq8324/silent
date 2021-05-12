<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include.jsp"%>
<title>password</title>
</head>
<body>
<br />
<br />
<form id="modifyPasswordForm" method="post" autocomplete="off"
	style="margin: 10; text-align: center;">
	<table width="100%">
		<tr>
			<td align="center">
				原&ensp;&ensp;密&ensp;&ensp;码：
				<input id="ymm"
				name="ymm" style="width: 120px;" type="password" validType="length[1,32]" required="true"
				class="easyui-textbox" autocomplete="off" missingMessage="请输入原密码"><br><br>
				
				新&ensp;&ensp;密&ensp;&ensp;码：
				<input
				id="xmm" name="xmm" style="width: 120px;" type="password" validType="areacode" required="true"
				class="easyui-textbox" autocomplete="off" missingMessage="请输入新密码，必须包含8-20位的数字、大小写字母和特殊字符！" ><br><br>
				确认新密码：
				<input id="qrxmm"
				name="qrxmm" style="width: 120px;" type="password" validType="equalTo['#xmm']" required="true"
				class="easyui-textbox" autocomplete="off" missingMessage="请再次输入新密码">
			</td>
		<tr />
		<tr>
			<td align="center"><br> <a href="#" iconCls="icon-clear"
				onclick="javascript:document.getElementById('modifyPasswordForm').reset();return false;"
				class="easyui-linkbutton">清&ensp;&ensp;空</a>&ensp;&ensp;&ensp;&ensp;<a
				href="#" iconCls="icon-edit" onclick="modifyPassword();" class="easyui-linkbutton">修&ensp;&ensp;改</a></td>
		<tr />
	</table>
</form>
<script type="text/javascript">

$(function(){
	   $.extend($.fn.validatebox.defaults.rules, {
	       areacode : {// 验证用户名
	           validator : function(value) {
	               return /^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{8,20}$/gi.test(value);
	           },
	           message : '必须包含8-20位的数字、大小写字母和特殊字符！'
	       }
	   });
});
	
$.extend($.fn.validatebox.defaults.rules, { 
	/*必须和某个字段相等*/
	equalTo: {
	validator:function(value,param){
	return $(param[0]).val() == value;
	},
	message:'两次输入的密码不一致'
	}
});
	
function modifyPassword() {
	
	var r = $('#modifyPasswordForm').form('validate');
	if (!r) {
		return false;
	}
	var params =$('#modifyPasswordForm').serializeArray();
	$.each(params, function(i, param) {
		if(param.name == 'ymm'){param.value = encrypt(param.value);}
		if(param.name == 'xmm'){param.value = encrypt(param.value);}
		if(param.name == 'qrxmm'){param.value = encrypt(param.value);}
	});
	
	$.post("modifyPassword", params,
			function(data) {
		        if(data.message){
		        	$.messager.alert('提示', data.message, 'info');
		        	closePopWindow();
		        }
			});
}

//前端加密
   function encrypt(word){
    var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
    var srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}

//md5算法
function Md5Jm(pwd){
   return $.md5(pwd);
}


</script>
</body>
</html>
