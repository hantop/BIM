package com.zzrbi.service;


import java.util.List;
import java.util.Map;




/**
 * Desc: 按人统计接口
 * User: LIHUI
 * Date: 2018-01-25
 * Time: 18:01
 */
public interface IPersonService {


	/**
	 *  @Description 获取会员累计统计
	 *  @Author lihui
	 *  @Date 2018/1/25 12:01
	 * @param
	 * @Return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryCumulativePerson()throws Exception;




	/**
	 *  @Description 分页获取 用户的信息 按天统计
	 *  @Author lihui
	 *  @Date 2018/2/6 10:01
	 * @param
	 * @Return Map<String,Object>
	 */
	public Map<String,Object> queryCumulativePersonByDay(String startTime,String endTime,int pageSize,int startIndex)throws Exception;





	/**
	 *  @Description 分页获取 用户的信息 按周统计
	 *  @Author lihui
	 *  @Date 2018/2/6 10:30
	 * @param
	 * @Return Map<String,Object>
	 */
	public Map<String,Object> queryCumulativePersonByWeek(String startTime,String endTime,int pageSize,int startIndex)throws Exception;




	/**
	 *  @Description 分页获取 用户的信息 按月统计
	 *  @Author lihui
	 *  @Date 2018/2/6 11:30
	 * @param
	 * @Return Map<String,Object>
	 */
	public Map<String,Object> queryCumulativePersonByMonth(String startTime,String endTime,int pageSize,int startIndex)throws Exception;


	
}
