
function memberSignUp() {
    var loginToken = document.getElementById("loginTokenInput").value;
    var password = document.getElementById("passwordInput").value;
    var token = {"loginToken":loginToken,"userPassword":password};
    $.ajax(
        {
            type: 'POST',
            url: '/member/signUp',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(token),
            dataType: "json",
            success:function (data) {
                if(data.message === 'failed'){
                    window.location.href='/error.jsp'
                }else {
                    window.location.href='/member/inactive.jsp'
                }
            }
        }
    )
}
function login() {
    var loginToken = document.getElementById("loginTokenInput").value;
    var password = document.getElementById("passwordInput").value;
    var token = {"loginToken":loginToken,"userPassword":password};
    $.ajax(
        {
            type: 'POST',
            url: '/user/login',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(token),
            dataType: "json",
            success:function (data) {
                if(data.message === 'failed'){
                    window.location.href='/error.jsp'
                }else {
                    if(data.userType === 'member')
                    window.location.href='/member/mainPage.jsp';
                    else if(data.userType === 'restaurant')
                        window.location.href='/restaurant/mainPage.jsp';
                    else if(data.userType === 'manager')
                        window.location.href='/manager/mainPage.jsp';
                    else
                        window.location.href='/error.jsp';
                }
            }
        }
    )
}
function addAddress() {
    window.location.href='/member/mainPage.jsp';
    window.open ('/member/map.jsp', 'newwindow', 'top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no')
}
function removeAddress() {
    var selectedValues = [];
    $("#address :selected").each(function() {
        selectedValues.push($(this).val());
    });
    var para = {address:selectedValues};

    $.ajax(
        {
            type: 'POST',
            url: '/member/removeAddress',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(para),
            dataType: "json",
            success:function () {
                alert('删除成功');
                window.location.href = "/member/mainPage.jsp"
            }
        }
    )
}
function modifyUser() {
    var memberName = document.getElementById("name").value;
    var telephone = document.getElementById("telephone").value;
    var para = {memberName:memberName,telephone:telephone};
    $.ajax(
        {
            type: 'POST',
            url: '/member/modifyUser',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(para),
            dataType: "json",
            success:function (data) {
                if(data.message === 'failed'){
                    alert('修改失败');
                    window.location.href='/member/mainPage.jsp'
                }else if(data.message === 'unauthorized'){
                    window.location.href='/error.jsp'
                }
                else {
                    alert('修改成功');
                    window.location.href='/member/mainPage.jsp'
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
            success:function (data) {
                if(data.message === 'failed'){
                    window.location.href='/error.jsp'
                }else {
                    window.location.href='/login.jsp'
                }
            }
        }
    )
}