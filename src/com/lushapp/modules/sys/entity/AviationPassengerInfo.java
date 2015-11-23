/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lushapp.common.orm.PropertyType;
import com.lushapp.common.orm.annotation.Delete;
import com.lushapp.common.orm.entity.BaseEntity;
import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 乘客信息AviationPassengerInfo
 * @author honey.zhao@aliyun.com  
 * 2015-11-10 上午12:28:04
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_AVIATION_PASSENGER_INFO")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties (value = { "hibernateLazyInitializer" , "handler","fieldHandler"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class AviationPassengerInfo
        extends BaseEntity
        implements Serializable {

    /**
     * 销售机票订单ID
     */
    private AviationOrder aviationOrder;

    /**
     * 乘客姓名
     */
    private String passengerName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 乘客类型
     */
    private String passengeType;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 身份证号
     */
    private String idcardSn;

    /**
     * 证件有效期
     */
    private Date idcardExpiryDate;

    /**
     * 票号
     */
    private String ticketNumber;

    /**
     * 应收金额
     */
    private Float receivableAmount;

    /**
     * 应付金额
     */
    private Float payableAmount;

    /**
     * 是否删除
     */
    private boolean deleted;

    public AviationPassengerInfo() {
    }

    public AviationPassengerInfo(AviationOrder aviationOrder, String passengerName, String sex, String passengeType, String birthday, String idcardSn, Date idcardExpiryDate, String ticketNumber, Float receivableAmount, Float payableAmount, boolean deleted) {
        this.aviationOrder = aviationOrder;
        this.passengerName = passengerName;
        this.sex = sex;
        this.passengeType = passengeType;
        this.birthday = birthday;
        this.idcardSn = idcardSn;
        this.idcardExpiryDate = idcardExpiryDate;
        this.ticketNumber = ticketNumber;
        this.receivableAmount = receivableAmount;
        this.payableAmount = payableAmount;
        this.deleted = deleted;
    }

    /**
     *------get() and set() methods----------------------
     *
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_aviation_order_id")
    public AviationOrder getAviationOrder() {
        return aviationOrder;
    }

    public void setAviationOrder(AviationOrder aviationOrder) {
        this.aviationOrder = aviationOrder;
    }

    @Column(name = "passenger_name")
    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "passenger_type")
    public String getPassengeType() {
        return passengeType;
    }

    public void setPassengeType(String passengeType) {
        this.passengeType = passengeType;
    }

    @Column(name = "birthday")
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Column(name = "idcard_sn")
    public String getIdcardSn() {
        return idcardSn;
    }

    public void setIdcardSn(String idcardSn) {
        this.idcardSn = idcardSn;
    }

    @Column(name = "idcard_expiry_date")
    public Date getIdcardExpiryDate() {
        return idcardExpiryDate;
    }

    public void setIdcardExpiryDate(Date idcardExpiryDate) {
        this.idcardExpiryDate = idcardExpiryDate;
    }

    @Column(name = "ticket_number")
    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Column(name = "receivable_amount")
    public Float getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(Float receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    @Column(name = "payable_amount")
    public Float getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(Float payableAmount) {
        this.payableAmount = payableAmount;
    }

    @Column(name="deleted",columnDefinition="TINYINT default 0")
    @NotNull
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }



}
