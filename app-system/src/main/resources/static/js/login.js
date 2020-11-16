function login() {
    var username = $('input#username').val()
    var password = $('input#password').val()
    var picCode = $('input#picCode').val()
    $.ajax({
        type: 'post',
        url: ctxPath + 'login',
        data: {username: username, password: password, picCode: picCode},
        dataType: 'json',
        success: function (response) {
            var {code, message} = response
            if (code == '0') {
                lightyear.notify(message, 'success', 100);
                window.location.href="index.html"
            } else {
                lightyear.notify(message, 'danger', 100);
            }
        }
    })
}