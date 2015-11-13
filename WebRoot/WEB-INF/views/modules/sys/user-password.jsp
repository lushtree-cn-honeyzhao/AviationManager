<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div>
	<form id="user_password_form" method="post">
		<input type="hidden" id="user_password_form_id" name="id" /> 
		<div>
			<label>新密码:</label> 
			<input type="password" id="newPassword" name="newPassword"
				class="easyui-validatebox" required="true" missingMessage="请输入新密码."
				validType="safepass" />
		</div>
		<div>
			<label>确认新密码:</label> 
			<input type="password" id="newPassword2" name="newPassword2"
				class="easyui-validatebox" required="true"
				missingMessage="请再次输入新密码." validType="equalTo['#newPassword']"
				invalidMessage="两次输入密码不匹配." />
		</div>
	</form>
</div>