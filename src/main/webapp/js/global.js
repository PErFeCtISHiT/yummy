function closeWindow() {
    if (navigator.userAgent.indexOf("MSIE") > 0) {//close IE
        if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
            window.opener = null;
            window.close();
        } else {
            window.open('', '_top');
            window.top.close();
        }
    }
    else if (navigator.userAgent.indexOf("Firefox") > 0) {//close firefox
        window.location.href = 'about:blank ';
    } else {//close chrome;It is effective when it is only one.
        window.opener = null;
        window.open('', '_self');
        window.close();
    }

}
function logout() {
    $.ajax(
        {
            type: 'POST',
            url: '/user/logout',
            contentType: "application/json; charset=utf-8",
            success:function () {
                window.location.href='/login.jsp'
            }
        }
    )
}
function mainPage() {
    window.location.href='/login.jsp'
}