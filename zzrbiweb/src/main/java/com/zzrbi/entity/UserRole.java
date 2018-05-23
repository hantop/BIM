package com.zzrbi.entity;

import java.io.Serializable;

/**
 * UserRole 的实体
 * @author yinlonglong
 * @date 2017年3月21日 下午2:55:14
 */
public class UserRole implements Serializable {

	private static final long serialVersionUID = -4618610788543696526L;

	/**
	 * 角色id
	 */
	private Integer id;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * \n            0=业务类型,\n            1=管理类型\n            
	 */
	private Integer roleType;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 平台类型 1管理平台 2商户平台
	 */
	private Integer platformType;

	
	
	/**
	 * 角色id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 角色id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 角色名称
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * \n            0=业务类型,\n            1=管理类型\n            
	 */
	public Integer getRoleType() {
		return roleType;
	}
	/**
	 * \n            0=业务类型,\n            1=管理类型\n            
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	/**
	 * 备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 平台类型 1管理平台 2商户平台
	 */
	public Integer getPlatformType() {
		return platformType;
	}
	/**
	 * 平台类型 1管理平台 2商户平台
	 */
	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}
	
	
}
