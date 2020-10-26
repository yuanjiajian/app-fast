$('#table').bootstrapTable({
    classes: 'table table-bordered table-hover table-striped',
    url: ctxPath + 'admin/list',
    method: 'get',
    dataType: 'json',        // 因为本示例中是跨域的调用,所以涉及到ajax都采用jsonp,
    uniqueId: 'id',
    idField: 'id',             // 每行的唯一标识字段
    toolbar: '#toolbar',       // 工具按钮容器
    //clickToSelect: true,     // 是否启用点击选中行
    showColumns: true,         // 是否显示所有的列
    showRefresh: true,         // 是否显示刷新按钮

    //showToggle: true,        // 是否显示详细视图和列表视图的切换按钮(clickToSelect同时设置为true时点击会报错)

    pagination: true,                    // 是否显示分页
    sortOrder: "asc",                    // 排序方式
    queryParams: function (params) {
        var temp = {
            limit: params.limit,         // 每页数据量
            offset: params.offset,       // sql语句起始索引
            page: (params.offset / params.limit) + 1,
            sort: params.sort,           // 排序的列名
            sortOrder: params.order      // 排序方式'asc' 'desc'
        };
        return temp;
    },                                   // 传递参数
    responseHandler: function (response) {
        var {data} = response
        var temp = {
            total: data.total,
            rows: data.records
        }
        return temp;
    },
    sidePagination: "server",            // 分页方式：client客户端分页，server服务端分页
    pageNumber: 1,                       // 初始化加载第一页，默认第一页
    pageSize: 10,                        // 每页的记录行数
    pageList: [10, 25, 50, 100],         // 可供选择的每页的行数
    //search: true,                      // 是否显示表格搜索，此搜索是客户端搜索

    //showExport: true,        // 是否显示导出按钮, 导出功能需要导出插件支持(tableexport.min.js)
    //exportDataType: "basic", // 导出数据类型, 'basic':当前页, 'all':所有数据, 'selected':选中的数据

    columns: [{
        checkbox: true    // 是否显示复选框
    }, {
        field: 'id',
        title: 'ID'
    }, {
        field: 'username',
        title: '用户名'
    }, {
        field: 'name',
        title: '名称',
    }, {
        field: 'avatar',
        title: '头像',
        formatter: function (value, row, index) {
            return '<img class="img-avatar" src="' + value + '"/>';
        }
    }, {
        field: 'sort',
        title: '排序',
        sortable: true    // 是否排序
    }, {
        field: 'status',
        title: '状态',
        editable: {
            type: 'select',
            title: "状态",
            pk: 1,
            source: [
                {value: 1, text: '禁用'},
                {value: 0, text: '启用'}
            ],
            noeditFormatter: function (value, row, index) {
                var result;
                if (value == '1' || value == '禁用') {
                    result = {filed: "status", value: "禁用", class: "label label-danger"};
                } else if (value == '0' || value == '启用') {
                    result = {filed: "status", value: "启用", class: "label label-success"};
                }

                return result; // 这里对bootstrap-table-editable.min.js做了一些修改，让其能接收class
            },
            // 可以按列分开做保存，也可以用后面的onEditableSave
            url: ctxPath + 'admin/update_status',
            ajaxOptions: {
                type: 'post',
                dataType: 'json'
            }, // 默认是post方式提交,这里因为跨域,改成get
            success: function (response, newValue) {
                var {code, message, data} = response
                if (code == '0') {
                    // 这里的状态显示有自定义样式区分，做单元格更新
                    for (var i = 0; i < data.length; i++)
                        $('#table').bootstrapTable('updateCellById', {
                            id: data[i].id,
                            field: 'status',
                            value: newValue
                        });
                } else {
                    return message;
                }
            },
            highlight: false,
            params: function (params) {
                var temp = {
                    ids: params.pk,
                    status: params.value
                }
                return temp;
            }
        }
    }, {
        field: 'createTime',
        title: '创建时间',
        formatter: function (value, row, index) {
            return value.replace('T', ' ');
        }
    }, {
        field: 'updateTime',
        title: '修改时间',
        formatter: function (value, row, index) {
            return value.replace('T', ' ');
        }
    }, {
        field: 'operate',
        title: '操作',
        formatter: btnGroup,  // 自定义方法
        events: {
            'click .edit-btn': function (event, value, row, index) {
                edit(row.id);
            },
            'click .del-btn': function (event, value, row, index) {
                del(row.id);
            },
            'click .show-btn': function (event, value, row, index) {
                show(row);
            }
        }
    }],
    /*
    onEditableSave: function (field, row, oldValue, $el) {
        $.ajax({
            type: "get",
            url: "http://www.bixiaguangnian.com/index/test/testEditTwo",
            data: row,
            dataType: 'jsonp',
            success: function (data, status) {
                if (data.code == '200') {
                    // 这里的状态显示有自定义样式区分，做单行的更新
                    $('.example-table').bootstrapTable('updateRow', {index: row.id, row: row});
                } else {
                    alert(data.msg);
                }
            },
            error: function () {
                alert('修改失败，请稍后再试');
            }
        });
    }
    */
    onLoadSuccess: function (data) {
        $("[data-toggle='tooltip']").tooltip();
    }
});

