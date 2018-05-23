<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/plugins/iCheck/custom.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/plugins/alert/jquery-alert.css?V=1.1" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/css/page-style.css?v=1.1" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<!-- 查询条件 -->
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>查询条件</h5>
				</div>
				<div class="ibox-content">
					<form class="form-horizontal" id="search_ibox">
						<div class="form-group">
							<div class="col-sm-5">
								<label class="col-sm-4 control-label"><span
									class="req_field" data-require-from="add-rolename">*&nbsp</span>角色名称</label>
								<div class="col-sm-8">
									<input type="text" class="form-control" id="search_rolename"
										value="">

								</div>
							</div>
							<div class="col-sm-3">
								<button type="button" class="btn btn-primary pull-right"
									id="query_role">
									<i class="fa fa-search"></i> 查询
								</button>
							</div>
							<div class="col-sm-3">
								<button id="rs" type="reset"
									class="btn btn-outline btn-default pull-left">
									<i class="fa fa-reply"></i> 重置
								</button>
							</div>
						</div>
						
					</form>
				</div>
			</div>
			<!--订单列表-->
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>角色管理</h5>
					<div class="ibox-tools">
						<button type="button" id="addrole_btn"
							class="btn btn-w-m btn-primary">
							<i class="fa fa-user-plus"></i> 添加角色
						</button>
					</div>
				</div>
				<table id="datatable"></table>
			</div>
		</div>

		<!-- 新建角色弹窗 -->
		<div class="modal inmodal in" id="addrole_box" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated flipInY">
					<div class="modal-header">
						<h4>新增角色</h4>
					</div>
					<div class="modal-body">
						<div class="form-horizontal">
							<div class="form-group">
								<div class="col-sm-10">
									<label class="col-sm-3 control-label"><span
										class="req_field" data-require-from="add-rolename">*&nbsp</span>角色名称</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" id="add-rolename"
											name="add-rolename">
									</div>
								</div>
							</div>

							<!-- <div class="form-group">
			            		    <div class="col-sm-10">
			                               <label class="col-sm-3 control-label"><span class="req_field"  data-require-from="add-remark">*&nbsp</span>备注</label>
			                               <div class="col-sm-8">
			                                  <textarea id="add-remark" class="form-control" name="add-remark"></textarea>
			                               </div>
			            		    </div>			            		    
		                        </div> -->
							<div class="hr-line-dashed"></div>
							<!-- 全部权限   多选 -->
							<div class="form-group">
								<div class="col-sm-10">
									<label class="col-sm-3 control-label"><span
										class="req_field" data-require-from="all_checks">*&nbsp</span>选择权限</label>
									<div class="col-sm-8">
										<div id="check_all" class="checkbox i-checks"
											data-group="checkbox" data-toggle="checkbox"
											data-check-to="#check_items">
											<label><input type="checkbox" data-group="group01"
												id="all_checks"> <i></i> 全选</label>
										</div>
									</div>
								</div>
							</div>
							<div class="role_check" id="check_items">
								<c:forEach var="dicParent" items="${dicParentList}" begin="0"
									varStatus="v1">
									<c:if
										test="${dicParent.consoleUrl == null || dicParent.consoleUrl =='' }">
										<div class="level_1 ">
											<div id="check_items_${v1.index}" class="checkbox i-checks"
												data-group="checkbox" data-toggle="checkbox"
												data-check-from="#check_all"
												data-check-to="#${dicParent.id}"
												data-permission-id="${dicParent.id}"
												data-permission-name="${dicParent.directoryName}">
												<label><input type="checkbox" data-group="group01">
													<i></i>${dicParent.directoryName}</label>
											</div>
										</div>

										<div class="level_2 col-sm-offset-1">
											<ul class="clearfix list-unstyled list-inline"
												id="${dicParent.id}">
												<c:forEach var="dic" items="${dicList}" begin="0">
													<c:if test="${dicParent.id==dic.parentNavigator}">
														<li>
															<div class="checkbox i-checks" data-group="checkbox"
																data-check-from="#check_items_${v1.index}"
																data-toggle="checkbox" data-permission-id="${dic.id}"
																data-permission-name="${dic.directoryName}">
																<label><input type="checkbox"
																	data-group="group01"> <i></i>${dic.directoryName}</label>
															</div>
														</li>
													</c:if>
												</c:forEach>
											</ul>
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" id="close_addrole">关闭</button>
							<button type="button" class="btn btn-primary" id="save_addrole">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- 编辑角色弹窗 -->
		<div class="modal inmodal in" id="updaterole_box" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated flipInY">
					<div class="modal-header">
						<h4>编辑角色</h4>
					</div>
					<div class="modal-body">
						<div class="form-horizontal">
							<div class="form-group">
								<div class="col-sm-10">
									<label class="col-sm-3 control-label"><span
										class="req_field" data-require-from="update-rolename">*&nbsp</span>角色名称</label>
									<div class="col-sm-8">
										<input type="text" class="form-control" id="update-rolename"
											name="update-rolename">
									</div>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-10">
									<label class="col-sm-3 control-label"><span
										class="req_field" data-require-from="update_all_checks">*&nbsp</span>选择权限</label>
									<div class="col-sm-8">
										<div id="update_check_all" class="checkbox i-checks"
											data-group="checkbox02" data-toggle="checkbox"
											data-check-to="#update_check_items">
											<label><input type="checkbox" data-group="group02"
												id="update_all_checks"> <i></i> 全选</label>
										</div>
									</div>
								</div>
							</div>
							<div class="role_check" id="update_check_items">
								<c:forEach var="dicParent" items="${dicParentList}" begin="0"
									varStatus="v1">
									<c:if
										test="${dicParent.consoleUrl == null || dicParent.consoleUrl =='' }">
										<div class="level_1">
											<div id="update_check_items_${v1.index}"
												class="checkbox i-checks" data-group="checkbox02"
												data-toggle="checkbox" data-check-from="#update_check_all"
												data-check-to="#u_${dicParent.id}"
												data-permission-id="${dicParent.id}"
												data-permission-name="${dicParent.directoryName}">
												<label><input type="checkbox" data-group="group02">
													<i></i>${dicParent.directoryName}</label>
											</div>
										</div>
										<div class="level_2 col-sm-offset-1">
											<ul class="clearfix  list-unstyled list-inline"
												id="u_${dicParent.id}">
												<c:forEach var="dic" items="${dicList}" begin="0">
													<c:if test="${dicParent.id==dic.parentNavigator}">
														<li>
															<div class="checkbox i-checks" data-group="checkbox02"
																data-check-from="#update_check_items_${v1.index}"
																data-toggle="checkbox" data-permission-id="${dic.id}"
																data-permission-name="${dic.directoryName}">
																<label><input type="checkbox"
																	data-group="group02"> <i></i>${dic.directoryName}</label>
															</div>
														</li>
													</c:if>
												</c:forEach>
											</ul>
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" id="close_updaterole">关闭</button>
							<button type="button" class="btn btn-primary"
								id="save_updaterole">保存</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.include.js?V=1.3"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js?v=1.2"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/plugins/jsTree/jstree.min.js?V=1.3"></script>
