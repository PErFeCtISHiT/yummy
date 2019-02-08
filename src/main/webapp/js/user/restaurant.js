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
    for (var i=0; i<radio.length; i++) {
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
            success:function (data) {
                if(data.message === 'failed'){
                    alert('修改失败');
                    window.location.href='/restaurant/mainPage.jsp'
                }else if(data.message === 'unauthorized'){
                    window.location.href='/error.jsp'
                }
                else {
                    alert('修改成功,等待审批');
                    window.location.href='/restaurant/mainPage.jsp'
                }
            }
        }
    )
}