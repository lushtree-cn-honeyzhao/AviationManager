/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.common.orm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lushapp.common.excel.annotation.Excel;
import com.lushapp.common.utils.DateUtil;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 统一定义entity基类. <br>
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. <br>
 * 子类可重载getId()函数重定义id的列名映射和生成策略. <br>
 * 2014-12-15 wencp:新加并发控制(乐观锁,用于并发控制)、数据更新时间、操作用户ID.
 * 
 * @author : honey.zhao@aliyun.com  
 * @date : 2014-12-21 上午11:12:07
 */
// @Column(name="...") 该属性对应表中的字段是什么，没有name表示一样
// @Table 对象与表映射
// @UniqueConstraint 唯一约束
// @Version 方法和字段级，乐观锁用法，返回数字和timestamp，数字为首选
// @Transient 暂态属性，表示不需要处理
// @Basic 最基本的注释。有两个属性：fetch是否延迟加载，optional是否允许null
// @Enumerated 枚举类型
// @Temporal 日期转换。默认转换Timestamp
// @Lob 通常与@Basic同时使用，提高访问速度。
// @Embeddable 类级，表可嵌入的
// @Embedded 方法字段级，表被嵌入的对象和@Embeddable一起使用
// @AttributeOverrides 属性重写
// @AttributeOverride 属性重写的内容和@AttributeOverrides一起嵌套使用
// @SecondaryTables 多个表格映射
// @SecondaryTable 定义辅助表格映射和@SecondaryTables一起嵌套使用
// @GeneratedValue 标识符生成策略，默认Auto
// JPA 基类的标识
// @AttributeOverride(name = "id", column = @Column(name = "base_id"))
@MappedSuperclass
public  class BaseEntity extends AutoEntity implements Serializable,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2142201445199112425L;

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String TIME_FORMAT = "HH:mm:ss";

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	public static final String TIMEZONE = "GMT+08:00";

	public static String date2String(Date date, String dateFormat) {
		return DateUtil.format(date, dateFormat);
	}

	public static <T extends Date> T string2Date(String dateString,
			String dateFormat, Class<T> targetResultType) {
		return (T) DateUtil.parse(dateString, dateFormat, targetResultType);
	}

	/**
	 * 记录状态标志位 正常(0) 已删除(1) 待审核(2) 锁定(3) 批准(4)
	 */
	protected Integer status = StatusState.normal.getValue();
	/**
	 * 操作版本(乐观锁,用于并发控制)
	 */
	protected Integer version;

	/**
	 * 记录创建者用户登录名
	 */
	@Excel(exportName="记录创建者", exportFieldWidth = 30)
	protected String createUser;
	/**
	 * 记录创建时间
	 */
	@Excel(exportName="记录创建时间", exportFieldWidth = 30)
	protected Date createTime;

	/**
	 * 记录更新用户 用户登录名
	 */
	protected String updateUser;
	/**
	 * 记录更新时间
	 */
	protected Date updateTime;

	public BaseEntity() {
		super();
	}


	/**
	 * 状态标志位
	 */
    @Column(name = "STATUS",columnDefinition="INT default 0")
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 状态描述
	 */
	@Transient
	public String getStatusView() {
		StatusState s = StatusState.getStatusState(status);
		String str = "";
		if(s != null){
			str =  s.getDescription();
		}
		return str;
	}


	/**
	 * 设置 状态标志位
	 * 
	 * @param status
	 *            状态标志位
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 版本号(乐观锁)
	 */
	@Version
    @Column(name = "VERSION",columnDefinition="INT default 1")
	public Integer getVersion() {
		return version;
	}

	/**
	 * 设置 版本号(乐观锁)
	 * 
	 * @param version
	 *            版本号(乐观锁)
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 记录创建者 用户登录名
	 */
	@Column(name = "CREATE_USER", updatable = false, length = 36)
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 设置 记记录创建者 用户登录名
	 * 
	 * @param createUser
	 *            用户登录名
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 记录创建时间.
	 */
	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = DATE_TIME_FORMAT, timezone = TIMEZONE)
	@Column(name = "CREATE_TIME", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 记录创建时间
	 * 
	 * @param createTime
	 *            记录创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 记录更新用户 用户登录名
	 */
	@Column(name = "UPDATE_USER", length = 36)
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 设置 记录更新用户 用户登录名
	 * 
	 * @param updateUser
	 *            用户登录名
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 记录更新时间
	 */
	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = DATE_TIME_FORMAT, timezone = TIMEZONE)
	@Column(name = "UPDATE_TIME")
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置 记录更新时间
	 * 
	 * @param updateTime
	 *            记录更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}



	public BaseEntity clone() {
		BaseEntity o = null;
		try {
			o = (BaseEntity) super.clone();// Object中的clone()识别出你要复制的是哪一个对象。
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
