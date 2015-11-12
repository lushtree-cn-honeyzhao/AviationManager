/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
package com.youyun.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.internal.NotNull;
import com.youyun.common.orm.PropertyType;
import com.youyun.common.orm.annotation.Delete;
import com.youyun.common.orm.entity.BaseEntity;
/**
 * 广告
 * @author zhaoguoqing
 * @Date 2014-09-25
 *
 */
@Entity
@Table(name = "T_SYS_ADVERT_INFO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class AdvertInfo extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	private AdvertSite advertSite;
	private String name;
	private byte[] picture;
	private String description;
	private String website;
	private Integer contentIntoId;
	private Integer insertUser;
	private String imgUrl;
	private String appimgUrl;
	private boolean publish;
	private Integer hits;
	private Date beginTime;
	private Date endTime;
	private Integer orderNum;
	private String keyValue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "advert_site_id")
	public AdvertSite getAdvertSite() {
		return advertSite;
	}

	public void setAdvertSite(AdvertSite advertSite) {
		this.advertSite = advertSite;
	}
	
	@Column(name = "name", nullable = false, length = 50)
	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Transient
	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	@Column(name = "description", nullable = false, length = 200)
	@NotNull
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "website", length = 200)
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
	@Column(name = "content_into_id")
	public Integer getContentIntoId() {
		return contentIntoId;
	}

	public void setContentIntoId(Integer contentIntoId) {
		this.contentIntoId = contentIntoId;
	}

	@Column(name = "insert_user")
	public Integer getInsertUser() {
		return insertUser;
	}

	public void setInsertUser(Integer insertUser) {
		this.insertUser = insertUser;
	}

	@Column(name = "img_url", nullable = false, length = 50)
	@NotNull
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@Column(name = "appimg_url", nullable = false, length = 50)
	@NotNull
	public String getAppimgUrl() {
		return appimgUrl;
	}

	public void setAppimgUrl(String appimgUrl) {
		this.appimgUrl = appimgUrl;
	}

	@Column(name="publish",columnDefinition="TINYINT default 0")
	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}
	@Column(name="hits")
	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}
	@Column(name="begin_time")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	@Column(name="end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@Column(name="order_num")
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	@Column(name="key_value")
	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	
	public AdvertInfo() {
		super();
	}
}
