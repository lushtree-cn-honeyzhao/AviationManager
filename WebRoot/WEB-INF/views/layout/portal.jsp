<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
.title{
	font-size:16px;
	font-weight:bold;
	padding:20px 10px;
	background:#eee;
	overflow:hidden;
	border-bottom:1px solid #ccc;
}
</style>
<script type="text/javascript" charset="utf-8">
	$(function() {
		panels = [ {
			id : 'p1',
			title : '内部新闻',
			height : 360,
			collapsible : true,
			href:'${ctx}/common/layout/portal-bug'
		}, {
			id : 'p2',
			title : '技术架构',
			height : 360,
			collapsible : true,
			href : '${ctx}/common/layout/portal-component'
		}];

		 $('#layout_portal_portal').portal({
			border : false,
			fit : true,
			onStateChange : function() {
				$.cookie('portal-state', getPortalState(), {
					expires : 7
				});
            }
		});
		var state = $.cookie('portal-state');
		if (!state) {
			state = 'p1:p2';/*冒号代表列，逗号代表行*/
		}
		addPortalPanels(state);
		$('#layout_portal_portal').portal('resize');

	});

	function getPanelOptions(id) {
		for ( var i = 0; i < panels.length; i++) {
			if (panels[i].id == id) {
				return panels[i];
			}
		}
		return undefined;
	}
	function getPortalState() {
		var aa=[];
		for(var columnIndex=0;columnIndex<2;columnIndex++) {
			var cc=[];
			var panels=$('#layout_portal_portal').portal('getPanels',columnIndex);
			for(var i=0;i<panels.length;i++) {
				cc.push(panels[i].attr('id'));
			}
			aa.push(cc.join(','));
		}
		return aa.join(':');
	}
	function addPortalPanels(portalState) {
		var columns = portalState.split(':');
		for (var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
			var cc = columns[columnIndex].split(',');
			for (var j = 0; j < cc.length; j++) {
				var options = getPanelOptions(cc[j]);
				if (options) {
					var p = $('<div/>').attr('id', options.id).appendTo('body');
					p.panel(options);
					$('#layout_portal_portal').portal('add', {
						panel : p,
						columnIndex : columnIndex
					});
				}
			}
		}
	}
</script>
<div class="easyui-layout" fit="true" style="width:100%;height:100%;overflow: hidden;">
	<div region="center" style="height: 100%;overflow: hidden;">
		<div id="layout_portal_portal"
			style="position: relative;width:100%;height:100%; overflow-x: hidden;">
			<div></div>
			<div></div>
		</div>	
	</div>
	<div  style="height: 100%;width:200px;overflow: hidden;"
          data-options="region:'east',title:'日历',href:'${ctx}/common/layout/portal-east'">
	</div>
</div>