<script type="text/javascript">
     $(document).ready(function() {  		
    		$("#query_role").click(queryRoleData);				
//    			复选框
    			$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green"});
    			$('.i-checks').on('ifClicked',$.onChecked);
    			
    			$('#addrole_btn').click(function(){
    				$('.i-checks').iCheck('uncheck');
    				$('#add-rolename').val('');
    				
    				$('#addrole_box').show();
    			});
    			
    			$('#close_addrole').click(function(){
    				$('#addrole_box').hide();
    			});
    			$("#save_addrole").on('click',function(){
    				var flag = $('#addrole_box .modal-body').checkReqfield();
    				var obj= {
    						selector : '#check_items',
    						rolenameSlctor :'#add-rolename',
    						  						
    				};
    				if(flag){
    					addRole(obj);
    					$('#addrole_box').hide();
    				}
    			});
    			
    			$('#close_updaterole').click(function(){
    				$('#updaterole_box').hide();
    			})
    			
    			$("#save_updaterole").on('click',function(){
    				var flag = $('#updaterole_box .modal-body').checkReqfield();
    				if(flag){
    					updateRole('#update_check_items','#update-rolename','#update-remark');
    					$('#updaterole_box').hide();
    				}
    			});
    			
    			function queryRoleData(){
    				$("#datatable").jqGrid({
    					url :"<%=request.getContextPath()%>/getStaffRoleList.shtml", // 请求地址
    					showExport:false,
    					data: {roleName: $("#search_rolename").val()},
    					columns : [ 
    					    {field : "id",title : "角色标识",width : "0%",visible:false}, 
    					    {field : "roleName",title: '角色名称',width : "15%",align: 'center',valign: 'middle'}, 
    					    {field : "remark",title : "权限",width : "70%",align: 'center',valign: 'middle'}, 
    					    {field : "operate",title : "操作",width : "15%",align: 'center',valign: 'middle',
    					    	formatter:function(value,row,index){
    								return "<a href='javascript:void(0);' class='trans-info'>&nbsp;编辑</a>&nbsp<a href='javascript:void(0);' class='remove'>&nbsp;删除</a>";					    		
    						    },
    						    events:{
    						    	'click .trans-info': function (e, value, row, index) {
    						    		console.log('点击编辑')
    						    		$('#updaterole_box').show();
    						    		$('.i-checks').iCheck('uncheck');
    						    		$('input#update-rolename').val(row.roleName);
    						    		$('input#update-rolename').data('updateroleid',row.id);
    						    		$('input#update-rolename').data('updateorgid',row.orgId);
    						    		 var arr = row.remark.split(',');
    						    		$('.i-checks').each(function(index, el) {
    						    			var _that = this;
    						    			var _pname = $(this).data('permissionName');
    						    			$.each(arr,function (i,el) {
    						    				if (this==_pname) {
    						    					$(_that).iCheck('check');
    						    				};
    						    			});
    						    		}); 
    					   			},
    						    	'click .remove': function (e, value, row, index) {    						    		
    						    		fairAlert.confirm({
    						    			msg:'您确定要删除该角色吗？删除后不可恢复！',
						    				confirmCallback:function (){
    	    									ajaxPostJson({
    	    										url:"<%=request.getContextPath()%>/removeRole.shtml",
    	    										data:{roleId : row.id},
    	    										success:function(data){   										   
    	    								    	    if(data == "success") {
    	    								    			fairAlert.success({"msg":"已成功删除角色"});
    	    								    			queryRoleData();
    	    								    	    }else{
    	    												fairAlert.error({"msg":"删除角色失败"});
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
    			queryRoleData();
    			
    			
    			
    			$.fn.checkReqfield = function () {
    				var fa = true;
    				$(this).find('.req_field').each(function(i,e){
    					var req_id = $(this).data('requireFrom');
    			    	if ($('#'+req_id).attr('type')=='text'||$('#'+req_id).attr('type')=='password') {
    				    	var req_val = $('#'+req_id).val().trim();
    				    	if (!req_val) {
    				    		alert('不能为空1');
    				    		fa = false;
    				    	};
    			    	};
    			    	if ($('#'+req_id).attr('type')=='checkbox'||$('#'+req_id).attr('type')=='radio') {
    			    		var req_val = $('#'+req_id+':checked').val();
    			    		var checknum = 0;
    				    	if (!req_val) {
    				    		var groupname = $('#'+req_id).data("group");
    				    		$('input[data-group="'+groupname+'"]').each(function(index, el) {
    				    			if ($('input:checked').val()) {
    									checknum++;
    				    			};
    				    		});
    				    		if(!checknum){
    				    			alert('不能为空2');
    				    			fa = false;
    				    		};
    				    	};
    			    	};
    				})
    				return fa;
    		    };

    			//添加角色
    			function addRole(obj){
    				var roleName = $(obj.rolenameSlctor).val();  				
    				var pstr = '';
    				var pst = '';
    				$(obj.selector).find('.i-checks').each(function(i, el) {
    					var pid = $(this).data('permissionId');   					
    					var pname = $(this).data('permissionName');  					
    					if($(this).find('div').hasClass('checked')){
    						pstr += '{id:'+'"'+pid+'",directoryName:'+'"'+pname+'"},';
    						pst +=''+pname+',';
    					};
    				});
    				var plist = "["+pstr.slice(0,-1)+"]";
    				var remark = ""+pst.slice(0,-1)+"";
    					$.ajax({
    						url:"<%=request.getContextPath()%>/addAndUpdateRole.shtml",
    						type: 'POST',
    						data: {roleName: roleName,remark: remark,
    								permissionList:plist
    						},
    						success: function(data){
    							if (data=="success") {
    								alert('数据保存成功');
    								queryRoleData();
    								$('#addrole_box').hide();
    							}else{
    								alert('保存失败');
    							};
    						}
    					});
    			};
    			//编辑角色
    			function updateRole(selector,rolenameSlctor,remarkSlctor){
    				var roleName = $(rolenameSlctor).val();   				
    				var id = $(rolenameSlctor).data('updateroleid');
    				var orgId = $(rolenameSlctor).data('updateorgid');
    				console.log(roleName+'==='+id+'==='+orgId)
    				var pstr = '';
    				var pst = '';
    				$(selector).find('.i-checks').each(function(i, el) {
    					var pid = $(this).data('permissionId');
    					var pname = $(this).data('permissionName');
    					if($(this).find('div').hasClass('checked')){
    						pstr += '{id:'+'"'+pid+'",directoryName:'+'"'+pname+'"},';
    						pst +=''+pname+',';
    					};
    				});
    				var plist = "["+pstr.slice(0,-1)+"]";
    				var remark = ""+pst.slice(0,-1)+"";
    				console.log(plist+'授权列表')
    					$.ajax({
    						url:"<%=request.getContextPath()%>/addAndUpdateRole.shtml",
										type : 'POST',
										data : {
											roleName : roleName,
											id : id,
											remark : remark,
											permissionList : plist
										},
										success : function(data) {
											if (data == "success") {
												alert('数据保存成功');
												queryRoleData();
												$('#myModal2').hide();
											} else {
												alert('保存失败');
											}
											;
										}
									});
						}
						;

					});
</script>


</html>