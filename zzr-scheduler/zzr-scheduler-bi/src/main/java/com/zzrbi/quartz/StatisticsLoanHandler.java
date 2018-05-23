package com.zzrbi.quartz;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.zzrbi.entity.StatisticsLoan;
import com.zzrbi.service.IStatisticsLoanSchedulerService;
import com.zzrbi.util.DateFormatUtil;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Desc: 统计项目的情况
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 11:37
 */
@JobHandler(value="statisticsLoanJobHandler")
@Component
public class StatisticsLoanHandler extends IJobHandler {
    @Resource
    private IStatisticsLoanSchedulerService statisticsLoanSchedulerService;

    public ReturnT<String> execute(String s) throws Exception {

        XxlJobLogger.log("=============每天定时统计项目的情况定时器执行开始！================");
        long startTime1 = System.currentTimeMillis();//获取当前时间
        XxlJobLogger.log("========统计统计项目的情况定时器执行开始时间为:"+startTime1+"==========");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(s.equals("old")){
            List<Map<String, Object>> statisticsLoanList =statisticsLoanSchedulerService.queryStatisticsLoanList();
            XxlJobLogger.log("=========统计历史项目情况的数据为:"+statisticsLoanList+"===========");
            XxlJobLogger.log("=========统计历史项目情况数据的条数为:"+statisticsLoanList.size()+"===========");
            if(statisticsLoanList!=null) {
                for (Map<String, Object> map : statisticsLoanList) {
                         Date dateTime = sdf.parse(map.get("investTime").toString());
                         int newLoanPeople = Integer.valueOf( map.get("newLoanPeople").toString());
                         BigDecimal newLoanMoney = (BigDecimal)map.get("newLoanMoney");
                         int newLoanCount = Integer.valueOf( map.get("newLoanCount").toString());
                         int People30 = Integer.valueOf( map.get("People30").toString());
                         BigDecimal Money30 = (BigDecimal)map.get("Money30");
                         int Count30 = Integer.valueOf( map.get("Count30").toString());
                         int People60 = Integer.valueOf( map.get("People60").toString());
                         BigDecimal Money60 = (BigDecimal)map.get("Money60");
                         int Count60 = Integer.valueOf( map.get("Count60").toString());
                         int People90 = Integer.valueOf( map.get("People90").toString());
                         BigDecimal Money90 = (BigDecimal)map.get("Money90");
                         int Count90 = Integer.valueOf( map.get("Count90").toString());
                         int People180 = Integer.valueOf( map.get("People180").toString());
                         BigDecimal Money180 = (BigDecimal)map.get("Money180");
                         int Count180 = Integer.valueOf( map.get("Count180").toString());
                         int People360 = Integer.valueOf( map.get("People360").toString());
                         BigDecimal Money360 = (BigDecimal)map.get("Money360");
                         int Count360 = Integer.valueOf( map.get("Count360").toString());
                         StatisticsLoan statisticsLoan=new StatisticsLoan();
                            statisticsLoan.setDateTime(dateTime);
                            statisticsLoan.setNewLoanPeople(newLoanPeople);
                            statisticsLoan.setNewLoanMoney(newLoanMoney);
                            statisticsLoan.setNewLoanCount(newLoanCount);
                            statisticsLoan.setPeople30(People30);
                            statisticsLoan.setMoney30(Money30);
                            statisticsLoan.setCount30(Count30);
                            statisticsLoan.setPeople60(People60);
                            statisticsLoan.setMoney60(Money60);
                            statisticsLoan.setCount60(Count60);
                            statisticsLoan.setPeople90(People90);
                            statisticsLoan.setMoney90(Money90);
                            statisticsLoan.setCount90(Count90);
                            statisticsLoan.setPeople180(People180);
                            statisticsLoan.setMoney180(Money180);
                            statisticsLoan.setCount180(Count180);
                            statisticsLoan.setPeople360(People360);
                            statisticsLoan.setMoney360(Money360);
                            statisticsLoan.setCount360(Count360);
                            statisticsLoan.setCreatTime(new Date());
                            statisticsLoan.setWeekCycle(DateFormatUtil.getWEEK(dateTime));
                        statisticsLoanSchedulerService.saveStatisticsLoan(statisticsLoan);
                }
            }
        }else{
            String date = null;
            if(s != null && !"".equals(s)){
                date=sdf.format(sdf.parse(s));
            }else{
                date=sdf.format(DateFormatUtil.getNextDay(new Date()));
            }
            //新手项目
            Map<String, Object> mapNew=statisticsLoanSchedulerService.findStatisticsLoan("new",date);
            int newLoanPeople = 0;
            BigDecimal newLoanMoney = new BigDecimal(0.00);
            int newLoanCount = 0;
            if(mapNew!=null){
                if(mapNew.get("newLoanPeople")!=null){
                    newLoanPeople=Integer.valueOf(mapNew.get("newLoanPeople").toString());
                }
                if(mapNew.get("newLoanMoney")!=null){
                    newLoanMoney=(BigDecimal)mapNew.get("newLoanMoney");
                }
                if(mapNew.get("newLoanCount")!=null){
                    newLoanCount =Integer.valueOf(mapNew.get("newLoanCount").toString());
                }
            }
            //30天项目
            Map<String, Object> map3=statisticsLoanSchedulerService.findStatisticsLoan("30",date);
            int People30 = 0;
            BigDecimal Money30 = new BigDecimal(0.00);
            int Count30 = 0;
            if(map3!=null){
                if(map3.get("People30")!=null){
                    People30 =Integer.valueOf(map3.get("People30").toString());
                }
                if(map3.get("Money30")!=null){
                    Money30=(BigDecimal)map3.get("Money30");
                }
                if(map3.get("Count30")!=null){
                    Count30=Integer.valueOf(map3.get("Count30").toString());
                }
            }
            //60天项目
            Map<String, Object> map6=statisticsLoanSchedulerService.findStatisticsLoan("60",date);
            int People60 = 0;
            BigDecimal Money60 = new BigDecimal(0.00);
            int Count60 = 0;
            if(map6!=null){
                if(map6.get("People60")!=null){
                    People60=Integer.valueOf(map6.get("People60").toString());
                }
                if(map6.get("Money60")!=null){
                    Money60=(BigDecimal)map6.get("Money60");
                }
                if(map6.get("Count60")!=null){
                    Count60=Integer.valueOf(map6.get("Count60").toString());
                }
            }
            //90天项目
            Map<String, Object> map9=statisticsLoanSchedulerService.findStatisticsLoan("90",date);
            int People90 = 0;
            BigDecimal Money90 = new BigDecimal(0.00);
            int Count90 = 0;
            if(map9!=null){
                if(map9.get("People90")!=null){
                    People90=Integer.valueOf(map9.get("People90").toString());
                }
                if(map9.get("Money90")!=null){
                    Money90 =(BigDecimal)map9.get("Money90");
                }
                if(map9.get("Count90")!=null){
                    Count90 =Integer.valueOf(map9.get("Count90").toString());
                }
            }
            //180天项目
            Map<String, Object> map18=statisticsLoanSchedulerService.findStatisticsLoan("180",date);
            int People180 = 0;
            BigDecimal Money180 = new BigDecimal(0.00);
            int Count180 = 0;
            if(map18!=null){
                if(map18.get("People180")!=null){
                    People180=Integer.valueOf(map18.get("People180").toString());
                }
                if(map18.get("Money180")!=null){
                    Money180=(BigDecimal)map18.get("Money180");
                }
                if(map18.get("Count180")!=null){
                    Count180 =Integer.valueOf(map18.get("Count180").toString());
                }
            }
            //360天项目
            Map<String, Object> map36=statisticsLoanSchedulerService.findStatisticsLoan("360",date);
            int People360 = 0;
            BigDecimal Money360 = new BigDecimal(0.00);
            int Count360 = 0;
            if(map36!=null){
                if(map36.get("People360")!=null){
                    People360=Integer.valueOf(map36.get("People360").toString());
                }
                if(map36.get("Money360")!=null){
                    Money360=(BigDecimal)map36.get("Money360");
                }
                if(map36.get("Count360")!=null){
                    Count360 =Integer.valueOf(map36.get("Count360").toString());
                }
            }
            StatisticsLoan statisticsLoan=new StatisticsLoan();
            statisticsLoan.setDateTime(sdf.parse(date));
            statisticsLoan.setNewLoanPeople(newLoanPeople);
            statisticsLoan.setNewLoanMoney(newLoanMoney);
            statisticsLoan.setNewLoanCount(newLoanCount);
            statisticsLoan.setPeople30(People30);
            statisticsLoan.setMoney30(Money30);
            statisticsLoan.setCount30(Count30);
            statisticsLoan.setPeople60(People60);
            statisticsLoan.setMoney60(Money60);
            statisticsLoan.setCount60(Count60);
            statisticsLoan.setPeople90(People90);
            statisticsLoan.setMoney90(Money90);
            statisticsLoan.setCount90(Count90);
            statisticsLoan.setPeople180(People180);
            statisticsLoan.setMoney180(Money180);
            statisticsLoan.setCount180(Count180);
            statisticsLoan.setPeople360(People360);
            statisticsLoan.setMoney360(Money360);
            statisticsLoan.setCount360(Count360);
            statisticsLoan.setCreatTime(new Date());
            statisticsLoan.setWeekCycle(DateFormatUtil.getWEEK(sdf.parse(date)));
            statisticsLoanSchedulerService.saveStatisticsLoan(statisticsLoan);
        }
        long end1 = System.currentTimeMillis();//获取当前时间
        XxlJobLogger.log("========统计项目的情况定时器执行结束时间为:"+end1+"==========");
        XxlJobLogger.log("=========每天定时统计项目的情况定时器执行结束！==============");
        return SUCCESS;

    }

}
