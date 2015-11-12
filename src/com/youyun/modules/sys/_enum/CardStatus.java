/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.modules.sys._enum;

/**
 * 
 * 附属加油卡状态   枚举类型.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-8-11 下午10:48:23
 * 
 */
public enum CardStatus {
	/** 使用(0) */
	use(0, "使用"),
	/** 挂失(1) */
	reportloss(1, "挂失"),
	/** 灰卡(2) */
	gray(2, "灰卡"),
	/** 失效(3) */
	failure(3, "失效"),
	/** 问题卡(4) */
	problem(4, "问题卡"),
	/** 坏卡(5) */
	bad(5, "坏卡"),
	/** 作废(6) */
	invalid(6, "作废"),
	/** 购入(7) */
	buy(7, "购入"),
	/** 白卡(8) */
	white(8, "白卡"),
	/** 入库(9) */
	storage(9, "入库"),
	/** 待销(10) */
	waitfor(10, "待销"),
	/** 置换(11) */
	replacement(11, "置换"),
	/** 过期(12) */
	overdue(12, "过期"),
	/** 是有公司支付(13) */
	companytopay(13, "是有公司支付"),
	/** 用户申请支付(14) */
	usersapplypayment(14, "用户申请支付");

	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	CardStatus(Integer value, String description) {
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

	public static CardStatus getCardStatus(Integer value) {
		if (null == value)
			return null;
		for (CardStatus _enum : CardStatus.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static CardStatus getCardStatus(String description) {
		if (null == description)
			return null;
		for (CardStatus _enum : CardStatus.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}