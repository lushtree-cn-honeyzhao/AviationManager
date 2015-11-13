<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
    var user_combogrid;
    $(function() {
        loadUser();
    });

    //加载用户
    function loadUser(){
        var isChange = false;
        user_combogrid = $('#userIds').combogrid({
            multiple:true,
            panelWidth:500,
            panelHeight:360,
            idField:'id',
            textField:'loginName',
            url:'${ctx}/sys/user/combogridOrganUser',
            mode: 'remote',
            fitColumns: true,
            striped: true,
            editable:false,
            rownumbers:true,//序号
            collapsible:false,//是否可折叠的
            fit: true,//自动大小
            method:'post',
            columns:[[
                {field:'ck',checkbox:true},
                {field:'id',title:'主键ID',width:100,hidden:'true'},
                {field:'name',title:'用户姓名',width:80,sortable:true},
                {field:'loginName',title:'用户登录名',width:120,sortable:true}
            ]],
            onBeforeLoad:function(param){
                param.organId = "${organId}";
            }
        });

    }
</script>
<div>
    <form id="post_user_form" method="post">
        <input type="hidden" id="id" name="id" />
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
            <label>岗位用户:</label>
            <input type="select" id="userIds" name="userIds" style="width: 360px;"/>
        </div>
    </form>
</div>