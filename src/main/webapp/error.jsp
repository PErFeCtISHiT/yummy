<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="yummy.util.NamedContext" %>
<%
    String errorMessage;
    switch (request.getServletContext().getAttribute(NamedContext.ERROR).toString()){
        case NamedContext.ACTIVEFAILED:
            errorMessage = "邮箱激活失败";
            break;
        case NamedContext.REPEATUSER:
            errorMessage = "该邮箱已被注册";
            break;
        case NamedContext.MAILINVALID:
            errorMessage = "邮箱不符合规范";
            break;
        case NamedContext.INCORRECTPASSWORD:
            errorMessage = "密码错误";
            break;
        case NamedContext.UNLOGIN:
            errorMessage = "未登录";
            break;
        case NamedContext.UNKNOWNUSER:
            errorMessage = "该邮箱未注册";
            break;
        case NamedContext.INACTIVE:
            errorMessage = "该邮箱未激活";
            break;
        case NamedContext.DESTROYED:
            errorMessage = "该用户已被注销";
            break;
        case NamedContext.UNAUTHORIZED:
            errorMessage = "未授权";
            break;
        case NamedContext.ADDRESSERROR:
            errorMessage = "未设置收货地址";
            break;
            default:
                errorMessage = "未知错误";
                break;
    }
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
    <title>错误</title>
    <script src="assets/js/jquery-3.3.1.js"></script>
    <script src="js/global.js"></script>
</head>
<body>
<header class="am-topbar am-topbar-inverse">
    <h1 class="am-topbar-brand">
        <a href="login.jsp">yummy!</a>
    </h1>
    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">
        <div class="am-topbar-right">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm" onclick=mainPage()>返回登陆页面</button>
        </div>
    </div>
</header>
<div style="position: relative;top:2em;float: top">
    <a class="am-badge am-badge-danger am-round am-text-lg">错误: <%=errorMessage%></a>
</div>
</body>
</html>
