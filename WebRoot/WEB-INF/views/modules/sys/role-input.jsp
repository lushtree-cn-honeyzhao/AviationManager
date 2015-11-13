<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
</script>
<div>
    <form id="role_form" method="post" novalidate>
        <input type="hidden"  name="id" />
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
            <label>角色名称:</label>
            <input name="name" type="text" class="easyui-validatebox"
                   maxLength="100" data-options="required:true,missingMessage:'请输入角色名称.',validType:['minLength[1]','legalInput']">
        </div>
        <div>
            <label>角色编码:</label>
            <input name="code" type="text" class="easyui-validatebox"
                   maxLength="36" >
        </div>
        <div>
            <label style="vertical-align: top;">描述:</label>
            <textarea maxLength="255" name="remark"
                      style="position: relative; resize: none; height: 75px; width: 260px;"></textarea>
        </div>
    </form>
</div>