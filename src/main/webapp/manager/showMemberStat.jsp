<%@ page import="yummy.util.NamedContext" %>
<%@ page import="org.json.JSONArray" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    JSONArray array = new JSONArray(session.getAttribute(NamedContext.MEMBER).toString());
    Integer memberNum = array.length();
%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <!-- Set render engine for 360 browser -->
    <meta name="renderer" content="webkit">

    <!-- No Baidu Siteapp-->
    <meta http-equiv="Cache-Control" content="no-siteapp"/>

    <link rel="icon" type="image/png" href="../assets/i/favicon.png">

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="../assets/i/app-icon72x72@2x.png">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="apple-touch-icon-precomposed" href="../assets/i/app-icon72x72@2x.png">

    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="assets/i/app-icon72x72@2x.png">
    <meta name="msapplication-TileColor" content="#0e90d2">

    <link rel="stylesheet" href="../assets/css/amazeui.min.css">
    <link rel="stylesheet" href="../assets/css/app.css">
    <title>会员统计</title>
    <script src="../assets/js/jquery-3.3.1.js"></script>
    <script src="../js/echarts.common.min.js"></script>
    <script src="../js/user/manager.js"></script>
    <script src="../js/global.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
    <script type="text/javascript"
            src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
    <script type="text/javascript"
            src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=3.0&ak=1Wg79yDG3F19q9MRVQSzrA187VO2Y9Dg"></script>
</head>
<script>
    members = <%=session.getAttribute(NamedContext.MEMBER)%>;
    memberLevel = <%=session.getAttribute(NamedContext.MEMBERLEVEL)%>;
    addresses = <%=session.getAttribute(NamedContext.ADDRESS)%>;
</script>
<body onload=loadMemberCharts()>
<header class="am-topbar am-topbar-inverse">
    <h1 class="am-topbar-brand">
        <a href="../login.jsp">yummy!</a>
    </h1>

    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">
        <div class="am-topbar-right">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm" onclick=logout()>登出</button>
        </div>
        <div class="am-topbar-right">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm" onclick=window.location.href='mainPage.jsp'>
                返回
            </button>
        </div>
    </div>
</header>
<form class="am-form">
    <fieldset>
        <legend>会员统计</legend>

        <div class="am-form-group">
            <label for="num">会员总数</label>
            <span id="num"><%=memberNum%></span>
        </div>
        <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
        <div class="am-form-group">
            <label for="sign">注册月份分布</label>
            <div id="sign" style="width: 600px;height:400px;"></div>
        </div>
        <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
        <div class="am-form-group">
            <label for="type">会员等级占比图</label>
            <div id="type" style="width: 600px;height:400px;"></div>
        </div>
        <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
        <div class="am-form-group">
            <label for="address">会员分布</label>
            <div id="address" style="width: 600px;height:400px;"></div>
        </div>

    </fieldset>
</form>
</body>
</html>