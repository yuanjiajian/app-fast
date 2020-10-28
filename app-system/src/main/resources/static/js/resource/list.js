// tree-grid使用
var data = JSON.parse(
    '[{"id":1, "pid":0, "status":1, "name":"用户管理", "permissionValue":"open:user:manage"},' +
    '{"id":2, "pid":0, "status":1, "name":"系统管理", "permissionValue":"open:system:manage"},' +
    '{"id":3, "pid":1, "status":1, "name":"新增用户", "permissionValue":"open:user:add"},' +
    '{"id":4, "pid":1, "status":1, "name":"修改用户", "permissionValue":"open:user:edit"},' +
    '{"id":5, "pid":1, "status":0, "name":"删除用户", "permissionValue":"open:user:del"},' +
    '{"id":6, "pid":2, "status":1, "name":"系统配置管理", "permissionValue":"open:systemconfig:manage"},' +
    '{"id":7, "pid":6, "status":1, "name":"新增配置", "permissionValue":"open:systemconfig:add"},' +
    '{"id":8, "pid":6, "status":1, "name":"修改配置", "permissionValue":"open:systemconfig:edit"},' +
    '{"id":9, "pid":6, "status":0, "name":"删除配置", "permissionValue":"open:systemconfig:del"},' +
    '{"id":10, "pid":2,"status":1, "name":"系统日志管理", "permissionValue":"open:log:manage"},' +
    '{"id":11, "pid":10,"status":1, "name":"新增日志", "permissionValue":"open:log:add"},' +
    '{"id":12, "pid":10,"status":1, "name":"修改日志", "permissionValue":"open:log:edit"},' +
    '{"id":13, "pid":10,"status":0, "name":"删除日志", "permissionValue":"open:log:del"}]');

var $treeTable = $('.tree-table');
$treeTable.bootstrapTable({
    data: data,
    idField: 'id',
    uniqueId: 'id',
    dataType: 'jsonp',
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
            field: 'status',
            title: '状态',
            sortable: true,
            /*
             * 可以选择采用开关来处理状态显示
             * 或者采用上个示例的处理方式
             */
            formatter: function (value, row, index) {
                if (value == 0) {
                    is_checked = '';
                } else if (value == 1) {
                    is_checked = 'checked="checked"';
                }
                result = '<label class="lyear-switch switch-primary lyear-status"><input type="checkbox" ' + is_checked + '><span  onClick="updateStatus(' + row.id + ', ' + value + ')"></span></label>';
                return result;
            },
        },
        {
            field: 'permissionValue',
            title: '权限值'
        },
        {
            field: 'operate',
            title: '操作',
            align: 'center',
            events: {
                'click .role-add': function (e, value, row, index) {
                    add(row.id);
                },
                'click .role-delete': function (e, value, row, index) {
                    del(row.id);
                },
                'click .role-edit': function (e, value, row, index) {
                    update(row.id);
                }
            },
            formatter: operateFormatter
        }
    ],

    treeShowField: 'name',
    parentIdField: 'pid',

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
        '<a type="button" class="role-add btn btn-xs btn-default m-r-5" title="编辑" data-toggle="tooltip"><i class="mdi mdi-plus"></i></a>',
        '<a type="button" class="role-edit btn btn-xs btn-default m-r-5" title="修改" data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>',
        '<a type="button" class="role-delete btn btn-xs btn-default" title="删除" data-toggle="tooltip"><i class="mdi mdi-delete"></i></a>'
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
    alert("add 方法 , id = " + id);
}

function del(id) {
    alert("del 方法 , id = " + id);
}

function update(id) {
    alert("update 方法 , id = " + id);
}

function updateStatus(id, state) {
    var newstate = (state == 1) ? 0 : 1; // 发送参数值跟当前参数值相反
    $.ajax({
        type: "get",
        url: "http://www.bixiaguangnian.com/index/test/testGridJson",
        data: {id: id, state: newstate},
        dataType: 'jsonp',
        success: function (data, status) {
            if (data.code == '200') {
                $treeTable.bootstrapTable('updateCellById', {id: id, field: 'status', value: newstate});
            } else {
                alert(data.msg);
                $treeTable.bootstrapTable('updateCellById', {id: id, field: 'status', value: state}); // 因开关点击后样式是变的，失败也重置下
            }
        },
        error: function () {
            alert('修改失败，请稍后再试');
        }
    });
}

function test() {
    var selRows = $treeTable.bootstrapTable("getSelections");
    if (selRows.length == 0) {
        alert("请至少选择一行");
        return;
    }
    console.log(selRows);

    var postData = "";
    $.each(selRows, function (i) {
        postData += this.id;
        if (i < selRows.length - 1) {
            postData += "， ";
        }
    });
    alert("你选中行的 id 为：" + postData);
}