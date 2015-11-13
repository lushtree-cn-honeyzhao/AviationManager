<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
</script>
<div>
	<form id="oiltrading_import_form" enctype="multipart/form-data" method="post" novalidate>
        <div>
			<label>上传文件:</label>
		    <input id="filedata" name="filedata" type="file" >
		    <!-- 导入输出日志begin -->
		    <div style="font-size:13px;font-weight: bold;color: red;">日志：</div>
		    <div style="width:100%;height:300px;">
		    	<textarea id="OutPrintLog" name="OutPrintLog" style="width:100%;height:300px;" rows="50" readonly="readonly" cols="8"></textarea>
		    </div>
		    <!-- 导入输出日志end -->
		</div>
	</form>
</div>