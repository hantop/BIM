package com.zzrbi.quartz;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.zzrbi.entity.Person;
import com.zzrbi.service.IPersonSchedulerService;
import com.zzrbi.util.DateFormatUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Desc: 统计所有用户的情况
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 11:37
 */
@JobHandler(value="personJobHandler")
@Component
public class PersonHandler extends IJobHandler {
    @Resource
    private IPersonSchedulerService personSchedulerService;

    public ReturnT<String> execute(String s) throws Exception {

        XxlJobLogger.log("=============每天定时统计用户情况定时器执行开始！================");
        long startTime1 = System.currentTimeMillis();//获取当前时间
        XxlJobLogger.log("==========统计用户情况定时器执行开始时间为:"+startTime1+"============");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        if(s.equals("old")){
            List<Map<String, Object>> personList =personSchedulerService.queryPersonList();
            XxlJobLogger.log("=========统计历史用户情况的数据为:"+personList+"===========");
            XxlJobLogger.log("=========统计历史用户情况数据的条数为:"+personList.size()+"===========");
            if(personList!=null) {
                for (Map<String, Object> map : personList) {
                    Date dateTime = sdf.parse(map.get("registerTime").toString());
                    int registerCount= Integer.valueOf( map.get("ZCRS").toString());
                    int todayRegisterAccount=Integer.valueOf(map.get("DRZRKH").toString());
                    int beforRegisterAccount=Integer.valueOf(map.get("WRZCKH").toString());
                    int investPerson=Integer.valueOf(map.get("TZRS").toString());
                    int todayRegisterInvest=Integer.valueOf(map.get("DRSTRS").toString());
                    int beforRegisterInvest=Integer.valueOf(map.get("WRSTRS").toString());
                    int repeatInvest=Integer.valueOf(map.get("FTRS").toString());
                    int investCount=Integer.valueOf(map.get("TZBS").toString());
                    Person person=new Person();
                    person.setDateTime(dateTime);
                    person.setRegisterCount(registerCount);
                    person.setTodayRegisterAccount(todayRegisterAccount);
                    person.setBeforRegisterAccount(beforRegisterAccount);
                    person.setInvestPerson(investPerson);
                    person.setTodayRegisterInvest(todayRegisterInvest);
                    person.setBeforRegisterInvest(beforRegisterInvest);
                    person.setRepeatInvest(repeatInvest);
                    person.setInvestCount(investCount);
                    person.setCreatTime(new Date());
                    person.setWeekCycle(DateFormatUtil.getWEEK(dateTime));
                    personSchedulerService.savePerson(person);
                }
            }
        }else{
            String date = null;
            if(s != null && !"".equals(s)){
                date=sdf.format(sdf.parse(s));
            }else{
                date=sdf.format(DateFormatUtil.getNextDay(new Date()));
            }
            //注册人数
            Map<String, Object> registerCountMap=personSchedulerService.findPerson("registerCount",date);
            int registerCount=0;
            if(registerCountMap!=null){
                if(registerCountMap.get("registerCount")!=null){
                    registerCount=Integer.valueOf(registerCountMap.get("registerCount").toString());
                }
            }
            XxlJobLogger.log("==========注册人数为:"+registerCount);
            //当天注册且开户人数
            Map<String, Object> todayRegisterAccountMap =personSchedulerService.findPerson("todayRegisterAccount",date);
            int todayRegisterAccount=0;
            if(todayRegisterAccountMap!=null){
                if(todayRegisterAccountMap.get("todayRegisterAccount")!=null){
                    todayRegisterAccount=Integer.valueOf(todayRegisterAccountMap.get("todayRegisterAccount").toString());
                }
            }
            XxlJobLogger.log("==========当天注册且开户人数为:"+todayRegisterAccount);
            //往日注册当天开户人数
            Map<String, Object> beforRegisterAccountMap=personSchedulerService.findPerson("beforRegisterAccount",date);
            int beforRegisterAccount=0;
            if(beforRegisterAccountMap!=null){
                if(beforRegisterAccountMap.get("beforRegisterAccount")!=null){
                    beforRegisterAccount=Integer.valueOf(beforRegisterAccountMap.get("beforRegisterAccount").toString());
                }
            }
            XxlJobLogger.log("==========往日注册当天开户人数为:"+beforRegisterAccount);
            //投资人数
            Map<String, Object> investPersonMap=personSchedulerService.findPerson("investPerson",date);
            int investPerson=0;
            if(investPersonMap!=null){
                if(investPersonMap.get("investPerson")!=null){
                    investPerson=Integer.valueOf(investPersonMap.get("investPerson").toString());
                }
            }
            XxlJobLogger.log("==========投资人数为:"+investPerson);
            //当天注册且当天首投投资人数
            Map<String, Object> todayRegisterInvestMap=personSchedulerService.findPerson("todayRegisterInvest",date);
            int todayRegisterInvest=0;
            if(todayRegisterInvestMap!=null){
                if(todayRegisterInvestMap.get("todayRegisterInvest")!=null){
                    todayRegisterInvest=Integer.valueOf(todayRegisterInvestMap.get("todayRegisterInvest").toString());
                }
            }
            XxlJobLogger.log("==========当天注册且当天首投投资人数为:"+todayRegisterInvest);
            //往日注册且当天首投投资人数
            Map<String, Object> beforRegisterInvestMap=personSchedulerService.findPerson("beforRegisterInvest",date);
            int beforRegisterInvest=0;
            if(beforRegisterInvestMap!=null){
                if(beforRegisterInvestMap.get("beforRegisterInvest")!=null){
                    beforRegisterInvest=Integer.valueOf(beforRegisterInvestMap.get("beforRegisterInvest").toString());
                }
            }
            XxlJobLogger.log("==========往日注册且当天首投投资人数为:"+beforRegisterInvest);
            //复投人数
            Map<String, Object> repeatInvestMap=personSchedulerService.findPerson("repeatInvest",date);
            int repeatInvest=0;
            if(repeatInvestMap!=null){
                if(repeatInvestMap.get("repeatInvest")!=null){
                    repeatInvest=Integer.valueOf(repeatInvestMap.get("repeatInvest").toString());
                }
            }
            XxlJobLogger.log("==========复投人数为:"+repeatInvest);
            //投资笔数
            Map<String, Object> investCountMap=personSchedulerService.findPerson("investCount",date);
            int investCount=0;
            if(investCountMap!=null){
                if(investCountMap.get("investCount")!=null){
                    investCount=Integer.valueOf(investCountMap.get("investCount").toString());
                }
            }
            XxlJobLogger.log("==========投资笔数为:"+investCount);
           //添加信息到person
            Person person=new Person();
            person.setDateTime(sdf.parse(date));
            person.setRegisterCount(registerCount);
            person.setTodayRegisterAccount(todayRegisterAccount);
            person.setBeforRegisterAccount(beforRegisterAccount);
            person.setInvestPerson(investPerson);
            person.setTodayRegisterInvest(todayRegisterInvest);
            person.setBeforRegisterInvest(beforRegisterInvest);
            person.setRepeatInvest(repeatInvest);
            person.setInvestCount(investCount);
            person.setCreatTime(new Date());
            person.setWeekCycle(DateFormatUtil.getWEEK(sdf.parse(date)));
            personSchedulerService.savePerson(person);
        }
        long end1 = System.currentTimeMillis();//获取当前时间
        XxlJobLogger.log("========统计用户情况定时器执行结束时间为:"+end1+"==========");
        XxlJobLogger.log("=========每天定时统计用户情况定时器执行结束！==============");
        return SUCCESS;

    }

}
