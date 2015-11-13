/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
package com.lushapp.modules.sys.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lushapp.common.orm.PropertyType;
import com.lushapp.common.orm.annotation.Delete;
import com.lushapp.common.orm.entity.BaseEntity;
/**
 * 广告
 * @author zhaoguoqing
 * @Date 2014-09-25
 * 
 */
@Entity
@Table(name = "T_SYS_ADVERT_SITE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class AdvertSite extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private Integer width;
	private Integer height;
	private Integer advertNumber;
	private String keyValue;
	private String insertUser;
	private String description;
	private Set<AdvertInfo> advertInfos = new HashSet<AdvertInfo>(0);
	
	
	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "width")
	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name = "height")
	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "insert_user")
	public String getInsertUser() {
		return insertUser;
	}

	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "advertSite")
	public Set<AdvertInfo> getAdvertInfos() {
		return this.advertInfos;
	}

	public void setAdvertInfos(Set<AdvertInfo> advertInfos) {
		this.advertInfos = advertInfos;
	}

	@Column(name = "advert_number")
	public Integer getAdvertNumber() {
		return advertNumber;
	}

	public void setAdvertNumber(Integer advertNumber) {
		this.advertNumber = advertNumber;
	}

	@Column(name = "key_value")
	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	
	
	public AdvertSite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdvertSite(String name, Integer width, Integer height,
			Integer advertNumber, String keyValue, String insertUser,String description, Set<AdvertInfo> advertInfos) {
		super();
		this.name = name;
		this.width = width;
		this.height = height;
		this.advertNumber = advertNumber;
		this.keyValue = keyValue;
		this.insertUser = insertUser;
		this.description = description;
		this.advertInfos = advertInfos;
	}
	
	
}
