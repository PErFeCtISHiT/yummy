function memberSignUp() {
    var loginToken = document.getElementById("loginTokenInput").value;
    var password = document.getElementById("passwordInput").value;
    var token = {"loginToken": loginToken, "userPassword": password};
    $.ajax(
        {
            type: 'POST',
            url: '/member/signUp',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(token),
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    window.location.href = '/error.jsp'
                } else {
                    window.location.href = '/member/inactive.jsp'
                }
            }
        }
    )
}

function login() {
    var loginToken = document.getElementById("loginTokenInput").value;
    var password = document.getElementById("passwordInput").value;
    var token = {"loginToken": loginToken, "userPassword": password};
    $.ajax(
        {
            type: 'POST',
            url: '/user/login',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(token),
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    window.location.href = '/error.jsp'
                } else {
                    if (data.userType === 'member')
                        window.location.href = '/member/mainPage.jsp';
                    else if (data.userType === 'restaurant')
                        window.location.href = '/restaurant/mainPage.jsp';
                    else if (data.userType === 'manager')
                        window.location.href = '/manager/mainPage.jsp';
                    else
                        window.location.href = '/error.jsp';
                }
            }
        }
    )
}

function addAddress() {
    window.location.href = '/member/mainPage.jsp';
    window.open('/member/map.jsp', 'newwindow', 'top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no')
}

function removeAddress() {
    var selectedValues = [];
    $("#address :selected").each(function () {
        selectedValues.push($(this).val());
    });
    var para = {address: selectedValues};

    $.ajax(
        {
            type: 'POST',
            url: '/member/removeAddress',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(para),
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('删除失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('删除成功');
                    window.location.href = '/member/mainPage.jsp'
                }
            }
        }
    )
}

function modifyUser() {
    var memberName = document.getElementById("name").value;
    var telephone = document.getElementById("telephone").value;
    var para = {memberName: memberName, telephone: telephone};
    $.ajax(
        {
            type: 'POST',
            url: '/member/modifyUser',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(para),
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('修改失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('修改成功');
                    window.location.href = '/member/mainPage.jsp'
                }
            }
        }
    )
}

function destroyUser() {
    $.ajax(
        {
            type: 'GET',
            url: '/member/destroyUser',
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    window.location.href = '/error.jsp'
                } else {
                    window.location.href = '/login.jsp'
                }
            }
        }
    )
}

function loadRestaurants() {
    var pageNum = parseInt((obj.length + 11) / 12);
    document.getElementById("page").innerHTML = "";
    for (var i = 0; i < pageNum; i++) {
        if (i !== page)
            document.getElementById("page").innerHTML += "<li><a href=\"/product/products.jsp?page=" + i + "\">" + (i + 1) + "</a></a></li>\n";
        else
            document.getElementById("page").innerHTML += "<li class=\"am-active\"><a href=\"/product/products.jsp?page=" + i + "\">" + (i + 1) + "</a></a></li>\n";

    }
    document.getElementById("gallery").innerHTML = "";
    for (var i = page * 12, j = 0; i < obj.length && j < 12; i++, j++) {
        document.getElementById("gallery").innerHTML += "<li>\n" +
            "        <div class=\"am-gallery-item\">\n" +
            "            <a onclick=getProducts(" + obj[i].id + ") class=\"\">\n" +
            "              <img src=\"http://s.amazeui.org/media/i/demos/bing-1.jpg\"/>\n" +
            "                <h3 class=\"am-gallery-title\">" + obj[i].restaurantName + "</h3>\n" +
            "                <div class=\"am-gallery-desc\">" + obj[i].restaurantType + "</div>\n" +
            "            </a>\n" +
            "        </div>\n" +
            "      </li"
    }
}

function getProducts(id) {
    $.ajax(
        {
            type: 'GET',
            url: '/member/getProducts?id=' + id,
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    window.location.href = '/error.jsp'
                } else {
                    window.location.href = '/member/addOrder.jsp?restaurantId=' + id;
                }
            }
        }
    )
}

var orderProducts = [];

