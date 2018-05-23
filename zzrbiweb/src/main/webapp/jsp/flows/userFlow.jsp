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
	<link href="<%=request.getContextPath()%>/css/plugins/chosen/chosen.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/page-style.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css" />



	<script type="text/javascript">
    var val_checkUnit = '2';
    var startTime = getNowFormatDate(30);
    var endTime = getNowFormatDate(0);

    //获取时间，格式YYYY-MM-DD
    function getNowFormatDate(item) {
        var date = new Date();

        if(item != 0){
            date = new Date(date.getTime() - item * 24 * 3600 * 1000);
        }

        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();



        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }

</script>
</head>
<body>
	<div class="wrapper-content">
		<!-- 信息查询 -->
		<div class="ibox float-e-margins">
	            <div class="ibox-content">
	               <form class="form-horizontal" id="selectUnit">
            		    <div class="form-group">
							<div class="col-sm-3">
								<h5>查询条件</h5>
							</div>
							<div class="col-sm-6 row">


									<input type="radio" name="radio" value="2" checked="checked"> 天&nbsp;
									<input type="radio" name="radio" value="3" > 周&nbsp;
									<input type="radio" name="radio" value="4" > 月&nbsp;&nbsp;

									<select style="width:90px" id="selectDate">
											<option value='day30'>近30天</option>
											<option value='day60'>近60天</option>
											<option value='day90'>近90天</option>
											<option value='year'>今年</option>
											<option value='choose'>自选</option>
									</select>&nbsp;&nbsp;


									<input type="text"  id="startTime" name="startTime"
										   placeholder="开始日期" style="text-align: left;width: 100px;display: none">&nbsp;
									<input type="text"  id="endTime" name="endTime"
										   placeholder="结束日期" style="text-align: left;width: 100px;display: none">


							</div>
	           		    	<div class="col-sm-3">
		                              <button type="button" class="btn btn-primary " id="query_bn"><i class="fa fa-search"></i> 查询</button>
							</div>

	                     </div>
	                     
                	</form>
           		</div>
        </div>
		<!--信息列表 -->
		<div class="ibox float-e-margins">
		    <div class="ibox-title">
                <h5>用户流量列表</h5>
	        </div>
		    <table id="datatable"></table>  
		 </div>
	</div>

</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.include.js?v=1.1"></script>

