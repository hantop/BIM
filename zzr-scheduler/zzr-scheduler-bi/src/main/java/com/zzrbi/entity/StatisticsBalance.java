package com.zzrbi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Desc: StatisticsBalance实体
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 10:58
 */
public class StatisticsBalance implements Serializable {

	private static final long serialVersionUID = 3383396709744745785L;
	/** id */
	public int id;
	/** 日期 */
	public Date dateTime;
	/** 当日首投金额 */
	public BigDecimal todayFirstInvest;
	/** 存量金额 */
	public BigDecimal availableBalance;
	/** 站岗资金 */
	public BigDecimal stand;
	/** 充值金额 */
	public BigDecimal recharge;
	/** 提现金额 */
	public BigDecimal cash;
	/** 投资金额 */
	public BigDecimal invest;
	/** 回款金额 */
	public BigDecimal investPhase;
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

	public BigDecimal getTodayFirstInvest() {
		return todayFirstInvest;
	}
  
	public void setTodayFirstInvest(BigDecimal todayFirstInvest) {
		this.todayFirstInvest = todayFirstInvest;
	} 
	
	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}
  
	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	} 
	
	public BigDecimal getStand() {
		return stand;
	}
  
	public void setStand(BigDecimal stand) {
		this.stand = stand;
	} 
	
	public BigDecimal getRecharge() {
		return recharge;
	}
  
	public void setRecharge(BigDecimal recharge) {
		this.recharge = recharge;
	} 
	
	public BigDecimal getCash() {
		return cash;
	}
  
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	} 
	
	public BigDecimal getInvest() {
		return invest;
	}
  
	public void setInvest(BigDecimal invest) {
		this.invest = invest;
	} 
	
	public BigDecimal getInvestPhase() {
		return investPhase;
	}
  
	public void setInvestPhase(BigDecimal investPhase) {
		this.investPhase = investPhase;
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