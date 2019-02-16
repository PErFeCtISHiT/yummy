<%@ page import="yummy.entity.UserEntity" %>
<%@ page import="yummy.util.NamedContext" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserEntity user = (UserEntity) session.getAttribute(NamedContext.USER);
    request.getServletContext().setAttribute(NamedContext.ERROR, NamedContext.UNLOGIN);
    if(user == null)
        request.getRequestDispatcher("/error.jsp").forward(request,response);
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
    <title>用户信息</title>
    <script src="../assets/js/jquery-3.3.1.js"></script>
    <script src="../js/user/member.js"></script>
    <script src="../js/global.js"></script>
</head>
<body>
<header class="am-topbar">
    <h1 class="am-topbar-brand">
        <a href="../login.jsp">yummy!</a>
    </h1>

    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">

        <div class="am-topbar-right">
            <button title="用户将被销毁，不可恢复" class="am-btn am-btn-danger am-topbar-btn am-btn-sm" onclick=destroyUser()>
                注销用户
            </button>
        </div>
        <div class="am-topbar-right">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm" onclick=logout()>登出</button>
        </div>
        <div class="am-topbar-right">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm" onclick=window.location.href='mainPage.jsp'>返回</button>
        </div>
    </div>
</header>
<div id="level">
    <span>用户级别:</span>
</div>
<script>
    var level =<%=user.getMemberMessageEntity().getLevel()%>
    var color;
    switch (level) {
        case 1:
            color = '';
            break;
        case 2:
            color = 'primary';
            break;
        case 3:
            color = 'secondary';
            break;
        case 4:
            color = 'success';
            break;
        case 5:
            color = 'warning';
            break;
        default:
            color = 'danger';
            break;
    }
    if (level === 1)
        document.getElementById("level").innerHTML += '<span class="am-badge am-round">1</span>';
    else
        document.getElementById("level").innerHTML += '<span class="am-badge am-badge-' + color + ' am-round">' + level + '</span>';
</script>
<form class="am-form">
    <fieldset>
        <legend>个人信息</legend>

        <div class="am-form-group">
            <label for="name">姓名</label>
            <input type="text" class="" id="name" value=<%=user.getMemberMessageEntity().getMemberName()%>>
        </div>

        <div class="am-form-group">
            <label for="telephone">电话</label>
            <input type="tel" class="" id="telephone"
                   value=<%=user.getMemberMessageEntity().getTelephone()%>>
        </div>


        <div class="am-btn-group">
            <button type="button" class="am-btn am-btn-default" onclick=window.location.href='mainPage.jsp'>取消</button>
            <button type="button" class="am-btn am-btn-default" onclick=modifyUser()>修改信息</button>
        </div>
    </fieldset>
</form>
</body>
</html>