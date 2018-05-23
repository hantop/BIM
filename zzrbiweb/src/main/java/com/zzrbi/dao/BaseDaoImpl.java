package com.zzrbi.dao;


import java.beans.PropertyDescriptor;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.zzrbi.util.BusinessException;
import com.zzrbi.util.CustomizedPropertyPlaceholderConfigurer;
import com.zzrbi.util.DateFormatUtil;
import com.zzrbi.util.SQLHelper;





@Repository
public class BaseDaoImpl implements IBaseDao{
	
private Logger logger = Logger.getLogger(BaseDaoImpl.class);
	
	@Resource
    public JdbcTemplate jdbcTemplate;
	
	@Resource
	private CustomizedPropertyPlaceholderConfigurer propertyPlaceholderConfigurer;

	@Override
	public String sqlMap(String key) {
		try {
            return propertyPlaceholderConfigurer.getContextProperty(key).toString();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return "";
        }
	}
	

	@Override
	public Map<String, Object> queryForListPage(final String sql, final int pageSize, final int startIndex) throws BusinessException {
		
		try {
			return this.jdbcTemplate.execute(sql, new PreparedStatementCallback<Map<String, Object>>() {
				@Override
				public Map<String, Object> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					logger.info(sql);
					
					ResultSet resultSet = ps.executeQuery();
					resultSet.last(); //移到最后一行
					int rowCount = resultSet.getRow();//得到当前行号，也就是记录数
					resultSet.beforeFirst();
					
					if(startIndex>0){
						resultSet.absolute(startIndex);
					}
					
					int num = 0; 
					List<Map<String,Object>> rowList = new ArrayList<Map<String,Object>>();
					while(resultSet.next()){
						num++;
						if(num > pageSize){
							 break;
						}
						rowList.add(SQLHelper.getResultMap(resultSet));
					}
					
					Map<String,Object> resultMap = new HashMap<String,Object>();
					resultMap.put("total", rowCount); // 总条数
					resultMap.put("rows", rowList); // 集合列表
					resultMap.put("totalPage", (rowCount+pageSize-1)/pageSize); // 总页数
					resultMap.put("startIndex", startIndex); // 当前页码
					resultMap.put("pageSize", pageSize); // 每页显示条数
					return resultMap;
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new BusinessException("SQL_01","系统错误，请联系系统管理员");
		}
	}
	/**
     * 新增一条记录且返回主键Id
     * 
     * @param sql
     *            新增待执行sql
     * @param param
     *            参数
     * @return 主键ID
     */
	@Override
	public int insertSqlAndReturnKeyId(String sql, Object[] param) {
        final String innersql = sql;
        final Object[] innerO = param;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(final Connection con)
                        throws SQLException {
                    PreparedStatement ps = con.prepareStatement(innersql,
                            Statement.RETURN_GENERATED_KEYS);
                    for (int i = 0; i < innerO.length; i++) {
                        ps.setObject(i + 1, innerO[i]);
                    }

                    return ps;
                }
            }, keyHolder);
        } catch (DataAccessException e) {

            e.printStackTrace();
        }
        return keyHolder.getKey().intValue();
    }
	/**
     * 返回clazz类型的对象
     * 
     * @param sql
     * @param args
     * @param clazz
     * @return
     */
	protected <T> T queryForObject(final String sql, final Object[] args, final Class<T> clazz) {
		try {

			List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, args);
			if (list != null && list.size() > 0) {
				Map<String, Object> data = list.get(0);
				if (data != null) {
					T retObj = this.mapObject(data, clazz);
					return retObj;
				}
			}
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	 /**
     * 返回clazz类型的实例，数据来源data，data中的key名称要与clazz的成员变量名称一致
     * 
     * @param clazz
     * @return
     * @throws
     * @throws Exception
     * @paramsql
     * @paramargs
     */
    private <T> T mapObject(final Map<String, Object> data, final Class<T> clazz)
            throws Exception {
        // 获取所有成员变量
        Field[] fields = clazz.getDeclaredFields();
        T distObj = clazz.newInstance();
        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = data.get(fieldName);

            // 如果存在值，则进行赋值
            if (fieldValue != null) {
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, clazz);
                Method setMethod = pd.getWriteMethod();
                // 获取字段类型
                Class type = field.getType();
                if (Integer.class.equals(type)) {
                    // Integer
                    Integer integerVal = Integer.valueOf(fieldValue.toString());
                    setMethod.invoke(distObj, integerVal);
                } else if (Date.class.equals(type)) {
                    DateFormatUtil format = DateFormatUtil.getInstance("yyyy-MM-dd HH:mm:ss");
                    // Date
                    setMethod.invoke(distObj, format.parse(fieldValue.toString()));
                }/*
                  * else if (DateTime.class.equals(type)) { // Date setMethod
                  * .invoke(distObj, DateUtil.parseDate(fieldValue.toString()));
                  * }
                  */else if (Short.class.equals(type)) {
                    // Short
                    if (fieldValue.getClass().equals(Boolean.class)) {
                        if (fieldValue.equals(Boolean.TRUE)) {
                            fieldValue = new Integer(1);
                        }
                        if (fieldValue.equals(Boolean.FALSE)) {
                            fieldValue = new Integer(0);
                        }
                    }
                    Short shortVal = Short.valueOf(fieldValue.toString());
                    setMethod.invoke(distObj, shortVal);
                } else if (String.class.equals(type)) {
                    String fieldValueStr = String.valueOf(fieldValue);
                    setMethod.invoke(distObj, fieldValueStr);
                } else {
                    setMethod.invoke(distObj, fieldValue);
                }
            }
        }
        return distObj;
    }
    
    /**
     * 通过list获取map，避免产生不必要的异常
     *
     * @return
     */
    public Map<String, Object> queryForMapNoException(final String sql,
                                                      final Object[] args) {
        Map<String, Object> data = null;
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql, args);
        if (list != null && list.size() > 0) {
            data = list.get(0);
        }
        return data;
    }
}
