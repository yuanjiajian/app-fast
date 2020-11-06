var data = loadData();

var test = $('.lyear-dropdown-tree').lyearDropdownTree({
    data: data,
    multiSelect: false,
    jsonStr: ',',
    selectedData: [$('#parentId').attr('data-parentId')],
    relationParent: true,
    relationChildren: true,

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
    var data = {
        id: id,
        parentId: test.getSelectedID() ? test.getSelectedID() : 0,
        name: $('#name').val(),
        url: $('#url').val(),
        type: $("input[name='type']:checked").val(),
        icon: $('#icon').val(),
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
