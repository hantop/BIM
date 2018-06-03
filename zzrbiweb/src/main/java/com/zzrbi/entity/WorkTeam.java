package com.zzrbi.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * WorkTeam实体
 * @author lihui
 * @date 2017年3月21日 下午2:54:44
 */
public class WorkTeam implements Serializable{
	
	private static final long serialVersionUID = 8039758271286614413L;
	
	/**
	 * 班组id
	 */
	private Integer id;
	
	/**
	 * 公司id
	 */
	private Integer companyId;
	
	/**
	 * 班组名称
	 */
	private String teamName;
	
	/**
	 * 班长
	 */
	private String teamManager;
	
	/**
	 * 移动电话
	 */
	private String mobile;

	private Date creatTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamManager() {
		return teamManager;
	}

	public void setTeamManager(String teamManager) {
		this.teamManager = teamManager;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
}
