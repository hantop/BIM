$(document).ready(function() {  
		$("#query_role").click(queryRoleData);
		
		
//		复选框
		$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green"});
		$('.i-checks').on('ifClicked',$.onChecked);
		
		$('#addrole_btn').click(function(){
			$('.i-checks').iCheck('uncheck');
			$('#add-rolename').val('');
			$('#addrole_box').show();
		});
		
		$('#close_addrole').click(function(){
			$('#addrole_box').hide();
		});
		$("#save_addrole").on('click',function(){
			var flag = $('#addrole_box .modal-body').checkReqfield();
			var obj= {
					selector : '#check_items',
					rolenameSlctor :'#add-rolename',
					orgid : 'ZZY-001-0001',
			};
			if(flag){
				addRole(obj);
				$('#addrole_box').hide();
			}
		});
		
		$('#close_updaterole').click(function(){
			$('#updaterole_box').hide();
		})
		
		$("#save_updaterole").on('click',function(){
			var flag = $('#updaterole_box .modal-body').checkReqfield();
			if(flag){
				updateRole('#update_check_items','#update-rolename');
				$('#updaterole_box').hide();
			}
		});
		
		function queryRoleData(){
			$("#datatable").jqGrid({
				url : "getRoleList.shtml", // 请求地址
				data: {roleName: $("#search_rolename").val()},
				columns : [ 
				    {field : "roleId",title : "角色标识",width : "0%",visible:false}, 
				    {field : "roleName",title: '角色名称',width : "15%",align: 'center',valign: 'middle'}, 
				    {field : "permissionName",title : "权限",width : "70%",align: 'center',valign: 'middle'}, 
				    {field : "operate",title : "操作",width : "15%",align: 'center',valign: 'middle',
				    	formatter:function(value,row,index){
							return "<a href='javascript:void(0);' class='trans-info'>&nbsp;编辑</a>&nbsp<a href='javascript:void(0);' class='remove'>&nbsp;删除</a>";					    		
					    },
					    events:{
					    	'click .trans-info': function (e, value, row, index) {
					    		console.log('点击编辑')
					    		$('#updaterole_box').show();
					    		$('.i-checks').iCheck('uncheck');
					    		$('input#update-rolename').val(row.roleName);
					    		$('input#update-rolename').data('updateroleid',row.roleId);
					    		$('input#update-rolename').data('updateorgid',row.orgId);
					    		var arr = row.permissionName.split(',');
					    		$('.i-checks').each(function(index, el) {
					    			var _that = this;
					    			var _pname = $(this).data('permissionName');
					    			$.each(arr,function (i,el) {
					    				if (this==_pname) {
					    					$(_that).iCheck('check');
					    				};
					    			});
					    		});
				   			},
					    	'click .remove': function (e, value, row, index) {
					    		var opts = {
					    				msg:'您确定要删除该角色吗？删除后不可恢复！',
					    				confirmCallback:function (){
					    					$.ajax({
					    						url: 'removeRole.shtml',
					    						type: 'POST',
					    						data: {
					    							roleId : row.roleId
					    						},
					    						success: function(data){
					    							if (data.code==0) {
					    								//删除成功
					    								alert('删除成功')
					    								queryRoleData();
					    							}else{
					    								alert('删除失败');
					    							};
					    						}
					    					});
					    				}
					    		}
					    		fairAlert.confirm(opts);
				   			},
				   		}
				    }, 
				]
			});
		}
		queryRoleData();
		
		
		
		$.fn.checkReqfield = function () {
			var fa = true;
			$(this).find('.req_field').each(function(i,e){
				var req_id = $(this).data('requireFrom');
		    	if ($('#'+req_id).attr('type')=='text'||$('#'+req_id).attr('type')=='password') {
			    	var req_val = $('#'+req_id).val().trim();
			    	if (!req_val) {
			    		alert('不能为空1');
			    		fa = false;
			    	};
		    	};
		    	if ($('#'+req_id).attr('type')=='checkbox'||$('#'+req_id).attr('type')=='radio') {
		    		var req_val = $('#'+req_id+':checked').val();
		    		var checknum = 0;
			    	if (!req_val) {
			    		var groupname = $('#'+req_id).data("group");
			    		$('input[data-group="'+groupname+'"]').each(function(index, el) {
			    			if ($('input:checked').val()) {
								checknum++;
			    			};
			    		});
			    		if(!checknum){
			    			alert('不能为空2');
			    			fa = false;
			    		};
			    	};
		    	};
			})
			return fa;
	    };

		//添加角色
		function addRole(obj){
			var roleName = $(obj.rolenameSlctor).val();
			var orgId = obj.orgid;
			var pstr = '';
			$(obj.selector).find('.i-checks').each(function(i, el) {
				var pid = $(this).data('permissionId');
				var pname = $(this).data('permissionName');
				if($(this).find('div').hasClass('checked')){
					pstr += '{permissionId:'+'"'+pid+'",permissionName:'+'"'+pname+'"},';
				};
			});
			var plist = "["+pstr.slice(0,-1)+"]";
				$.ajax({
					url: 'addRole.shtml',
					type: 'POST',
					data: {roleName: roleName,
							orgId:orgId,
							permissionList:plist
					},
					success: function(data){
						if (data.code==0) {
							alert('数据保存成功add');
							queryRoleData();
							$('#addrole_box').hide();
						}else{
							alert('保存失败add');
						};
					}
				});
		};
		//编辑角色
		function updateRole(selector,rolenameSlctor){
			var roleName = $(rolenameSlctor).val();
			var roleId = $(rolenameSlctor).data('updateroleid');
			var orgId = $(rolenameSlctor).data('updateorgid');
			console.log(roleName+'==='+roleId+'==='+orgId)
			var pstr = '';
			$(selector).find('.i-checks').each(function(i, el) {
				var pid = $(this).data('permissionId');
				var pname = $(this).data('permissionName');
				if($(this).find('div').hasClass('checked')){
					pstr += '{permissionId:'+'"'+pid+'",permissionName:'+'"'+pname+'"},';
				};
			});
			var plist = "["+pstr.slice(0,-1)+"]";
			console.log(plist+'授权列表')
				$.ajax({
					url: 'updateRole.shtml',
					type: 'POST',
					data: {roleName: roleName,
							roleId:roleId,
							orgId:orgId,
							permissionList:plist
					},
					success: function(data){
						if (data.code==0) {
							alert('数据保存成功up');
							queryRoleData();
							$('#myModal2').hide();
						}else{
							alert('保存失败up');
						};
					}
				});
//			$.ajax({
//				url: 'updateRole.shtml',
//				type: 'POST',
//				data: {roleName: '100法务',
//						roleId:"00201702201423578776",
//						orgId:"ZZY-001-0001",
//						permissionList:'[{permissionId:"undefined",permissionName:"我的工作台"},{permissionId:"1_1_WORK",permissionName:"待办任务"},{permissionId:"1_2_WORK",permissionName:"已完成任务"}]'
//				},
//				success: function(data){
//					if (data.code==0) {
//						alert('数据保存成功up');
//						queryRoleData();
//						$('#myModal2').hide();
//					}else{
//						alert('保存失败up');
//					};
//				}
//			});
		};
		
		
	});  
