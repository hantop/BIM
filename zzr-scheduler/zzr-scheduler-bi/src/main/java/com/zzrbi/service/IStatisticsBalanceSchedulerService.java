package com.zzrbi.service;


import com.zzrbi.entity.StatisticsBalance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Desc:  统计所有用户资金情况的接口
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 13:51
 */
public interface IStatisticsBalanceSchedulerService {

    /**
     * Desc: 统计所有用户资金情况
     * user: yinlonglong
     * Date: 2018/1/26 15:25
     * @Param shoutou
     * @Return: java.math.BigDecimal
     */
    BigDecimal findFinances(String type,String date);
    /**
     * Desc: 保存所有统计的数据
     * user: yinlonglong
     * Date: 2018/1/26 15:26
     * @Param statisticsBalance
     * @Return: void
     */
    int saveStatisticsBalance(StatisticsBalance statisticsBalance);
    /**
     * Desc: 统计用户资金历史数据
     * user: yinlonglong
     * Date: 2018/1/30 10:34
     * @Param
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> queryStatisticsBalanceList();
    /**
     * Desc: 根据时间查询历史的站岗资金
     * user: yinlonglong
     * Date: 2018/2/1 14:48
     * @Param dateTime
     * @Return: java.math.BigDecimal
     */
    BigDecimal findStand(String dateTime);
}
