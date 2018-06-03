<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>默认首页</title>
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet">
  	<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
  	<link href="<%=request.getContextPath()%>/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
  	<link href="<%=request.getContextPath()%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css"/>
  	<link href="<%=request.getContextPath()%>/css/plugins/chosen/chosen.css" rel="stylesheet">
  	<link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css"/>
  	<link href="<%=request.getContextPath()%>/css/page-style.css" rel="stylesheet" type="text/css"/>
	<link href="<%=request.getContextPath()%>/css/plugins/datapicker/datepicker3.css" rel="stylesheet" type="text/css" />

	<!-- echarts end -->
</head>
<body>
<script>
    var tabMenuItem = '#home';
    var startTime = '';
    var endTime = '';
    var val_checkUnit = '1';

</script>
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<!-- 查询条件 -->
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>数据概要</h5>
			</div>
			<div class="ibox-content" style="margin-bottom: 8px">
					<div>
					<div class="row">
						<div class="col-sm-3">
						</div>
						<div class="col-sm-3">
							<h4>实时人数</h4>
						</div>
						<div class="col-sm-3">
							<h4>日累计人数</h4>
						</div>
						<div class="col-sm-3">
							<h4>在场人数</h4>
						</div>

					</div>
					<div class="row">
						<div class="col-sm-3">
						<label class="col-sm-4 control-label">工人</label>
						</div>
						<div class="col-sm-3">
							${today.registerCount}138
						</div>
						<div class="col-sm-3">
							${today.investCount}87
						</div>
						<div class="col-sm-3">
							${today.firstInvestCount}88
						</div>

					</div>
					<div class="row">
						<div class="col-sm-3">
							<label class="col-sm-4 control-label">管理员</label>
						</div>
						<div class="col-sm-3">
							${yesday.registerCount}56
						</div>
						<div class="col-sm-3">
							${yesdayPerson.rows[0].investPerson}89
						</div>
						<div class="col-sm-3">
							${yesday.firstInvestCount}148
						</div>

					</div>

					</div>

			</div>
		</div>
	</div>
</div>

<div class="ibox-content">
<div class="row">
	<ul class="nav nav-tabs col-sm-6" id="myTab" >
		<li class="active"><a href="#home" data-toggle="tab">工人图表</a></li>
		<li><a href="#profile" data-toggle="tab">出勤率</a></li>
		<li><a href="#fenbu" data-toggle="tab">工种分布</a></li>
		<li><a href="#messages" data-toggle="tab">最新动态</a></li>
	</ul>

    <div class="col-sm-6 row">

	</div>


</div>

<div class="tab-content">
	<div class="tab-pane active" id="home">

		<div id="container" style="height:600px;width: 95%;margin-top: 15px"></div>

	</div>
	<div class="tab-pane" id="profile">

		<div id="container2" style="height:600px;width:950px;margin-top: 15px"></div>

	</div>
	<div class="tab-pane" id="fenbu">

		<div id="container3" style="height:600px;width:950px;margin-top: 15px"></div>

	</div>
	<div class="tab-pane" id="messages">

		<!--数据列表-->
		<div >
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

<script  type="text/javascript">

    $(function () {
        $('#myTab a:first').tab('show');
    })
    $('#myTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        tabMenuItem  = $(this).attr("href");
        console.log("tabMenuItem==="+tabMenuItem);

        //数据列表
        if (tabMenuItem == '#messages') {
            queryTablesData();
        }
    })

</script>




