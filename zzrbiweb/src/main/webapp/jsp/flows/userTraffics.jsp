<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户流量</title>
	<link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
  	<link href="<%=request.getContextPath()%>/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
  	<link href="<%=request.getContextPath()%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css"/>
  	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>
  	<link href="<%=request.getContextPath()%>/css/page-style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div class="wrapper-content">
		<!-- 信息查询 -->
		<div class="ibox float-e-margins">
	            <div class="ibox-title">
	                <h5>查询条件</h5>
	            </div>
	            <div class="ibox-content">
	               <form class="form-horizontal" id="searchForm">
            		    <div class="form-group">
	           		    	 <div class="col-sm-5">
	                              <label class="col-sm-3 control-label">日期：</label>
	                              <div class="col-sm-8">
	                                  <div class="input-group input-medium date-picker input-daterange" data-date-format="yyyy-mm-dd">
									    <input class="form-control" style="font-size: 13px;" data-date-format="yyyy-mm-dd" max_range="90" type="text" id="startDate" name="startDate" value="">
									    <span class="input-group-addon">至</span>
									    <input class="form-control" style="font-size: 13px;" data-date-format="yyyy-mm-dd" max_range="90" type="text" id="endDate" name="endDate" value="">
									</div> 
									
	                              </div>
	           		    	</div>
	           		    	<div class="col-sm-3">
		                              <button type="button" class="btn btn-primary pull-right" id="query_btn"><i class="fa fa-search"></i> 查询</button>
		           		    	</div>		           		    
		           		    	<div class="col-sm-3">
		                              <button type="reset" class="btn btn-outline btn-default pull-left"><i class="fa fa-reply"></i> 重置</button>
		           		    	</div>	           		    
	                     </div>
	                       
                	</form>
           		</div>
        </div>
		<!--信息列表 -->
		<div class="ibox float-e-margins">
		    <div class="ibox-title">
                <h5>信息列表</h5>                  
	        </div>
		    <table id="datatable"></table>  
		 </div>
	</div>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery-3.1.1.min.js"></script>  
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.include.js?v=1.1"></script>
    <script type="text/javascript">
    $(function(){     	
    	//开始时间
    	$('#startDate').datepicker({     		
    		language: 'zh_CN',
    	    todayBtn : "linked",  
    	    autoclose : true,  
    	    todayHighlight : true,  
    	    endDate : new Date()  
    	}).on('changeDate',function(e){  
    	    var startTime = e.date;    	    
    	    $('#endDate').datepicker('setStartDate',startTime);
    	});  
    	//结束时间：  
    	$('#endDate').datepicker({    		
    		language: 'zh_CN',
    	    todayBtn : "linked",  
    	    autoclose : true,  
    	    todayHighlight : true,  
    	    endDate : new Date()  
    	}).on('changeDate',function(e){  
    	    var endTime = e.date;   	     
    	    $('#startDate').datepicker('setEndDate',endTime);
    	});
	function queryTableData(){
		$("#datatable").jqGrid({
			url : "<%=request.getContextPath() %>/getUserTrafficList.shtml", // 请求地址
			data: {startDate: $("#startDate").val(),endDate:$("#endDate").val(),},
			columns : [ 
			    {field : "registerTime",title : "日期",width : "15%",align: 'center',valign: 'middle'}, 
			    {field : "ZCRS",title: '注册人数',width : "15%",align: 'center',valign: 'middle',formatter:function(value,row,index){
			    	if(value != null){
			    		return value;
			    	}else{
			    		return 0;
			    	}
			    }}, 
			    {field : "KHRS",title : "开户人数",width : "15%",align: 'center',valign: 'middle',formatter:function(value,row,index){
			    	if(value != null){
			    		return value;
			    	}else{
			    		return 0;
			    	}
			    }},
			    {field : "KHL",title : "开户率",width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
			    	if(value != null){
			    		return value+"%";
			    	}else{
			    		return 0;
			    	}
			    }},
			    {field : "STRS",title : "首投人数",width : "15%",align: 'center',valign: 'middle',formatter:function(value,row,index){
			    	if(value != null){
			    		return value;
			    	}else{
			    		return 0;
			    	}
			    }},
			    {field : "TZZHL",title : "投资转化率",width : "15%",align: 'center',valign: 'middle',formatter:function(value,row,index){
			    	if(value != null){
			    		return value+"%";
			    	}else{
			    		return 0;
			    	}
			    }},
			]
		});
	}
	queryTableData();
	$("#query_btn").click(function(){
		var dayNum = parseFloat($('input').attr("max_range"));
		var startDate=$('#startDate').val();
    	var endDate=$('#endDate').val();
    	if(!checkEndTime(startDate,endDate,dayNum)){
            alert("超过最大查询时间"+dayNum+"天");
        }else{
        	queryTableData();
        }
	});
	//判断时间控件的时间是否在规定的时间内
	function checkEndTime(startTime,endTime,day){	    
	    var start=new Date(startTime.replace("-", "/").replace("-", "/"));	    
	    var end=new Date(endTime.replace("-", "/").replace("-", "/"));
	    if((end-start)/86400000>day){
	        return false;
	    }
	    return true;
	}
	
});
  
</script>
</body>
</html>