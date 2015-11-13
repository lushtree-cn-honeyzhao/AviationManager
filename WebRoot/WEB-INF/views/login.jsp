<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户登录</title>
    <%@ include file="/common/meta.jsp"%>
    <script type="text/javascript">
        var loginForm;
        var login_linkbutton;
        var $loginName,$password,$rememberPassword,$autoLogin;
        $(function(){
            $loginName = $("#loginName");
            $password = $("#password");
            $rememberPassword = $("#rememberPassword");
            $autoLogin = $("#autoLogin");
            loginForm = $('#loginForm').form();
            //refreshCheckCode();

            $rememberPassword.prop("checked", "${cookie.rememberPassword.value}" == "" ? false : true);
            $autoLogin.prop("checked", "${cookie.autoLogin.value}" == "" ? false : true);

            $loginName.val("${cookie.loginName.value}");
            if("${cookie.rememberPassword.value}" != ""){
                $password.val("${cookie.password.value}");
            }
            loginForm.form("validate");


            if("${cookie.autoLogin.value}" != ""){
                login();
            }else{
                $loginName.focus();
            }

            $rememberPassword.click(function(){
                var checked = $(this).prop('checked');
                if(checked){
                    $.cookie('password', $password.val(), {
                        expires : 7
                    });
                    $.cookie('rememberPassword', checked, {
                        expires : 7
                    });
                }else{
                    $.cookie('password', "", {
                        expires : 7
                    });
                    $.cookie('rememberPassword', "", {
                        expires : 7
                    });
                }
            });
            $autoLogin.click(function(){
                var checked = $(this).prop('checked');
                if(checked){
                    $.cookie('autoLogin', checked, {
                        expires : 7
                    });
                    $rememberPassword.prop('checked',checked);
                    $.cookie('rememberPassword', checked, {
                        expires : 7
                    });
                }else{
                    $.cookie('autoLogin', "", {
                        expires : 7
                    });
                }
            });
        });
        //刷新验证码
        function refreshCheckCode() {
            //加上随机时间 防止IE浏览器不请求数据
            var url = '${ctx}/servlet/ValidateCodeServlet?'+ new Date().getTime();
            $('#validateCode_img').attr('src',url);
        }
        // 登录
        function login() {
            $.cookie('loginName', $loginName.val(), {
                expires : 7
            });
            if($rememberPassword.prop("checked")){
                $.cookie('password', $password.val(), {
                    expires : 7
                });
            }
            if(loginForm.form('validate')){
                login_linkbutton = $('#login_linkbutton').linkbutton({
                    text:'正在登录...' ,
                    disabled:true
                });
                var cookieThemeType = "${cookie.themeType.value}"; //cookie初访的登录管理界面类型
                $.post('${ctx}/login/login?theme='+cookieThemeType,$.serializeObject(loginForm), function(data) {
                    if (data.code ==1){
                        window.location = data.obj;//操作结果提示
                    }else {
                        login_linkbutton.linkbutton({
                            text:'登录' ,
                            disabled:false
                        });
                        $('#validateCode').val('');
                        eu.showMsg(data.msg);
                        //refreshCheckCode();
                    }
                }, 'json');
            }
        }
    </script>
<style type="text/css">
html {
	background-color: #FEFEFE;
}
.wrapper {
	margin: 50px auto;
	width: 1000px;
}
.loginBox {
	background-color: #FEFEFE;
	border: 1px solid #BfD6E1;
	border-radius: 5px;
	color: #444;
	font: 14px 'Microsoft YaHei', '微软雅黑';
	margin: 0 auto;
	width: 388px
}
.loginBox .loginBoxCenter {
	border-bottom: 1px solid #DDE0E8;
	padding: 24px;
}
.loginBox .loginBoxCenter p {
	margin-bottom: 10px;
	margin-left: 50px;
	
}
.loginBox .loginBoxButtons {
	border-top: 1px solid #FFF;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	line-height: 28px;
	overflow: hidden;
	padding: 20px 69px;
	vertical-align: center;
}
.loginBox .loginInput {
	border: 1px solid #D2D9dC;
	border-radius: 2px;
	color: #444;
	font: 12px 'Microsoft YaHei', '微软雅黑';
	padding: 8px 14px;
	margin-bottom: 8px;
	width: 310px;
}
.loginBox .loginInput:FOCUS {
	border: 1px solid #B7D4EA;
	box-shadow: 0 0 8px #B7D4EA;
}
.loginBox .loginBtn {
	background-image: -moz-linear-gradient(to bottom, #fbcba4, #f89d54);
	border: 1px solid #ff7200;
	border-radius: 20px;
	box-shadow: inset rgba(255,255,255,0.6) 0 1px 1px, rgba(0,0,0,0.1) 0 1px 1px;
	color: #FFF;
	cursor: pointer;
	float: right;
	font: bold 13px Arial;
	padding: 5px 14px;
}
.loginBox .loginBtn:HOVER {
	background-image: -moz-linear-gradient(to top, #fbcba4, #f89d54);
}
.loginBox a.forgetLink {
	color: #ABABAB;
	cursor: pointer;
	float: right;
	font: 11px/20px Arial;
	text-decoration: none;
	vertical-align: middle;
}
.loginBox a.forgetLink:HOVER {
	text-decoration: underline;
}
.loginBox input#rememberPassword {
	vertical-align: middle;
}
.loginBox label[for="rememberPassword"] {
	font: 11px Arial;
}
.loginBox input#autoLogin {
	vertical-align: middle;
}
.loginBox label[for="autoLogin"] {
	font: 11px Arial;
}
#header {
	width:1000px;
	margin: 50px auto 0px;
	display: block;
}
#logo {
	height: 57px;
	width: 340px;
	margin: 0 auto;
	display: block;
}
.row {
 *zoom:1
}
.row:before, .row:after {
	display: table;
	line-height: 0;
	content: ""
}
.row:after {
	clear: both
}
</style>
</head>
<body>
<div id="header">
  <div class="row"> <img src="${ctxStatic}/img/logo.png" alt="" id="logo" class="span2"></img> </div>
</div>
<div class="wrapper">
 <form id="loginForm" method="post" class="bg" novalidate>
    <div class="loginBox">
      <div class="loginBoxCenter">
        <p>
          <label for="username">账号：</label>
        </p>
        <p>
          <input class="loginInput easyui-validatebox"  placeholder="请输入登录名..." style="width: 200px" type="text" id="loginName" name="loginName" 
	           required="true" value=""
	           data-options="validType:'minLength[1]',missingMessage:'请输入用户名!'"  />
        </p>
        <p>
          <label for="password">密码：</label>
        <p>
          <input type="password" id="password" name="password" placeholder="请输入密码..."  class="loginInput easyui-validatebox"
	           required="true" onkeydown="if(event.keyCode==13)login()" style="width: 200px" value=""
	           data-options="validType:'minLength[1]',missingMessage:'请输入密码!'" />
        </p>
      </div>
      <div class="loginBoxButtons">
        <input id="rememberPassword" type="checkbox" style="width: 15px;" />
        <label for="rememberPassword">记住密码</label>
        <input id="autoLogin" type="checkbox" style="width: 15px;" />
        <label for="autoLogin">自动登录</label>
        <div class="button">
    		<a id="login_linkbutton" style="width:211px;" href="#" class="easyui-linkbutton" onclick="login()" >登录</a>
  		</div>
      </div>
    </div>
  </form>
</div>
</body>
</html>
