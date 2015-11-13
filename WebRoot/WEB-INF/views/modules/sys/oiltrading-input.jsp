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
</style>
<div>
    <form id="oiltrading_form" method="post" novalidate>
	    <input type="hidden"  name="id"/>
	    <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version"/>
		<div>
			<label>加油卡号码:</label>
		    <input id="cardSn" name="cardSn" type="text" class="easyui-validatebox"
				maxLength="50" data-options="required:true,missingMessage:'请输入加油卡号码.',validType:['minLength[1]','legalInput']">
		</div>
       	<div>
			<label>卡类型:</label>
		    <input id="cardType" data-options="required:true,missingMessage:'请输入卡类型.',validType:['minLength[1]','legalInput']" name="cardType" type="text" class="easyui-validatebox" maxLength="100" >
		</div>
		<div>
			<label>持卡人:</label>
		    <input name="cardUser" id="cardUser" data-options="required:true,missingMessage:'请输入持卡人.',validType:['minLength[1]','legalInput']" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		<div>
			<label>交易时间:</label>
		    <input name="tradingTime" id="tradingTime" type="text" class="easyui-datebox" maxLength="200"  />
		</div> 
		<div>
			<label>金额:</label>
		    <input name="amount" id="amount" type="text" class="easyui-validatebox" maxLength="100"  />
		</div>
		<div>
			<label>油卡:</label>
		    <input name="cardOil" id="cardOil" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>数量:</label>
		    <input name="count" id="count" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>单价:</label>
		    <input name="unitPrice" id="unitPrice" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>奖励积分:</label>
		    <input name="rewardPoints" id="rewardPoints" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>地址:</label>
		    <input name="address" id="address" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
	</form>
</div>