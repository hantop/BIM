package com.zzrbi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Desc: EveryTime实体
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 11:05
 */
public class EveryTime implements Serializable {

	private static final long serialVersionUID = 7066691528291142301L;
	/**  */
	public int id;
	/** 日期时间 */
	public Date dateTime;
	/** 注册人数 */
	public int registerCount;
	/** 投资人数 */
	public int investCount;
	/** 首投人数 */
	public int firstInvestCount;
	/** 存量金额 */
	public BigDecimal availableBalance;
	/** 站岗资金 */
	public BigDecimal stand;
	/** 充值金额 */
	public BigDecimal recharge;
	/** 提现金额 */
	public BigDecimal cash;
	/** 投资金额 */
	public BigDecimal investMoney;
	/** 回款金额 */
	public BigDecimal investPhase;
	/** 创建日期 */
	public Date creatTime;


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

	public int getInvestCount() {
		return investCount;
	}

	public void setInvestCount(int investCount) {
		this.investCount = investCount;
	}

	public int getFirstInvestCount() {
		return firstInvestCount;
	}

	public void setFirstInvestCount(int firstInvestCount) {
		this.firstInvestCount = firstInvestCount;
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

	public BigDecimal getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(BigDecimal investMoney) {
		this.investMoney = investMoney;
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
}