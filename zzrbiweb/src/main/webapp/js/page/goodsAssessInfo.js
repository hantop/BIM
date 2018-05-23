/**
 * 押品评估信息
 */
$(function(){
	var linkType = getQueryUrlValue("linkType");
	var returnUrl = "";
	if(linkType!=null && linkType == "01"){
		createPageNav([{name:"待办任务",url:"work_todo.shtml"},{name:"押品评估信息"}], "work_todo.shtml");
		returnUrl = "work_todo.shtml";
		$("#back_process").attr("href","work_todo.shtml");
	}else if(linkType!=null && linkType == "02"){
		//导航菜单
		createPageNav([{name:"押品登记",url:"goods_register.shtml"},{name:"押品登记信息"}], "goods_register.shtml");
		returnUrl = "goods_register.shtml";
		$("#back_process").attr("href","goods_register.shtml");
	}else{
		// 导航菜单
		createPageNav([{name:"押品评估",url:"goods_assess.shtml"},{name:"押品评估信息"}], "goods_assess.shtml");
		returnUrl = "goods_assess.shtml";
		$("#back_process").attr("href","goods_assess.shtml");
	}
	// 金额转大写
	$("#assessPrice").blur(function(){
		var nums = $('#assessPrice').val();
		var upperCase = Arabia_to_Chinese(nums);
		$('#assessForm .upperCase').val(upperCase);
	});
	
 	// 加载订单信息
 	function loadOrderInfo(){
 		// 流程实例
 		var processId = getQueryUrlValue("processId");
 		var processNodeId = getQueryUrlValue("processNodeId");
 		if(processId !=undefined && processId.length !=0){
			ajaxPostJson({
				url:"getOrderInfo.shtml",
				data:{processId:processId,processNodeId:processNodeId},
				success:function(resultJson){
					var resultObj = eval(resultJson);
					if(resultObj.type == "S") {
						$("#orderInfo").setForm(resultObj);
					}else{
						if(resultObj.code == "SYS_01"){
							toLoginPage();
						}
					}
				}
			});
		}
 	}
 	
 	
	function getAssessData(){
		var processId = $("#assessForm #processId").val();
		var processNodeId = $("#assessForm #processNodeId").val();
		if(processId !=undefined && processId.length !=0){
			ajaxPostJson({
				url:"getTaskProcessData.shtml",
				data:{processId:processId,processNodeId:processNodeId},
				success:function(resultJson){
					var resultObj = eval(resultJson);
					if(resultObj.type == "S") {
						$("#assessForm").setForm(resultObj);
						if(resultObj.fileData != undefined && resultObj.fileData.length !=0){
							initFileUpload(resultObj.fileData);
						}else{
							initFileUpload([]);
						}
						$("#assessForm #fileList").val(JSON.stringify(resultObj.fileList));
					}else{
						if(resultObj.code == "SYS_01"){
							toLoginPage();
						}
						initFileUpload([]);
					}
				},error:function(){
					fairAlert.def({"msg":"服务器错误，请稍后重试"});
					window.location.href = returnUrl;
				}
			});
		}else{
			initFileUpload([]);
		}
	}
 	
 	// 获取流程节点
 	function loadProcessTaskNode(){
 		var processId = getQueryUrlValue("processId");
 		if(processId !=undefined && processId.length !=0){
 			ajaxPostJson({
				url:"getProcessInstanceTaskList.shtml",
				data:{processId:getQueryUrlValue("processId")},
				success:function(resultJson){
					var resultObj = eval(resultJson);
					if(resultObj.type == "S") {
						var taskArray = resultObj.taskList;
						$.each(taskArray,function(_key){
							if(taskArray[_key].modelid == "GOODS_REGISTER_TASK"){
								// 押品登记
								$("#goodsInfo").attr("src","process_goods_info.shtml?processId="+taskArray[_key].progress+"&processNodeId="+taskArray[_key].dbid+"&executionId="+taskArray[_key].executionId);
							}else if(taskArray[_key].modelid == "GOODS_ASSESS_TASK"){
								// 执行中
								if(taskArray[_key].state == "2"){
									$("#assessForm #processId").val(taskArray[_key].progress);
									$("#assessForm #processNodeId").val(taskArray[_key].dbid);
									$("#assessForm #guaranteeId").val(getQueryUrlValue("processId"));
									// 押品评估
									initPage(taskArray[_key].substate,taskArray[_key].assignee);
									
								}else if(taskArray[_key].state == "3"){
									// 已完成
									$("#assessForm").css("display","none");
									$("#assessFormInfo").attr("src","process_assess_info.shtml?processId="+taskArray[_key].progress+"&processNodeId="+taskArray[_key].dbid+"&executionId="+taskArray[_key].executionId);
									$("#assessFormInfo").css("display","block");
									
									hideBizButton();
						 			hideProcessButton();
						 			showBackButton();
								}
							}
						});
					}
				}
			});
 		}
 	}
 	// 初始化页面样式
 	function initPage(subStatus,assPartyId){
 		// 未分派/未领取
 		if(subStatus == "1"){
	 		hideBizButton();
	 		showProcessButton();
	 		$(".read-only-mask").show();
	 	}else if(subStatus == "2"){
	 		// 已领取
	 		hideProcessButton();
	 		showBizButton();
	 		$(".read-only-mask").hide();
	 		// 获取暂存数据
	 		getAssessData();
	 	}else if(subStatus == "3"){// 已分派
	 		// 被分派给当前登录人
	 		if(loginPartyId == assPartyId){
	 			hideProcessButton();
		 		showBizButton();
		 		$(".read-only-mask").hide();
		 		// 获取暂存数据
		 		getAssessData();
	 		}else{
	 			hideBizButton();
	 			hideProcessButton();
	 			showBackButton();
		 		$(".read-only-mask").show();
	 		}
	 	}
 	}
 	loadProcessTaskNode();
 	loadOrderInfo();
 	
 	
 	// 添加文件
	var addFile = function(fileUID,fileTitle,fileName){
		removeFile(fileUID);
		var fileList = $('#fileList').val();
		if(fileList == undefined || fileList.length ==0){
			$('#fileList').val('[{"fileUID":"'+fileUID+'","fileType":"'+fileTitle+'","fileName":"'+fileName+'"}]');
		}else{
			var fileJson = jQuery.parseJSON(fileList);
			fileJson.push(jQuery.parseJSON('{"fileUID":"'+fileUID+'","fileType":"'+fileTitle+'","fileName":"'+fileName+'"}'));
			$("#fileList").val(JSON.stringify(fileJson));
		}
	} 
	// 删除文件
	var removeFile = function(fileUID){
		var fileList = $('#fileList').val();
		if(fileList !=undefined && fileList.length !=0){
			var fileJson = jQuery.parseJSON(fileList);
			$.each(fileJson,function(_key){
				if(fileJson[_key].fileUID == fileUID){
					delete fileJson[_key];
				}
			});
			var json = [];
			$.each(fileJson,function(_key){
				if(fileJson[_key] != undefined && fileJson[_key] !=null){
					json.push(jQuery.parseJSON('{"fileUID":"' + fileJson[_key].fileUID + '","fileType":"' + fileJson[_key].fileType + '","fileName":"' + fileJson[_key].fileName + '"}')); 
				}
			});
			if(json.length == 0){
				$("#fileList").val("");
			}else{
				$("#fileList").val(JSON.stringify(json));
			}
		}
	} 
 	
	function initFileUpload(files){
		var checkMap = new Map();
		$.each(files,function(i,row){
			checkMap.put(row.fileUID, row.fileName);
		});
		
		$("#assessForm #addFilePanel").uploadFile({
			initFiles:files,
			maxSize:10*1024,
			columnsNum:3,
			uploadCallback:function(fileElementId,panelElementId,fileUID,fileTitle){
				// 点击上传按钮回调
				AjaxFileUpload(fileElementId,function(data, status){
					var resultObj = eval(data);
					if(resultObj.type == "S") {
						checkMap.put(fileUID, resultObj.fileName);
						addFile(fileUID, fileTitle, resultObj.fileName);
						fairAlert.success({"msg":"文件上传成功"});
					}else{
						fairAlert.error({"msg":"文件上传失败"});
					}
				}, function(data, status, e){
					fairAlert.error({"msg":"文件上传失败"});
				});
				
			},removeCallback:function(fileUID){
				console.log(fileUID);
				// 点击删除按钮回调
				var fileName = checkMap.get(fileUID);
				if(fileName != undefined && fileName!=null){
					ajaxPostJson({
						url:"removeFile.shtml",
						data:{fileName:fileName},
						success:function(resultjson){
							var resultObj = eval(resultjson);
							if(resultObj.type == "S") {
								console.log(fileUID);
								removeFile(fileUID);
								fairAlert.success({"msg":"删除文件成功"});
							}else{
								fairAlert.error({"msg":"删除文件失败"});
							}
						},
						error:function(){
							fairAlert.error({"msg":"删除文件失败"});
						}
					});
				}
			},failedCallback:function(errorId,errorMsg){
				// 上传文件错误回调
				fairAlert.error({"msg":errorMsg});
			}
		});
	}
	
	
	
 	// 领取
 	$("#receive_process").click(function(){
 		
 		var processId = $("#assessForm #processId").val();
		var processNodeId = $("#assessForm #processNodeId").val();
 		
 		ajaxPostJson({
			url:"receiveTask.shtml",
			data:{processId:processId,processNodeId:processNodeId},
			success:function(resultJson){
				var resultObj = eval(resultJson);
				if(resultObj.type == "S") {
					fairAlert.success({"msg":"任务领取成功！"});
					location.reload(false);
				}else{
					fairAlert.def({"msg":resultObj.message});
				}
			}
		});
 	});
 	// 分派
 	$("#allot_process").click(function(){
 		var processId = $("#assessForm #processId").val();
		var processNodeId = $("#assessForm #processNodeId").val();
 		createAllotPanel(loginPartyId, processId, processNodeId);
 	});
 	
 	var submitUrl="";
 	
 	// 保存
 	$("#save_process").click(function(){
 		submitUrl = "goodsAssessSave.shtml";
 		$("#assessForm").validate({submitHandler:submitHandler});
		$("#assessForm").submit();
 	});
 	
 	// 提交流程
 	$("#submit_process").click(function(){
 		submitUrl = "goodsAssessSubmit.shtml";
 		$("#assessForm").validate({submitHandler:submitHandler});
		$("#assessForm").submit();
 	});
 	
 	// 表单提交
	var submitHandler = function(form){
		$(form).ajaxSubmit({
			url : submitUrl, 
		    dataType: "json",
		    type:"post",
		 	clearForm:true,
			resetForm: true,
			success: function(responseText, statusText){
				var resultObj = eval(responseText);
		    	if(resultObj.type == "S") {
		    		fairAlert.success({
		    			msg:'操作成功！',
		    			callback:function(){
		    				window.location.href = returnUrl;
		    			}
		    		});
		    	}else{
		    		fairAlert.error({"msg":","+resultObj.message});
		    	}
			}
		});
	};
 	
 	
 	// -------  操作按钮显示与隐藏  -------//
 	function showBizButton(){
 		$("#save_process").show();
 		$("#submit_process").show();
 	}
 	function hideBizButton(){
 		$("#save_process").hide();
 		$("#submit_process").hide();
 	}
 	function showProcessButton(){
 		$("#receive_process").show();
 		$("#allot_process").show();
 		$("#back_process").show();
 	}
 	function hideProcessButton(){
 		$("#receive_process").hide();
 		$("#allot_process").hide();
 		$("#back_process").hide();
 	}
 	function showBackButton(){
 		$("#back_process").show();
 	}
 	function hideBackButton(){
 		$("#back_process").hide();
 	}
});