package com.zzrbi.service;


import com.zzrbi.entity.StatisticsLoan;

import java.util.List;
import java.util.Map;

/**
 * Desc: 统计项目情况的接口
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 13:56
 */
public interface IStatisticsLoanSchedulerService {

    /**
     * Desc: 统计项目情况的历史数据
     * user: yinlonglong
     * Date: 2018/2/2 17:42
     * @Param
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    List<Map<String,Object>> queryStatisticsLoanList();
    /**
     * Desc: 保存统计项目情况
     * user: yinlonglong
     * Date: 2018/2/2 17:42
     * @Param statisticsLoan
     * @Return: void
     */
    int saveStatisticsLoan(StatisticsLoan statisticsLoan);
    /**
     * Desc: 统计项目情况
     * user: yinlonglong
     * Date: 2018/2/2 17:42
     * @Param type
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> findStatisticsLoan(String type,String date);
}
