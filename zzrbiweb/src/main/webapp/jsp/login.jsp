<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<link rel="shortcut icon" href="<%=request.getContextPath() %>/images/favicon.ico" type="image/x-icon" />
<link href="<%=request.getContextPath()%>/css/font-awesome.min.css?V=2.0" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css">
<link href="<%=request.getContextPath()%>/css/plugins/alert/jquery-alert.css?V=2.0" rel="stylesheet"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery.md5.js"></script>
</head>
<body>
	<div class="page">
			<div class="con_bd01">
				<div class="logo"></div>
				<div class="timer"></div>
			</div>
			<div class="con_bd02"></div>

	    <div class="content">
		    <div class="con_title">
		    	<span>数据后台管理系统</span><span class="cVersion">v1.0</span>
		    </div>
		    <div class="con_bd">
		    	<div class="uname pop_check">
		    		<span>用户名</span>
		    		<input class="username" type="text" maxlength="20">
		    		<div class="error userTips" style="display: none"><span>用户名错误</span><i class="fa fa-times-circle"></i></div>
		    	</div>
		    	
				<!-- 密码 -->
		        <div class="pop_check_msg pop_check">
		        	<span>密&nbsp;&nbsp;&nbsp;码</span>
		            <input class="pop_num" id="password" type="password" maxlength="20" >
		            <div class="error pwTips" style="display: none"><span>密码错误</span><i class="fa fa-times-circle"></i></div>
		        </div>
		        <!--图片验证码 -->
		        <%--<div class="pop_check_txt pop_check">--%>
		        	<%--<span>验证码</span>--%>
		            <%--<input id="input_code" class="pop_num" type="text" maxlength="4">--%>
		            <%--<img class="img-code tg_code" src="<%=request.getContextPath()%>/getCode.shtml" alt="验证码" title="验证码">--%>
		            <%--<div class="error picTips" style="display: none"><span>验证码错误</span><i class="fa fa-times-circle"></i></div>--%>
		        <%--</div>--%>
				<!-谷歌令牌 -->
				<div class="pop_check_txt pop_check">
				<span>令&nbsp;&nbsp;&nbsp;牌</span>
				<input id="input_code" class="pop_num" type="text" maxlength="6">
				<div class="error picTips" style="display: none"><span>令牌错误</span><i class="fa fa-times-circle"></i>
				</div>
				</div>
		        <div class="go_login">点击登录</div>
		    </div>
	    </div>
    </div>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/plugins/alert/jquery-alert.js?V=2.0"></script>
	<script src="<%=request.getContextPath()%>/js/login.js?V=2.0"></script>
</body>
</html>