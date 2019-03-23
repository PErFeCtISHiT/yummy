var address;

function addRestaurant() {
    var password = window.prompt('密码', '');
    if (password != null && password !== "") {
        var para = {userPassword: password};
        $.ajax(
            {
                type: 'POST',
                url: '/restaurant/signUp',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(para),
                dataType: "json",
                success: function (data) {
                    if (data.message === 'failed') {
                        alert('申请失败');
                    } else {
                        alert('您的登陆码为:' + data.loginToken)
                    }
                }
            }
        )
    }
}

function addAddress() {
    var map = window.open('/restaurant/map.jsp', 'newwindow', 'top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no');

    var loop = setInterval(function () {
        if (map.closed) {
            clearInterval(loop);
            address = {
                addressName: map.document.getElementById("addressName").innerHTML,
                longitude: map.document.getElementById("lng").innerHTML,
                latitude: map.document.getElementById("lat").innerHTML
            };
            document.getElementById("address").innerHTML = '<option>' + address.addressName + '</option>'
        }
    }, 1000);
}

function modifyRestaurant() {
    var restaurantName = document.getElementById("name").value;
    var restaurantType;
    var radio = document.getElementsByName("docInlineRadio");
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].checked) {
            restaurantType = radio[i].value
        }
    }
    var para = address;
    para.restaurantName = restaurantName;
    para.restaurantType = restaurantType;
    $.ajax(
        {
            type: 'POST',
            url: '/restaurant/modifyRestaurant',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(para),
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('修改失败');
                    window.location.href = '/restaurant/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('修改成功,等待审批');
                    window.location.href = '/restaurant/mainPage.jsp'
                }
            }
        }
    )
}

function addProduct() {
    var productName = document.getElementById("name").value;
    var num = document.getElementById("num").value;
    var price = document.getElementById("price").value;
    var radio = document.getElementsByName("docInlineRadio");
    var endDate = document.getElementById("date").value;
    var type;
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].checked) {
            type = radio[i].value
        }
    }
    var para = {productName: productName, num: num, price: price, type: type, endDate: endDate};
    $.ajax(
        {
            type: 'POST',
            url: '/restaurant/addProduct',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(para),
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('餐厅尚有未审批的信息，请等待管理员审批');
                    window.location.href = '/restaurant/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('添加成功');
                    window.location.href = '/restaurant/mainPage.jsp'
                }
            }
        }
    )
}

var orderProducts = [];

function loadProducts() {
    document.getElementById("product").innerHTML = "";
    for (var i = 0; i < obj.length; i++) {
        if (obj[i].num > 0) {
            document.getElementById("product").innerHTML += "<section class=\"am-panel am-panel-default am-panel-secondary\">\n" +
                "    <header class=\"am-panel-hd\">\n" +
                "        <h3 class=\"am-panel-title\">商品名称:" + obj[i].productName + "</h3>\n" +
                "    </header>\n" +
                "    <div class=\"am-panel-bd\">\n" +
                "<span>商品价格:" + obj[i].price + "元</span><br>" +
                "<span>剩余数量:" + obj[i].num + "件</span><br>" +
                "<a class=\"am-btn am-btn-default\" onclick=\"pushProduct(" + obj[i].id + "," + obj[i].price + ")\">添加</a>\n" +
                "    </div>\n" +
                "</section>"
        }
    }
}

function pushProduct(pid, price) {
    var f = false;
    for (var i = 0; i < orderProducts.length; i++) {
        if (orderProducts[i].pid === pid) {
            orderProducts[i].num++;
            f = true;
            break;
        }
    }
    if (!f) {
        var t = {pid: pid, num: 1};
        orderProducts.push(t);
    }
    var p = parseFloat(document.getElementById("price").value);
    price = parseFloat(price);
    document.getElementById("price").value = p + price;
    alert('添加成功');
}

