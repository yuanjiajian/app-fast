function uploadAvatar(object) {
    var file = $(object).find("input[type='file']")
    file.on('click', function (e) {
        e.stopPropagation();
    });
    file.trigger('click');
}

$(document).on('change', "input[type='file']", function () {
    var $this = $(this);
    var $input = $(this)[0];
    var $len = $input.files.length;
    var formFile = new FormData();
    if ($len == 0) {
        return false;
    } else {
        var fileAccaccept = $this.attr('accaccept');
        var fileType = $input.files[0].type;
        var type = (fileType.substr(fileType.lastIndexOf("/") + 1)).toLowerCase();

        if (!type || fileAccaccept.indexOf(type) == -1) {
            lightyear.notify('您上传图片的类型不符合(.jpg|.jpeg|.gif|.png|.bmp)', 'danger', 1000);
            return false;
        }
        formFile.append("file", $input.files[0]);
    }
    var data = formFile;

    $.ajax({
        url: ctxPath + 'admin/upload_avatar',
        data: data,
        type: "POST",
        dataType: "json",
        //上传文件无需缓存
        cache: false,
        //用于对data参数进行序列化处理 这里必须false
        processData: false,
        //必须
        contentType: false,
        success: function (response) {
            var {code, message, data} = response
            if (code == '0') {
                console.log(data)
            } else {
                lightyear.notify(message, 'danger', 100);
            }
        },
    });
})