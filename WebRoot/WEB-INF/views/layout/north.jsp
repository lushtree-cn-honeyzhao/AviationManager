<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
var login_about_dialog;
var login_password_dialog;
var login_password_form;
function showAbout(){
    //弹出对话窗口
	login_about_dialog = $('<div/>').dialog({
		title:'关于我们',
		width : 420,
		height : 220,
		modal : true,
        iconCls:'icon-essh',
		href : '${ctx}/common/layout/about',
		buttons : [{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				login_about_dialog.dialog('destroy');
			}
		}],
		onClose : function() {
            login_about_dialog.dialog('destroy');
		}
	}).dialog('open');
//  	$(".panel-title").css('text-align', 'center');
}
  
 function initLoginPasswordForm(){
   login_password_form = $('#login_password_form').form({
		url: '${ctx}/sys/user!updateUserPassword',
		onSubmit: function(param){  
	        param.upateOperate = '1';  
	        return $(this).form('validate');
	    }, 
		success: function(data){
			var json = eval('('+ data+')');  
			if (json.code == 1){
				login_about_dialog.dialog('close');	
				eu.showMsg(json.msg);//操作结果提示
			} else if(json.code == 2){
				$.messager.alert('提示信息！', json.msg, 'warning',function(){
					var userId = $('#login_password_form_id').val();
					$(this).form('clear');
					$('#login_password_form_id').val(userId);
					if(json.obj){
						$('#login_password_form input[name="'+json.obj+'"]').focus();
					}
				});
			}else {
				eu.showAlertMsg(json.msg,'error');
			}
		}
	});
}
  
   function editLoginUserPassword(){
    //弹出对话窗口
	login_about_dialog = $('<div/>').dialog({
		title:'修改用户密码',
		width : 460,
		height : 240,
		modal : true,
		href : '${ctx}/common/layout/north-password',
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				login_password_form.submit();;
			}
		},{
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				login_about_dialog.dialog('destroy');
			}
		}],
		onClose : function() {
            login_about_dialog.dialog('destroy');
		},
		onLoad:function(){
			initLoginPasswordForm();
		}
	}).dialog('open');
   }
//注销
function logout(clearCookie) {
	$.messager.confirm('确认提示！', '您确定要退出系统吗？', function(r) {
		if (r) {
            if(clearCookie){
                $.cookie('autoLogin', "", {
                    expires : 7
                });
            }
			window.location.href = "${ctx}/login/logout";
		}
	});
}
    //切换到桌面版
    function toApp(){
        var themeType_index = "app";
        $.cookie('themeType', themeType_index, {
            expires : 7
        });
        window.location.href = '${ctx}/login/index?theme='+themeType_index;
    }
</script>
<div style="height: 100%;">
    <div style="float: left;">
	    <img alt="essh" title="中斗优油" class="easyui-tooltip" data-options="position:'right'" src="${ctxStatic}/img/essh_logo.png">
	</div>
	<div style="float: right; position: absolute; bottom: 20px; right: 10px">
	    <div style="text-align: right;">您好,<span style="color: red;">${sessionInfo.loginName}</span>[${sessionInfo.roleNames}] 欢迎您！</div>
		<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_pfMenu" iconCls="icon-user_red">更换皮肤</a>
		<div id="layout_north_pfMenu" style="width: 120px; display: none;">
			<div onclick="eu.changeTheme('bootstrap');">bootstrap</div>
			<div onclick="eu.changeTheme('default');">蓝色</div>
			<div onclick="eu.changeTheme('gray');">灰色</div>
			<div onclick="eu.changeTheme('black');">黑色</div>
			<div onclick="eu.changeTheme('metro');">metro</div>
		</div>

	    <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help">控制面板</a>
		<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
			<div onclick="editLoginUserPassword();" iconCls="icon-edit">修改密码</div>
			<div class="menu-sep"></div>
            <div onclick="toApp();">切换到桌面版</div>
            <div class="menu-sep"></div>
			<div onclick="" data-options="iconCls:'icon-help'">帮助</div>
			<div onclick="showAbout();" data-options="iconCls:'icon-essh'">关于</div>
		</div>

		<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_logoutMenu" iconCls="icon-back">安全退出</a>
		<div id="layout_north_logoutMenu" style="width: 100px; display: none;">
			<div onclick="logout(true);"  data-options="iconCls:'icon-lock'">切换账号</div>
			<div onclick="logout();"  data-options="iconCls:'icon-lock'">注销</div>
		</div>
	</div>
</div>
