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

});
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
        },
        onSelect:function(record){
             $("#title").css({'color':record.value});
        } ,
        onLoadSuccess:function(){
            $("#title").css({'color':$(this).combobox('getValue')});
        }
    });
}
</script>
<div>
    <form id="bug_form" method="post" novalidate>
	    <input type="hidden"  name="id"/>
	    <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version"/>
        <div>
			<label>类型:</label>
            <e:dictionary id="_type" code="<%=DictionaryUtils.DIC_BUG%>" type="combobox" name="type" selectType="select" validType="'comboboxRequired[\\'#_type\\']'"></e:dictionary>
		</div>
		<div>
			<label>标题:</label>
		    <input id="title" name="title" type="text" class="easyui-validatebox"
				maxLength="100" data-options="required:true,missingMessage:'请输入标题.',validType:['minLength[1]','legalInput']">
            <input id="color" name="color" type="text" class="easyui-combobox" >
		</div>
        <table style="border: 0px;width: 100%;">
            <tr>
                <td style="display: inline-block; width: 96px; vertical-align: top;">内容:</td>
                <td><textarea id="content_kindeditor" name="content" ></textarea></td>
            </tr>
        </table>
	</form>
</div>