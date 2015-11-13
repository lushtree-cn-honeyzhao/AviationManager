/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.lushapp.common.orm.entity.BaseEntity;
import com.lushapp.common.utils.StringUtils;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * 系统数据字典Entity.
 * 
 * @author : honey.zhao@aliyun.com  
 * @date 2014-10-23 下午9:08:36
 */

@Entity
@Table(name = "T_SYS_DICTIONARY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
// jackson标记不生成json对象的属性
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" , "handler","fieldHandler", "parentDictionary",
		"dictionaryType", "subDictionarys" })
@SuppressWarnings("serial")
public class Dictionary extends BaseEntity{

	/**
	 * 参数名称
	 */
	private String name;
	/**
	 * 参数编码
	 */
	private String code;
    /**
     * 参数值
     */
    private String value;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 排序
	 */
	private Integer orderNo;
	/**
	 * 上级编码
	 */
	private Dictionary parentDictionary;
	/**
	 * 子Dictionary集合 @Transient
	 */
	private List<Dictionary> subDictionarys = Lists.newArrayList();

	/**
	 * 上级编码 @Transient
	 */
	private String parentDictionaryCode;
	/**
	 * 上级名称 @Transient
	 */
	private String parentDictionaryName;

	/**
	 * 系统字典类型
	 */
	private DictionaryType dictionaryType;

	/**
	 * 系统字典类型 编码code @Transient
	 */
	private String dictionaryTypeCode;

	/**
	 * 系统字典类型 名称name @Transient
	 */
	private String dictionaryTypeName;

	public Dictionary() {
        super();
	}

    public Dictionary(Long id) {
        this();
        this.id = id;
    }

    @Column(name = "NAME",length = 100, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CODE",length = 36, unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    @Column(name = "VALUE",length = 100)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    @Column(name = "REMAK",length = 100)
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "ORDER_NO")
	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PARENT_CODE", referencedColumnName = "CODE")
	public Dictionary getParentDictionary() {
		return parentDictionary;
	}

	public void setParentDictionary(Dictionary parentDictionary) {
		this.parentDictionary = parentDictionary;
	}

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "parentDictionary")
	// @OrderBy("orderNo asc")
	// @LazyCollection(LazyCollectionOption.FALSE)
	@Transient
	public List<Dictionary> getSubDictionarys() {
		return subDictionarys;
	}

	public void setSubDictionarys(List<Dictionary> subDictionarys) {
		this.subDictionarys = subDictionarys;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "DICTIONARYTYPE_CODE", referencedColumnName = "CODE")
	public DictionaryType getDictionaryType() {
		return dictionaryType;
	}

	public void setDictionaryType(DictionaryType dictionaryType) {
		this.dictionaryType = dictionaryType;
	}

	@Transient
	public String getParentDictionaryCode() {
		if (StringUtils.isBlank(parentDictionaryCode)
				&& parentDictionary != null) {
			parentDictionaryCode = parentDictionary.getCode();
		}
		return parentDictionaryCode;
	}

	public void setParentDictionaryCode(String parentDictionaryCode) {
		this.parentDictionaryCode = parentDictionaryCode;
	}

	@Transient
	public String getParentDictionaryName() {
		if (parentDictionary != null) {
			parentDictionaryName = parentDictionary.getName();
		}
		return parentDictionaryName;
	}

	public void setParentDictionaryName(String parentDictionaryName) {
		this.parentDictionaryName = parentDictionaryName;
	}

	@Transient
	public String getDictionaryTypeCode() {
		if (StringUtils.isBlank(dictionaryTypeCode) && dictionaryType != null) {
			dictionaryTypeCode = dictionaryType.getCode();
		}
		return dictionaryTypeCode;
	}

	public void setDictionaryTypeCode(String dictionaryTypeCode) {
		this.dictionaryTypeCode = dictionaryTypeCode;
	}

	@Transient
	public String getDictionaryTypeName() {
		if (dictionaryType != null) {
			dictionaryTypeName = dictionaryType.getName();
		}
		return dictionaryTypeName;
	}

	public void setDictionaryTypeName(String dictionaryTypeName) {
		this.dictionaryTypeName = dictionaryTypeName;
	}
}