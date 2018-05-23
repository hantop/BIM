 package com.zzrbi.service.Impl;

 import com.zzrbi.dao.BaseDaoImpl;
import com.zzrbi.entity.Person;
import com.zzrbi.service.IPersonSchedulerService;
import com.zzrbi.util.DynamicContextChange;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

 /**
  * Desc: 统计所有用户情况的实现
  * User: yinlonglong
  * Date: 2018/2/2
  * Time: 15:42
  */
 @Service
 public class PersonSchedulerServiceImpl extends BaseDaoImpl implements IPersonSchedulerService {
     /**
      * 获取log
      */
     private Logger logger = Logger.getLogger(PersonSchedulerServiceImpl.class);

     @Override
     public Map<String,Object> findPerson(String type,String date) {
         logger.info("进入findPerson 方法");
         //设置数据源类型为zzr库
         DynamicContextChange.setDataSourceTypeSlave();
         String sql= null;
         Map<String,Object> map=null;
         if(type.equals("registerCount")){
             sql=" select COUNT(u.userId)AS registerCount from user_main u where u.roles=1 AND DATE_FORMAT(u.registerTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(u.registerTime,'%Y-%m-%d') ";
             logger.info("统计注册人数的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("todayRegisterAccount")){
             sql=" select COUNT(u.userId)AS todayRegisterAccount from user_main u,user_account ua where u.userId=ua.userId AND u.roles=1 AND DATE_FORMAT(u.registerTime,'%Y-%m-%d')= DATE_FORMAT(ua.openTime,'%Y-%m-%d') AND DATE_FORMAT(u.registerTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(u.registerTime,'%Y-%m-%d') ";
             logger.info("统计当天注册且开户人数的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("beforRegisterAccount")){
             sql=" select COUNT(u.userId)AS beforRegisterAccount from user_main u,user_account ua where u.userId=ua.userId AND u.roles=1 AND DATE_FORMAT(u.registerTime,'%Y-%m-%d')< DATE_FORMAT(ua.openTime,'%Y-%m-%d') AND ua.pnrUserId IS NOT NULL and DATE_FORMAT(ua.openTime,'%Y-%m-%d') = ? GROUP BY DATE_FORMAT(ua.openTime,'%Y-%m-%d') ";
             logger.info("统计往日注册当天开户人数的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("investPerson")){
             sql=" select COUNT(a.investorUserId)AS investPerson from (select DATE_FORMAT(investTime,'%Y-%m-%d') investTime,investorUserId from loan_investor  where pnrStatus=1 GROUP BY DATE_FORMAT(investTime,'%Y-%m-%d'),investorUserId)a WHERE a.investTime = ? GROUP BY a.investTime ";
             logger.info("统计投资人数的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("todayRegisterInvest")){
             sql=" SELECT COUNT(bb.userId)AS todayRegisterInvest FROM ( SELECT u.userId, u.registerTime, li.investTime, li.investAmount FROM user_main u, loan_investor li WHERE u.userId = li.investorUserId AND li.pnrStatus = 1 AND date_format(u.registerTime, '%Y-%m-%d') = date_format(li.`investTime`, '%Y-%m-%d') GROUP BY u.userId ) bb WHERE date_format(bb.`investTime`, '%Y-%m-%d')= ? GROUP BY date_format(bb.investTime, '%Y-%m-%d') ";
             logger.info("统计当天注册且当天首投投资人数的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("beforRegisterInvest")){
             sql=" SELECT COUNT(a.investorUserId) AS beforRegisterInvest FROM ( SELECT li.investTime, li.investorUserId, u.registerTime FROM loan_investor li, user_main u WHERE li.investorUserId = u.userId AND li.pnrStatus = 1 GROUP BY li.investorUserId ) a WHERE DATE_FORMAT(a.investTime, '%Y-%m-%d') != DATE_FORMAT(a.registerTime, '%Y-%m-%d') AND DATE_FORMAT(a.investTime, '%Y-%m-%d') = ? GROUP BY DATE_FORMAT(a.investTime, '%Y-%m-%d') ";
             logger.info("统计往日注册且当天首投投资人数的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("repeatInvest")){
             sql=" select COUNT(aa.id)AS repeatInvest from (SELECT DATE_FORMAT(investTime,'%Y-%m-%d')investTime,COUNT(id) id from loan_investor  where pnrStatus=1 GROUP BY DATE_FORMAT(investTime,'%Y-%m-%d'),investorUserId )aa where aa.id > 1 AND aa.investTime = ? GROUP BY aa.investTime ";
             logger.info("统计复投人数的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }else if(type.equals("investCount")){
             sql=" select COUNT(li.id)AS investCount from loan_investor li where li.pnrStatus=1 AND DATE_FORMAT(li.investTime,'%Y-%m-%d')= ? GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d') ";
             logger.info("统计投资笔数的sql为："+sql);
             map=queryForMapNoException(sql,new Object[]{ date });
         }
         DynamicContextChange.clearCustomerType();
         return map;
     }

     @Override
     public int savePerson(Person person) {
         logger.info("--------->>>>>>>> savePerson()方法");
         //设置数据源类型为zzrbi库
         DynamicContextChange.setDataSourceTypeMaster();
         String sql = " INSERT INTO `person` (`dateTime`, `registerCount`, `todayRegisterAccount`, `beforRegisterAccount`, `investPerson`, `todayRegisterInvest`, `beforRegisterInvest`, `repeatInvest`, `investCount`, `creatTime`, `weekCycle`) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
         int rows = insertSqlAndReturnKeyId(sql.toString(), new Object[]{
                 person.getDateTime(),
                 person.getRegisterCount(),
                 person.getTodayRegisterAccount(),
                 person.getBeforRegisterAccount(),
                 person.getInvestPerson(),
                 person.getTodayRegisterInvest(),
                 person.getBeforRegisterInvest(),
                 person.getRepeatInvest(),
                 person.getInvestCount(),
                 person.getCreatTime(),
                 person.getWeekCycle()
         });
         DynamicContextChange.clearCustomerType();
         return rows;
     }

     @Override
     public List<Map<String, Object>> queryPersonList() {
         logger.info("--------->>>>>>>> queryPersonList()方法");
         //设置数据源类型为zzr库
         DynamicContextChange.setDataSourceTypeSlave();
         String sql=" select aa.registerTime,IFNULL(hh.zcrs,0)AS ZCRS ,IFNULL(bb.todayzrkh,0)AS DRZRKH,IFNULL(cc.wrzckh,0)AS WRZCKH,IFNULL(dd.tzrs,0)AS TZRS,IFNULL(dd.tzbs,0)AS TZBS,IFNULL(ee.todaystrs,0)AS DRSTRS,IFNULL(ff.wrstrs,0)AS WRSTRS,IFNULL(jj.ftrs,0)AS FTRS from  " +
                 "(SELECT n.registerTime FROM ( SELECT DATE_FORMAT(u.registerTime, '%Y-%m-%d') AS registerTime FROM user_main u UNION ALL SELECT DATE_FORMAT(ua.openTime, '%Y-%m-%d') AS registerTime FROM user_account ua UNION ALL SELECT DATE_FORMAT(li.investTime, '%Y-%m-%d') AS registerTime FROM loan_investor li ) n GROUP BY n.registerTime) aa " +
                 "LEFT JOIN (select DATE_FORMAT(ua.openTime,'%Y-%m-%d')openTime,COUNT(u.userId)todayzrkh from user_main u,user_account ua where u.userId=ua.userId AND u.roles=1 AND DATE_FORMAT(u.registerTime,'%Y-%m-%d')= DATE_FORMAT(ua.openTime,'%Y-%m-%d') GROUP BY DATE_FORMAT(u.registerTime,'%Y-%m-%d') " +
                 ")bb ON aa.registerTime=bb.openTime " +
                 "LEFT JOIN (SELECT DATE_FORMAT(ua.openTime, '%Y-%m-%d')openTime, COUNT(u.userId) AS wrzckh FROM user_main u, user_account ua WHERE u.userId = ua.userId AND u.roles = 1 AND DATE_FORMAT(u.registerTime, '%Y-%m-%d') < DATE_FORMAT(ua.openTime, '%Y-%m-%d') AND ua.pnrUserId IS NOT NULL GROUP BY DATE_FORMAT(ua.openTime, '%Y-%m-%d') " +
                 ")cc ON aa.registerTime=cc.openTime " +
                 "LEFT JOIN (select DATE_FORMAT(li.investTime,'%Y-%m-%d')investTime,COUNT(li.id)tzbs,COUNT(DISTINCT li.investorUserId)tzrs from loan_investor li where li.pnrStatus=1  GROUP BY DATE_FORMAT(li.investTime,'%Y-%m-%d') " +
                 ")dd ON aa.registerTime=dd.investTime " +
                 "LEFT JOIN (SELECT date_format(c.`investTime`, '%Y-%m-%d')investTime,COUNT(c.userId)todaystrs FROM ( SELECT u.userId, u.registerTime, li.investTime, li.investAmount FROM user_main u, loan_investor li WHERE u.userId = li.investorUserId AND li.pnrStatus = 1 AND date_format(u.registerTime, '%Y-%m-%d') = date_format(li.`investTime`, '%Y-%m-%d') GROUP BY u.userId ) c GROUP BY date_format(c.investTime, '%Y-%m-%d') " +
                 ")ee ON aa.registerTime=ee.investTime " +
                 "LEFT JOIN (SELECT DATE_FORMAT(a.investTime, '%Y-%m-%d') AS investTime, COUNT(a.investorUserId) AS wrstrs FROM ( SELECT li.investTime, li.investorUserId, u.registerTime FROM loan_investor li, user_main u WHERE li.investorUserId = u.userId AND li.pnrStatus = 1 GROUP BY li.investorUserId ) a WHERE DATE_FORMAT(a.investTime, '%Y-%m-%d') != DATE_FORMAT(a.registerTime, '%Y-%m-%d') GROUP BY DATE_FORMAT(a.investTime, '%Y-%m-%d') " +
                 ")ff ON aa.registerTime=ff.investTime " +
                 "LEFT JOIN (select b.investTime,COUNT(b.id)ftrs from (SELECT DATE_FORMAT(investTime,'%Y-%m-%d')investTime,COUNT(id) id from loan_investor  where pnrStatus=1 GROUP BY DATE_FORMAT(investTime,'%Y-%m-%d'),investorUserId )b where b.id > 1  GROUP BY b.investTime " +
                 ")jj ON aa.registerTime=jj.investTime " +
                 "LEFT JOIN (select DATE_FORMAT(u.registerTime,'%Y-%m-%d')registerTime,COUNT(u.userId)zcrs from user_main u where u.roles=1 GROUP BY DATE_FORMAT(u.registerTime,'%Y-%m-%d'))hh ON aa.registerTime=hh.registerTime " +
                 "where aa.registerTime < DATE_FORMAT(date_sub(NOW(),interval 1 day),'%Y-%m-%d')AND aa.registerTime>='2016-03-23' ORDER BY aa.registerTime ";
         logger.info("统计用户情况历史数据的sql为："+sql);
         List<Map<String, Object>> lists = null;
         try {
             lists = jdbcTemplate.queryForList(sql);
         } catch (Exception e) {
             e.printStackTrace();
         }
         DynamicContextChange.clearCustomerType();
         return lists;
     }
 }
