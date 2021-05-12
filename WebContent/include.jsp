<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="/silent" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/jquery-easyui-1.4.4/themes/black/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/jquery-easyui-1.4.4/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/customicon.css" />
<script type="text/javascript"
	src="${ctx}/js/jquery-easyui-1.4.4/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/md5.js"></script>
<script type="text/javascript"
	src="${ctx}/js/aes.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-easyui-1.4.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/jquery-easyui-1.4.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/datagrid-filter.js"></script>  
<script type="text/javascript">
	if ($.fn.datebox) {
		$.fn.datebox.defaults.formatter = function(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
					+ (d < 10 ? ('0' + d) : d);
		};
		$.fn.datebox.defaults.parser = function(val) {
			if (!val)
				return new Date();
			val = '' + val;
			var y = val.substring(0, 4);
			var m = val.substring(5, 7);
			var d = val.substring(8, 10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
				return new Date(y, m - 1, d);
			} else {
				return new Date();
			}
		};
	}
	if ($.fn.datetimebox) {
		$.fn.datetimebox.defaults.formatter = function(date) {
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			var d = date.getDate();
			var h = date.getHours();
			var M = date.getMinutes();
			var s = date.getSeconds();
			return y + '-' + (m < 10 ? ('0' + m) : m) + '-'
					+ (d < 10 ? ('0' + d) : d) + ' ' + (h < 10 ? ('0' + h) : h)
					+ ':' + (M < 10 ? ('0' + M) : M) + ':'
					+ (s < 10 ? ('0' + s) : s);
		};
		//2017-06-01 11:41:15
		$.fn.datetimebox.defaults.parser = function(val) {
			if (!val)
				return new Date();
			val = '' + val;
			var y = val.substring(0, 4);
			var m = val.substring(5, 7);
			var d = val.substring(8, 10);
			var h = val.substring(11, 13);
			var M = val.substring(14, 16);
			var s = val.substring(17, 19);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(M)
					&& !isNaN(s)) {
				return new Date(y, m - 1, d, h, M, s);
			} else {
				return new Date();
			}
		};
	}
</script>
<script type="text/javascript" src="${ctx}/js/jquery.form.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript"
	src="${ctx}/js/plupload-2.1.2/i18n/zh_CN.js"></script>
<%-- <script type="text/javascript" src="${ctx}/js/highcharts.js"></script> --%>
<script type="text/javascript">
	(function($) {
		var _ajax = $.ajax;
		$.ajax = function(options) {
			var fn = {
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				},
				success : function(data, textStatus) {
				},
				complete : function(XMLHttpRequest, textStatus) {
				}
			}
			if (options.error) {
				fn.error = options.error;
			}
			if (options.success) {
				fn.success = options.success;
			}
			if (options.complete) {
				fn.complete = options.complete;
			}
			var _options = $.extend(options, {
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					//错误回调函数增强处理
					$.messager.alert('错误', 'ajax请求异常', 'error');
					fn.error(XMLHttpRequest, textStatus, errorThrown);
				},
				success : function(data, textStatus) {
					//成功回调函数增强处理
					if (data.error) {
						$.messager.alert('错误', data.error, 'error');
					}
					fn.success(data, textStatus);
				},
				complete : function(XMLHttpRequest, textStatus) {
					//完成回调函数增强处理
					fn.complete(XMLHttpRequest, textStatus);
				}
			});
			_ajax(_options);
		};
	})(jQuery);
	$.ajaxSetup({
		cache : false
	});
	if (!globalXtDm) {
		if (!parent.globalXtDm) {
			var globalXtDm = {};
		} else {
			var globalXtDm = parent.globalXtDm;
		}
	}
	function initPermission() {
		$(document).ready(function() {
			if ('${hiddenYsids}') {
				$.each('${hiddenYsids}'.split(";;"), function(i, hiddenYsid) {
					var ysidArray = hiddenYsid.split("::");
					if (ysidArray.length != 2) {
						$.messager.alert('提示', '界面元素权限配置错误', 'info');
						return;
					}
					$("." + ysidArray[0]).each(function() {
						if (ysidArray[1] == $(this).attr('id')) {
							$(this).hide();
						}
					});
				});
			}
		});
	}
	function createExportIframe(options) {
		var config = $.extend(true, {
			method : 'post'
		}, options);
		var $iframe = $('<iframe id="exportIframe" />');
		var $form = $('<form target="exportIframe" method="' + config.method + '" />');
		$form.attr('action', config.url);
		for ( var key in config.data) {
			$form
					.append("<input type='hidden' name='" + key + "' value='" + config.data[key] + "' />");
		}
		$iframe.append($form);
		$(document.body).append($iframe);
		$form[0].submit();
		$iframe.remove();
	}
	function getDatagridJson(datagridId) {
		var result = {};
		var titleResult = {};
		var rows = $('#' + datagridId).datagrid('getRows');
		var columnFields = $('#' + datagridId).datagrid('getColumnFields');
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var subResult = {};
			var k = 0;
			for (var j = 0; j < columnFields.length; j++) {
				var columnField = columnFields[j];
				var columnOption = $('#' + datagridId).datagrid(
						'getColumnOption', columnField);
				if (columnOption.checkbox) {
					continue;
				}
				if (columnOption.hidden) {
					continue;
				}
				titleResult[k] = columnOption.title;
				//subResult[k] = row[columnOption.field];
				
				if (columnOption.formatter){
					subResult[k] = columnOption.formatter(row[columnOption.field], i);
				} else {
					subResult[k] = row[columnOption.field];
				}
				
				k++;
			}
			if (i == 0) {
				result[i] = titleResult;
			}
			result[i + 1] = subResult;
		}
		return JSON.stringify(result);
	}
	function exportExcel(datagridId, fileName) {
		if (window.ActiveXObject || "ActiveXObject" in window) {
			createExportIframe({
				url : 'file/datagridExportExcel', //请求的url
				data : {
					msie : 'true',
					fileName : fileName,
					data : getDatagridJson(datagridId)
				}
			//要发送的数据
			});
		} else {
			createExportIframe({
				url : 'file/datagridExportExcel', //请求的url
				data : {
					msie : 'false',
					fileName : fileName,
					data : getDatagridJson(datagridId)
				}
			//要发送的数据
			});
		}
	}
</script>
<style type="text/css">
body {
	font-size: 14px;
	font-family: "楷体";
}

img {
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	display: inline;
}

td, p, div, a, font {
	font-size: 14px;
	font-family: "楷体";
}

input, select, textarea {
	font-size: 14px;
	font-family: "楷体";
}

button {
	font-family: "楷体";
}
</style>
