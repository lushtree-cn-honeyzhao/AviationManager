/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys._enum;

/**
 * 
 * 结算方式 枚举类型.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-8-11 下午10:48:23
 * 
 */
public enum CreditCertification {

	/** 未认证(0) */
	unable_certification(0, "未认证"),
	/**  已认证(1) */
	enable_certification(1,"已认证");


	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	CreditCertification(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * 获取值
	 * @return value
	 */
	public Integer getValue() {
		return value;
	}

	/**
     * 获取描述信息
     * @return description
     */
	public String getDescription() {
		return description;
	}

	public static CreditCertification getCreditCertification(Integer value) {
		if (null == value)
			return null;
		for (CreditCertification _enum : CreditCertification.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static CreditCertification getCreditCertification(String description) {
		if (null == description)
			return null;
		for (CreditCertification _enum : CreditCertification.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}