/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.common.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * easyui可装载组合框combobox模型.
 * 
 * @author : honey.zhao@aliyun.com  
 * @date 2014-10-11 下午10:13:25
 */
public class Combobox {

	/**
	 * 值域
	 */
	private String value;
	/**
	 * 文本域
	 */
	private String text;
    /**
     * 分组
     */
    private String group;
	/**
	 * 是否选中
	 */
	private boolean selected;

	public Combobox() {
		super();
	}

    /**
     *
     * @param value
     *            值域
     * @param text
     *            文本域
     */
    public Combobox(String value, String text) {
        super();
        this.value = value;
        this.text = text;
    }

	/**
	 * 
	 * @param value
	 *            值域
	 * @param text
	 *            文本域
     * @param group
     *            分组
	 */
	public Combobox(String value, String text, String group) {
		super();
		this.value = value;
		this.text = text;
        this.group = group;
	}

	/**
	 * 
	 * @param value
	 *            值域
	 * @param text
	 *            文本域
	 * @param group
	 *            分组
     * @param selected
     *            是否选中
	 */
	public Combobox(String value, String text, String group, boolean selected) {
		super();
		this.value = value;
		this.text = text;
        this.group = group;
		this.selected = selected;
	}

	/**
	 * 值域
	 */
	public String getValue() {
		return value;
	}
    /**
     * 设置值域
     * @param value   文本域
     */
	public Combobox setValue(String value) {
		this.value = value;
        return this;
	}

	/**
	 * 文本域
	 */
	public String getText() {
		return text;
	}

    /**
     * 设置文本域
     * @param text   文本域
     */
	public Combobox setText(String text) {
		this.text = text;
        return this;
	}


    /**
     * 分组
     * @return
     */
    public String getGroup() {
        return group;
    }

    /**
     * 设置分组
     * @param group
     */
    public Combobox setGroup(String group) {
        this.group = group;
        return this;
    }

    /**
	 * 是否选中
	 */
	public boolean isSelected() {
		return selected;
	}

	public Combobox setSelected(boolean selected) {
		this.selected = selected;
        return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
