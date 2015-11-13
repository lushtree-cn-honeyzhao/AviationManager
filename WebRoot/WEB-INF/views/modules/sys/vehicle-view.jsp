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
    <form id="vehicle_form" method="post" novalidate>
	    <input type="hidden"  name="id"/>
	    <!-- 用户版本控制字段 version -->
	    <input type="hidden" id="version" name="version"/>
		
		<div>
			<label>车牌号:</label>
		    ${model.plateNo}
		</div>
	      	<div>
			<label>车牌类型:</label>
		    ${model.vchicleType}
		</div>
		<div>
			<label>车辆所有人:</label>
		    ${model.owner}
		</div>
		<div>
			<label>地址:</label>
		    ${model.address}
		</div> 
		<div>
			<label>品牌型号:</label>
		    ${model.modelBrand}
		</div>
		<div>
			<label>使用性质:</label>
		    ${model.useCharacter}
		</div>
		
		<div>
			<label>发动机编号:</label>
		    ${model.engineNo}
		</div>
		
		<div>
			<label>车辆识别码:</label>
		    ${model.vin}
		</div>
		
		<div>
			<label>注册登录日期:</label>
		    ${model.registerDate}
		</div>
		
		<div>
			<label>发证日期:</label>
		    ${model.issueDate}
		</div>
		
		<div>
			<label>档案编号:</label>
		    ${model.fileId}
		</div>
		
		<div>
			<label>车牌号码副:</label>
		    ${model.plateNoAdditional}
		</div>
		
		<div>
			<label>车辆类型副:</label>
		    ${model.vchicleTypeAdditional}
		</div>
		
		<div>
			<label>总质量(吨):</label>
		   ${model.totleQuality}
		</div>
		
		<div>
			<label>整备质量(吨):</label>
		    ${model.servicingQuality}
		</div>
		
		<div>
			<label>核载重质量(吨):</label>
		    ${model.nuclearloadQuality}
		</div>
		
		<div>
			<label>准牵引总质量(吨):</label>
		    ${model.quasitractionQuality}
		</div>
		
		<div>
			<label>准牵引总质量(吨):</label>
		    ${model.quasitractionQuality}
		</div>
		
		<div>
			<label>核定载客(人):</label>
		    ${model.ratifyPassenger}
		</div>
		
		<div>
			<label>驾驶室共乘(人):</label>
		   ${model.rideCount}
		</div>
		
		<div>
			<label>货箱内部尺寸(米):</label>
		    ${model.containerInsideSize}
		</div>
		
		<div>
			<label>后桥钢板弹簧片数(片):</label>
		    ${model.rearAxleSteelPlateSpringLeafNumber}
		</div>
		
		<div>
			<label>外廓尺寸(米):</label>
		    ${model.outsideSize}
		</div>
		
		<div>
			<label>检验有效期:</label>
		    ${model.inspectionTime}
		</div>
		
		<div>
			<label>检验记录:</label>
		    ${model.inspectionRecords}
		</div>
		
		<div>
			<label>车辆尺寸:</label>
		    ${model.vehicleSize}
		</div>
		
		<div>
			<label>吨位:</label>
		    ${model.tonnage}
		</div>
		
		<div>
			<label>轮胎型号:</label>
		    ${model.tireModel}
		</div>
		
		<div>
			<label>轮胎个数:</label>
		    ${model.tireCount}
		</div>
		
		<div>
			<label>主驾驶:</label>
		    ${model.mianDriver}
		</div>
		
		<div>
			<label>副驾驶:</label>
		    ${model.coPilot}
		</div>
		
		<div>
			<label>资质性质:</label>
		    ${model.qualification}
		</div>
		
		<div>
			<label>行驶证扫描件:</label>
		    ${model.driverImages}
		</div>
		<div class="clearfix"></div>
		<div>
			<label>备注:</label>
		    ${model.description}
		</div>
	</form>
</div>
