<%@ page import="yummy.util.NamedContext" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>订购</title>
    <script src="../assets/js/jquery-3.3.1.js"></script>
    <script src="../js/user/member.js"></script>
    <script src="../js/global.js"></script>
    <link rel="stylesheet" type="text/css" href="../js/default.css">
</head>
<script>
    var obj = <%=session.getAttribute(NamedContext.ALLPRODUCTS)%>;
    var restaurantId =
    <%=request.getParameter(NamedContext.RESTAURANTID)%>
</script>
<body onload=loadAllProducts(obj)>
<header class="am-topbar">
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
        <legend>订购单品</legend>

        <div class="am-form-group">
            <label for="price">价格</label>
            <input type="number" value="0.0" min="0.0" step="0.01" class="" id="price" contenteditable="false">
        </div>

        <div>
            <label for="product">添加单品</label>
            <div id="product">

            </div>
        </div>

    </fieldset>
</form>
<div class="am-btn-group">
    <button type="button" class="am-btn am-btn-default" onclick=window.location.href='mainPage.jsp'>取消</button>
    <button type="button" class="am-btn am-btn-default" onclick=addSingleOrder(restaurantId)>订购</button>
</div>
<form class="am-form">
    <fieldset>
        <legend>订购套餐</legend>

        <div>
            <label for="order">选择套餐</label>
            <div id="order">

            </div>
        </div>


    </fieldset>
</form>
</body>
</html>