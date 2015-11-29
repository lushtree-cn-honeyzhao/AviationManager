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
public enum SettlementType {

	/** 日结(1) */
	day_sett(1, "外开"),
	/** 月结(2) */
	month_sett(2, "自开"),
	/**  年结(3) */
	year_sett(3,"月结");


	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	SettlementType(Integer value, String description) {
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

	public static SettlementType getSettlementType(Integer value) {
		if (null == value)
			return null;
		for (SettlementType _enum : SettlementType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static SettlementType getSettlementType(String description) {
		if (null == description)
			return null;
		for (SettlementType _enum : SettlementType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}