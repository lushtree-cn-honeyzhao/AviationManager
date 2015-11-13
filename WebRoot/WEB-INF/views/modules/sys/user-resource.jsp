<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
    var resource_combotree;
    $(function() {
        //级联点击事件
        $('#changeMode').click(function(){
            var tempData =  resource_combotree.combotree('getValues');
            resource_combotree.combotree({
                cascadeCheck:$(this).is(':checked'),
                onShowPanel:function(){
                    var tree =  $(this).combotree("tree")
                    var checkeNodes = tree.tree("getChecked");
                    var tempValues = new Array();
                    $.each(checkeNodes,function(index,nodeData){
                        tempValues.push(nodeData.id);
                    });
                    resource_combotree.combotree("setValues",tempValues);
                }
            });
            resource_combotree.combotree('setValues',tempData);
            resource_combotree.combotree("showPanel");
        });
        loadResource();
    });

    //加载资源
    function loadResource(){
        resource_combotree = $('#resourceIds').combotree({
            data : ${resourceComboboxData},
            cascadeCheck : false,
            multiple : true
        });
    }
</script>
<div>
    <form id="user_resource_form"  method="post" novalidate>
        <input type="hidden" id="id" name="id" />
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
            <label>关联资源:</label>
            <input id="resourceIds" name="resourceIds"  style="width:200px" />
            级联模式<input id="changeMode" type="checkbox" />
        </div>
    </form>
</div>