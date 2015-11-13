<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<script type="text/javascript">
    /**
     * 判断是否登录用户是不是超级管理员.
     * @returns {Boolean}
     */
    function isSuperUser(){
        //登录用户id
        var sessonUserId = "${sessionInfo.userId}";
        //超级管理员id为"1"
        if(sessonUserId ==1){
            return true;
        }
        return false;
    }
    /**
     * 是否是超级用户id.
     */
    function isSuperOwner(userId){
        //超级管理员id为"1"
        if(userId ==1){
            return true;
        }
        return false;
    }

    /**
     * 判但是是否允许操作.
     * @param userId
     */
    function isOpeated(userId){
        if(userId == 1 && isSuperUser() == false){
            return false;
        }
        return true;
    }
    var organ_tree;//组织机树(左边)
    var user_datagrid;
    var user_form;
    var user_password_form;
    var user_role_form;
    var user_resource_form;
    var user_organ_form;
    var user_search_form;
    var user_dialog;
    var user_password_dialog;
    var user_role_dialog;
    var user_resource_dialog;
    var user_organ_dialog;

    var $user_post_form;
    var $user_post_dialog;

    $(function() {
        user_search_form = $('#user_search_form').form();


        //组织机构树
        var selectedNode = null;//存放被选中的节点对象 临时变量
        organ_tree = $("#organ_tree").tree({
            url : "${ctx}/sys/organ/tree",
            onClick:function(node){
                search();
            },
            onBeforeSelect:function(node){
                var selected = $(this).tree('getSelected');
                if(selected){
                    if(selected.id == node.id){
                        $(".tree-node-selected", $(this).tree()).removeClass("tree-node-selected");//移除样式
                        selectedNode = null;
                        return false;
                    }
                }
                selectedNode = node;
                return true;
            },
            onLoadSuccess:function(node, data){
                if(selectedNode !=null){
                    selectedNode = $(this).tree('find', selectedNode.id);
                    if(selectedNode !=null){//刷新树后 如果临时变量中存在被选中的节点 则重新将该节点置为被选状态
                        $(this).tree('select', selectedNode.target);
                    }
                }
                $(this).tree("expandAll");
            }
        });

        //数据列表
        user_datagrid = $('#user_datagrid').datagrid({
            url:'${ctx}/sys/user/userDatagrid',
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
            frozen:true,
            collapsible: true,
            frozenColumns:[[
                {field:'ck',checkbox:true},
                {field:'loginName',title:'登录名',width:120,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        if(isSuperOwner(rowData.id)){
                            return $.formatString('<span  style="color:red">{0}</span>',value);
                        }
                        return value;
                    }
                },
                {field:'name',title:'姓名',width:100,sortable:true},
                {field:'tel',title:'电话',width:120},
                {field:'mobilephone',title:'手机号',width:120}
            ]],
            columns:[[
                {field:'id',title:'主键',hidden:true,sortable:true,align:'right',width:80} ,
                {field:'defaultOrganName',title:'默认机构',width:160},
                {field:'organNames',title:'所属组织机构',width:200},
                {field:'postNames',title:'岗位',width:160},
                {field:'roleNames',title:'关联角色',width:200,
                    formatter:function(value,rowData,rowIndex){
                        if(isSuperOwner(rowData.id)){
                            return $.formatString('<span  style="color:red">{0}</span>','超级管理员(无需设置角色)');
                        }
                        return value;
                    }
                },
                {field:'sexView',title:'性别',width:60,align:'center'},
                {field:'email',title:'邮箱',width:120},
                {field:'address',title:'地址',width:200},
                {field:'statusView',title:'状态',align:'center',width:60}
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
                text:'修改密码',
                iconCls:'icon-lock',
                handler:function(){editPassword()}
            },'-',{
                text:'设置机构',
                iconCls:'icon-group',
                handler:function(){editUserOrgan()}
            },'-',{
                text:'设置岗位',
                iconCls:'icon-edit',
                handler:function(){editUserPost()}
            },'-',{
                text:'设置角色',
                iconCls:'icon-group',
                handler:function(){editUserRole()}
            },'-',{
                text:'设置资源',
                iconCls:'icon-edit',
                handler:function(){editUserResource()}
            }],
            onLoadSuccess:function(){
                $(this).datagrid('clearSelections');//取消所有的已选择项
                $(this).datagrid('unselectAll');//取消全选按钮为全选状态
            },
            onRowContextMenu : function(e, rowIndex, rowData) {
                e.preventDefault();
                $(this).datagrid('unselectAll');
                $(this).datagrid('selectRow', rowIndex);
                $('#user_datagrid_menu').menu('show', {
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
    user_form = $('#user_form').form({
        url: '${ctx}/sys/user/save',
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
                user_dialog.dialog('destroy');//销毁对话框
                user_datagrid.datagrid('reload');//重新加载列表数据
                organ_tree.tree("reload");
                eu.showMsg(json.msg);//操作结果提示
            }else if(json.code == 2){
                $.messager.alert('提示信息！', json.msg, 'warning',function(){
                    if(json.obj){
                        $('#user_form input[name="'+json.obj+'"]').focus();
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
    var inputUrl = "${ctx}/sys/user/input";
    if(row != undefined && row.id){
        inputUrl = inputUrl+"?id="+row.id;
    }

    //弹出对话窗口
    user_dialog = $('<div/>').dialog({
        title:'用户详细信息',
        top:20,
        width : 500,
        modal : true,
        maximizable:true,
        href : inputUrl,
        buttons : [ {
            text : '保存',
            iconCls : 'icon-save',
            handler : function() {
                user_form.submit();
            }
        },{
            text : '关闭',
            iconCls : 'icon-cancel',
            handler : function() {
                user_dialog.dialog('destroy');
            }
        }],
        onClose : function() {
            user_dialog.dialog('destroy');
        },
        onLoad:function(){
            formInit();
            if(row){
                $('#password_div').css('display','none');
                $('#repassword_div').css('display','none');
                $('#password_div input').removeAttr('class');
                $('#repassword_div input').removeAttr('class');
                user_form.form('load', row);
            }else{
                var node = organ_tree.tree('getSelected'); //组织机构选中节点
                if(node != undefined && node.id != undefined){
                    //设置组织机构默认值
                    $('#organIds').combogrid("setValue",node.id);
                }

                $('#password_div').css('display','block');
                $('#repassword_div').css('display','block');
                $("input[name=status]:eq(0)").attr("checked",'checked');//状态 初始化值
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
    var rows = user_datagrid.datagrid('getSelections');
    //选中的行（第一次选择的行）
    var row = user_datagrid.datagrid('getSelected');
    if (row){
        if(rows.length>1){
            row = rows[rows.length-1];
            eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
        }
        //判断是否允许操作
        if(isOpeated(row.id) == false){
            eu.showMsg("超级管理员用户信息不允许被其他人修改！");
            return;
        }

        showDialog(row);
    }else{
        eu.showMsg("请选择要操作的对象！");
    }
}
//初始化修改密码表单
function initPasswordForm(){
    user_password_form = $('#user_password_form').form({
        url: '${ctx}/sys/user/updateUserPassword',
        onSubmit: function(param){
            param.upateOperate = '0';
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
                user_password_dialog.dialog('destroy');//销毁对话框
                user_datagrid.datagrid('reload');	// reload the user data
                organ_tree.tree("reload");
                eu.showMsg(json.msg);//操作结果提示
            }else {
                eu.showAlertMsg(json.msg,'error');
            }
        }
    });
}

//修改用户密码
function editPassword(){
    //选中的所有行
    var rows = user_datagrid.datagrid('getSelections');
    //选中的行（第一条）
    var row = user_datagrid.datagrid('getSelected');
    if (row){
        if(rows.length>1){
            eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
        }
        //判断是否允许操作
        if(isOpeated(row.id) == false){
            eu.showMsg("超级管理员用户信息不允许被其他人修改！");
            return;
        }

        user_password_dialog = $('<div/>').dialog({
            title:'修改用户密码',
            top:20,
            width : 500,
            modal : true,
            maximizable:true,
            href : '${ctx}/sys/user/password',
            buttons : [ {
                text : '保存',
                iconCls : 'icon-save',
                handler : function() {
                    user_password_form.submit();
                }
            },{
                text : '关闭',
                iconCls : 'icon-cancel',
                handler : function() {
                    user_password_dialog.dialog('destroy');
                }
            }],
            onClose : function() {
                user_password_dialog.dialog('destroy');
            },
            onLoad:function(){
                initPasswordForm();
                $('#user_password_form_id').val(row.id);
            }
        }).dialog('open');

    }else{
        eu.showMsg("请选择要操作的对象！");
    }
}

//初始化用户角色表单
function initUserRoleForm(){
    user_role_form = $('#user_role_form').form({
        url: '${ctx}/sys/user/updateUserRole',
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
                user_role_dialog.dialog('destroy');//销毁对话框
                user_datagrid.datagrid('reload');	// reload the user data
                organ_tree.tree("reload");
                eu.showMsg(json.msg);//操作结果提示
            }else {
                eu.showAlertMsg(json.msg,'error');
            }
        }
    });
}

//修改用户角色
function editUserRole(){
    //选中的所有行
    var rows = user_datagrid.datagrid('getSelections');
    //选中的行（第一条）
    var row = user_datagrid.datagrid('getSelected');
    if (row){
        if(rows.length>1){
            eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
        }
        //判断是否允许操作
        if(isOpeated(row.id) == false){
            eu.showMsg("超级管理员用户信息不允许被其他人修改！");
            return;
        }
        //判断是否允许操作
        if(row.id == 1){
            eu.showMsg("超级管理员无需设置角色！");
            return;
        }

        var inputUrl = '${ctx}/sys/user/role';
        if(row != undefined && row.id){
            inputUrl = inputUrl+"?id="+row.id;
        }
        //弹出对话窗口
        user_role_dialog = $('<div/>').dialog({
            title:'用户角色信息',
            top:20,
            width : 500,
            modal : true,
            maximizable:true,
            href : inputUrl,
            buttons : [ {
                text : '保存',
                iconCls : 'icon-save',
                handler : function() {
                    user_role_form.submit();
                }
            },{
                text : '关闭',
                iconCls : 'icon-cancel',
                handler : function() {
                    user_role_dialog.dialog('destroy');
                }
            }],
            onClose : function() {
                user_role_dialog.dialog('destroy');
            },
            onLoad:function(){
                initUserRoleForm();
                user_role_form.form('load', row);
            }
        }).dialog('open');

    }else{
        eu.showMsg("请选择要操作的对象！");
    }
}


//初始化用户角色表单
function initUserResourceForm(){
    user_resource_form = $('#user_resource_form').form({
        url: '${ctx}/sys/user/updateUserResource',
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
                user_resource_dialog.dialog('destroy');//销毁对话框
                user_datagrid.datagrid('reload');	// reload the user data
                organ_tree.tree("reload");
                eu.showMsg(json.msg);//操作结果提示
            }else {
                eu.showAlertMsg(json.msg,'error');
            }
        }
    });
}
//修改用户角色
function editUserResource(){
    //选中的所有行
    var rows = user_datagrid.datagrid('getSelections');
    //选中的行（第一条）
    var row = user_datagrid.datagrid('getSelected');
    if (row){
        if(rows.length>1){
            eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
        }
        //判断是否允许操作
        if(isOpeated(row.id) == false){
            eu.showMsg("超级管理员用户信息不允许被其他人修改！");
            return;
        }
        //判断是否允许操作
        if(row.id == 1){
            eu.showMsg("超级管理员无需设置资源！");
            return;
        }
        //弹出对话窗口
        user_resource_dialog = $('<div/>').dialog({
            title:'用户资源信息',
            top:20,
            width : 500,
            modal : true,
            maximizable:true,
            href : '${ctx}/sys/user/resource',
            buttons : [ {
                text : '保存',
                iconCls : 'icon-save',
                handler : function() {
                    user_resource_form.submit();
                }
            },{
                text : '关闭',
                iconCls : 'icon-cancel',
                handler : function() {
                    user_resource_dialog.dialog('destroy');
                }
            }],
            onClose : function() {
                user_resource_dialog.dialog('destroy');
            },
            onLoad:function(){
                initUserResourceForm();
                user_resource_form.form('load', row);
            }
        }).dialog('open');

    }else{
        eu.showMsg("请选择要操作的对象！");
    }
}

//初始化用户机构表单
function initUserOrganForm(){
    user_organ_form = $('#user_organ_form').form({
        url: '${ctx}/sys/user/updateUserOrgan',
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
                user_organ_dialog.dialog('destroy');//销毁对话框
                user_datagrid.datagrid('reload');	// reload the user data
                organ_tree.tree("reload");
                eu.showMsg(json.msg);//操作结果提示
            }else {
                eu.showAlertMsg(json.msg,'error');
            }
        }
    });
}

//设置用户机构
function editUserOrgan(){
    //选中的所有行
    var rows = user_datagrid.datagrid('getSelections');
    //选中的行（第一条）
    var row = user_datagrid.datagrid('getSelected');
    if (row){
        if(rows.length>1){
            eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
        }
        //判断是否允许操作
        if(isOpeated(row.id) == false){
            eu.showMsg("超级管理员用户信息不允许被其他人修改！");
            return;
        }

        var inputUrl = '${ctx}/sys/user/organ';
        if(row != undefined && row.id){
            inputUrl = inputUrl+"?id="+row.id;
        }
        //弹出对话窗口
        user_organ_dialog = $('<div/>').dialog({
            title:'用户机构信息',
            top:20,
            width : 500,
            modal : true,
            maximizable:true,
            href : inputUrl,
            buttons : [ {
                text : '保存',
                iconCls : 'icon-save',
                handler : function() {
                    user_organ_form.submit();
                }
            },{
                text : '关闭',
                iconCls : 'icon-cancel',
                handler : function() {
                    user_organ_dialog.dialog('destroy');
                }
            }],
            onClose : function() {
                user_organ_dialog.dialog('destroy');
            },
            onLoad:function(){
                initUserOrganForm();
                user_organ_form.form('load', row);
            }
        }).dialog('open');

    }else{
        eu.showMsg("请选择要操作的对象！");
    }
}

//初始化用户岗位表单
function initUserPostForm(){
    $user_post_form = $('#user_post_form').form({
        url: '${ctx}/sys/user/updateUserPost',
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
                $user_post_dialog.dialog('destroy');//销毁对话框
                user_datagrid.datagrid('reload');	// reload the role data
                eu.showMsg(json.msg);//操作结果提示
            }else {
                eu.showAlertMsg(json.msg,'error');
            }
        }
    });
}
//修改用户岗位
function editUserPost(){
    //选中的所有行
    var rows = user_datagrid.datagrid('getSelections');
    //选中的行（第一条）
    var row = user_datagrid.datagrid('getSelected');
    if (row){
        if(rows.length>1){
            eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
        }
        var userUrl = "${ctx}/sys/user/post";
        if(row != undefined && row.id){
            userUrl = userUrl+"?id="+row.id;
        }
        //弹出对话窗口
        $user_post_dialog = $('<div/>').dialog({
            title:'用户岗位信息',
            top:20,
            width : 500,
            modal : true,
            maximizable:true,
            href : userUrl,
            buttons : [ {
                text : '保存',
                iconCls : 'icon-save',
                handler : function() {
                    $user_post_form.submit();
                }
            },{
                text : '关闭',
                iconCls : 'icon-cancel',
                handler : function() {
                    $user_post_dialog.dialog('destroy');
                }
            }],
            onClose : function() {
                $user_post_dialog.dialog('destroy');
            },
            onLoad:function(){
                initUserPostForm();
                $user_post_form.form('load', row);
            }
        }).dialog('open');

    }else{
        eu.showMsg("请选择要操作的对象！");
    }
}

//删除用户
function del(){
    var rows = user_datagrid.datagrid('getSelections');

    if(rows.length >0){
        $.messager.confirm('确认提示！','您确定要删除选中的所有行?',function(r){
            if (r){
                var ids = new Array();
                $.each(rows,function(i,row){
                    ids[i] = row.id;
                });
                $.ajax({
                    url:'${ctx}/sys/user/_remove',
                    type:'post',
                    data: {ids:ids},
                    traditional:true,
                    dataType:'json',
                    success:function(data) {
                        if (data.code==1){
                            user_datagrid.datagrid('load');	// reload the user data
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
    var node = organ_tree.tree('getSelected');//
    var organId = '';
    if(node != null){
        organId = node.id; //搜索 id:主键 即是通过左边组织机构树点击得到搜索结果
    }

    user_datagrid.datagrid('load',{organId:organId,loginNameOrName:$("#loginNameOrName").val()});
}
</script>
<%-- 列表右键 --%>
<div id="user_datagrid_menu" class="easyui-menu" style="width:120px;display: none;">
    <div onclick="showDialog();" data-options="iconCls:'icon-add'">新增</div>
    <div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
    <div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
    <div onclick="editPassword();" data-options="iconCls:'icon-lock'">修改密码</div>
    <div onclick="editUserOrgan();" data-options="iconCls:'icon-group'">设置机构</div>
    <div onclick="editUserPost();" data-options="iconCls:'icon-group'">设置岗位</div>
    <div onclick="editUserRole();" data-options="iconCls:'icon-group'">设置角色</div>
    <div onclick="editUserResource();" data-options="iconCls:'icon-group'">设置资源</div>
</div>
<%-- easyui-layout布局 --%>
<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">

    <%-- 左边部分 菜单树形 --%>
    <div data-options="region:'west',title:'组织机构列表',split:false,collapsed:false,border:false"
         style="width: 180px; text-align: left;padding:5px;">
        <ul id="organ_tree"></ul>
    </div>

    <!-- 中间部分 列表 -->
    <div data-options="region:'center',split:true" style="overflow: hidden;">
        <div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">
            <div data-options="region:'center',split:true" style="overflow: hidden;">
                <table id="user_datagrid" ></table>
            </div>

            <div data-options="region:'north',title:'过滤条件',split:false,collapsed:false,border:false"
                 style="width: 100%;height:60px; ">
                <form id="user_search_form" style="padding: 0px;">
                    &nbsp;登录名或姓名:<input type="text" id="loginNameOrName" name="loginNameOrName" placeholder="请输入登录名或姓名..."
                                        onkeydown="if(event.keyCode==13)search()" maxLength="25" style="width: 160px"/>
                    <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search',onClick:search">查询</a>
                    <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-no'" onclick="javascript:user_search_form.form('reset');">重置查询</a>
                </form>
            </div>
        </div>
    </div>
</div>