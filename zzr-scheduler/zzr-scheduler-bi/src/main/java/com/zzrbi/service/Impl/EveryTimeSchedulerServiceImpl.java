 package com.zzrbi.service.Impl;

 import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.entity.EveryTime;
import com.zzrbi.service.IEveryTimeSchedulerService;
import com.zzrbi.util.DynamicContextChange;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Desc: 统计用户时时情况的实现
 * User: yinlonglong
 * Date: 2018/2/2
 * Time: 11:14
 */
 @Service
 public class EveryTimeSchedulerServiceImpl extends BaseDaoImpl implements IEveryTimeSchedulerService {
     /**
      * 获取log
      */
     private Logger logger = Logger.getLogger(EveryTimeSchedulerServiceImpl.class);

     @Override
     public Map<String, Object> findEveryTime(String type, String date) {
         logger.info("进入findEveryTime 方法");
         //设置数据源类型为zzr库
         DynamicContextChange.setDataSourceTypeSlave();
         String sql= null;
         Map<String,Object> map=null;
         if(type.equals("registerCount")){
             sql=" select COUNT(userId)AS registerCount from user_main  where roles=1 AND DATE_FORMAT(registerTime,'%Y-%m-%d %H')= ? GROUP BY DATE_FORMAT(registerTime,'%Y-%m-%d %H') ";
             logger.info("统计注册人数的sql为："+sql);
              map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("invest")){
             sql=" select COUNT(DISTINCT investorUserId)AS investCount ,SUM(investAmount)AS investMoney from loan_investor  where pnrStatus=1 AND DATE_FORMAT(investTime,'%Y-%m-%d %H')= ? GROUP BY DATE_FORMAT(investTime,'%Y-%m-%d %H') ";
             logger.info("统计投资人数和金额的sql为："+sql);
              map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("firstInvestCount")){
             sql=" select COUNT(aa.investorUserId)AS firstInvestCount from (select li.investTime,li.investAmount,li.investorUserId from loan_investor li where li.pnrStatus=1 GROUP BY li.investorUserId)aa where DATE_FORMAT(aa.investTime,'%Y-%m-%d %H')= ? ";
             logger.info("首投人数的sql为："+sql);
              map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("availableBalance")){
             sql=" SELECT SUM(lip.toBeCollectedPrincipal) AS availableBalance FROM loan_investor_phase lip WHERE lip.pnrStatus IS NULL AND DATE_FORMAT(lip.phaseDate, '%Y-%m-%d %H') >= ? ";
             logger.info("统计存量金额的sql为："+sql);
              map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("stand")){
             sql=" select SUM(ua.cash)AS stand from user_main u, user_account ua where u.userId=ua.userId AND u.roles=1 and DATE_FORMAT(u.registerTime,'%Y-%m-%d %H')<= ?";
            logger.info("统计站岗资金的sql为："+sql);
              map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("recharge")){
             sql=" select SUM(rl.amount)AS recharge from recharge_log rl,user_main um where rl.userId=um.userId AND um.roles=1 AND rl.pnrStatus=1 AND DATE_FORMAT(rl.rechargeTime,'%Y-%m-%d %H')= ? GROUP BY DATE_FORMAT(rl.rechargeTime,'%Y-%m-%d %H') ";
            logger.info("统计充值金额的sql为："+sql);
              map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("cash")){
             sql=" select SUM(c.amount)AS cash from cash_withdraw_request c ,user_main u where c.userId=u.userId AND u.roles=1 AND c.pnrStatus=1 AND DATE_FORMAT(c.applyTime,'%Y-%m-%d %H')= ? GROUP BY DATE_FORMAT(c.applyTime,'%Y-%m-%d %H') ";
            logger.info("统计提现金额的sql为："+sql);
              map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("investPhase")){
             sql=" select SUM(toBeCollectedPrincipal+toBeCollectedInterest)AS investPhase from loan_investor_phase WHERE DATE_FORMAT(phaseDate,'%Y-%m-%d') = ? GROUP BY DATE_FORMAT(phaseDate,'%Y-%m-%d %H') ";
            logger.info("统计回款金额的sql为："+sql);
              map=queryForMapNoException(sql,new Object[]{ date });
         }
         DynamicContextChange.clearCustomerType();
         return map;
     }

     @Override
     public int saveEveryTime(EveryTime everyTime) {
         logger.info("--------->>>>>>>> saveEveryTime()方法");
         int rows =0;
         try{
             //设置数据源类型为zzrbi库
             String sql = " INSERT INTO `every_time` (`dateTime`, `registerCount`, `investCount`, `firstInvestCount`, `availableBalance`, `stand`, `recharge`, `cash`, `investMoney`, `investPhase`, `creatTime`) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
             DynamicContextChange.setDataSourceTypeMaster();
             rows = insertSqlAndReturnKeyId(sql, new Object[]{
                     everyTime.getDateTime(),
                     everyTime.getRegisterCount(),
                     everyTime.getInvestCount(),
                     everyTime.getFirstInvestCount(),
                     everyTime.getAvailableBalance(),
                     everyTime.getStand(),
                     everyTime.getRecharge(),
                     everyTime.getCash(),
                     everyTime.getInvestMoney(),
                     everyTime.getInvestPhase(),
                     everyTime.getCreatTime()
             });
             DynamicContextChange.clearCustomerType();
         }catch (Exception e){
             e.printStackTrace();
         }
         return rows;
     }

 }
