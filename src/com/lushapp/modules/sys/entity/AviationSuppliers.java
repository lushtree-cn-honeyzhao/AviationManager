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
import java.util.Date;

/**
 * 供应商AviationSuppliers
 * @author honey.zhao@aliyun.com  
 * 2015-11-10 上午12:28:04
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_AVIATION_SUPPLIERS")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties (value = { "hibernateLazyInitializer" , "handler","fieldHandler"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class AviationSuppliers
        extends BaseEntity
        implements Serializable {


        /**
         * 姓名
         */
        private String userName;

        /**
         * 密码
         */
        private String password;

        /**
         * 确认密码
         */
        private String confirmPassword;

        /**
         * 供应商类型
         */
        private String suppliersType;

        /**
         * 供应商名称
         */
        private  String suppliersName;

        /**
         * 办公编号
         */
        private  String officeSn;

        /**
         * 是否有效
         */
        private boolean effected;

        /**
         * 是否允许 自动支付
         */
        private Integer autoPaying;

        /**
         * 工作时间
         */
        private Date workTime;

        /**
         * 联系人
         */
        private String contactPerson;

        /**
         * 手机号
         */
        private  String mobile;

        /**
         * 传真
         */
        private String faxNumber;

        /**
         * 公司名称
         */
        private String companyName;

        /**
         * 公司地址
         */
        private  String companAaddress;

        /**
         * QQ
         */
        private String qq;

        /**
         * 出票评级
         */
        private  String ticketRate;

        

















}
