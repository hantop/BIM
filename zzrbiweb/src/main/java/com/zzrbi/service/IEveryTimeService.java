package com.zzrbi.service;


import java.util.List;
import java.util.Map;




/**
 * Desc: 按时统计接口
 * User: LIHUI
 * Date: 2018-01-25
 * Time: 18:01
 */
public interface IEveryTimeService {


	/**
	 *  @Description 根据时间获取会员统计
	 *  @Author lihui
	 *  @Date 2018/1/25 12:01
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	List<Map<String, Object>> queryPersonListByDateTime(String dateTime)throws Exception;

	List<Map<String, Object>> queryMoneyListByDateTime(String dateTime)throws Exception;
	/**
	 *  @Description 根据时间获取所有的数据 按时间
	 *  @Author zhaobiao
	 *  @Date 2018/1/30 12:01
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	List<Map<String,Object>> queryUserSumByTime(String time) throws  Exception;


	/**
	 *  @Description 根据时间获取用户的数据 按天
	 *  @Author zhaobiao
	 *  @Date 2018/1/30 14:01
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	List<Map<String,Object>> queryUserSumByDays(String startTime,String endTime)throws  Exception;


	/**
	 *  @Description 根据时间获取用户的数据 按周
	 *  @Author zhaobiao
	 *  @Date 2018/1/30 14:41
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	List<Map<String,Object>> queryUserSumByWeek(String startTime,String endTime)throws Exception;



	/**
	 *  @Description 根据时间获取用户的数据 按月
	 *  @Author zhaobiao
	 *  @Date 2018/1/30 15:00
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	List<Map<String,Object>> queryUserSumByMonth(String startTime,String endTime)throws Exception;



	/**
	 *  @Description 根据时间获取所有的数据 按时间
	 *  @Author zhaobiao
	 *  @Date 2018/1/30 12:01
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	Map<String,Object> queryUserSumByTimeFy(String time,Integer pageSize,Integer startIndex) throws  Exception;



	
}
