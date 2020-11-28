// tree-grid使用
var data = loadData();

function loadData() {
    var list;
    $.ajax({
        type: 'get',
        async: false,
        url: ctxPath + 'resource/selectAll',
        dataType: 'json',
        success: function (response) {
            var {code, message, data} = response
            if (code == '0') {
                list = data
            } else {
                $.confirm({
                    title: '',
                    content: message,
                    type: 'red',
                    typeAnimated: true,
                    buttons: {
                        tryAgain: {
                            isHidden: true,
                        },
                        close: {
                            isHidden: true,
                        }
                    }
                });
            }

        }
    })
    return list;
}

var $treeTable = $('.tree-table');
$treeTable.bootstrapTable({
    data: data,
    idField: 'id',
    uniqueId: 'id',
    dataType: 'json',
    toolbar: '#toolbar2',
    columns: [
        {
            field: 'check',
            checkbox: true
        },
        {
            field: 'name',
            title: '名称'
        },
        {
            field: 'url',
            title: '地址'
        },
        {
            field: 'type',
            title: '类型',
            formatter: function (value, row, index) {
                if (value == 0) {
                    return '目录';
                } else if (value == 1) {
                    return '菜单';
                } else if (value == 2) {
                    return '按钮';
                }
            }
        },
        {
            field: 'icon',
            title: '图标',
            formatter: function (value, row, index) {
                return '<i class="mdi ' + value + '"></i>';
            }
        },
        {
            field: 'sort',
            title: '排序'
        },
        {
            field: 'status',
            title: '状态',
            sortable: true,
            /*
             * 可以选择采用开关来处理状态显示
             * 或者采用上个示例的处理方式
             */
            formatter: function (value, row, index) {
                if (value == 1) {
                    is_checked = '';
                } else if (value == 0) {
                    is_checked = 'checked="checked"';
                }
                result = '<label class="lyear-switch switch-primary lyear-status"><input type="checkbox" ' + is_checked + '><span  onClick="updateStatus(' + row.id + ', ' + value + ')"></span></label>';
                return result;
            },
        },
        {
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
        },
        {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: {
                'click .add': function (e, value, row, index) {
                    add(row.id);
                },
                'click .delete': function (e, value, row, index) {
                    del(row.id);
                },
                'click .edit': function (e, value, row, index) {
                    update(row.id);
                }
            },
            formatter: operateFormatter
        }
    ],

    treeShowField: 'name',
    parentIdField: 'parentId',

    onResetView: function (data) {
        $treeTable.treegrid({
            initialState: 'collapsed', // 所有节点都折叠
            treeColumn: 1,
            //expanderExpandedClass: 'mdi mdi-folder-open',  // 可自定义图标样式
            //expanderCollapsedClass: 'mdi mdi-folder',
            onChange: function () {
                $treeTable.bootstrapTable('resetWidth');
            }
        });

        // 只展开树形的第一集节点
        $treeTable.treegrid('getRootNodes').treegrid('expand');
    },
    onCheck: function (row) {
        var datas = $treeTable.bootstrapTable('getData');

        selectChilds(datas, row, 'id', 'pid', true); // 选择子类

        selectParentChecked(datas, row, 'id', 'pid'); // 选择父类

        $treeTable.bootstrapTable('load', datas);
    },

    onUncheck: function (row) {
        var datas = $treeTable.bootstrapTable('getData');
        selectChilds(datas, row, 'id', 'pid', false);
        $treeTable.bootstrapTable('load', datas);
    },
});

// 操作按钮
function operateFormatter(value, row, index) {
    return [
        '<a type="button" class="add btn btn-xs btn-default m-r-5" title="编辑" data-toggle="tooltip"><i class="mdi mdi-plus"></i></a>',
        '<a type="button" class="edit btn btn-xs btn-default m-r-5" title="修改" data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>',
        '<a type="button" class="delete btn btn-xs btn-default" title="删除" data-toggle="tooltip"><i class="mdi mdi-delete"></i></a>'
    ].join('');
}

/**
 * 选中父项时，同时选中子项
 * @param datas 所有的数据
 * @param row 当前数据
 * @param id id 字段名
 * @param pid 父id字段名
 */
function selectChilds(datas, row, id, pid, checked) {
    for (var i in datas) {
        if (datas[i][pid] == row[id]) {
            datas[i].check = checked;
            selectChilds(datas, datas[i], id, pid, checked);
        }
        ;
    }
}

function selectParentChecked(datas, row, id, pid) {
    for (var i in datas) {
        if (datas[i][id] == row[pid]) {
            datas[i].check = true;
            selectParentChecked(datas, datas[i], id, pid);
        }
        ;
    }
}

function add(id) {
    window.location.href = ctxPath + 'resource/edit.html?parentId=' + id;
}

function del(ids) {
    if (ids) {
        $.ajax({
            type: 'post',
            url: ctxPath + 'resource/delete',
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

function update(id) {
    window.location.href = ctxPath + 'resource/edit.html?id=' + id
}

function updateStatus(ids, state) {
    var newstate = (state == 1) ? 0 : 1; // 发送参数值跟当前参数值相反
    $.ajax({
        type: "post",
        url: ctxPath + 'resource/update_status',
        data: {ids: ids, status: newstate},
        dataType: 'json',
        success: function (response, status) {
            var {code, message, data} = response
            if (code == '0') {
                for (var i = 0; i < data.length; i++) {
                    $treeTable.bootstrapTable('updateCellById', {id: data[i].id, field: 'status', value: newstate});
                }

            } else {
                lightyear.notify(message, 'danger', 100);
                for (var i = 0; i < data.length; i++) {
                    $treeTable.bootstrapTable('updateCellById', {id: data[i].id, field: 'status', value: state}); // 因开关点击后样式是变的，失败也重置下
                }

            }
        },
        error: function () {
            alert('修改失败，请稍后再试');
        }
    });
}

