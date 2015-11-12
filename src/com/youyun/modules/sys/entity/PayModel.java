/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.modules.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.youyun.common.orm.PropertyType;
import com.youyun.common.orm.annotation.Delete;
import com.youyun.common.orm.entity.BaseEntity;

/**
 * 支付类型PayModel.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-21 上午12:28:04 
 *
 */
@Entity
@Table(name = "T_SYS_PAYMODEL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class PayModel
        extends BaseEntity
        implements Serializable {
	
    private static final long serialVersionUID = 1L;
	/**
     * 支付类型值
     */
    private Integer modelValue;
    /**
     * 支付名称
     * int
     */
    private String modelName;
    
    /**
     * 支付排序
     */
    private String payDesc;
    /**
     * app手续费
     */
    private float apphandCharge;
    /**
     * web手续费
     */
    private float webhandCharge;
    
    /**
     *关键字
     */
    private String metaKeys;

    @Column(name="model_value")
	public Integer getModelValue() {
		return modelValue;
	}

	public void setModelValue(Integer modelValue) {
		this.modelValue = modelValue;
	}
	
	@Column(name="model_name")
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	@Column(name="pay_desc")
	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}
	
	@Column(name="meta_keys")
	public String getMetaKeys() {
		return metaKeys;
	}

	public void setMetaKeys(String metaKeys) {
		this.metaKeys = metaKeys;
	}

	public PayModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Column(name="app_hand_charge")
	public float getApphandCharge() {
		return apphandCharge;
	}

	public void setApphandCharge(float apphandCharge) {
		this.apphandCharge = apphandCharge;
	}
	@Column(name="web_hand_charge")
	public float getWebhandCharge() {
		return webhandCharge;
	}

	public void setWebhandCharge(float webhandCharge) {
		this.webhandCharge = webhandCharge;
	}

	
}
