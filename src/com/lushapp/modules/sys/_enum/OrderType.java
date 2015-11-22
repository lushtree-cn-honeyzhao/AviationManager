/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys._enum;

/**
 * 
 * 订单类型 枚举类型.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-8-11 下午10:48:23
 * 
 */
public enum OrderType {

	/** 国际(1) */
	international(1, "国际"),
	/** 国内(2) */
	domestic(2, "国内");

	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	OrderType(Integer value, String description) {
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

	public static OrderType getOrderType(Integer value) {
		if (null == value)
			return null;
		for (OrderType _enum : OrderType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static OrderType getOrderType(String description) {
		if (null == description)
			return null;
		for (OrderType _enum : OrderType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}