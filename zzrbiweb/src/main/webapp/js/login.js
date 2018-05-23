$(function(){
	 window.location.hash="no-back";
     window.location.hash="Again-No-back-button";//again because google chrome don't insert first hash into history
     window.onhashchange=function(){window.location.hash="no-back";}
	
     $(".tg_code").click(function(){
	        $(".tg_code").attr("src","getCode.shtml?v="+Math.random());
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
        if(!checkInput('.username','请输入用户名')){
            return;
        }
        if(!checkInput("#password",'请输入密码')){
            return;
        }
        if(!checkInput("#input_code",'请输入令牌')){
            return;
        }
        // if(!checkInput("#input_code",'请输入图片验证码')){
        //     return;
        // }
        $.ajax({
            type : "POST",
            url :  "login.shtml",
            data : {
                "userName" : username,
                "password" : $.md5(password),
                "verCode" : vecode
            },
            success : function(data) {
                if (data.code == 1) {
                   infoTips("#input_code",data.message);
                   $(".tg_code").attr("src","getCode.shtml?v="+Math.random());
                   return;
                }else if (data.code == 2) {
                	infoTips(".username",data.message);
                	infoTips("#password",data.message);
                	$(".tg_code").attr("src","getCode.shtml?v="+Math.random());
                    return;
                } else if(data.code == 3) {
                	infoTips(".username",data.message);
                	$(".tg_code").attr("src","getCode.shtml?v="+Math.random());
                    return;
                } else if (data.code == 4) {
                    infoTips("#input_code", data.message);
                    $(".tg_code").attr("src", "getCode.shtml?v=" + Math.random());
                    return;
                }else if(data.code == 99){
                	fairAlert.error({"msg":data.message});
                	$(".tg_code").attr("src","getCode.shtml?v="+Math.random());
                	return;
                }else if(data.code == 0){
                	window.location.href = 'index.shtml';
                }
            },
            error : function (){
            	fairAlert.error({"msg":"系统异常，请稍后重试!"});
            }
        });
    });

    //验证输入框是否为空
    function checkInput(obj,tipmsg){
        var msg = $(obj).val().trim();
        if(msg==''||msg==null){
            infoTips(obj,tipmsg);
            return false;
        }
        return true;
    }
    function infoTips(el,msg){	
    	$(el).siblings('.error').show().children('span').text(msg);
    	setTimeout(function  () {
    		$(el).siblings('.error').hide();
        }, 3000)
    }


})
