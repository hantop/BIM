 package com.zzrbi.service.Impl;

 import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.entity.StatisticsBalance;
import com.zzrbi.service.IStatisticsBalanceSchedulerService;
import com.zzrbi.util.DynamicContextChange;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

 /**
  * Desc: 统计所有用户资金情况的实现
  * User: yinlonglong
  * Date: 2018/1/25
  * Time: 13:57
  */
 @Service
 public class StatisticsBalanceSchedulerServiceImpl extends BaseDaoImpl implements IStatisticsBalanceSchedulerService {
     /**
      * 获取log
      */
     private Logger logger = Logger.getLogger(StatisticsBalanceSchedulerServiceImpl.class);

     @Override
     public BigDecimal findFinances(String type,String date) {
         logger.info("进入findFinances 方法");
         //设置数据源类型为zzr库
         DynamicContextChange.setDataSourceTypeSlave();
         String sql= null;
         Map<String, Object> rusult  = null;
         if(type.equals("shoutou")){
             sql=" select SUM(a.investAmount)AS amount from (SELECT investTime, investAmount FROM loan_investor WHERE pnrStatus = 1 GROUP BY investorUserId)a WHERE DATE_FORMAT(a.investTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(a.investTime,'%Y-%m-%d') ";
             logger.info("统计首投的sql为："+sql);
             rusult  = queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("cunliang")){
             sql=" SELECT SUM(lip.toBeCollectedPrincipal) AS amount FROM loan_investor_phase lip, loan_phase lp, loan l WHERE lip.loanPhaseId = lp.id AND lp.loanId = l.loanId AND DATE_FORMAT(lip.phaseDate, '%Y-%m-%d') >= ? AND DATE_FORMAT(l.onlineTime, '%Y-%m-%d') <= ?  ";
             logger.info("统计存量的sql为："+sql);
             rusult  = queryForMapNoException(sql,new Object[]{ date ,date });
         }else if(type.equals("stand")){
             sql=" select SUM(ua.cash)AS amount from user_main um,user_account ua where um.userId=ua.userId and um.roles=1 AND DATE_FORMAT(um.registerTime,'%Y-%m-%d')<= ? ";
             logger.info("统计站岗的sql为："+sql);
             rusult  = queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("recharge")){
             sql=" select SUM(r.amount)AS amount from recharge_log r,user_main u where r.userId=u.userId AND u.roles=1 AND r.pnrStatus=1 AND DATE_FORMAT(r.rechargeTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(r.rechargeTime,'%Y-%m-%d') ";
             logger.info("统计充值的sql为："+sql);
             rusult  = queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("cash")){
             sql=" select SUM(c.amount)AS cash from cash_withdraw_request c ,user_main u where c.userId=u.userId AND u.roles=1 AND c.pnrStatus=1 AND DATE_FORMAT(c.applyTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(c.applyTime,'%Y-%m-%d') ";
             logger.info("统计提现的sql为："+sql);
             rusult  = queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("invest")){
             sql=" select SUM(investAmount)AS amount from loan_investor where pnrStatus=1 AND DATE_FORMAT(investTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(investTime,'%Y-%m-%d') ";
             logger.info("统计投资的sql为："+sql);
             rusult  = queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("investPhase")){
             sql=" select SUM(toBeCollectedPrincipal+toBeCollectedInterest)AS amount from loan_investor_phase WHERE DATE_FORMAT(phaseDate,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(phaseDate,'%Y-%m-%d') ";
             logger.info("统计回款的sql为："+sql);
             rusult  = queryForMapNoException(sql,new Object[]{ date });
         }
         DynamicContextChange.clearCustomerType();
         if(rusult !=null){
             if(rusult.get("amount")!=null){
                 return new BigDecimal(rusult.get("amount").toString());
             }else{
                 return new BigDecimal(0.00);
             }
         }else{
             return new BigDecimal(0.00);
         }
     }

     @Override
     public int saveStatisticsBalance(StatisticsBalance statisticsBalance) {
         logger.info("--------->>>>>>>> saveStatisticsBalance()方法");
         int rows =0;
         try{
             //设置数据源类型为zzrbi库
             String sql = " INSERT INTO `statistics_balance` (`dateTime`, `todayFirstInvest`, `availableBalance`, `stand`, `recharge`, `cash`, `invest`, `investPhase`, `creatTime`, `weekCycle`) VALUES (?,?,?,?,?,?,?,?,?,?) ";
             DynamicContextChange.setDataSourceTypeMaster();
              rows = insertSqlAndReturnKeyId(sql, new Object[]{
                     statisticsBalance.getDateTime(),
                     statisticsBalance.getTodayFirstInvest(),
                     statisticsBalance.getAvailableBalance(),
                     statisticsBalance.getStand(),
                     statisticsBalance.getRecharge(),
                     statisticsBalance.getCash(),
                     statisticsBalance.getInvest(),
                     statisticsBalance.getInvestPhase(),
                     statisticsBalance.getCreatTime(),
                     statisticsBalance.getWeekCycle()
             });
             DynamicContextChange.clearCustomerType();
         }catch (Exception e){
             e.printStackTrace();
         }
         return rows;
     }

     @Override
     public List<Map<String, Object>> queryStatisticsBalanceList() {
         logger.info("--------->>>>>>>> queryStatisticsBalanceList()方法");
         //设置数据源类型为zzr库
         DynamicContextChange.setDataSourceTypeSlave();
         String sql="  select aa.investTime,IFNULL(bb.tzje,0)AS TZJE,IFNULL(cc.txje,0)AS TXJE,IFNULL(dd.czje,0)AS CZJE,0.00 AS CLJE,IFNULL(jj.stje,0)AS STJE,IFNULL(ff.hkje,0)AS HKJE from  " +
                 "(SELECT a.investTime FROM ( SELECT DATE_FORMAT(l.investTime, '%Y-%m-%d') AS investTime FROM loan_investor l UNION ALL SELECT DATE_FORMAT(r.rechargeTime, '%Y-%m-%d') AS investTime FROM recharge_log r UNION ALL SELECT DATE_FORMAT(c.applyTime, '%Y-%m-%d') AS investTime FROM cash_withdraw_request c UNION ALL SELECT DATE_FORMAT(p.phaseDate, '%Y-%m-%d') AS investTime FROM loan_investor_phase p ) a GROUP BY a.investTime) aa  " +
                 "LEFT JOIN (select DATE_FORMAT(investTime,'%Y-%m-%d')investTime,SUM(investAmount)tzje from loan_investor where pnrStatus=1 GROUP BY DATE_FORMAT(investTime,'%Y-%m-%d'))bb ON aa.investTime=bb.investTime  " +
                 "LEFT JOIN (select DATE_FORMAT(c.applyTime,'%Y-%m-%d')applyTime,SUM(c.amount)txje from cash_withdraw_request c,user_main u where c.userId=u.userId AND u.roles=1 AND c.pnrStatus=1 GROUP BY DATE_FORMAT(c.applyTime,'%Y-%m-%d'))cc ON aa.investTime=cc.applyTime  " +
                 "LEFT JOIN (select DATE_FORMAT(r.rechargeTime,'%Y-%m-%d')rechargeTime,SUM(r.amount)czje from recharge_log r,user_main u where r.userId =u.userId AND u.roles=1 AND r.pnrStatus=1 GROUP BY DATE_FORMAT(r.rechargeTime,'%Y-%m-%d'))dd ON aa.investTime=dd.rechargeTime  " +
                 "LEFT JOIN (select DATE_FORMAT(phaseDate,'%Y-%m-%d')phaseDate,SUM(toBeCollectedPrincipal+toBeCollectedInterest)hkje  from loan_investor_phase GROUP BY DATE_FORMAT(phaseDate,'%Y-%m-%d'))ff ON aa.investTime=ff.phaseDate  " +
                 "LEFT JOIN (select DATE_FORMAT(a.investTime,'%Y-%m-%d')investTime,SUM(a.investAmount)stje from (SELECT investTime, investAmount FROM loan_investor WHERE pnrStatus = 1 GROUP BY investorUserId)a GROUP BY DATE_FORMAT(a.investTime,'%Y-%m-%d')  " +
                 ")jj ON aa.investTime=jj.investTime where aa.investTime < DATE_FORMAT(date_sub(NOW(),interval 1 day),'%Y-%m-%d') AND  aa.investTime >= '2016-03-23' ORDER BY aa.investTime ";
         logger.info("统计用户资金历史数据的sql为："+sql);
         List<Map<String, Object>> lists = null;
         try {
             lists = jdbcTemplate.queryForList(sql);
         } catch (Exception e) {
             e.printStackTrace();
         }
         DynamicContextChange.clearCustomerType();
         return lists;
     }

     @Override
     public BigDecimal findStand(String dateTime) {
         logger.info("进入findStand 方法");
         //设置数据源类型为zzr库
         DynamicContextChange.setDataSourceTypeSlave();
         String sql= " SELECT s.availableBalance FROM statistics_balance s WHERE DATE_FORMAT(s.createTime,'%Y-%m-%d')=? ";
         logger.info("统计历史数据站岗资金的sql为："+sql);
         Map<String, Object> map  = queryForMapNoException(sql,new Object[]{dateTime});
         DynamicContextChange.clearCustomerType();
         if(map !=null){
             if(null!=map.get("availableBalance")){
                 return new BigDecimal(map.get("availableBalance").toString());
             }else{
                 return new BigDecimal(0.00);
             }
         }else{
             return new BigDecimal(0.00);
         }
     }
 }
