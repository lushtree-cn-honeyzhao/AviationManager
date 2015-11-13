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
    <form id="vehicle_form" method="post" novalidate>
	    <input type="hidden"  name="id"/>
	    <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version"/>
		<div>
			<label>车牌号:</label>
		    <input id="plateNo" name="plateNo" type="text" class="easyui-validatebox"
				maxLength="50" data-options="required:true,missingMessage:'请输入车牌号.',validType:['minLength[1]','legalInput']">
		</div>
       	<div>
			<label>车牌类型:</label>
		    <input id="vchicleType" data-options="required:true,missingMessage:'请输入车牌类型.',validType:['minLength[1]','legalInput']" name="vchicleType" type="text" class="easyui-validatebox" maxLength="100" >
		</div>
		<div>
			<label>车辆所有人:</label>
		    <input name="owner" id="owner" data-options="required:true,missingMessage:'请输入车辆所有人.',validType:['minLength[1]','legalInput']" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		<div>
			<label>地址:</label>
		    <input name="address" id="address" type="text" class="easyui-validatebox" maxLength="200"  />
		</div> 
		<div>
			<label>品牌型号:</label>
		    <input name="modelBrand" id="modelBrand" type="text" class="easyui-validatebox" maxLength="100"  />
		</div>
		<div>
			<label>使用性质:</label>
		    <input name="useCharacter" id="useCharacter" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>发动机编号:</label>
		    <input name="engineNo" id="engineNo" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>车辆识别码:</label>
		    <input name="vin" id="vin" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>注册登录日期:</label>
		    <input name="registerDate" id="registerDate" type="datetime" class="easyui-datebox" maxLength="50"  />
		</div>
		
		<div>
			<label>发证日期:</label>
		    <input name="issueDate" id="issueDate" type="datetime" class="easyui-datebox" maxLength="50"  />
		</div>
		
		<div>
			<label>档案编号:</label>
		    <input name="fileId" id="fileId" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>车牌号码副:</label>
		    <input name="plateNoAdditional" id="plateNoAdditional" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>车辆类型副:</label>
		    <input name="vchicleTypeAdditional" id="vchicleTypeAdditional" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>总质量(吨):</label>
		    <input name="totleQuality" id="totleQuality" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>整备质量(吨):</label>
		    <input name="servicingQuality" id="servicingQuality" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>核载重质量(吨):</label>
		    <input name="nuclearloadQuality" id="nuclearloadQuality" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>准牵引总质量(吨):</label>
		    <input name="quasitractionQuality" id="quasitractionQuality" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>准牵引总质量(吨):</label>
		    <input name="quasitractionQuality" id="quasitractionQuality" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>核定载客(人):</label>
		    <input name="ratifyPassenger" id="ratifyPassenger" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>驾驶室共乘(人):</label>
		    <input name="rideCount" id="rideCount" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>货箱内部尺寸(米):</label>
		    <input name="containerInsideSize" id="containerInsideSize" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>后桥钢板弹簧片数(片):</label>
		    <input name="rearAxleSteelPlateSpringLeafNumber" id="rearAxleSteelPlateSpringLeafNumber" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>外廓尺寸(米):</label>
		    <input name="outsideSize" id="outsideSize" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>检验有效期:</label>
		    <input name="inspectionTime" id="inspectionTime" type="datetime" class="easyui-datebox" maxLength="12"  />
		</div>
		
		<div>
			<label>检验记录:</label>
		    <input name="inspectionRecords" id="inspectionRecords" type="datetime" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>车辆尺寸:</label>
		    <input name="vehicleSize" id="vehicleSize" type="datetime" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>吨位:</label>
		    <input name="tonnage" id="tonnage" type="datetime" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>轮胎型号:</label>
		    <input name="tireModel" id="tireModel" type="datetime" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>轮胎个数:</label>
		    <input name="tireCount" id="tireCount" type="text" class="easyui-validatebox" maxLength="10"  />
		</div>
		
		<div>
			<label>主驾驶:</label>
		    <input name="mianDriver" id="mianDriver" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>副驾驶:</label>
		    <input name="coPilot" id="coPilot" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>资质性质:</label>
		    <input name="qualification" id="qualification" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		
		<div>
			<label>行驶证扫描件:</label>
		    <input name="driverImages" id="driverImages" type="text" class="easyui-validatebox" maxLength="50"  />
		</div>
		<div class="clearfix"></div>
		<div>
			<label>备注:</label>
		    <textarea name="description" id="description"  class="easyui-validatebox" maxLength="1000"  ></textarea>
		</div>
	</form>
</div>