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
            document.getElementById("address").innerHTML += '<option>' + address.addressName + '</option>'
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
                    alert('添加失败');
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
            document.getElementById("product").innerHTML += "<dl pid=" + obj[i].id + ">\n" +
                "<p>\n" +
                "<span class=\"pricedetail\">商品名称:" + obj[i].productName + "</span>\n" +
                "<br>\n" +
                "<span class=\"pricedetail\">\n价格:<strong>" + obj[i].price + "</strong>\n</span>\n" +
                "<br>\n" +
                "<span class=\"pricedetail\">剩余:<strong>" + obj[i].num + "</strong> 件</span>\n" +
                "<br>\n" +
                "</p>\n" +
                "<dd>\n" +
                "<a class=\"am-btn am-btn-default\" onclick=\"pushProduct(" + obj[i].id + "," + obj[i].price + ")\">添加至套餐</a>\n" +
                "</dd>\n" +
                "</dl>"
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
                    alert('添加失败');
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