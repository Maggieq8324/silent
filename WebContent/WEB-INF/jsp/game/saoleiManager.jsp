<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/include.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>扫雷</title>
</head>
<body>
<div class="easyui-panel" fit="true" border="false">
	<div class="easyui-layout" fit="true" border="false">
		<div data-options="region:'center'" style="width: 100%; overflow: auto; border:2px solid #8EE5EE;">
			<iframe src="${ctx}/html/saolei/index.html" width="100%" height="100%" style="overflow: hidden; border:none;"></iframe>
		</div>
	</div>
</div>
<div class="main-content">
    
</div>
</body>
</html>