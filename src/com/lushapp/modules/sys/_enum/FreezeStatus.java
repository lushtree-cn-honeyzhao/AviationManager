/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys._enum;

/**
 * 
 * 订单状态 枚举类型.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-8-11 下午10:48:23
 * 订单状态:
 * 0:未冻结
 * 1:已冻结
 *
 */
public enum FreezeStatus {

	/** 未冻结(0) */
    unfreezed(0, "未冻结"),
	/** 已冻结(1) */
    freezed(1, "已冻结");


	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	FreezeStatus(Integer value, String description) {
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

	public static FreezeStatus getFreezeStatus(Integer value) {
		if (null == value)
			return null;
		for (FreezeStatus _enum : FreezeStatus.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static FreezeStatus getFreezeStatus(String description) {
		if (null == description)
			return null;
		for (FreezeStatus _enum : FreezeStatus.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}