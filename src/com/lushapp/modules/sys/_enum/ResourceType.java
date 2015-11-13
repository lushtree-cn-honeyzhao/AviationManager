/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys._enum;

/**
 * 
 * 资源类型标识 枚举类型.
 * <br>菜单(0) 功能(1)
 * @author honey.zhao@aliyun.com  
 * @date 2014-01-28 上午10:48:23
 * 
 */
public enum ResourceType {
	/** 菜单(0) */
	menu(0, "菜单"),
	/** 按钮(1) */
	function(1, "功能");

	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	ResourceType(Integer value, String description) {
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

	public static ResourceType getResourceType(Integer value) {
		if (null == value)
			return null;
		for (ResourceType _enum : ResourceType.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static ResourceType getResourceType(String description) {
		if (null == description)
			return null;
		for (ResourceType _enum : ResourceType.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}