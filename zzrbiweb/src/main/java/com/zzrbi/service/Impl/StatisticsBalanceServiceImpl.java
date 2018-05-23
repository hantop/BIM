package com.zzrbi.service.Impl;

import java.util.List;
import java.util.Map;

import com.zzrbi.service.IStatisticsBalanceService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.zzrbi.dao.BaseDaoImpl;

/**
 * Desc: 按钱统计实现类
 * User: LIHUI
 * Date: 2018-01-25
 * Time: 18:01
 */
@Service
public class StatisticsBalanceServiceImpl extends BaseDaoImpl implements IStatisticsBalanceService {

	private Logger logger = Logger.getLogger(StatisticsBalanceServiceImpl.class);


	@Override
	public List<Map<String, Object>> queryCumulativeMoney()throws Exception {
		logger.info("获取钱累计统计");

		String sql = "SELECT sb.availableBalance,SUM(sb.invest) AS invest,SUM(sb.recharge - sb.cash) AS inflow FROM statistics_balance sb ORDER BY sb.creatTime DESC";

		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> queryCumulativeMoney2()throws Exception {
		logger.info("获取钱累计统计");

//		String sql = "SELECT SUM(sb.recharge) AS recharge,SUM(sb.availableBalance) AS availableBalance,SUM(invest) AS invest,sb.stand,SUM(sb.investPhase) as investPhase,SUM(sb.cash) as cash,SUM(sb.recharge - sb.cash) AS inflow FROM statistics_balance sb ORDER BY sb.creatTime DESC";
		String sql = "SELECT SUM(sb.recharge) AS recharge,sb.availableBalance,SUM(invest) AS invest,sb.stand,SUM(sb.investPhase) AS investPhase,SUM(sb.cash) AS cash,SUM(sb.recharge - sb.cash) AS inflow FROM (SELECT * FROM statistics_balance ORDER BY id DESC) AS sb ";
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String,Object>> queryCumulativeMoneyByDay(String startTime,String endTime)throws Exception{

		logger.info("进入 方法queryCumulativeMoneyByDay 统计 "+startTime+" 到 "+endTime+"  的钱累计统计");

		String sql = "SELECT DATE_FORMAT(dateTime, '%Y-%m-%d') as dateTime, availableBalance,invest AS investMoney,(recharge - cash) as jlMoney FROM statistics_balance  where DATE_FORMAT(dateTime, '%Y-%m-%d') >= '"+startTime+"' and DATE_FORMAT(dateTime, '%Y-%m-%d') <= '"+endTime+"' ORDER BY dateTime";

		return jdbcTemplate.queryForList(sql);

	}

	@Override
	public List<Map<String,Object>> queryCumulativeMoneyByWeek(String startTime,String endTime)throws Exception{

		logger.info("进入 queryCumulativeMoneyByWeek 统计 "+startTime+" 到 "+endTime+"  的钱累计统计");

		String sql = "SELECT dateTime, availableBalance, SUM(investMoney) AS investMoney, SUM(jlMoney) AS jlMoney, weekCycle FROM ( SELECT DATE_FORMAT(dateTime, '%Y-%m-%d') AS dateTime, availableBalance, invest AS investMoney, (recharge - cash) AS jlMoney, weekCycle FROM statistics_balance ORDER BY dateTime DESC ) s WHERE dateTime >= '"+startTime+"' AND dateTime <= '"+endTime+"' GROUP BY DATE_FORMAT(dateTime, '%Y'), weekCycle";

		return jdbcTemplate.queryForList(sql);

	}

	@Override
	public List<Map<String,Object>> queryCumulativeMoneyByMonth(String startTime,String endTime)throws Exception{

		logger.info("进入 queryCumulativeMoneyByMonth 统计 "+startTime+" 到 "+endTime+"  月的钱累计统计");

		String sql = "SELECT DATE_FORMAT(s.dateTime, '%Y-%m') AS dateTime, availableBalance, SUM(s.investMoney) AS investMoney, SUM(s.jlMoney) AS jlMoney FROM ( SELECT dateTime, b.availableBalance, b.invest AS investMoney, (b.recharge - b.cash) AS jlMoney FROM statistics_balance b ORDER BY dateTime DESC ) s WHERE s.dateTime >= '"+startTime+"' AND s.dateTime <= '"+endTime+"' GROUP BY DATE_FORMAT(s.dateTime, '%Y-%m') ORDER BY DATE_FORMAT(s.dateTime, '%Y-%m')";

		return jdbcTemplate.queryForList(sql);

	}

	@Override
	public Map<String,Object> queryCumulativeMoneyByDayFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception{

		logger.info("进入 queryCumulativeMoneyByMonth 统计 "+startTime+" 到 "+endTime+"  天的所有累计统计");

		String sql = "SELECT f.dateTime, CASE WHEN per.registerCount IS NULL THEN 0 ELSE per.registerCount END AS registerCount, CASE WHEN per.investCount IS NULL THEN 0 ELSE per.investCount END AS investCount, CASE WHEN per.firstInvestCount IS NULL THEN 0 ELSE per.firstInvestCount END AS firstInvestCount, CASE WHEN sb.availableBalance IS NULL THEN 0.00 ELSE sb.availableBalance END AS availableBalance, CASE WHEN sb.investMoney IS NULL THEN 0.00 ELSE sb.investMoney END AS investMoney, CASE WHEN sb.jlMoney IS NULL THEN 0.00 ELSE sb.jlMoney END AS jlMoney FROM ( SELECT dateTime FROM ( SELECT DATE_FORMAT(p.dateTime, '%Y-%m-%d') AS dateTime FROM person p UNION SELECT DATE_FORMAT(s.dateTime, '%Y-%m-%d') AS dateTime FROM statistics_balance s ) m GROUP BY dateTime ) f LEFT JOIN ( SELECT DATE_FORMAT(dateTime, '%Y-%m-%d') AS dateTime, registerCount, investPerson AS investCount, ( todayRegisterInvest + beforRegisterInvest ) AS firstInvestCount FROM person ) per ON f.dateTime = DATE_FORMAT(per.dateTime, '%Y-%m-%d') " +
				"LEFT JOIN ( SELECT DATE_FORMAT(dateTime, '%Y-%m-%d') AS dateTime, availableBalance, invest AS investMoney, (recharge - cash) AS jlMoney FROM statistics_balance ) sb ON f.dateTime = DATE_FORMAT(sb.dateTime, '%Y-%m-%d')  WHERE f.dateTime >= '"+startTime+"' AND f.dateTime <= '"+endTime+"' ORDER BY f.dateTime desc";

		return queryForListPage(sql.toString(),pageSize,startIndex);

	}

	@Override
	public Map<String,Object> queryCumulativeMoneyByWeekFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception{

		logger.info("进入 queryCumulativeMoneyByWeekFy 统计 "+startTime+" 到 "+endTime+"  周的所有累计统计");

		String sql = "SELECT d.dateTime, d.weekCycle, CASE WHEN f.registerCount IS NULL THEN 0 ELSE f.registerCount END AS registerCount, CASE WHEN f.investCount IS NULL THEN 0 ELSE f.investCount END AS investCount, CASE WHEN f.firstInvestCount IS NULL THEN 0 ELSE f.firstInvestCount END AS firstInvestCount, CASE WHEN h.availableBalance IS NULL THEN 0.00 ELSE h.availableBalance END AS availableBalance, CASE WHEN h.investMoney IS NULL THEN 0.00 ELSE h.investMoney END AS investMoney, CASE WHEN h.jlMoney IS NULL THEN 0.00 ELSE h.jlMoney END AS jlMoney FROM" +
				" ( SELECT dateTime, weekCycle FROM ( SELECT DATE_FORMAT(p.dateTime, '%Y-%m-%d') AS dateTime, p.weekCycle FROM person p UNION ALL SELECT DATE_FORMAT(s.dateTime, '%Y-%m-%d') AS dateTime, s.weekCycle FROM statistics_balance s ) m GROUP BY DATE_FORMAT(dateTime, '%Y'), weekCycle ) d LEFT JOIN ( SELECT dateTime, SUM(registerCount) AS registerCount, SUM(investCount) AS investCount, SUM(firstInvestCount) AS firstInvestCount, weekCycle FROM ( SELECT registerCount, investPerson AS investCount, ( todayRegisterInvest + beforRegisterInvest ) AS firstInvestCount, DATE_FORMAT(dateTime, '%Y') AS dateTime, weekCycle FROM person ORDER BY DATE_FORMAT(dateTime, '%Y-%m-%d') DESC ) p GROUP BY dateTime, p.weekCycle ) f ON DATE_FORMAT(d.dateTime, '%Y') = f.dateTime AND d.weekCycle = f.weekCycle LEFT JOIN ( SELECT dateTime, availableBalance, SUM(investMoney) AS investMoney, SUM(jlMoney) AS jlMoney, weekCycle FROM ( SELECT DATE_FORMAT(dateTime, '%Y') AS dateTime, availableBalance, invest AS investMoney, (recharge - cash) AS jlMoney, weekCycle FROM statistics_balance ORDER BY DATE_FORMAT(dateTime, '%Y-%m-%d') DESC ) s GROUP BY dateTime, weekCycle ) h ON DATE_FORMAT(d.dateTime, '%Y') = h.dateTime AND d.weekCycle = h.weekCycle WHERE d.dateTime >= '"+startTime+"' AND d.dateTime <= '"+endTime+"' ORDER BY d.dateTime DESC";

		return queryForListPage(sql.toString(),pageSize,startIndex);

	}



	@Override
	public Map<String,Object> queryCumulativeMoneyByMonthFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception{

		logger.info("进入 queryCumulativeMoneyByMonthFy 统计 "+startTime+" 到 "+endTime+"  月的所有累计统计");

		String sql = "SELECT d.dateTime, CASE WHEN h.registerCount IS NULL THEN 0 ELSE h.registerCount END AS registerCount, CASE WHEN h.investCount IS NULL THEN 0 ELSE h.investCount END AS investCount, CASE WHEN h.firstInvestCount IS NULL THEN 0 ELSE h.firstInvestCount END AS firstInvestCount, CASE WHEN f.availableBalance IS NULL THEN 0.00 ELSE f.availableBalance END AS availableBalance, CASE WHEN f.investMoney IS NULL THEN 0.00 ELSE f.investMoney END AS investMoney, CASE WHEN f.jlMoney IS NULL THEN 0.00 ELSE f.jlMoney END AS jlMoney " +
				"FROM ( SELECT dateTime FROM ( SELECT DATE_FORMAT(p.dateTime, '%Y-%m') AS dateTime FROM person p UNION ALL SELECT DATE_FORMAT(s.dateTime, '%Y-%m') AS dateTime FROM statistics_balance s ) m GROUP BY dateTime ) d LEFT JOIN ( SELECT DATE_FORMAT(s.dateTime, '%Y-%m') AS dateTime, availableBalance, SUM(s.investMoney) AS investMoney, SUM(s.jlMoney) AS jlMoney FROM ( SELECT dateTime, b.availableBalance, b.invest AS investMoney, (b.recharge - b.cash) AS jlMoney FROM statistics_balance b ORDER BY b.dateTime DESC ) s GROUP BY DATE_FORMAT(s.dateTime, '%Y-%m')) f ON f.dateTime = d.dateTime LEFT JOIN ( SELECT dateTime, sum(d.registerCount) AS registerCount, SUM(d.investPerson) AS investCount, SUM(d.firstInvestCount) AS firstInvestCount FROM ( SELECT DATE_FORMAT(t.dateTime, '%Y-%m') AS dateTime, t.registerCount, t.investPerson, ( todayRegisterInvest + beforRegisterInvest ) AS firstInvestCount FROM person t ) d GROUP BY d.dateTime ORDER BY d.dateTime ) h ON h.dateTime = d.dateTime WHERE d.dateTime >= '"+startTime+"' AND d.dateTime <= '"+endTime+"' ORDER BY d.dateTime DESC";

		return queryForListPage(sql.toString(),pageSize,startIndex);

	}


	@Override
	public List<Map<String,Object>> queryMoneyStreamByDay(String startTime,String endTime)throws Exception{

		logger.info("进入 queryMoneyStreamByDay 统计 "+startTime+" 到 "+endTime+"  天的资金的流量统计");

		String sql = "SELECT DATE_FORMAT(s.dateTime, '%Y-%m-%d') AS dateTime, s.todayFirstInvest, s.availableBalance, s.stand, s.recharge, s.cash, s.invest, s.investPhase, (s.recharge - s.cash) AS jlMoney FROM statistics_balance s WHERE DATE_FORMAT(dateTime, '%Y-%m-%d') >= '"+startTime+"' AND DATE_FORMAT(dateTime, '%Y-%m-%d') <= '"+endTime+"' ORDER BY dateTime";

		return jdbcTemplate.queryForList(sql);

	}

	@Override
	public List<Map<String,Object>> queryMoneyStreamByWeek(String startTime,String endTime)throws Exception{

		logger.info("进入 queryMoneyStreamByWeek 统计 "+startTime+" 到 "+endTime+"  周的资金的流量统计");

		String sql = "SELECT DATE_FORMAT(s.dateTime, '%Y-%m-%d') AS dateTime, SUM(s.todayFirstInvest) AS todayFirstInvest, availableBalance, stand, SUM(s.recharge) AS recharge, SUM(s.cash) AS cash, SUM(s.invest) AS invest, SUM(s.investPhase) AS investPhase, SUM(s.recharge - s.cash) AS jlMoney, s.weekCycle FROM ( SELECT * FROM statistics_balance ORDER BY dateTime DESC ) s WHERE DATE_FORMAT(dateTime, '%Y-%m-%d') >= '"+startTime+"' AND DATE_FORMAT(dateTime, '%Y-%m-%d') <= '"+endTime+"' GROUP BY DATE_FORMAT(s.dateTime, '%Y'), s.weekCycle ORDER BY s.dateTime";

		return jdbcTemplate.queryForList(sql);

	}


	@Override
	public List<Map<String,Object>> queryMoneyStreamByMoney(String startTime,String endTime)throws Exception{

		logger.info("进入 queryMoneyStreamByMoney 统计 "+startTime+" 到 "+endTime+"  月的资金的流量统计");

		String sql = "SELECT DATE_FORMAT(s.dateTime, '%Y-%m') AS dateTime, SUM(s.todayFirstInvest) AS todayFirstInvest, availableBalance, stand, SUM(s.recharge) AS recharge, SUM(s.cash) AS cash, SUM(s.invest) AS invest, SUM(s.investPhase) AS investPhase, SUM(s.recharge - s.cash) AS jlMoney, s.weekCycle FROM ( SELECT * FROM statistics_balance ORDER BY dateTime DESC ) s WHERE DATE_FORMAT(dateTime, '%Y-%m') >= '"+startTime+"' AND DATE_FORMAT(dateTime, '%Y-%m') <= '"+endTime+"' GROUP BY DATE_FORMAT(s.dateTime, '%Y-%m') ORDER BY s.dateTime";

		return jdbcTemplate.queryForList(sql);

	}

	@Override
	public Map<String, Object> queryMoneyStreamByDayFy(String startTime, String endTime, Integer pageSize, Integer startIndex) throws Exception {


		logger.info("进入 queryMoneyStreamByDayFy 分页统计 "+startTime+" 到 "+endTime+"  天的资金的流量统计");

		String sql = "SELECT DATE_FORMAT(s.dateTime, '%Y-%m-%d') AS dateTime, s.todayFirstInvest, s.availableBalance, s.stand, s.recharge, s.cash, s.invest, s.investPhase, (s.recharge - s.cash) AS jlMoney FROM statistics_balance s WHERE DATE_FORMAT(dateTime, '%Y-%m-%d') >= '"+startTime+"' AND DATE_FORMAT(dateTime, '%Y-%m-%d') <= '"+endTime+"' ORDER BY dateTime desc";

		return queryForListPage(sql.toString(),pageSize,startIndex);
	}


	@Override
	public Map<String,Object> queryMoneyStreamByWeekFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception{

		logger.info("进入 queryMoneyStreamByWeekFy 分页统计 "+startTime+" 到 "+endTime+"  周的资金的流量统计");

		String sql = "SELECT DATE_FORMAT(s.dateTime, '%Y-%m-%d') AS dateTime, SUM(s.todayFirstInvest) AS todayFirstInvest, availableBalance, stand, SUM(s.recharge) AS recharge, SUM(s.cash) AS cash, SUM(s.invest) AS invest, SUM(s.investPhase) AS investPhase, SUM(s.recharge - s.cash) AS jlMoney, s.weekCycle FROM ( SELECT * FROM statistics_balance ORDER BY dateTime DESC ) s WHERE DATE_FORMAT(dateTime, '%Y-%m-%d') >= '"+startTime+"' AND DATE_FORMAT(dateTime, '%Y-%m-%d') <= '"+endTime+"' GROUP BY DATE_FORMAT(s.dateTime, '%Y'), s.weekCycle ORDER BY s.dateTime DESC";

		return queryForListPage(sql.toString(),pageSize,startIndex);

	}


	@Override
	public Map<String,Object> queryMoneyStreamByMoneyFy(String startTime,String endTime,Integer pageSize,Integer startIndex)throws Exception{

		logger.info("进入 queryMoneyStreamByMoneyFy 分页统计 "+startTime+" 到 "+endTime+"  月的资金的流量统计");

		String sql = "SELECT DATE_FORMAT(s.dateTime, '%Y-%m') AS dateTime, SUM(s.todayFirstInvest) AS todayFirstInvest, availableBalance, stand, SUM(s.recharge) AS recharge, SUM(s.cash) AS cash, SUM(s.invest) AS invest, SUM(s.investPhase) AS investPhase, SUM(s.recharge - s.cash) AS jlMoney, s.weekCycle FROM ( SELECT * FROM statistics_balance ORDER BY dateTime DESC ) s  WHERE DATE_FORMAT(dateTime, '%Y-%m-%d') >= '"+startTime+"' AND DATE_FORMAT(dateTime, '%Y-%m') <= '"+endTime+"' GROUP BY DATE_FORMAT(s.dateTime, '%Y-%m') ORDER BY s.dateTime desc";

		return queryForListPage(sql.toString(),pageSize,startIndex);

	}


}
