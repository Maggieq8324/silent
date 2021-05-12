<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="/silent" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>桌面台球</title>
<style>
* {margin:0; padding:0}
body {background:black; text-align:center}
h1 {font-size:12px; color:gray; font-weight:normal; line-height:200%}
h1 .sub {vertical-align:super; color:red; font-size:9px; }
.info {position:absolute; right:0}
#table {position:relative; width:800px; margin:20px auto 10px; height:544px; background:url(${ctx}/images/taiqiu/table.jpg) no-repeat}
.ball {position:absolute; width:30px; height:30px}
#dotWrap {position:absolute; z-index:2; left:32px; top:32px; width:736px; height:480px}
.guide {z-index:3; background-image:url(${ctx}/images/taiqiu/dashed_ball.png); _background:none; _filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true', sizingMethod='scale', src="${ctx}/images/taiqiu/dashed_ball.png"); background-repeat:no-repeat}
.target {left:500px; top:250px; background-image:url(${ctx}/images/taiqiu/yellow_ball.png); _background:none; _filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true', sizingMethod='scale', src="${ctx}/images/taiqiu/yellow_ball.png"); background-repeat:no-repeat}
.cue {background-image:url(${ctx}/images/taiqiu/white_ball.png); _background:none; _filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true', sizingMethod='scale', src="${ctx}/images/taiqiu/white_ball.png"); background-repeat:no-repeat}
.bot {position:relative; width:800px; margin:10px auto 10px; height:70px}
.ctrl {position:absolute; top:0; right:0; width:200px; height:80px; background:url(${ctx}/images/taiqiu/bg_controler.png) no-repeat}
#force {position:absolute; left:0; top:18px; width:75px; height:20px; background:url(${ctx}/images/taiqiu/force_conver.png) no-repeat}
#shootPos {position:absolute; top:0; right:14px; width:52px; height:52px}
#dot {position:absolute; top:22px; left:22px; width:8px; height:8px; background:url(${ctx}/images/taiqiu/bule_dot.png) no-repeat; font-size:1px}
#scoreBoard {position:absolute; z-index:3; top:230px; left:346px; font-size:50px; color:white; filter:alpha(opacity=0); -moz-opacity:0; opacity:0}
#tips {padding:15px 0 0 20px; text-align:left; color:red; font-size:12px}
</style>
<script type="text/javascript" src="${ctx}/js/taiqiu/zzsc.js"></script>
</head>
<body>
<div id="table">
    <div id="scoreBoard"></div>
</div>
<div class="bot">
    <!-- <div id="tips"></div> -->
    <div class = "ctrl">
        <div id="force"></div>
        <div id="shootPos"> 
            <div id="dot"></div>
        </div>
    </div>
</div>

</body>
</html>