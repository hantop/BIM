package com.zzrbi.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 返回信息
 * @author zx
 *
 */
public class ReturnMapUtil {
	
	public static final String module = ReturnMapUtil.class.getName();
	
	private static Map<String,Object> returnMap = new HashMap<String,Object>();
	
	private static String success_type = "S";
	
	private static String error_type = "E";
	
	private static Locale locale = Locale.SIMPLIFIED_CHINESE;
	
	private static Logger logger = Logger.getLogger(module);
	
	/**
	 * 获取成功Map
	 * @param code code
	 * @return
	 */
	public static Map<String,Object> getSuccessMap(){
		return returnMessage(null,null,success_type,locale);
	}
	
	/**
	 * 获取成功Map
	 * @return
	 */
	public static Map<String,Object> getSuccessMap(String code){
		
		if(UtilValidate.isEmpty(code)){
			return returnMessage(null,null,success_type,locale);
		}else{
			return returnMessage(code,null,success_type,locale);
		}
	}
	/**
	 * 获取成功Map
	 * @param code code
	 * @param message 消息
	 * @return
	 */

	public static Map<String,Object> getSuccessMap(String code,String message){
		if(UtilValidate.isEmpty(code)){
			return getSuccessMap();
		}else{
			if(UtilValidate.isEmpty(message)){
				return getSuccessMap(code);
			}else{
				return returnMessage(code,message,success_type,locale);
			}
		}
	}
	
	/**
	 * 获取失败Map
	 * @param code
	 * @return
	 */
	public static Map<String,Object> getErrorMap(String code){
		if(UtilValidate.isEmpty(code)){
			logger.warning("参数{code}必输");
		}
		return returnMessage(code,null,error_type,locale);
	}
	/**
	 * 获取失败Map
	 * @param code
	 * @param message
	 * @return
	 */
	public static Map<String,Object> getErrorMap(String code,String message){
		if(UtilValidate.isEmpty(message)){
			return getErrorMap(code);
		}else{
			return returnMessage(code,message,error_type,locale);
		}
	}
	
	/**
	 * 判断返回结果成功还是失败
	 * @param returnMap
	 * @return
	 */
	public static boolean isErrorReturnMap(Map<String,Object> returnMap){
		if(UtilValidate.areEqual(returnMap.get("type"), success_type)){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 判断返回结果成功还是失败
	 * @param returnMap
	 * @return
	 */
	public static boolean isSuccessReturnMap(Map<String,Object> returnMap){
		if(UtilValidate.areEqual(returnMap.get("type"), success_type)){
			return true;
		}else{
			return false;
		}
	}
	
	private static Map<String,Object> returnMessage(String code,String message,String type,Locale local){
		returnMap = new HashMap<String, Object>();
		// 缺少文件解析
		returnMap.put("code", code);
		returnMap.put("message", message);
		returnMap.put("type", type);
		
		return returnMap;
	}
}
