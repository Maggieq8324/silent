<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="/silent" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中国象棋</title>
<link rel="stylesheet" href="${ctx}/css/chess/index.css" type="text/css">
<link rel="stylesheet" href="${ctx}/css/chess/Chess.css" type="text/css">

<script src="${ctx}/js/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/js/chess/Show.js" type="text/javascript"></script>
<script src="${ctx}/js/chess/OnDoing.js" type="text/javascript"></script>
<script src="${ctx}/js/chess/CMoveRule.js" type="text/javascript"></script>
<script src="${ctx}/js/chess/CChess.js" type="text/javascript"></script>
</head>
<body>
<section id="ground">
    <section id="board">
        <section id="line">
            <section id="rows">
                <article class="row"></article>
                <article class="row"></article>
                <article class="row"></article>
                <article class="row"></article>
                <article class="row"></article>
                <article class="row"></article>
                <article class="row"></article>
                <article class="row"></article>
                <article class="row"></article>
                <article class="row end"></article>
            </section>
            <section id="lines">
                <article class="line"></article>
                <article class="line"></article>
                <article class="line"></article>
                <article class="line"></article>
                <article class="line"></article>
                <article class="line"></article>
                <article class="line"></article>
                <article class="line"></article>
                <article class="line end"></article>
            </section>
            <section id="river">
                <article>۞۞۞۞۞۞۞۞</article>
            </section>
            <section id="flower">
                <section id="F">
                    <section class="L2">
                        <article></article>
                        <article class="t2"></article>
                    </section>
                    <section class="L5">
                        <article></article>
                        <article></article>
                        <article></article>
                        <article></article>
                        <article></article>
                    </section>
                </section>
                <section id="L">
                    <section class="L5">
                        <article></article>
                        <article></article>
                        <article></article>
                        <article></article>
                        <article></article>
                    </section>
                    <section class="L2">
                        <article></article>
                        <article></article>
                    </section>
                </section>
            </section>
            <section id="cross">
                <section id="T">
                    <article></article>
                    <article></article>
                </section>
                <section id="B">
                    <article></article>
                    <article></article>
                </section>
            </section>
        </section>
        <section id="space"></section>
    </section>
</section>
</body>
</html>