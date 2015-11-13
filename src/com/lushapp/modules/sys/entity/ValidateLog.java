/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lushapp.common.orm.PropertyType;
import com.lushapp.common.orm.annotation.Delete;
import com.lushapp.common.orm.entity.BaseEntity;

/**
 * 发送验证码日志ValidateLog.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-21 上午12:28:04 
 *
 */
@Entity
@Table(name = "T_VALIDATE_LOG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler","oilCards"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class ValidateLog extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	//验证码类型，1邮箱验证码，2手机号验证码
	private String type;
	//生成的验证码,createDate是验证码生成时间
	private String validateCode;
	private String sendTo;//验证码发送的终端
	
	@Column(name="sendto")
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	@Column(name="type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name="validate_code")
	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	
}
