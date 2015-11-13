<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<link href="${ctxStatic}/js/kendoui/styles/kendo.common.min.css" rel="stylesheet">
<link href="${ctxStatic}/js/kendoui/styles/kendo.default.min.css" rel="stylesheet">
<script src="${ctxStatic}/js/kendoui/js/kendo.all.min.js"></script>
<script type="text/javascript">
var role_datagrid;
var role_form;
var role_search_form;
var role_resource_form
var role_user_form;
var role_dialog;
var role_resource_dialog;
var role_user_dialog;
$(function() {  
	role_form = $('#role_form').form();
	role_search_form = $('#role_search_form').form();
    //数据列表
    role_datagrid = $('#role_datagrid').datagrid({  
	    url:'${ctx}/sys/role/datagrid',
        fit:true,
	    pagination:true,//底部分页
	    rownumbers:true,//显示行数
	    fitColumns:false,//自适应列宽
	    striped:true,//显示条纹
	    pageSize:20,//每页记录数
        remoteSort:false,//是否通过远程服务器对数据排序
	    sortName:'id',//默认排序字段
		sortOrder:'asc',//默认排序方式 'desc' 'asc'
		idField : 'id',
        frozenColumns:[[
            {field:'ck',checkbox:true},
            {field:'name',title:'角色名称',width:200},
            {field:'code',title:'角色编码',width:120}
        ]],
	    columns:[[
            {field:'id',title:'主键',hidden:true,sortable:true,align:'right',width:80},
            {field:'resourceNames',title:'关联资源',width:420},
	        {field:'remark',title:'描述',width:200}
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
            text:'设置资源',
            iconCls:'icon-edit',
            handler:function(){editRoleResource()}
        },'-',{
            text:'设置用户',
            iconCls:'icon-edit',
            handler:function(){editRoleUser()}
        }],
	    onLoadSuccess:function(){
	    	$(this).datagrid('clearSelections');//取消所有的已选择项
	    	$(this).datagrid('unselectAll');//取消全选按钮为全选状态
		},
	    onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			$('#role_datagrid_menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		},
        onDblClickRow:function(rowIndex, rowData){
            edit(rowIndex, rowData);
        }
	}).datagrid('showTooltip');
    
});
</script>
<script type="text/javascript">
        function formInit(){
        	role_form = $('#role_form').form({
				url: '${ctx}/sys/role/save',
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
						role_dialog.dialog('destroy');//销毁对话框 
						role_datagrid.datagrid('reload');//重新加载列表数据
						eu.showMsg(json.msg);//操作结果提示
					}else if(json.code == 2){
						$.messager.alert('提示信息！', json.msg, 'warning',function(){
							if(json.obj){
								$('#role_form input[name="'+json.obj+'"]').focus();
							}
						});
					}else {
						eu.showAlertMsg(json.msg,'error');
					}
				}
			});
		}
		//显示弹出窗口 新增：row为空 编辑:row有值 
		function showDialog(row){
            var inputUrl = "${ctx}/sys/role/input";
            if(row != undefined && row.id){
                inputUrl = inputUrl+"?id="+row.id;
            }

			//弹出对话窗口
			role_dialog = $('<div/>').dialog({
				title:'角色详细信息',
                top:20,
				width : 500,
				modal : true,
				maximizable:true,
				href : inputUrl,
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						role_form.submit();
					}
				},{
					text : '关闭',
					iconCls : 'icon-cancel',
					handler : function() {
						role_dialog.dialog('destroy');
					}
				}],
				onClose : function() {
                    role_dialog.dialog('destroy');
				},
				onLoad:function(){
					formInit();
					if(row){
						role_form.form('load', row);
					}
					
				}
			}).dialog('open');
			
		}
		
		//编辑
        function edit(rowIndex, rowData){
            //响应双击事件
            if(rowIndex != undefined) {
                showDialog(rowData);
                return;
            }
			//选中的所有行
			var rows = role_datagrid.datagrid('getSelections');
			//选中的行（第一次选择的行）
			var row = role_datagrid.datagrid('getSelected');
			if (row){
				if(rows.length>1){
					row = rows[rows.length-1];
					eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
				}
				showDialog(row);
			}else{
				eu.showMsg("请选择要操作的对象！");
			}
		}


        //初始化角色角色表单
        function initRoleResourceForm(){
            role_resource_form = $('#role_resource_form').form({
                url: '${ctx}/sys/role/updateRoleResource',
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
                        role_resource_dialog.dialog('destroy');//销毁对话框
                        role_datagrid.datagrid('reload');	// reload the role data
                        eu.showMsg(json.msg);//操作结果提示
                    }else {
                        eu.showAlertMsg(json.msg,'error');
                    }
                }
            });
        }
        //修改角色角色
        function editRoleResource(){
            //选中的所有行
            var rows = role_datagrid.datagrid('getSelections');
            //选中的行（第一条）
            var row = role_datagrid.datagrid('getSelected');
            if (row){
                if(rows.length>1){
                    eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
                }
                //弹出对话窗口
                role_resource_dialog = $('<div/>').dialog({
                    title:'角色资源信息',
                    top:20,
                    width : 500,
                    modal : true,
                    maximizable:true,
                    href : '${ctx}/sys/role/resource',
                    buttons : [ {
                        text : '保存',
                        iconCls : 'icon-save',
                        handler : function() {
                            role_resource_form.submit();
                        }
                    },{
                        text : '关闭',
                        iconCls : 'icon-cancel',
                        handler : function() {
                            role_resource_dialog.dialog('destroy');
                        }
                    }],
                    onClose : function() {
                        role_resource_dialog.dialog('destroy');
                    },
                    onLoad:function(){
                        initRoleResourceForm();
                        role_resource_form.form('load', row);
                    }
                }).dialog('open');

            }else{
                eu.showMsg("请选择要操作的对象！");
            }
        }

        //初始化角色用户表单
        function initRoleUserForm(){
            role_user_form = $('#role_user_form').form({
                url: '${ctx}/sys/role/updateRoleUser',
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
                        role_user_dialog.dialog('destroy');//销毁对话框
                        role_datagrid.datagrid('reload');	// reload the role data
                        eu.showMsg(json.msg);//操作结果提示
                    }else {
                        eu.showAlertMsg(json.msg,'error');
                    }
                }
            });
        }
        //修改角色用户
        function editRoleUser(){
            //选中的所有行
            var rows = role_datagrid.datagrid('getSelections');
            //选中的行（第一条）
            var row = role_datagrid.datagrid('getSelected');
            if (row){
                if(rows.length>1){
                    eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
                }
                var userUrl = "${ctx}/sys/role/user";
                if(row != undefined && row.id){
                    userUrl = userUrl+"?id="+row.id;
                }
                //弹出对话窗口
                role_user_dialog = $('<div/>').dialog({
                    title:'角色用户信息',
                    top:20,
                    width : 800,
                    modal : true,
                    maximizable:true,
                    href : userUrl,
                    buttons : [ {
                        text : '保存',
                        iconCls : 'icon-save',
                        handler : function() {
                            role_user_form.submit();
                        }
                    },{
                        text : '关闭',
                        iconCls : 'icon-cancel',
                        handler : function() {
                            role_user_dialog.dialog('destroy');
                        }
                    }],
                    onClose : function() {
                        role_user_dialog.dialog('destroy');
                    },
                    onLoad:function(){
                        initRoleUserForm();
                        role_user_form.form('load', row);
                    }
                }).dialog('open');

            }else{
                eu.showMsg("请选择要操作的对象！");
            }
        }

		//删除
		function del(){
			var rows = role_datagrid.datagrid('getSelections');
			if(rows.length >0){
				$.messager.confirm('确认提示！','您确定要删除选中的所有行？',function(r){
					if (r){
                        var ids = new Array();
                        $.each(rows,function(i,row){
                            ids[i] = row.id;
                        });
                        $.ajax({
                            url:'${ctx}/sys/role/_remove',
                            type:'post',
                            data: {ids:ids},
                            traditional:true,
                            dataType:'json',
                            success:function(data) {
                                if (data.code==1){
                                    role_datagrid.datagrid('load');	// reload the user data
                                    eu.showMsg(data.msg);//操作结果提示
                                } else {
                                    eu.showAlertMsg(data.msg,'error');
                                }
                            }
                        });
					}
				});
			}else{
				eu.showMsg("请选择要操作的对象！");
			}
		}
		
		//搜索
		function search(){
			role_datagrid.datagrid('load',$.serializeObject(role_search_form));
		}
		
