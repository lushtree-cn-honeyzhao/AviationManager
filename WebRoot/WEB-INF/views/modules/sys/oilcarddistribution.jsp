<%@ page import="com.lushapp.modules.sys.utils.DictionaryUtils" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%-- 引入kindEditor插件 --%>
<link rel="stylesheet" href="${ctxStatic}/js/kindeditor-4.1.10/themes/default/default.css">
<script type="text/javascript" src="${ctxStatic}/js/kindeditor-4.1.10/kindeditor-all-min.js" charset="utf-8"></script>
<script type="text/javascript">
var oilcarddistribution_datagrid;
var oilcarddistribution_form;
var oilcarddistribution_search_form;
var oilcarddistribution_dialog;

var oilcarddistribution_import_dialog;//oilcarddistribution导入表单弹出对话框
var oilcarddistribution_import_form;
$(function() {
	oilcarddistribution_form = $('#oilcarddistribution_form').form();
	oilcarddistribution_search_form = $('#oilcarddistribution_search_form').form();
    //数据列表
    oilcarddistribution_datagrid = $('#oilcarddistribution_datagrid').datagrid({  
	    url:'${ctx}/sys/oilcarddistribution/datagrid',
        fit:true,
	    pagination:true,//底部分页
	    rownumbers:true,//显示行数
	    fitColumns:false,//自适应列宽
	    striped:true,//显示条纹
	    nowrap : true,
	    pageSize:20,//每页记录数
        remoteSort:false,//是否通过远程服务器对数据排序
	    sortName:'id',//默认排序字段
		sortOrder:'asc',//默认排序方式 'desc' 'asc'
		idField : 'id',
        frozenColumns:[[
            {field:'ck',checkbox:true},
            {field:'oilCard.cardSn',title:'附属卡号',width:200,formatter:function(value,rowData,rowIndex){
                var html = $.formatString("<span style='color:{0}'>{1}</span>",rowData.color,value);
                return html;
            }}
        ]],
        columns:[[
              {field:'id',title:'主键',hidden:true,sortable:true,align:'right',width:80},
              {field:'oilCard.cardIdnumber',title:'身份证号',width:120 },
              {field:'oilCard.cardUser',title:'持卡人',width:120 },
              {field:'distribeAmount',title:'分配金额',width:120 },
              {field:'distribeIntegral',title:'分配积分',width:200 },
              {field:'completeTime',title:'完成时间',width:150 },
              {field:'operater',title:'操作',width:260,formatter:function(value,rowData,rowIndex){
            	  var url = $.formatString('${ctx}/sys/oilcarddistribution/_view?id={0}',rowData.id);
         	      var operaterHtml = "<a class='easyui-linkbutton' iconCls='icon-add'  " +
                          "onclick='view("+rowIndex+");' >查看</a>"
                  +"&nbsp;<a class='easyui-linkbutton' iconCls='icon-edit'  href='#' " +
                          "onclick='edit("+rowIndex+");' >编辑</a>"
                  +"&nbsp;<a class='easyui-linkbutton' iconCls='icon-remove'  href='#' " +
                  "onclick='del("+rowIndex+");' >删除</a>";
         	      return operaterHtml;
              }}
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
            } ,'-',{
                text:'Excel导出',
                iconCls:'icon-edit',
                handler:function(){exportExcel()}
            }/* ,'-',{
                text:'Excel导入',
                iconCls:'icon-edit',
                handler:function(){importExcel()}
            } */],
	    onLoadSuccess:function(){
	    	$(this).datagrid('clearSelections');//取消所有的已选择项
	    	$(this).datagrid('unselectAll');//取消全选按钮为全选状态
		},
	    onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			$('#oilcarddistribution_datagrid_menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		} ,
        onDblClickRow:function(rowIndex, rowData){
            edit(rowIndex, rowData);
        }
	}).datagrid('showTooltip');
});
</script>
<script type="text/javascript">

    function formInit(){
       	oilcarddistribution_form = $('#oilcarddistribution_form').form({
			url: '${ctx}/sys/oilcarddistribution/_save',
			onSubmit: function(param){  
				$.messager.progress({
					title : '提示信息！',
					text : '数据处理中，请稍后....'
				});
				if(content_kindeditor){
					content_kindeditor.sync();
				}
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
					oilcarddistribution_dialog.dialog('destroy');//销毁对话框 
					oilcarddistribution_datagrid.datagrid('reload');//重新加载列表数据
					eu.showMsg(json.msg);//操作结果提示
				}else if(json.code == 2){
					$.messager.alert('提示信息！', json.msg, 'warning',function(){
						if(json.obj){
							$('#oilcarddistribution_form input[name="'+json.obj+'"]').focus();
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
        var inputUrl = "${ctx}/sys/oilcarddistribution/_input";
        if(row != undefined && row.id){
            inputUrl = inputUrl+"?id="+row.id;
        }

		//弹出对话窗口
		oilcarddistribution_dialog = $('<div/>').dialog({
			title:'详细信息',
			width : 850,
			height : 500,
			modal : true,
			maximizable:true,
			href : inputUrl,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					oilcarddistribution_form.submit();
				}
			},{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					oilcarddistribution_dialog.dialog('destroy');
				}
			}],
			onClose : function() {
                oilcarddistribution_dialog.dialog('destroy');
			},
			onLoad:function(){
				formInit();
				if(row){
					oilcarddistribution_form.form('load', row);
				}
				if(content_kindeditor){
					content_kindeditor.sync();
				}
			}
		}).dialog('open');
		
	}
	
	//显示弹出窗口 新增：row为空 编辑:row有值 
	function showDialog_view(row){
        var inputUrl = "${ctx}/sys/oilcarddistribution/_view";
        if(row != undefined && row.id){
            inputUrl = inputUrl+"?id="+row.id;
        }

		//弹出对话窗口
		oilcarddistribution_dialog = $('<div/>').dialog({
			title:'详细信息',
			width : 850,
			height : 500,
			modal : true,
			maximizable:true,
			href : inputUrl,
			buttons : [ {
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					oilcarddistribution_dialog.dialog('destroy');
				}
			}],
			onClose : function() {
                oilcarddistribution_dialog.dialog('destroy');
			},
			onLoad:function(){
				formInit();
				if(row){
					oilcarddistribution_form.form('load', row);
				}
				if(content_kindeditor){
					content_kindeditor.sync();
				}
			}
		}).dialog('open');
		
	}
	
	//编辑
    function edit(rowIndex, rowData){
        //响应双击事件
        if(rowIndex != undefined) {
            oilcarddistribution_datagrid.datagrid('unselectAll');
            oilcarddistribution_datagrid.datagrid('selectRow',rowIndex);
            var rowData = oilcarddistribution_datagrid.datagrid('getSelected');
            oilcarddistribution_datagrid.datagrid('unselectRow',rowIndex);
            showDialog(rowData);
            return;
        }
		//选中的所有行
		var rows = oilcarddistribution_datagrid.datagrid('getSelections');
		//选中的行（第一次选择的行）
		var row = oilcarddistribution_datagrid.datagrid('getSelected');
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
	
	//查看
    function view(rowIndex, rowData){
        //响应双击事件
        if(rowIndex != undefined) {
            oilcarddistribution_datagrid.datagrid('unselectAll');
            oilcarddistribution_datagrid.datagrid('selectRow',rowIndex);
            var rowData = oilcarddistribution_datagrid.datagrid('getSelected');
            oilcarddistribution_datagrid.datagrid('unselectRow',rowIndex);
            showDialog_view(rowData);
            return;
        }
		//选中的所有行
		var rows = oilcarddistribution_datagrid.datagrid('getSelections');
		//选中的行（第一次选择的行）
		var row = oilcarddistribution_datagrid.datagrid('getSelected');
		if (row){
			if(rows.length>1){
				row = rows[rows.length-1];
				eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
			}
			showDialog_view(row);
		}else{
			eu.showMsg("请选择要操作的对象！");
		}
	}
	
	//删除
	function del(rowIndex){
        var rows = new Array();
        var tipMsg =  "您确定要删除选中的所有行？";
        if(rowIndex != undefined) {
            oilcarddistribution_datagrid.datagrid('unselectAll');
            oilcarddistribution_datagrid.datagrid('selectRow',rowIndex);
            var rowData = oilcarddistribution_datagrid.datagrid('getSelected');
            rows[0] = rowData;
            oilcarddistribution_datagrid.datagrid('unselectRow',rowIndex);
            tipMsg =  "您确定要删除？";
        }else{
		    rows = oilcarddistribution_datagrid.datagrid('getSelections');
        }

		if(rows.length >0){
			$.messager.confirm('确认提示！',tipMsg,function(r){
				if (r){
                    var ids = new Array();
                    $.each(rows,function(i,row){
                        ids[i] = row.id;
                    });
                    $.ajax({
                        url:'${ctx}/sys/oilcarddistribution/_remove',
                        type:'post',
                        data: {ids:ids},
                        traditional:true,
                        dataType:'json',
                        success:function(data) {
                            if (data.code==1){
                                oilcarddistribution_datagrid.datagrid('load');	// reload the user data
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
		oilcarddistribution_datagrid.datagrid('load',$.serializeObject(oilcarddistribution_search_form));
	}
		
	//导出Excel
	function exportExcel(){
		$('#oilcarddistribution_temp_iframe').attr('src','${ctx}/sys/oilcarddistribution/exportExcel');
	}
	
	function importFormInit(){
		oilcarddistribution_import_form = $('#oilcarddistribution_import_form').form({
			url: '${ctx}/sys/oilcarddistribution/importExcel',
			onSubmit: function(param){  
				$.messager.progress({
					title : '提示信息！',
					text : '数据处理中，请稍后....'
				});
		        return $(this).form('validate');
		    },
			success: function(data){
				$.messager.progress('close');
				var json = $.parseJSON(data);
				if (json.code ==1){
					oilcarddistribution_import_dialog.dialog('destroy');//销毁对话框 
					oilcarddistribution_datagrid.datagrid('reload');//重新加载列表数据
					eu.showMsg(json.msg);//操作结果提示
				}else {
					eu.showAlertMsg(json.msg,'error');
				}
			}
		});
	}
	
	//导入
	function importExcel(){
		oilcarddistribution_import_dialog = $('<div/>').dialog({//基于中心面板
			title:'Excel导入',
            top:20,
			width : 500,
			modal : true,
			maximizable:true,
			href : '${ctx}/sys/oilcarddistribution/import',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					oilcarddistribution_import_form.submit();
				}
			},{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					oilcarddistribution_import_dialog.dialog('destroy');
				}
			}],
			onClose : function() {
                oilcarddistribution_import_dialog.dialog('destroy');
			},
			onLoad:function(){
				importFormInit();
			}
		}).dialog('open');
	}
</script>

<%-- 隐藏iframe --%>
<iframe id="oilcarddistribution_temp_iframe" style="display: none;"></iframe>
<%-- 列表右键 --%>
<div id="oilcarddistribution_datagrid_menu" class="easyui-menu" style="width:120px;display: none;">
    <div onclick="showDialog();" iconCls="icon-add">新增</div>
    <div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
    <div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
    <div onclick="exportExcel();" data-options="iconCls:'icon-edit'">Excel导出</div>
    <div onclick="importExcel();" data-options="iconCls:'icon-edit'">Excel导入</div>
</div>
<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">
    <div data-options="region:'north',title:'过滤条件',collapsed:false,split:false,border:false"
         style="padding: 0px; height: 56px;width:100%; overflow-y: hidden;">
        <form id="oilcarddistribution_search_form" style="padding: 0px;">
            附属卡号:<input type="text" name="filter_LIKES_oilCard_cardSn" maxLength="25"
                      onkeydown="if(event.keyCode==13)search()" style="width: 160px" />
            <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search',onClick:search">查询</a>
            <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-no'" onclick="javascript:oilcarddistribution_search_form.form('reset');">重置查询</a>
        </form>
    </div>
    <%-- 中间部分 列表 --%>
    <div data-options="region:'center',split:false,border:false"
         style="padding: 0px; height: 100%;width:100%; overflow-y: hidden;">
        <table id="oilcarddistribution_datagrid"></table>
    </div>
</div>