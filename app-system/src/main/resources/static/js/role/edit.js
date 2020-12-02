var data = loadData();
var ids = [];
$("input[name='resourceList']").each(function (index, item) {
    ids.push($(item).val());
});

var test = $('.lyear-dropdown-tree').lyearDropdownTree({
    data: data,
    multiSelect: true,
    jsonStr: ',',
    selectedData: ids,
    relationParent: false,
    relationChildren: false,

    checkHandler: function (el) {
        //console.log("checked ", el);
    },
});

function loadData() {
    var list;
    $.ajax({
        type: 'get',
        async: false,
        url: ctxPath + 'resource/selectAll',
        dataType: 'json',
        success: function (response) {
            var {data} = response
            list = data
        }
    })
    return toTree(list);
}

function toTree(data) {
    let result = []
    if (!Array.isArray(data)) {
        return result
    }
    data.forEach(item => {
        delete item.children;
    });
    let map = {};
    data.forEach(item => {
        map[item.id] = item;
    });
    data.forEach(item => {
        let parent = map[item.parentId];
        if (parent) {
            (parent.children || (parent.children = [])).push(item);
        } else {
            result.push(item);
        }
    });
    return result;
}

function submit(id) {
    var resourceList = []
    var idList = test.getSelectedID().split(',')
    for (var i = 0; i < idList.length; i++) {
        var rid = idList[i]
        if (rid) {
            var resource = {
                id: rid
            }
            resourceList.push(resource)
        }
    }
    var data = {
        id: id,
        name: $('#name').val(),
        sort: $('#sort').val(),
        status: $("input[name='status']:checked").val(),
        resourceList: resourceList
    }

    if (id) {
        $.ajax({
            type: 'post',
            url: ctxPath + 'role/update',
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
            url: ctxPath + 'role/add',
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
