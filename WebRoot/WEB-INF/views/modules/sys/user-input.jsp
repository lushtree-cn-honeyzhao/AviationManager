<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
    $(function() {
        loadSex();
    });
    //性别
    function loadSex(){
        $('#sex').combobox({
            url: '${ctx}/sys/user/sexTypeCombobox?selectType=select',
            width: 120,
            editable:false,
            value:'2'
        });
    }
</script>
<div>
    <form id="user_form"  method="post" novalidate>
        <input type="hidden" id="id" name="id" />
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
            <label>登录名:</label>
            <input type="text" id="loginName" name="loginName" maxLength="36"
                   class="easyui-validatebox"
                   data-options="required:true,missingMessage:'请输入登录名.',validType:['minLength[1]','legalInput']"/>
        </div>
        <div id="password_div">
            <label>密码:</label>
            <input type="password" id="password"
                   name="password" class="easyui-validatebox" maxLength="36"
                   data-options="required:true,missingMessage:'请输入密码.',validType:['safepass']">
        </div>
        <div id="repassword_div">
            <label>确认密码:</label>
            <input type="password" id="repassword"
                   name="repassword" class="easyui-validatebox" required="true"
                   missingMessage="请再次填写密码." validType="equalTo['#password']"
                   invalidMessage="两次输入密码不匹配.">
        </div>
        <div>
            <label>姓名:</label>
            <input name="name" type="text" maxLength="6" class="easyui-validatebox" data-options="validType:['CHS','length[2,6]']" />
        </div>
        <div>
            <label>性别:</label>
            <input id="sex" name="sex" type="text" class="easyui-combobox" />
        </div>
        <div>
            <label>邮箱:</label>
            <input name="email" type="text" class="easyui-validatebox" validType="email" maxLength="255" />
        </div>
        <div>
            <label>地址:</label>
            <input name="address" type="text" class="easyui-validatebox" validType="legalInput" maxLength="255" />
        </div>
        <div>
            <label>电话:</label>
            <input name="tel" type="text" class="easyui-validatebox" validType="phone">
        </div>
        <div>
            <label>手机号:</label>
            <input name="mobilephone" type="text" class="easyui-validatebox" validType="mobile">
        </div>
        <div>
            <label>状态:</label>
            <label style="text-align: left;width: 60px;">
                <input type="radio" name="status" style="width: 20px;" value="0" /> 启用
            </label>
            <label style="text-align: left;width: 60px;">
                <input type="radio" name="status" style="width: 20px;" value="3" /> 停用
            </label>
        </div>
    </form>
</div>