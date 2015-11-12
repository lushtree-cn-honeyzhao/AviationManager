/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.common.model;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * easyui propertygrid.
 * @author honey.zhao@aliyun.com  
 */
@SuppressWarnings("serial")
public class Propertygrid implements Serializable {
    /**
     * 总记录数
     */
    private long total;
    /**
     * 列表 Map包含属性：name、value、group、editor等
     */
    private List<Map<String, Object>> rows = Lists.newArrayList();

    public Propertygrid() {
    }

    public Propertygrid(long total, List<Map<String, Object>> rows) {
        this.total = total;
        this.rows = rows;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public Propertygrid setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public Propertygrid setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
