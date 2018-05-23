package com.zzrbi.entity;

import java.io.Serializable;

/**
 * Directory实体
 * @author yinlonglong
 * @date 2017年3月21日 下午2:53:33
 */
public class Directory implements Serializable {

	private static final long serialVersionUID = 2963082437254472711L;

	/**
	 * 目录id
	 */
	private int id;
	
	/**
	 * 目录名称
	 */
	private String directoryName;
	
	/**
	 * 目录编码
	 */
	private String directoryCode;
	
	/**
	 * 目录优先级
	 */
	private Integer directoryPriority;
	
	/**
	 * 0 功能导航 1 功能菜单  3 文件夹  4 按钮
	 */
	private int directoryType;
	
	/**
	 * 目录图标
	 */
	private String directoryIcon;
	
	/**
	 * 目录父导航
	 */
	private Integer parentNavigator;
	
	/**
	 * 目录控制台url
	 */
	private String consoleUrl;

	
	
	/**
	 * 目录id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 目录id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 目录名称
	 */
	public String getDirectoryName() {
		return directoryName;
	}
	/**
	 * 目录名称
	 */
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName == null ? null : directoryName.trim();;
	}
	/**
	 * 目录编码
	 */
	public String getDirectoryCode() {
		return directoryCode;
	}
	/**
	 * 目录编码
	 */
	public void setDirectoryCode(String directoryCode) {
		this.directoryCode = directoryCode == null ? null : directoryCode.trim();
	}
	/**
	 * 目录优先级
	 */
	public Integer getDirectoryPriority() {
		return directoryPriority;
	}
	/**
	 * 目录优先级
	 */
	public void setDirectoryPriority(Integer directoryPriority) {
		this.directoryPriority = directoryPriority;
	}
	/**
	 * 0 功能导航 1 功能菜单  3 文件夹  4 按钮
	 */
	public int getDirectoryType() {
		return directoryType;
	}
	/**
	 * 0 功能导航 1 功能菜单  3 文件夹  4 按钮
	 */
	public void setDirectoryType(int directoryType) {
		this.directoryType = directoryType;
	}
	/**
	 * 目录图标
	 */
	public String getDirectoryIcon() {
		return directoryIcon;
	}
	/**
	 * 目录图标
	 */
	public void setDirectoryIcon(String directoryIcon) {
		this.directoryIcon = directoryIcon == null ? null : directoryIcon.trim();
	}
	/**
	 * 目录父导航
	 */
	public Integer getParentNavigator() {
		return parentNavigator;
	}
	/**
	 * 目录父导航
	 */
	public void setParentNavigator(Integer parentNavigator) {
		this.parentNavigator = parentNavigator;
	}
	/**
	 * 目录控制台url
	 */
	public String getConsoleUrl() {
		return consoleUrl;
	}
	/**
	 * 目录控制台url
	 */
	public void setConsoleUrl(String consoleUrl) {
		this.consoleUrl = consoleUrl == null ? null : consoleUrl.trim();
	}
	
	
	
	
}
