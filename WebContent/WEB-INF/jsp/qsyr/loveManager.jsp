<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include.jsp"%>
<title>520</title>
<script type="text/javascript" src="${ctx}/js/qsyr/love/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/qsyr/love/jscex.min.js"></script>
<script type="text/javascript" src="${ctx}/js/qsyr/love/jscex-parser.js"></script>
<script type="text/javascript" src="${ctx}/js/qsyr/love/jscex-jit.js"></script>
<script type="text/javascript" src="${ctx}/js/qsyr/love/jscex-builderbase.min.js"></script>
<script type="text/javascript" src="${ctx}/js/qsyr/love/jscex-async.min.js"></script>
<script type="text/javascript" src="${ctx}/js/qsyr/love/jscex-async-powerpack.min.js"></script>
<script type="text/javascript" src="${ctx}/js/qsyr/love/functions.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/js/qsyr/love/love.js" charset="utf-8"></script>
<style>
body{margin:0;padding:0;background:#ffe;font-size:14px;font-family:'微软雅黑','宋体',sans-serif;color:#231F20;overflow:auto}
a {color:#000;font-size:14px;}
#main{width:100%;}
#wrap{position:relative;margin:0 auto;width:1100px;height:680px;margin-top:10px;}
#text{width:400px;height:425px;left:60px;top:80px;position:absolute;}
#code{display:none;font-size:16px;}
#clock-box {position:absolute;left:60px;top:550px;font-size:28px;display:none;}
#clock-box a {font-size:28px;text-decoration:none;}
#clock{margin-left:48px;}
#clock .digit {font-size:64px;}
#canvas{margin:0 auto;width:1100px;height:680px;}
#error{margin:0 auto;text-align:center;margin-top:60px;display:none;}
.hand{cursor:pointer;}
.say{margin-left:5px;}
.space{margin-right:150px;}

</style>
</head>
<body>
<div id="main">
            <div id="error">亲，您使用的浏览器无法支持即将显示的内容，请换成谷歌(<a href="http://www.google.cn/chrome/intl/zh-CN/landing_chrome.html?hl=zh-CN&brand=CHMI">Chrome</a>)或者火狐(<a href="http://firefox.com.cn/download/">Firefox</a>)浏览器哟~</div>
            <audio src="${ctx}/js/qsyr/love/love.mp3" autoplay="autoplay" loop=""></audio>
            <div id="wrap">
               <div id="text">
                    <div id="code">                 
                    <span class="say">一时间不知道从哪里说起，</span><br>
                    <span class="say">心里一直住着一个人，从2010年9月见到她之后。</span><br>
                    <span class="say">有时候很想不管也不顾了，</span><br>
                    <span class="say">什么是对，什么是错，</span><br>
                    <span class="say">只要拥有她，一切都变得不重要！</span><br>
                    <br>
                    <span class="say">以前的以前，</span><br>
                    <span class="say">总觉得要占据她的所有才算拥有。</span><br>
                    <span class="say">后来的现在，</span><br>
                    <span class="say">520的今天，</span><br>
                    <span class="say">除了祝愿，</span><br>
                    <span class="say">还想告诉你，</span><br>
                    <span class="say">你的幸福是我现在最在意的事！</span><br>
                    <br>
                    <span class="say">很难想象，</span><br>
                    <span class="say">像我这么卑微的人也能拥有这么高尚的爱情。</span><br>
                    <span class="say">假如，</span><br>
                    <span class="say">我是说假如，</span><br>
                    <span class="say">后半辈子还能再碰见你，</span><br>
                    <span class="say">我不要再放过你！</span><br>
                   
                    <span class="say"><span class="space"></span> -- Yours, 通弟.</span>
                    </div>
                </div>
                <div id="clock-box">
                    <a href="https://www.jianshu.com/u/77ca4d5a7cac" target="_blank">@李通</a> 与 <a href="https://www.jianshu.com/u/77ca4d5a7cac" target="_blank">@黄程霞</a> 认识的
                        <div id="clock"></div>
                </div>
                <canvas id="canvas" width="1100" height="680"></canvas>
            </div>
            
        </div>
<script type="text/javascript">
(function(){
    var canvas = $('#canvas');

    if (!canvas[0].getContext) {
        $("#error").show();
        return false;
    }

    var width = canvas.width();
    var height = canvas.height();
    
    canvas.attr("width", width);
    canvas.attr("height", height);

    var opts = {
        seed: {
            x: width / 2 - 20,
            color: "rgb(190, 26, 37)",
            scale: 2
        },
        branch: [
            [535, 680, 570, 250, 500, 200, 30, 100, [
                [540, 500, 455, 417, 340, 400, 13, 100, [
                    [450, 435, 434, 430, 394, 395, 2, 40]
                ]],
                [550, 445, 600, 356, 680, 345, 12, 100, [
                    [578, 400, 648, 409, 661, 426, 3, 80]
                ]],
                [539, 281, 537, 248, 534, 217, 3, 40],
                [546, 397, 413, 247, 328, 244, 9, 80, [
                    [427, 286, 383, 253, 371, 205, 2, 40],
                    [498, 345, 435, 315, 395, 330, 4, 60]
                ]],
                [546, 357, 608, 252, 678, 221, 6, 100, [
                    [590, 293, 646, 277, 648, 271, 2, 80]
                ]]
            ]] 
        ],
        bloom: {
            num: 700,
            width: 1080,
            height: 650,
        },
        footer: {
            width: 1200,
            height: 5,
            speed: 10,
        }
    }

    var tree = new Tree(canvas[0], width, height, opts);
    var seed = tree.seed;
    var foot = tree.footer;
    var hold = 1;

    canvas.click(function(e) {
        var offset = canvas.offset(), x, y;
        x = e.pageX - offset.left;
        y = e.pageY - offset.top;
        if (seed.hover(x, y)) {
            hold = 0; 
            canvas.unbind("click");
            canvas.unbind("mousemove");
            canvas.removeClass('hand');
        }
    }).mousemove(function(e){
        var offset = canvas.offset(), x, y;
        x = e.pageX - offset.left;
        y = e.pageY - offset.top;
        canvas.toggleClass('hand', seed.hover(x, y));
    });

    var seedAnimate = eval(Jscex.compile("async", function () {
        seed.draw();
        while (hold) {
            $await(Jscex.Async.sleep(10));
        }
        while (seed.canScale()) {
            seed.scale(0.95);
            $await(Jscex.Async.sleep(10));
        }
        while (seed.canMove()) {
            seed.move(0, 2);
            foot.draw();
            $await(Jscex.Async.sleep(10));
        }
    }));

    var growAnimate = eval(Jscex.compile("async", function () {
        do {
            tree.grow();
            $await(Jscex.Async.sleep(10));
        } while (tree.canGrow());
    }));

    var flowAnimate = eval(Jscex.compile("async", function () {
        do {
            tree.flower(2);
            $await(Jscex.Async.sleep(10));
        } while (tree.canFlower());
    }));

    var moveAnimate = eval(Jscex.compile("async", function () {
        tree.snapshot("p1", 240, 0, 610, 680);
        while (tree.move("p1", 500, 0)) {
            foot.draw();
            $await(Jscex.Async.sleep(10));
        }
        foot.draw();
        tree.snapshot("p2", 500, 0, 610, 680);

        // 会有闪烁不得已这样做, (＞﹏＜)
        canvas.parent().css("background", "url(" + tree.toDataURL('image/png') + ")");
        canvas.css("background", "#ffe");
        $await(Jscex.Async.sleep(300));
        canvas.css("background", "none");
    }));

    var jumpAnimate = eval(Jscex.compile("async", function () {
        var ctx = tree.ctx;
        while (true) {
            tree.ctx.clearRect(0, 0, width, height);
            tree.jump();
            foot.draw();
            $await(Jscex.Async.sleep(25));
        }
    }));

    var textAnimate = eval(Jscex.compile("async", function () {
        var together = new Date();
        together.setFullYear(2010, 8, 29);
        together.setHours(19);
        together.setMinutes(0);
        together.setSeconds(0);
        together.setMilliseconds(0);

        $("#code").show().typewriter();
        $("#clock-box").fadeIn(500);
        while (true) {
            timeElapse(together);
            $await(Jscex.Async.sleep(2000));
        }
    }));

    var runAsync = eval(Jscex.compile("async", function () {
        $await(seedAnimate());
        $await(growAnimate());
        $await(flowAnimate());
        $await(moveAnimate());

        textAnimate().start();

        $await(jumpAnimate());
    }));

    runAsync().start();
})();
</script>
</body>
</html>