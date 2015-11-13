<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div>
	<form id="log_keepTime_form" method="post" novalidate>
        <div>
            <label>保留时间（天）:</label>
            <input name="keepTime" value="${keepTime}" class="easyui-numberspinner"
                   data-options="required:true,missingMessage:'请输入保留时间.', min: 0,max: 999999999,">
        </div>
    </form>
</div>