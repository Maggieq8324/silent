<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include.jsp"%>
<title>玲珑骰子安红豆</title>
<style>
body,div,ul,li,img{margin: 0;padding: 0;}

ul,li{list-style: none;}

#left{float:left;position: relative;margin: left;width: 100%;height: 100%;border: 0px solid red;}

#banner{position: relative;width: 100%;height: 100%;border: 0px solid green;overflow: hidden;}

.imgList{position: relative;width: 100%;height: 100%;z-index: 10;overflow: hidden;}

.imgList li{float: left;display: inline;}

#prev,#next{position: absolute;top: 240px;z-index: 20;cursor: pointer;opacity: 0.2;filter:alpha(opacity=20);}
#prev{left: 10px;}
#next{right: 10px;}

#prev:hover,#next:hover{opacity: 0.6;filter:alpha(opacity=60);}

#bg{position: absolute;bottom: 0;width: 100%;height: 40px;z-index: 20;background: black;opacity: 0.4;filter: alpha(opacity=40);}

.infoList{position: absolute;left: 10px;bottom: 10px;z-index: 30;}

.infoList li{display: none;}

.infoList .infoOn{display: inline;color: white;}

.indexList{position: absolute;right: 10px;bottom: 10px;background: b;z-index: 30;}

.indexList li{display: inline;cursor: pointer;background: gray;margin-left: 5px;padding: 2px 4px;}

.a{display: inline;cursor: pointer;background: gray;margin-left: 5px;padding: 2px 4px;}

.indexList .indexOn{background: wheat;font-weight: bold;}

p{font-size:16px;}
</style>
</head>
<body>
<div class="easyui-panel" fit="true" border="false">
	<div class="easyui-layout" fit="true" border="false">
		<div data-options="region:'west'" style="width: 30%; overflow: auto; border:2px solid #8EE5EE;">
			<div id="left">
				<div id="banner">
					<!-- 轮播图片 -->
					<ul class="imgList">
						<li><img id="viewImg" src="" width="100%" height="100%" alt="puss image1"/></li>
					</ul>
					<!-- 左右箭头的图片 -->
					<img src="img/leftjt.gif" width="25px" height="60px" id="prev" />
					<img src="img/rightjt.gif" width="25px" height="60px" id="next" />
					<div id="bg"></div>
					<!-- 轮播文字 -->
					<ul class="infoList">
						<li id="wzsm" class="infoOn"></li>
					</ul>
					<!-- 轮播角标 -->
					<ul id="indexList" class="indexList">
						<!-- <li class="indexOn">1</li> -->
					</ul>
				</div>
			</div>
		</div>
		<div data-options="region:'center'" style="overflow: hidden;" border="false">
			<div class="easyui-layout" fit="true" border="false">
				<div data-options="region:'center'" border="false" style="overflow: hidden; height:32%; border:2px solid #8EE5EE;border-bottom:1px;border-left:1.5px;">
					<form id="QsyrFjxxAddForm"  style="margin: 10px; text-align: center; " action="qsyr/upload" method="post">
						<input type="hidden" name="fjbh" value="0"/>
						<input type="hidden" name="fjbz" id="fjbz" value="0"/>	        
						<table width="100%" style="border-collapse:separate; border-spacing:0px 10px;">
						    <tr>
				                <td align="left" colspan="2"><b>照片上传：</b></td>
			                </tr>
							<tr>
								<td align="right">附件：</td>
								<td align="left">
									<input class="easyui-filebox" name="file" id="QsyrFjxxAddForm_file" style="width: 300px;" validType="length[0,512]" required="true" buttonText="选择文件"/>
								</td>
								<td align="right">附件名：</td>
								<td align="left">
									<input class="easyui-textbox" name="fjmc" id="QsyrFjxxAddForm_fjmc" style="width: 300px;" validType="length[0,512]"  required="true"/>
								</td>
							</tr>
							<tr >
								<td align="right">照片留言：</td>
								<td colspan="3">
								    <input class="easyui-textbox" id="zpsm" name="wzsm" data-options ="multiline:true" value ="" prompt="写点什么吧..." style="width:730px;height:38px;">
								</td>
							</tr>
							<tr>
						        <td colspan="4" align="center">
			    					<a href="#" class="easyui-linkbutton"
									iconCls="icon-clear" onclick="clearQsyrFjxxAddForm();">清空</a>
									&ensp;&ensp;
			    					<a href="#"  id ="QsyrFjxxAddForm_addQsyrFjxxLinkbutton" class="easyui-linkbutton"
									iconCls="icon-search" onclick="uploadFjxxAddForm();">上传</a>
								</td>
							</tr>
						</table>
					</form>	
				</div>
				<div data-options="region:'south'"  border="false" style="overflow: hidden; height:68%; border:2px solid #8EE5EE;border-bottom:1px;border-left:1.5px;padding:20px;">
					<div style="text-align: center;">
						<font style="color: #FFFFFF; margin:0 auto; font-weight: bold; font-size: 32px;">想和你们谈谈</font>
					</div>
					<p>&ensp;&ensp;&ensp;&ensp;这一年，我二十岁，自以为知道无数的道理，可依旧过不好这一生。人世间的事情莫过于此，用一个瞬间来喜欢一样东西，
					然后用多年的时间来拷问自己为什么会喜欢这样东西。</p>
					<p>&ensp;&ensp;&ensp;&ensp;有时候觉得懂的越多就越像是这个世界的孤儿，走的越远才明白世界本身就是一个孤儿院。每一年开春时站在火车站
					亦或机场，看人潮涌动，不明白的是为什么会有那么多人选择无论任何目的的出门漂泊，直到从熟悉的南方飘到遥远的北方后才明白，一切的漂泊都只是为了不再漂泊。</p>
					<p>&ensp;&ensp;&ensp;&ensp;妈妈曾说过，不用对生命中的每个过客负责，也不用对每个路人说教。有时候想证明给无数人看，到后来才恍然，
					只要得到一个人的明白就足够了。一直希望自己能够笑的洒洒脱脱，活的没心没肺，可能说得出来的苦都不算苦。一路走来，看街道上的落英缤纷，看潮起潮落，
					二十岁的年纪没想过要看破红尘，但别以为我一直在笑都不会痛了。</p>
					<p>&ensp;&ensp;&ensp;&ensp;失望是种很抽象的东西，没有虚妄的希望，自然就没有虚妄过后的失望，更没有所谓的绝望，倘若能避开猛烈的狂喜，自然也不会有
					悲痛的来袭。人生如棋各种痴，自然是喜欢什么就做什么好了，不回头的走下去，人生这道题早晚会有答案的!!!</p>
					<p style="text-align:right;">2015年夏<br />Coisini&ensp;</p>
				</div>
			</div>
		</div>
	</div>	
