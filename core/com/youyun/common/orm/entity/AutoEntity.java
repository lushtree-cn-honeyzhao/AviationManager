/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.common.orm.entity;


import com.youyun.common.excel.annotation.Excel;

import javax.persistence.*;

/**
 * 统一定义entity基类. <br>
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. <br>
 * 子类可重载getId()函数重定义id的列名映射和生成策略. <br>
 * 2014-12-15 wencp:新加并发控制(乐观锁,用于并发控制)、数据更新时间、操作用户ID.
 * 
 * @author : honey.zhao@aliyun.com  
 * @date : 2014-12-21 上午11:12:07
 */
@MappedSuperclass
public abstract class AutoEntity  extends AbstractEntity<Long>{

	/**
	 * 主键ID
	 */
	@Excel(exportName="编号", exportFieldWidth = 10)
	protected Long id;

	public AutoEntity() {
	}

	/**
	 * 主键ID 根据数据库主键自增长策略 依赖于数据库(SQL Serveer、MySQL数据库使用)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
	public Long getId() {
		return id;
	}

	/**
	 * 设置 主键ID
	 * 
	 * @param id
	 *            主键ID
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
