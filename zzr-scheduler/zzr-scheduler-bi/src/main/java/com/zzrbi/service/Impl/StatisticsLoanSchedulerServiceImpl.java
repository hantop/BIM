 package com.zzrbi.service.Impl;

 import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.entity.StatisticsLoan;
import com.zzrbi.service.IStatisticsLoanSchedulerService;
import com.zzrbi.util.DynamicContextChange;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

 /**
  * Desc: 统计项目情况的实现
  * User: yinlonglong
  * Date: 2018/1/25
  * Time: 13:57
  */
 @Service
 public class StatisticsLoanSchedulerServiceImpl extends BaseDaoImpl implements IStatisticsLoanSchedulerService {
     /**
      * 获取log
      */
     private Logger logger = Logger.getLogger(StatisticsLoanSchedulerServiceImpl.class);


     @Override
     public List<Map<String, Object>> queryStatisticsLoanList() {
         logger.info("--------->>>>>>>> queryStatisticsLoanList()方法");
         //设置数据源类型为zzr库
         DynamicContextChange.setDataSourceTypeSlave();
         String sql=" select a.investTime,IFNULL(b.newLoanPeople,0)AS newLoanPeople,IFNULL(b.newLoanCount,0)AS newLoanCount,IFNULL(b.newLoanMoney,0.00)AS newLoanMoney, " +
                 "IFNULL(c.People3,0)AS People30,IFNULL(c.Count3,0)AS Count30,IFNULL(c.Money3,0.00)AS Money30,IFNULL(d.People6,0)AS People60,IFNULL(d.Count6,0)AS Count60,IFNULL(d.Money6,0.00)AS Money60, " +
                 "IFNULL(e.People9,0)AS People90,IFNULL(e.Count9,0)AS Count90,IFNULL(e.Money9,0.00)AS Money90,IFNULL(f.People18,0)AS People180,IFNULL(f.Count18,0)AS Count180,IFNULL(f.Money18,0.00)AS Money180, " +
                 "IFNULL(g.People36,0)AS People360,IFNULL(g.Count36,0)AS Count360,IFNULL(g.Money36,0.00)AS Money360  " +
                 "from (select DATE_FORMAT(investTime,'%Y-%m-%d')investTime from loan_investor GROUP BY DATE_FORMAT(investTime,'%Y-%m-%d')) a " +
                 "LEFT JOIN (select DATE_FORMAT(li.investTime,'%Y-%m-%d')AS investTimeNew,COUNT(DISTINCT li.investorUserId)AS newLoanPeople,COUNT(li.id)AS newLoanCount,SUM(li.investAmount)AS newLoanMoney from loan_investor li ,loan_desc ld where li.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj=1 GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d'))b ON a.investTime=b.investTimeNew " +
                 "LEFT JOIN (select DATE_FORMAT(li.investTime,'%Y-%m-%d')AS investTime3,COUNT(DISTINCT li.investorUserId)AS People3,COUNT(li.id)AS Count3,SUM(li.investAmount)AS Money3 from loan_investor li,loan l,loan_desc ld where li.loanId=l.loanId AND l.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj!=1 AND l.termCount <= 30 GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d'))c ON a.investTime=c.investTime3 " +
                 "LEFT JOIN (select DATE_FORMAT(li.investTime,'%Y-%m-%d')AS investTime6,COUNT(DISTINCT li.investorUserId)AS People6,COUNT(li.id)AS Count6,SUM(li.investAmount)AS Money6 from loan_investor li,loan l,loan_desc ld where li.loanId=l.loanId AND l.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj!=1 AND l.termCount > 30 AND l.termCount <= 60 GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d'))d ON a.investTime=d.investTime6 " +
                 "LEFT JOIN (select DATE_FORMAT(li.investTime,'%Y-%m-%d')AS investTime9,COUNT(DISTINCT li.investorUserId)AS People9,COUNT(li.id)AS Count9,SUM(li.investAmount)AS Money9 from loan_investor li,loan l,loan_desc ld where li.loanId=l.loanId AND l.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj!=1 AND l.termCount > 60 AND l.termCount <= 90 GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d'))e ON a.investTime=e.investTime9 " +
                 "LEFT JOIN (select DATE_FORMAT(li.investTime,'%Y-%m-%d')AS investTime18,COUNT(DISTINCT li.investorUserId)AS People18,COUNT(li.id)AS Count18,SUM(li.investAmount)AS Money18 from loan_investor li,loan l,loan_desc ld where li.loanId=l.loanId AND l.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj!=1 AND l.termCount > 90 AND l.termCount <= 180 GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d'))f ON a.investTime=f.investTime18 " +
                 "LEFT JOIN (select DATE_FORMAT(li.investTime,'%Y-%m-%d')AS investTime36,COUNT(DISTINCT li.investorUserId)AS People36,COUNT(li.id)AS Count36,SUM(li.investAmount)AS Money36 from loan_investor li,loan l,loan_desc ld where li.loanId=l.loanId AND l.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj!=1 AND l.termCount > 180  GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d'))g ON a.investTime=g.investTime36  WHERE a.investTime < DATE_FORMAT(date_sub(NOW(),interval 1 day),'%Y-%m-%d') AND a.investTime >= '2016-03-23'";
         logger.info("统计项目情况历史数据的sql为："+sql);
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
     public int saveStatisticsLoan(StatisticsLoan statisticsLoan) {
         logger.info("--------->>>>>>>> saveStatisticsLoan()方法");
         int rows =0;
         try{
             //设置数据源类型为zzrbi库
             String sql = " INSERT INTO `statistics_loan` (`dateTime`, `newLoanPeople`, `newLoanMoney`, `newLoanCount`, `People30`, `Money30`, `Count30`, `People60`, `Money60`, `Count60`, `People90`, `Money90`, `Count90`, `People180`, `Money180`, `Count180`, `People360`, `Money360`, `Count360`, `creatTime`, `weekCycle`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";
             DynamicContextChange.setDataSourceTypeMaster();
             rows = insertSqlAndReturnKeyId(sql, new Object[]{
                     statisticsLoan.getDateTime(),
                     statisticsLoan.getNewLoanPeople(),
                     statisticsLoan.getNewLoanMoney(),
                     statisticsLoan.getNewLoanCount(),
                     statisticsLoan.getPeople30(),
                     statisticsLoan.getMoney30(),
                     statisticsLoan.getCount30(),
                     statisticsLoan.getPeople60(),
                     statisticsLoan.getMoney60(),
                     statisticsLoan.getCount60(),
                     statisticsLoan.getPeople90(),
                     statisticsLoan.getMoney90(),
                     statisticsLoan.getCount90(),
                     statisticsLoan.getPeople180(),
                     statisticsLoan.getMoney180(),
                     statisticsLoan.getCount180(),
                     statisticsLoan.getPeople360(),
                     statisticsLoan.getMoney360(),
                     statisticsLoan.getCount360(),
                     statisticsLoan.getCreatTime(),
                     statisticsLoan.getWeekCycle()
              });
             DynamicContextChange.clearCustomerType();
         }catch (Exception e){
             e.printStackTrace();
         }
         return rows;
     }

     @Override
     public Map<String, Object> findStatisticsLoan(String type,String date) {
         logger.info("findStatisticsLoan 方法");
         //设置数据源类型为zzr库
         DynamicContextChange.setDataSourceTypeSlave();
         String sql= null;
         Map<String,Object> map=null;
         if(type.equals("new")){
             sql=" select COUNT(DISTINCT li.investorUserId)AS newLoanPeople,COUNT(li.id)AS newLoanCount,SUM(li.investAmount)AS newLoanMoney from loan_investor li ,loan_desc ld where li.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj=1 AND DATE_FORMAT(li.investTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d') ";
             logger.info("统计新手项目情况的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("30")){
             sql=" select COUNT(DISTINCT li.investorUserId)AS People30,COUNT(li.id)AS Count30,SUM(li.investAmount)AS Money30 from loan_investor li,loan l,loan_desc ld where li.loanId=l.loanId AND l.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj!=1 AND l.termCount <= 30 AND DATE_FORMAT(li.investTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d') ";
             logger.info("统计30天项目情况的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("60")){
             sql=" select COUNT(DISTINCT li.investorUserId)AS People60,COUNT(li.id)AS Count60,SUM(li.investAmount)AS Money60 from loan_investor li,loan l,loan_desc ld where li.loanId=l.loanId AND l.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj!=1 AND l.termCount > 30 AND l.termCount <= 60 AND DATE_FORMAT(li.investTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d') ";
             logger.info("统计60天项目情况的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("90")){
             sql=" select COUNT(DISTINCT li.investorUserId)AS People90,COUNT(li.id)AS Count90,SUM(li.investAmount)AS Money90 from loan_investor li,loan l,loan_desc ld where li.loanId=l.loanId AND l.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj!=1 AND l.termCount > 60 AND l.termCount <= 90 AND DATE_FORMAT(li.investTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d') ";
             logger.info("统计90天项目情况的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("180")){
             sql=" select COUNT(DISTINCT li.investorUserId)AS People180,COUNT(li.id)AS Count180,SUM(li.investAmount)AS Money180 from loan_investor li,loan l,loan_desc ld where li.loanId=l.loanId AND l.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj!=1 AND l.termCount > 90 AND l.termCount <= 180 AND DATE_FORMAT(li.investTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d') ";
             logger.info("统计180天项目情况的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("360")){
             sql=" select COUNT(DISTINCT li.investorUserId)AS People360,COUNT(li.id)AS Count360,SUM(li.investAmount)AS Money360 from loan_investor li,loan l,loan_desc ld where li.loanId=l.loanId AND l.loanId=ld.loanID AND li.pnrStatus=1 AND ld.isRookiePrj!=1 AND l.termCount > 180 AND DATE_FORMAT(li.investTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d') ";
             logger.info("统计360天项目情况的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }
         DynamicContextChange.clearCustomerType();
         return map;
     }
 }
