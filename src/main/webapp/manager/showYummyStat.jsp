<%@ page import="yummy.util.NamedContext" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Double account = (Double) session.getAttribute(NamedContext.YUMMY);
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
    <title>Yummy!财务情况</title>
    <script src="../assets/js/jquery-3.3.1.js"></script>
    <script src="../js/echarts.common.min.js"></script>
    <script src="../js/user/manager.js"></script>
    <script src="../js/global.js"></script>
</head>
<script>
    yummyAccounts = <%=session.getAttribute(NamedContext.ACCOUNT)%>;
</script>
<body onload=loadYummyCharts()>
<header class="am-topbar am-topbar-inverse">
    <h1 class="am-topbar-brand">
        <a href="../login.jsp">yummy!</a>
    </h1>

    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">
        <div class="am-topbar-right">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm" onclick=logout()>登出</button>
        </div>
        <div class="am-topbar-right">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm" onclick=window.location.href='mainPage.jsp'>返回</button>
        </div>
    </div>
</header>
<form class="am-form">
    <fieldset>
        <legend>财务情况</legend>

        <div class="am-form-group">
            <label for="num">Yummy总资产</label>
            <span id="num"><%=account%>元</span>
        </div>
        <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
        <div class="am-form-group">
            <label for="account">每月收支</label>
            <div id="account" style="width: 600px;height:400px;"></div>
        </div>
    </fieldset>
</form>
</body>
</html>