<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	$(function() {
        loadGroup();
	});
	//加载分组
	function loadGroup(){
        $('#_parentId').combobox({
            url:'${ctx}/sys/dictionary-type/group_combobox?selectType=select',
            multiple:false,//是否可多选
            editable:false,//是否可编辑
            width:120,
            valueField:'value',
            textField:'text',
            onHidePanel:function(){
                //防止自关联
                if($('#id').val() != undefined && $(this).combobox('getValue') == $('#code').val()){
                    $(this).combobox('setValue','');
                }
            }
        });
	}


</script>
<div>
	<form id="dictionaryType_form" method="post">
		<input type="hidden" id="id" name="id" />
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
	    <div>
			<label>分组类型:</label>
			<input id="_parentId" name="groupDictionaryTypeCode" class="easyui-combobox" />
		</div>
		<div>
			<label>类型名称:</label>
			<input type="text" id="name" name="name"  maxLength="20"
				class="easyui-validatebox"   placeholder="请输入类型名称..."
				data-options="required:true,missingMessage:'请输入类型名称.'" />
		</div>
		<div>
			<label>类型编码:</label>
			<input type="text" id="code" name="code"
				maxLength="36" class="easyui-validatebox" placeholder="请输入类型编码..."
				data-options="required:true,missingMessage:'请输入类型编码.',validType:['minLength[1]']" />
		</div>
		<div>
			<label>排序:</label>
			<input type="text" id="orderNo" name="orderNo" class="easyui-numberspinner"
                   data-options="min:1,max:99999999,size:9,maxlength:9" />
		</div>
        <div>
            <label>备注:</label>
            <textarea id="remark" name="remark" maxLength="255"
                      style="position: relative;resize: none;height: 75px;width: 350px;"/>
        </div>
	</form>
</div>