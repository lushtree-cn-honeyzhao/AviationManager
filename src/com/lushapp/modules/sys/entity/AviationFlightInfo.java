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
 * 航班信息AviationFlightInfo.
 * @author honey.zhao@aliyun.com  
 * 2015-11-10 上午12:28:04
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_AVIATION_FLIGHT_INFO")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties (value = { "hibernateLazyInitializer" , "handler","fieldHandler"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class AviationFlightInfo
        extends BaseEntity
        implements Serializable {

    /**
     * 销售机票订单ID
     */
    private AviationOrder aviationOrder;

    /**
     * 承运人
     */
    private String carrier;

    /**
     * 航班号
     */
    private String number;

    /**
     * 出发地
     */
    private String starting;

    /**
     * 到达地
     */
    private String arrival;

    /**
     * 仓位
     */
    private String positions;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 到达时间
     */
    private Date endTime;

    /**
     * 出发航站楼
     */
    private String startAirportTerminal;

    /**
     * 到达航站楼
     */
    private String arrivalAirporTterminal;


    /**
     * 是否删除
     */
    private boolean deleted;


    public AviationFlightInfo() {
    }

    public AviationFlightInfo(AviationOrder aviationOrder, String carrier, String number, String starting, String arrival, String positions, Date startTime, Date endTime, String startAirportTerminal, String arrivalAirporTterminal, boolean deleted) {
        this.aviationOrder = aviationOrder;
        this.carrier = carrier;
        this.number = number;
        this.starting = starting;
        this.arrival = arrival;
        this.positions = positions;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startAirportTerminal = startAirportTerminal;
        this.arrivalAirporTterminal = arrivalAirporTterminal;
        this.deleted = deleted;
    }

    /**
     * ---------get() and set() methods-------------------
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "t_aviation_order_id")
    public AviationOrder getAviationOrder() {
        return aviationOrder;
    }

    public void setAviationOrder(AviationOrder aviationOrder) {
        this.aviationOrder = aviationOrder;
    }

    @Column(name = "carrier",nullable = true,length = 45)
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @Column(name = "number",nullable = true,length = 45)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "starting",nullable = true,length = 45)
    public String getStarting() {
        return starting;
    }

    public void setStarting(String starting) {
        this.starting = starting;
    }

    @Column(name = "arrival",nullable = true,length = 45)
    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    @Column(name = "positions",nullable = true,length = 45)
    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    @Column(name = "start_time",nullable = true)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time",nullable = true)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "start_airport_terminal",nullable = true,length = 45)
    public String getStartAirportTerminal() {
        return startAirportTerminal;
    }

    public void setStartAirportTerminal(String startAirportTerminal) {
        this.startAirportTerminal = startAirportTerminal;
    }

    @Column(name = "arrival_airport_terminal",nullable = true,length = 45)
    public String getArrivalAirporTterminal() {
        return arrivalAirporTterminal;
    }

    public void setArrivalAirporTterminal(String arrivalAirporTterminal) {
        this.arrivalAirporTterminal = arrivalAirporTterminal;
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
