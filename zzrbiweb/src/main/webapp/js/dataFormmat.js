// 保留两位小数
function formatterBigDecimal(data){
    var num="";
    if (data != undefined && data != "undefined" && data.length != 0 && data != null  ) {
        var dataStr = data+"";
        var split = dataStr.split(".");
        if(split.length ==1){
            num = dataStr +".00";
        }else{
            var split2 = split[1];
            if(split2.length == 1){
                num = dataStr +"0";
            }else{
                num = split[0] + "."+split[1].toString().substring(0,2);
            }
        }
    }
    return num;
}

// 千分位分隔符
function formatterCount(amount) {
    //console.log(amount);
    amount = amount+"".replace(new RegExp(",", "gm"), "");
    var amountStrs = amount.split(".");
    var str = amountStrs[0];
    var newStr = "";
    var count = 0;
    for (var i = str.length - 1; i >= 0; i--) {
        if (count % 3 == 0 && count != 0) {
            newStr = str.charAt(i) + "," + newStr;
        } else {
            newStr = str.charAt(i) + newStr;
        }
        count++;
    }

    if(amountStrs.length >= 2){
        return newStr+"."+(((amountStrs[1]+"").substring(0,2)));
    }

    return newStr;
}