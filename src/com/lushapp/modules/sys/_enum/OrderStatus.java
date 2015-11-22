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
 * 1:待出票
 * 2:已出票
 * 3:待退票
 * 4:已退票
 * 5:待改签
 * 6:已改签
 * 7:待废票
 * 8:已废票
 * 101:未收审
 * 102:已收审
 * 201:未付审
 * 202:已付审
 * 301:未终审
 * 302:已终审
 * 
 */
public enum OrderStatus {

	/** 待出票(1) */
    undetermined_ticket(1, "待出票"),
	/** 已出票(2) */
    determined_ticket(2, "已出票"),
    /** 待退票(3) */
    undetermined_refund(3, "待退票"),
    /** 已退票(4) */
    determined_refund(4, "已退票"),
	/** 待改签(5) */
	undetermined_endorse(5, "待改签"),
	/** 已改签(6) */
	determined_endorse(6, "已改签"),
	/** 待改签(7) */
	undetermined_invalidated(7, "待废票"),
	/** 已改签(8) */
	determined_invalidated(8, "已废票"),

	/** 未收审(101) */
	undetermined_remanded(101, "未收审"),
	/** 已收审(102) */
	determined_remanded(102, "已收审"),

	/** 未付审(201) */
	undetermined_payaudit(201, "未付审"),
	/** 已付审(202) */
	determined_payaudit(202, "已付审"),

	/** 未终审(301) */
	undetermined_finalaudit(301, "未终审"),
	/** 已终审(302) */
	determined_finalaudit(302, "已终审");


	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	OrderStatus(Integer value, String description) {
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

	public static OrderStatus getOrderStatus(Integer value) {
		if (null == value)
			return null;
		for (OrderStatus _enum : OrderStatus.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static OrderStatus getOrderStatus(String description) {
		if (null == description)
			return null;
		for (OrderStatus _enum : OrderStatus.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}