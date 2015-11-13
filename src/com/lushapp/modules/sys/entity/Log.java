/**
 *  Copyright (c) 2014-2013 http://www.lushapplication.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
package com.lushapp.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lushapp.common.orm.entity.BaseEntity;
import com.lushapp.modules.sys._enum.LogType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统操作日志Entity
 * @author : honey.zhao@aliyun.com  
 * @date: 13-11-27 下午9:18
 */
@Entity
@Table(name = "T_SYS_LOG")
// jackson标记不生成json对象的属性
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Log extends BaseEntity implements Serializable {

    /**
     * 操作日志类型
     * @see com.lushapp.modules.sys._enum.LogType
     */
    private Integer type;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 操作开始时间
     */
    private Date operTime;
    /**
     * 模块
     */
    private String module;
    /**
     * 操作方法
     */
    private String action;
    /**
     * 操作耗时ms
     */
    private String actionTime;
    /**
     * 用户IP地址
     */
    private String ip;
    /**
     * 客户端（浏览器）类型 {@link com.lushapp.common.utils.browser.BrowserType}
     */
    private String browserType;
    /**
     * 备注
     */
    private String remark;

    public Log() {
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 日志类类型 View
     */
    @Transient
    public String getTypeView() {
        LogType s = LogType.getLogType(type);
        String str = "";
        if(s != null){
            str =  s.getDescription();
        }
        return str;
    }

    /**
     * 登录名
     *
     * @return
     */
    @Column(name = "LOGIN_NAME", length = 36)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "OPER_TIME")
    @JsonFormat(pattern = DATE_TIME_FORMAT, timezone = TIMEZONE)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    @Column(name = "MODULE", length = 36)
    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Column(name = "ACTION", length = 255)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Column(name = "ACTION_TIME", length = 20)
    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    @Column(name = "IP", length = 64)
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "BROWSER_TYPE")
    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    @Column(name = "REMARK", length = 1000)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}