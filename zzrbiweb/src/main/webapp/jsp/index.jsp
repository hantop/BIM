<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>现场劳务管理系统</title>
	<!--[if lt IE 10]>
	<meta http-equiv="refresh" content="0;URL=<%=request.getContextPath() %>/ie.shtml" />
	<![endif]-->
	<link href="<%=request.getContextPath()%>/images/favicon.ico" rel="Shortcut Icon">
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet">
  	<link href="<%=request.getContextPath()%>/css/common.css" rel="stylesheet" type="text/css"/>
  	<link href="<%=request.getContextPath()%>/css/plugins/sidebar/jquery-sidebar-tab.css" rel="stylesheet" type="text/css">
  	<link href="<%=request.getContextPath()%>/css/index-style.css" rel="stylesheet" type="text/css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css"/>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery-3.1.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery.slimscroll.min.js?v=1.0"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/plugins/sidebar/jquery-sidebar-tab.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/plugins/sidebar/jquery.metisMenu.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap-modal.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery.md5.js"></script>
</head>
<body style="overflow-y: hidden;">
	<!-- 头部信息 -->
	<div class="header clearfix">
		<div class="logo-header">
			<%--<img alt="" src="<%=request.getContextPath()%>/images/index/logo.png">--%>
		</div>
		<div class="title-header">
			<h1>现场劳务管理系统<span>V 1.0</span></h1>
		</div>
		<div class="user-header">
			<div class="user-info">
				<i></i>
				<a class="username" href="javascript:void(0);">${requestScope.userData.staffName}</a>
				<a href="<%=request.getContextPath()%>/logout.shtml" class="loginOut">注销</a>
				<a data-toggle="modal" href="#example" class="loginOut ml40">修改密码</a>
			</div>
		</div>
	</div>
	<div id="example" class="modal hide fade in" style="display: none; ">
		<div class="modal-header" style="text-align: center; " >
			<a class="close" data-dismiss="modal">×</a>
			<h3>修改密码</h3>
		</div>
		<div class="modal-body" style="text-align: center; ">
			新&nbsp;密&nbsp;码 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="pass" type="password" name="pass"
																 required="true"></br>
			确&nbsp;认&nbsp;密&nbsp;码 <input id="confirmPass" type="password" name="confirmPass" required="true">
		</div>
		<div class="modal-footer">
			<a href="#" onclick="xgmm()" class="btn btn-success">确定</a>
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
		</div>
	</div>
	<!-- 中部操作信息 -->
	<div class="content clearfix">
		<!-- 左侧菜单 -->
		<div class="content-sidebar">
			<div class="sidebar-page">
				<ul class="nav-sidebar J_SIDEBAR">
					<li>
						<a class="JM_I" href="home.shtml" data-index="_h1">
							<i class="fa fa-home"></i>
							<span class="nav-label">首页</span>
						</a>
					</li>
					<c:forEach var="dicParent" items="${dicParentList}" begin="0" varStatus="v1">
						<li>
							<c:if test="${dicParent.consoleUrl == null || dicParent.consoleUrl =='' }">
							      <a class="border-top"  href="#" data-index="_${v1.index}">
									<i class="fa fa-home"></i>
									<span class="nav-label">${dicParent.directoryName}</span>
									<span class="fa arrow"></span>
								</a>
									<ul class="sidebar-second-level">
										 <c:forEach var="dic" items="${dicList}" begin="0" varStatus="v2">								
											    <c:if test="${dicParent.id==dic.parentNavigator}">
											        <li>
														<a class="JM_I" href="<%=request.getContextPath() %>${dic.consoleUrl}" data-index="_${v1.index}${v2.index}"><span class="nav-label">${dic.directoryName}</span></a>											
													</li>
											    </c:if>							
										</c:forEach> 
									</ul>
							</c:if>
							 <c:if test="${dicParent.consoleUrl != null && dicParent.consoleUrl !='' }">
							      <a class="JM_I" href="<%=request.getContextPath() %>${dicParent.consoleUrl}" data-index="_h1">
									<i class="fa fa-home"></i>
									<span class="nav-label">${dicParent.directoryName}</span>
								 </a>
							</c:if>	 
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<!-- 右侧操作区 -->
		<div class="content-page">
			<!-- 头部tab栏 -->
			<div class="content-tabs JM_C">
				<!-- 向左-->
				<button class="content-tab-left roll-left JT_L"><i class="fa fa-backward"></i></button>
				<!-- tab 标签 -->
				<div class="content-menu-tabs JM_M">
					<div class="content-page-tabs JM_P">
	                    <a href="javascript:void(0);" class="active JM_T" data-id="home.shtml">首页</a>
                    </div>
				</div>
				<!-- 向右-->				
				<button class="content-tab-right roll-right JT_R"><i class="fa fa-forward"></i></button>
			</div>
			<div class="content-main J_IFRAME_MAIN" >
				<iframe id="iframe_h1" class="J_IFRAME" name="iframe_h1" width="100%" height="100%" src="home.shtml" frameborder="0" data-id="home.shtml"></iframe>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/plugins/pace/pace.min.js"></script>
	<script type="text/javascript">
		$(function () {
	        $('.J_SIDEBAR').metisMenu();
	    });

        function xgmm() {
            var password = $("#pass").val();
            var confirmPass = $("#confirmPass").val();
            var data = {"password": $.md5(password), "confirmPass": $.md5(confirmPass)};
            if (password.length == 0 || confirmPass.length == 0) {
                alert("密码不能为空 ！");
                return;
            }else  if(password.length < 6 || confirmPass.length < 6 || password.length > 12 || confirmPass.length > 12){
                alert("密码长度为 6 至 12 之间的字符串 ！");
                return;
			}
            $.ajax({
                type: "post",
                dataType: "json",
                data: data,
                url: <%=request.getContextPath()%>"/updatePass.shtml",
                success: function (msg) {
                    if (msg == "500") {
                        alert("两次输入不一致");
                        $("#pass").attr("value", "");
                        $("#confirmPass".attr("value", ""));
                    } else if (msg == "200") {
                        alert("修改成功");
                        $('#example').modal('hide')
                    }
                }

            });

        }


	</script> 
</body>
</html>