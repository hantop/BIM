package com.zzrbi.service.Impl;

import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.service.IPersonService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Desc: 按人统计实现类
 * User: LIHUI
 * Date: 2018-01-25
 * Time: 18:01
 */
@Service
public class PersonServiceImpl extends BaseDaoImpl implements IPersonService {

	private Logger logger = Logger.getLogger(PersonServiceImpl.class);
	@Override
	public List<Map<String, Object>> queryCumulativePerson()throws Exception {
		logger.info("获取会员累计统计");
		String sql = "SELECT SUM(p.registerCount) AS registerCount,SUM(p.investPerson) AS investPerson,SUM(p.todayRegisterInvest + p.beforRegisterInvest) AS firstInvestCount FROM person p ";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public Map<String,Object> queryCumulativePersonByDay(String startTime,String endTime,int pageSize,int startIndex)throws Exception{

		logger.info("进入 方法 queryCumulativePersonByDay 统计 用户所有的数据  从"+startTime+" 到 "+endTime+" 天的  所有数据");
		String sql = "SELECT DATE_FORMAT(p.dateTime, '%Y-%m-%d') AS dateTime, p.registerCount, p.todayRegisterAccount, p.beforRegisterAccount, p.investPerson, p.todayRegisterInvest, p.beforRegisterInvest, p.repeatInvest, p.investCount FROM person p WHERE DATE_FORMAT(p.dateTime, '%Y-%m-%d') >= '"+startTime+"' AND DATE_FORMAT(p.dateTime, '%Y-%m-%d') <= '"+endTime+"' ORDER BY p.dateTime DESC";
		return queryForListPage(sql.toString(),pageSize,startIndex);

	}


	@Override
	public Map<String,Object> queryCumulativePersonByWeek(String startTime,String endTime,int pageSize,int startIndex)throws Exception{

		logger.info("进入 方法 queryCumulativePersonByWeek 统计 用户所有的数据  从"+startTime+" 到 "+endTime+" 周的  所有数据");
		String sql = "SELECT p.dateTime, SUM(p.registerCount) AS registerCount, SUM(p.todayRegisterAccount) AS todayRegisterAccount, SUM(p.beforRegisterAccount) AS beforRegisterAccount, SUM(p.investPerson) AS investPerson, SUM(p.todayRegisterInvest) AS todayRegisterInvest, SUM(p.beforRegisterInvest) AS beforRegisterInvest, SUM(p.repeatInvest) AS repeatInvest, SUM(p.investCount) AS investCount, p.weekCycle " +
				"FROM ( SELECT DATE_FORMAT(dateTime, '%Y-%m-%d') AS dateTime, registerCount, todayRegisterAccount, beforRegisterAccount, investPerson, todayRegisterInvest, beforRegisterInvest, repeatInvest, investCount, weekCycle FROM person ) p WHERE p.dateTime >= '"+startTime+"' AND p.dateTime <= '"+endTime+"' GROUP BY DATE_FORMAT(dateTime, '%Y'), p.weekCycle ORDER BY p.dateTime DESC";
		return queryForListPage(sql.toString(),pageSize,startIndex);

	}


	@Override
	public Map<String,Object> queryCumulativePersonByMonth(String startTime,String endTime,int pageSize,int startIndex)throws Exception{


		logger.info("进入 方法 queryCumulativePersonByMonth 统计 用户所有的数据  从"+startTime+" 到 "+endTime+" 月的  所有数据");
		String sql = "SELECT d.dateTime, SUM(d.registerCount) AS registerCount, SUM(d.todayRegisterAccount) AS todayRegisterAccount, SUM(d.beforRegisterAccount) AS beforRegisterAccount, SUM(d.investPerson) AS investPerson, SUM(d.todayRegisterInvest) AS todayRegisterInvest, SUM(d.beforRegisterInvest) AS beforRegisterInvest, SUM(d.repeatInvest) AS repeatInvest, SUM(d.investCount) AS investCount " +
				"FROM ( SELECT DATE_FORMAT(t.dateTime, '%Y-%m') AS dateTime, t.registerCount, t.todayRegisterAccount, t.beforRegisterAccount, t.investPerson, t.todayRegisterInvest, t.beforRegisterInvest, t.repeatInvest, t.investCount FROM person t ) d WHERE d.dateTime >= '"+startTime+"' AND d.dateTime <= '"+endTime+"' GROUP BY d.dateTime ORDER BY d.dateTime desc";
		return queryForListPage(sql.toString(),pageSize,startIndex);

	}

}
