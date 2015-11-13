<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$(function() {  
    //数据列表
    $('#bug_datagrid').datagrid({  
	    url:'${ctx}/sys/bug/datagrid',
	    pagination:false,//底部分页
	    rownumbers:false,//显示行数
	    fitColumns:true,//自适应列宽
	    striped:true,//显示条纹
	    singleSelect:true,
        showHeader:false,
	    pageSize:10,//每页记录数
	    sortName:'id',//默认排序字段
		sortOrder:'asc',//默认排序方式 'desc' 'asc'
		idField : 'id',
		frozenColumns:[[ 
              {field:'title',title:'bug标题',width:600,formatter:function(value,rowData,rowIndex){
            	     var url = $.formatString('${ctx}/sys/bug/view?id={0}',rowData.id);
            	     var title = $.formatString("<a style='color:{0}' href='javascript:eu.addTab(window.parent.layout_center_tabs, \"{1}\",\"{2}\", true)' >{3}</a>",
                             rowData.color,value,url, value);
            	     return title;
                  }
              }
		]],
	    onLoadSuccess:function(){
	    	//鼠标移动提示列表信息tooltip
			$(this).datagrid('showTooltip');
		}
	});
	
});

</script>
<div style="width: 100%;height: 100%">
<table id="bug_datagrid" fit="true"></table>
</div>