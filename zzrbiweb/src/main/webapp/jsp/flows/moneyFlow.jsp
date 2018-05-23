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
	<link href="<%=request.getContextPath()%>/css/plugins/chosen/chosen.css" rel="stylesheet">
	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/page-style.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css" />


</head>
<body>
<script>
    var tabMenuItem = '#home';
    var startTime = '';
    var endTime = '';
    var val_checkUnit = '2';
</script>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<!-- 查询条件 -->
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>数据概要</h5>
			</div>
			<div class="ibox-content" >
				<div>

					<table style="width: 100%;">
						<thead>
						<tr width="100%" style=" height: 30px;">
							<th width="9%"></th>
							<th width="13%">充值金额/元</th>
							<th width="13%">在投金额/元</th>
							<th width="13%">交易额/元</th>
							<th width="13%">站岗资金/元</th>
							<th width="13%">回款金额/元</th>
							<th width="13%">提现金额</th>
							<th width="13%">净流入/元</th>
						</tr>
						</thead>
						<tbody>
						<tr style="height: 25px;">
							<td><label class="control-label">今日</label></td>
							<td>${today.recharge}</td>
							<td>${today.availableBalance}</td>
							<td>${today.investMoney}</td>
							<td>${today.stand}</td>
							<td>${today.investPhase}</td>
							<td>${today.cash}</td>
							<td>${today.inflow}</td>
						</tr>
						<tr style="height: 25px;">
							<td><label class="control-label">昨日</label></td>
							<td>${yesday.recharge}</td>
							<td>${yesday.availableBalance}</td>
							<td>${yesday.investMoney}</td>
							<td>${yesday.stand}</td>
							<td>${yesday.investPhase}</td>
							<td>${yesday.cash}</td>
							<td>${yesday.inflow}</td>
						</tr>
						<tr style="height: 25px;">
							<td><label class="control-label">平台累计</label></td>
							<td>${balance.recharge}</td>
							<td></td>
							<td>${balance.invest}</td>
							<td>${balance.stand}</td>
							<td>${balance.investPhase}</td>
							<td>${balance.cash}</td>
							<td>${balance.inflow}</td>
						</tr>
						</tbody>
					</table>

				</div>

			</div>
		</div>
	</div>
</div>

<div class="ibox-content">
	<div class="row">
		<ul class="nav nav-tabs col-sm-6" id="myTab" >
			<li class="active"><a href="#home" data-toggle="tab">基本趋势</a></li>
			<li><a href="#messages" data-toggle="tab">数据列表</a></li>
		</ul>

		<div class="col-sm-6 row">
			<form id="selectUnit">
				<%--<input type="radio" name="radio" value="1" checked="checked"> 时&nbsp;--%>
				<input type="radio" name="radio" value="2" checked="checked"> 天&nbsp;
				<input type="radio" name="radio" value="3"> 周&nbsp;
				<input type="radio" name="radio" value="4"> 月&nbsp;&nbsp;

				<select style="width:90px" id="selectDate">
					<%--<option value='today'>今天</option>--%>
					<%--<option value='yestoday'>昨天</option>--%>
					<%--<option value='choose1'>自选</option>--%>
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
				<input type="button" class="button" id="checkButton" value="确定">

			</form>
		</div>


	</div>

	<div class="tab-content">
		<div class="tab-pane active" id="home">

			<div id="container" style="height:600px;width: 95%;margin-top: 15px" ></div>

		</div>

		<div class="tab-pane" id="messages">

			<!--数据列表-->
			<div class="ibox"  style="margin-top: 10px">
				<table id="datatable"></table>
			</div>

		</div>
	</div>


</div>

</body>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/tool/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/dataFormmat.js"></script>

<!-- echarts  start -->
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.include.js?v=1.1"></script>
<!-- echarts end -->
<script  type="text/javascript">

    $(function () {
        $('#myTab a:first').tab('show');
    })
    $('#myTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        tabMenuItem  = $(this).attr("href");
        console.log("tabMenuItem==="+tabMenuItem);
    })

</script>

