function checkRestaurantApply() {
    $.ajax(
        {
            type: 'GET',
            url: '/manager/getRestaurantApply',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('信息获取失败');
                    window.location.href = '/manager/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    window.location.href = '/manager/checkApply.jsp'
                }
            }
        }
    )
}

var applies;

function loadApplies() {
    document.getElementById("apply").innerHTML = "";
    for (var i = 0; i < applies.length; i++) {
        document.getElementById("apply").innerHTML += "<section class=\"am-panel am-panel-default am-panel-secondary\">\n" +
            "    <header class=\"am-panel-hd\">\n" +
            "        <h3 class=\"am-panel-title\">餐厅名称:" + applies[i].restaurantName + "</h3>\n" +
            "    </header>\n" +
            "    <div class=\"am-panel-bd\" id=" + applies[i].id + ">\n" +
            "<span>申请日期:" + applies[i].applyDate + "</span><br>" +
            "<span>餐厅类型:" + applies[i].restaurantType + "</span><br>" +
            "<span>地址名称:" + applies[i].addressName + "</span><br>" +
            "<a class=\"am-btn am-btn-default\" onclick=\"approveApply(" + applies[i].id + ")\">同意修改</a>\n" +
            "<a class=\"am-btn am-btn-default\" onclick=\"cancelApply(" + applies[i].id + ")\">拒绝修改</a>\n" +
            "    </div>\n" +
            "</section>"
    }
}
function approveApply(id) {
    $.ajax(
        {
            type: 'GET',
            url: '/manager/approveApply?id='+id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('审批失败');
                    window.location.href = '/manager/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('审批成功')
                    window.location.href = '/manager/mainPage.jsp'
                }
            }
        }
    )
}
function cancelApply(id) {
    $.ajax(
        {
            type: 'GET',
            url: '/manager/cancelApply?id='+id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('审批失败');
                    window.location.href = '/manager/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('取消成功');
                    window.location.href = '/manager/mainPage.jsp'
                }
            }
        }
    )
}
function clearAccount() {
    $.ajax(
        {
            type: 'GET',
            url: '/manager/getRestaurantAccount',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('信息获取失败');
                    window.location.href = '/manager/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    window.location.href = '/manager/clearAccount.jsp'
                }
            }
        }
    )
}
var accounts;
function loadAccounts() {
    document.getElementById("account").innerHTML = "";
    for (var i = 0; i < accounts.length; i++) {
        document.getElementById("account").innerHTML += "<section class=\"am-panel am-panel-default am-panel-secondary\">\n" +
            "    <header class=\"am-panel-hd\">\n" +
            "        <h3 class=\"am-panel-title\">餐厅名称:" + accounts[i].restaurantName + "</h3>\n" +
            "    </header>\n" +
            "    <div class=\"am-panel-bd\" id=" + accounts[i].id + ">\n" +
            "<span>账单产生日期:" + accounts[i].accountDate + "</span><br>" +
            "<span>账单金额:" + accounts[i].account + "元</span><br>" +
            "<a class=\"am-btn am-btn-default\" onclick=\"approveAccount(" + accounts[i].id + ")\">结算</a>\n" +
            "    </div>\n" +
            "</section>"
    }
}
function approveAccount(id) {
    $.ajax(
        {
            type: 'GET',
            url: '/manager/approveAccount?id='+id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('结算失败');
                    window.location.href = '/manager/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    alert('结算成功');
                    window.location.href = '/manager/mainPage.jsp'
                }
            }
        }
    )
}
function showStat(para) {
    $.ajax(
        {
            type: 'GET',
            url: '/manager/get'+para+'Stat',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.message === 'failed') {
                    alert('信息获取失败');
                    window.location.href = '/manager/mainPage.jsp'
                } else if (data.message === 'unauthorized') {
                    window.location.href = '/error.jsp'
                }
                else {
                    window.location.href = '/manager/show'+para+'Stat.jsp'
                }
            }
        }
    )
}