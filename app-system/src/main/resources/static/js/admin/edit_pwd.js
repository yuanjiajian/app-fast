function submit() {
    var newpwd = $("input[name='newpwd']").val()
    var confirmpwd = $("input[name='confirmpwd']").val()
    if (newpwd != confirmpwd) {
        lightyear.notify("两次密码不正确", 'danger', 100);
        return false;
    }
    $.ajax({
        type: 'post',
        url: ctxPath + 'admin/update_password',
        data: {password: newpwd},
        dataType: 'json',
        success: function (response) {
            var {code, message} = response
            if (code == '0') {
                lightyear.notify(message, 'success', 100);
            } else {
                lightyear.notify(message, 'danger', 100);
            }
        }
    })

}