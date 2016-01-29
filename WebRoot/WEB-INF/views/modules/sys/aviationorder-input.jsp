<%@ page import="com.lushapp.modules.sys.utils.DictionaryUtils" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
var content_kindeditor;
$(function() {
    loadColor();
	window.setTimeout(function() {
		content_kindeditor = KindEditor.create('#content_kindeditor', {
			width : '96%',
			height : '360px',
			minWidth:'650px',//默认最小值为"650px"
			items : [ 'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak', 'anchor', 'link', 'unlink' ],
			allowFileManager : true,
			uploadJson : '${ctx}/servlet/FileUploadServlet',
			fileManagerJson : '${ctx}/servlet/FileManagerServlet',
			afterCreate:function(){ //加载完成后改变皮肤
		        var color = $('.panel-header').css('background-color');
		        $('.ke-toolbar').css('background-color',color);
		    }
		});
	}, 1);

    loadCustom();
    loadSupplier();

});

//加载客户［采购商］
function loadCustom(){
    $('#custom').combobox({
        url: '${ctx}/sys/user/sexTypeCombobox?selectType=select',
        width: 120,
        editable:false,
        value:'2'
    });
}
//加载平台［供应商］
function loadSupplier(){
    $('#supplier').combobox({
        url: '${ctx}/sys/user/sexTypeCombobox?selectType=select',
        width: 120,
        editable:false,
        value:'2'
    });
}


//加载颜色
function loadColor(){
    $('#color').combobox({
        url:'${ctxStatic}/js/json/color.json',
        multiple:false,//是否可多选
        editable:false,//是否可编辑
        width:60,
        value:'black',//默认值 黑色：black
        formatter: function(row){
            var opts = $(this).combobox('options');
            var html = '<span style="color:' + row[opts.valueField]+
                    '">' + row[opts.textField] + '</span>';
            return html;
        } ,
        onSelect:function(record){
             $("#plateNo").css({'color':record.value});
        } ,
        onLoadSuccess:function(){
            $("#plateNo").css({'color':$(this).combobox('getValue')});
        }
    });
}
</script>


<style >

form div {
    display: block;
    float: left;
    margin-bottom: 5px;
    width: 385px;
}
form div input[type="text"], form div input[type="password"] {
    width: 150px;
}
</style>
<div>
    <form id="aviationOrder_form" method="post" novalidate>
	    <input type="hidden"  name="id"/>
	    <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version"/>
        <table class="table-bordered">
            <tr>
                <td>
                    <label>订单类型:</label>
                    <input type="radio" name="orderType" style="width: 20px;" value="1" /> 国际
                    <input type="radio" name="orderType" style="width: 20px;" value="2" /> 国内
                </td>
            </tr>
            <tr>
                <td>
                    <label>PNR编号:</label>
                    <input id="pnr" data-options="required:true,missingMessage:'请输入PNR编号.',validType:['minLength[1]','legalInput']" name="pnr" type="text" class="easyui-validatebox" maxLength="100" >
                    <input type="button" value="解析" />
                    <input type="button" value="导入" />
                </td>
                <td>
                    <label>运价匹配:</label>
                    <input type="radio" name="fareMatch" style="width: 20px;" value="1" /> 外开
                    <input type="radio" name="fareMatch" style="width: 20px;" value="2" /> 自开
                </td>
                <td>
                    <label>客户</label>
                    <select name="custom" id="custom" class="easyui-validatebox" maxLength="200"  > </select>
                </td>
                <td>
                    <label>出票平台:</label>
                    <select name="supplier" id="supplier" class="easyui-validatebox" maxLength="200"  > </select>
                </td>

            </tr>

        </table>
	</form>
</div>