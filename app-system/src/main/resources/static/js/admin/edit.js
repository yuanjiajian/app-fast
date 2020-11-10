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
                var picList = $this.closest('ul.lyear-uploads-pic')
                var item = picList.find('li');
                if (item.length == 1) {
                    var itemHtml = '<li class="col-xs-4 col-sm-3 col-md-2">\n' +
                        '  <figure>\n' +
                        '    <img src="' + data + '" alt="图片一" />\n' +
                        '    <figcaption>\n' +
                        '      <a onclick="eyePic(this)" class="btn btn-round btn-square btn-primary" href="#!"\n' +
                        '        ><i class="mdi mdi-eye"></i\n' +
                        '      ></a>\n' +
                        '      <a onclick="deletePic(this)" class="btn btn-round btn-square btn-danger" href="#!"\n' +
                        '        ><i class="mdi mdi-delete"></i\n' +
                        '      ></a>\n' +
                        '    </figcaption>\n' +
                        '  </figure>\n' +
                        '</li>'
                    picList.prepend(itemHtml)
                } else if (item.length == 2) {
                    item.eq(0).find('img').attr('src', data)
                }

            } else {
                lightyear.notify(message, 'danger', 100);
            }
        },
    });
})

/*
查看图片
 */
function eyePic(object) {
    var src = $(object).closest('li').find('img').attr('src')
    $.confirm({
        columnClass: 'col-md-6 col-md-offset-3',
        title: '',
        content: '<div><img width="100%" src="' + src + '"></div>',
        type: 'green',
        buttons: {
            omg: {
                text: '确定',
                btnClass: 'btn-green',
            },
            close: {
                text: '关闭',
            }
        }
    });
}

/*
删除图片
 */
function deletePic(object) {
    $(object).closest('li').remove()
}


function submit(id) {
    var roleList = []
    $("input[name='roleList']:checked").each(function (index, item) {
        var id = $(item).val();
        if (id) {
            var role = {
                id: $(item).val()
            }
            roleList.push(role);
        }
    });
    var data = {
        id: id,
        username: $('#username').val(),
        password: $('#password').val(),
        name: $('#name').val(),
        avatar: $('ul.lyear-uploads-pic li img').attr('src'),
        sort: $('#sort').val(),
        status: $("input[name='status']:checked").val(),
        roleList: roleList
    }
    if (id) {
        $.ajax({
            type: 'post',
            url: ctxPath + 'admin/update',
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
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
    } else {
        $.ajax({
            type: 'post',
            url: ctxPath + 'admin/add',
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
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
    return false;
}
