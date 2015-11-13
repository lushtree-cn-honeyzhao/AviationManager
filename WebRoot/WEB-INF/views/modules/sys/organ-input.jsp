<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
    var organType_combobox;
    var $_parent_combotree;
    var organTypeUrl = '${ctx}/sys/organ/organTypeCombobox?selectType=select';
    $(function() {
        loadParent();
        loadType();
        if("${model.id}"==""){  //新增
            setSortValue();
            $("input[name=status]:eq(0)").attr("checked",'checked');//状态 初始化值
        }
    });
    //加载父级机构
    function loadParent(){
        $_parent_combotree = $('#_parentId').combotree({
            url:'${ctx}/sys/organ/parentOrgan?selectType=select',
            multiple:false,//是否可多选
            editable:false,//是否可编辑
            width:120,
            valueField:'id',
            onHidePanel:function(){
                //防止自关联
                if($('#id').val() && $(this).combotree('getValue') == $('#id').val()){
                    eu.showMsg('不允许设置上级机构为自己,请重新选择!');
                    $(this).combotree('setValue','');
                }
            },
            onBeforeLoad:function(node,param){
                param.id = "${id}";
            },
            onSelect:function(node){
                //上级机构类型 机构：0 部门：1  小组：2
                var parentType = node.attributes.type;
                if(parentType != undefined){
                    organType_combobox.combobox('reload',organTypeUrl+"&parentOrganType="+parentType);
                }
            }

        });
    }

    //加载机构类型
    function loadType(){
        organType_combobox = $('#type').combobox({
            url:organTypeUrl+"&parentOrganType=${parentOrganType}",
            multiple:false,//是否可多选
            editable:false,//是否可编辑
            width:120,
            validType:['comboboxRequired[\'#type\']']
        });
    }

    //设置排序默认值
    function setSortValue() {
        $.get('${ctx}/sys/organ/maxSort', function(data) {
            if (data.code == 1) {
                $('#orderNo').numberspinner('setValue',data.obj+1);
            }
        }, 'json');
    }
</script>
<div>
    <form id="organ_form" method="post">
        <input type="hidden" id="id" name="id" />
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
            <label>上级机构:</label>
            <input id="_parentId" name="_parentId" />
        </div>
        <div>
            <label>机构类型:</label>
            <input id="type" name="type" class="easyui-combobox"
                   data-options="required:true,missingMessage:'请选择机构类型.'" />
            <%--提示小图标--%>
            <span class="tree-icon tree-file icon-tip easyui-tooltip"
                  title="顶级机构只能为[机构(法人单位)]；上级机构的机构类型为[部门]，则机构类型只可以为[部门]或者[小组]；上级机构的机构类型为[小组]，则机构类型只可以为[小组]." ></span>
        </div>
        <div>
            <label>机构名称:</label>
            <input type="text" id="name" name="name"
                   maxLength="255" class="easyui-validatebox" placeholder="请输入机构名称..."
                   data-options="required:true,missingMessage:'请输入机构名称.',validType:['minLength[1]']" />
        </div>
        <div>
            <label>机构系统编码:</label>
            <input type="text" id="sysCode" name="sysCode"
                   maxLength="36" class="easyui-validatebox" placeholder="请输入机构系统编码..."
                   data-options="required:true,missingMessage:'请输入机构系统编码.',validType:['minLength[1]']" />
        </div>
        <div>
            <label>机构编码:</label>
            <input type="text" id="code" name="code"
                   maxLength="36" class="easyui-validatebox" placeholder="请输入机构编码..."
                   data-options="validType:['minLength[1]']" />
        </div>
        <div>
            <label>地址:</label>
            <input type="text" id="address" name="address"
                   maxLength="255" class="easyui-validatebox" placeholder="请输入地址..."
                   data-options="validType:['minLength[1]']" />
        </div>
        <div>
            <label>电话号码:</label>
            <input type="text" id="phone" name="phone"
                   class="easyui-validatebox" placeholder="请输入地址..."
                   data-options="validType:['phone']" />
        </div>
        <div>
            <label>传真:</label>
            <input type="text" id="fax" name="fax"
                   class="easyui-validatebox" placeholder="请输入传真..."/>
        </div>
        <div>
            <label>排序:</label>
            <input type="text" id="orderNo" name="orderNo" class="easyui-numberspinner"
                   data-options="min:1,max:99999999,size:9,maxlength:9" />
        </div>
        <div>
            <label>状态:</label>
            <input type="radio" name="status" style="width: 20px;" value="0" /> 启用
            <input type="radio" name="status" style="width: 20px;" value="3" /> 停用
        </div>
    </form>
</div>