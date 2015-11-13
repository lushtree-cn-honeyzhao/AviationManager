<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div>
	<form id="login_password_form" method="post" novalidate>
		<input type="hidden" id="login_password_form_id" name="id" value="${sessionInfo.userId}"></input>
		<div>
			<label>原始密码:</label> <input type="password" id="password"
				name="password" class="easyui-validatebox" required="true"
				missingMessage="请输入原始密码." validType="safepass"/>
		</div>
		<div>
			<label>新密码:</label> <input type="password" id="newPassword"
				name="newPassword" class="easyui-validatebox" required="true"
				missingMessage="请输入新密码." validType="safepass" />
		</div>
		<div>
			<label>确认新密码:</label> <input type="password" name="newPassword2"
				id="newPassword2" required="true" class="easyui-validatebox"
				missingMessage="请再次输入确认新密码." validType="equalTo['#newPassword']"
				invalidMessage="两次输入密码 不一致." />
		</div>
	</form>
</div>