</div>	
<script type="text/javascript">
var fjxx_map;
var html = '';
var home_pagetotal,total,datarows;
var home_page=1,home_rows=5,home_sort="xssxh",home_order="asc";
$(function() {
	loadPage(home_page);
});

function loadPage(_page){
	home_page = _page;
	var args = "page="+home_page+"&rows="+home_rows+"&sort="+home_sort+"&order="+home_order;
	$.post('${ctx}/qsyr/QueryQsyrPhotos',args,function(data){
		if(data.error){
			$.messager.alert(data.error);
			return;
		}else{
			//console.log(JSON.stringify(data));
			fjxx_map = data.rows;
			total = data.total;
        	datarows = data.rows;
        	home_pagetotal = parseInt(total/home_rows) + (total%home_rows > 0?1:0);
			if(datarows.length > 0){
				html = "";
				$("#indexList").empty();
				
				$("#viewImg").attr("src",'/upload/'+datarows[0].fjlj);
				$("#wzsm").text(datarows[0].wzsm);
				
				if(_page != 1){
					html +='<a id="previousPage" class="a">'+ "上一页" + '</a>';
				}
				for(var i = 1; i <= datarows.length; i++){
					if(i == 1){
						html +='<li class="indexOn">'+ ((_page-1)*home_rows+i) + '</li>';
					}else{
						html +='<li>'+ ((_page-1)*home_rows+i) + '</li>';
					}
				}
				if(_page != home_pagetotal){
					html +='<a id="nextPage" class="a">'+ "下一页" + '</a>';
				}
				
				$("#indexList").append(html);
				
				var count = 0;
			 
				/**
				 * 1.让图片通过setInterval（）定时函数，实现图片自动滑动
				 * 2.点击左右按钮时，清除自动轮播，绑定左右切换事件
				 * 3.自动轮播事件，要注意图片动，相应的文字描述、角标也要跟随变化
				 */
				var autoChange = setInterval(function() {
					if(count < datarows.length - 1) {
						count++;
					} else {
						count = 0;
					}
					//调用变化函数
					toChange(count);
				}, 3000);
				
				/**
				 * 左箭头事件，鼠标划入，停止轮播；滑出，开始轮播
				 * 点击左箭头，表示上一页，这时要确定上一张图片的count数为多少
				 * @param {Object} count
				 */
				$("#prev").hover(function(){
					clearInterval(autoChange);
				},function(){
					autoChangeAgain();
				});
				
				$("#prev").click(function(){
					count = (count > 0)? (--count) : (datarows.length - 1);
					toChange(count);
				});
				
				/**
				 * 右箭头事件，鼠标划入，停止轮播；滑出，开始轮播
				 * 点击右箭头，表示下一页，这时要确定下一张图片的count数为多少
				 * @param {Object} count
				 */
				$("#next").hover(function(){
					clearInterval(autoChange);
				},function(){
					autoChangeAgain();
				});
				
				$("#next").click(function(){
					count = (count < datarows.length -1)? (++count) : 0;
					toChange(count);
				});
				
				/**
				 * 上一页下一页事件
				 */
				$('#previousPage').click(function(){
					clearInterval(autoChange);
					loadPage(home_page-1);
				});
				
				$('#nextPage').click(function(){
					clearInterval(autoChange);
					loadPage(home_page+1);
				});
				
				/**
				 * 给下面的角标绑定滑入事件
				 */
				$(".indexList").find("li").each(function(item){
					$(this).hover(function(){
						clearInterval(autoChange);
						toChange(item);
						count = item;
					},function(){
						autoChangeAgain();
					});
				});
				
				//这个函数要放在里面才可以，因为autoChange、count参数都是提前声明好的，需要传承上一个调用函数的数据
				function autoChangeAgain(){
					autoChange = setInterval(function() {
						if(count < datarows.length - 1) {
							count++;
						} else {
							count = 0;
						}
						//调用变化函数
						toChange(count);
					}, 3000);
				}
				
			}
		}
	});
}

