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
            url: '/manager/approveApply?id=' + id,
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
            url: '/manager/cancelApply?id=' + id,
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
            url: '/manager/approveAccount?id=' + id,
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
            url: '/manager/get' + para + 'Stat',
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
                    window.location.href = '/manager/show' + para + 'Stat.jsp'
                }
            }
        }
    )
}

var restaurants;
var members;
var yummyAccounts;
var restaurantType;
var addresses;
var memberLevel;
var convertData = function (data) {
    var res = [];
    for (var i = 0; i < data.length; i++) {
        res.push({
            name: data[i].name,
            value: data[i].value.concat(150)
        });
    }
    return res;
};

function signCharts(chartData) {
    var data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var myChart = echarts.init(document.getElementById("sign"));
    for (var i = 0; i < chartData.length; i++) {
        var dateStr = chartData[i].signDate;
        dateStr = dateStr.replace(/-/g, "/");
        var date = new Date(dateStr);
        data[date.getMonth()]++;
    }
    myChart.setOption({
        title: {
            text: '注册分布图'
        },
        tooltip: {},
        legend: {
            data: ['注册数']
        },
        xAxis: {
            name:'月份',
            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        },
        yAxis: {
            name:'注册数'
        },
        series: [{
            name: '注册数',
            type: 'bar',
            data: data
        }]
    });
}

function pieCharts(chartData, name) {
    var typeChart = echarts.init(document.getElementById("type"));
    var typeData = [];
    if (name === "类型") {
        typeData = [{name: "美食", value: chartData.美食}, {name: "饮品", value: chartData.饮品}, {
            name: "甜品",
            value: chartData.甜品
        }];
    } else {
        for (var i = 0; i < 7; i++) {
            var iStr = i.toString();
            typeData.push({
                name: i,
                value: chartData[iStr]
            });
        }
    }
    typeChart.setOption({
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}个 ({d}%)"
        },
        series: [
            {
                name: name,
                roseType: 'angle',
                type: 'pie',
                radius: '55%',
                data: typeData
            }
        ]
    });
}

function addressCharts(chartData, name) {
    var addressChart = echarts.init(document.getElementById("address"));
    addressChart.setOption({
        title: {
            text: name + '分布图',
            left: 'center',
            textStyle: {
                color: '#fff'
            }
        },
        bmap: {
            center: [118.7969, 32.0603],
            zoom: 10,
            roam: true
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}"
        },
        series: [{
            name: name+'名',
            type: 'scatter',
            coordinateSystem: 'bmap',
            data: convertData(chartData),
            symbolSize: function (val) {
                return val[2] / 10;
            },
            label: {
                normal: {
                    formatter: '{b}',
                    position: 'right',
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
            itemStyle: {
                normal: {
                    color: '#8B0000'
                }
            }
        }]
    });
}

function loadRestaurantCharts() {
    signCharts(restaurants);
    pieCharts(restaurantType, "类型");
    addressCharts(addresses, "餐厅");
}

function loadMemberCharts() {
    signCharts(members);
    pieCharts(memberLevel, "等级");
    addressCharts(addresses, "会员");
}

function loadYummyCharts() {
    var data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    var myChart = echarts.init(document.getElementById("account"));
    for (var i = 0; i < yummyAccounts.length; i++) {
        var dateStr = yummyAccounts[i].accountDate;
        dateStr = dateStr.replace(/-/g, "/");
        var date = new Date(dateStr);
        data[date.getMonth()] += yummyAccounts[i].account;
    }
    for (var i = 0; i < data.length; i++) {
        data[i] = parseFloat(data[i].toFixed(2))
    }
    myChart.setOption({
        title: {
            text: 'Yummy账务图'
        },
        tooltip: {},
        legend: {
            data: ['账务金额']
        },
        xAxis: {
            data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        },
        yAxis: {},
        series: [{
            name: '账务金额',
            type: 'bar',
            data: data
        }]
    });
}