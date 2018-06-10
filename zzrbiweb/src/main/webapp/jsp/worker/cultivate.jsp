<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>安全培训</title>
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
	                               <label class="col-sm-4 control-label">教育类型</label>
	                               <div class="col-sm-8">
	                                  <select class="chosen-select" id="userRoleId1" name="userRoleId">
										  <option value ="">请选择...</option>
										  <option value="1" hassubinfo="true">定期安全培训</option>
										  <option value="2" hassubinfo="true">安全技术交底</option>
										  <option value="3" hassubinfo="true">VR安全教育</option>
									  </select>
	                               </div>
		            		    </div>

								<div class="col-sm-6">
									<label class="col-sm-4 control-label">培训主题</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" id="staffName" name="staffName" value="">
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
                	<h5>培训管理</h5>
	                <div class="ibox-tools">
                        <button type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#add_team"><i class="fa fa-user-plus"></i> 添加培训</button>
                    </div>
            	</div>
		        <table id="datatable"></table>  
	        </div> 
        </div>
	</div>
	<!-- 添加员工 -->
	<div class="modal inmodal" id="add_team" role="dialog" aria-hidden="true" data-backdrop="static" style="display: none;">
    	<div class="modal-dialog">
       	    <form id="addTeamForm" class="modal-content animated fadeIn" novalidate="novalidate">
           	   <div class="modal-header">
                    <button type="reset" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title text-left">新增培训</h4>
                  </div>
                <div class="modal-body">
					<div class="form-horizontal ">

						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label"><span class="form-valid-field">*</span>教育类型：</label>
								<div class="col-sm-8">
									<select class="form-control" name="studyType" id="studyType" data-rule-required="true"  data-live-search="true">
										<option value ="">请选择...</option>
										<option value="1" hassubinfo="true">定期安全培训</option>
										<option value="2" hassubinfo="true">安全技术交底</option>
										<option value="3" hassubinfo="true">VR安全教育</option>
									</select>
								</div>
							</div>
						</div>

					  <div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field" >*</span>培训主题：</label>
		                    <div class="col-sm-8">
		                        <input type="text" class="form-control" name="title" id="title" data-rule-required="true"  data-rule-rangelength="[4,12]">
		                    </div></div>
		                </div>

					    <div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field"  >*</span>培训日期：</label>
		                    <div class="col-sm-8">
		                        <input type="text" class="form-control" name="studyDate" id="studyDate" data-rule-required="true" data-rule-rangelength="[6,12]">
		                    </div></div>
		                </div>

					    <div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field"  >*</span>培训人：</label>
		                    <div class="col-sm-8">
		                        <input type="text" class="form-control" name="teacher" id="teacher" data-rule-required="true" data-rule-isMobile="true" maxlength=11 >
		                        
		                    </div></div>
		                </div>

						<div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field"  >*</span>培训课时：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="studylong" id="studylong" data-rule-required="true" data-rule-isMobile="true" maxlength=11 >

							</div></div>
						</div>

						<div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field"  >*</span>培训人数：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="students" id="students" data-rule-required="true" data-rule-isMobile="true" maxlength=11 >

							</div></div>
						</div>

						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label"><span class="form-valid-field">*</span>所属公司：</label>
								<div class="col-sm-4">
									<select class="form-control" name="userRoleId" id="userRoleIds" data-rule-required="true"  data-live-search="true">
										<option value="">请选择公司...</option>
										<c:forEach var="userRole" items="${userRoleList}" begin="0">
											<option value="${userRole.id}" hassubinfo="true">${userRole.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-4">
									<select class="form-control" name="userRoleId" id="userRoleId" data-rule-required="true"  data-live-search="true">
										<option value="">请选择班组...</option>
										<c:forEach var="userRole" items="${userRoleList}" begin="0">
											<option value="${userRole.id}" hassubinfo="true">${userRole.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>


					</div>
                </div>
                <div class="modal-footer">
	                 <button type="reset" class="btn btn-outline btn-default" data-dismiss="modal"><i class="fa fa-remove"></i>&nbsp;取消</button>
                     <button type="submit" class="btn btn-primary" ><i class="fa fa-check"></i>&nbsp;确定</button>
                </div>
        	 </form> 
    	</div>
    </div>


	<!-- 编辑员工 -->
	<div class="modal inmodal" id="edit_staff" role="dialog" aria-hidden="true" data-backdrop="static" style="display: none;">
		<div class="modal-dialog">
			<form id="editTeamForm" class="modal-content animated fadeIn" novalidate="novalidate" >
				<div class="modal-header">
					<button type="reset" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title text-left">编辑培训</h4>
				</div>
				<div class="modal-body">
					<div class="form-horizontal ">

						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label"><span class="form-valid-field">*</span>教育类型：</label>
								<div class="col-sm-8">
									<select class="form-control" name="studyType" id="studyTypes" data-rule-required="true"  data-live-search="true">
										<option value ="">请选择...</option>
										<option value="1" hassubinfo="true">定期安全培训</option>
										<option value="2" hassubinfo="true">安全技术交底</option>
										<option value="3" hassubinfo="true">VR安全教育</option>
									</select>
								</div>
							</div>
						</div>

						<div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field" >*</span>培训主题：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="title" id="titles" data-rule-required="true"  data-rule-rangelength="[4,12]">
							</div></div>
						</div>

						<div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field"  >*</span>培训日期：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="studyDate" id="studyDates" data-rule-required="true" data-rule-rangelength="[6,12]">
							</div></div>
						</div>

						<div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field"  >*</span>培训人：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="teacher" id="teachers" data-rule-required="true" data-rule-isMobile="true" maxlength=11 >

							</div></div>
						</div>

						<div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field"  >*</span>培训课时：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="studylong" id="studylongs" data-rule-required="true" data-rule-isMobile="true" maxlength=11 >

							</div></div>
						</div>

						<div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field"  >*</span>培训人数：</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" name="students" id="studentss" data-rule-required="true" data-rule-isMobile="true" maxlength=11 >

							</div></div>
						</div>
<!--
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label"><span class="form-valid-field">*</span>所属公司：</label>
								<div class="col-sm-4">
									<select class="form-control" name="userRoleId" id="userRoleIds" data-rule-required="true"  data-live-search="true">
										<option value="">请选择公司...</option>
										<c:forEach var="userRole" items="${userRoleList}" begin="0">
											<option value="${userRole.id}" hassubinfo="true">${userRole.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-4">
									<select class="form-control" name="userRoleId" id="userRoleId" data-rule-required="true"  data-live-search="true">
										<option value="">请选择班组...</option>
										<c:forEach var="userRole" items="${userRoleList}" begin="0">
											<option value="${userRole.id}" hassubinfo="true">${userRole.name}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
-->
						<div class="form-group"><div class="col-sm-12">
							<label class="col-sm-3 control-label"><span class="form-valid-field"  >*</span>资料上传：</label>
							<div class="col-sm-8">
								<input type="file" class="form-control" name="attachment" id="attachment" data-rule-required="true" data-rule-isMobile="true" maxlength=11 >

							</div></div>
						</div>




					</div>
				</div>
				<div class="modal-footer">
					<button type="reset" class="btn btn-outline btn-default" data-dismiss="modal"><i class="fa fa-remove"></i>&nbsp;取消</button>
					<button type="submit" class="btn btn-primary"><i class="fa fa-check"></i>&nbsp;确定</button>
				</div>
			</form>
		</div>
	</div>



	<!-- 签到人员列表 -->
	<div class="modal inmodal" id="worker_staff" role="dialog" aria-hidden="true" data-backdrop="static" style="display: none;">
		<div class="modal-dialog">
			<table id="qiandao"></table>
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
    			    {field : "studyDate",title : "培训日期",width : "10%",align: 'center',valign: 'middle'},
    			    {field : "studyType",title: '教育类型',width : "10%",align: 'center',valign: 'middle'},
    			    {field : "title",title: '培训主题',width : "10%",align: 'center',valign: 'middle'},
    			    {field : "teacher",title: '培训人',width : "10%",align: 'center',valign: 'middle'},
                    {field : "students",title: '培训人数',width : "10%",align: 'center',valign: 'middle'},
                    {field : "studylong",title: '时长',width : "10%",align: 'center',valign: 'middle'},
    			    {field : "operate",title : "操作",width : "15%",align: 'center',valign: 'middle',
    			    	formatter:function(value,row,index){
    						return   "<a href='javascript:void(0);' class='edit_party'>编辑</a>&nbsp;"
                            		+"<a href='javascript:void(0);' class='worker_staff'>签到人员</a>&nbsp;"
    								+"<a href='javascript:void(0);' class='remove_party'>&nbsp;删除</a>";					    		
    				    },
    				    events:{
    				    	'click .edit_party': function (e, value, row, index) {
    				    		$('.mm1').hide();
    				    		$('.mm2').hide();
    				    		$("#editTeamForm #userRoleIds").val(row.userRoleId);
    					    	$("#editTeamForm #userRoleIds").trigger('chosen:updated');
    				    	    
    				    		$("#editTeamForm #isUpdatePwd").val(false);
    				    		$("#editTeamForm #partyId").val(row.id);
    				    	    $("#editTeamForm #userNames").val(row.staffName);
    				    	    $("#editTeamForm #emailCodes").val(row.email);
    				    	    $("#editTeamForm #phoneCodes").val(row.mobile);
    				    	    
    				    	    $('#edit_staff').modal();
    			   			},
    				    	'click .remove_party': function (e, value, row, index) {
    				    		fairAlert.confirm({
    				    			msg:'您确定要删除该培训吗？删除后不可恢复！',
    				    			confirmCallback:function(){
    									ajaxPostJson({
    										url:"<%=request.getContextPath()%>/removeUser.shtml",
    										data:{id:row.id},
    										success:function(result){   										   
    								    	    if(result == "success") {
    								    			fairAlert.success({"msg":"已成功删除培训"});
    								    			installGrid();
    								    	    }else{
    												fairAlert.error({"msg":"删除培训失败"});
    								    	    }
    								    	}
    									});
    								}
    				    		})
    			   			},
    			   		}
    			    }, 
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
    	var message = {userName: { remote:e+"培训已经存在"}};
    	// 添加员工表单提交
    	$("#addTeamForm").validate({
             rules: rules, 
            messages: message,           
            submitHandler:function(form){
            		$(form).ajaxSubmit({
            			url : "<%=request.getContextPath()%>/addAndUpdateWorkTeam.shtml",
           			    dataType: "json",
           			    type: 'POST',
           			 	clearForm:true,
           				resetForm: true,
           				beforeSubmit:function(formData, jqForm, options){          					
           					formData[2].value = $("#addTeamForm #password").val()
           					console.log(formData);
           				},
           				success: function(result){
           			    	if(result == "success") {
           			    		fairAlert.success({"msg":"已成功添加培训"});
           			    	}else{
           			    		fairAlert.error({"msg":"添加培训失败"});
           			    	}
           			    	installGrid();
           			    	$('#add_team').modal('hide');
           			    	resetForm("addTeamForm");
           				}
            		});
               window.location.href="<%=request.getContextPath()%>/workTeam.shtml";
            }    
        });
    	$("#editTeamForm #checkPassword").on('ifClicked',function(){
    		if($(this).find('div').hasClass('checked')){
    			$("#editTeamForm #isUpdatePwd").val(false);
    			$('.mm1').hide();
	    		$('.mm2').hide();
    		}else{
    			$("#editTeamForm #isUpdatePwd").val(true);
    			$('.mm1').show();
	    		$('.mm2').show();
    		}
    	});
    	/**
    	 * 检查是否修改面
    	 */
    	function validateUpdatePassword(){
    		if($("#editTeamForm #isUpdatePwd").val() == true || $("#editTeamForm #isUpdatePwd").val() == "true"){
    			var pas = $("#editTeamForm #passwords").val();
    			var past = $("#editTeamForm #password_two").val();
    			if(pas == undefined || pas.length == 0){
    				$("#editTeamForm #password-error").html('<i class="fa fa-times-circle"></i> 必填');
    				$("#editTeamForm #password-error").css("display","block");
    				return false;
    			}else if(pas.length > 12 || pas.length < 6 ){
    				$("#editTeamForm #password-error").html('<i class="fa fa-times-circle"></i> 请输入长度为 6 至 12 之间的字符串');
    				$("#editTeamForm #password-error").css("display","block");
    				return false;
    			}else{
    				$("#editTeamForm #password-error").html('');
    				$("#editTeamForm #password-error").css("display","none");
    				if(past == undefined || past.length == 0){
    					$("#editTeamForm #password_two-error").html('<i class="fa fa-times-circle"></i> 必填');
    					$("#editTeamForm #password_two-error").css("display","block");
    					return false;
    				}else{
    					if(pas != past ){
    						$("#editTeamForm #password_two-error").html('<i class="fa fa-times-circle"></i> 两次输入的值不一致');
    						$("#editTeamForm #password_two-error").css("display","block");
    						return false;
    					}else{
    						$("#editTeamForm #password_two-error").html('');
    						$("#editTeamForm #password_two-error").css("display","none");
    						return true;
    					}
    				}
    			}
    		}else{
    			$("#editTeamForm #password-error").html('');
    			$("#editTeamForm #password-error").css("display","none");
    			$("#editTeamForm #password_two-error").html('');
    			$("#editTeamForm #password_two-error").css("display","none");
    			return true;
    		}
    	}
    	$("#editTeamForm #passwords").blur(function(){
    		validateUpdatePassword();
    	});
    	$("#editTeamForm #password_two").blur(function(){
    		validateUpdatePassword();
    	});
    	// 编辑员工表单提交
    	$("#editTeamForm").validate({
    	    rules: rules, 
    		messages: message,
    		submitHandler:function(form){
    			if(validateUpdatePassword()){
    				$(form).ajaxSubmit({
    					url : "<%=request.getContextPath()%>/addAndUpdateUser.shtml", 
    					dataType: "json",
    					type:"POST",
    					clearForm:true,
    					resetForm: true,
    					beforeSubmit:function(formData, jqForm, options){ 
    						if($("#editTeamForm #passwords").val()!=undefined && $("#editTeamForm #passwords").val().length != 0){
    							formData[4].value = $("#editTeamForm #passwords").val()
    						}
    						console.log(formData);
    					},
    					success: function(result){
    						if(result == "success") {
           			    		fairAlert.success({"msg":"已成功修改培训"});
           			    	}else{
           			    		fairAlert.error({"msg":"修改培训失败"});
           			    	}
    						installGrid();
    						$('#edit_staff').modal('hide');
    						resetForm("editTeamForm");
    					}
    				});
    			}
    		}    
    	});
    });
    </script> 
</body>
</html>