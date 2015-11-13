<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/meta.jsp"%>
<script type="text/javascript">
var dictionary_datagrid;
var editRow = undefined;
var editRowData = undefined;
var dictionary_search_form;
var dictionaryTypeCode = undefined;
var dictionary_filter_EQS_dictionaryType__code;
$(function() {
	dictionary_search_form = $('#dictionary_search_form').form();
    //数据列表
    dictionary_datagrid = $('#dictionary_datagrid').datagrid({  
	    url:'${ctx}/sys/dictionary/datagrid',
        fit:true,
	    pagination:true,//底部分页
	    pagePosition:'bottom',//'top','bottom','both'.
	    fitColumns:false,//自适应列宽
	    striped:true,//显示条纹
	    pageSize:20,//每页记录数
	    singleSelect:false,//单选模式
	    rownumbers:true,//显示行数
	    checkbox:true,
		nowrap : true,
		border : false,
        remoteSort:false,//是否通过远程服务器对数据排序
		sortName:'orderNo',//默认排序字段
		sortOrder:'asc',//默认排序方式 'desc' 'asc'
		idField : 'id',
        frozenColumns:[[
            {field : 'ck',checkbox : true,width : 60},{
                field : 'dictionaryTypeCode',
                title : '字典类型',
                width : 150,
                formatter : function(value, rowData, rowIndex) {
                    return rowData.dictionaryTypeName;
                },
                editor : {
                    type : 'combobox',
                    options : {
                        url:'${ctx}/sys/dictionary-type/combobox',
                        required : true,
                        missingMessage:'请选择字典类型(如果不存在,可以选择[字典类型管理]按钮,添加字典类型)！',
                        editable:false,//是否可编辑
                        groupField:'group',
                        onSelect:function(record){
                            dictionaryTypeCode = record.value;
                            var dictionaryTypeEditor = dictionary_datagrid.datagrid('getEditor',{index:editRow,field:'parentDictionaryCode'});
                            $(dictionaryTypeEditor.target).combotree('clear').combotree('reload');
                            var codeEditor = dictionary_datagrid.datagrid('getEditor',{index:editRow,field:'code'});
                            var vallueEditor = dictionary_datagrid.datagrid('getEditor',{index:editRow,field:'value'})
                            $(codeEditor.target).val(dictionaryTypeCode);
                            $(vallueEditor.target).val(dictionaryTypeCode);
                            $(codeEditor.target).validatebox('validate');
                        }
                    }
                }
            }, {
                field : 'name',
                title : '名称',
                width : 200,
                editor : {
                    type : 'validatebox',
                    options : {
                        required : true,
                        missingMessage:'请输入名称！',
                        validType:['minLength[1]','length[1,64]','legalInput']
                    }
                }
            }
        ]],
		columns:[ [
            {field : 'id',title : '主键',hidden : true,sortable:true,align : 'right',width : 80},{
                field : 'parentDictionaryCode',
                title : '上级节点',
                width : 200,
                formatter : function(value, rowData, rowIndex) {
                    return rowData.parentDictionaryName;
                },
                editor : {
                    type : 'combotree',
                    options : {
                        url:'${ctx}/sys/dictionary/combotree?selectType=select',
                        onBeforeLoad:function(node,param){
                            if(dictionaryTypeCode != undefined){
                                param.dictionaryTypeCode = dictionaryTypeCode;
                            }
                            if(editRowData != undefined){
                                param.id = editRowData.id;
                            }
                        }
                    }
                }
            },{
				field : 'code',
				title : '编码',
				width : 100,
				sortable:true,
				editor : {
					type : 'validatebox',
					options : {
						required : true,
						missingMessage:'请输入编码！',
						validType:['minLength[1]','length[1,36]','legalInput']
					}
				}
			},{
                field : 'value',
                title : '属性值',
                width : 120,
                sortable:true,
                editor : {
                    type : 'validatebox',
                        options : {
                        }
                }
            }, {
				field : 'remark',
				title : '备注',
				width : 200,
				editor : {
					type : 'text',
					options : {
					}
				}
			}, {
				field : 'orderNo',
				title : '排序',
				align : 'right',
				width : 60,
				sortable:true,
				editor : {
					type : 'numberspinner',
					options : {
						required : true
					}
				}
			}] ],
            toolbar:[{
                text:'新增',
                iconCls:'icon-add',
                handler:function(){add()}
            },'-',{
                text:'编辑',
                iconCls:'icon-edit',
                handler:function(){edit()}
            },'-',{
                text:'删除',
                iconCls:'icon-remove',
                handler:function(){del()}
            },'-',{
                text:'保存',
                iconCls:'icon-save',
                handler:function(){save()}
            },'-',{
                text:'取消编辑',
                iconCls:'icon-undo',
                handler:function(){cancelEdit()}
            },'-',{
                text:'取消选中',
                iconCls:'icon-undo',
                handler:function(){cancelSelect()}
            }],
            onDblClickRow : function(rowIndex, rowData) {
				if (editRow != undefined) {
					eu.showMsg("请先保存正在编辑的数据！");
					//dictionary_datagrid.datagrid('endEdit', editRow);
				}else{
					$(this).datagrid('beginEdit', rowIndex);
					$(this).datagrid('unselectAll');
                    bindCodeEvent(rowIndex);
				}
			},
			onBeforeEdit:function(rowIndex, rowData) {
                editRow = rowIndex;
				editRowData = rowData;
				dictionaryTypeCode = rowData.dictionaryTypeCode;
            },
			onAfterEdit : function(rowIndex, rowData, changes) {
                debugger;
				$.messager.progress({
					title : '提示信息！',
					text : '数据处理中，请稍后....'
				});
				var inserted = dictionary_datagrid.datagrid('getChanges', 'inserted');
				var updated = dictionary_datagrid.datagrid('getChanges', 'updated');
				if (inserted.length < 1 && updated.length < 1) {
					editRow = undefined;
					editRowData = undefined;
					$(this).datagrid('unselectAll');
                    $.messager.progress('close');
                    eu.showMsg("数据未更新!");
					return;
				}
				$.post('${ctx}/sys/dictionary/_save',rowData,
						function(data) {
					$.messager.progress('close');
					if (data.code == 1) {
						dictionary_datagrid.datagrid('acceptChanges');
						cancelSelect();
						dictionary_datagrid.datagrid('reload');
						eu.showMsg(data.msg);
					}else{// 警告信息
						$.messager.alert('提示信息！', data.msg, 'warning',function(){
							dictionary_datagrid.datagrid('beginEdit', editRow);
							if(data.obj){//校验失败字段 获取焦点
								var validateEdit = dictionary_datagrid.datagrid('getEditor',{index:rowIndex,field:data.obj});
								$(validateEdit.target).focus();
							}
						});
					}
			    }, 'json');
			},
			onLoadSuccess:function(data){
				$(this).datagrid('clearSelections');//取消所有的已选择项
		    	$(this).datagrid('unselectAll');//取消全选按钮为全选状态
				editRow = undefined;
				editRowData = undefined;
				dictionaryTypeCode = undefined;

            },
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#dictionary_menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		}).datagrid('showTooltip');

    dictionary_filter_EQS_dictionaryType__code = $('#filter_EQS_dictionaryType__code').combobox({
    	url:'${ctx}/sys/dictionary-type/combobox?selectType=all',
	    multiple:false,//是否可多选
	    editable:false,//是否可编辑
	    width:120,
        groupField:'group'
    });
});

    //字典编码 editor绑定change事件
    function bindCodeEvent(rowIndex){
        // 绑定事件监听
        var codeEditor = dictionary_datagrid.datagrid('getEditor', {index:rowIndex,field:'code'});
        var valueEditor =  dictionary_datagrid.datagrid('getEditor', {index:rowIndex,field:'value'});
        codeEditor.target.bind('change', function(){
            $(valueEditor.target).val($(this).val())
        });
    }
    //字典类型管理
    function dictionaryType(){
    	//parent.layout_center_tabs 指向父级layout_center_tabs选项卡(center.jsp)
    	eu.addTab(parent.layout_center_tabs,"字典类型管理","${ctx}/sys/dictionary-type",true,"icon-folder");
    }
    
	//设置排序默认值
	function setSortValue(target) {
		$.get('${ctx}/sys/dictionary/maxSort', function(data) {
			if (data.code == 1) {
				$(target).numberbox({value:data.obj + 1});
				$(target).numberbox('validate');
			}
		}, 'json');
	}

	//新增
	function add() {
		if (editRow != undefined) {
			eu.showMsg("请先保存正在编辑的数据！");
			//结束编辑 自动保存
			//dictionary_datagrid.datagrid('endEdit', editRow);
		}else{
			cancelSelect();
			var row = {id : ''}; 
			dictionary_datagrid.datagrid('appendRow', row);
			editRow = dictionary_datagrid.datagrid('getRows').length - 1;
			dictionary_datagrid.datagrid('selectRow', editRow);
			dictionary_datagrid.datagrid('beginEdit', editRow);
			var rowIndex = dictionary_datagrid.datagrid('getRowIndex',row);//返回指定行的索引
			var sortEdit = dictionary_datagrid.datagrid('getEditor',{index:rowIndex,field:'orderNo'});
			setSortValue(sortEdit.target);
            bindCodeEvent(rowIndex);
		}
	}

	//编辑
	function edit() {
		//选中的所有行
		var rows = dictionary_datagrid.datagrid('getSelections');
		//选中的行（第一次选择的行）
		var row = dictionary_datagrid.datagrid('getSelected');
		if (row){
			if(rows.length>1){
				row = rows[rows.length-1];
				eu.showMsg("您选择了多个操作对象，默认操作最后一次被选中的记录！");
			}
			if (editRow != undefined) {
				eu.showMsg("请先保存正在编辑的数据！");
				//结束编辑 自动保存
				//dictionary_datagrid.datagrid('endEdit', editRow);
			}else{
				editRow = dictionary_datagrid.datagrid('getRowIndex', row);
				dictionary_datagrid.datagrid('beginEdit', editRow);
				cancelSelect();
                bindCodeEvent(editRow);
			}
		} else {
			if(editRow != undefined){
				eu.showMsg("请先保存正在编辑的数据！");
			} else{
			    eu.showMsg("请选择要操作的对象！");
			}
		}
	}

	//保存
	function save(rowData) {
		if (editRow != undefined) {
			dictionary_datagrid.datagrid('endEdit', editRow);
		} else {
			eu.showMsg("请选择要操作的对象！");
		}
	}
	
	//取消编辑
	function cancelEdit() {
		cancelSelect();
		dictionary_datagrid.datagrid('rejectChanges');
		editRow = undefined;
		editRowData = undefined;
		dictionaryTypeCode = undefined;
	}
	//取消选择
	function cancelSelect() {
		dictionary_datagrid.datagrid('unselectAll');
	}

	//删除
	function del() {
		var rows = dictionary_datagrid.datagrid('getSelections');
		if (rows.length > 0) {
			if(editRow != undefined){
				eu.showMsg("请先保存正在编辑的数据！");
				return;
			}
			$.messager.confirm('确认提示！', '您确定要删除当前选中的所有行？', function(r) {
				if (r) {
                    var ids = new Array();
                    $.each(rows,function(i,row){
                        ids[i] = row.id;
                    });
                    $.ajax({
                        url:'${ctx}/sys/dictionary/remove',
                        type:'post',
                        data: {ids:ids},
                        traditional:true,
                        dataType:'json',
                        success:function(data) {
                            if (data.code==1){
                                dictionary_datagrid.datagrid('clearSelections');//取消所有的已选择项
                                dictionary_datagrid.datagrid('load');//重新加载列表数据
                                eu.showMsg(data.msg);//操作结果提示
                            } else {
                                eu.showAlertMsg(data.msg,'error');
                            }
                        }
                    });
				}
			});
		} else {
			eu.showMsg("请选择要操作的对象！");
		}
	}

	//搜索
	function search() {
		dictionary_datagrid.datagrid('load',$.serializeObject(dictionary_search_form));
	}
</script>
<%-- 列表右键 --%>
<div id="dictionary_menu" class="easyui-menu" style="width:120px;display: none;">
    <%--<div onclick="dictionaryType();" data-options="iconCls:'icon-folder'">字典类型管理</div>--%>
    <div onclick="add();" data-options="iconCls:'icon-add'">新增</div>
    <div onclick="edit();" data-options="iconCls:'icon-edit'">编辑</div>
    <div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div class="easyui-layout" fit="true" style="margin: 0px;border: 0px;overflow: hidden;width:100%;height:100%;">

    <div data-options="region:'north',title:'过滤条件',collapsed:false,split:false,border:false"
         style="padding: 0px; height: 56px;width:100%; overflow-y: hidden;">
        <span style="display: none;">&nbsp;</span><%--兼容IE8--%>
        <form id="dictionary_search_form" style="padding: 0px;">
            &nbsp;字典分组:<select id="filter_EQS_dictionaryType__code" name="filter_EQS_dictionaryType__code" class="easyui-combobox" ></select>
            名称或编码: <input type="text" id="filter_LIKES_name_OR_code" name="filter_LIKES_name_OR_code" placeholder="请输入名称或编码..."
                          onkeydown="if(event.keyCode==13)search()" maxLength="25" style="width: 160px"></input>
            <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-search',onClick:search">查询</a>
            <a class="easyui-linkbutton" href="#" data-options="iconCls:'icon-no'" onclick="javascript:dictionary_search_form.form('reset');">重置查询</a>
        </form>
    </div>

	<%-- 中间部分 列表 --%>
	<div data-options="region:'center',split:false,border:false" 
		style="padding: 0px; height: 100%;width:100%; overflow-y: hidden;">
	   <table id="dictionary_datagrid" ></table>

	</div>
</div>