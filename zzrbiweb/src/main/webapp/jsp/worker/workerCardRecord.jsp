<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工时统计管理</title>
	<link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/chosen/chosen.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/page-style.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css" />

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
	                               <label class="col-sm-4 control-label">队伍/班组</label>
	                               <div class="col-sm-8">
	                                  <input type="text" class="form-control" id="staffName" name="staffName" value="">
	                               </div>
		            		    </div>
		            		    <div class="col-sm-6">
	                               <label class="col-sm-4 control-label">统计时间</label>
	                               <div class="col-sm-4">
									   <input type="text" class="form-control" id="startTime" name="startTime"
											  placeholder="开始日期" >&nbsp;
								   </div>
								   <div class="col-sm-4">
									  <input type="text" class="form-control" id="endTime" name="endTime"
										  placeholder="结束日期" >
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

		        <table id="datatable" class="table">

					<tr>
						<td>序号</td>
						<td>队伍名称</td>
						<td>队长电话</td>
						<td>工时统计</td>
						<td>1日</td>
						<td>2日</td>
						<td>3日</td>
						<%--<c:forEach var="userRole" items="${userRoleList}" begin="0">
							<td>1日</td>
						</c:forEach>--%>
					</tr>

					<%--<c:forEach var="userRole" items="${userRoleList}" begin="0">
						<tr>
							<td>1</td>
							<td>杨荣耀队伍</td>
							<td>13288888888</td>
							<td>1000</td>
							<td>200</td>
						</tr>
					</c:forEach>--%>

					<tr>
						<td>1</td>
						<td>杨荣耀队伍</td>
						<td>13288888888</td>
						<td>1000</td>
						<td>200</td>
						<td>200</td>
						<td>200</td>
					</tr>


				</table>
	        </div> 
        </div>
	</div>

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.include.js?V=1.3"></script>

    <script type="text/javascript">
    $(document).ready(function() {
        bindEvent();
    });


    var bindEvent = function () {
        // 开始日期
        $('#startTime').datepicker({
            format: "yyyy-mm-dd",
            language: 'zh_CN',
            orientation: "top left",
            showButtonPanel: true,
            multidate: false,
            beforeShowDay: function (date) {
                var d = new Date();
                var curr_date = d.getDate();
                var curr_month = d.getMonth() + 1;
                var curr_year = d.getFullYear();
                var formatDate = curr_year + "-" + curr_month + "-" + curr_date;
                var p_date = date.getDate();
                var p_month = date.getMonth() + 1;
                var p_year = date.getFullYear();
                var pDate = p_year + "-" + p_month + "-" + p_date;
                if (pDate == formatDate) {
                    return {classes: 'specialdays'};
                } else {
                    return;
                }
            }
        }).on("changeDate", function (e) {
//			 $('#endTime').datepicker('setStartDate', $('#startTime').val());
        });

        $('#endTime').datepicker({
            format: "yyyy-mm-dd",
            language: 'zh_CN',
            orientation: "top left",
            showButtonPanel: true,
            multidate: false,
            beforeShowDay: function (date) {
                var d = new Date();
                var curr_date = d.getDate();
                var curr_month = d.getMonth() + 1;
                var curr_year = d.getFullYear();
                var formatDate = curr_year + "-" + curr_month + "-" + curr_date;
                var p_date = date.getDate();
                var p_month = date.getMonth() + 1;
                var p_year = date.getFullYear();
                var pDate = p_year + "-" + p_month + "-" + p_date;
                if (pDate == formatDate) {
                    return {classes: 'specialdays'};
                } else {
                    return;
                }
            }
        }).on("changeDate", function (e) {
//			 $('#endTime').datepicker('setStartDate', $('#startTime').val());
        });

    }

    </script> 
</body>
</html>