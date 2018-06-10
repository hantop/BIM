<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员管理</title>
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
	                               <label class="col-sm-4 control-label">人员姓名</label>
	                               <div class="col-sm-8">
	                                  <input type="text" class="form-control" id="staffName" name="staffName" value="">
	                               </div>
		            		    </div>
		            		    <div class="col-sm-6">
	                               <label class="col-sm-4 control-label">所属班组</label>
	                               <div class="col-sm-8">
	                                  <select class="chosen-select" id="ofteam" name="ofteam">
										  <option value ="">请选择...</option>
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
			<div  class="modal fade myModal"  role="dialog"  tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content-div">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal" aria-hidden="true">×</button>
							<h3 class="modal-title">二维码</h3>
						</div>
						<div class="modal-body">
							<img id="codeImg" style="height: 300px; width: 300px;" src="">
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						</div>
					</div>

				</div>
			</div>
			<!--员工列表-->
            <div class="ibox">
	            <div class="ibox-title">
                	<h5>人员管理</h5>
	                <div class="ibox-tools">
                        <button type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#add_staff"><i class="fa fa-user-plus"></i> 添加人员</button>
                    </div>
            	</div>
		        <table id="datatable"></table>  
	        </div> 
        </div>
	</div>
	<!-- 添加员工 -->
	<div class="modal inmodal" id="add_staff" role="dialog" aria-hidden="true" data-backdrop="static" style="display: none;">
    	<div class="modal-dialog">
       	    <form id="addStaffForm" class="modal-content animated fadeIn" novalidate="novalidate"> 
           	   <div class="modal-header">
                    <button type="reset" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
				   <h4 class="modal-title text-left">新增劳务人员</h4>
                  </div>
                <div class="modal-body">
					<div class="form-horizontal ">
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="realName"><span
										class="form-valid-field">*</span>员工名：</label>
		                    <div class="col-sm-8">
								<input type="hidden" class="form-control" name="id" id="workerId"/>
								<input type="text" class="form-control" name="realName" id="realName"
									   data-rule-required="true" data-rule-rangelength="[2,12]"
									   data-msg-rangelength="请输入正确的用户名">
		                    </div></div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="sex"><span class="form-valid-field">*</span>性别：</label>
								<div class="col-sm-8">
									<select class="form-control" name="sex" id="sex" data-rule-required="true"
											data-live-search="true">
										<option value="">请选择...</option>
										<option value="0">男</option>
										<option value="1">女</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="age"><span class="form-valid-field">*</span>年龄：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="age" id="age"
										   data-rule-required="true" data-rule-rangelength="[0,3]"
										   data-msg-rangelength="请输入正确的年龄">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="nation"><span
										class="form-valid-field">*</span>民族：</label>
								<div class="col-sm-8">
									<select class="form-control" name="nation" id="nation" data-rule-required="true"
											data-live-search="true">
										<option value="">请选择...</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="idCard"><span
										class="form-valid-field">*</span>身份证：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="idCard" id="idCard"
										   data-rule-required="true" data-rule-rangelength="[18,18]"
										   data-msg-rangelength="请输入正确的身份证">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label"><span
										class="form-valid-field">*</span>合同状态：</label>
								<div class="col-sm-8">
									<select class="form-control" name="contractStatus" id="contractStatus"
											data-rule-required="true" data-live-search="true">
										<option value="">请选择...</option>
										<option value="0">未签合同</option>
										<option value="1">已签合同</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="type"><span class="form-valid-field">*</span>身份：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="type" id="type"
										   data-rule-required="true" data-rule-rangelength="[0,3]"
										   data-msg-rangelength="请输入正确的身份">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="station"><span
										class="form-valid-field">*</span>岗位：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="station" id="station"
										   data-rule-required="true" data-rule-rangelength="[0,10]"
										   data-msg-rangelength="请选择正确的岗位">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="department"><span
										class="form-valid-field">*</span>部门：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="department" id="department"
										   data-rule-required="true" data-rule-rangelength="[0,10]"
										   data-msg-rangelength="请输入正确的部门">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="team"><span
										class="form-valid-field">*</span>所属班组：</label>
								<div class="col-sm-8">
									<select class="form-control" name="teamId" id="team"
											data-rule-required="true" data-live-search="true">
										<option value="">请选择...</option>
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
       	   <form id="editStaffForm" class="modal-content animated fadeIn" novalidate="novalidate" >
           	   <div class="modal-header">
                    <button type="reset" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title text-left">编辑员工</h4>
                  </div>
                <div class="modal-body">
					<div class="form-horizontal ">
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="realNameU"><span
										class="form-valid-field">*</span>员工名：</label>
								<div class="col-sm-8">
									<input type="hidden" class="form-control" name="id" id="workerIdU"/>
									<input type="text" class="form-control" name="realName" id="realNameU"
										   data-rule-required="true" data-rule-rangelength="[2,12]"
										   data-msg-rangelength="请输入正确的用户名">
								</div></div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="sexU"><span class="form-valid-field">*</span>性别：</label>
								<div class="col-sm-8">
									<select class="form-control" name="sex" id="sexU" data-rule-required="true"
											data-live-search="true">
										<option value="">请选择...</option>
										<option value="0">男</option>
										<option value="1">女</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="ageU"><span class="form-valid-field">*</span>年龄：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="age" id="ageU"
										   data-rule-required="true" data-rule-rangelength="[0,3]"
										   data-msg-rangelength="请输入正确的年龄">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="nationU"><span
										class="form-valid-field">*</span>民族：</label>
								<div class="col-sm-8">
									<select class="form-control" name="nation" id="nationU" data-rule-required="true"
											data-live-search="true">
										<option value="">请选择...</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="idCardU"><span
										class="form-valid-field">*</span>身份证：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="idCard" id="idCardU"
										   data-rule-required="true" data-rule-rangelength="[18,18]"
										   data-msg-rangelength="请输入正确的身份证">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label"><span
										class="form-valid-field">*</span>合同状态：</label>
								<div class="col-sm-8">
									<select class="form-control" name="contractStatus" id="contractStatusU"
											data-rule-required="true" data-live-search="true">
										<option value="">请选择...</option>
										<option value="0">未签合同</option>
										<option value="1">已签合同</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="typeU"><span class="form-valid-field">*</span>身份：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="type" id="typeU"
										   data-rule-required="true" data-rule-rangelength="[0,3]"
										   data-msg-rangelength="请输入正确的身份">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="stationU"><span
										class="form-valid-field">*</span>岗位：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="station" id="stationU"
										   data-rule-required="true" data-rule-rangelength="[0,10]"
										   data-msg-rangelength="请选择正确的岗位">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="departmentU"><span
										class="form-valid-field">*</span>部门：</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" name="department" id="departmentU"
										   data-rule-required="true" data-rule-rangelength="[0,10]"
										   data-msg-rangelength="请输入正确的部门">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<label class="col-sm-3 control-label" for="teamU"><span
										class="form-valid-field">*</span>所属班组：</label>
								<div class="col-sm-8">
									<select class="form-control" name="teamId" id="teamU"
											data-rule-required="true" data-live-search="true">
										<option value="">请选择...</option>
									</select>
								</div>
							</div>
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.include.js?V=1.3"></script>
     <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js?v=1.3"></script>   
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/plugins/jsTree/jstree.min.js?V=1.3"></script>
    <script type="text/javascript">
        var national = [
            "汉族", "壮族", "满族", "回族", "苗族", "维吾尔族", "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族", "瑶族", "朝鲜族", "白族", "哈尼族",
            "哈萨克族", "黎族", "傣族", "畲族", "傈僳族", "仡佬族", "东乡族", "高山族", "拉祜族", "水族", "佤族", "纳西族", "羌族", "土族", "仫佬族", "锡伯族",
            "柯尔克孜族", "达斡尔族", "景颇族", "毛南族", "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族", "京族", "基诺族", "德昂族", "保安族",
            "俄罗斯族", "裕固族", "乌孜别克族", "门巴族", "鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族"
        ];
        $(document).ready(function () {

    	// 装载列表
    	function installGrid(){
    		$("#datatable").jqGrid({
    			url : "<%=request.getContextPath()%>/getWorkerList.shtml", // 请求地址
    			showExport:false,
    			data: {staffName: $("#searchForm #staffName").val(),userRoleId: $("#searchForm #ofteam").val()},
    			columns : [ 
    			    {field : "id",title : "用户标识",width : "0%",visible:false},
                    {field : "teamId",title : "所属班组",width : "0%",visible:false},
                    {field : "realName",title : "姓名",width : "10%",align: 'center',valign: 'middle'},
    			    {field : "sex",title: '性别',width : "10%",align: 'center',valign: 'middle'},
    			    {field : "age",title: '年龄',width : "5%",align: 'center',valign: 'middle'},
    			    {field : "nation",title: '民族',width : "5%",align: 'center',valign: 'middle'},
    			    {field : "idCard",title: '身份证',width : "10%",align: 'center',valign: 'middle'},
                    {field : "contractStatus",title: '合同状态',width : "10%",align: 'center',valign: 'middle'},
                    {field : "type",title: '身份',width : "10%",align: 'center',valign: 'middle'},
                    {field : "cardTime",title: '进场时间',width : "10%",align: 'center',valign: 'middle'},
                    {field : "cardTime",title: '退场时间',width : "10%",align: 'center',valign: 'middle'},
                    {field : "station",title: '岗位',width : "10%",align: 'center',valign: 'middle'},
                    {field : "department",title: '部门',width : "10%",align: 'center',valign: 'middle'},
    			    {field : "operate",title : "操作",width : "10%",align: 'center',valign: 'middle',
    			    	formatter:function(value,row,index){
    						return   "<a href='javascript:void(0);' class='edit_party'>编辑</a>&nbsp;"  			    		
    								+"<a href='javascript:void(0);' class='remove_party'>&nbsp;删除</a>";					    		
    				    },
                        events: {
    				    	'click .edit_party': function (e, value, row, index) {
    				    	    $("#realNameU").val(row.realName);
                                $("#sexU").val(row.sex);
                                $("#ageU").val(row.age);
                                $("#nationU").val(row.nation);
                                $("#idCardU").val(row.idCard);
                                $("#contractStatusU").val(row.contractStatus);
                                $("#typeU").val(row.type);
                                $("#stationU").val(row.station);
                                $("#departmentU").val(row.department);
                                $("#teamU").val(row.teamId);
                                $("#workerIdU").val(row.id);
    				    	    $('#edit_staff').modal();
    			   			},
    				    	'click .remove_party': function (e, value, row, index) {
    				    		fairAlert.confirm({
    				    			msg:'您确定要删除该用户吗？删除后不可恢复！',
    				    			confirmCallback:function(){
    									ajaxPostJson({
    										url:"<%=request.getContextPath()%>/worker/removeWorker.shtml",
    										data:{id:row.id},
    										success:function(result){   										   
    								    	    if(result.status == 200) {
    								    			fairAlert.success({"msg":"已成功删除用户"});
    								    			installGrid();
    								    	    }else{
    												fairAlert.error({"msg":"删除用户失败"});
    								    	    }
    								    	}
    									});
    								}
    				    		})
    			   			},
    			   		}
                    },
                ],
    		});
    	}
        readyChangePeople(1);changTeam(1);
    	readyChangePeople(2);changTeam(2);
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
                    	return $("#addStaffForm #userName").val();
                    }}
                }
            }
    	}; 
    	var message = {userName: { remote:e+"员工名已经存在"}};
            // 添加劳务人员表单提交
    	$("#addStaffForm").validate({   		
             rules: rules, 
            messages: message,           
            submitHandler:function(form){
            		$(form).ajaxSubmit({
                        url: "<%=request.getContextPath()%>/worker/addWorker.shtml",
           			    dataType: "json",
           			    type: 'POST',
           			 	clearForm:true,
           				resetForm: true,
           				success: function(result){
           			    	if(result.msg == "OK") {
           			    		fairAlert.success({"msg":"已成功添加用户"});
           			    	}else{
           			    		fairAlert.error({"msg":"添加用户失败"});
           			    	}
           			    	installGrid();
           			    	$('#add_staff').modal('hide');
           			    	resetForm("addStaffForm");
           				}
            		});
                window.location.href = "<%=request.getContextPath()%>/worker.shtml";
            }    
        });
    	// 编辑员工表单提交
    	$("#editStaffForm").validate({
    	    rules: rules, 
    		messages: message,
    		submitHandler:function(form){
    				$(form).ajaxSubmit({
    					url : "<%=request.getContextPath()%>/worker/updateWorker.shtml",
    					dataType: "json",
    					type:"POST",
    					clearForm:true,
    					resetForm: true,
    					beforeSubmit:function(formData, jqForm, options){
    						console.log(formData);
    					},
    					success: function(result){
    						if(result == "success") {
           			    		fairAlert.success({"msg":"已成功修改用户"});
           			    	}else{
           			    		fairAlert.error({"msg":"修改用户失败"});
           			    	}
    						installGrid();
    						$('#edit_staff').modal('hide');
    						resetForm("editStaffForm");
    					}
    				});
                window.location.href = "<%=request.getContextPath()%>/worker.shtml";
    		}    
    	});

    });
        //动态的实现班组的选择
        function changTeam(type){
            var team;
			switch (type) {
                case 1:
                    team = document.getElementById("team");
                    break;
                case 2:
                    team = document.getElementById("teamU");
                    break;
            }
            var url="/workteam/getAllWorkrTeam.shtml";
            $.post(url,function(result){
                for(var i=0;i<result.data.length;i++){
                    var option = document.createElement('option');
                    option.value = result.data[i].id;
                    option.innerHTML = result.data[i].teamName;
                    team.appendChild(option);
                }
            });
        }



        //新增劳务人员时。民族属性选择下拉框生成
        function readyChangePeople(type) {
            var people;
            switch (type) {
                case 1:
                    people = document.getElementById("nation");
                    break;
                case 2:
                    people = document.getElementById("nationU");
                    break;
            }
            for (var i = 0; i < national.length; i++) {
                var option = document.createElement('option');
                option.value = national[i];
                option.innerHTML = national[i];
                people.appendChild(option);
            }
        }
	</script>
</body>
</html>