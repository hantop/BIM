package com.zzrbi.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Worker的实体
 * @author yinlonglong
 * @date 2017年3月21日 下午2:54:44
 */
public class Worker implements Serializable{
	
	private static final long serialVersionUID = 8039758271126614416L;
	
	/**
	 * 员工id
	 */
	private Integer id;
	
	/**
	 * 所属班组
	 */
	private Integer teamId;
	

	
	/**
	 * 员工名称
	 */
	private String realName;
	
	/**
	 * 0男  1女
	 */
	private String sex;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 *民族
	 */
	private String nation;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 合同状态1=已签合同0=未签合同
	 */
	private String contractStatus;
	
	/**
	 * 1=管理员
	 */
	private String type;
	

	
	/**
	 * 创建时间
	 */
	private Date creatTime;
	

	
	/**
	 * 岗位
	 */
	private String station;
	
	/**
	 * 部门
	 */
	private Integer department;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Worker{" +
				"id=" + id +
				", teamId=" + teamId +
				", realName='" + realName + '\'' +
				", sex=" + sex +
				", age=" + age +
				", nation='" + nation + '\'' +
				", idCard='" + idCard + '\'' +
				", contractStatus='" + contractStatus + '\'' +
				", type='" + type + '\'' +
				", creatTime=" + creatTime +
				", station='" + station + '\'' +
				", department=" + department +
				'}';
	}
}
