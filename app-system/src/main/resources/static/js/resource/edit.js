
function submit(id) {
    var data = {
        id: id,
        parentId:$('#parentId').val(),
        name: $('#name').val(),
        url:$('#url').val(),
        type:$('#type').val(),
        icon:$('#icon').val(),
        sort: $('#sort').val(),
        status: $("input[name='status']:checked").val()
    }

    if (id) {
        $.ajax({
            type: 'post',
            url: ctxPath + 'resource/update',
            data: data,
            dataType: 'json',
            success: function (response) {
                var {code, message, data} = response
                if (code == '0') {
                    lightyear.notify(message, 'success', 100);
                } else {
                    lightyear.notify(message, 'danger', 100);
                }
            }
        })
    } else {
        $.ajax({
            type: 'post',
            url: ctxPath + 'resource/add',
            data: data,
            dataType: 'json',
            success: function (response) {
                var {code, message, data} = response
                if (code == '0') {
                    lightyear.notify(message, 'success', 100);
                } else {
                    lightyear.notify(message, 'danger', 100);
                }
            }
        })
    }
    return false;
}
