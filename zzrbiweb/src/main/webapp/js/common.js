$(function () {
    // 初始化下拉列表
    if($(".chosen-select").length !=0){
    	var config = {
    			'.chosen-select'           : {},
    			'.chosen-select-deselect'  : {allow_single_deselect:true},
    			'.chosen-select-no-single' : {disable_search_threshold:10},
    			'.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
    			'.chosen-select-width'     : {width:"100%"}
    	}
    	$(".chosen-select").chosen(config);
    	$(".chosen-select").trigger("chosen:updated");
    }
    
    $('button[type="reset"]').click(function(){
    	resetForm($(this)[0].form.id);
    });
    
    if($(".i-checks").length && jQuery()){
    	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green"});
    }
    
});
// 指派面板
function createAllotPanel(loginPartyId,processId,processNodeId) {
	$.allotModal({
		url : "getPartyRelationList.shtml",
		data : {
			partyId : loginPartyId
		},
		title : "任务分派",
		isCheckBox : true,
		columns : {
			field : 'partyId',
			title : 'partyName'
		},
		checkBack : function(checkData) {
			ajaxPostJson({
				url : "allotTask.shtml",
				data : {
					processId : processId,
					processNodeId : processNodeId,
					partyId : checkData[0].partyId
				},
				success : function(resultJson) {
					var resultObj = eval(resultJson);
					if (resultObj.type == "S") {
						fairAlert.success({
							"msg" : "任务分派成功！"
						});
						location.reload(false);
					} else {
						fairAlert.def({
							"msg" : resultObj.message
						});
					}
				}
			});

		}
	});
}


// 上传文件
function AjaxFileUpload(fileElementId,successCallback,errorCallback){
	$.ajaxFileUpload({
		url: 'fileUpload.shtml',
	    fileElementId:fileElementId,
	    secureuri : false, 
	    dataType : 'json', 
	    success:function(data, status){
	    	$("#"+fileElementId).val("");
	    	successCallback(data, status);
	    },
	    error:function (data, status, e) {
	    	errorCallback(data, status, e);
	    }
	});
}
// 删除文件
function AjaxFileRemove(fileName,successCallback,errorCallback){
	$.ajaxFileUpload({
		url: 'fileUpload.shtml',
		fileElementId:fileElementId,
		secureuri : false, 
		dataType : 'json', 
		success:function(data, status){
			$("#"+fileElementId).val("");
			console.log(data);
			successCallback(data, status);
		},
		error:function (data, status, e) {
			console.log(data);
			errorCallback(data, status, e);
		}
	});
}

function getQueryUrlValue(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

function toLoginPage(){
	top.location.href ='index.shtml';
}



// 重置表单元素
var resetForm = function(form_id){
	// 重置基本表单
	document.getElementById(form_id).reset(); 
	// 重置下拉列表
	$(".chosen-select").trigger("chosen:updated");
	// 重置多项选择框
	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green"});
	// 重置表单验证
	$("#"+form_id).validate().resetForm();
	// 重置特殊属性的隐藏框
	$('#'+form_id+' input[type="hidden"]').each(function(){
		if($(this).data("reset") == true || $(this).data("reset") == "true") {
			$(this).val('');
		}
	});
}

// 创建头部文本导航
var createPageNav = function(arr,backUrl){
	var html = '<ol class="breadcrumb pull-left"><li><i class="fa fa-home"  style="font-size:20px"></i>&nbsp<a href="javascript:;">首页</a></li>' 
		$.each(arr,function(key){
			if(arr[key].url != null && arr[key].url.length!=0){
				html += '<li><a href="'+arr[key].url+'">'+arr[key].name+'</a></li>'
			}else{
				html += '<li class="active">'+arr[key].name+'</li>'
			}
		});
	
	html += '</ol><div class="ibox-tools"><a class="btn btn-white btn-rounded btn-outline pull-right" href="'+backUrl+'"><i class="fa fa-arrow-left"></i>&nbsp返回</a></div>'
	
	$(".page_top_nav").html(html);
}

function iframeAutoFit(){
    var ex;
    try{
        if(window!=parent){
            var a = parent.document.getElementsByTagName("IFRAME");
            for(var i=0; i<a.length; i++) {
                if(a[i].contentWindow==window){
                    var h1=0, h2=0;
                    if(document.documentElement&&document.documentElement.scrollHeight)
                    {
                        h1=document.documentElement.scrollHeight;
                    }
                    if(document.body) h2=document.body.scrollHeight;

                    var h=Math.max(h1, h2);
                    if(document.all) {h += 4;}
                    if(window.opera) {h += 1;}
                    a[i].style.height = h +"px";
                }
            }
        }
    }catch (ex){}
}


// ajaxPostjson简单封装
var ajaxPostJson = function(options){
	var defaults={
		type : "POST",
		url : "", 
		dataType: "json",
		data: '',
		success:function(){},
		error:function(){}
	};
	var opts = $.extend(defaults,options);
	$.ajax(opts);
}

/**
 * 扩展方法下拉列表值变化，发送ajax请求
 */
$.fn.chosenValChange = function (options) {
	var defaults={
		url : "", 
		data: "",
		success:function(){}
	};
	var opts = $.extend(defaults,options);
	ajaxPostJson(opts);
};


/* 
*  方法:Array.remove(dx) 通过遍历,重构数组 
*  功能:删除数组元素. 
*  参数:dx删除元素的下标. 
*/ 
Array.prototype.remove=function(dx) { 
    if(isNaN(dx)||dx>this.length){
    	return false;
    } 
    for(var i=0,n=0;i<this.length;i++) { 
        if(this[i] != this[dx]) { 
            this[n++]=this[i] 
        } 
    } 
    this.length-=1 
} 
