<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>发卡统计</title>
	<link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/iCheck/custom.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/chosen/chosen.css" rel="stylesheet"/>
	<link href="<%=request.getContextPath()%>/css/plugins/alert/jquery-alert.css?V=1.0" rel="stylesheet"/>
	<link href="<%=request.getContextPath()%>/css/plugins/jsTree/style.min.css" rel="stylesheet"/>
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/page-style.css?v=1.1" rel="stylesheet" type="text/css"/>

</head>
<style type="text/css">
	.modal-content-div{
		position: relative;
		background-color: #fff;
		border: 1px solid #999;
		border: 1px solid rgba(0,0,0,0.2);
		border-radius: 6px;
		outline: 0;
		-webkit-box-shadow: 0 3px 9px rgba(0,0,0,0.5);
		box-shadow: 0 3px 9px rgba(0,0,0,0.5);
		background-clip: padding-box;
	}
	.modal-body{
		text-align: center;
	}
</style>
<body>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<!-- 查询条件 -->
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>查询条件</h5>
			</div>
			<div class="ibox-content">
				<form class="form-horizontal" id="searchForm">
					<div class="form-group">
						<div class="col-sm-11">
							<div class="col-sm-6">
								<label class="col-sm-4 control-label">班组</label>
								<div class="col-sm-8">
									<select class="form-control" name="teamId" id="teamIds" data-rule-required="true"  data-live-search="true">
										<option value="">请选择班组...</option>
										<c:forEach var="userRole" items="${userRoleList}" begin="0">
											<option value="${userRole.id}" hassubinfo="true">${userRole.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-6">
								<label class="col-sm-4 control-label">卡类型</label>
								<div class="col-sm-8">
									<select class="form-control" name="teamId" id="cardType" data-rule-required="true"  data-live-search="true">
										<option value="">请选择卡类型...</option>
											<option value="0" hassubinfo="true">IC卡</option>
									</select>
								</div>
							</div>

						</div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group">
						<div class="col-sm-6">
							<button type="button" class="btn btn-primary pull-right" id="query_button"><i class="fa fa-search"></i> 查询</button>
						</div>
						<div class="col-sm-6">
							<button type="reset" class="btn btn-outline btn-default pull-left"><i class="fa fa-reply"></i> 重置</button>
						</div>
					</div>
				</form>
			</div>
		</div>

		<!--员工列表-->
		<div class="ibox">
			<div class="ibox-title">
				<h5>发卡统计</h5>

			</div>
			<table id="datatable"></table>
		</div>
	</div>
</div>



<script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.include.js?V=1.3"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js?v=1.3"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/plugins/jsTree/jstree.min.js?V=1.3"></script>
<script type="text/javascript">
    $(document).ready(function() {
        // 装载列表
        function installGrid(){
            $("#datatable").jqGrid({
                url : "<%=request.getContextPath()%>/getWorkTeamList.shtml", // 请求地址
                showExport:false,
                data: {staffName: $("#searchForm #staffName").val(),userRoleId: $("#searchForm #userRoleId1").val()},
                columns : [
                    {field : "id",title : "标识",width : "0%",visible:false},
                    {field : "empower",title: '班组名称',width : "10%",align: 'center',valign: 'middle'},
                    {field : "cardType",title: '卡类型',width : "10%",align: 'center',valign: 'middle'},
                    {field : "cardType",title : "办卡",width : "10%",align: 'center',valign: 'middle'},
                    {field : "cardType",title: '退卡',width : "10%",align: 'center',valign: 'middle'}

                ]
            });
        }
        installGrid();
        // 查询事件
        $("#query_button").click(installGrid);
        // 表单验证
        var e = "<i class='fa fa-times-circle'></i>";
        var rules = {
            userName: {
                remote: {
                    url: "<%=request.getContextPath()%>/checkLogin.shtml",
                    type: "get",
                    dataType: "json",
                    data: {userName: function(){
                        return $("#addTeamForm #userName").val();
                    }}
                }
            }
        };
        var message = {userName: { remote:e+"班组已经存在"}};
        // 添加员工表单提交

    });
</script>
</body>
</html>