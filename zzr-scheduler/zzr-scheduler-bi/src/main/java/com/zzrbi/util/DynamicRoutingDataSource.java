package com.zzrbi.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源动态路由
 * @author sihang
 *
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
	
	private int dataSourceMasterSize;
	private int dataSourceSlaveSize;
	
	/**
	 * 检测当前参照的key
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicContextChange.getDataSourceType();
	}
	
	public void init(){
		DynamicContextChange.dataSourceMasterSize = dataSourceMasterSize;
		DynamicContextChange.dataSourceSlaveSize = dataSourceSlaveSize;
	}
	
	public int getDataSourceMasterSize() {
		return dataSourceMasterSize;
	}

	public void setDataSourceMasterSize(int dataSourceMasterSize) {
		this.dataSourceMasterSize = dataSourceMasterSize;
	}

	public int getDataSourceSlaveSize() {
		return dataSourceSlaveSize;
	}

	public void setDataSourceSlaveSize(int dataSourceSlaveSize) {
		this.dataSourceSlaveSize = dataSourceSlaveSize;
	}

}
