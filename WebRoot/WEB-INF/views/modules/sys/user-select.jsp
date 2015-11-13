<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<script type="text/javascript">
var $select_user_datagrid;
var $select_user_search_form;
var $select_organ_tree;
var $select_role_tree;
var selectUserIds = new Array();
$(function () {
    selectOrganTree();
    selectRoleTree();
    $.each($("#selectUser option"), function (index, option) {
        selectUserIds.push(option.value);
    });
    userDatagrid();
});

//数据表
function userDatagrid() {
    $select_user_search_form = $('#select_user_search_form').form();
    //数据列表
    $select_user_datagrid = $('#select_user_datagrid').datagrid({
        url: "${ctx}/sys/user/combogridSelectUser",
        fit: true,
        pagination: false,//底部分页
        rownumbers: true,//显示行数
        fitColumns: false,//自适应列宽
        striped: true,//显示条纹
        remoteSort: false,//是否通过远程服务器对数据排序
        sortName: 'id',//默认排序字段
        sortOrder: 'asc',//默认排序方式 'desc' 'asc'
        idField: 'id',
        frozen: true,
        collapsible: true,
        frozenColumns: [
            [
                {field: 'ck', checkbox: true},
                {field: 'name', title: '姓名', width: 80, sortable: true}
            ]
        ],
        columns: [
            [
                {field: 'id', title: '主键', hidden: true, sortable: true, width: 10} ,
                {field: 'loginName', title: '登录名', width: 80},
                {field: 'sexView', title: '性别', width: 50}
            ]
        ],
        toolbar: [
            {
                text: '查询',
                iconCls: 'icon-search',
                handler: function () {
                    search();
                }
            },
            '-',
            {
                text: '清空条件',
                iconCls: 'icon-no',
                handler: function () {
                    $select_user_search_form.form('clear');
                }
            }
        ],
        onCheck: function (rowIndex, rowData) {
            addSelectUser([rowData]);
        },
        onCheckAll: function (rows) {
            addSelectUser(rows);
        },
        onUncheck: function (rowIndex, rowData) {
            cancelSelectUser([rowData]);
        },
        onUncheckAll: function (rows) {
            cancelSelectUser(rows);
        },
        onDblClickRow: function (rowIndex, rowData) {
//                addSelectUser([rowData]);
        },
        onLoadSuccess: function (data) {
            $.each(selectUserIds, function (i, userId) {
                $.each(data.rows, function (j, row) {
                    if (userId == row.id) {
                        $select_user_datagrid.datagrid("selectRow", j);
                    }
                });
            });
        }
    });
}
function addSelectUser(rowData) {
    var rows = undefined;
    if (rowData) {
        rows = rowData;
    } else {
        rows = $select_user_datagrid.datagrid('getSelections');
    }
    if (rows) {
        $.each(rows, function (i, row) {
            var isSame = false;
            if ($("#selectUser option").length == 0) {
                $("#selectUser").append("<option value='" + row.id + "'>" + row.loginName + "</option>");
            } else {
                $("#selectUser option").each(function () {
                    if ($(this).val() == row.id) {
                        isSame = true;
                        return;
                    }
                });
                if (!isSame) {
                    $("#selectUser").append("<option value='" + row.id + "'>" + row.loginName + "</option>");
                }
            }
        });

    } else {
        eu.showMsg("请选择要操作的对象！");
    }
}

