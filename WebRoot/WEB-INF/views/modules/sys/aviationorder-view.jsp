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
			allowFileManager : true ,
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
    <form id="vehicle_form" method="post" novalidate>
	    <input type="hidden"  name="id"/>
	    <!-- 用户版本控制字段 version -->
	    <input type="hidden" id="version" name="version"/>
		<div>
			<label>用户卡号:</label>
		   	${model.cardSn}
		</div>
       	<div>
			<label>卡类型:</label>
		     ${model.cardType}
		</div>
		<div>
			<label>持有人:</label>
		    ${model.cardUser}
		</div>
		<div>
			<label>身份证号码:</label>
		   	${model.cardIdnumber}
		</div> 
		<div>
			<label>余额:</label>
		    ${model.cardBalance}元
		</div>
		<div>
			<label>备付金余额:</label>
		    ${model.cardCoverbalance}
		</div>
		<div>
			<label>积分备付金:</label>
		    ${model.cardProvisionsIntegral}
		</div>
		
		<div>
			<label>积分:</label>
		    ${model.cardIntegral}
		</div>
		
		<div>
			<label>卡状态:</label>
		    ${model.cardStatus}
		</div>
		
		<div>
			<label>限定油品:</label>
		    ${model.cardOil}
		</div>		
		<div>
			<label>审核意见:</label>
			<c:choose>
				<c:when test="${model.checkFlag==true}">已审核</c:when>
				<c:otherwise>未审核</c:otherwise>
			</c:choose>		    
		</div>		
		<div>
			<label>审核内容:</label>
		    ${model.checkContent}
		</div>
		
		<div>
			<label>有效期:</label>
		    ${model.validityDate}
		</div>
		<div>
			<label>app描述:</label>
		    ${model.appCardDetail}
		</div>
		<div>
			<label>微信描述:</label>
		    ${model.wechatCardDetail}
		</div>
		
	</form>
</div>
