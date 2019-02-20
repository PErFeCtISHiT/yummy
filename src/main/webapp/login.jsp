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

    <link rel="icon" type="image/png" href="assets/i/favicon.png">

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="assets/i/app-icon72x72@2x.png">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
    <link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">

    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="assets/i/app-icon72x72@2x.png">
    <meta name="msapplication-TileColor" content="#0e90d2">

    <link rel="stylesheet" href="assets/css/amazeui.min.css">
    <link rel="stylesheet" href="assets/css/app.css">
    <link rel="stylesheet" href="assets/css/bg.css">
    <title>登陆</title>
    <script src="assets/js/jquery-3.3.1.js"></script>
    <script src="js/user/member.js"></script>
    <script src="js/user/restaurant.js"></script>
    <script src="js/global.js"></script>
</head>
<body>
<header class="am-topbar am-topbar-inverse">
    <h1 class="am-topbar-brand">
        <a href="login.jsp">yummy!</a>
    </h1>
    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">
        <div class="am-topbar-left">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm"
                    onclick=window.location.href='/member/signUp.jsp'>会员注册
            </button>
        </div>
        <div class="am-topbar-left">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm" onclick=addRestaurant()>餐厅申请</button>
        </div>
    </div>
</header>
<form class="am-form am-form-horizontal" style="position: relative;left:15em;top:8em;max-width: 60%">

    <div class="am-form-group am-form-group-sm">
        <label for="loginTokenInput" class="am-u-sm-2 am-form-label">登陆码</label>
        <div class="am-u-sm-10">
            <input type="text" name="loginToken" id="loginTokenInput" class="am-form-field">
        </div>
    </div>

    <div class="am-form-group am-form-group-sm">
        <label for="loginTokenInput" class="am-u-sm-2 am-form-label">密码</label>
        <div class="am-u-sm-10">
            <input type="password" name="userPassword" id="passwordInput" class="am-form-field">
        </div>
    </div>

    <div class="am-form-group">
        <div class="am-u-sm-10 am-u-sm-offset-2">
            <button type="button" class="am-btn am-btn-primary am-btn-block" onclick="login()">登陆</button>
        </div>
    </div>
</form>
</body>
</html>