function cancelSelectUser(rowData) {
    var rows = undefined;
    if (rowData) {
        rows = rowData;
    } else {
        rows = $select_user_datagrid.datagrid('getSelections');
    }
    if (rows) {
        $.each(rows, function (i, row) {
            $("#selectUser option[value='" + row.id + "']").remove();
        });

    } else {
        eu.showMsg("请选择要操作的对象！");
    }
}
//部门树形
function selectOrganTree() {
    //组织机构树
    var selectedOrganNode = null;//存放被选中的节点对象 临时变量
    $select_organ_tree = $("#select_organ_tree").tree({
        url: "${ctx}/sys/organ/tree",
        onClick: function (node) {
            $select_role_tree.tree('select', null);
            search();
        },
        onBeforeSelect: function (node) {
            var selected = $(this).tree('getSelected');
            if (selected != undefined && node != undefined) {
                if (selected.id == node.id) {
                    $(".tree-node-selected", $(this).tree()).removeClass("tree-node-selected");//移除样式
                    selectedOrganNode = null;
                    return false;
                }
            }
            selectedOrganNode = node;
            return true;
        },
        onLoadSuccess: function (node, data) {
            if (selectedOrganNode != null) {
                selectedOrganNode = $(this).tree('find', selectedOrganNode.id);
                if (selectedOrganNode != null) {//刷新树后 如果临时变量中存在被选中的节点 则重新将该节点置为被选状态
                    $(this).tree('select', selectedOrganNode.target);
                }
            }
            $(this).tree("expandAll");
        }
    });
}
//部门树形
function selectRoleTree() {
    //组织机构树
    var selectedRoleNode = null;//存放被选中的节点对象 临时变量
    $select_role_tree = $("#select_role_tree").tree({
        url: "${ctx}/sys/role/tree",
        onClick: function (node) {
            $select_organ_tree.tree('select', null);
            search();
        },
        onBeforeSelect: function (node) {
            var selected = $(this).tree('getSelected');
            if (selected != undefined && node != undefined) {
                if (selected.id == node.id) {
                    $(".tree-node-selected", $(this).tree()).removeClass("tree-node-selected");//移除样式
                    selectedRoleNode = null;
                    return false;
                }
            }
            selectedRoleNode = node;
            return true;
        },
        onLoadSuccess: function (node, data) {
            if (selectedRoleNode != null) {
                selectedRoleNode = $(this).tree('find', selectedRoleNode.id);
                if (selectedRoleNode != null) {//刷新树后 如果临时变量中存在被选中的节点 则重新将该节点置为被选状态
                    $(this).tree('select', selectedRoleNode.target);
                }
            }
            $(this).tree("expandAll");
        }
    });
}
//搜索
function search() {
    var selectOrganNode = $select_organ_tree.tree('getSelected');//
    var organId = '';
    if (selectOrganNode != null) {
        organId = selectOrganNode.id; //搜索 id:主键 即是通过左边组织机构树点击得到搜索结果
    }
    var selectRoleNode = $select_role_tree.tree('getSelected');//
    var roleId = '';
    if (selectRoleNode != null) {
        roleId = selectRoleNode.id; //搜索 id:主键 即是通过左边组织机构树点击得到搜索结果
    }
    $select_user_datagrid.datagrid('load', {organId: organId, roleId: roleId, loginNameOrName: $("#select_filter_EQS_name_OR_loginName").val()});
}
</script>
<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">

    <div data-options="region:'west',title:'选择人员',split:true,collapsed:false,border:false"
         style="width: 200px;text-align: left;padding:0px;">
        <div class="easyui-accordion" data-options="fit:true" style="width:190px;">
            <div title="按部门" iconCls="icon-group" style="overflow:auto;padding:4px;">
                <div id="select_organ_tree"></div>
            </div>
            <div title="按角色" iconCls="icon-group" style="padding:4px;">
                <div id="select_role_tree"></div>
            </div>
        </div>
    </div>


    <!-- 中间部分 列表 -->
    <div data-options="region:'center',split:true" style="overflow: hidden;">
        <div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">
            <div data-options="region:'center',split:true" style="overflow: hidden;">
                <table id="select_user_datagrid"></table>
            </div>

            <div data-options="region:'north',title:'过滤条件',split:false,collapsed:false,border:false"
                 style="width: 100%;height:56px; ">
                <form id="select_user_search_form" style="padding: 0px;">
                    &nbsp;&nbsp;登录名或姓名:<input type="text" id="select_filter_EQS_name_OR_loginName"
                                              name="select_filter_EQS_name_OR_loginName" maxLength="25"
                                              placeholder="登录名或姓名..." style="width: 200px"/>
                </form>
            </div>
        </div>
    </div>
    <div data-options="region:'east',title:'已选择人员',split:true,collapsed:false,border:false"
         style="width: 180px;text-align: left;padding:1px;">
        <div>
            <select id="selectUser" multiple="multiple" style="width:178px;height:388px">
                <c:forEach var="user" begin="0" items="${users}">
                    <option value='${user.id}'>${user.loginName}</option>
                </c:forEach>
            </select>
        </div>
    </div>
</div>