function addOrder() {
    var orderName = document.getElementById("name").value;
    var price = document.getElementById("price").value;
    var endDate = document.getElementById("date").value;
    var discount = document.getElementById("discount").value;
    console.log(orderProducts);
    var para = {
        orderName: orderName,
        price: price,
        endDate: endDate,
        discount: discount,
        pidList: JSON.stringify(orderProducts)
    };
    $.ajax(
        {
            type: 'POST',
            url: '/restaurant/addOrder',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(para),
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('餐厅尚有未审批的信息，请等待管理员审批');
                    window.location.href = '/restaurant/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('添加成功');
                    window.location.href = '/restaurant/mainPage.jsp'
                }
            }
        }
    )

}
var deliveredOrders;
function getDeliveredOrder() {
    $.ajax(
        {
            type: 'GET',
            url: '/restaurant/getDeliveredOrder',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('获取失败');
                    window.location.href = '/restaurant/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    window.location.href = '/restaurant/deliveredOrders.jsp'
                }
            }
        }
    )
}
function loadOrders() {
    document.getElementById("order").innerHTML = "";
    for (var i = 0; i < deliveredOrders.length; i++) {
        document.getElementById("order").innerHTML += "<section class=\"am-panel am-panel-default am-panel-secondary\">\n" +
            "    <header class=\"am-panel-hd\">\n" +
            "        <h3 class=\"am-panel-title\">套餐名称:" + deliveredOrders[i].orderName + "</h3>\n" +
            "<h3 class=\"am-panel-title\">订购日期:"+deliveredOrders[i].endDate+"</h3>"+
            "<h3 class=\"am-panel-title\">套餐金额:"+deliveredOrders[i].price+" 元</h3>"+
            "    </header>\n" +
            "    <div class=\"am-panel-bd\" id="+deliveredOrders[i].id+">\n";
        document.getElementById("order").innerHTML +=    "    </div>\n" +
            "</section>"
        var orderProducts = JSON.parse(deliveredOrders[i].pidList);
        document.getElementById(deliveredOrders[i].id).innerHTML += "<table class=\"am-table\">\n" +
            "    <thead>\n" +
            "        <tr>\n" +
            "            <th>单品名称</th>\n" +
            "            <th>单品数量</th>\n" +
            "        </tr>\n" +
            "    </thead>\n" + "    <tbody id="+("body"+deliveredOrders[i].id.toString()).toString()+">";
        document.getElementById("order").innerHTML += "    </tbody>\n" +
            "</table>";
        for(var j = 0;j < orderProducts.length;j++){
            document.getElementById(("body"+deliveredOrders[i].id.toString().toString())).innerHTML += "<tr>\n" +
                "            <td>"+orderProducts[j].productName+"</td>\n" +
                "            <td>"+orderProducts[j].num+"</td>\n" +
                "        </tr>"
        }
    }
}
function showStatMain(status,orderDate,price,memberLevel) {
    var para = {status:status,orderDate:orderDate,price:price,memberLevel:memberLevel};
    $.ajax(
        {
            type: 'POST',
            url: '/restaurant/getStat',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(para),
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('信息获取失败');
                    window.location.href = '/restaurant/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    window.location.href = '/restaurant/stat.jsp?status='+status+'&orderDate='+orderDate+'&price='+price+'&memberLevel='+memberLevel
                }
            }
        }
    )
}
var orderStat;
function loadStat() {
    document.getElementById("order").innerHTML = "";
    for (var i = 0; i < orderStat.length; i++) {
        document.getElementById("order").innerHTML += "<section class=\"am-panel am-panel-default am-panel-secondary\">\n" +
            "    <header class=\"am-panel-hd\">\n" +
            "        <h3 class=\"am-panel-title\">套餐名称:" + orderStat[i].orderName + "</h3>\n" +
            "<h3 class=\"am-panel-title\">订购日期:"+orderStat[i].endDate+"</h3>"+
            "<h3 class=\"am-panel-title\">套餐金额:"+orderStat[i].price+" 元</h3>"+
            "    </header>\n" +
            "    <div class=\"am-panel-bd\" id="+orderStat[i].id+">\n";
        document.getElementById("order").innerHTML +=    "    </div>\n" +
            "</section>"
        var orderProducts = JSON.parse(orderStat[i].pidList);
        document.getElementById(orderStat[i].id).innerHTML += "<table class=\"am-table\">\n" +
            "    <thead>\n" +
            "        <tr>\n" +
            "            <th>单品名称</th>\n" +
            "            <th>单品数量</th>\n" +
            "        </tr>\n" +
            "    </thead>\n" + "    <tbody id="+("body"+orderStat[i].id.toString()).toString()+">";
        document.getElementById("order").innerHTML += "    </tbody>\n" +
            "</table>";
        for(var j = 0;j < orderProducts.length;j++){
            document.getElementById(("body"+orderStat[i].id.toString().toString())).innerHTML += "<tr>\n" +
                "            <td>"+orderProducts[j].productName+"</td>\n" +
                "            <td>"+orderProducts[j].num+"</td>\n" +
                "        </tr>"
        }
    }
}