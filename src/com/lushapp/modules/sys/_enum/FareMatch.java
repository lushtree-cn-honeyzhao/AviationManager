/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys._enum;

/**
 * 
 * 运价匹配 枚举类型.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-8-11 下午10:48:23
 * 
 */
public enum FareMatch {

	/** 外开(1) */
	openout(1, "外开"),
	/** 自开(2) */
	selfout(2, "自开");

	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	FareMatch(Integer value, String description) {
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

	public static FareMatch getFareMatch(Integer value) {
		if (null == value)
			return null;
		for (FareMatch _enum : FareMatch.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static FareMatch getFareMatch(String description) {
		if (null == description)
			return null;
		for (FareMatch _enum : FareMatch.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}