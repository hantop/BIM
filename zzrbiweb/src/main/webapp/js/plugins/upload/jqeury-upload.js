/**
 * fair-upload-panel
 * 
 * 文件上传插件
 */
(function($) {
	
	var t,l_w,s = ".file-upload-panel",p = ".fair-upload-panel",opts,m_w = 220,accept = [];
	
	var img = ["JPG","JPEG","BMP","GIF","PNG","jpg","jpeg","bmp","gif","png"]; // 图片类型
	
	var pdf = ["pdf","PDF"];
	
	var web = ["html","htm","HTML","HTM"];
	
	
	// 绘制默认面板
	var drawDefUploadPanel = function(){
		var seq = new Date().getTime();
		var html = '';
		html+='<li id="li_'+seq+'" style="width:'+l_w+'" ><center><div class="fair-upload-panel">';
		html+='<div class="fair-title"><input type="text" id="fileTitle'+seq+'" name="fileTitle" value="其他" style="text-align: center;border: 0px;"></div>';
		html+='<div class="fair-button"><a href="javascript:void(0);" class="btn btn-default btn-sm">选择<input id="upload_file_'+seq+'" name="file" class="upload-file" data-preview="#preview_'+seq+'" type="file" accept="'+accept.join()+'">文件</a></div>';
		html+='<div class="fair-preview" id="preview_'+seq+'"><img width="100%" src="images/default.png"></div>';
		html+='<div class="fair-tools"><button type="button" data-tool-flag="delete" data-li-panel="#li_'+seq+'" class="btn btn-outline btn-link">删除</button>';
		html+='<button type="button" data-tool-flag="upload" data-file-input="upload_file_'+seq+'" data-li-panel="#li_'+seq+'" class="btn btn-outline btn-link">上传</button></div>';
		html+='<input type="hidden" id="fileUID" name="fileUID" value="'+seq+'">'
		html+='</div></center></li>';
		$(s+" ul").append(html);
		resizeUploadPanel();
		
		$("input[type='file']").change(choiceFileEvent);
		
		$("#li_"+seq+" button").click(buttonEvent);
	}
	
	// 绘制初始化面板
	var drawInitUploadPanel = function(){
		var html = '';
		if(opts.initFiles != undefined && opts.initFiles != null){
			for(var seq=0;seq<opts.initFiles.length;seq++){
				html+='<li id="li_'+seq+'" style="width:'+l_w+'" ><center><div class="fair-upload-panel">';
				if(opts.initFiles[seq].isMust != undefined && opts.initFiles[seq].isMust == true){
					html+='<div class="fair-title"><span class="form-valid-field">*</span><label>'+opts.initFiles[seq].fileTitle+'</label></div>';
				}else{
					html+='<div class="fair-title"><label>'+opts.initFiles[seq].fileTitle+'</label></div>';
				}
				html+='<div class="fair-button"><a href="javascript:void(0);" class="btn btn-default btn-sm">选择<input id="upload_file_'+seq+'" name="file" class="upload-file" data-preview="#preview_'+seq+'" type="file" accept="'+accept.join()+'">文件</a></div>';
				html+='<div class="fair-preview" id="preview_'+seq+'">'+rePreview(html,getFileType(opts.initFiles[seq].url),opts.initFiles[seq].url)+'</div>';
				html+='<div class="fair-tools"><button type="button" data-tool-flag="delete" data-li-panel="#li_'+seq+'" class="btn btn-outline btn-link">删除</button>';
				html+='<button type="button" data-tool-flag="upload" data-file-input="upload_file_'+seq+'" data-li-panel="#li_'+seq+'" class="btn btn-outline btn-link">上传</button></div>';
				html+='<input type="hidden" id="fileUID" name="fileUID" value="'+opts.initFiles[seq].fileUID+'">';
				html+='<input type="hidden" id="fileTitle'+seq+'" name="fileTitle" value="'+opts.initFiles[seq].fileTitle+'">';
				html+='</div></center></li>';
			}
			$(s+" ul").append(html);
			resizeUploadPanel();
			$("input[type='file']").change(choiceFileEvent);
			$(".fair-tools button").click(buttonEvent);
		}
	}
	// 绘制图片预览视图
	var drawPreviewPanel = function(){
		var html = '';
		console.log(opts.data);
		console.log(opts.data.length);
		for(var seq=0;seq<opts.data.length;seq++){
			html+='<li style="width:'+l_w+'" ><center><div class="fair-upload-panel">';
			html+='<div class="fair-title"><label>'+opts.data[seq].fileType+'</label></div>';
			html+='<div class="fair-preview">'+rePreview(html,getFileType(opts.data[seq].url),opts.data[seq].url)+'</div>';
			html+='<div class="fair-tools"><button type="button" data-tool-flag="delete" data-li-panel="#li_'+seq+'" class="btn btn-outline btn-link">预览</button>';
			html+='<a target="_blank" class="btn btn-outline btn-link" href="'+opts.downloadUrl+'?fileName='+opts.data[seq].fileName+'">下载</a></div>';
			html+='</div></center></li>';
		}
		$(t).find("ul").append(html);
		
		resizeUploadPanel();
	}
	
	// 重新绘制面板的大小
	var resizeUploadPanel = function(){
		var s_w = $(s).outerWidth();
		var p_w = s_w/opts.columnsNum;
		if(p_w > m_w){
			$(p).width(m_w);
		}else{
			$(p).width(p_w);
		}
		var view_w = $(".fair-preview").width();
		$(".fair-preview img").height(view_w);
		$(".fair-preview textarea").height(view_w);
		$(".fair-preview div").height(view_w);
		$(".fair-preview embed").height(view_w);
	}
	
	// 选择文件事件
	var choiceFileEvent = function(e){
		// 上传的文件
		var file  = e.target.files[0];
		
		if(!file){
			return ;
		}
		// 文件扩展名
		var fileContentType = getFileType(file.name);
		// 文件类型不正确
		if(opts.fileType.length != 0 && opts.fileType.join().indexOf(fileContentType) <= -1){
			$(this).val("");
			opts.failedCallback("file_type_failed","文件类型不正确");
			console.log("file_type_failed:文件类型不正确");
			return ;
		}
		// 文件超出最大支持范围
		if(opts.maxSize < Math.round(file.size/1024)){
			$(this).val("");
			opts.failedCallback("file_size_failed","文件大小超出范围");
			console.log("file_size_failed:文件大小超出范围");
			return ;
		}
		
		if(img.join().indexOf(fileContentType) > -1){
			
			// 图片类型
			imagePreview($(this),file);
			
		}else if(web.join().indexOf(fileContentType) > -1){
			// 网页
			webPreview($(this),file);
			
		}else if(pdf.join().indexOf(fileContentType) > -1){
			
			// pdf
			pdfPreview($(this),file);
			
		}else{
			
			// 文本
			defPreview($(this),file);
		}
		
	}
	// 默认视图
	var defPreview = function($this,file){
		var view = $this.data("preview");
		var reader = new FileReader();
		reader.readAsText(file,"UTF-8");
		reader.onload = function ( event ) { 
			var r_data = event.target.result;
			$(view).html('<textarea style="width:100%;overflow:auto;color: #428bca;resize: none;" readonly=true>'+r_data+'</textarea>');
			resizeUploadPanel();
		};
	}
	// 图片视图
	var imagePreview = function($this,file){
		var t = $this.data("preview");
		var url = URL.createObjectURL(file);   
		$(t).html('<img src='+url+' width="100%">');
		resizeUploadPanel();
	}
	// pdf视图
	var pdfPreview = function($this,file){
		var t = $this.data("preview");
		var url = URL.createObjectURL(file);   
		$(t).html('<embed class="kv-preview-data" src="'+url+'" width="100%" type="application/pdf">');
		resizeUploadPanel();
	}
	// web视图
	var webPreview = function($this,file){
		var t = $this.data("preview");
		var reader = new FileReader();
		reader.readAsText(file,"UTF-8");
		reader.onload = function ( event ) { 
			var r_data = event.target.result;
			var REG_BODY = /<body[^>]*>([\s\S]*)<\/body>/;
			$(t).html('<div style="width:100%;overflow:auto;">'+REG_BODY.exec(r_data)+'</div>');
			resizeUploadPanel();
		};
	}
	
	var rePreview = function(html,fileContentType,url){
		if(url == undefined || url.length == 0){
			return '<img width="100%" src="images/default.png">';
		}else{
			if(img.join().indexOf(fileContentType) > -1){
				// 图片类型
				return '<img width="100%" src="'+url+'">';
			}else if(web.join().indexOf(fileContentType) > -1){
				// 网页
				var REG_BODY = /<body[^>]*>([\s\S]*)<\/body>/;
				var webHtml = ajaxReadFile(url);
				return '<div style="width:100%;overflow:auto;">'+REG_BODY.exec(webHtml)+'</div>';
			}else if(pdf.join().indexOf(fileContentType) > -1){
				// pdf
				return '<embed class="kv-preview-data" src="'+url+'" width="100%" type="application/pdf">';
			}else{
				// 文本
				var txtHtml = ajaxReadFile(url);
				return '<textarea  style="width:100%;overflow:auto;color: #428bca;resize: none;" readonly=true>'+txtHtml+'</textarea>';
			}
		}
	}
	
	// 按钮事件
	var buttonEvent = function(){
		var flag = $(this).data("tool-flag");
		if(flag == "upload"){
			var fileElementId = $(this).data("file-input");
			var panelElementId = $(this).data("li-panel");
			var fileValue = $("#"+fileElementId).val();
			if(fileValue == undefined || fileValue.length == 0){
				opts.failedCallback("failed_file","文件已上传或未选择文件");
				return ;
			}
			opts.uploadCallback(fileElementId,panelElementId,$(panelElementId+" #fileUID").val(),$(panelElementId+' input[name="fileTitle"]').val());
		}else{
			var panelElementId = $(this).data("li-panel");
			opts.removeCallback($(panelElementId+" #fileUID").val());
			$(panelElementId).remove();
		}
	}
	
	// 读取文件
	var ajaxReadFile = function(fileName,url){
		$.ajax({
			async:false,
			url:opts.readFileUrl,
			data:{url:url},
			type:"post",
			dataType:"json",
			success:function(data){
				result = data;
				return data;
			}
		});
	}
	
	// 获取文件扩展名
	function getFileType(fileName){
		return fileName.match(/^(.*)(\.)(.{1,8})$/)[3];
	}
	// 文件上传
	$.fn.uploadFile = function(options) {
		// 配置项
		var defaults = {
		   readFileUrl:"readFile.shtml",
           fileType:[], 	// 支持的文件类型
           maxSize:50*1024, // 支持最大文件  单位K
           initFiles:[],    // 初始化文件试图{fileUID:"文件标识",fileTitle:"文件标题",isMust:"是否必须",url:"路径"}
           columnsNum:3,	// 每行显示几列
           uploadCallback:function(){},  // 上传按钮事件
           removeCallback:function(){},  // 删除按钮事件
           failedCallback:function(){}   // 文件类型，文件大小等回调事件
		}
		t = this,opts = $.extend(defaults,options),l_w = 100/opts.columnsNum+"%";
		
		$.each(opts.fileType,function(_k){
			if(opts.fileType[_k].indexOf(".")<=-1){
				accept.push("."+opts.fileType[_k]);
			}else{
				accept.push(opts.fileType[_k]);
			}
		});
		
		$(s).html('<ul class="list-inline"></ul>');
		
		// 绑定添加按钮
		$(this).click(drawDefUploadPanel);
		
		// 初始化panel
		drawInitUploadPanel();
		
		// 绑定resize事件
		$(window).bind("load resize",function() {
			resizeUploadPanel();
		});
	};
	// 重置表单
	$.fn.uploadFile.resetUpload = function(options) {
		$(s).html('<ul class="list-inline"></ul>');
		drawDefUploadPanel();
	};
	// 文件预览视图
	$.fn.filePreview = function(options) {
		var defaults = {
		   readFileUrl:"readFile.shtml",
		   downloadUrl:"downloadFile.shtml",
		   data:[],
           columnsNum:3,
		}
		opts = $.extend(defaults,options);
		
		t = this;
		
		$(t).html('<ul class="list-inline"></ul>');
		
		drawPreviewPanel();
		
		// 绑定resize事件
		$(window).bind("load resize",function() {
			resizeUploadPanel();
		});
	};
	
	
})(jQuery);







































