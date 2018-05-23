/**
 * 将返回的结果放入表单中
 * 
 * 
 */
(function($) {
	$.fn.setForm = function(jsonValue) {
		var obj=this;
		$.each(jsonValue, function (name, ival) {
			 var $oinput = obj.find("input[name=" + name + "]"); 
			 if($oinput != undefined && $oinput.length != 0){
				 if ($oinput.attr("type")== "radio" || $oinput.attr("type")== "checkbox"){
					 $oinput.each(function(){
						 // 是复选框，并且是数组
						 if(Object.prototype.toString.apply(ival) == '[object Array]'){
							 for(var i=0;i<ival.length;i++){
								 if($(this).val()==ival[i]){
									 $(this).attr("checked", "checked");
								 }
							 }
						 } else{
							 if($(this).val() == ival){
								 $(this).attr("checked", "checked");
							 }
						 }
					 });
				 }else{
					 if(obj.find("[name="+name+"]") != undefined && obj.find("[name="+name+"]").length !=0){
						 obj.find("[name="+name+"]").val(ival); 
					 }
				 }
			 }else{
				 if(obj.find("[name="+name+"]") != undefined && obj.find("[name="+name+"]").length !=0){
					 obj.find("[name="+name+"]").val(ival); 
				 }
			 }
		});
	}
})(jQuery);
