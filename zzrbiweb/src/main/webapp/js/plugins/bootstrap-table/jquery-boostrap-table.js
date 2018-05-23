(function($) {
	$.fn.jqGrid = function(options) {
		var defaults = {
            method: 'post',
			contentType: "application/x-www-form-urlencoded",
			striped: true, // 是否显示行间色
			pagination: true, // 是否显示分页
			paginationLoop:true,
			onlyInfoPagination:true,
            singleSelect: false,
            showExport: true,
			exportDataType: "basic",//导出类型  
			pageSize : 15, // 每页显示条数
			pageNumber:1,  //当前第几页 
			pageList: [15, 25, 50, 100], 
            sidePagination: "server", // 请求资源
            minimunCountColumns: 2,
            paginationPreText:'上一页',
            paginationNextText:'下一页',
            paginationFirstText:'首页',
            paginationLastText:'末页'
		}
		
		var opts = $.extend(defaults,options);
		
		function queryParams(params) {
			var data = opts.data;
			data.pageSize =  params.limit;
			data.startIndex =  params.offset;
			data.sortOrder = params.order;
			var pageArr = {
	             pageSize: params.limit,
	             startIndex: params.offset,
	             sortOrder: params.order,
		    };
	        return data;
	    }
		
		$(this).bootstrapTable('destroy').bootstrapTable({
			url:opts.url,
            method: opts.method,
			contentType: opts.contentType,
			queryParams: queryParams,
			height :opts.height,
			showExport:opts.showExport,
			exportDataType: opts.exportDataType,
			striped: opts.striped, // 是否显示行间色
			pagination: opts.pagination, // 是否显示分页
            singleSelect: opts.singleSelect,
			pageSize :opts.pageSize, // 每页显示条数
			pageNumber:opts.pageNumber,  //当前第几页 
            sidePagination: opts.sidePagination, // 请求资源
            minimunCountColumns: opts.minimunCountColumns,
            columns:opts.columns,
            paginationPreText:opts.paginationPreText,
            paginationNextText:opts.paginationNextText,
            paginationFirstText:opts.paginationFirstText,
            paginationLastText:opts.paginationLastText,
            
		});
		
		$(window).bind("load resize",function() {
			$(this).bootstrapTable('resetWidth');
		});
		
		$(".fixed-table-pagination .pagination").css('display','block');
	};
})(jQuery);