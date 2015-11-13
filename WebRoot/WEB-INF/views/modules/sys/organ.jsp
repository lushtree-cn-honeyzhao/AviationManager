<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<script type="text/javascript">
var organ_treegrid;
var organ_form;
var organ_user_form;
var organ_dialog;
var organ_user_dialog;
var organ_search_form;
var organ_Id;
$(function() {
    //数据列表
    organ_treegrid = $('#organ_treegrid').treegrid({
        url:'${ctx}/sys/organ/treegrid',
        fit:true,
        fitColumns:false,//自适应列宽
        striped:true,//显示条纹
        rownumbers:true,//显示行数
        nowrap : false,
        border : false,
        singleSelect:true,
        remoteSort:false,//是否通过远程服务器对数据排序
        sortName:'orderNo',//默认排序字段
        sortOrder:'asc',//默认排序方式 'desc' 'asc'
        idField : 'id',
        treeField:"name",
        frozenColumns:[[
            {field:'name',title:'机构名称',width:200},
            {field:'sysCode',title:'机构系统编码',width:120}
        ]],
        columns:[[
            {field:'id',title:'主键',hidden:true,sortable:true,align:'right',width:80},
            {field:'managerUserLoginName',title:'主管',width:120},
            {field:'superManagerUserLoginName',title:'分管领导',width:120},
            {field:'code',title:'机构编码',width:120},
            {field:'address',title:'地址',width:120},
            {field:'phone',title:'电话号码',width:120},
            {field:'fax',title:'传真号',width:120},
            {field:'typeView',title:'机构类型',align:'center',width:100},
            {field:'orderNo',title:'排序',align:'right',width:60,sortable:true},
            {field:'userLoginNames',title:'机构用户',width:420},
            {field:'statusDesc',title:'状态',align:'center',width:60}
        ]],
        toolbar:[{
            text:'新增',
            iconCls:'icon-add',
            handler:function(){showDialog()}
        },'-',{
            text:'编辑',
            iconCls:'icon-edit',
            handler:function(){edit()}
        },'-',{
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){del()}
        },'-',{
            text:'设置用户',
            iconCls:'icon-edit',
            handler:function(){editOrganUser()}
        }],
        onContextMenu : function(e, row) {
            e.preventDefault();
            $(this).treegrid('select', row.id);
            $('#organ_menu').menu('show', {
                left : e.pageX,
                top : e.pageY
            });

        },
        onDblClickRow:function(row){
            edit(row);
        }
    }).datagrid('showTooltip');

});

function formInit(){
    organ_form = $('#organ_form').form({
        url: '${ctx}/sys/organ/_save',
        onSubmit: function(param){
            $.messager.progress({
                title : '提示信息！',
                text : '数据处理中，请稍后....'
            });
            var isValid = $(this).form('validate');
            if (!isValid) {
                $.messager.progress('close');
            }
            return isValid;
        },
        success: function(data){
            $.messager.progress('close');
            var json = $.parseJSON(data);
            if (json.code ==1){
                organ_dialog.dialog('destroy');//销毁对话框
                organ_treegrid.treegrid('reload');//重新加载列表数据
                eu.showMsg(json.msg);//操作结果提示
            }else if(json.code == 2){
                $.messager.alert('提示信息！', json.msg, 'warning',function(){
                    if(json.obj){
                        $('#organ_form input[name="'+json.obj+'"]').focus();
                    }
                });
            }else {
                eu.showAlertMsg(json.msg,'error');
            }
        },
        onLoadSuccess:function(data){
            if(data != undefined && data._parentId != undefined){
                //$('#_parentId')是弹出-input页面的对象 代表所属分组
                $('#_parentId').combotree('setValue',data._parentId);
            }
        }
    });
}
//显示弹出窗口 新增：row为空 编辑:row有值
function showDialog(row){
    var inputUrl = "${ctx}/sys/organ/input";
    if(row != undefined && row.id){
        inputUrl = inputUrl+"?id="+row.id;
    }else{
        var selectedNode = organ_treegrid.treegrid('getSelected');
        if(selectedNode != undefined && selectedNode.type != undefined){
            inputUrl +="?parentOrganType="+selectedNode.type;
        }
    }

    //弹出对话窗口
    organ_dialog = $('<div/>').dialog({
        title:'机构详细信息',
        top:20,
        width : 500,
        modal : true,
        maximizable:true,
        href : inputUrl,
        buttons : [ {
            text : '保存',
            iconCls : 'icon-save',
            handler : function() {
                organ_form.submit();
            }
        },{
            text : '关闭',
            iconCls : 'icon-cancel',
            handler : function() {
                organ_dialog.dialog('destroy');
            }
        }],
        onClose : function() {
            organ_dialog.dialog('destroy');
        },
        onLoad:function(){
            formInit();
            if(row){
                organ_form.form('load', row);
            } else{
                var selectedNode = organ_treegrid.treegrid('getSelected');
                if(selectedNode){
                    var initFormData = {'_parentId':[selectedNode.id],'type':selectedNode.type};
                    organ_form.form('load',initFormData );
                }
            }
        }
    }).dialog('open');

}