<script type="text/javascript">
    $(document).ready(function () {
        bindEvent();
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
</script>

<script type="text/javascript">

    //按单位时间查询
    function checkUnit() {
        var val_checkUnit = $('#selectUnit input[name="radio"]:checked ').val();
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

        $("#checkButton").click(function(){
            getData();
        });

        getData();

    });

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

    //按条件获取数据
    function getData() {
        console.log('get');

        val_checkUnit = $('#selectUnit input[name="radio"]:checked ').val();


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
        console.log('get==gggggg=='+tabMenuItem+"==="+startTime+"==="+endTime);

        var dayNum = parseFloat($('input').attr("max_range"));
        if(!checkEndTime(startTime,endTime,dayNum)){
            alert("超过最大查询时间"+dayNum+"天");
        }else {
            //金额
            if (tabMenuItem == '#home') {
                getMoney();
            }
            //数据列表
            if (tabMenuItem == '#messages') {
                queryTablesData();
            }
        }

    }





</script>


<script type="text/javascript">

    function getMoney() {

        $.ajax({
            type : "GET",
            url :  "/money/stream.shtml",
            data : {
                "type" : val_checkUnit,
                "startTime" : startTime,
                "endTime" : endTime
            },
            success : function(data) {
                var hz = data.hz;
                var recharge = data.recharge;
                var availableBalance = data.availableBalance;
                var stand = data.stand;
                var cash = data.cash;
                var invest = data.invest;
                var investPhase = data.investPhase;
                var jlMoney = data.jlMoney;

                var dom = document.getElementById("container");
                var myChart = echarts.init(dom);
                var app = {};
                option = null;
                option = {
                    title: {
                        text: ''//折线图堆叠
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['充值金额','在投金额','交易额','站岗资金','回款金额','提现金额','净流入']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: hz
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            name:'充值金额',
                            type:'line',
                            data:recharge
                        },
                        {
                            name:'在投金额',
                            type:'line',
                            data:availableBalance
                        },
                        {
                            name:'交易额',
                            type:'line',
//                            stack: '总量',
                            data:invest
                        },
                        {
                            name:'站岗资金',
                            type:'line',
                            data:stand
                        },
                        {
                            name:'回款金额',
                            type:'line',
                            data:investPhase
                        },
                        {
                            name:'提现金额',
                            type:'line',
                            data:cash
                        },
                        {
                            name:'净流入',
                            type:'line',
                            data:jlMoney
                        }
                    ]
                };
                ;
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }

                return;
            },
            error : function (){
                fairAlert.error({"msg":"系统异常，请稍后重试!"});
            }
        });
    }



    function queryTablesData(){
        $("#datatable").jqGrid({
            url : "<%=request.getContextPath() %>/tableMoney/stream.shtml", // 请求地址
            data: {startTime: startTime,endTime:endTime,type:val_checkUnit},
            columns : [
                {field : "dateTime",title : "日期",width : "10%",align: 'center',valign: 'middle'},
                {field : "recharge",title: '充值金额/元',width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return formatterCount(formatterBigDecimal(value));;
                    }else{
                        return '-';
                    }
                }},
                {field : "availableBalance",title : "在投金额/元",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return formatterCount(formatterBigDecimal(value));;
                    }else{
                        return '-';
                    }
                }},
                {field : "invest",title : "投资金额/元",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return formatterCount(formatterBigDecimal(value));;
                    }else{
                        return '-';
                    }
                }},
                {field : "stand",title: '站岗资金/元',width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return formatterCount(formatterBigDecimal(value));;
                    }else{
                        return '-';
                    }
                }},
                {field : "investPhase",title : "回款金额/元",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return formatterCount(formatterBigDecimal(value));;
                    }else{
                        return '-';
                    }
                }},
                {field : "cash",title : "提现金额/元",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return formatterCount(formatterBigDecimal(value));;
                    }else{
                        return '-';
                    }
                }},
                {field : "jlMoney",title : "净流量/元",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return formatterCount(formatterBigDecimal(value));;
                    }else{
                        return '-';
                    }
                }}
            ]
        });
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

</script>


</html>