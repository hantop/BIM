$(document).ready(function(){
	//日历控件
	$("#priceDate").datepicker({
		format: "yyyy-mm-dd",
		language: 'zh_CN',
	    orientation: "top left",
	    multidate: false
	});
	// 金额转大写
	
	$("#priceAmount").blur(function(){
		var nums = $('#priceAmount').val();
		var upperCase = Arabia_to_Chinese(nums);
		$('.upperCase').val(upperCase);
	});
	// 导航菜单
	createPageNav([{name:"押品登记",url:"goods_register.shtml"},{name:"添加押品"}], "goods_register.shtml");
	var fileData = [];
	// 加载数据
	function loadPageData(){
		var processId = getQueryUrlValue("processId");
		if(processId !=undefined && processId.length !=0){
			ajaxPostJson({
				url:"getWaitingRegister.shtml",
				data:{processId:getQueryUrlValue("processId")},
				success:function(resultJson){
					var resultObj = eval(resultJson);
					if(resultObj.type == "S") {
						var processId = getQueryUrlValue("processId");
						var processNodeId = getQueryUrlValue("processNodeId");
						$("#add_goods_form").setForm(resultObj);
						$("#processId").val(processId);
						$("#processNodeId").val(processNodeId);
						$("#fileList").val(JSON.stringify(resultObj.fileList));
						var nums = $('#priceAmount').val();
						var upperCase = Arabia_to_Chinese(nums);
						$('.upperCase').val(upperCase);
						if(resultObj.fileData != undefined && resultObj.fileData.length !=0){
							fileData = resultObj.fileData;
							initFileUpload(fileData);
						}else{
							initFileUpload(fileData);
						}
						setTimeout(function(){
							$("#bossId").trigger("chosen:updated");
						}, 100);
					}else{
						if(resultObj.code == "SYS_01"){
							// 未登录认证
							toLoginPage();
						}
					}
				}
			});
		}else{
			initFileUpload(fileData);
		}
	}
	loadPageData();
	
	// 添加文件
	var addFile = function(fileUID,fileTitle,fileName){
		removeFile(fileUID);
		var fileList = $('#fileList').val();
		if(fileList == undefined || fileList.length ==0){
			$('#fileList').val('[{"fileUID":"'+fileUID+'","fileType":"'+fileTitle+'","fileName":"'+fileName+'"}]');
		}else{
			var fileJson = jQuery.parseJSON(fileList);
			fileJson.push(jQuery.parseJSON('{"fileUID":"'+fileUID+'","fileType":"'+fileTitle+'","fileName":"'+fileName+'"}'));
			$("#fileList").val(JSON.stringify(fileJson));
		}
	} 
	// 删除文件
	var removeFile = function(fileUID){
		var fileList = $('#fileList').val();
		if(fileList !=undefined && fileList.length !=0){
			var fileJson = jQuery.parseJSON(fileList);
			$.each(fileJson,function(_key){
				if(fileJson[_key].fileUID == fileUID){
					delete fileJson[_key];
				}
			});
			var json = [];
			$.each(fileJson,function(_key){
				if(fileJson[_key] != undefined && fileJson[_key] !=null){
					json.push(jQuery.parseJSON('{"fileUID":"' + fileJson[_key].fileUID + '","fileType":"' + fileJson[_key].fileType + '","fileName":"' + fileJson[_key].fileName + '"}')); 
				}
			});
			if(json.length == 0){
				$("#fileList").val("");
			}else{
				$("#fileList").val(JSON.stringify(json));
			}
		}
	} 
	
	function initFileUpload(files){
		var checkMap = new Map();
		$.each(files,function(i,row){
			checkMap.put(row.fileUID, row.fileName);
		});
		$("#addFilePanel").uploadFile({
			initFiles:files,
			maxSize:10*1024,
			columnsNum:3,
			uploadCallback:function(fileElementId,panelElementId,fileUID,fileTitle){
				// 点击上传按钮回调
				AjaxFileUpload(fileElementId,function(data, status){
					var resultObj = eval(data);
					if(resultObj.type == "S") {
						checkMap.put(fileUID, resultObj.fileName);
						addFile(fileUID, fileTitle, resultObj.fileName);
						fairAlert.success({"msg":"文件上传成功"});
						console.log(checkMap+'Map')
					}else{
						fairAlert.error({"msg":"文件上传失败"});
					}
				}, function(data, status, e){
					fairAlert.error({"msg":"文件上传失败"});
				});
				
			},removeCallback:function(fileUID){
				// 点击删除按钮回调
				var fileName = checkMap.get(fileUID);
				if(fileName != undefined && fileName!=null){
					ajaxPostJson({
						url:"removeFile.shtml",
						data:{fileName:fileName},
						success:function(resultjson){
							var resultObj = eval(resultjson);
							if(resultObj.type == "S") {
								removeFile(fileUID);
								fairAlert.success({"msg":"删除文件成功"});
							}else{
								fairAlert.error({"msg":"删除文件失败"});
							}
						},
						error:function(){
							fairAlert.error({"msg":"删除文件失败"});
						}
					});
				}
			},failedCallback:function(errorId,errorMsg){
				// 上传文件错误回调
				fairAlert.error({"msg":errorMsg});
			}
		});
	}
	
	var submitUrl = "";
	
	$("#add_goods_form").validate({submitHandler:function(form){
		$(form).ajaxSubmit({
			url : submitUrl, 
			dataType: "json",
			type:"post",
			clearForm:true,
			resetForm: true,
			success: function(responseText, statusText){
				var resultObj = eval(responseText);
				if(resultObj.type == "S") {
					fairAlert.success({
						msg:'保存成功！',
						callback:function(){
							window.location.href='goods_register.shtml';
						}
					});
				}else{
					fairAlert.error({"msg":","+resultObj.message});
				}
			}
		});
	}});
	
	$("#btn_save").click(function(){
		submitUrl = "goodsSave.shtml";
		$("#add_goods_form").submit();
	});
	// 提交流程
	$("#submit_process").click(function(){
		submitUrl = "goodsSubmit.shtml";
		$("#add_goods_form").submit();
	});
	// 重置表单
	$('#reset_btn').click(function(){
		resetForm("add_goods_form");
		$("#addFilePanel").uploadFile.resetUpload();
	});
});