function toChange(count) {
	/**
	* 逻辑处理：
	* 1.图片切换
	* 2.文字切换
	* 3.角标切换
	*/
	$("#viewImg").attr("src",'/upload/'+fjxx_map[count].fjlj);
	$("#wzsm").text(fjxx_map[count].wzsm);
	
	$(".indexList").find("li").removeClass("indexOn").eq(count).addClass("indexOn");
}

$("#QsyrFjxxAddForm_file").filebox({
	onChange: function (n,o) {
		var filename = n;
	
	    if (filename.lastIndexOf("\\") > 0){
	    	filename = filename.substring(filename.lastIndexOf("\\")+1, filename.length);
	    } else if (filename.lastIndexOf("/") > 0){
	    	filename = filename.substring(filename.lastIndexOf("/")+1, filename.length);
	    }
	    var fjhzm = filename.substring(filename.lastIndexOf("."), filename.length);
	    $("#QsyrFjxxAddForm_fjmc").textbox("setValue", filename.substring(0,filename.indexOf(".")));
	}
});

function clearQsyrFjxxAddForm(){
	$('#QsyrFjxxAddForm').form('clear');
}

function uploadFjxxAddForm(){
	var fileName = $("#QsyrFjxxAddForm_file").filebox('getValue');
	var fjhzm = fileName.substring(fileName.lastIndexOf("."), fileName.length);
	var zpsm = $('#zpsm').textbox('getValue');
	var result = $('#QsyrFjxxAddForm').form('validate');
	if (!result) {
		return false;
	}
	
	if(fjhzm!=".jpg"&&fjhzm!=".png"&&fjhzm!=".gif"){
		$.messager.alert('温馨提示', '请选择照片格式', 'info');
		return false;
	}
	
	if(zpsm == null || zpsm == ""){
		$.messager.alert('温馨提示','说点什么吧...','info');
		return false;
	}
	$('#QsyrFjxxAddForm_addQsyrFjxxLinkbutton').linkbutton('disable');
	var options = {
		success : function(data){
			$('#QsyrFjxxAddForm_addQsyrFjxxLinkbutton').linkbutton('enable');
			if(data.message){
				$.messager.alert('温馨提示',data.message,'info');
				loadPage(home_page);
				clearQsyrFjxxAddForm();
			}
			
		}
	};
	$('#QsyrFjxxAddForm').ajaxSubmit(options);
	
}
</script>
</body>
</html>