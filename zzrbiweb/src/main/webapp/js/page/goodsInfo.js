$(function(){
	 var pageNav = [{name:"押品登记",url:"goods_register.shtml"},{name:"押品登记信息"}];
	 //导航菜单
		createPageNav(pageNav, "goods_register.shtml");	    
	    
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
									$("#goodsInfo").attr("src","process_goods_info.shtml?processId="+taskArray[_key].progress+"&processNodeId="+taskArray[_key].dbid+"&executionId="+taskArray[_key].executionId);
								}else if(taskArray[_key].modelid == "GOODS_ASSESS_TASK"){
									initPage(taskArray[_key].state,taskArray[_key].assignee);
								}
							});
						}
					}
				});
	 		}
	 	}
	 	// 初始化页面样式
	 	function initPage(Status,assPartyId){
		 	 if(Status == "2"){//待评估
		 		$("#to_loan").hide();
		 		$("#assessForm").hide();
		 		//$(".read-only-mask").hide();
		 	}else if(Status == "3"){//已评估
		 		$("#assessForm").show();
		 		$("#back_register").show();
		 		$("#to_loan").show();
			 	//$(".read-only-mask").hide();
			 		
		 	}
	 	}
	 	loadProcessTaskNode();
	 	loadOrderInfo();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
})