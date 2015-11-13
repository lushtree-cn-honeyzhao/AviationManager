/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.common.orm.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 统一定义entity基类. <br>
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. <br>
 * 子类可重载getId()函数重定义id的列名映射和生成策略. <br>
 * 
 * @author : honey.zhao@aliyun.com  
 * @date : 2014-12-21 上午11:12:07
 */
@MappedSuperclass
public abstract class UUIDEntity extends AbstractEntity<String> {

	/**
	 * 主键ID
	 */
	protected String id;

	public UUIDEntity() {
	}

	/**
	 * Hibernate3.6以后,UUIDHexGenerator(uuid)已不推荐使用，改用UUIDGenerator(org.hibernate
	 * .id.UUIDGenerator)
	 */
	@Id
	@Column(name = "ID",updatable = false, length = 36)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
	public String getId() {
		return id;
	}

	/**
	 * 设置 主键ID
	 * 
	 * @param id
	 *            主键ID
	 */
	public void setId(String id) {
		this.id = id;
	}

}
