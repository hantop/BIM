package com.zzrbi.service.Impl;

import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.service.IEveryTimeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Desc: 按时统计实现类
 * User: LIHUI
 * Date: 2018-01-25
 * Time: 18:01
 */
@Service
public class EveryTimeServiceImpl extends BaseDaoImpl implements IEveryTimeService {

	private Logger logger = Logger.getLogger(EveryTimeServiceImpl.class);


	@Override
	public List<Map<String, Object>> queryPersonListByDateTime(String dateTime)throws Exception {
		logger.info("根据时间获取某天统计");

//		String sql = "SELECT IFNULL((SUM(registerCount)), 0) AS registerCount, IFNULL((SUM(investCount)), 0) AS investCount, IFNULL((SUM(firstInvestCount)), 0) AS firstInvestCount, IFNULL((SUM(availableBalance)), 0) AS availableBalance, IFNULL((SUM(investMoney)), 0) AS investMoney, IFNULL((SUM(recharge - cash)), 0) AS inflow FROM every_time WHERE DATE_FORMAT(DATETIME, '%Y-%m-%d') = '"+dateTime+"'";
        String sql = "SELECT IFNULL((SUM(registerCount)), 0) AS registerCount, IFNULL((SUM(investCount)), 0) AS investCount, IFNULL((SUM(firstInvestCount)), 0) AS firstInvestCount, IFNULL(tab.availableBalance, 0) AS availableBalance, IFNULL((SUM(investMoney)), 0) AS investMoney, IFNULL((SUM(recharge - cash)), 0) AS inflow FROM every_time LEFT JOIN (SELECT stand,availableBalance,DATE_FORMAT(DATETIME, '%Y-%m-%d') AS dtm FROM every_time WHERE DATE_FORMAT(DATETIME, '%Y-%m-%d') =  '"+dateTime+"' ORDER BY id DESC LIMIT 0,1) AS tab ON tab.dtm = DATE_FORMAT(DATETIME, '%Y-%m-%d') WHERE DATE_FORMAT(DATETIME, '%Y-%m-%d') = '"+dateTime+"'";
		return  jdbcTemplate.queryForList(sql);
	}
	@Override
	public List<Map<String, Object>> queryMoneyListByDateTime(String dateTime)throws Exception {
		logger.info("根据时间获取某天金额统计");

//		String sql = "SELECT SUM(recharge) AS recharge,SUM(availableBalance) AS availableBalance,SUM(investMoney) AS investMoney,stand,SUM(investPhase) AS investPhase,SUM(cash) AS cash,SUM(recharge - cash) AS inflow FROM every_time WHERE DATE_FORMAT(DATETIME, '%Y-%m-%d') =  '"+dateTime+"' ORDER BY id DESC ";
		String sql = "SELECT IFNULL((SUM(recharge)),0) AS recharge,IFNULL(tab.availableBalance,0) AS availableBalance,IFNULL((SUM(investMoney)),0) AS investMoney,IFNULL((tab.stand),0.00)stand,IFNULL(investPhase,0) AS investPhase,IFNULL((SUM(cash)),0) AS cash,IFNULL((SUM(recharge - cash)),0) AS inflow FROM every_time LEFT JOIN (SELECT stand,availableBalance,DATE_FORMAT(DATETIME, '%Y-%m-%d') AS dtm FROM every_time WHERE DATE_FORMAT(DATETIME, '%Y-%m-%d') =  '"+dateTime+"' ORDER BY id DESC LIMIT 0,1) AS tab ON tab.dtm = DATE_FORMAT(DATETIME, '%Y-%m-%d') WHERE DATE_FORMAT(DATETIME, '%Y-%m-%d') = '"+dateTime+"' ";
		return  jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String,Object>> queryUserSumByTime(String time) throws  Exception{

		logger.info("进去 方法 queryUserSumByTime 按时间统计"+time+"一天当中的所有数据");

		StringBuffer sql = new StringBuffer("SELECT DATE_FORMAT(dateTime, '%Y-%m-%d %H:%i:%s') as dateTime,registerCount,investCount,firstInvestCount,availableBalance,investMoney,(recharge - cash) as jlMoney FROM every_time  WHERE DATE_FORMAT(dateTime, '%Y-%m-%d') = '"+time+"' ORDER BY dateTime ");

		return  jdbcTemplate.queryForList(sql.toString());

	}

	@Override
	public List<Map<String, Object>> queryUserSumByDays(String startTime, String endTime) throws Exception {

		logger.info("进去 方法 queryUserSumByDays 统计 从 "+startTime+" 到 "+endTime+" 的所有数据");

		StringBuffer sql = new StringBuffer("SELECT DATE_FORMAT(dateTime, '%Y/%m/%d') as dateTime, registerCount, investPerson as investCount, ( todayRegisterInvest + beforRegisterInvest ) AS firstInvestCount FROM person WHERE DATE_FORMAT(dateTime, '%Y-%m-%d') >= '"+startTime+"' AND DATE_FORMAT(dateTime, '%Y-%m-%d') <= '"+endTime+"' ORDER BY dateTime");

		return jdbcTemplate.queryForList(sql.toString());
	}


	@Override
	public List<Map<String,Object>> queryUserSumByWeek(String startTime,String endTime)throws Exception{

		logger.info("进去 方法 queryUserSumByWeek 统计 从"+startTime+" 到 "+endTime+" 的所有数据");

		StringBuffer sql = new StringBuffer("SELECT dateTime,SUM(registerCount) AS registerCount, SUM(investCount) AS investCount, SUM(firstInvestCount) AS firstInvestCount FROM ( SELECT registerCount, investPerson AS investCount, ( todayRegisterInvest + beforRegisterInvest ) AS firstInvestCount, DATE_FORMAT(dateTime, '%Y-%m-%d') AS dateTime, weekCycle FROM person ) p WHERE p.dateTime >= '"+startTime+"' AND p.dateTime <= '"+endTime+"' GROUP BY DATE_FORMAT(dateTime, '%Y'),p.weekCycle");

		return jdbcTemplate.queryForList(sql.toString());

	}


	@Override
	public List<Map<String,Object>> queryUserSumByMonth(String startTime,String endTime)throws Exception{

		logger.info("进去 方法 queryUserSumByMonth 统计  从第"+startTime+"月 到 第"+endTime+"月 的所有数据");

		StringBuffer sql = new StringBuffer("SELECT dateTime, sum(d.registerCount) AS registerCount, SUM(d.investPerson) AS investCount, SUM(d.firstInvestCount) AS firstInvestCount FROM ( SELECT DATE_FORMAT(t.dateTime, '%Y-%m') AS dateTime, t.registerCount, t.investPerson, ( todayRegisterInvest + beforRegisterInvest ) AS firstInvestCount FROM person t ) d WHERE d.dateTime >= '"+startTime+"' AND d.dateTime <= '"+endTime+"' GROUP BY d.dateTime ORDER BY d.dateTime");

		return jdbcTemplate.queryForList(sql.toString());

	}


	@Override
	public Map<String,Object> queryUserSumByTimeFy(String time,Integer pageSize,Integer startIndex) throws  Exception{

		logger.info("进去 方法 queryUserSumByTimeFy 按时间分页统计"+time+"一天当中的所有数据");

		StringBuffer sql = new StringBuffer("SELECT DATE_FORMAT(dateTime, '%Y-%m-%d %H:%i:%s') as dateTime,registerCount,investCount,firstInvestCount,availableBalance,investMoney,(recharge - cash) as jlMoney FROM every_time  WHERE DATE_FORMAT(dateTime, '%Y-%m-%d') = '"+time+"' ORDER BY dateTime desc ");

		return queryForListPage(sql.toString(),pageSize,startIndex);
	}






}
