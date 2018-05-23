/**
 * 后管弹出框JS
 * @author szxgood
 */
jQuery.extend({
	// 初始化
    init:function(){
    	var html = '<div id="ui-alert-model" class="ui-alert-model"></div>';
    	$(document.body).append(html);
    },
    getMsg:function(msg){
    	// 提示消息的长度
    	var msgLength = msg.length;
    	// 每行显示文字的个数
    	var lineLength = 13;
    	// 行数
    	var lineNum = parseInt(msgLength/15);
    	// 取余数
    	var remainder = msgLength%15;
    	// 计算真实行数
    	lineNum = lineNum > 4 ? 4:remainder > 0 ? lineNum+1:lineNum;
    	// 显示的文本
    	var resultMsg = "";
    	if(msgLength > 50){
    		resultMsg = msg.substring(45,50);
    	}
    	for (var i=lineNum;i>0;i--){
    		if(i == lineNum){
    			if(msgLength > 50){
    				resultMsg = msg.substring(lineLength*(i-1),50)+"...";
    			}else{
    				resultMsg = msg.substring(lineLength*(i-1),msgLength);
    			}
    		}else{
    			resultMsg = msg.substring(lineLength*(i-1),lineLength*i)+"<br>"+resultMsg;
    		}
    	}
    	
    	return resultMsg;
    },
   def:function(options){
    	var defaults={
			title:'系统提示',
			msg:'XXXXXXXXXXXX'
		};
    	
    	var opts = $.extend(defaults,options);
    	
		var html = '<div class="ui-alert-hint"><div class="ui-alert-title" ><p>'+opts.title+'</p></div>'
			+'<div class="ui-alert-content" ><p>'+opts.msg+'</p></div>'
			+'<div class="ui-alert-button"><button id="button_confirm" style="width: 100%;border-bottom-left-radius: 5px;border-bottom-right-radius: 5px;float: left;">OK</button></div></div>';
		$("#ui-alert-model").append(html);
		$("#ui-alert-model").fadeToggle(500);
		$('#button_confirm').click(function(){
			$("#ui-alert-model").fadeToggle(500,function(){
				$("#ui-alert-model").empty();
			});
		});
    },
	success:function(options){
		var defaults={
			modelWidth:'180px',
			modelHeght:'180px',
			iconWidth:'80px',
			iconHeight:'80px',
	        msg:'操作成功',
	        callback:function(){}
	    };
		var opts = $.extend(defaults,options);
	    
		var msg = $.getMsg(opts.msg);
		
		var msgHeight = parseInt(opts.modelWidth) - parseInt(opts.iconWidth) - 60;
		
		var lineNum = msg.split("<br>").length;
		
		var paddingTop = (msgHeight-lineNum * 19)/2;
	    
		var html ='<div class="ui-alert-default" style="width:'+opts.modelWidth+';height:'+opts.modelHeght+';">'
					+'<div class="ui-alert-icon" style="height:'+opts.iconHeight+';">'
					+'<i class="success" style="background-size:'+opts.iconWidth+' '+opts.iconHeight+';width:'+opts.iconWidth+';height:'+opts.iconHeight+';">'
					+'</i></div><div class="ui-alert-msg" style="height:'+msgHeight+'px"><p style="margin-top:'+paddingTop+'px">'+msg+'</p></div></div>';
		$("#ui-alert-model").append(html);
		$("#ui-alert-model").fadeToggle(500);
		setTimeout(function(){
			$("#ui-alert-model").fadeToggle(500,function(){
				$("#ui-alert-model").empty();
			});
			opts.callback();
		}, 1500);
	},
	error:function(options){
		var defaults={
				modelWidth:'240px',
				modelHeght:'240px',
				iconWidth:'100px',
				iconHeight:'100px',
				msg:'错误原因：XXXXXXXX',
				callback:function(){}
		};
		var opts = $.extend(defaults,options);
		
		var msg = $.getMsg(opts.msg);
		
		var msgHeight = parseInt(opts.modelWidth) - parseInt(opts.iconWidth) - 60;
		
		var lineNum = msg.split("<br>").length;
		
		var pMsgheight = lineNum * 19;
		
		var paddingTop = (msgHeight-pMsgheight)/2;
		
		var error_model = '<div class="ui-alert-default" style="width:'+opts.modelWidth+';height:'+opts.modelHeght+';">'
							+'<div class="ui-alert-icon" style="height:'+opts.iconHeight+';">'
							+'<i class="error" style="background-size:'+opts.iconWidth+' '+opts.iconHeight+';width:'+opts.iconWidth+';height:'+opts.iconHeight+';">'
							+'</i></div><div class="ui-alert-msg" style="height:'+msgHeight+'px;"><p style="margin-top:'+paddingTop+'px">'+msg+'</p></div></div>';
		$("#ui-alert-model").append(error_model);
		$("#ui-alert-model").fadeToggle(500);
		setTimeout(function(){
			$("#ui-alert-model").fadeToggle(500,function(){
				$("#ui-alert-model").empty();
			});
			opts.callback();
		}, 3000);
	},
	confirm:function(options){
		var defaults={
				title:'系统提示',
				failText:'取消',
				confirmText:'确认',
				msg:'XXXXXXXXXXXX',
				failCallback:null,
				confirmCallback:null
		};
		var opts = $.extend(defaults,options);
		var html = '<div class="ui-alert-hint"><div class="ui-alert-title" ><p>'+opts.title+'</p></div>'
			+'<div class="ui-alert-content" ><p>'+opts.msg+'</p></div>'
			+'<div class="ui-alert-button"><button id="button_fail" style="border-bottom-left-radius: 5px;border-right:1px solid #ddd;float: left;">'+opts.failText+'</button>'
			+'<button id="button_confirm" style="float: right;border-bottom-right-radius: 5px;">'+opts.confirmText+'</button></div></div>';
		$("#ui-alert-model").append(html);
		$("#ui-alert-model").fadeToggle(500);
		
		if (opts.failCallback == undefined || opts.failCallback == null) {
			$('#button_fail').click(function(){
				$("#ui-alert-model").fadeToggle(500,function(){
					$("#ui-alert-model").empty();
				});
			});
	    }else{
	    	$('#button_fail').click(function(){
	    		opts.failCallback();
			});
	    }
		
		if (opts.confirmCallback == undefined || opts.confirmCallback == null) {
			$('#button_confirm').click(function(){
				$("#ui-alert-model").fadeToggle(500,function(){
					$("#ui-alert-model").empty();
				});
			});
		}else{
			$('#button_confirm').click(function(){
				$("#ui-alert-model").fadeToggle(500,function(){
					$("#ui-alert-model").empty();
					opts.confirmCallback();
				});
			});
		}
	},
	hint:function(options){
		var defaults={
				title:'系统提示',
				failText:'取消',
				confirmText:'确认',
				msg:'XXXXXXXXXXXX',
				failCallback:null,
				confirmCallback:null
		};
		var opts = $.extend(defaults,options);
		var html = '<div class="ui-alert-hint"><div class="ui-alert-title" ><p>'+opts.title+'</p></div>'
			+'<div class="ui-alert-content" ><p>'+opts.msg+'</p></div>'
			+'<div class="ui-alert-button"><button id="button_fail" style="border-bottom-left-radius: 5px;border-right:1px solid #ddd;float: left;">'+opts.failText+'</button>'
			+'<button id="button_confirm" style="float: right;border-bottom-right-radius: 5px;">'+opts.confirmText+'</button></div></div>';
		$("#ui-alert-model").append(html);
		$("#ui-alert-model").fadeToggle(500);
		if (opts.failCallback == undefined || opts.failCallback == null) {
			$('#button_fail').click(function(){
				$("#ui-alert-model").fadeToggle(500,function(){
					$("#ui-alert-model").empty();
				});
			});
		}else{
			$('#button_fail').click(function(){
				opts.failCallback;
			});
		}
		if (opts.confirmCallback == undefined || opts.confirmCallback == null) {
			$('#button_confirm').click(function(){
				$("#ui-alert-model").fadeToggle(500,function(){
					$("#ui-alert-model").empty();
				});
			});
		}else{
			$('#button_confirm').click(function(){
				opts.confirmCallback;
			});
		}
	}
});
/**
 * 弹出框函数  type:默认为def 默认提示框 success 成功  error 失败  confirm 确认弹出框  hint 提示弹出框 
 * @param options
 */
function fairAlert(options) {
	var defaults = {
		title : '系统提示',
		failText : '取消',
		confirmText : '确认',
		msg : 'XXXXXXXXXXXX',
		type : 'def',
		failCallback : null,
		confirmCallback : null,
	};
	var opts = $.extend(defaults, options);
	
	if (opts.type == "success") {
		
		fairAlert.success(opts);
		
	} else if (opts.type == "error") {
		
		fairAlert.error(opts);
		
	} else if (opts.type == "confirm") {
		
		fairAlert.confirm(opts);
		
	} else if (opts.type == "hint") {
		
		fairAlert.hint(opts);
		
	} else {
		
		fairAlert.def(opts);
		
	}
}

fairAlert.def = function(options) {
	$.init();
	$.def(options);
}
fairAlert.success = function(options) {
	$.init();
	$.success(options);
}
fairAlert.error = function(options) {
	$.init();
	$.error(options);
}
fairAlert.confirm = function(options) {
	$.init();
	$.confirm(options);
}

fairAlert.hint = function(options) {
	$.init();
	$.hint(options);
}
