$.extend({
	includePath : '',
	include : function(file) {
		var files = typeof file == "string" ? [ file ] : file;
		for (var i = 0; i < files.length; i++) {
			var name = files[i].replace(/^\s|\s$/g, "");
			var att = name.split('.');
			var ext = att[att.length - 1].toLowerCase();
			var isCSS = ext == "css";
			var tag = isCSS ? "link" : "script";
			var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
			var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
			if ($(tag + "[" + link + "]").length == 0){
				$("body").append("<" + tag + attr + link + "></" + tag + ">");
			}
		}
	}
}); 
var v = "1.6";
	
$.include("js/bootstrap.min.js?v="+v);
$.include("js/tool/jquery.form.js?v="+v);
$.include("js/tool/jquery.setForm.js?v="+v);
$.include("js/tool/jquery.md5.js?v="+v);
$.include("js/tool/jquery.map.js?v="+v);
$.include("js/plugins/validate/jquery.validate.min.js?v="+v);
$.include("js/plugins/validate/messages_zh.min.js?v="+v);
$.include("js/plugins/validate/validate-methods.js?v="+v);
$.include("js/plugins/bootstrap-table/bootstrap-table.js?v="+v);
$.include("js/plugins/bootstrap-table/tableExport.js?v="+v);
$.include("js/plugins/bootstrap-table/bootstrap-table-export.js?v="+v);
$.include("js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?v="+v);
$.include("js/plugins/bootstrap-table/jquery-boostrap-table.js?v="+v);
$.include("js/plugins/datapicker/bootstrap-datepicker.js?v="+v);
$.include("js/plugins/chosen/chosen.jquery.js?v="+v);
$.include("js/plugins/iCheck/icheck.min.js?v="+v);
$.include("js/plugins/iCheck/icheck.group.event.js?v="+v);
$.include("js/plugins/alert/jquery-alert.js?v="+v);