function loadAllProducts(obj) {
    var products = obj.products;
    var orders = obj.orders;
    document.getElementById("product").innerHTML = "";
    for (var i = 0; i < products.length; i++) {
        document.getElementById("product").innerHTML += "<dl pid=" + products[i].id + ">\n" +
            "<p>\n" +
            "<span class=\"pricedetail\">商品名称:" + products[i].productName + "</span>\n" +
            "<br>\n" +
            "<span class=\"pricedetail\">\n价格:<strong>" + products[i].price + "</strong>\n</span>\n" +
            "<br>\n" +
            "<span class=\"pricedetail\">剩余:<strong>" + products[i].num + "</strong> 件</span>\n" +
            "<br>\n" +
            "</p>\n" +
            "<dd>\n" +
            "<a class=\"am-btn am-btn-default\" onclick=\"pushProduct(" + products[i].id + "," + products[i].price + ")\">添加</a>\n" +
            "</dd>\n" +
            "</dl>"
    }
    document.getElementById("order").innerHTML = "";
    for (var i = 0; i < orders.length; i++) {
        document.getElementById("order").innerHTML += "<section class=\"am-panel am-panel-default\">\n" +
            "    <header class=\"am-panel-hd\">\n" +
            "        <h3 class=\"am-panel-title\">" + orders[i].orderName + "</h3>\n" +
            "    </header>\n" +
            "    <div class=\"am-panel-bd\" id="+orders[i].id+">\n";
        document.getElementById("order").innerHTML +=    "    </div>\n" +
            "</section>"
        var orderProducts = JSON.parse(orders[i].pidList);
        document.getElementById(orders[i].id).innerHTML += "<table class=\"am-table\">\n" +
            "    <thead>\n" +
            "        <tr>\n" +
            "            <th>单品名称</th>\n" +
            "            <th>单品数量</th>\n" +
            "        </tr>\n" +
            "    </thead>\n" + "    <tbody id="+("body"+orders[i].id.toString()).toString()+">";
        document.getElementById("order").innerHTML += "    </tbody>\n" +
            "</table>";
        document.getElementById("order").innerHTML += "<button type=\"button\" class=\"am-btn am-btn-default\" onclick=addRestaurantOrder("+orders[i].id+")>订购</button>";
        for(var j = 0;j < orderProducts.length;j++){
            document.getElementById(("body"+orders[i].id.toString().toString())).innerHTML += "<tr>\n" +
                "            <td>"+orderProducts[j].productName+"</td>\n" +
                "            <td>"+orderProducts[j].num+"</td>\n" +
                "        </tr>"
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

function addSingleOrder(restaurantId) {
    var price = document.getElementById("price").value;
    var para = {price: price, pidList: JSON.stringify(orderProducts), restaurantId: restaurantId};
    $.ajax(
        {
            type: 'POST',
            url: '/member/addSingleOrder',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(para),
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('订购失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('订购成功,前往支付');
                    window.location.href = '/member/pay.jsp'
                }
            }
        }
    )
}
function addRestaurantOrder(orderId) {
    $.ajax(
        {
            type: 'GET',
            url: '/member/addRestaurantOrder?orderId='+orderId,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('订购失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('订购成功,前往支付');
                    window.location.href = '/member/pay.jsp'
                }
            }
        }
    )
}
function payOrder(orderId) {
    $.ajax(
        {
            type: 'GET',
            url: '/member/pay?orderId=' + orderId,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('支付失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('支付完成');
                    window.location.href = '/member/mainPage.jsp'
                }
            }
        }
    )
}

function loadAddress() {
    document.getElementById("address").innerHTML = "";
    for (var i = 0; i < addresses.length; i++) {
        if (addresses[i].id !== mainAddressId)
            document.getElementById("address").innerHTML += '<option>' + addresses[i].addressName + '</option>';
        else
            document.getElementById("address").innerHTML += '<option style="color:red">' + addresses[i].addressName + '</option>'
    }
}

