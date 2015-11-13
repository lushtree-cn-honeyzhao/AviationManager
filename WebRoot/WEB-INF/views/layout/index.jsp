<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>后台管理主界面</title>
<%@ include file="/common/meta.jsp"%>
<script type="text/javascript" src="${ctxStatic}/js/session-keep.js" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8">
        var indexLayout;
        var tabHtml;
        $(function(){
            indexLayout = $("#indexLayout").layout();
            var $tabs = $('.easyui-tabs').tabs();
            // add a new tab panel
            $tabs.tabs('add',{
                title:'New Tab',
                content:'Tab Body',
                closable:true,
                tools:[{
                    iconCls:'icon-mini-refresh',
                    handler:function(){
                        alert('refresh');
                    }
                }]
            });
            $tabs.tabs('add',{
                title:'New Tab2',
                content:'Tab Body',
                closable:true,
                tools:[{
                    iconCls:'icon-mini-refresh',
                    handler:function(){
                        alert('refresh');
                    }
                }]
            });

            tabHtml = $tabs.html();
            $.messager.show({
                title:'代办任务',
                width:"auto",
                height:"auto",
                msg:tabHtml,
                timeout:0,
                showType:'show'
            });
            eu.destroyMessage();
//            $(".messager-body").window('destroy');
        });
    </script>
</head>
<div class="easyui-tabs" data-options="tabWidth:112" style="width:700px;height:250px">
</div>
<script type="text/javascript" charset="utf-8">
    var tabHtml = $(".easyui-tabs").html();
</script>
<body id="indexLayout" class="easyui-layout" style="height: 100%;width: 100%;overflow-y: hidden;">
	<noscript>
		<div style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="${ctxStatic}/img/noscript.gif" alt='请开启脚本支持!' />
		</div>
	</noscript>
	<%-- north顶部Logo区域 --%>
	<div data-options="region:'north',border:false,split:false,href:'${ctx}/common/layout/north'"
		style="height: 72px;overflow: hidden;">
	</div>

	<%-- west菜单栏 --%>
	<div data-options="region:'west',title:'导航菜单',split:false,href:'${ctx}/common/layout/west'"
		style="width: 160px;overflow: hidden;">
	</div>

	<%-- center主面板 --%>
	<div data-options="region:'center',split:false,href:'${ctx}/common/layout/center'" style="overflow: hidden;">
	</div>
	
	<%-- south底部 --%>
	<div data-options="region:'south',border:false,split:false,href:'${ctx}/common/layout/south'"
		style="height: 20px;overflow: hidden;">
	</div>
</body>
</html>