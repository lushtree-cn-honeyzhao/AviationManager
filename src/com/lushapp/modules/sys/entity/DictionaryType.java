/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.lushapp.common.excel.annotation.Excel;
import com.lushapp.common.orm.entity.BaseEntity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

/**
 * 系统字典类型Entity.
 * 
 * @author : honey.zhao@aliyun.com  
 * @date 2014-10-23 下午9:08:36
 */

@Entity
@Table(name = "T_SYS_DICTIONARYTYPE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" , "handler","fieldHandler",
        "groupDictionaryType","subDictionaryTypes" })
@SuppressWarnings("serial")
public class DictionaryType extends BaseEntity {

	/**
	 * 类型名称
	 */
	@Excel(exportName="类型名称", exportFieldWidth = 30)
	private String name;
	/**
	 * 类型编码
	 */
	@Excel(exportName="类型编码", exportFieldWidth = 20)
	private String code;

    /**
     * 父级类型 即分组
     */
    private DictionaryType groupDictionaryType;
    /**
     * @Transient 父级类型 即分组名称
     */
    private String groupDictionaryTypeName;
    /**
     * @Transient 父级类型 即分组编码
     */
    private String groupDictionaryTypeCode;
    /**
     * 子DictionaryType集合
     */
    private List<DictionaryType> subDictionaryTypes = Lists.newArrayList();
    /**
     * 备注
     */
    private String remark;

	/**
	 * 排序
	 */
	@Excel(exportName="排序", exportFieldWidth = 10)
	private Integer orderNo;

	public DictionaryType() {
		super();
	}

    public DictionaryType(Long id) {
        this();
        super.id = id;
    }

    /**
	 * 系统数据字典类型构造函数.
	 * 
	 * @param name
	 *            类型名称
	 * @param code
	 *            类型编码
	 * @param orderNo
	 *            排序
	 */
	public DictionaryType(String name, String code, Integer orderNo) {
		super();
		this.name = name;
		this.code = code;
		this.orderNo = orderNo;
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

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "GROUP_CODE", referencedColumnName = "CODE")
    public DictionaryType getGroupDictionaryType() {
        return groupDictionaryType;
    }

    public void setGroupDictionaryType(DictionaryType groupDictionaryType) {
        this.groupDictionaryType = groupDictionaryType;
    }

    @Transient
    public String getGroupDictionaryTypeName() {
        if(groupDictionaryType != null){
            groupDictionaryTypeName = groupDictionaryType.getName();
        }
        return groupDictionaryTypeName;
    }

    public void setGroupDictionaryTypeName(String groupDictionaryTypeName) {
        this.groupDictionaryTypeName = groupDictionaryTypeName;
    }

    @Transient
    @JsonProperty("_parentId")
    public String getGroupDictionaryTypeCode() {
        if(groupDictionaryType != null){
            groupDictionaryTypeCode = groupDictionaryType.getCode();
        }
        return groupDictionaryTypeCode;
    }

    public void setGroupDictionaryTypeCode(String groupDictionaryTypeCode) {
        this.groupDictionaryTypeCode = groupDictionaryTypeCode;
    }

    @OneToMany(mappedBy = "groupDictionaryType")
    public List<DictionaryType> getSubDictionaryTypes() {
        return subDictionaryTypes;
    }

    public void setSubDictionaryTypes(List<DictionaryType> subDictionaryTypes) {
        this.subDictionaryTypes = subDictionaryTypes;
    }
    @Column(name = "REMARK")
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

}