$(document).ready(function() { 
	// 查询
	$("#searchForm #orgId").change(function(){
		  var orgId = $(this).val();
		  var html = '<option value="">请选择...</option>';
		  if(orgId == undefined || orgId == "" || orgId==null || orgId == "000000"){
			  $('#searchForm #depId').html(html);
			  $("#searchForm #depId").trigger("chosen:updated");
		  }else{
			  $(this).chosenValChange({
				  data:{orgId:$(this).val()},
				  url:"getGroupData.shtml",
				  success:function(resultJson){
					  var resultObj = eval(resultJson);
			    	  if(resultObj.type == "S") {
			    		 var data = resultObj.dataList;
			    		 for(var i=0; i<data.length; i++){
			    			html = html + '<option value="'+data[i].depId+'">'+data[i].depName+'</option>';
			    		 }
			    		 $('#searchForm #depId').html(html);
						 $("#searchForm #depId").trigger("chosen:updated");
			         }
				  }
			  });
		  }
	  });
	// 添加员工下拉列表
	$("#addStaffForm #orgId").change(function(){
		var orgId = $(this).val();
		var html = '<option value="">请选择...</option>';
		if(orgId == undefined || orgId == "" || orgId==null || orgId == "000000"){
		    $('#addStaffForm #depId').html(html);
		    $("#addStaffForm #depId").trigger("chosen:updated");
	    }else{
		    $(this).chosenValChange({
			    data:{orgId:$(this).val()},
			    url:"getGroupData.shtml",
			    success:function(resultJson){
				    var resultObj = eval(resultJson);
		    	    if(resultObj.type == "S") {
		    		   var data = resultObj.dataList;
		    		   for(var i=0; i<data.length; i++){
		    			   html = html + '<option value="'+data[i].depId+'">'+data[i].depName+'</option>';
		    		   }
		    		   $('#addStaffForm #depId').html(html);
					   $("#addStaffForm #depId").trigger("chosen:updated");
		           }
			    }
		   });
	    }
	});
	// 编辑下拉列表
	$("#editStaffForm #orgId").change(function(){
		var orgId = $(this).val();
		var html = '<option value="">请选择...</option>';
		if(orgId == undefined || orgId == "" || orgId==null){
		    $('#editStaffForm #depId').html(html);
		    $("#editStaffForm #depId").trigger("chosen:updated");
		}else{
		    $(this).chosenValChange({
			    data:{orgId:$(this).val()},
			    url:"getGroupData.shtml",
			    success:function(resultJson){
				    var resultObj = eval(resultJson);
		    	    if(resultObj.type == "S") {
		    		   var data = resultObj.dataList;
		    		   for(var i=0; i<data.length; i++){
		    			   html = html + '<option value="'+data[i].depId+'">'+data[i].depName+'</option>';
		    		   }
		    		   $('#editStaffForm #depId').html(html);
					   $("#editStaffForm #depId").trigger("chosen:updated");
		           }
			    }
		   });
	    }
	});
	
	// 装载列表
	function installGrid(){
		$("#datatable").jqGrid({
			url : "getUserList.shtml", // 请求地址
			data: {userName: $("#searchForm #userName").val(),orgId: $("#searchForm #orgId").val(),depId: $("#searchForm #depId").val()},
			columns : [ 
			    {field : "partyId",title : "员工标识",width : "0%",visible:false}, 
			    {field : "orgId",title : "机构标识",width : "0%",visible:false}, 
			    {field : "depId",title : "部门标识",width : "0%",visible:false}, 
			    {field : "partyName",title: '姓名',width : "10%",align: 'center',valign: 'middle'}, 
			    {field : "userName",title: '用户名',width : "10%",align: 'center',valign: 'middle'}, 
			    {field : "orgName",title: '所属机构',width : "10%",align: 'center',valign: 'middle'}, 
			    {field : "depName",title: '所属部门',width : "10%",align: 'center',valign: 'middle'}, 
			    {field : "emailCode",title: '邮箱',width : "10%",align: 'center',valign: 'middle'}, 
			    {field : "phoneCode",title: '手机',width : "10%",align: 'center',valign: 'middle'}, 
			    {field : "roleNames",title: '角色',width : "15%",align: 'center',valign: 'middle'}, 
			    {field : "memberNum",title: '授权人数',width : "5%",align: 'center',valign: 'middle'}, 
			    {field : "operate",title : "操作",width : "15%",align: 'center',valign: 'middle',
			    	formatter:function(value,row,index){
						return   "<a href='javascript:void(0);' class='edit_party'>编辑</a>&nbsp;"  
								+"<a href='javascript:void(0);' class='add_group'>授权</a>&nbsp;"				    		
								+"<a href='javascript:void(0);' class='remove_party'>&nbsp;删除</a>";					    		
				    },
				    events:{
				    	'click .edit_party': function (e, value, row, index) {
				    		$("#editStaffForm #orgId").val(row.orgId);
				    		// 查询部门
				    		var html = '<option value="">请选择...</option>';
				    		$(this).chosenValChange({
							    data:{orgId:row.orgId},
							    url:"getGroupData.shtml",
							    success:function(resultJson){
								    var resultObj = eval(resultJson);
						    	    if(resultObj.type == "S") {
						    		   var data = resultObj.dataList;
						    		   for(var i=0; i<data.length; i++){
						    			   html = html + '<option value="'+data[i].depId+'">'+data[i].depName+'</option>';
						    		   }
						    		   $('#editStaffForm #depId').html(html);
									   $("#editStaffForm #depId").trigger("chosen:updated");
									   
									   $("#editStaffForm #depId").val(row.depId);
						           }
							    }
						    });
				    	    // 查询员工对应的角色
				    		ajaxPostJson({
				    			url:"getUserRoleList.shtml",
				    			data:{partyId:row.partyId},
				    			success:function(resultJson){
								    var resultObj = eval(resultJson);
						    	    if(resultObj.type == "S") {
						    		   var data = resultObj.dataList;
						    		   $.each(data,function(_key){
					    				   $("#editStaffForm .i-checks").each(function(){
					    					   var p_key = $(this).data("key");
					    					   if(data[_key].roleId == $(this).data("key")){
					    						   $(this).iCheck("check");
					    					   }
					    				   });
					    			   });
						    		   
						    		   $("#editStaffForm #roleList").val(JSON.stringify(data));
						           }
							    }
				    		});
				    		$("#editStaffForm #isUpdatePwd").val(false);
				    		$("#editStaffForm #partyId").val(row.partyId);
				    	    $("#editStaffForm #partyName").val(row.partyName);
				    	    $("#editStaffForm #userName").val(row.userName);
				    	    $("#editStaffForm #emailCode").val(row.emailCode);
				    	    $("#editStaffForm #phoneCode").val(row.phoneCode);
				    	   
				    	    $('#edit_staff').modal();
			   			},
				    	'click .add_group': function (e, value, row, index) {
				    		ajaxPostJson({
								url:"getUserData.shtml",
								data:{partyId:row.partyId},
								success:function(resultJson){
								    var resultObj = eval(resultJson);
						    	    if(resultObj.type == "S") {
						    	    	$("#authorizeModel #jsTree").jstree({
											"plugins" : ["search","checkbox"], //出现选择框
								            "checkbox": { cascade: false, three_state: true }, //不级联
								            "core":{"data":resultObj.data}
						    	    	}).bind("loaded.jstree",function(e,data){
						    	    		var s_data = resultObj.s_data;
						    	    		$.each(s_data,function(_key){
						    	    			$("#authorizeModel #jsTree").find("li").each(function(){
						    	    				if($(this).attr("id") == s_data[_key].partyId){  
						    	    					$("#authorizeModel #jsTree").jstree("check_node",$(this));  
						    	    				}  
						    	    			});
						    	    		});
						    	    	});
						    	    	
						    	    	$("#authorizeModel #partyId").val(row.partyId);
						    	    	$('#authorizeModel').modal();
						    	    }
						    	}
							});
			   			},
				    	'click .remove_party': function (e, value, row, index) {
				    		fairAlert.confirm({
				    			msg:'您确定要删除该员工吗？删除后不可恢复！',
				    			confirmCallback:function(){
									ajaxPostJson({
										url:"removeUser.shtml",
										data:{partyId:row.partyId},
										success:function(resultJson){
										    var resultObj = eval(resultJson);
								    	    if(resultObj.type == "S") {
								    			fairAlert.success({"msg":"已成功删除用户"});
								    			installGrid();
								    	    }else{
												fairAlert.error({"msg":"删除员工失败,"+resultObj.message});
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
	
	var to = false;  
	$('#query_tree').keyup(function () {  
	    if(to) {  
	      clearTimeout(to);   
	    }  
	  
	    to = setTimeout(function () {  
	        $('#jsTree').jstree(true).search($('#query_tree').val());  
	  
	    }, 250);  
	});  
	
	
	// 查询事件
	$("#query_button").click(installGrid);
	
	// 检查角色选择必输项
	function roleListValidate(el_id){
		if($(el_id).val() == undefined || $(el_id).val().length == 0){
			$(el_id+"-error").css("display","block");
			return false;
		}else{
			$(el_id+"-error").css("display","none");
			return true;
		}
	}
	// 添加员工 点击事件
	$('#add_staff_role .i-checks').on('ifClicked',function(){
		var objJson = [];
		if($("#addStaffForm #roleList").val() == undefined || $("#addStaffForm #roleList").val().length == 0){
			objJson = []; 
		}else{
			objJson = jQuery.parseJSON($("#addStaffForm #roleList").val());
		}
		// 点击后的状态
		var k = $(this).data("key"),v = $(this).data("value");
		if(!$(this).find('div').hasClass('checked')){
			objJson.push(jQuery.parseJSON('{"roleId":"' + $(this).data("key") + '","roleName":"' + $(this).data("value") + '"}')); 
		}else{
			$.each(objJson,function(_key){
				if(objJson[_key].roleId == k){
					delete objJson[_key];
				}
			});
		}
		var json = [];
		$.each(objJson,function(_key){
			if(objJson[_key] != undefined && objJson[_key] !=null){
				json.push(jQuery.parseJSON('{"roleId":"' + objJson[_key].roleId + '","roleName":"' + objJson[_key].roleName + '"}')); 
			}
		});
		if(json.length == 0){
			$("#addStaffForm #roleList").val("");
		}else{
			$("#addStaffForm #roleList").val(JSON.stringify(json));
		}
		roleListValidate("#addStaffForm #roleList");
	});
	
	// 表单验证 
	var e = "<i class='fa fa-times-circle'></i>";
	var rules = {
		userName: {
            remote: {
                url: "checkLogin.shtml",
                type: "get",
                dataType: "json",
                data: {userName: function(){
                	return $("#addStaffForm #userName").val();
                }}
            }
        }
	};
	var message = {userName: { remote:e+"用户名已经存在"}};
	
	// 添加员工表单提交
	$("#addStaffForm").validate({
        rules: rules,
        messages: message,
        submitHandler:function(form){
        	if(roleListValidate("#addStaffForm #roleList")){
        		$(form).ajaxSubmit({
        			url : "addUser.shtml", 
       			    dataType: "json",
       			    type:"post",
       			 	clearForm:true,
       				resetForm: true,
       				beforeSubmit:function(formData, jqForm, options){
       					formData[6].value = $.md5($("#addStaffForm #password").val())
       					console.log(formData);
       				},
       				success: function(responseText, statusText){
       					var resultObj = eval(responseText);
       			    	if(resultObj.type == "S") {
       			    		fairAlert.success();
       			    	}else{
       			    		fairAlert.error({"msg":"添加员工失败,"+resultObj.message});
       			    	}
       			    	installGrid();
       			    	$('#add_staff').modal('hide');
       			    	resetForm("addStaffForm");
       				}
        		});
        	}
        }    
    });
	$("#editStaffForm #checkPassword").on('ifClicked',function(){
		if($(this).find('div').hasClass('checked')){
			$("#editStaffForm #isUpdatePwd").val(false);
		}else{
			$("#editStaffForm #isUpdatePwd").val(true);
		}
	});
	/**
	 * 检查是否修改面
	 */
	function validateUpdatePassword(){
		if($("#editStaffForm #isUpdatePwd").val() == true || $("#editStaffForm #isUpdatePwd").val() == "true"){
			var pas = $("#editStaffForm #password").val();
			var past = $("#editStaffForm #password_two").val();
			if(pas == undefined || pas.length == 0){
				$("#editStaffForm #password-error").html('<i class="fa fa-times-circle"></i> 必填');
				$("#editStaffForm #password-error").css("display","block");
				return false;
			}else if(pas.length > 12 || pas.length < 6 ){
				$("#editStaffForm #password-error").html('<i class="fa fa-times-circle"></i> 请输入长度为 6 至 12 之间的字符串');
				$("#editStaffForm #password-error").css("display","block");
				return false;
			}else{
				$("#editStaffForm #password-error").html('');
				$("#editStaffForm #password-error").css("display","none");
				if(past == undefined || past.length == 0){
					$("#editStaffForm #password_two-error").html('<i class="fa fa-times-circle"></i> 必填');
					$("#editStaffForm #password_two-error").css("display","block");
					return false;
				}else{
					if(pas != past ){
						$("#editStaffForm #password_two-error").html('<i class="fa fa-times-circle"></i> 两次输入的值不一致');
						$("#editStaffForm #password_two-error").css("display","block");
						return false;
					}else{
						$("#editStaffForm #password_two-error").html('');
						$("#editStaffForm #password_two-error").css("display","none");
						return true;
					}
				}
			}
		}else{
			$("#editStaffForm #password-error").html('');
			$("#editStaffForm #password-error").css("display","none");
			$("#editStaffForm #password_two-error").html('');
			$("#editStaffForm #password_two-error").css("display","none");
			return true;
		}
	}
	
	$("#editStaffForm #password").blur(function(){
		validateUpdatePassword();
	});
	
	$("#editStaffForm #password_two").blur(function(){
		validateUpdatePassword();
	});
	
	// 添加员工 点击事件
	$('#edit_staff_role .i-checks').on('ifClicked',function(){
		var objJson = [];
		if($("#editStaffForm #roleList").val() == undefined || $("#editStaffForm #roleList").val().length == 0){
			objJson = []; 
		}else{
			objJson = jQuery.parseJSON($("#editStaffForm #roleList").val());
		}
		// 点击后的状态
		var k = $(this).data("key"),v = $(this).data("value");
		if(!$(this).find('div').hasClass('checked')){
			objJson.push(jQuery.parseJSON('{"roleId":"' + $(this).data("key") + '","roleName":"' + $(this).data("value") + '"}')); 
		}else{
			$.each(objJson,function(_key){
				if(objJson[_key].roleId == k){
					delete objJson[_key];
				}
			});
		}
		var json = [];
		$.each(objJson,function(_key){
			if(objJson[_key] != undefined && objJson[_key] !=null){
				json.push(jQuery.parseJSON('{"roleId":"' + objJson[_key].roleId + '","roleName":"' + objJson[_key].roleName + '"}')); 
			}
		});
		if(json.length == 0){
			$("#editStaffForm #roleList").val("");
		}else{
			$("#editStaffForm #roleList").val(JSON.stringify(json));
		}
		roleListValidate("#editStaffForm #roleList");
	});
	
	
	
	// 编辑员工表单提交
	$("#editStaffForm").validate({
		rules: rules,
		messages: message,
		submitHandler:function(form){
			if(validateUpdatePassword() && roleListValidate("#editStaffForm #roleList")){
				$(form).ajaxSubmit({
					url : "updateUser.shtml", 
					dataType: "json",
					type:"post",
					clearForm:true,
					resetForm: true,
					beforeSubmit:function(formData, jqForm, options){
						if($("#editStaffForm #password").val()!=undefined && $("#editStaffForm #password").val().length != 0){
							formData[10].value = $.md5($("#editStaffForm #password").val())
						}
						console.log(formData);
					},
					success: function(responseText, statusText){
						var resultObj = eval(responseText);
						if(resultObj.type == "S") {
							fairAlert.success();
						}else{
							fairAlert.error({"msg":"编辑员工失败,"+resultObj.message});
						}
						installGrid();
						$('#edit_staff').modal('hide');
						resetForm("editStaffForm");
					}
				});
			}
		}    
	});
	
	$("#authorizeModel .close").click(function(){
		$("#authorizeModel").fadeToggle(500);
		$("#jsTree").jstree("destroy");  
	});
	
	$("#authorizeModel #save_authorize").click(function(){
		var p_a = [];
		$('li[aria-level="3"]').each(function(){
			if($(this).attr("aria-selected") == true || $(this).attr("aria-selected") == "true"){
				p_a.push($(this).attr("id"));
			}
		});
		var partyList = p_a.join();
		if(partyList == undefined || partyList.length == 0){
			$("#authorizeModel #partyList-error").css("display","block");
			setTimeout(function(){
				$("#authorizeModel #partyList-error").css("display","none");
			}, 3000);
		}else{
			ajaxPostJson({
				url:"addPartyRelation.shtml",
				data:{partyId:$("#authorizeModel #partyId").val(),partyList:partyList},
				success:function(resultJson){
				    var resultObj = eval(resultJson);
		    	    if(resultObj.type == "S") {
		    			fairAlert.success();
		    	    }else{
						fairAlert.error({"msg":"员工授权失败,"+resultObj.message});
		    	    }
		    	    installGrid();
		    	    $('#authorizeModel').modal('hide');
		    	    $("#jsTree").jstree("destroy"); 
		    	}
			});
		}
	});
});