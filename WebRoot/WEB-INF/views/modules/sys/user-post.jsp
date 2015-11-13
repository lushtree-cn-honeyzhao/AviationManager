<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
    var post_combobox;
    $(function() {
        loadPost();
    });

    //加载用户
    function loadPost(){
        post_combobox = $("#postIds").combobox({
            url: '${ctx}/sys/post/combobox?userId=${model.id}',
            multiple: true,
            editable:false
        });
    }
</script>
<div>
    <form id="user_post_form" method="post">
        <input type="hidden" id="id" name="id" />
        <!-- 用户版本控制字段 version -->
        <input type="hidden" id="version" name="version" />
        <div>
            <label>岗位:</label>
            <input type="select" class="easyui-combogrid" id="postIds" name="postIds" style="width: 260px;"/>
        </div>
    </form>
</div>