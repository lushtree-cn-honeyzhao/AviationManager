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
import com.lushapp.modules.sys._enum.FareMatch;
import com.lushapp.modules.sys._enum.OrderStatus;
import com.lushapp.modules.sys._enum.OrderType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 机票销售订单AviationOrder.
 * @author honey.zhao@aliyun.com  
 * 2015-11-10 上午12:28:04
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_AVIATION_ORDER")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties (value = { "hibernateLazyInitializer" , "handler","fieldHandler"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class AviationOrder
        extends BaseEntity
        implements Serializable {

    /**
     * 订单号
     */
    private String orderSn;
    /**
     * 订单类型 国际(1) 国内(2)
     */
    private Integer orderType = OrderType.domestic.getValue();
    /**
     * PRN
     */
    private String prn;
    /**
     * 外开(1) 自开(2)
     */
    private Integer fareMatch = FareMatch.selfout.getValue();

    /**
     * 客户
     */
    private String custom;
    /**
     * 平台供应商
     */
    private String supplier;
    /**
     * ------------成人------------------
     * 成人票面
     */
    private Float grownFaceAmount;
    /**
     * 成人税收
     */
    private Float grownInlandRevenue;
    /**
     * 成人Q税
     */
    private Float grownQTax;
    /**
     * 代理费
     */
    private Float grownAgencyFee;
    /**
     * 成人返回金额
     */
    private Float grownReturnFee;
    /**
     * ------------儿童------------------
     * 儿童票面
     */
    private Float childFaceAmount;
    /**
     * 儿童税收
     */
    private Float childInlandRevenue;
    /**
     * 儿童Q税
     */
    private Float childQTax;
    /**
     * 儿童代理费
     */
    private Float childAgencyFee;
    /**
     * 儿童返现金额
     */
    private Float childReturnFee;
    /**
     * 手续费
     */
    private Float  handlingCharges;
    /**
     * 客户应收
     */
    private Float customReceivable;
    /**
     * 客户实际应收
     */
    private Float customRealReceivable;
    /**
     * 平台应收
     */
    private Float platformReceivable;
    /**
     * 平台实收
     */
    private Float platformRealReceivable;
    /**
     * 收款方式
     */
    private String receivableType;
    /**
     * 收款账号
     */
    private String receivableSn;
    /**
     * 付款方式
     */
    private String paymentType;
    /**
     * 付款账号
     */
    private String paymentSn;
    /**
     * 大编码
     */
    private String bigSn;
    /**
     * 备注
     */
    private String remarks;
    /**
     * rtkt信息
     */
    private String rtktRemarks;

    /**
     *订单状态:
     *1:待出票
     *2:已出票
     *3:待退票
     *4:已退票
     *5:待改签
     *6:已改签
     *7:待废票
     *8:已废票
     *101:未收审
     *102:已收审
     *201:未付审
     *202:已付审
     *301:未终审
     *302:已终审
     */
    private Integer orderstatus = OrderStatus.undetermined_ticket.getValue();

    /**
     * ------------get()and set() methods ------------------
     */


    public AviationOrder() {

    }

    public AviationOrder(String orderSn, Integer orderType, String prn, Integer fareMatch, String custom, String supplier, Float grownFaceAmount, Float grownInlandRevenue, Float grownQTax, Float grownAgencyFee, Float grownReturnFee, Float childFaceAmount, Float childInlandRevenue, Float childQTax, Float childAgencyFee, Float childReturnFee, Float handlingCharges, Float customReceivable, Float customRealReceivable, Float platformReceivable, Float platformRealReceivable, String receivableType, String receivableSn, String paymentType, String paymentSn, String bigSn, String remarks, String rtktRemarks, Integer orderstatus) {
        this.orderSn = orderSn;
        this.orderType = orderType;
        this.prn = prn;
        this.fareMatch = fareMatch;
        this.custom = custom;
        this.supplier = supplier;
        this.grownFaceAmount = grownFaceAmount;
        this.grownInlandRevenue = grownInlandRevenue;
        this.grownQTax = grownQTax;
        this.grownAgencyFee = grownAgencyFee;
        this.grownReturnFee = grownReturnFee;
        this.childFaceAmount = childFaceAmount;
        this.childInlandRevenue = childInlandRevenue;
        this.childQTax = childQTax;
        this.childAgencyFee = childAgencyFee;
        this.childReturnFee = childReturnFee;
        this.handlingCharges = handlingCharges;
        this.customReceivable = customReceivable;
        this.customRealReceivable = customRealReceivable;
        this.platformReceivable = platformReceivable;
        this.platformRealReceivable = platformRealReceivable;
        this.receivableType = receivableType;
        this.receivableSn = receivableSn;
        this.paymentType = paymentType;
        this.paymentSn = paymentSn;
        this.bigSn = bigSn;
        this.remarks = remarks;
        this.rtktRemarks = rtktRemarks;
        this.orderstatus = orderstatus;
    }

    @Column(name = "order_sn", length = 200,nullable = true)
    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    @Column(name = "order_type",nullable = true)
    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    @Column(name = "prn",nullable = true,columnDefinition = "text(2000)")
    public String getPrn() {
        return prn;
    }

    public void setPrn(String prn) {
        this.prn = prn;
    }
    @Column(name = "fare_match",nullable = true)
    public Integer getFareMatch() {
        return fareMatch;
    }

    public void setFareMatch(Integer fareMatch) {
        this.fareMatch = fareMatch;
    }

    @Column(name = "custom",nullable = true)
    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    @Column(name = "supplier",nullable = true)
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Column(name = "grown_face_amount",nullable = true)
    public Float getGrownFaceAmount() {
        return grownFaceAmount;
    }

    public void setGrownFaceAmount(Float grownFaceAmount) {
        this.grownFaceAmount = grownFaceAmount;
    }

    @Column(name = "grown_inland_revenue",nullable = true)
    public Float getGrownInlandRevenue() {
        return grownInlandRevenue;
    }

    public void setGrownInlandRevenue(Float grownInlandRevenue) {
        this.grownInlandRevenue = grownInlandRevenue;
    }

    @Column(name = "grown_q_tax",nullable = true)
    public Float getGrownQTax() {
        return grownQTax;
    }

    public void setGrownQTax(Float grownQTax) {
        this.grownQTax = grownQTax;
    }

    @Column(name = "grown_agency_fee",nullable = true)
    public Float getGrownAgencyFee() {
        return grownAgencyFee;
    }

    public void setGrownAgencyFee(Float grownAgencyFee) {
        this.grownAgencyFee = grownAgencyFee;
    }

    @Column(name = "grown_return_fee",nullable = true)
    public Float getGrownReturnFee() {
        return grownReturnFee;
    }

    public void setGrownReturnFee(Float grownReturnFee) {
        this.grownReturnFee = grownReturnFee;
    }

    @Column(name = "child_face_amount",nullable = true)
    public Float getChildFaceAmount() {
        return childFaceAmount;
    }

    public void setChildFaceAmount(Float childFaceAmount) {
        this.childFaceAmount = childFaceAmount;
    }

    @Column(name = "child_inland_revenue",nullable = true)
    public Float getChildInlandRevenue() {
        return childInlandRevenue;
    }

    public void setChildInlandRevenue(Float childInlandRevenue) {
        this.childInlandRevenue = childInlandRevenue;
    }

    @Column(name = "child_q_tax",nullable = true)
    public Float getChildQTax() {
        return childQTax;
    }

    public void setChildQTax(Float childQTax) {
        this.childQTax = childQTax;
    }

    @Column(name = "child_agency_fee",nullable = true)
    public Float getChildAgencyFee() {
        return childAgencyFee;
    }

    public void setChildAgencyFee(Float childAgencyFee) {
        this.childAgencyFee = childAgencyFee;
    }

    @Column(name = "child_return_fee",nullable = true)
    public Float getChildReturnFee() {
        return childReturnFee;
    }

    public void setChildReturnFee(Float childReturnFee) {
        this.childReturnFee = childReturnFee;
    }

    @Column(name = "handling_charges",nullable = true)
    public Float getHandlingCharges() {
        return handlingCharges;
    }

    public void setHandlingCharges(Float handlingCharges) {
        this.handlingCharges = handlingCharges;
    }

    @Column(name = "custom_receivable",nullable = true)
    public Float getCustomReceivable() {
        return customReceivable;
    }

    public void setCustomReceivable(Float customReceivable) {
        this.customReceivable = customReceivable;
    }

    @Column(name = "custom_real_receivable",nullable = true)
    public Float getCustomRealReceivable() {
        return customRealReceivable;
    }

    public void setCustomRealReceivable(Float customRealReceivable) {
        this.customRealReceivable = customRealReceivable;
    }

    @Column(name = "platform_receivable",nullable = true)
    public Float getPlatformReceivable() {
        return platformReceivable;
    }

    public void setPlatformReceivable(Float platformReceivable) {
        this.platformReceivable = platformReceivable;
    }

    @Column(name = "platform_real_receivable",nullable = true)
    public Float getPlatformRealReceivable() {
        return platformRealReceivable;
    }

    public void setPlatformRealReceivable(Float platformRealReceivable) {
        this.platformRealReceivable = platformRealReceivable;
    }

    @Column(name = "receivable_type",nullable = true)
    public String getReceivableType() {
        return receivableType;
    }

    public void setReceivableType(String receivableType) {
        this.receivableType = receivableType;
    }

    @Column(name = "receivable_sn",nullable = true)
    public String getReceivableSn() {
        return receivableSn;
    }

    public void setReceivableSn(String receivableSn) {
        this.receivableSn = receivableSn;
    }

    @Column(name = "payment_type",nullable = true)
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Column(name = "payment_sn",nullable = true)
    public String getPaymentSn() {
        return paymentSn;
    }

    public void setPaymentSn(String paymentSn) {
        this.paymentSn = paymentSn;
    }

    @Column(name = "big_sn",nullable = true)
    public String getBigSn() {
        return bigSn;
    }

    public void setBigSn(String bigSn) {
        this.bigSn = bigSn;
    }

    @Column(name = "remarks",nullable = true)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    @Column(name = "rtkt_remarks",nullable = true)
    public String getRtktRemarks() {
        return rtktRemarks;
    }

    public void setRtktRemarks(String rtktRemarks) {
        this.rtktRemarks = rtktRemarks;
    }

    @Column(name = "order_status",nullable = true)
    public Integer getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Integer orderstatus) {
        this.orderstatus = orderstatus;
    }
}
