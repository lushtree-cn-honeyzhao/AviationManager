<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
    var user_combogrid;
    var managerUser_combogrid;
    var $superManagerUser_combogrid;
    var managerUserCacheValues;  //主管初始值
    $(function() {
        loadManagerUser();
        loadUser();
        loadSuperManagerUser();
    });

    //加载用户
    function loadUser(){
        var isChange = false; //标识所属组织机构是否变更
        user_combogrid = $('#userIds').combogrid({
            multiple:true,
            panelWidth:500,
            panelHeight:360,
            idField:'id',
            textField:'loginName',
            <%--url:'${ctx}/sys/user/combogridAll',--%>
            data: ${usersCombogridData},
            mode: 'remote',
            fitColumns: true,
            striped: true,
            editable:true,
            rownumbers:true,//序号
            collapsible:false,//是否可折叠的
            fit: true,//自动大小
            method:'post',
            columns:[[
                {field:'ck',checkbox:true},
                {field:'id',title:'主键ID',width:100,hidden:'true'},
                {field:'name',title:'姓名',width:80},
                {field:'loginName',title:'登录名',width:120}
            ]] ,
            onBeforeLoad:function(param){
                param.filter_EQI_status = 0;//排除已删除的数据
            },
            onHidePanel:function(){
                if(isChange){
                    managerUserCacheValues  = managerUser_combogrid.combogrid("getValues");
                    var selectionsData = $(this).combogrid('grid').datagrid("getSelections");
                    var managerUserData = new Array();
                    var managerUserTempValues = new Array();
                    $.each(selectionsData,function(index, row) {
                        managerUserData.push(row);
                        $.each(managerUserCacheValues,function(index, value) {
                            if(row.id == value){
                                managerUserTempValues.push(value);
                            }
                        });
                    });
                    managerUser_combogrid.combogrid("setValues",managerUserTempValues);
                    managerUser_combogrid.combogrid('grid').datagrid("loadData",managerUserData);
                }
            },
            onChange:function(newValue, oldValue){
                if((newValue.length != 0 || oldValue.length != 0) && newValue.toString() != oldValue.toString()){
                    isChange = true;
                }else{
                    isChange = false;
                }
            }
        });

    }

    //加载主管用户
    function loadManagerUser(){
        managerUser_combogrid = $('#managerUserId').combogrid({
            panelWidth:500,
            idField:'id',
            textField:'loginName',
            data:${managerUserCombogridData},
            fitColumns: true,
            striped: true,
            editable:false,
            rownumbers:true,//序号
            collapsible:false,//是否可折叠的
            fit: true,//自动大小
            method:'post',
            columns:[[
                {field:'id',title:'主键ID',width:100,hidden:'true'},
                {field:'name',title:'姓名',width:80},
                {field:'loginName',title:'登录名',width:120}
            ]],
            keyHandler : {
                enter: function() {
                    var name = $('#managerUserId').combogrid('getText');
                    var dg = $('#managerUserId').combogrid("grid");
                    dg.datagrid("reload", {'pageType':2,'filter_LIKES_loginName_OR_name':name});
                },
                query : function(q) {
                }
            },
            onBeforeLoad:function(param){
                param.filter_EQI_status = 0;//排除已删除的数据
            }
        });
    }
    //加载分管用户
    function loadSuperManagerUser(){
        $superManagerUser_combogrid = $('#superManagerUserId').combogrid({
            panelWidth:500,
            idField:'id',
            textField:'loginName',
            <%--url:'${ctx}/sys/user/combogridAll',--%>
            data: ${usersCombogridData},
            fitColumns: true,
            striped: true,
            editable:true,
            rownumbers:true,//序号
            collapsible:false,//是否可折叠的
            sortName:'name',//默认排序字段
            sortOrder:'asc',//默认排序方式 'desc' 'asc'
            fit: true,//自动大小
            method:'post',
            mode:'local',
            columns:[[
                {field:'id',title:'主键ID',width:100,hidden:'true'},
                {field:'name',title:'姓名',width:80},
                {field:'loginName',title:'登录名',width:120}
            ]],
            onBeforeLoad:function(param){
                param.filter_EQI_status = 0;//排除已删除的数据
            }
        });
    }
</script>
<div>
    <form id="organ_user_form" method="post">
        <input type="hidden" id="id" name="id" />
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
            <label>机构用户:</label>
            <input type="select" class="easyui-combogrid" id="userIds" name="userIds" style="width: 260px;"/>
        </div>
        <div>
            <label>主管:</label>
            <input type="select" class="easyui-combogrid" id="managerUserId" name="managerUserId" style="width: 260px;"/>
        </div>
        <div>
            <label>分管领导人:</label>
            <input type="select" class="easyui-combogrid" id="superManagerUserId" name="superManagerUserId" style="width: 260px;"/>
        </div>
    </form>
</div>