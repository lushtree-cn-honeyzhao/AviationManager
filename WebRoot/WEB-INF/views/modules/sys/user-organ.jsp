<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
    var organs_combotree;
    $(function() {
        loadOrgan();
        initdDefaultOrgan();
    });

    //加载默认组织机构
    var defaultOrgan_combobox;
    function initdDefaultOrgan(){
        defaultOrgan_combobox =  $("#defaultOrganId").combobox({
            editable:false,
            data:${defaultOrganComboboxData}
        });
    }


    function loadOrgan(){
        var isChange = false; //标识所属组织机构是否变更
        organs_combotree = $("#organIds").combotree({
            url:'${ctx}/sys/organ/parentOrgan',
            multiple:true,//是否可多选
            editable:false,
            cascadeCheck:false,
            onHidePanel:function(){
                if(isChange){
                    var selectionsNodes = $(this).combotree("tree").tree("getChecked");
                    var defaultOrganData = new Array();
                    var defaultOrganValues = defaultOrgan_combobox.combobox("getValues");
                    var defaultOrganTempValues = new Array();
                    $.each(selectionsNodes,function(index, node) {
                        defaultOrganData.push({"value":node.id,"text":node.text});
                        $.each(defaultOrganValues,function(index, value) {
                            if(node.id == value){
                                defaultOrganTempValues.push(value);
                            }
                        });
                    });
                    defaultOrgan_combobox.combobox("setValues",defaultOrganTempValues);
                    defaultOrgan_combobox.combobox("loadData",defaultOrganData);
                }
            },
            onChange:function(newValue, oldValue){
                if((newValue.length != 0 || oldValue.length != 0) && newValue.toString() != oldValue.toString()){
                    isChange = true;
                }else{
                    isChange = false;
                }
            }
        });
    }
</script>
<div>
    <form id="user_organ_form"  method="post" novalidate>
        <input type="hidden" id="id" name="id" />
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
            <label>所属组织机构:</label>
            <input type="select" id="organIds" name="organIds"  style="width: 260px;"/>
        </div>
        <div>
            <label>默认组织机构:</label>
            <input type="select" id="defaultOrganId" name="defaultOrganId" style="width: 260px;"/>
        </div>
    </form>
</div>