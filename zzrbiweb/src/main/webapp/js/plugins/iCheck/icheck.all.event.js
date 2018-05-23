$(function(){
	jQuery.extend({
		// checked事件
	    onChecked:function(){
	    	
	    	var t = $(this).data("toggle"),f = $(this).data("check-from"),to = $(this).data("check-to");
			
			// 点击的为checkbox
			if("checkbox" == t){
				
				$(this).iCheck('toggle'); 
				
				// 判断被点击元素是被勾选还是未被勾选
				var b = $(this).find('div').hasClass('checked');
				
				// 如果被点击元素设置为勾选，则判断设置下级元素为选中，相反则结果也相反
				if(to != undefined && to != null){
					if(b){
						$(to+" .i-checks").iCheck('check');
					}else{
						$(to+" .i-checks").iCheck('uncheck');
					}
				}
				if(f != undefined && f != null){
					// 如果被点击元素未勾选，则将上级元素设置为未选
					if(!b){
						$(f).iCheck('uncheck');
					}
					// 循环遍历与点击同级元素,判断是否全部为选中，如果全部为选中，则设置上一级元素为选中
					$.onCheckedFrom(f);
				}
			}		
	    },
	    // 控制上级元素
		onCheckedFrom:function(f){
			var b = true;
			$("div[data-check-from='"+f+"']").each(function(){
				if(!$(this).find('div').hasClass('checked')){
					b = false;
					return false;
				}
			});
			if(b){
				$(f).iCheck('check');
			}else{
				$(f).iCheck('uncheck');
			}
			var cf = $(f).data("check-from");
			if(cf != undefined && cf != null){
				$.onCheckedFrom(cf);
			}
		},
	});
	$('.i-checks').on('ifClicked',$.onChecked);
});