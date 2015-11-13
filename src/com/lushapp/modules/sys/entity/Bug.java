/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lushapp.common.excel.annotation.Excel;
import com.lushapp.common.orm.entity.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * BUG内容 PO
 */
@Entity
@Table(name = "T_SYS_BUG")
// jackson标记不生成json对象的属性
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" , "handler","fieldHandler"})
@SuppressWarnings("serial")
public class Bug extends BaseEntity implements Serializable{

	/**
	 * bug标题.
	 */
	@Excel(exportName="标题", exportFieldWidth = 50)
	private String title;
    /**
     * 颜色
     */
    @Excel(exportName="颜色", exportFieldWidth = 20)
    private String color;
	/**
	 * bug类型 使用数据字典
	 */
    private String type;
    /**
     * bug类型名称 @Transient
     */
	@Excel(exportName="类型", exportFieldWidth = 20)
    private String typeName;
    
	/**
	 * bug描述.
	 */
    @Excel(exportName="内容", exportFieldWidth = 100)
	private String content;
	/**
	 * bug描述. @Transient
	 */
//	@Excel(exportName="内容", exportFieldWidth = 100)
//	private String contentView;

	public Bug() {
		super();
	}

    @Column(name = "TITLE",length = 255)
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    @Column(name = "COLOR",length = 12)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "TYPE",length = 36)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Transient
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * bug内容.
	 */
//	@Lob
	//自定义Clob
//	@JsonSerialize(using = ClobSerializer.class)
    @Column(name = "CONTENT",columnDefinition = "text(4000)")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 用于view显示
	 * @return
	 */
//	@Transient
//	public String getContentView() {
//		String str = "";
//		if(content != null){
//			str = ClobUtil.getString(content);
//		}else{
//			str = contentView;
//		}
//		return str;
//	}
//
//    public void setContentView(String contentView) {
//        this.contentView = contentView;
//    }
}
