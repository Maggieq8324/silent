<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include.jsp"%>
<title>入骨相思知不知</title>
<style>
body{overflow-y:scroll;font-family:"HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, sans-serif;background:#f4f4f4;padding:0;margin:0;}
h1{font-size:31px;line-height:32px;font-weight:normal;margin-bottom:25px;}
a,a:hover{border:none;text-decoration:none;}
img,a img{border:none;}
pre{overflow-x:scroll;background:#ffffff;border:1px solid #cecece;padding:10px;}
.clear{clear:both;}
.zoomed > .container{-webkit-filter:blur(3px);filter:blur(3px);}
.container{width:505px;margin:0 auto;}
.gallery{list-style-type:none;float:left;background:#ffffff;padding:15px 20px 10px 15px;margin:0;-webkit-box-shadow:0 1px 3px rgba(0,0,0,0.25);-moz-box-shadow:0 1px 3px rgba(0,0,0,0.25);box-shadow:0 1px 3px rgba(0,0,0,0.25);-webkit-border-radius:2px;-moz-border-radius:2px;border-radius:2px;}
.gallery li{float:left;padding:0 10px 10px 0;}
.gallery li:nth-child(6n){padding-right:0;}
.gallery li a,.gallery li img{float:left;}

#zoom {
	z-index: 99990;
	position: fixed;
	top: 0;
	left: 0;
	display: none;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.8);
	filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";
	-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";
}
#zoom .content {
	z-index: 99991;
	position: absolute;
	top: 50%;
	left: 50%;
	width: 200px;
	height: 200px;
	background: #ffffff no-repeat 50% 50%;
	padding: 0;
	margin: -100px 0 0 -100px;
	box-shadow: -20px 20px 20px rgba(0, 0, 0, 0.3);
	border-radius: 4px;
}
#zoom .content.loading {
	background-image: url('${ctx}/img/qsyr/loading.gif');
}
#zoom img {
	display: block;
	max-width: none;
	background: #ececec;
	box-shadow: 0 1px 3px rgba(0,0,0,0.25);
	border-radius: 4px;
}
#zoom .close {
	z-index: 99993;
	position: absolute;
	top: 0;
	right: 0;
	width: 49px;
	height: 49px;
	cursor: pointer;
	background: transparent url('${ctx}/img/qsyr/icons/close.png') no-repeat 50% 50%;
	opacity: 1;
	filter: alpha(opacity=100);
	border-radius: 0 0 0 4px;
}
#zoom .previous,
#zoom .next {
	z-index: 99992;
	position: absolute;
	top: 50%;
	overflow: hidden;
	display: block;
	width: 49px;
	height: 49px;
	margin-top: -25px;
}
#zoom .previous {
	left: 0;
	background: url('${ctx}/img/qsyr/icons/arrows.png') no-repeat 0 0;
	border-radius: 0 4px 4px 0;
}
#zoom .next {
	right: 0;
	background: url('${ctx}/img/qsyr/icons/arrows.png') no-repeat 100% 0;
	border-radius: 4px 0 0 4px;
}
#zoom .close:hover {
	background-color: #da4f49;
}
#zoom .previous:hover,
#zoom .next:hover {
	background-color: #0088cc;
}


</style>
</head>
<body>

<div class="container">
	<ul id="gallery" class="gallery">
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0001-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0001-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0002-900x900.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0002-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0003-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0003-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0004-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0004-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0005-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0005-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0006-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0006-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0007-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0007-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0008-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0008-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0009-900x900.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0009-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0010-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0010-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0011-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0011-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0012-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0012-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0013-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0013-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0014-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0014-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0015-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0015-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0016-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0016-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0017-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0017-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0018-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0018-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0019-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0019-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0020-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0020-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0021-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0021-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0022-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0022-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0023-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0023-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0024-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0024-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0025-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0025-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0026-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0026-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0027-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0027-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0028-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0028-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0029-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0029-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0030-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0030-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0031-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0031-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0032-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0032-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0033-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0033-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0034-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0034-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0035-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0035-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0036-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0036-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0037-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0037-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0038-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0038-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0039-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0039-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0040-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0040-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0041-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0041-69x69.jpg"/></a></li>
		<li><a href="${ctx}/img/qsyr/gallery/DSC_0042-441x660.jpg"><img src="${ctx}/img/qsyr/gallery/DSC_0042-69x69.jpg"/></a></li>
	</ul>
	<div class="clear"></div>
	
</div>
<script type="text/javascript">
var html='';
$(function() {
	//loadPhotos();
});

function loadPhotos(){
	$.post('${ctx}/qsyr/QueryZhifouPhotosFor',"fjlx=1",function(data){
		if(data.error){
			$.messager.alert(data.error);
			return;
		}else{
			//console.log(JSON.stringify(data));
			$.post('${ctx}/qsyr/QueryZhifouPhotosFor',"fjlx=2",function(datas){
				if(datas.error){
					$.messager.alert(datas.error);
					return;
				}else{
					//console.log(JSON.stringify(datas));
					for(var i = 0;i <= data.length;i++){
						if(data[i]&&datas[i]){
							html +=   '<li><a href="/upload'+data[i].fjlj+'"><img src="/upload'+datas[i].fjlj+'"/></a></li>';
						}
					}
					$("#gallery").append(html);
				}
			});
		}
	});
}
</script>

<script src="${ctx}/js/jquery-1.9.1.min.js"></script>
<script src="${ctx}/js/qsyr/zoom.min.js"></script>
<div style="text-align:center;clear:both">
</div>
</body>
</html>