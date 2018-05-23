package com.zzrbi.dao;

import java.util.Map;

/**
 * 通用DAO
 * 
 * @author T440
 *
 */
public abstract interface IBaseDao {
	
	/**
     * 通过sql map key 获得sql语句
     *
     * @param key
     * @return
     */
     public String sqlMap(String key);
     /**
      * 新增一条记录且返回主键Id
      * 
      * @param sql
      *            新增待执行sql
      * @param param
      *            参数
      * @return 主键ID
      */
     public int insertSqlAndReturnKeyId(String sql, Object[] param);
     /**
      * 
      * @param sql
      * @param pageSize
      * @param startIndex
      * @return
      * @throws Exception
      */
     public Map<String, Object> queryForListPage(String sql, int pageSize, int startIndex) throws Exception;

}
