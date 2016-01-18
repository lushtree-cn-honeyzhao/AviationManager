<%@ page import="com.lushapp.modules.sys.utils.DictionaryUtils" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<%-- 引入kindEditor插件 --%>
<link rel="stylesheet" href="${ctxStatic}/js/kindeditor-4.1.10/themes/default/default.css">
<script type="text/javascript" src="${ctxStatic}/js/kindeditor-4.1.10/kindeditor-all-min.js" charset="utf-8"></script>
<script type="text/javascript">
var aviationOrder_datagrid;
var aviationOrder_form;
var aviationOrder_search_form;
var aviationOrder_dialog;

var aviationOrder_import_dialog;//aviationorder导入表单弹出对话框
var aviationOrder_import_form;
$(function() {
	aviationorder_form = $('#aviationOrder_form').form();
	aviationorder_search_form = $('#aviationOrder_search_form').form();
    //数据列表
    aviationOrder_datagrid = $('#aviationOrder_datagrid').datagrid({
	    url:'${ctx}/sys/aviationOrder/datagrid',
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
            {field:'ID',title:'ID',width:200,formatter:function(value,rowData,rowIndex){
                var html = $.formatString("<span style='color:{0}'>{1}</span>",rowData.color,value);
                return html;
            }}
        ]],
        columns:[[
              {field:'id',title:'主键',hidden:true,sortable:true,align:'right',width:40},
              {field:'orderSn',title:'编号',width:60 },
              {field:'createTime',title:'创建时间',width:60 },
              {field:'flightInfoCarrier',title:'航空公司',width:100 },
              {field:'passengerInfoPassengerName',title:'乘客',width:60 },
              {field:'flightInfoNumber',title:'机票号',width:60 },
              {field:'customReceivable',title:'应收',width:60 },//客户应收
              {field:'customReceivable',title:'毛利',width:60 },//毛利＝客户应收－平台应收
              {field:'custom',title:'客户',width:60 },
              {field:'pnr',title:'PNR',width:60 },
              {field:'createUser',title:'建单人',width:60 },
			  {field:'createUser',title:'-建单部门-',width:100 },
			  {field:'drawType',title:'出票方式',width:60 },
              {field:'drawPlatform',title:'出票部门',width:60 },
              {field:'lockUser',title:'锁定人',width:60 },
              {field:'orderstatusView',title:'订单状态',width:60 },
              {field:'operater',title:'操作',width:150,formatter:function(value,rowData,rowIndex){
            	  var url = $.formatString('${ctx}/sys/aviationOrder/_view?id={0}',rowData.id);
         	      var operaterHtml = "<a class='easyui-linkbutton' iconCls='icon-add'  " +
                           "onclick='view("+rowIndex+");' >查看</a>"
                  +"&nbsp;<a class='easyui-linkbutton' iconCls='icon-edit'  href='#' " +
                          "onclick='edit("+rowIndex+");' >锁定/解锁</a>"
                  +"&nbsp;<a class='easyui-linkbutton' iconCls='icon-remove'  href='#' " +
                  "onclick='del("+rowIndex+");' >删除</a>";
                  if(rowData.status==0){
                	  operaterHtml=operaterHtml+"&nbsp;<a class='easyui-linkbutton' iconCls='icon-edit'  href='#' " +
                	  "onclick='updateStatus("+rowData.id+");' >审核</a>";
                  }
                  
         	      return operaterHtml;
              }}
		    ]],
            toolbar:[{
                text:'订单创建',
                iconCls:'icon-add',
                handler:function(){showDialog()}
            },'-',{
                text:'锁定/解锁',
                iconCls:'icon-edit',
                handler:function(){edit()}
            },'-',{
                text:'删除',
                iconCls:'icon-remove',
                handler:function(){del()}
            }/*,'-',{
                text:'Excel导出',
                iconCls:'icon-edit',
                handler:function(){exportExcel()}
            } ,'-',{
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
			$('#aviationOrder_datagrid_menu').menu('show', {
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
       	aviationorder_form = $('#aviationOrder_form').form({
			url: '${ctx}/sys/aviationOrder/_save',
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
					aviationorder_dialog.dialog('destroy');//销毁对话框 
					aviationOrder_datagrid.datagrid('reload');//重新加载列表数据
					eu.showMsg(json.msg);//操作结果提示
				}else if(json.code == 2){
					$.messager.alert('提示信息！', json.msg, 'warning',function(){
						if(json.obj){
							$('#aviationorder_form input[name="'+json.obj+'"]').focus();
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
        var inputUrl = "${ctx}/sys/aviationOrder/_input";
        if(row != undefined && row.id){
            inputUrl = inputUrl+"?id="+row.id;
        }

		//弹出对话窗口
		aviationorder_dialog = $('<div/>').dialog({
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
					aviationorder_form.submit();
				}
			},{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function() {
					aviationorder_dialog.dialog('destroy');
				}
			}],
			onClose : function() {
                aviationorder_dialog.dialog('destroy');
			},
			onLoad:function(){
				formInit();
				if(row){
					aviationorder_form.form('load', row);
				}
				if(content_kindeditor){
					content_kindeditor.sync();
				}
			}
		}).dialog('open');
		
	}
	
	//显示弹出窗口 新增：row为空 编辑:row有值 
	function showDialog_view(row){
        var inputUrl = "${ctx}/sys/aviationOrder/_view";
        if(row != undefined && row.id){
            inputUrl = inputUrl+"?id="+row.id;
        }

		//弹出对话窗口
		aviationorder_dialog = $('<div/>').dialog({
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
					aviationorder_dialog.dialog('destroy');
				}
			}],
			onClose : function() {
                aviationorder_dialog.dialog('destroy');
			},
			onLoad:function(){
				formInit();
				if(row){
					aviationorder_form.form('load', row);
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
            aviationOrder_datagrid.datagrid('unselectAll');
            aviationOrder_datagrid.datagrid('selectRow',rowIndex);
            var rowData = aviationOrder_datagrid.datagrid('getSelected');
            aviationOrder_datagrid.datagrid('unselectRow',rowIndex);
            showDialog(rowData);
            return;
        }
		//选中的所有行
		var rows = aviationOrder_datagrid.datagrid('getSelections');
		//选中的行（第一次选择的行）
		var row = aviationOrder_datagrid.datagrid('getSelected');
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
            aviationOrder_datagrid.datagrid('unselectAll');
            aviationOrder_datagrid.datagrid('selectRow',rowIndex);
            var rowData = aviationOrder_datagrid.datagrid('getSelected');
            aviationOrder_datagrid.datagrid('unselectRow',rowIndex);
            showDialog_view(rowData);
            return;
        }
		//选中的所有行
		var rows = aviationOrder_datagrid.datagrid('getSelections');
		//选中的行（第一次选择的行）
		var row = aviationOrder_datagrid.datagrid('getSelected');
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
            aviationOrder_datagrid.datagrid('unselectAll');
            aviationOrder_datagrid.datagrid('selectRow',rowIndex);
            var rowData = aviationOrder_datagrid.datagrid('getSelected');
            rows[0] = rowData;
            aviationOrder_datagrid.datagrid('unselectRow',rowIndex);
            tipMsg =  "您确定要删除？";
        }else{
		    rows = aviationOrder_datagrid.datagrid('getSelections');
        }

		if(rows.length >0){
			$.messager.confirm('确认提示！',tipMsg,function(r){
				if (r){
                    var ids = new Array();
                    $.each(rows,function(i,row){
                        ids[i] = row.id;
                    });
                    $.ajax({
                        url:'${ctx}/sys/aviationOrder/_remove',
                        type:'post',
                        data: {ids:ids},
                        traditional:true,
                        dataType:'json',
                        success:function(data) {
                            if (data.code==1){
                                aviationOrder_datagrid.datagrid('load');	// reload the user data
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
	//审核
	function updateStatus(rowIndex){
        var rows = new Array();
        var tipMsg =  "您确定要审核吗？";

        $.messager.confirm('确认提示！',tipMsg,function(r){
			$.ajax({
                url:'${ctx}/sys/aviationOrder/_updateStatus',
                type:'post',
                data: {id:rowIndex},
                traditional:true,
                dataType:'json',
                success:function(data) {
                    if (data.code==1){
                        aviationOrder_datagrid.datagrid('load');	// reload the user data
                        eu.showMsg(data.msg);//操作结果提示
                    } else {
                        eu.showAlertMsg(data.msg,'error');
                    }
                }
            });
		});
	}
	//搜索
	function search(){
		aviationOrder_datagrid.datagrid('load',$.serializeObject(aviationorder_search_form));
	}
		

</script>

<%-- 隐藏iframe --%>
<iframe id="aviationOrder_temp_iframe" style="display: none;"></iframe>
<%-- 列表右键 --%>
<div id="aviationOrder_datagrid_menu" class="easyui-menu" style="width:120px;display: none;">
    <div onclick="showDialog();" iconCls="icon-add">新增</div>
    <div onclick="edit();" data-options="iconCls:'icon-edit'">锁定/解锁</div>
    <div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
    <%--<div onclick="exportExcel();" data-options="iconCls:'icon-edit'">Excel导出</div>--%>
    <%--<div onclick="importExcel();" data-options="iconCls:'icon-edit'">Excel导入</div>--%>
</div>
<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">
    <div data-options="region:'north',title:'过滤条件',collapsed:false,split:false,border:false"
         style="padding: 0px; height: 56px;width:100%; overflow-y: hidden;">
        <form id="aviationorder_search_form" style="padding: 0px;">
            附属卡号:<input type="text" name="filter_LIKES_cardSn" maxLength="25"
                      onkeydown="if(event.keyCode==13)search()" style="width: 160px" />
            <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search',onClick:search">查询</a>
            <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-no'" onclick="javascript:aviationorder_search_form.form('reset');">重置查询</a>
        </form>
    </div>
    <%-- 中间部分 列表 --%>
    <div data-options="region:'center',split:false,border:false"
         style="padding: 0px; height: 100%;width:100%; overflow-y: hidden;">
        <table id="aviationOrder_datagrid"></table>
    </div>
</div>