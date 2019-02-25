<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>送餐地址</title>
    <style type="text/css">
        html {
            height: 100%
        }

        body {
            height: 100%;
            margin: 0px;
            padding: 0px
        }

        #container {
            height: 100%
        }
    </style>
    <script src="../assets/js/jquery-3.3.1.js"></script>
    <script src="../js/global.js"></script>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=3.0&ak=1Wg79yDG3F19q9MRVQSzrA187VO2Y9Dg"></script>
</head>

<body>
<div id="container"></div>

<script type="text/javascript">
    var map = new BMap.Map("container");
    // 创建地图实例
    var point = new BMap.Point(118.805871, 32.07224);
    // 创建点坐标
    map.centerAndZoom(point, 12);
    // 初始化地图，设置中心点坐标和地图级别
    map.addEventListener("click", function (e) {
        var addressName = window.prompt("地址名称", "");
        if (addressName != null && addressName !== "") {
            var para = {"addressName": addressName, "longitude": e.point.lng, "latitude": e.point.lat}
            $.ajax(
                {
                    type: 'POST',
                    url: '/member/addAddress',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(para),
                    dataType: "json",
                    success: function (data) {
                        if (data.message === 'success')
                            alert('添加成功');
                        else
                            alert('添加失败');
                        closeWindow();
                    }
                }
            )
        }
    });
</script>
</body>
</html>