//编辑
function edit(row) {
    if (row == undefined) {
        row = organ_treegrid.treegrid('getSelected');
    }
    if (row != undefined) {
        showDialog(row);
    } else {
        eu.showMsg("请选择要操作的对象！");
    }
}

//初始化机构用户表单
function initOrganUserForm(){
    organ_user_form = $('#organ_user_form').form({
        url: '${ctx}/sys/organ/updateOrganUser',
        onSubmit: function(param){
            $.messager.progress({
                title : '提示信息！',
                text : '数据处理中，请稍后....'
            });
            var isValid = $(this).form('validate');
            if (!isValid) {
                $.messager.progress('close');
            }
            return isValid;
        },
        success: function(data){
            $.messager.progress('close');
            var json = $.parseJSON(data);
            if (json.code == 1){
                organ_user_dialog.dialog('destroy');//销毁对话框
                organ_treegrid.treegrid('reload');	// reload the organ data
                eu.showMsg(json.msg);//操作结果提示
            }else {
                eu.showAlertMsg(json.msg,'error');
            }
        }
    });
}
//修改机构用户
function editOrganUser(){
    //选中的行（第一条）
    var row = organ_treegrid.treegrid('getSelected');
    if (row){
        var userUrl = "${ctx}/sys/organ/user";
        if(row != undefined && row.id){
            userUrl = userUrl+"?id="+row.id;
        }
        //弹出对话窗口
        organ_user_dialog = $('<div/>').dialog({
            title:'机构用户信息',
            top:20,
            width : 500,
            modal : true,
            maximizable:true,
            href : userUrl,
            buttons : [ {
                text : '保存',
                iconCls : 'icon-save',
                handler : function() {
                    organ_user_form.submit();
                }
            },{
                text : '关闭',
                iconCls : 'icon-cancel',
                handler : function() {
                    organ_user_dialog.dialog('destroy');
                }
            }],
            onClose : function() {
                organ_user_dialog.dialog('destroy');
            },
            onLoad:function(){
                initOrganUserForm();
                organ_user_form.form('load', row);
            }
        }).dialog('open');

    }else{
        eu.showMsg("请选择要操作的对象！");
    }
}

//删除
function del(rowIndex){
    var row;
    if (rowIndex == undefined) {
        row = organ_treegrid.treegrid('getSelected');
    }
    if (row != undefined) {
        $.messager.confirm('确认提示！','您确定要删除(如果存在子节点，子节点也一起会被删除)？',function(r){
            if (r){
                $.post('${ctx}/sys/organ/delete/'+row.id,{},function(data){
                    if (data.code==1){
                        organ_treegrid.treegrid('unselectAll');//取消选择 1.3.6bug
                        organ_treegrid.treegrid('load');	// reload the user data
                        eu.showMsg(data.msg);//操作结果提示
                    } else {
                        eu.showAlertMsg(data.msg,'error');
                    }
                },'json');

            }
        });
    } else {
        eu.showMsg("请选择要操作的对象！");
    }
}

</script>
<%-- 列表右键 --%>
<div id="organ_menu" class="easyui-menu" style="width:120px;display: none;">
    <div onclick="showDialog();" data-options="iconCls:'icon-add'">新增</div>
    <div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
    <div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
    <div onclick="editOrganUser();" data-options="iconCls:'icon-edit'">设置用户</div>
</div>
<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">
    <%-- 中间部分 列表 --%>
    <div data-options="region:'center',split:false,border:false"
         style="padding: 0px; height: 100%;width:100%; overflow-y: hidden;">
        <table id="organ_treegrid"></table>

    </div>
</div>