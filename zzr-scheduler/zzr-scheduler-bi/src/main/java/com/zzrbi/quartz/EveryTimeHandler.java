package com.zzrbi.quartz;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.zzrbi.entity.EveryTime;
import com.zzrbi.service.IEveryTimeSchedulerService;
import com.zzrbi.util.DateFormatUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Desc: 统计用户时时的情况
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 11:37
 */
@JobHandler(value="everyTimeJobHandler")
@Component
public class EveryTimeHandler extends IJobHandler {
    @Resource
    private IEveryTimeSchedulerService everyTimeSchedulerService;

    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log("===============每小时定时统计统计用户时时的情况定时器执行开始！================");
        long startTime1 = System.currentTimeMillis();//获取当前时间
        XxlJobLogger.log("=========统计用户时时的情况定时器执行开始时间为:"+startTime1+"==========");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;
        String date1 = null;
         if(s != null && !"".equals(s)){
             date=sdf.format(sdf.parse(s));
             date1=sdf1.format(sdf1.parse(s));
         }else{
             date=sdf.format(DateFormatUtil.getBeforeHourTime(new Date()));
             date1=sdf1.format(new Date());
         }
        //注册人数
        Map<String, Object> registerCountMap=everyTimeSchedulerService.findEveryTime("registerCount",date);
        int registerCount=0;
        if(registerCountMap!=null){
            if(registerCountMap.get("registerCount")!=null){
                registerCount=Integer.valueOf(registerCountMap.get("registerCount").toString());
            }
        }
        XxlJobLogger.log("==========注册人数为:"+registerCount);
        //投资人数、投资金额
        Map<String, Object> investMap=everyTimeSchedulerService.findEveryTime("invest",date);
        int investCount=0;
        BigDecimal investMoney=new BigDecimal(0.00);
        if(investMap!=null){
            if(investMap.get("investCount")!=null){
                investCount=Integer.valueOf(investMap.get("investCount").toString());
            }
            if(investMap.get("investMoney")!=null){
                investMoney=(BigDecimal)investMap.get("investMoney");
            }
        }
        XxlJobLogger.log("==========投资人数为:"+investCount);
        XxlJobLogger.log("==========投资金额为:"+investMoney);
        //首投人数
        Map<String, Object> firstInvestCountMap=everyTimeSchedulerService.findEveryTime("firstInvestCount",date);
        int firstInvestCount=0;
        if(firstInvestCountMap!=null){
            if(firstInvestCountMap.get("firstInvestCount")!=null){
                firstInvestCount=Integer.valueOf(firstInvestCountMap.get("firstInvestCount").toString());
            }
        }
        XxlJobLogger.log("==========首投人数为:"+firstInvestCount);
        //存量金额
        Map<String, Object> availableBalanceMap=everyTimeSchedulerService.findEveryTime("availableBalance",date);
        BigDecimal availableBalance=new BigDecimal(0.00);
        if(availableBalanceMap!=null){
            if(availableBalanceMap.get("availableBalance")!=null){
                availableBalance=(BigDecimal)availableBalanceMap.get("availableBalance");
            }
        }
        XxlJobLogger.log("==========存量金额为:"+availableBalance);
        //站岗资金
        Map<String, Object> standMap=everyTimeSchedulerService.findEveryTime("stand",date);
        BigDecimal stand=new BigDecimal(0.00);
        if(standMap!=null){
            if(standMap.get("stand")!=null){
                stand=(BigDecimal)standMap.get("stand");
            }
        }
        XxlJobLogger.log("==========站岗资金为:"+stand);
        //充值金额
        Map<String, Object> rechargeMap=everyTimeSchedulerService.findEveryTime("recharge",date);
        BigDecimal recharge=new BigDecimal(0.00);
        if(rechargeMap!=null){
            if(rechargeMap.get("recharge")!=null){
                recharge=(BigDecimal)rechargeMap.get("recharge");
            }
        }
        XxlJobLogger.log("==========充值金额为:"+recharge);
        //提现金额
        Map<String, Object> cashMap=everyTimeSchedulerService.findEveryTime("cash",date);
        BigDecimal cash=new BigDecimal(0.00);
        if(cashMap!=null){
            if(cashMap.get("cash")!=null){
                cash=(BigDecimal)cashMap.get("cash");
            }
        }
        XxlJobLogger.log("==========提现金额为:"+cash);
        //回款金额
        Map<String, Object> investPhaseMap=everyTimeSchedulerService.findEveryTime("investPhase",date1);
        BigDecimal investPhase=new BigDecimal(0.00);
        if(investPhaseMap!=null){
            if(investPhaseMap.get("investPhase")!=null){
                investPhase=(BigDecimal)investPhaseMap.get("investPhase");
            }
        }
        XxlJobLogger.log("==========回款金额为:"+investPhase);

        EveryTime everyTime= new EveryTime();
        everyTime.setDateTime(sdf.parse(date));
        everyTime.setRegisterCount(registerCount);
        everyTime.setInvestCount(investCount);
        everyTime.setFirstInvestCount(firstInvestCount);
        everyTime.setAvailableBalance(availableBalance);
        everyTime.setStand(stand);
        everyTime.setRecharge(recharge);
        everyTime.setCash(cash);
        everyTime.setInvestMoney(investMoney);
        everyTime.setInvestPhase(investPhase);
        everyTime.setCreatTime(new Date());
        everyTimeSchedulerService.saveEveryTime(everyTime);
        long end1 = System.currentTimeMillis();//获取当前时间
        XxlJobLogger.log("========统计用户时时的情况定时器执行结束时间为:"+end1+"==========");
        XxlJobLogger.log("=========每小时定时统计用户时时的情况定时器执行结束！==============");
        return SUCCESS;

    }

}
