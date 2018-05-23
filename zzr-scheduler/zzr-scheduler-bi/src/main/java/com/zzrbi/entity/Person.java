package com.zzrbi.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Desc:  Person实体
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 10:57
 */
public class Person implements Serializable {

	private static final long serialVersionUID = 6680325774091881232L;
	/**  */
	public int id;
	/** 日期 */
	public Date dateTime;
	/** 注册人数 */
	public int registerCount;
	/** 当天注册且开户人数 */
	public int todayRegisterAccount;
	/** 往日注册当天开户人数 */
	public int beforRegisterAccount;
	/** 投资人数 */
	public int investPerson;
	/** 当天注册且当天首投投资人数 */
	public int todayRegisterInvest;
	/** 往日注册且当天首投投资人数 */
	public int beforRegisterInvest;
	/** 复投人数 */
	public int repeatInvest;
	/** 投资笔数 */
	public int investCount;
	/** 创建日期 */
	public Date creatTime;
	/** 周期 */
	public int weekCycle;

	public int getId() {
		return id;
	}
  
	public void setId(int id) {
		this.id = id;
	} 
	
	public Date getDateTime() {
		return dateTime;
	}
  
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(int registerCount) {
		this.registerCount = registerCount;
	}

	public int getTodayRegisterAccount() {
		return todayRegisterAccount;
	}
  
	public void setTodayRegisterAccount(int todayRegisterAccount) {
		this.todayRegisterAccount = todayRegisterAccount;
	} 
	
	public int getBeforRegisterAccount() {
		return beforRegisterAccount;
	}
  
	public void setBeforRegisterAccount(int beforRegisterAccount) {
		this.beforRegisterAccount = beforRegisterAccount;
	} 
	
	public int getInvestPerson() {
		return investPerson;
	}
  
	public void setInvestPerson(int investPerson) {
		this.investPerson = investPerson;
	} 
	
	public int getTodayRegisterInvest() {
		return todayRegisterInvest;
	}
  
	public void setTodayRegisterInvest(int todayRegisterInvest) {
		this.todayRegisterInvest = todayRegisterInvest;
	} 
	
	public int getBeforRegisterInvest() {
		return beforRegisterInvest;
	}
  
	public void setBeforRegisterInvest(int beforRegisterInvest) {
		this.beforRegisterInvest = beforRegisterInvest;
	} 
	
	public int getRepeatInvest() {
		return repeatInvest;
	}
  
	public void setRepeatInvest(int repeatInvest) {
		this.repeatInvest = repeatInvest;
	} 
	
	public int getInvestCount() {
		return investCount;
	}
  
	public void setInvestCount(int investCount) {
		this.investCount = investCount;
	} 
	
	public Date getCreatTime() {
		return creatTime;
	}
  
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	} 
	
	public int getWeekCycle() {
		return weekCycle;
	}
  
	public void setWeekCycle(int weekCycle) {
		this.weekCycle = weekCycle;
	} 
	
	
	

	

	
	
	
}