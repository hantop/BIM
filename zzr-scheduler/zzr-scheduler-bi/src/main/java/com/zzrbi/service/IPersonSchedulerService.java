package com.zzrbi.service;


import com.zzrbi.entity.Person;

import java.util.List;
import java.util.Map;

/**
 * Desc: 统计所有用户情况的接口
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 13:51
 */
public interface IPersonSchedulerService {

    /**
     * Desc: 统计所有用户情况
     * user: yinlonglong
     * Date: 2018/1/29 14:32
     * @Param registerCount
     * @Return: int
     */

    Map<String,Object> findPerson(String type,String date);
    /**
     * Desc: 保存所有统计的数据
     * user: yinlonglong
     * Date: 2018/1/29 14:33
     * @Param person
     * @Return: int
     */
    int savePerson(Person person);
   /**
    * Desc: 统计用户情况历史数据
    * user: yinlonglong
    * Date: 2018/1/31 11:28
    * @Param
    * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    List<Map<String,Object>> queryPersonList();
}