</script>
<%-- 列表右键 --%>
<div id="role_datagrid_menu" class="easyui-menu" style="width:120px;display: none;">
	<div onclick="showDialog();" data-options="iconCls:'icon-add'">新增</div>
	<div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
	<div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
    <div onclick="editRoleResource();" data-options="iconCls:'icon-edit'">设置资源</div>
    <div onclick="editRoleUser();" data-options="iconCls:'icon-edit'">设置用户</div>
</div>

<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">
    <div data-options="region:'north',title:'过滤条件',collapsed:false,split:false,border:false"
         style="padding: 0px; height: 60px;width:100%; overflow-y: hidden;">
        <form id="role_search_form" style="padding: 2px;">
            &nbsp;角色名称:<input type="text" name="filter_LIKES_name" placeholder="请输入角色名称..."
                              onkeydown="if(event.keyCode==13)search()"  maxLength="25" style="width: 160px" />
            <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search',onClick:search">查询</a>
            <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-no'" onclick="javascript:role_search_form.form('reset');">重置查询</a>
        </form>
    </div>
    <%-- 中间部分 列表 --%>
    <div data-options="region:'center',split:false,border:false"
         style="padding: 0px; height: 100%;width:100%; overflow-y: hidden;">
        <table id="role_datagrid"></table>
    </div>
</div>


   