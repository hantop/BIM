/*
 * 车贷项目研发组，jQuery扩展插件  
 * 功能树菜单框架，点击在右侧区域打开标签
 * 
 */
$(function(){
	

jQuery.extend({
	// 计算元素的宽度和
    widthSum:function(l){
    	var k = 0;
    	$(l).each(function() {
    		k += $(this).outerWidth(true)
    	});
    	return k;
    },
    /*
     * 计算标签容器的偏移量并设置滑动动画
     */
	autoWidth:function(n){
		
		// 获取元素n之前的所有元素的宽度和
		var o = $.widthSum($(n).prevAll());
		// 获取元素n之后的所有元素的宽度和
		var q = $.widthSum($(n).nextAll());
		// 获取占用标签容器元素的宽度
		var l = $.widthSum($(".JM_C").children().not(".JM_M"));
		// 获取标签容器的可用宽度
		var k = $(".JM_C").outerWidth(true) - l;
		var d = 0;
		$(".JM_T").each(function() {
			d += $(this).outerWidth();
		});
		var p = 0;
		// 如果打开的标签宽度和大于标签容器的可用宽度，则计算偏移量
		if (d < k) {
			p = 0;
		} else {
			if (q <= (k - $(n).outerWidth(true) - $(n).next().outerWidth(true))) {
				if ((k - $(n).next().outerWidth(true)) > q) {
					p = o;
					var m = n;
					while ((p - $(m).outerWidth()) > (d - k)) {
						p -= $(m).prev().outerWidth();
						m = $(m).prev();
					}
				}
			} else {
				if (o > (k - $(n).outerWidth(true) - $(n).prev().outerWidth(true))) {
					p = o - $(n).prev().outerWidth(true)
				}
			}
		}
		$(".JM_P").animate({
			marginLeft : 0 - p + "px"
		}, "fast")
	},
	/*
	 * 定位当前获取焦点的tab标签，计算偏移量并设置移动动画 （向左）
	 */
	tabToLeft:function(){
		var o = Math.abs(parseInt($(".JM_P").css("margin-left")));
	    var l = $.widthSum($(".JM_C").children().not(".JM_M"));
	    var k = $(".JM_C").outerWidth(true) - l;
	    var p = 0;
	    if ($(".JM_P").width() >= k) {
	        var m = $(".JM_T:first");
	        var n = 0;
	        while ((n + $(m).outerWidth(true)) <= o) {
	        	n += $(m).outerWidth(true);
	        	m = $(m).next()
	        }
	        n = 0;
	        if ($.widthSum($(m).prevAll()) > k) {
	        	while ((n + $(m).outerWidth(true)) < (k) && m.length > 0) {
	        		n += $(m).outerWidth(true);
	        		m = $(m).prev()
	        	}
	        	p = $.widthSum($(m).prevAll())
	        }
	    } else {
	    	return false
	    }
	    $(".JM_P").animate({marginLeft: 0 - p + "px"},"fast")
	},
	/*
	 * 定位当前获取焦点的tab标签，计算偏移量并设置移动动画 （向右）
	 */
	tabToRight:function(){
		var o = Math.abs(parseInt($(".JM_P").css("margin-left")));
	    var l = $.widthSum($(".JM_C").children().not(".JM_M"));
	    var k = $(".JM_C").outerWidth(true) - l;
	    var p = 0;
	    if ($(".JM_P").width() >= k) {
	        var m = $(".JM_T:first");
	        var n = 0;
	        while ((n + $(m).outerWidth(true)) <= o) {
	        	n += $(m).outerWidth(true);
	        	m = $(m).next()
	        }
	        n = 0;
	        while ((n + $(m).outerWidth(true)) < (k) && m.length > 0) {
	        	n += $(m).outerWidth(true);
	        	m = $(m).next()
	        }
	        p = $.widthSum($(m).prevAll());
	        if (p > 0) {
	        	$(".JM_P").animate({marginLeft: 0 - p + "px"},"fast")
	        }
	    } else {
	    	return false
	    }
	},
	/*
	 * 打开折叠左侧功能树 
	 */
	sidebarExpanded:function(){
		var dd = $(this).children(".JM_I");
		if(($(this).children(".JM_I")).length == 0){
			if (!$(this).hasClass("active")) {
				$(this).addClass("active").siblings(".J_SIDEBAR>li").removeClass("active");
			}else{
				$(this).removeClass('active');
			}
		}else{
			$(".J_SIDEBAR>li").removeClass("active");
		}
	},
	/*
	 * 切换标签 
	 */
	cutTabPage:function(){
		var o = $(this).data("id");
		if (!$(this).hasClass("active")) {
			$(this).addClass("active").siblings(".JM_T").removeClass("active");
			$(".J_IFRAME").each(function() {
                if ($(this).data("id") == o) {
                    $(this).show().siblings(".J_IFRAME").hide();
                    return false;
                }
            });
		}
	},
	/*
	 * 打开标签页
	 */
	openTabPage:function(){
		var o = $(this).attr("href"),
		l = $.trim($(this).text()),
		t = $(this).data("index"),
		// 标记是否需要新建TAB标签
		k = true;
		if (o == undefined || $.trim(o).length == 0) {
	        return false
	    }
		$(".JM_T").each(function() {
			// 如果已打开的标签等于将要打开的链接
	        if ($(this).data("id") == o) {
	        	// 如果本元素不是被激活状态,则激活此标签
	            if (!$(this).hasClass("active")) {
	            	// 添加此元素为被激活状态，并将此元素外的其他元素移除激活状态
	                $(this).addClass("active").siblings(".JM_T").removeClass("active");
	                $.autoWidth(this);
	                // TODO 可以设置打开等待框
	                
	                // 定位page页，并设置激活
	                $(".J_IFRAME").each(function() {
	                    if ($(this).data("id") == o) {
	                        $(this).show().siblings(".J_IFRAME").hide();
	                        return false;
	                    }
	                });
	            }
	            k = false;
	            return false;
	        }
	    });
		
		// 添加标签并打开页面
		if(k){
			var p = '<a href="javascript:void(0);" class="JM_T active" data-id="' + o + '">' + l + ' <i class="fa fa-times-circle"></i></a>';
	        $(".JM_T").removeClass("active");
	        $(".JM_P").append(p);
	        var n = '<iframe id="iframe'+t+'" name="iframe'+t+'" class="J_IFRAME" width="100%" height="100%" src="' + o + '" frameborder="0" data-id="' + o + '" seamless></iframe>';
	        $(".J_IFRAME_MAIN").find("iframe.J_IFRAME").hide().parents(".J_IFRAME_MAIN").append(n);
	        $.autoWidth($(".JM_T.active"));
	        $(".JM_T").on("click",$.cutTabPage);
		}
		return false;
	},
	closeTabPage:function() {
		console.log("关闭");
	    var m = $(this).parents(".JM_T").data("id");
	    
	    var l = $(this).parents(".JM_T").width();
	    
	    if ($(this).parents(".JM_T").hasClass("active")) {
	    	
	        if ($(this).parents(".JM_T").next(".JM_T").length) {
	            var k = $(this).parents(".JM_T").next(".JM_T:eq(0)").data("id");
	            $(this).parents(".JM_T").next(".JM_T:eq(0)").addClass("active");
	            $(".J_IFRAME").each(function() {
	                if ($(this).data("id") == k) {
	                    $(this).show().siblings(".J_IFRAME").hide();
	                    return false
	                }
	            });
	            var n = parseInt($(".JM_P").css("margin-left"));
	            if (n < 0) {
	                $(".JM_P").animate({marginLeft: (n + l) + "px"},"fast")
	            }
	            $(this).parents(".JM_T").remove();
	            $(".J_IFRAME").each(function() {
	                if ($(this).data("id") == m) {
	                    $(this).remove();
	                    return false
	                }
	            })
	        }
	        if ($(this).parents(".JM_T").prev(".JM_T").length) {
	            var k = $(this).parents(".JM_T").prev(".JM_T:last").data("id");
	            $(this).parents(".JM_T").prev(".JM_T:last").addClass("active");
	            $(".J_IFRAME").each(function() {
	                if ($(this).data("id") == k) {
	                    $(this).show().siblings(".J_IFRAME").hide();
	                    return false
	                }
	            });
	            $(this).parents(".JM_T").remove();
	            $(".J_IFRAME").each(function() {
	                if ($(this).data("id") == m) {
	                    $(this).remove();
	                    return false
	                }
	            })
	        }
	    } else {
	        $(this).parents(".JM_T").remove();
	        $(".J_IFRAME").each(function() {
	            if ($(this).data("id") == m) {
	                $(this).remove();
	                return false
	            }
	        });
	        $.autoWidth($(".JM_T.active"));
	    }
	    return false
	},
	openThisPage:function(){
		var o = $(this).attr("href");
		// 循环遍历功能菜单
		$(".JM_I").each(function() {
			// 找到与标签同样的功能菜单，并打开菜单
			if($(this).attr("href") == o){
				var l = $.trim($(this).text()),
				t = $(this).data("index"),
				k = true;
				if (o == undefined || $.trim(o).length == 0) {
		            return false
		        }
				$(".JM_T").each(function() {
					// 如果已打开的标签等于将要打开的链接
			        if ($(this).data("id") == o) {
			        	// 如果本元素不是被激活状态,则激活此标签
			            if (!$(this).hasClass("active")) {
			            	// 添加此元素为被激活状态，并将此元素外的其他元素移除激活状态
			                $(this).addClass("active").siblings(".JM_T").removeClass("active");
			                $.autoWidth(this);
			                // TODO 可以设置打开等待框
			                // 定位page页，并设置激活
			                $(".J_IFRAME").each(function() {
			                    if ($(this).data("id") == o) {
			                    	// 如果元素已经为打开状态，则刷新页面
			                    	$(this).attr('src', $(this).attr('src'));
			                        $(this).show().siblings(".J_IFRAME").hide();
			                        return false;
			                    }
			                });
			            }
			            k = false;
			            return false;
			        }
			    });
				if(k){
					var p = '<a href="javascript:void(0);" class="JM_T active" data-id="' + o + '">' + l + ' <i class="fa fa-times-circle"></i></a>';
			        $(".JM_T").removeClass("active");
			        $(".JM_P").append(p);
			        var n = '<iframe id="iframe'+t+'" name="iframe'+t+'" class="J_IFRAME" width="100%" height="100%" src="' + o + '" frameborder="0" data-id="' + o + '" seamless></iframe>';
			        $(".J_IFRAME_MAIN").find("iframe.J_IFRAME").hide().parents(".J_IFRAME_MAIN").append(n);
			        $.autoWidth($(".JM_T.active"));
			        $(".JM_T").on("click",$.cutTabPage);
				}
				return false;
			}
		});
		return false;
	}
});
$(".JM_T").on("click",$.cutTabPage);
$(".JM_I").on("click",$.openTabPage);
$(".JT_L").on("click", $.tabToLeft);
$(".JT_R").on("click",$.tabToRight);
$(".JM_M").on("click", ".JM_T i", $.closeTabPage);
(function($){
	// 设置滚动条
	$(".sidebar-page").slimScroll({
		height: "100%",
		railOpacity: .9,
		alwaysVisible: !1,
		color: '#4f6986',
		railColor:'#5d7b97'
	});
	$(".J_IFRAME_MAIN").slimScroll({
		height: "100%",
		railOpacity: .9,
		alwaysVisible: !1,
		color: '#4f6986',
		railColor:'#5d7b97'
	});
});

})

