$(function(){
	$("#reg_date").datepicker({
		format: "yyyy-mm-dd",
		language: 'zh_CN',
	    orientation: "top left",
	    multidate: false
	});
	function queryTableData(){
		$("#datatable").jqGrid({
			url : "getGuarantList.shtml", // 请求地址
			data: {transDate: $("#transDate").val(),guaranteeId:$("#guaranteeId").val(),goodsCode:$("#goodsCode").val(),},
			columns : [ 
			    {field : "processInstanceTaskId",visible:false,title:"流程节点"}, 
			    {field : "processInstanceId",visible:false,title:"流程实例"}, 
			    {field : "createDate",title : "登记日期",width : "15%",align: 'center',valign: 'middle',formatter:function(value,row,index){
			    	console.log(value+'==='+row)
			    	if (value != null) {
			    		return value.substring(0, 10);		
			    	}
			    }}, 
			    {field : "guaranteeId",title: '押品登记编号',width : "15%",align: 'center',valign: 'middle',formatter:function(value,row,index){
			    	if(row.assessStatus == "1"){
			    		return "<a href='add_goods.shtml?guaranteeId="+value+"&processId="+row.processInstanceId+"&processNodeId="+row.processInstanceTaskId+"' class='trans-info'>"+value+"</a>";
			    	}else if(row.assessStatus == "2"){
			    		return "<a href='goods_info.shtml?guaranteeId="+value+"&processId="+row.processInstanceId+"&processNodeId="+row.processInstanceTaskId+"' class='trans-info'>"+value+"</a>";
			    	}else if(row.assessStatus == "3"){
			    		return "<a href='goods_info.shtml?linkType=02&guaranteeId="+value+"&processId="+row.processInstanceId+"&processNodeId="+row.processInstanceTaskId+"&subStatus="+row.subStatus+"' class='trans-info'>"+value+"</a>";
			    	}
			    }}, 
			    {field : "processName",title : "流程名称",width : "15%",align: 'center',valign: 'middle'},
			    {field : "goodsCode",title : "车牌号",width : "10%",align: 'center',valign: 'middle'},
			    {field : "desc",title : "备注",width : "15%",align: 'center',valign: 'middle'},
			    {field : "assessStatus",title : "流程状态",width : "15%",align: 'center',valign: 'middle',formatter:function(value,row,index){
			    	if(row.assessStatus == "1"){
			    		return "待提交";
			    	}else if(row.assessStatus == "2"){
			    		return "待评估";
			    	}else if(row.assessStatus == "3"){
			    		return "已评估";
			    	}else if(row.assessStatus == "4"){
			    		return "已过期";
			    	}else{
			    		return "-";
			    	}
			    }},
			    {field : "operate",title : "操作",width : "15%",align: 'center',valign: 'middle',formatter:function(value,row,index){
			    	if(row.assessStatus == "3"){
			    		if(row.isLink == true){
			    			return "&nbsp;<a href='javascript:void(0);' class='trans-info'>发起贷款申请</a>";					    		
			    		}else {
			    			return "-";					    		
			    		}
			    	}else if(row.assessStatus == "4"){
			    		return "&nbsp;<a href='javascript:void(0);' class='trans-info'>重新评估</a>";					    		
			    	}else{
			    		return "-";					    		
			    	}
				}}
			]
		});
	}
	queryTableData();
	
	$("#query_btn").click(queryTableData);
});