<script type="text/javascript">
// <!-- 柱状图 -->
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    var posList = [
        'left', 'right', 'top', 'bottom',
        'inside',
        'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
        'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
    ];

    app.configParameters = {
        rotate: {
            min: -90,
            max: 90
        },
        align: {
            options: {
                left: 'left',
                center: 'center',
                right: 'right'
            }
        },
        verticalAlign: {
            options: {
                top: 'top',
                middle: 'middle',
                bottom: 'bottom'
            }
        },
        position: {
            options: echarts.util.reduce(posList, function (map, pos) {
                map[pos] = pos;
                return map;
            }, {})
        },
        distance: {
            min: 0,
            max: 100
        }
    };

    app.config = {
        rotate: 90,
        align: 'left',
        verticalAlign: 'middle',
        position: 'insideBottom',
        distance: 15,
        onChange: function () {
            var labelOption = {
                normal: {
                    rotate: app.config.rotate,
                    align: app.config.align,
                    verticalAlign: app.config.verticalAlign,
                    position: app.config.position,
                    distance: app.config.distance
                }
            };
            myChart.setOption({
                series: [{
                    label: labelOption
                }, {
                    label: labelOption
                }, {
                    label: labelOption
                }, {
                    label: labelOption
                }]
            });
        }
    };


    var labelOption = {
        normal: {
            show: true,
            position: app.config.position,
            distance: app.config.distance,
            align: app.config.align,
            verticalAlign: app.config.verticalAlign,
            rotate: app.config.rotate,
            formatter: '{c}  {name|{a}}',
            fontSize: 16,
            rich: {
                name: {
                    textBorderColor: '#fff'
                }
            }
        }
    };

    option = {
        color: ['#003366', '#006699', '#4cabce', '#e5323e'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['现场实时人数', '日累计进场', '队伍在场人数']
        },
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                axisTick: {show: false},
                data: ['周成飞', '梁俊川', '赵树军', '李玉杨', '尹春景']
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '现场实时人数',
                type: 'bar',
                barGap: 0,
                label: labelOption,
                data: [320, 332, 301, 334, 390]
            },
            {
                name: '日累计进场',
                type: 'bar',
                label: labelOption,
                data: [220, 182, 191, 234, 290]
            },
            {
                name: '队伍在场人数',
                type: 'bar',
                label: labelOption,
                data: [150, 232, 201, 154, 190]
            }
        ]
    };;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

</script>



<script type="text/javascript">
    // <!-- 折线图-->


    var dom2 = document.getElementById("container2");
    var myChart2 = echarts.init(dom2);
    var app = {};
    option = null;
    option = {
        title : {
            text: '最近七天出勤率',
            x:'center'
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: [820, 932, 901, 934, 1290, 1330, 1320],
            type: 'line',
            areaStyle: {}
        }]
    };
    ;
    if (option && typeof option === "object") {
        myChart2.setOption(option, true);
    }


</script>




<script type="text/javascript">
    // <!-- 饼图-->
    var dom3 = document.getElementById("container3");
    var myChart3 = echarts.init(dom3);
    var app = {};
    option = null;
    option = {
        title : {
            text: '现场工人工种分布',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['钢筋工','水电工','桩基工','瓦工','汽车吊司机']
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                    {value:335, name:'钢筋工'},
                    {value:310, name:'水电工'},
                    {value:234, name:'桩基工'},
                    {value:135, name:'瓦工'},
                    {value:1548, name:'汽车吊司机'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    ;
    if (option && typeof option === "object") {
        myChart3.setOption(option, true);
    }
</script>


<script type="text/javascript">

    function queryTablesData(){
        $("#datatable").jqGrid({
            url : "<%=request.getContextPath() %>/table/list.shtml", // 请求地址
            data: {startTime: startTime,endTime:endTime,type:val_checkUnit},
            columns : [
                {field : "dateTime",title : "时间",width : "10%",align: 'center',valign: 'middle'},
                {field : "registerCount",title: '姓名',width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return value;
                    }else{
                        return '-';
                    }
                }},
                {field : "investCount",title : "岗位",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return value;
                    }else{
                        return '-';
                    }
                }},
                {field : "firstInvestCount",title : "部门",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return value;
                    }else{
                        return '-';
                    }
                }},
                {field : "availableBalance",title: '班组',width : "10%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return formatterCount(formatterBigDecimal(value));
                    }else{
                        return '-';
                    }
                }},
                {field : "investMoney",title : "操作",width : "5%",align: 'center',valign: 'middle',formatter:function(value,row,index){
                    if(value != null){
                        return formatterCount(formatterBigDecimal(value));
                    }else{
                        return '-';
                    }
                }}
            ]
        });
    }

</script>
</html>