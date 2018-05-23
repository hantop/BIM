package com.zzrbi.entity;

import java.io.Serializable;

/**
 * RoleDirectoryRel的实体
 * @author yinlonglong
 * @date 2017年3月21日 下午2:54:16
 */
public class RoleDirectoryRel implements Serializable {

	private static final long serialVersionUID = 7984628723379269935L;

	/**
	 * 角色目录id
	 */
	private int id;
	
	/**
	 * 目录id
	 */
	private int directoryId;
	
	/**
	 * 角色id
	 */
	private int roleId;

	
	
	/**
	 * 角色目录id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 角色目录id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 目录id
	 */
	public int getDirectoryId() {
		return directoryId;
	}
	/**
	 * 目录id
	 */
	public void setDirectoryId(int directoryId) {
		this.directoryId = directoryId;
	}
	/**
	 * 角色id
	 */
	public int getRoleId() {
		return roleId;
	}
	/**
	 * 角色id
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	
	
}
