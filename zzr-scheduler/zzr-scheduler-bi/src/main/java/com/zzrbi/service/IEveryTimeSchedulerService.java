package com.zzrbi.service;


import com.zzrbi.entity.EveryTime;

import java.util.Map;

/**
 * Desc: 统计用户时时情况的接口
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 13:50
 */
public interface IEveryTimeSchedulerService {

    /**
     * Desc:  统计前一个小时用户的情况
     * user: yinlonglong
     * Date: 2018/2/6 17:16
     * @Param type
     * @Param date
     * @Return: Map<String,Object>
     */
    Map<String,Object> findEveryTime(String type, String date);
    /**
     * Desc: 保存统计前一个小时用户的情况
     * user: yinlonglong
     * Date: 2018/2/2 11:12
     * @Param everyTime
     * @Return: void
     */
    int saveEveryTime(EveryTime everyTime);
}
