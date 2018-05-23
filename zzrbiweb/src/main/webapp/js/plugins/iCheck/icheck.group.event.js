$(function() {
	jQuery.extend({
		// checked事件
		onChecked : function() {

			var t = $(this).data("toggle"), f = $(this).data(
					"check-from"), to = $(this).data("check-to");

			// 点击的为checkbox
			if ("checkbox" == t) {

				$(this).iCheck('toggle');

				// 判断被点击元素是被勾选还是未被勾选
				var b = $(this).find('div').hasClass('checked');

				// 如果被点击元素设置为勾选，则判断设置下级元素为选中，相反则结果也相反
				if (to != undefined && to != null) {
					if (b) {
						$(to + " .i-checks").iCheck('check');
					} else {
						$(to + " .i-checks").iCheck('uncheck');
					}
				}
				if (f != undefined && f != null) {
					// 如果被点击元素未勾选，则将上级元素设置为未选
					if (!b) {
						$(f).iCheck('uncheck');
					}
					// 循环遍历与点击同级元素,判断是否全部为选中，如果全部为选中，则设置上一级元素为选中
					$.onCheckedFrom(f);

					$.onCheckedAll();
				}
			}
		},
		// 控制上级元素
		onCheckedFrom : function(f) {
			var b = false;
			$("div[data-check-from='" + f + "']").each(function() {
				if ($(this).find('div').hasClass('checked')) {
					b = true;
					return false;
				}
			});
			if (b) {
				$(f).iCheck('check');
			} else {
				$(f).iCheck('uncheck');
			}
		},
		onCheckedAll : function(f) {
			var idMap = new Map();
			var checkMap = new Map();
			var groupArray = new Array();

			$(".i-checks").each(function() {
				var g = $(this).data("group");
				var f = $(this).data("check-from");

				if (groupArray.join().indexOf(g) == -1) {
					groupArray.push(g);
				}
				if (f == undefined || f == null) {
					idMap.put(g, $(this).attr("id"));
				} else {
					if (!$(this).find('div').hasClass('checked')) {
						checkMap.put(g, false);
					}
				}
			});

			$.each(groupArray, function(_key) {
				if (!checkMap.containsKey(groupArray[_key])) {
					// 全选
					$("#" + idMap.get(groupArray[_key])).iCheck('check');
				} else {
					// 查看结果
					if (checkMap.get(groupArray[_key])) {
						$("#" + idMap.get(groupArray[_key])).iCheck(
								'check');
					} else {
						$("#" + idMap.get(groupArray[_key])).iCheck(
								'uncheck');
					}
				}
			});
		}
	});
});