package com.zzrbi.service;


import java.util.List;
import java.util.Map;




/**
 * Desc: 按钱统计接口
 * User: LIHUI
 * Date: 2018-01-25
 * Time: 18:01
 */
public interface IStatisticsBalanceService {


	/**
	 *  @Description 获取钱累计统计
	 *  @Author lihui
	 *  @Date 2018/1/25 12:01
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryCumulativeMoney()throws Exception;

	public List<Map<String, Object>> queryCumulativeMoney2()throws Exception;


	/**
	 *  @Description 获取钱累计统计 按天
	 *  @Author zhaobiao
	 *  @Date 2018/2/1 15:01
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryCumulativeMoneyByDay(String startTime,String endTime)throws Exception;


	/**
	 *  @Description 获取钱累计统计 按周
	 *  @Author zhaobiao
	 *  @Date 2018/2/1 15:31
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryCumulativeMoneyByWeek(String startTime,String endTime)throws Exception;



	/**
	 *  @Description 获取钱累计统计 按月
	 *  @Author zhaobiao
	 *  @Date 2018/2/1 16:31
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryCumulativeMoneyByMonth(String startTime,String endTime)throws Exception;



	/**
	 *  @Description 获取钱累计统计 按月
	 *  @Author zhaobiao
	 *  @Date 2018/2/2 16:31
	 * @param
	 * @Return Map<String,Object>
	 */
	public Map<String,Object> queryCumulativeMoneyByDayFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception;




	/**
	 *  @Description 获取钱累计统计 按周
	 *  @Author zhaobiao
	 *  @Date 2018/2/5 14:31
	 * @param
	 * @Return Map<String,Object>
	 */
	public Map<String,Object> queryCumulativeMoneyByWeekFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception;


	/**
	 *  @Description 获取钱累计统计 按月
	 *  @Author zhaobiao
	 *  @Date 2018/2/5 14:31
	 * @param
	 * @Return Map<String,Object>
	 */
	public Map<String,Object> queryCumulativeMoneyByMonthFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception;




	/**
	 *  @Description 按天获取资金折线图
	 *  @Author zhaobiao
	 *  @Date 2018/2/6 13:41
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryMoneyStreamByDay(String startTime,String endTime)throws Exception;



	/**
	 *  @Description 按周获取资金折线图
	 *  @Author zhaobiao
	 *  @Date 2018/2/6 14:01
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryMoneyStreamByWeek(String startTime,String endTime)throws Exception;



	/**
	 *  @Description 按周获取资金折线图
	 *  @Author zhaobiao
	 *  @Date 2018/2/6 14:01
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryMoneyStreamByMoney(String startTime,String endTime)throws Exception;



	/**
	 *  @Description 按天分页获取资金列表数据
	 *  @Author zhaobiao
	 *  @Date 2018/2/7 10:06
	 * @param
	 * @Return Map<String,Object>
	 */
	public Map<String,Object> queryMoneyStreamByDayFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception;



	/**
	 *  @Description 按周分页获取资金列表数据
	 *  @Author zhaobiao
	 *  @Date 2018/2/7 10:16
	 * @param
	 * @Return Map<String,Object>
	 */
	public Map<String,Object> queryMoneyStreamByWeekFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception;



	/**
	 *  @Description 按周分页获取资金列表数据
	 *  @Author zhaobiao
	 *  @Date 2018/2/7 10:26
	 * @param
	 * @Return Map<String,Object>
	 */
	public Map<String,Object> queryMoneyStreamByMoneyFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception;

}
