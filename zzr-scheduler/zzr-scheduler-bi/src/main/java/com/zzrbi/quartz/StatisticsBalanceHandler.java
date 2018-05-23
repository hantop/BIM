package com.zzrbi.quartz;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.zzrbi.entity.StatisticsBalance;
import com.zzrbi.service.IStatisticsBalanceSchedulerService;
import com.zzrbi.util.DateFormatUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Desc: 统计所有用户的资金情况
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 11:37
 */
@JobHandler(value="statisticsBalanceJobHandler")
@Component
public class StatisticsBalanceHandler extends IJobHandler {
    @Resource
    private IStatisticsBalanceSchedulerService statisticsBalanceSchedulerService;

    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("=============每天定时统计所有用户金额情况的定时器执行开始！================");
        long startTime1 = System.currentTimeMillis();//获取当前时间
        XxlJobLogger.log("=========统计所有用户金额情况的定时器执行开始时间为:"+startTime1+"===========");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(s.equals("old")){
            List<Map<String, Object>> statisticsBalanceList =statisticsBalanceSchedulerService.queryStatisticsBalanceList();
            XxlJobLogger.log("=========统计用户资金历史数据为:"+statisticsBalanceList+"===========");
            XxlJobLogger.log("=========统计用户资金历史数据的条数为:"+statisticsBalanceList.size()+"===========");
            if(statisticsBalanceList!=null) {
                for (Map<String, Object> h : statisticsBalanceList) {
                    Date dateTime = sdf.parse(h.get("investTime").toString());
                    BigDecimal todayFirstInvest = (BigDecimal)h.get("STJE");
                    BigDecimal availableBalance = (BigDecimal)h.get("CLJE");
                    BigDecimal recharge =(BigDecimal)h.get("CZJE");
                    BigDecimal cash = (BigDecimal)h.get("TXJE");
                    BigDecimal invest = (BigDecimal)h.get("TZJE");
                    BigDecimal investPhase = (BigDecimal)h.get("HKJE");
                    StatisticsBalance statisticsBalance=new StatisticsBalance();
                    statisticsBalance.setDateTime(dateTime);
                    statisticsBalance.setTodayFirstInvest(todayFirstInvest);
                    statisticsBalance.setAvailableBalance(availableBalance);
                    statisticsBalance.setStand(statisticsBalanceSchedulerService.findStand(sdf.format(dateTime)));
                    statisticsBalance.setRecharge(recharge);
                    statisticsBalance.setCash(cash);
                    statisticsBalance.setInvest(invest);
                    statisticsBalance.setInvestPhase(investPhase);
                    statisticsBalance.setCreatTime(new Date());
                    statisticsBalance.setWeekCycle(DateFormatUtil.getWEEK(dateTime));
                    statisticsBalanceSchedulerService.saveStatisticsBalance(statisticsBalance);
                }
            }
      }else{
            String date = null;
            if(s != null && !"".equals(s)){
                date=sdf.format(sdf.parse(s));
            }else{
                date=sdf.format(DateFormatUtil.getNextDay(new Date()));
            }
          //首投金额
          BigDecimal todayFirstInvest=statisticsBalanceSchedulerService.findFinances("shoutou",date);
          XxlJobLogger.log("=========首投金额为:"+todayFirstInvest);
          //存量金额
          BigDecimal availableBalance=statisticsBalanceSchedulerService.findFinances("cunliang",date);
          XxlJobLogger.log("=========存量金额为:"+availableBalance);
          //站岗资金
          BigDecimal stand=statisticsBalanceSchedulerService.findFinances("stand",date);
          XxlJobLogger.log("=========站岗资金为:"+stand);
          //充值金额
          BigDecimal recharge=statisticsBalanceSchedulerService.findFinances("recharge",date);
          XxlJobLogger.log("=========充值金额为:"+recharge);
          //提现金额
          BigDecimal cash=statisticsBalanceSchedulerService.findFinances("cash",date);
          XxlJobLogger.log("=========提现金额为:"+cash);
          //投资金额
          BigDecimal invest=statisticsBalanceSchedulerService.findFinances("invest",date);
          XxlJobLogger.log("=========投资金额为:"+invest);
          //回款金额
          BigDecimal investPhase=statisticsBalanceSchedulerService.findFinances("investPhase",date);
          XxlJobLogger.log("=========回款金额为:"+investPhase);
          StatisticsBalance statisticsBalance=new StatisticsBalance();
          statisticsBalance.setDateTime(sdf.parse(date));
          statisticsBalance.setTodayFirstInvest(todayFirstInvest);
          statisticsBalance.setAvailableBalance(availableBalance);
          statisticsBalance.setStand(stand);
          statisticsBalance.setRecharge(recharge);
          statisticsBalance.setCash(cash);
          statisticsBalance.setInvest(invest);
          statisticsBalance.setInvestPhase(investPhase);
          statisticsBalance.setCreatTime(new Date());
          statisticsBalance.setWeekCycle(DateFormatUtil.getWEEK(sdf.parse(date)));
          statisticsBalanceSchedulerService.saveStatisticsBalance(statisticsBalance);
      }
        long end1 = System.currentTimeMillis();//获取当前时间
        XxlJobLogger.log("========统计所有用户金额情况定时器执行结束时间为:"+end1+"==========");
        XxlJobLogger.log("=========每天定时统计所有用户金额情况定时器执行结束！==============");
        return SUCCESS;

    }

}
