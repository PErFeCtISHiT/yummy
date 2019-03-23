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
    <title>支付</title>
    <script src="../assets/js/jquery-3.3.1.js"></script>
    <script src="../js/user/member.js"></script>
    <script src="../js/global.js"></script>
</head>
<body>
<header class="am-topbar am-topbar-inverse">
    <h1 class="am-topbar-brand">
        <a href="../login.jsp">yummy!</a>
    </h1>


    <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">

        <div class="am-topbar-right">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm" onclick=logout()>登出</button>
        </div>
        <div class="am-topbar-right">
            <button class="am-btn am-btn-primary am-topbar-btn am-btn-sm" onclick=payFailed(<%=session.getAttribute(NamedContext.ORDERID)%>)>返回</button>
        </div>
    </div>
</header>

<section class="am-panel am-panel-default">
    <header class="am-panel-hd">
        <h3 class="am-panel-title">邮箱</h3>
    </header>
    <div class="am-panel-bd">
        <%=session.getAttribute(NamedContext.MAIL)%>
    </div>
</section>

<section class="am-panel am-panel-default">
    <header class="am-panel-hd">
        <h3 class="am-panel-title">余额/元</h3>
    </header>
    <div class="am-panel-bd">
        <%=session.getAttribute(NamedContext.BALANCE)%>
    </div>
</section>

<section class="am-panel am-panel-default">
    <header class="am-panel-hd">
        <h3 class="am-panel-title">预计运送时间/分钟</h3>
    </header>
    <div class="am-panel-bd">
        <%=session.getAttribute(NamedContext.SENTMINUTE)%>
    </div>
</section>

<section class="am-panel am-panel-default">
    <header class="am-panel-hd">
        <h3 class="am-panel-title">支付金额/元</h3>
    </header>
    <div class="am-panel-bd">
        <%=session.getAttribute(NamedContext.PAY)%>
    </div>
</section>
<section class="am-panel am-panel-default">
    <header class="am-panel-hd">
        <h3 class="am-panel-title">支付时间</h3>
    </header>
    <div class="am-panel-bd" id="span_dt_dt">

    </div>
</section>
<script>
    var begin = new Date();
    begin.setMinutes(begin.getMinutes() + 2);

    function show_date_time() {
        window.setTimeout("show_date_time()", 1000);
        var now = new Date();
        var timeold = (begin.getTime() - now.getTime());
        var msPerDay = 24 * 60 * 60 * 1000
        var e_daysold = timeold / msPerDay
        var daysold = Math.floor(e_daysold);
        var e_hrsold = (e_daysold - daysold) * 24;
        var hrsold = Math.floor(e_hrsold);
        var e_minsold = (e_hrsold - hrsold) * 60;
        var minsold = Math.floor((e_hrsold - hrsold) * 60);
        var seconds = Math.floor((e_minsold - minsold) * 60);
        if(minsold === 0 && seconds === 0){
            payFailed(<%=session.getAttribute(NamedContext.ORDERID)%>);
        }
        span_dt_dt.innerHTML = "支付剩余时间" + minsold + "分" + seconds + "秒" + "";
    }

    show_date_time();
</script>
<div class="am-btn-group">
    <button type="button" class="am-btn am-btn-default" onclick=payFailed(<%=session.getAttribute(NamedContext.ORDERID)%>)>取消</button>
    <button type="button" class="am-btn am-btn-default" onclick=payOrder(<%=session.getAttribute(NamedContext.ORDERID)%>)>支付</button>
</div>
</body>
</html>