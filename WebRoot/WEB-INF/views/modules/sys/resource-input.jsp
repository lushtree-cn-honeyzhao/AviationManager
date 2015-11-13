<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
    var $type_combobox;
    var typeUrl = "${ctx}/sys/resource/resourceTypeCombobox";
	$(function() {
		loadParent();
		loadIco();
        loadType();
        if("${model.id}"==""){  //新增
            setSortValue();
            $("input[name=status]:eq(0)").attr("checked",'checked');//状态 初始化值
        }
	});

	//加载父级资源
	function loadParent(){
		$('#_parentId').combotree({
	        url:'${ctx}/sys/resource/parentResource?selectType=select',
		    multiple:false,//是否可多选
		    editable:false,//是否可编辑
		    width:120,
	        valueField:'id',
	        onHidePanel:function(){
	        	//防止自关联
	        	if($('#id').val() && $(this).combotree('getValue') == $('#id').val()){
                    eu.showMsg('不允许设置上级资源为自己,请重新选择!');
	        		$(this).combotree('setValue','');
	        	}
	        },
            onBeforeLoad:function(node,param){
                param.id = "${id}";
            },
            onSelect:function(node){
                //上级资源类型 菜单：0 功能：1  限制:如果上级是功能则下级只能是功能
                var parentType = node.attributes.type;
                if(parentType != undefined ){
                    $type_combobox.combobox('reload',typeUrl+"?parentType="+parentType);
                }
            }

		});
	}
	//加载资源图标
	function loadIco(){
		$('#iconCls').combobox({
			url:'${ctxStatic}/js/json/resource.json',
		    multiple:false,//是否可多选
		    editable:false,//是否可编辑
		    width:120,
	        formatter:function(row){    
	        	return $.formatString('<span class="tree-icon tree-file {0}"></span>{1}', row.value, row.text);
	        }
		});
	}
    //加载资源类型
    function loadType(){
        $type_combobox = $('#type').combobox({
            url:typeUrl+'?parentType=${parentType}',
            multiple:false,//是否可多选
            editable:false,//是否可编辑
            width:120,
            value:'0'//默认值 ‘0’即菜单
        });
    }
    //设置排序默认值
    function setSortValue() {
        $.get('${ctx}/sys/resource/maxSort', function(data) {
            if (data.code == 1) {
                $('#orderNo').numberspinner('setValue',data.obj+1);
            }
        }, 'json');
    }
</script>
<div>
    <form id="resource_form" method="post">
        <input type="hidden" id="id" name="id" />
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
            <label>资源类型:</label>
            <input id="type" name="type" class="easyui-combobox"
                   data-options="required:true,missingMessage:'请选择资源类型.'" />
            <%--提示小图标--%>
            <span class="tree-icon tree-file icon-tip easyui-tooltip"
                  title="上级资源的资源类型为[功能]，则资源类型默认为[功能]，并且不可更改." ></span>
        </div>
        <div>
            <label>上级资源:</label>
            <input id="_parentId" name="_parentId" class="easyui-combotree" />
        </div>
        <div>
            <label>资源图标:</label>
            <input id="iconCls" name="iconCls" type="text"
                   class="easyui-combobox"
                   data-options="tipPosition:'left',width:200,required:true,missingMessage:'请选择资源图标.',url:'${ctxStatic}/js/json/resource.json'" />
        </div>
        <div>
            <label>资源名称:</label>
            <input type="text" id="name" name="name"
                   maxLength="20" class="easyui-validatebox" placeholder="请输入资源名称..."
                   data-options="required:true,missingMessage:'请输入资源名称.',validType:['minLength[1]']" />
        </div>
        <div>
            <label>资源编码:</label>
            <input type="text" id="code" name="code"
                   maxLength="20" class="easyui-validatebox" placeholder="请输入资源编码..."
                   data-options="validType:['minLength[1]']" />
            <%--提示小图标--%>
            <span class="tree-icon tree-file icon-tip easyui-tooltip"
                  title="资源识别的唯一标识;主要用于[功能]类型的资源能够根据编码进行权限控制." ></span>
        </div>
        <div>
            <label style="vertical-align: top;">链接地址:</label>
            <textarea maxLength="255" name="url"
                      style="position: relative; resize: none; height: 50px; width: 260px;"></textarea>
        </div>
        <div>
            <label style="vertical-align: top;">标识地址:</label>
            <textarea maxLength="2000" name="markUrl"
                      style="position: relative; resize: none; height: 50px; width: 260px;"></textarea>
            <%--提示小图标--%>
            <span class="tree-icon tree-file icon-tip easyui-tooltip"
                  title="设置标识地址的URL会被拦截器拦截；支持通配符'*';多个标识地址之间以';'分割." ></span>
        </div>
        <div>
            <label>排序:</label>
            <input type="text" id="orderNo" name="orderNo" class="easyui-numberspinner"
                   data-options="min:1,max:99999999,size:9,maxlength:9" />
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