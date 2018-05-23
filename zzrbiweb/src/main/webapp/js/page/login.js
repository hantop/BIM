$(function(){
	
	 $(".tg_code").click(function(){
	        $(this).attr("src","getCode.shtml?v="+Math.random())
	    })
	 $('input').bind('keypress', function (e) {
        if(e.keyCode=='13'){
            $('.go_login').click();
        }
	 })
    $(".go_login").on("click",function(){
        var username = $('.username').val().trim();
        var password = $('#password').val().trim();
        var vecode = $("#input_code").val().trim();
        if(!checkInput($('.username'),'请输入用户名')){
            return;
        }
        if(!checkInput($("#password"),'请输入密码')){
            return;
        }
        if(!checkInput($("#input_code"),'请输入图片验证码')){
            return;
        }       
        $.ajax({
            type : "POST",
            url :  "login.shtml",
            data : {
                "userName" : username,
                "password" : $.md5(password),
                "verCode" : vecode
            },
            success : function(data) {
            	console.log(data);
                if (data.code == 1) {
                   infoTips(data.message);
                   return;
                }else if (data.code == 2) {
                    infoTips(data.message);
                    return;
                } else if(data.code == 3) {
                    infoTips(data.message);
                    return;
                }else if(data.code == 99){
                	infoTips(data.message);
                	return;
                }else if(data.code == 0){
                	window.location.href = 'index.shtml';
                }
            },
            error : function (){
            	infoTips('系统异常，请稍后重试');
            }
        });
    });

    //验证输入框是否为空
    function checkInput(obj,str){
        var msg = obj.val().trim();
        if(msg==''||msg==null){
            infoTips(str);
            return false;
        }
        return true;
    }
    function infoTips(msg){	
    	$('.info').show().text(msg);
    	setTimeout(function  () {
            $(".info").hide();
        }, 1500)
    }
})
