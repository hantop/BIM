package com.zzrbi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Desc: StatisticsLoan实体
 * User: yinlonglong
 * Date: 2018/1/25
 * Time: 11:03
 */
public class StatisticsLoan implements Serializable {
	
	private static final long serialVersionUID = -5948131804598441756L;
	/**  */
	public int id;
	/** 日期 */
	public Date dateTime;
	/** 投新手项目人数 */
	public int newLoanPeople;
	/** 投新手项目总金额 */
	public BigDecimal newLoanMoney;
	/** 投新手项目笔数 */
	public int newLoanCount;
	/** 投30天项目人数 */
	public int People30;
	/** 投30天项目总金额 */
	public BigDecimal Money30;
	/** 投30天项目笔数 */
	public int Count30;
	/** 投60天项目人数 */
	public int People60;
	/** 投60天项目总金额 */
	public BigDecimal Money60;
	/** 投60天项目笔数 */
	public int Count60;
	/** 投90天项目人数 */
	public int People90;
	/** 投90天项目总金额 */
	public BigDecimal Money90;
	/** 投90天项目笔数 */
	public int Count90;
	/** 投180天项目人数 */
	public int People180;
	/** 投180天项目总金额 */
	public BigDecimal Money180;
	/** 投180天项目笔数 */
	public int Count180;
	/** 投360天项目人数 */
	public int People360;
	/** 投360天项目总金额 */
	public BigDecimal Money360;
	/** 投360天项目笔数 */
	public int Count360;
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

	public int getNewLoanPeople() {
		return newLoanPeople;
	}

	public void setNewLoanPeople(int newLoanPeople) {
		this.newLoanPeople = newLoanPeople;
	}

	public BigDecimal getNewLoanMoney() {
		return newLoanMoney;
	}

	public void setNewLoanMoney(BigDecimal newLoanMoney) {
		this.newLoanMoney = newLoanMoney;
	}

	public int getNewLoanCount() {
		return newLoanCount;
	}

	public void setNewLoanCount(int newLoanCount) {
		this.newLoanCount = newLoanCount;
	}

	public int getPeople30() {
		return People30;
	}

	public void setPeople30(int people30) {
		People30 = people30;
	}

	public BigDecimal getMoney30() {
		return Money30;
	}

	public void setMoney30(BigDecimal money30) {
		Money30 = money30;
	}

	public int getCount30() {
		return Count30;
	}

	public void setCount30(int count30) {
		Count30 = count30;
	}

	public int getPeople60() {
		return People60;
	}

	public void setPeople60(int people60) {
		People60 = people60;
	}

	public BigDecimal getMoney60() {
		return Money60;
	}

	public void setMoney60(BigDecimal money60) {
		Money60 = money60;
	}

	public int getCount60() {
		return Count60;
	}

	public void setCount60(int count60) {
		Count60 = count60;
	}

	public int getPeople90() {
		return People90;
	}

	public void setPeople90(int people90) {
		People90 = people90;
	}

	public BigDecimal getMoney90() {
		return Money90;
	}

	public void setMoney90(BigDecimal money90) {
		Money90 = money90;
	}

	public int getCount90() {
		return Count90;
	}

	public void setCount90(int count90) {
		Count90 = count90;
	}

	public int getPeople180() {
		return People180;
	}

	public void setPeople180(int people180) {
		People180 = people180;
	}

	public BigDecimal getMoney180() {
		return Money180;
	}

	public void setMoney180(BigDecimal money180) {
		Money180 = money180;
	}

	public int getCount180() {
		return Count180;
	}

	public void setCount180(int count180) {
		Count180 = count180;
	}

	public int getPeople360() {
		return People360;
	}

	public void setPeople360(int people360) {
		People360 = people360;
	}

	public BigDecimal getMoney360() {
		return Money360;
	}

	public void setMoney360(BigDecimal money360) {
		Money360 = money360;
	}

	public int getCount360() {
		return Count360;
	}

	public void setCount360(int count360) {
		Count360 = count360;
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