package com.zzrbi.util;

import org.apache.log4j.Logger;

import java.util.Random;

/**
 * 动态上下文切换
 * 
 * @author sihang
 *
 */
public class DynamicContextChange {
	
	private static Logger logger = Logger.getLogger(DynamicContextChange.class);
	
	private static final ThreadLocal<String> localContext = new ThreadLocal<String>();
	
	public static int dataSourceMasterSize;
	
    public static int dataSourceSlaveSize;
	
	private static Random random = new Random();
	
	/**
	 * 获取数据源类型
	 * @return
	 */
	public static String getDataSourceType() {
		if (null == localContext.get()) {
			DynamicContextChange.setDataSourceTypeSlave();
		}
		logger.info("DataSource ================>>>>>>>>>>" + localContext.get());
		return localContext.get();
	}
	
	/**
	 * 设置数据源类型为主库
	 */
	public static void setDataSourceTypeMaster() {
		logger.info("================>>>>>>>>>>主库 =   MASTER" );
		localContext.set("MASTER");
	}

	/**
	 * 设置数据源类型为从库
	 */
	public static void setDataSourceTypeSlave() {
		int randomSlave = random.nextInt(dataSourceSlaveSize);
		logger.info("================>>>>>>>>>>randomSlave = " + randomSlave + "  SLAVE" );
		localContext.set("SLAVE");
	}

	/**
	 * 清除客户端数据源类型
	 */
	public static void clearCustomerType() {
		logger.info("DataSource ================>>>>>>>>>>" + localContext.get());
		localContext.remove();
	}

}