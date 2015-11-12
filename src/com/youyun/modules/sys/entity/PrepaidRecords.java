/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.internal.NotNull;
import com.youyun.common.orm.PropertyType;
import com.youyun.common.orm.annotation.Delete;
import com.youyun.common.orm.entity.BaseEntity;

/**
 * 充值记录PrepaidRecords.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-21 上午12:28:04 
 *
 */
@Entity
@Table(name = "T_OIL_PREPAID_RECORDS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class PrepaidRecords
        extends BaseEntity
        implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 交易订单号
	 */
	private String tradeSn;
	
	/**
	 * 充值时间
	 */
	private Date prepaidTime;
	
	/**
	 * 充值地址
	 * varchar(200)
	 */
	private String prepaidAddress;
	 /**
     * 交易类型
     */
    private Integer recordType;
	/**
	 *充值方式 
	 *varchar(50)
	 */
	private String prepaidType;
	
	/**
     * 用户
     */
    private User user; 
    
    /**
     * 账单号
     * @return
     */
	private String accountCode;
	
	/**
	 * 交易前用户可用金额
	 * @return
	 */
	private Float beforeAmount;
	
	/**
	 * 交易金额
	 * @return
	 */
	private Float amount;
	
	/**
	 * 交易后用户可用金额
	 * @return
	 */
	
	private Float afterAmount;
	
	/**
	 * 之前退税
	 * @return
	 */
    private Float beforeRebate;
    
    /**
     * 退税
     * @return
     */
    private Float rebate;
    
    /**
	 * 之后退税
	 * @return
	 */
    private Float afterRebate;
    
    /**
     * 备注
     * @return
     */
    private String note;
    
	
	@Column(name="prepaid_time")
	public Date getPrepaidTime() {
		return prepaidTime;
	}
	public void setPrepaidTime(Date prepaidTime) {
		this.prepaidTime = prepaidTime;
	}
	@Column(name="prepaid_address",length=200)
	public String getPrepaidAddress() {
		return prepaidAddress;
	}
	public void setPrepaidAddress(String prepaidAddress) {
		this.prepaidAddress = prepaidAddress;
	}
	
	@Column(name="prepaid_type",length=50)
	public String getPrepaidType() {
		return prepaidType;
	}
	public void setPrepaidType(String prepaidType) {
		this.prepaidType = prepaidType;
	}
	
	@Column(name="trade_sn",length=200)
	@NotNull
	public String getTradeSn() {
		return tradeSn;
	}
	public void setTradeSn(String tradeSn) {
		this.tradeSn = tradeSn;
	}
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public PrepaidRecords() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Column(name="record_type",columnDefinition="INT default 0")
	@NotNull
	public Integer getRecordType() {
		return recordType;
	}
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}
	@Column(name="account_code",length=100)
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	
	@Column(name="before_amount",columnDefinition="FLOAT default 0")
	@NotNull
	public Float getBeforeAmount() {
		return beforeAmount;
	}
	public void setBeforeAmount(Float beforeAmount) {
		this.beforeAmount = beforeAmount;
	}
	@Column(name="amount",columnDefinition="FLOAT default 0")
	@NotNull
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	@Column(name="after_amount",columnDefinition="FLOAT default 0")
	@NotNull
	public Float getAfterAmount() {
		return afterAmount;
	}
	public void setAfterAmount(Float afterAmount) {
		this.afterAmount = afterAmount;
	}
	
	@Column(name="before_rebate",columnDefinition="FLOAT default 0")
	@NotNull
	public Float getBeforeRebate() {
		return beforeRebate;
	}
	public void setBeforeRebate(Float beforeRebate) {
		this.beforeRebate = beforeRebate;
	}
	@Column(name="rebate",columnDefinition="FLOAT default 0")
	@NotNull
	public Float getRebate() {
		return rebate;
	}
	public void setRebate(Float rebate) {
		this.rebate = rebate;
	}
	@Column(name="after_rebate",columnDefinition="FLOAT default 0")
	@NotNull
	public Float getAfterRebate() {
		return afterRebate;
	}
	public void setAfterRebate(Float afterRebate) {
		this.afterRebate = afterRebate;
	}
	@Column(name="note",length=1000)
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
