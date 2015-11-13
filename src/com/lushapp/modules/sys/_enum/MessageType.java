/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys._enum;

/**
 * 日志类型
 */
public enum MessageType {
    /** 通知消息(1) */
	notification(1, "通知消息"),
    /** 反馈意见(2) */
	feedback(2, "反馈意见");

	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

    MessageType(Integer value, String description) {
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

	public static MessageType getMessageType(Integer value) {
		if (null == value)
			return null;
		for (MessageType _enum : MessageType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static MessageType getMessageType(String description) {
		if (null == description)
			return null;
		for (MessageType _enum : MessageType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}