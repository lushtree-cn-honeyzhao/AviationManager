<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<div>
	<div align="center">
	    <h3 style="color:${model.color} ">${model.title}</h3>
	</div>
	<div align="right">
	   [ ${model.createUser} 发布于<fmt:formatDate value="${model.createTime}" type="both" /> ]
	</div>
	<hr>
	<div>${model.content}</div>
</div>