function mainAddress() {
    var selectedValues = [];
    $("#address :selected").each(function () {
        selectedValues.push($(this).val());
    });
    if (selectedValues.length !== 1)
        return;

    $.ajax(
        {
            type: 'GET',
            url: '/member/mainAddress?addressName=' + selectedValues[0],
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('修改失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('修改成功');
                    mainAddressId = data.addressId;
                    loadAddress();
                }
            }
        }
    )
}

function payFailed(orderId) {
    $.ajax(
        {
            type: 'GET',
            url: '/member/payFailed?orderId=' + orderId,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('支付失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('订单被取消');
                    window.location.href = '/member/mainPage.jsp'
                }
            }
        }
    )
}
var unDeliveredOrders;
function getUndeliveredOrder() {
    $.ajax(
        {
            type: 'GET',
            url: '/member/getUndeliveredOrder',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('获取失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    window.location.href = '/member/unDeliveredOrders.jsp'
                }
            }
        }
    )
}
function loadOrders() {
    document.getElementById("order").innerHTML = "";
    for (var i = 0; i < unDeliveredOrders.length; i++) {
        document.getElementById("order").innerHTML += "<section class=\"am-panel am-panel-default am-panel-secondary\">\n" +
            "    <header class=\"am-panel-hd\">\n" +
            "        <h3 class=\"am-panel-title\">套餐名称:" + unDeliveredOrders[i].orderName + "</h3>\n" +
            "<h3 class=\"am-panel-title\">订购日期:"+unDeliveredOrders[i].endDate+"</h3>"+
            "<h3 class=\"am-panel-title\">套餐金额:"+unDeliveredOrders[i].price+" 元</h3>"+
            "    </header>\n" +
            "    <div class=\"am-panel-bd\" id="+unDeliveredOrders[i].id+">\n";
        document.getElementById("order").innerHTML +=    "    </div>\n" +
            "</section>"
        var orderProducts = JSON.parse(unDeliveredOrders[i].pidList);
        document.getElementById(unDeliveredOrders[i].id).innerHTML += "<table class=\"am-table\">\n" +
            "    <thead>\n" +
            "        <tr>\n" +
            "            <th>单品名称</th>\n" +
            "            <th>单品数量</th>\n" +
            "        </tr>\n" +
            "    </thead>\n" + "    <tbody id="+("body"+unDeliveredOrders[i].id.toString()).toString()+">";
        document.getElementById("order").innerHTML += "    </tbody>\n" +
            "</table>";
        document.getElementById("order").innerHTML += "<button type=\"button\" class=\"am-btn am-btn-default\" onclick=cancelOrder("+unDeliveredOrders[i].id+")>退订</button>";
        document.getElementById("order").innerHTML += "<button type=\"button\" class=\"am-btn am-btn-default\" onclick=acceptOrder("+unDeliveredOrders[i].id+")>确认收货</button>";
        for(var j = 0;j < orderProducts.length;j++){
            document.getElementById(("body"+unDeliveredOrders[i].id.toString().toString())).innerHTML += "<tr>\n" +
                "            <td>"+orderProducts[j].productName+"</td>\n" +
                "            <td>"+orderProducts[j].num+"</td>\n" +
                "        </tr>"
        }
    }
}

function acceptOrder(orderId) {
    $.ajax(
        {
            type: 'GET',
            url: '/member/acceptOrder?orderId=' + orderId,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('接受失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('收货完成');
                    window.location.href = '/member/mainPage.jsp'
                }
            }
        }
    )
}

function cancelOrder(orderId) {
    $.ajax(
        {
            type: 'GET',
            url: '/member/cancelOrder?orderId=' + orderId,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('取消失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('订单被取消,收到退款 '+data.cancelPrice+' 元');
                    window.location.href = '/member/mainPage.jsp'
                }
            }
        }
    )
}
function showStatMain(status,orderDate,price,restaurantType) {
    var para = {status:status,orderDate:orderDate,price:price,restaurantType:restaurantType};
    $.ajax(
        {
            type: 'POST',
            url: '/member/getStat',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(para),
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('信息获取失败');
                    window.location.href = '/member/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    window.location.href = '/member/statMain.jsp?status='+status+'&orderDate='+orderDate+'&price='+price+'&restaurantType='+restaurantType
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