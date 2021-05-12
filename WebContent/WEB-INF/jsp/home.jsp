<!doctype html>
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include.jsp"%>
<title>心乎爱矣</title>
<style type="text/css">
#hometab .tabs-panels .panel .panel-body {
  overflow: hidden;
}
#hometab .tabs-header .tabs-pill li.tabs-selected a.tabs-inner {
  background: #ffffff;
  color: #0E2D5F;
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,endColorstr=#ffffff,GradientType=0);
  border-color: #95B8E7;
}
#centerLayout .layout-panel-west .panel-title {
  position: absolute;
  top: 50%;
  margin-top: -8px;
  margin-left: 12px;
}
#centerLayout .layout-panel-west .panel-icon {
  margin-left: 6px;
}
#centerLayout .layout-panel-west .panel-header {
  height: 20px;
}
#centerLayout .layout-panel-west .accordion .panel-header {
  height: 16px;
}
a:link {color: #FF0000}
a:visited {color: #00FF00}
a:hover {color: #FF00FF}
a:active {color: #0000FF}
</style>
<script type="text/javascript">
var homeCheckedDatetime = new Date().getTime() - 120000;
$(document).ready(function(){
	$.post('xtgl/queryDjmkByCzy', function(data) {
		if(data.error){
			return;
		}else{
			var allGnbh = "";
			$.each(data,function(i,xtGn){
                if(xtGn.gnlxdm == '0' && xtGn.sjgnbh == '0'){
                	if(i == 0){
                		allGnbh += xtGn.gnbh;
                	} else {
                		allGnbh += ',' + xtGn.gnbh;
                	}
                }
            });
			var gnHtml = "";
    		$.each(data,function(i,xtGn){
                if(xtGn.gnlxdm == '0' && xtGn.sjgnbh == '0'){
                	var gnbh = "'"+xtGn.gnbh+"'";
                	var gnmc = "'"+xtGn.gnmc+"'";
                	var gntblmc = "'"+xtGn.gntblmc+"'";
                	var gnbhList = "'"+allGnbh+"'";
                	var init = "'0'";
                	if(i == 0){
                		gnHtml += '<a href="#" id="btn'+xtGn.gnbh+'" plain="true" style="color: rgb(211,215,226);" iconCls="'+xtGn.gntblmc+'" onmouseover="changeColorOver('+gnbh+');" onmouseout="changeColorOut('+gnbh+');" onclick="loadMkgn('+gnbh+','+gnmc+','+gntblmc+','+gnbhList+','+init+');">'+xtGn.gnmc+'</a>';
                	} else {
                		gnHtml += '&ensp;<a href="#" id="btn'+xtGn.gnbh+'" plain="true" style="color: rgb(211,215,226);" iconCls="'+xtGn.gntblmc+'" onmouseover="changeColorOver('+gnbh+');" onmouseout="changeColorOut('+gnbh+');" onclick="loadMkgn('+gnbh+','+gnmc+','+gntblmc+','+gnbhList+','+init+');">'+xtGn.gnmc+'</a>';
                	}
                }
            });
    		$('#djdhcd').html(gnHtml);
    		$.each(data,function(i,xtGn){
    			if(i == 0){
    				$('#btn'+xtGn.gnbh).linkbutton({
        				toggle : true,
        				group : 'djdhcdGroup',
        				selected : true
            		});
    				loadMkgn(xtGn.gnbh, xtGn.gnmc, xtGn.gntblmc, allGnbh, '1');
    			} else {
    				$('#btn'+xtGn.gnbh).linkbutton({
        				toggle : true,
        				group : 'djdhcdGroup'
            		});
    			}
    		});
		}
	});
	$(".tabs-header").bind('contextmenu',function(e){
        e.preventDefault();
        $('#rightMenu').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
    });
    $("#closeCur").bind("click",function(){
        var tab = $('#hometab').tabs('getSelected');
        var index = $('#hometab').tabs('getTabIndex',tab);
        if(index != 0){
            $('#hometab').tabs('close',index);
        }
        var tablist = $('#hometab').tabs('tabs');
        if(index == tablist.length){
        	$('#hometab').tabs('select',index-1);
        }else{
        	$('#hometab').tabs('select',index);
        }
    });
    $("#closeOther").bind("click",function(){
        var tablist = $('#hometab').tabs('tabs');
        var tab = $('#hometab').tabs('getSelected');
        var index = $('#hometab').tabs('getTabIndex',tab);
        for(var i=tablist.length-1; i>index; i--){
            $('#hometab').tabs('close',i);
        }
        var num = index - 1;
        for(var i=num; i>0; i--){
            $('#hometab').tabs('close',1);
        }
        if(index == 0){
        	$('#hometab').tabs('select',0);
        }else{
        	$('#hometab').tabs('select',1);
        }
    });
    $("#closeAll").bind("click",function(){
        var tablist = $('#hometab').tabs('tabs');
        for(var i=tablist.length-1; i>0; i--){
            $('#hometab').tabs('close',i);
        }
    });
    $.post('xtgl/queryAllDm', function(data) {
		if(data.error){
			return;
        }else{
        	var result = {};
        	var subResult = {};
        	var prevDmflbm = "";
        	$.each(data,function(i,xtDm){
        		if(prevDmflbm && prevDmflbm != xtDm.dmflbm){
        			result[prevDmflbm] = subResult;
        			subResult = {};
        		}
        		subResult[xtDm.dmbm] = xtDm.dmmc;
        		prevDmflbm = xtDm.dmflbm;
        	});
        	if(subResult && prevDmflbm){
        		result[prevDmflbm] = subResult;
    			subResult = {};
    			prevDmflbm = "";
        	}
        	globalXtDm = result;
        }
	});
});
function changeColorOver(djmkgnbh) {
	if(!$('#btn'+djmkgnbh).linkbutton("options").selected){
		$('#btn'+djmkgnbh).css('color','rgb(44,44,44)');
	}
}
function changeColorOut(djmkgnbh) {
	if(!$('#btn'+djmkgnbh).linkbutton("options").selected){
		$('#btn'+djmkgnbh).css('color','rgb(211,215,226)');
	}
}
function loadMkgn(djmkgnbh, djmkgnmc, djmkgntblmc, djmkGnbhList, init) {
	if(init == '1'){
		$('#btn'+djmkgnbh).css('color','rgb(255 255 240)');
	} else {
		var djmkGnbhArray = djmkGnbhList.split(',');
    	$.each(djmkGnbhArray,function(i,gnbh){
    		if(gnbh == djmkgnbh){
    			$('#btn'+gnbh).css('color','rgb(255 255 240)');
    		} else {
    			$('#btn'+gnbh).css('color','rgb(211,215,226)');
    		}
    	});
	}
	if((new Date().getTime() - homeCheckedDatetime) < 1500){
		return;
	}
	homeCheckedDatetime = new Date().getTime();
	$('#centerLayout').layout('panel','west').panel({
		title : djmkgnmc,
		iconCls : djmkgntblmc
	});
	var menuAccordionHtml = '<div id="menuAccordion" fit="true" border="false"></div>';
	$('#dhcd').html(menuAccordionHtml);
	$('#menuAccordion').accordion({
	});
	$.post('xtgl/queryGnByCzy', 'djmkgnbh=' + djmkgnbh, function(data) {
		if(data.error){
			return;
		}else{
			var gnHtml = "";
    		var scXtGn = null;
    		$.each(data,function(i,xtGn){
                if(xtGn.gnlxdm == '0' && xtGn.sjgnbh != '0'){
                	if(gnHtml != ""){
                		$('#'+'menuAccordion'+scXtGn.gnbh).html(gnHtml);
                		gnHtml = "";
                	}
                    $('#menuAccordion').accordion('add',{
                    	id: 'menuAccordion'+xtGn.gnbh,
                        title: xtGn.gnmc,
                        iconCls: xtGn.gntblmc,
                        selected: false
                    });
                    scXtGn = xtGn;
                }
                if(xtGn.gnlxdm=='1' && scXtGn!=null){
            		var gnmcArgs = "'"+xtGn.gnmc+"'";
            		var gnljdzArgs = "'"+xtGn.gnljdz+"'";
            		var gnljfsArgs = "'"+xtGn.gnljfsdm+"'";
            		var gntblmcArgs = "'"+xtGn.gntblmc+"'";
            		var gntbljdzArgs = "${ctx}/"+xtGn.gntbljdz;
            		gnHtml += '<ul><li style="cursor: pointer; list-style-type: none; list-style-image: url('
            				+gntbljdzArgs+');" onclick="openGn('
            				+gnmcArgs+','+gnljdzArgs+','+gnljfsArgs+','+gntblmcArgs+');">'
            				+xtGn.gnmc+'</li></ul>';
            	}
            });
    		if(gnHtml != ""){
        		$('#'+'menuAccordion'+scXtGn.gnbh).html(gnHtml);
        		gnHtml = "";
        	}
    		if(scXtGn!=null){
    			$('#menuAccordion').accordion('select',0);
    			scXtGn = null;
    		}
		}
	});
}
function openGn(title, href, ljfs, gntblmc) {
	if ($('#hometab').tabs('exists', title)) {
		$('#hometab').tabs('select', title);
	} else {
		if(ljfs == 1 || ljfs == 0){
			$('#hometab').tabs('add', {
				title : title,
				iconCls : gntblmc,
				content : '<iframe scrolling="auto" frameborder="0" src="navigation?link=' + href + '" style="width:100%;height:100%;"></iframe>',
				closable : true,
				border : false
			});
		}else if (ljfs == 3){
			$('#hometab').tabs('add', {
				title : title,
				iconCls : gntblmc,
				content : '<iframe scrolling="auto" frameborder="1" src="navigation?link=' + href + '" style="width:370px;height:100%;"></iframe>',
				closable : true,
				border : false
			});
		}else{
			$('#hometab').tabs('add', {
				title : title,
				iconCls : gntblmc,
				href : 'navigation?link=' + href,
				closable : true,
				border : false
			});
		}
	}
}
function showPopWindow(options) {
	$('#popWindow').window(options);
	$('#popWindow').window('center');
}
function closePopWindow() {
	$('#popWindow').window('close');
}
function showInlineWindow(windowid, options) {
	$('#'+windowid).window({
		closed : true,
		modal : false,
		draggable : false,
		fit : true,
		inline : true,
		shadow : false,
		minimizable : false,
		maximizable : false,
		collapsible : false,
		resizable : false,
		cache : false,
		style : 'margin: 0px; padding: 0px; overflow: auto;'
	});
	$('#'+windowid).window(options);
	$('#'+windowid).window('open');
}
function closeInlineWindow(windowid) {
	$('#'+windowid).window('close');
}
</script>
</head>
<body>
<div class="easyui-panel" fit="true" border="false" >
	<div class="easyui-layout" fit="true" border="false">
		<div data-options="region:'north'" border="false" style="height: 90px; overflow: hidden;">
			<table border="0" width="100%" height="100%" style="overflow: hidden; background-image: url(${ctx}/img/silent_1.jpg); background-repeat: no-repeat center center fixed; background-size: cover;">
				<tr>
        			<td width="250px" rowspan="2">
        				<div style="margin: 6px 0px 0px 6px;">
        					<font style="color: rgb(211,215,226); font-weight: bold; font-size: 12px;">欢迎您：${loginInfo.czymc}</font>
        				</div>
        				<div style="margin: 6px 0px 0px 6px;">
        					<font style="color: rgb(211,215,226); font-weight: bold; font-size: 12px;">账&ensp;&ensp;号：${loginInfo.dlzh}</font>
        				</div>
        			</td>
            		<td style="text-align: center;">
						<font style="color: #FFFFFF; text-align: center; font-weight: bold; font-size: 32px;">心乎爱矣&ensp;遐不谓矣</font>
            		</td>
            		<td width="250px">
        			</td>
        		</tr>
        		<tr height="30px">
            		<td>
            			<div id="djdhcd">
							&ensp;&ensp;
						</div>
            		</td>
            		<td width="250px">
            			&ensp;&ensp;<a href="#" id="modifyPasswordLinkbutton" class="easyui-linkbutton" plain="true" style="color: rgb(211,215,226);" iconCls="icon-edit" onmouseover="$('#modifyPasswordLinkbutton').css('color','rgb(44,44,44)');" onmouseout="$('#modifyPasswordLinkbutton').css('color','rgb(211,215,226)');" onclick="showPasswordWindow()">修改密码</a>
						&ensp;&ensp;<a href="#" id="reloginLinkbutton" class="easyui-linkbutton" plain="true" style="color: rgb(211,215,226);" iconCls="icon-redo" onmouseover="$('#reloginLinkbutton').css('color','rgb(44,44,44)');" onmouseout="$('#reloginLinkbutton').css('color','rgb(211,215,226)');" onclick="relogin()">重新登录</a>
        			</td>
        		</tr>
			</table>
		</div>
		<div data-options="region:'west'" border="false" style="width: 10px; background-image: url(${ctx}/img/silent_2.jpg); background-repeat: no-repeat center center fixed; background-size: cover;">
		</div>
		<div data-options="region:'center'" border="false" style="padding: 0px;">
			<div id="centerLayout" class="easyui-layout" fit="true" border="false">
				<div data-options="region:'west', split:true" border="false" collapsible="true"
					title="导航菜单" iconCls="icon-system" style="width: 240px;">
					<div class="easyui-panel" fit="true" style="overflow: auto;" border="false">
	    				<div id="dhcd" class="easyui-panel" fit="true" style="overflow: auto;" border="false">
							&ensp;&ensp;
						</div>
					</div>
				</div>
				<div data-options="region:'center'" style="padding: 0px;" border="false">
					<div id="hometab" class="easyui-tabs" fit="true" border="false" pill="true">
	    				<div title="系统首页" iconCls="icon-function" border="false">
	    					<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
	    						<tr>
	    							<td align="center" valign="center">
				    					<table width="80%" height="80%" border="0" cellspacing="0" cellpadding="0">
				    						<tr>
				    							<td align="center" valign="center"  width="100%" height="100%"  style="background-image:url(${ctx}/img/sil_2.jpg);background-repeat: no-repeat center center fixed; background-size: cover;">
				    							</td>
				    						</tr>
				    					</table>
	    							</td>
	    						</tr>
	    					</table>
	    				</div>
					</div>
				</div>
			</div>
		</div>
		<div data-options="region:'east'" border="false" style="width: 10px; background-image: url(${ctx}/img/silent_2.jpg); background-repeat: no-repeat center center fixed; background-size: cover;">
		</div>
		<div data-options="region:'south'" border="false" style="height: 20px; text-align: center; vertical-align: middle; background-image: url(${ctx}/img/silent_1.jpg); background-repeat: no-repeat center center fixed; background-size: cover;">
			<span style="text-align: center; vertical-align: middle; font-size: 14px; font-family: Arial; color: rgb(211,215,26);"><a href="http://www.miitbeian.gov.cn/">滇ICP备19004036号</a></span>
		</div>
	</div>
	<div id="rightMenu" class="easyui-menu" style="width: 120px;">
       	<div id="closeCur" data-options="iconCls:'icon-cancel'">关闭</div>
       	<div id="closeOther">关闭其他</div>
       	<div id="closeAll">关闭全部</div>
   	</div>
	<div id="popWindow" modal="true" shadow="false" minimizable="false"
		cache="false" maximizable="false" collapsible="false"
		resizable="false" style="margin: 0px; padding: 0px; overflow: auto;">
	</div>
	<div id="popDialog" modal="true" shadow="false" minimizable="false"
		cache="false" maximizable="false" collapsible="false"
		resizable="false" style="margin: 0px; padding: 0px; overflow: auto;">
	</div>
</div>

<script type="text/javascript">
function showPasswordWindow() {
	showPopWindow({
		title : '修改密码',
		href : 'navigation?link=password',
		width : 400,
		height : 275,
		onLoad : function() {
			$('#modifyPasswordForm').form('clear');
		}
	});
}
function relogin() {
	$('#reloginLinkbutton').attr("href",'relogin');
}

</script>
</body>
</html>
