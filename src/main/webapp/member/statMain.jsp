<%@ page import="yummy.util.NamedContext" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String orderType = request.getParameter(NamedContext.STATUS);
    String showOrderType;
    switch (orderType){
        case NamedContext.ALL:
            showOrderType = "所有订单";
            break;
        case NamedContext.PAYED:
            showOrderType = "待送达";
            break;
        case NamedContext.CANCELED:
            showOrderType = "已退订";
            break;
        case NamedContext.DELIVERED:
            showOrderType = "已送达";
            break;
        default:
            showOrderType = "error";
    }
    String orderDate = request.getParameter(NamedContext.ORDERDATE);
    String showOrderDate;
    if(orderDate.equals(NamedContext.ALL))
        showOrderDate = "";
    else
        showOrderDate = orderDate;
    String price = request.getParameter(NamedContext.PRICE);
    String showPrice;
    if(price.equals(NamedContext.ALL))
        showPrice = "所有金额";
    else{
        Integer priceValue = Integer.valueOf(price);
        switch (priceValue){
            case 20:
                showPrice = "0-20";
                break;
            case 40:
                showPrice = "20-40";
                break;
                default:
                    showPrice = "40+";
                    break;
        }
    }
    String restaurantType = request.getParameter(NamedContext.RESTAURANTTYPE);
    String showRestaurantType;
    if(restaurantType.equals(NamedContext.ALL))
        showRestaurantType = "所有类型";
    else
        showRestaurantType = restaurantType;
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
    <title>消费信息</title>
    <script src="../assets/js/jquery-3.3.1.js"></script>
    <script src="../js/user/member.js"></script>
    <script src="../js/global.js"></script>
    <script src="../assets/js/amazeui.min.js"></script>
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
        <div class="am-topbar-right">
            <div class="am-dropdown" data-am-dropdown="{boundary: '.am-topbar'}">
                <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm am-dropdown-toggle" data-am-dropdown-toggle id="dropButton"></button>
                <ul class="am-dropdown-content">
                    <li class="am-dropdown-header">订单类型</li>
                    <li><a onclick=showStatMain('all',orderDate,orderDate,restaurantType)>所有订单</a></li>
                    <li><a onclick=showStatMain('payed',orderDate,orderDate,restaurantType)>待送达</a></li>
                    <li><a onclick=showStatMain('canceled',orderDate,orderDate,restaurantType)>已退订</a></li>
                    <li><a onclick=showStatMain('delivered',orderDate,orderDate,restaurantType)>已送达</a></li>
                </ul>
            </div>
        </div>
        <div class="am-topbar-right">
            <div class="am-dropdown" data-am-dropdown="{boundary: '.am-topbar'}">
                <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm am-dropdown-toggle" data-am-dropdown-toggle>订购日期 <span class="am-icon-caret-down"></span></button>
                <ul class="am-dropdown-content">
                    <li><a onclick=showStatMain(orderType,'all',orderPrice,restaurantType)>所有日期</a></li>
                    <input type="date" id="date" onchange=showStatMain(orderType,value,orderPrice,restaurantType)>
                </ul>
            </div>
        </div>
        <div class="am-topbar-right">
            <div class="am-dropdown" data-am-dropdown="{boundary: '.am-topbar'}">
                <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm am-dropdown-toggle" data-am-dropdown-toggle id="priceDropButton"></button>
                <ul class="am-dropdown-content">
                    <li class="am-dropdown-header">订单金额/元</li>
                    <li><a onclick=showStatMain(orderType,orderDate,'all',restaurantType)>所有金额</a></li>
                    <li><a onclick=showStatMain(orderType,orderDate,'20',restaurantType)>0-20</a></li>
                    <li><a onclick=showStatMain(orderType,orderDate,'40',restaurantType)>20-40</a></li>
                    <li><a onclick=showStatMain(orderType,orderDate,'1000',restaurantType)>40+</a></li>
                </ul>
            </div>
        </div>
        <div class="am-topbar-right">
            <div class="am-dropdown" data-am-dropdown="{boundary: '.am-topbar'}">
                <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm am-dropdown-toggle" data-am-dropdown-toggle id="typeDropButton"></button>
                <ul class="am-dropdown-content">
                    <li class="am-dropdown-header">餐厅类型</li>
                    <li><a onclick=showStatMain(orderType,orderDate,orderPrice,'all')>所有类型</a></li>
                    <li><a onclick=showStatMain(orderType,orderDate,orderPrice,'美食')>美食</a></li>
                    <li><a onclick=showStatMain(orderType,orderDate,orderPrice,'饮品')>饮品</a></li>
                    <li><a onclick=showStatMain(orderType,orderDate,orderPrice,'甜品')>甜品</a></li>
                </ul>
            </div>
        </div>
    </div>
</header>
<form class="am-form">
    <fieldset>
        <legend>订单</legend>

        <div>
            <div id="order">

            </div>
        </div>


    </fieldset>
</form>
<script>
    var orderType = '<%=orderType%>';
    var orderDate = '<%=orderDate%>';
    var orderPrice = '<%=price%>';
    var restaurantType = '<%=restaurantType%>';
    document.getElementById("dropButton").innerHTML = "<%=showOrderType%>" + " <span class=\"am-icon-caret-down\"></span>";
    document.getElementById("priceDropButton").innerHTML = "<%=showPrice%>" + " <span class=\"am-icon-caret-down\"></span>";
    document.getElementById("typeDropButton").innerHTML = "<%=showRestaurantType%>" + " <span class=\"am-icon-caret-down\"></span>";
    orderStat = <%=session.getAttribute(NamedContext.ORDERS)%>
    loadStat();
</script>
</body>
</html>