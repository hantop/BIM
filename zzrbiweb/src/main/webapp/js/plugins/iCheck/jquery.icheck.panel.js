/**
 */
(function($) {
	
	var opts,title,isCheckBox,initData,viewData,checkData,columns,result='rows';
	
	// 初始化
	var init = function(){
		$(document.body).append('<div class="modal fade in" id="allot_modal" role="dialog" data-backdrop="static" style="display: block;"></div><div class="modal-backdrop fade in" id="allot_modal_masker"></div>');
		$("#allot_modal").append('<div class="modal-dialog allot-model"></div>');
		$("#allot_modal .allot-model").append('<div class="modal-content"><div>');
		$("#allot_modal .modal-content").append('<div class="modal-header"></div><div class="modal-body" style="background: #fff;"></div><div class="modal-footer" style="text-align: center;"></div>');
		$("#allot_modal .modal-header").append('<h4 class="modal-title text-left">'+title+'</h4>');
		$("#allot_modal .modal-footer").append('<button type="button" class="btn btn-primary" id="allot_cancel"><i class="fa fa-close"></i>&nbsp;取消</button>&nbsp;&nbsp;');
		$("#allot_modal .modal-footer").append('<button type="button" class="btn btn-primary" id="allot_confirm"><i class="fa fa-check"></i>&nbsp;确定</button>');
		createAllotPanel();
		
		// 值改变事件
		$("#allot_cancel").click(function(){
			$("#allot_modal").remove();
			$("#allot_modal_masker").remove();
		});
		
		$("#allot_confirm").click(function(){
			$("#allot_modal").remove();
			$("#allot_modal_masker").remove();
			if(checkData.length != 0){
				opts.checkBack(checkData);
			}
		});
		
	}
	
	// 创建panel
	var createAllotPanel = function(){
		$("#allot_modal .modal-body").append('<div class="form-horizontal"><div class="form-group"><div class="col-sm-12"></div></div></div>');
		$("#allot_modal .col-sm-12").append('<div class="col-sm-5" style="width:45%;" id="allot_left"></div>');
		$("#allot_modal .col-sm-12").append('<div class="col-sm-2" style="width:10%;" id="allot_middle"></div>');
		$("#allot_modal .col-sm-12").append('<div class="col-sm-5" style="width:45%;" id="allot_right"></div>');
		createLeftAllotPanel();
		createMiddleAllotPanel();
		createRightAllotPanel();
		findData($("#queryParam").val());
		loadCheckData();
	}
	
	// 创建左侧panel
	var createLeftAllotPanel = function(){
		if(isCheckBox){
			$("#allot_modal #allot_left").append('<div class="row"><label class="col-sm-12">待选择</label></div>');
		}else{
			$("#allot_modal #allot_left").append('<div class="row"><label class="col-sm-12">待选择&nbsp;&nbsp;<i style="color:red;">注：只能选择一个</i></label></div>');
		}
		$("#allot_modal #allot_left").append('<div class="allot-icheck-panel"><div class="row"><div class="input-group" style="padding-top: 5px;padding-bottom: 5px;"><span class="input-group-btn"><button type="button" class="btn btn-primary">搜</button> </span><input type="text" class="form-control" id="queryParam"></div></div><div class="row" id="allot_panel_left"></div></div>');
		
		// 值改变事件
		$("#queryParam").bind('input propertychange',function(){
			// 检索数据
			findData($(this).val());
		});
	}
	
	// 创建中部panel
	var createMiddleAllotPanel = function(){
		$("#allot_modal #allot_middle").append('<div class="allot-button-panel" ><button class="btn btn-primary btn-circle btn-lg" type="button" style="padding-left: 18px;"><i class="fa fa-arrow-right"></i></button></div>');
		selectButtonBindEvent();
	}
	
	// 创建右部panel
	var createRightAllotPanel = function(){
		$("#allot_modal #allot_right").append('<div class="row"><label class="col-sm-12">已选择</label></div>');
		$("#allot_modal #allot_right").append('<div class="allot-icheck-panel"><div class="row" id="allot_panel_right"></div></div>');
	}
	// 装载待选择数据
	function loadInitData(){
		var html = "";
		if(viewData.length == 0){
			$("#allot_modal #allot_panel_left").html("");
		}
		$.each(viewData,function(i,row){
			var keyVlaue = row[columns.field];
			var flag = true;
			$.each(checkData,function(j,rowCheck){
				if(row[columns.field] == rowCheck[columns.field]){
					flag = false;
				}
			});
			if(flag){
				html += '<div class="checkbox i-checks"><label class="col-sm-12"><input type="checkbox" value="'+row[columns.field]+'" name="allot" data-allot-key="'+row[columns.field]+'" data-allot-value="'+row[columns.title]+'" data-allot-index="'+i+'"> <i></i>'+row[columns.title]+'</label></div>';
			}
			$("#allot_modal #allot_panel_left").html(html);
			$("#allot_modal #allot_panel_left .i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green"});
		});
		leftCheckBindEvent();
	}
	// 装载待选择数据
	function loadCheckData(){
		var html = "";
		$.each(checkData,function(i,row){
			html +='<div class="checkbox i-checks" id="icheck_'+i+'">';
			html +='<label class="col-sm-6"><div class="icheckbox_square-green checked" style="position: relative;"></div>'+row[columns.title]+'</label>';
			html +='<label class="col-sm-6 control-label" style="color:red;font-size: 16px;padding-top: 0px;"><i class="fa fa-minus-circle remove-check" data-allot-index="'+i+'"></i></label></div>';
		});
		$("#allot_modal #allot_panel_right").html(html);
		$("#allot_modal #allot_panel_right .i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green"});
		removeSelectBindEvent();
	}
	function leftCheckBindEvent(){
		$('#allot_panel_left .i-checks').on('ifClicked',function(){
			var b = $(this).find('div').hasClass('checked');
			// 单选
			if(!isCheckBox){
				if(!b){
					$("#allot_panel_left .i-checks").each(function() {
						$(this).iCheck('uncheck');
					});
				}
			}
		});
	}
	
	function selectButtonBindEvent(){
		$('#allot_middle button').click(function(){
			$("#allot_panel_left .i-checks").each(function() {
				var b = $(this).find('div').hasClass('checked');
				var allotKey = $(this).find('input').data("allot-key");
				var allotIndex = $(this).find('input').data("allot-index");
				if(!isCheckBox && b){
					checkData = [];
					checkData.push(viewData[allotIndex]);
					loadInitData();
					loadCheckData();
				}else if(isCheckBox && b){
					checkData.push(viewData[allotIndex]);
					loadInitData();
					loadCheckData();
				}
			});
		});
	}
	function removeSelectBindEvent(){
		$('#allot_panel_right .remove-check').click(function(){
			var allotIndex = $(this).data("allot-index");
			$("#icheck_"+allotIndex).remove();
			checkData.remove(allotIndex);
			loadInitData();
			loadCheckData();
		});
	}
	
	// 模糊检索数据
	function findData(v){
		if(v == undefined || v.length == 0){
			viewData = initData;
		}else{
			viewData = [];
			$.each(initData,function(i,row){
				var keyVlaue = row[columns.field];
				var flag = true;
				$.each(checkData,function(j,rowCheck){
					if(row[columns.field] == rowCheck[columns.field]){
						flag = false;
					}
				});
				if(flag){
					var titleVlaue = row[columns.title];
					if(titleVlaue.indexOf(v) > -1){
						viewData.push(row);
					}
				}
			});
		}
		loadInitData();
	}
	
	function ajaxPostRequest(){
		var request = {
            type: "post",
            url:  opts.url,
            data: opts.data,
            dataType: "json",
            success: function (res) {
            	initData = res[result];
            	init();
            },
            error: function (res) {
            	console.log("服务器请求异常");
            }
        };
		$.ajax(request);
	}
	jQuery.extend({
		// 分派
		allotModal:function(options) {
			// 配置项
			var defaults = {
			   url:"",
			   data:{}, //参数
			   title:"任务分派",
			   checkData:[],// 已选择数据
			   columns:[],
			   isCheckBox:false, // 是否单选
	           checkBack:function(){} // 点击确定后回调
			}
			opts = $.extend(defaults,options);
			
			title = opts.title,isCheckBox = opts.isCheckBox,checkData=opts.checkData,columns=opts.columns;
			
			ajaxPostRequest();
		}
	});
})(jQuery);