$.extend({
    createUploadIframe: function(id){
        var frameId = 'jUploadFrame' + id;
        return $('<iframe>').attr({id:frameId,name:frameId}).css({top:'-9999px',left:'-9999px',position:'absolute'}).appendTo('body')[0];
    },
    createUploadForm: function(id, fileElementId){
        var formId = 'jUploadForm' + id,fileId = 'jUploadFile' + id,oldElement = $('#' + fileElementId),newElement = $(oldElement).clone();
        return $('<form>').attr({id:formId,name:formId}).append($(oldElement).attr('id', fileId).before(newElement)).css({position:'absolute',top:'-1200px',left:'-1200px'}).appendTo('body')[0];
    },
    addOtherRequestsToForm: function(form,data){
        var originalElement = $('<input type="hidden"/>');
        for (var key in data) originalElement.clone().attr({'name':key,'value':data[key]}).appendTo(form);
        return form;
    },
    ajaxFileUpload: function(s) {
        s = $.extend({}, $.ajaxSettings, s);
        var id = new Date().getTime(),io = $.createUploadIframe(id),form = $.createUploadForm(id, s.fileElementId),frameId = 'jUploadFrame' + id,formId = 'jUploadForm' + id,requestDone = false,xml = {};
        if ( s.data ) form = $.addOtherRequestsToForm(form,s.data);
        if ( s.global && ! $.active++ ) $.event.trigger( "ajaxStart" );
        if ( s.global ) $.event.trigger("ajaxSend", [xml, s]);
        var uploadCallback = function(isTimeout){
            var io = $('#'+frameId)[0];
            try{
            	var ioContent=io.contentWindow||io.contentDocument;
                if(ioContent)xml.responseText = ioContent.document.body?ioContent.document.body.innerHTML:null,xml.responseXML = ioContent.document.XMLDocument?ioContent.document.XMLDocument:ioContent.document;
            }catch(e){
                $.handleError(s, xml, null, e);
            }
            if ( xml || isTimeout == "timeout"){
                requestDone = true;
                var status;
                try {
                    status = isTimeout != "timeout" ? "success" : "error";
                    if ( status != "error" ){
                        var data = $.uploadHttpData( xml, s.dataType );
                        if ( s.success ) s.success( data, status );
                        if( s.global )$.event.trigger( "ajaxSuccess", [xml, s] );
                    } else
                        $.handleError(s, xml, status);
                } catch(e){
                    status = "error";
                    $.handleError(s, xml, status, e);
                }
                if( s.global )
                    $.event.trigger( "ajaxComplete", [xml, s] );
                if ( s.global && ! --$.active )
                    $.event.trigger( "ajaxStop" );
                if ( s.complete )
                    s.complete(xml, status);
                $(io).off()
                setTimeout(function(){
	                try{
		                $(io,form).remove();
		                $('#' + formId).remove();
	                } catch(e){
                        $.handleError(s, xml, null, e);
                    }
                }, 100)
                xml = null
            }
        }
        if ( s.timeout > 0 )
            setTimeout(function(){if( !requestDone ) uploadCallback( "timeout" );}, s.timeout);
        try{
            $('#' + formId).attr({action:s.url,method:'POST',target:frameId,encoding:'multipart/form-data',enctype:'multipart/form-data'}).submit();
        } catch(e){
            $.handleError(s, xml, null, e);
        }
        $('#'+frameId).on('load',uploadCallback);
        return {abort: function () {}};
    },
    uploadHttpData: function( r, type ) {
        var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;
        if ( type == "script" ){
        	$.globalEval( data );
        }
        if ( type == "json" ){
        	console.log(data);
        	data = $.parseJSON(data.replace(/<.*?>/ig,""));
        	console.log(data);
        }
        if ( type == "html" ){
        	$("<div>").html(data).evalScripts();
        }
        return data;
    },
    handleError: function( s, xhr, status, e ){
        if ( s.error )
            s.error.call( s.context || s, xhr, status, e );
        if ( s.global )
            (s.context ? $(s.context) : $.event).trigger( 'ajaxError', [xhr, s, e] );
    }
});