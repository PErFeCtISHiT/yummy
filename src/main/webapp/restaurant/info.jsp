<%@ page import="yummy.entity.UserEntity" %>
<%@ page import="yummy.util.NamedContext" %>
<%@ page import="org.json.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    UserEntity user = (UserEntity) session.getAttribute(NamedContext.USER);
    request.getServletContext().setAttribute(NamedContext.ERROR, NamedContext.UNLOGIN);
    if (user == null)
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    assert user != null;
    JSONObject jsonObject = new JSONObject();
    if (user.getRestaurantMessageEntity().getAddressEntity() != null) {
        jsonObject = new JSONObject(user.getRestaurantMessageEntity().getAddressEntity());
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
    <title>餐厅信息</title>
    <script src="../assets/js/jquery-3.3.1.js"></script>
    <script src="../js/user/restaurant.js"></script>
    <script src="../js/global.js"></script>
</head>
<body>
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
        <legend>餐厅信息</legend>

        <div class="am-form-group">
            <label for="name">餐厅名称</label>
            <input type="text" class="" id="name" value=<%=user.getRestaurantMessageEntity().getRestaurantName()%>>
        </div>

        <div class="am-form-group">
            <label for="type1">餐厅类型</label>
            <label class="am-radio-inline" id="type1">
                <input type="radio" value="美食" name="docInlineRadio"> 美食
            </label>
            <label class="am-radio-inline" id="type2">
                <input type="radio" value="饮品" name="docInlineRadio"> 饮品
            </label>
            <label class="am-radio-inline" id="type3">
                <input type="radio" value="甜品" name="docInlineRadio"> 甜品
            </label>
        </div>
        <div class="am-form-group">
            <label for="address">餐厅地址</label>
            <div class="am-btn-group">
                <button type="button" class="am-btn am-btn-success am-radius" id="doc-prompt-toggle"
                        onclick=addAddress()>修改地址
                </button>
            </div>
            <select multiple class="" id="address">

            </select>
            <script>
                address = <%=jsonObject%>;
                if (address.addressName !== undefined) {
                    document.getElementById("address").innerHTML += '<option>' + address.addressName + '</option>'
                }
            </script>
        </div>


        <div class="am-btn-group">
            <button type="button" class="am-btn am-btn-default" onclick=window.location.href='mainPage.jsp'>取消</button>
            <button type="button" class="am-btn am-btn-default" onclick=modifyRestaurant()>修改信息</button>
        </div>
    </fieldset>
</form>
</body>
</html>