// 操作按钮
function btnGroup() {
    let html =
        '<a href="#!" class="btn btn-xs btn-default m-r-5 edit-btn" title="编辑" data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>' +
        '<a href="#!" class="btn btn-xs btn-default m-r-5 show-btn" title="查看" data-toggle="tooltip"><i class="mdi mdi-eye"></i></a>' +
        '<a href="#!" class="btn btn-xs btn-default del-btn" title="删除" data-toggle="tooltip"><i class="mdi mdi-window-close"></i></a>';
    return html;
}

// 操作方法 - 编辑
function edit(id) {
    if (id) {
        window.location.href = ctxPath + 'admin/edit.html?id=' + id
    } else {
        window.location.href = ctxPath + 'admin/edit.html'
    }
}

// 操作方法 - 删除
function del(ids) {
    if (ids) {
        $.ajax({
            type: 'post',
            url: ctxPath + 'admin/delete',
            data: {ids: ids},
            dataType: 'json',
            success: function (response) {
                var {code, message} = response
                if (code == '0') {
                    $('#table').bootstrapTable('refresh');
                    lightyear.notify(message, 'success', 100);
                } else {
                    lightyear.notify(message, 'danger', 100);
                }
            }
        })
    }

}

// 操作方法 - 查看
function show(row) {
    if (row) {
        $.confirm({
            columnClass: 'col-md-6 col-md-offset-3',
            title: '',
            content: '<div>' +
                '<div class="form-group "><label for="username">用户名</label><div>' + row.username + '</div></div>' +
                '<div class="form-group "><label for="name">名称</label><div>' + row.name + '</div></div>' +
                '<div class="form-group "><label>头像</label><div><img class="img-avatar" src="' + row.avatar + '" alt="图片一"></div></div>' +
                '<div class="form-group "><label for="sort">排序</label><div>' + row.sort + '</div></div>' +
                '<div class="form-group "><label for="status">状态</label><div>' + (row.status == 0 ? "启用" : "禁用") + '</div></div>' +
                '<div class="form-group "><label for="createTime">创建时间</label><div>' + (row.createTime.replace('T', ' ')) + '</div></div>' +
                '<div class="form-group "><label for="updateTime">更新时间</label><div>' + (row.updateTime.replace('T', ' ')) + '</div></div>' +
                '</div>',
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

}

/*
编辑状态
status:(0：启用,1：禁用)
 */
function edit_status(status) {
    var ids = $('#table').bootstrapTable('getAllSelections').map(item => item.id).join(',')
    $.ajax({
        type: 'post',
        url: ctxPath + 'admin/update_status',
        data: {ids: ids, status: status},
        dataType: 'json',
        success: function (response) {
            var {code, message, data} = response
            if (code == '0') {
                // 这里的状态显示有自定义样式区分，做单元格更新
                for (var i = 0; i < data.length; i++)
                    $('#table').bootstrapTable('updateCellById', {
                        id: data[i].id,
                        field: 'status',
                        value: data[i].status
                    });
            } else {
                lightyear.notify(message, 'danger', 100);
            }
        }
    })
}

/*
全部删除
 */
function delAll() {
    var ids = $('#table').bootstrapTable('getAllSelections').map(item => item.id).join(',')
    del(ids)
}