<script type="text/javascript">

    //按单位时间查询
    function checkUnit() {
        var val_checkUnit = $('#selectUnit input[name="radio"]:checked').val();
        console.log("val_checkUnit==checkUnit"+$('#selectUnit input[name="radio"]:checked').val()+"=="+val_checkUnit);
        $("#selectDate").empty();
        if(val_checkUnit == 1){
            $('#selectDate').append("<option value='today'>今天</option>");
            $('#selectDate').append("<option value='yestoday'>昨天</option>");
            $('#selectDate').append("<option value='choose1'>自选</option>");
        }
        if(val_checkUnit == 2){
            $('#selectDate').append("<option value='day30'>近30天</option>");
            $('#selectDate').append("<option value='day60'>近60天</option>");
            $('#selectDate').append("<option value='day90'>近90天</option>");
            $('#selectDate').append("<option value='year'>今年</option>");
            $('#selectDate').append("<option value='choose'>自选</option>");
        }
        if(val_checkUnit == 3){
            $('#selectDate').append("<option value='week12'>近12周</option>");
            $('#selectDate').append("<option value='week24'>近24周</option>");
            $('#selectDate').append("<option value='choose'>自选</option>");
        }
        if(val_checkUnit == 4){
            $('#selectDate').append("<option value='mouth'>近12月</option>");
            $('#selectDate').append("<option value='choose'>自选</option>");
        }
        $('#startTime').hide();
        $('#endTime').hide();
    }

    $(function(){


        $(":radio").click(function(){
            checkUnit();
        });

        $("#selectDate").change(function(){
            var val_selectDate = $('#selectDate').val();
            if(val_selectDate == 'choose1'){
                $('#startTime').show();
                $('#endTime').hide();
            }else if(val_selectDate == 'choose'){
                $('#startTime').show();
                $('#endTime').show();
            }else{
                $('#startTime').hide();
                $('#endTime').hide();
            }

        });

        $("#query_bn").click(function(){
            getData();
        });

        bindEvent();

        queryTablesData();

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







    //按条件获取数据
    function getData() {
        console.log('get');

        val_checkUnit = $('#selectUnit input[name="radio"]:checked ').val();

        console.log('get===val_checkUnit==='+val_checkUnit);
        var val_selectDate = $('#selectDate').val();

        //单位 时
        if(val_checkUnit == 1 && val_selectDate == 'today' ){
            startTime = getNowFormatDate(0);
        }
        if(val_checkUnit == 1 && val_selectDate == 'yestoday' ){
            startTime = getNowFormatDate(1);
        }
        if(val_checkUnit == 1 && val_selectDate == 'choose1' ){
            startTime = $('#startTime').val();
        }

        //天
        if(val_checkUnit == 2 && val_selectDate == 'day30' ){
            startTime = getNowFormatDate(30);
            endTime = getNowFormatDate(0);
        }
        if(val_checkUnit == 2 && val_selectDate == 'day60' ){
            startTime = getNowFormatDate(60);
            endTime = getNowFormatDate(0);
        }
        if(val_checkUnit == 2 && val_selectDate == 'day90' ){
            startTime = getNowFormatDate(90);
            endTime = getNowFormatDate(0);
        }
        if(val_checkUnit == 2 && val_selectDate == 'year' ){
            var date = new Date();
            var year = date.getFullYear();
            startTime = year + '-01-01';
            endTime = getNowFormatDate(0);
        }
        if(val_checkUnit == 2 && val_selectDate == 'choose' ){
            startTime = $('#startTime').val();
            endTime = $('#endTime').val();
        }

        //周
        if(val_checkUnit == 3 && val_selectDate == 'week12' ){
            startTime = getNowFormatDate(7*11);
            endTime = getNowFormatDate(0);
        }
        if(val_checkUnit == 3 && val_selectDate == 'week24' ){
            startTime = getNowFormatDate(7*23);
            endTime = getNowFormatDate(0);
        }
        if(val_checkUnit == 3 && val_selectDate == 'choose' ){
            startTime = $('#startTime').val();
            endTime = $('#endTime').val();
        }

        //月
        if(val_checkUnit == 4 && val_selectDate == 'mouth' ){
            startTime = getNowFormatDate(30*11);
            endTime = getNowFormatDate(0);
        }
        if(val_checkUnit == 4 && val_selectDate == 'choose' ){
            startTime = $('#startTime').val();
            endTime = $('#endTime').val();
        }
        console.log("get======="+startTime+"==="+endTime);

        var dayNum = parseFloat($('input').attr("max_range"));
        if(!checkEndTime(startTime,endTime,dayNum)){
            alert("超过最大查询时间"+dayNum+"天");
        }else {
            queryTablesData();

        }

    }

    //判断时间控件的时间是否在规定的时间内
    function checkEndTime(startTime,endTime,day){
        var start=new Date(startTime.replace("-", "/").replace("-", "/"));
        var end=new Date(endTime.replace("-", "/").replace("-", "/"));
        if((end-start)/86400000>day){
            return false;
        }
        return true;
    }


    function queryTablesData(){
        $("#datatable").jqGrid({
            url : "<%=request.getContextPath() %>/user/moneyStream.shtml", // 请求地址
            data: {startTime:startTime,endTime:endTime,type:val_checkUnit},
            columns : [
                {field : "dateTime",title : "日期",width : "10%",align: 'center',valign: 'middle'},
                {field : "registerCount",title: '注册人数/人',width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return value;
                    }else{
                        return '-';
                    }
                }},
                {field : "todayRegisterAccount",title : "开户人数/人",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return value +"&nbsp;&nbsp;<span style='color: red'>|</span>&nbsp;&nbsp;" + row.beforRegisterAccount;
                    }else{
                        return '-';
                    }
                }},
                {field : "todayRegisterAccount",title : "开户率",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null && row.registerCount != 0){
                        return ((value+row.beforRegisterAccount)/row.registerCount * 100).toFixed(2) + "%";
                    }else{
                        return '-';
                    }
                }},
                {field : "todayRegisterInvest",title: '投资人数/人',width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return value +"&nbsp;&nbsp;<span style='color: red'>|</span>&nbsp;&nbsp;"+(row.investPerson - row.todayRegisterInvest);
                    }else{
                        return '-';
                    }
                }},
                {field : "beforRegisterInvest",title : "首投人数/人",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return row.todayRegisterInvest +"&nbsp;&nbsp;<span style='color: red'>|</span>&nbsp;&nbsp;"+ value;
                    }else{
                        return '-';
                    }
                }},
                {field : "investPerson",title : "首投转化率",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(row.registerCount != null && row.registerCount != 0){
                        return ((row.todayRegisterInvest + row.beforRegisterInvest)/row.registerCount * 100).toFixed(2) +"%";
                    }else{
                        return '-';
                    }
                }},
                {field : "repeatInvest",title: '二次复投人数/人',width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return value;
                    }else{
                        return '-';
                    }
                }},
                {field : "investPerson",title : "复投转化率",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null && value != 0){
                        return (row.repeatInvest/value * 100).toFixed(2) +"%";
                    }else{
                        return '-';
                    }
                }}
            ]
        });
    }

</script>

</html>