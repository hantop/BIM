<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资金流量</title>
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
									    <input class="form-control" style="font-size: 13px;" data-date-format="yyyy-mm-dd" max_range="90"  type="text" id="startDate" name="startDate" value="">
									    <span class="input-group-addon">至</span>
									    <input class="form-control" style="font-size: 13px;" data-date-format="yyyy-mm-dd" max_range="90" type="text" id="endDate" name="endDate" value="">
									</div> 
	                              </div>
	           		    	</div>
	           		    	<div class="col-sm-3">
		                              <button type="button" class="btn btn-primary pull-right" id="query_bn"><i class="fa fa-search"></i> 查询</button>
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.include.js"></script> 
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
    	
    	function queryTablesData(){
    		$("#datatable").jqGrid({
    			url : "<%=request.getContextPath() %>/getCapitalFlowList.shtml", // 请求地址
    			data: {startDate: $("#startDate").val(),endDate:$("#endDate").val(),},
    			columns : [   			    
    			    {field : "investTime",title : "日期",width : "10%",align: 'center',valign: 'middle'}, 
    			    {field : "czje",title: '充值(元)',width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return formatMoney(value,2);
    			    	}else{
    			    		return '-';
    			    	}
    			    }}, 
    			    {field : "czrs",title : "充值人数",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return value;
    			    	}else{
    			    		return '-';
    			    	}
    			    }},
    			    {field : "czbs",title : "充值笔数",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return value;
    			    	}else{
    			    		return '-';
    			    	}
    			    }},
    			    {field : "hkje",title: '回款(元)',width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return formatMoney(value,2);
    			    	}else{
    			    		return '-';
    			    	}
    			    }}, 
    			    {field : "hkrs",title : "回款人数",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return value;
    			    	}else{
    			    		return '-';
    			    	}
    			    }},
    			    {field : "hkbs",title : "回款笔数",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return value;
    			    	}else{
    			    		return '-';
    			    	}
    			    }},
    			    {field : "tzje",title: '投资(元)',width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return formatMoney(value,2);
    			    	}else{
    			    		return '-';
    			    	}
    			    }}, 
    			    {field : "tzrs",title : "投资人数",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return value;
    			    	}else{
    			    		return '-';
    			    	}
    			    }},
    			    {field : "tzbs",title : "投资笔数",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return value;
    			    	}else{
    			    		return '-';
    			    	}
    			    }},
    			    {field : "txje",title: '提现(元)',width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return formatMoney(value,2);
    			    	}else{
    			    		return '-';
    			    	}
    			    }}, 
    			    {field : "txrs",title : "提现人数",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return value;
    			    	}else{
    			    		return '-';
    			    	}
    			    }},
    			    {field : "txbs",title : "提现笔数",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return value;
    			    	}else{
    			    		return '-';
    			    	}
    			    }},
    			    {field : "jlr",title : "净流入",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return formatMoney(value,2);
    			    	}else{
    			    		return '-';
    			    	}
    			    }},
    			    {field : "ye",title : "余额(元)",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
    			    	if(value != null){
    			    		return formatMoney(value,2);
    			    	}else{
    			    		return '-';
    			    	}
    			    }},
    			]
    		});
    	}
    	queryTablesData();

    	$("#query_bn").click(function(){
    		var dayNum = parseFloat($('input').attr("max_range"));
    		var startDate=$('#startDate').val();
        	var endDate=$('#endDate').val();
        	if(!checkEndTime(startDate,endDate,dayNum)){
                alert("超过最大查询时间"+dayNum+"天");
            }else{
            	queryTablesData();
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

		//金额格式化  千位符
		function formatMoney(s1, n) { //  n为小数点位数   
			if(s1>0){
				s = parseFloat((s1 + "").replace(/[^\d\.-]/g, "")).toFixed(n)+ ""; //每一位都用 空格隔开
				var l = s.split(".")[0].split("").reverse();
				var r = "";
				var gg = null;
				if (s.indexOf(".") > 0) { //判断是否包含小数点
					r = s.split(".")[1];
				}
				t = "";
				for (i = 0; i < l.length; i++) {
					t += l[i]+ ((i + 1) % 3 == 0 && (i + 1) != l.length ? ",": "");
				}
				if (s.indexOf(".") > 0) {
					gg = t.split("").reverse().join("") + "." + r;
				} else {
					gg = t.split("").reverse().join("");
				}
				return gg;
			}else{
				var source = String(s1).split(".");//按小数点分成2部分
		        source[0] = source[0].replace(new RegExp('(\\d)(?=(\\d{3})+$)','ig'),"$1,");//只将整数部分进行都好分割
		        return source.join(".");//再将小数部分合并进来
			}
			
		}
	</script>    
</body>
</html>