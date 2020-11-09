function submit() {

    var data = {
        projectPath:$('#projectPath').val(),
        packageName:$('#packageName').val(),
        dbUrl:$('#dbUrl').val(),
        dbUsername:$('#dbUsername').val(),
        dbPassword:$('#dbPassword').val(),
        tables:$('#tables').val()
    }

    $.ajax({
        type: 'post',
        url: ctxPath + 'generator/create',
        data: data,
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

    return false;
}
