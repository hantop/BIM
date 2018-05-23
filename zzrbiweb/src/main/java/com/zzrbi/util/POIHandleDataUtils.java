package com.zzrbi.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;

/**
 * Desc：
 * User：ZhaoBiao
 * Date：2018-02-27
 * Time：13:49
 */
public class POIHandleDataUtils {

    private static Logger logger = Logger.getLogger(POIHandleDataUtils.class);

    /**
     *
     * @description 获取excel 列标行信息
     * @param key excel列标行 列字段 key
     * @param value excel列标行 列字段 value
     * @return LinkedHashMap<String,Object>    返回的是LinkedHashMap<String, Object>
     */
    public static LinkedHashMap<String, Object> getExcelColumnTitleData(String[] key, String[] value){
        logger.info("getExcelTitleData方法获取excel列标行信息 start：----------->>>>>>>>>>>>>>>>>>>");
        LinkedHashMap<String, Object> titleMap = new LinkedHashMap<String, Object>();
        if(ArrayUtils.isNotEmpty(key) && ArrayUtils.isNotEmpty(value) && (key.length == value.length)){
            for(int i= 0; i < key.length;i++){
                titleMap.put(key[i], value[i]);
            }
            logger.info("getExcelTitleData方法获取excel 列标行信息成功！");
        }else{
            logger.info("getExcelTitleData方法获取excel 列标行信息失败！");
        }
        return titleMap;
    }

    /**
     *
     * @description  格式化输出金额   ###,###.## 保留小数点后两位添加千位符
     * @param obj 需要格式化的money
     * @return  String    返回类型
     */
    public static String formatMoney(BigDecimal obj){
        //格式化输出数据
        DecimalFormat dataFormat=new DecimalFormat("###,##0.00");
        return dataFormat.format(obj);
    }
}
