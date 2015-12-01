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
import com.lushapp.modules.sys._enum.CreditCertification;
import com.lushapp.modules.sys._enum.FreezeStatus;
import com.lushapp.modules.sys._enum.SettlementType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 采购商 AviationBuyers
 * @author honey.zhao@aliyun.com  
 * 2015-11-10 上午12:28:04
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_AVIATION_BUYERS")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties (value = { "hibernateLazyInitializer" , "handler","fieldHandler"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class AviationBuyers
        extends BaseEntity
        implements Serializable {

        /**
         * 用户名
         */
        private String user_name;

        /**
         * 密码
         */
        private String password;

        /**
         * 确认密码
         */
        private String confirm_password;

        /**
         * 采购商名称
         */
        private String buyers_name;

        /**
         * office号
         */
        private String office_sn;
        /**
         * 公司名称
         */
        private String company_name;
        /**
         * 公司地址
         */
        private String company_address;
        /**
         * 联系人
         */
        private String contact_person;
        /**
         * 手机号
         */
        private String mobile;
        /**
         *传真号
         */
        private String fax_number;
        /**
         *服务时间
         */
        private String serverTime;
        /**
         * url
         */
        private String company_url;
        /**
         * email
         */
        private String email;
        /**
         * 固定电话
         */
        private String telephone;
        /**
         * 服务联系邮箱
         */
        private String server_email;

        /**
         * 结算方式
         */
        private Integer  settlementType = SettlementType.day_sett.getValue();

        /**
         * qq
         */
        private String qq;

        /**
         *客户经理
         */
        private String customerManager;

        /**
         * 授权认证
         */
        private Integer creditCertification = CreditCertification.unable_certification.getValue();

        /**
         * 城市
         */
        private String city;

        private String area;


        /**
         *授信余额
         */
        private Float creditBalance;


        /**
         * 工作时间
         */
        private Date workTime;

        /**
         * 出票评级
         */
        private String ticketRate;
        /**
         * 临时额度
         */
        private Float tempAmount;

        /**
         * 负债
         */
        private String liabilities;

        /**
         * 手续费费率
         */
        private Float factorageRate;

        /**
         * 最高费率
         */
        private Float maxFactorageRate;

        /**
         * 是否有效
         */
        private boolean effected;
        /**
         * 是否允许 自动支付
         */
        private Integer autoPaying;
        /**
         * 出票提醒邮箱
         */
        private String ticketRemindEmail;

        /**
         *invalid_ticket_worktime
         */
        private String invalid_ticket_worktime;

        /**
         *service_ticket_begintime
         */
        private String service_ticket_begintime;

        /**
         *service_ticket_endtime
         */
        private String service_ticket_endtime;

        private  String remark;

        /**
         * 冻结状态
         */
        private Integer freezeStatus = FreezeStatus.unfreezed.getValue();

        public AviationBuyers() {
        }

        public AviationBuyers(String user_name, String password, String confirm_password, String buyers_name, String office_sn, String company_name, String company_address, String contact_person, String mobile, String fax_number, String serverTime, String company_url, String email, String telephone, String server_email, Integer settlementType, String qq, String customerManager, Integer creditCertification, String city, String area, Float creditBalance, Date workTime, String ticketRate, Float tempAmount, String liabilities, Float factorageRate, Float maxFactorageRate, boolean effected, Integer autoPaying, String ticketRemindEmail, String invalid_ticket_worktime, String service_ticket_begintime, String service_ticket_endtime, String remark, Integer freezeStatus) {
                this.user_name = user_name;
                this.password = password;
                this.confirm_password = confirm_password;
                this.buyers_name = buyers_name;
                this.office_sn = office_sn;
                this.company_name = company_name;
                this.company_address = company_address;
                this.contact_person = contact_person;
                this.mobile = mobile;
                this.fax_number = fax_number;
                this.serverTime = serverTime;
                this.company_url = company_url;
                this.email = email;
                this.telephone = telephone;
                this.server_email = server_email;
                this.settlementType = settlementType;
                this.qq = qq;
                this.customerManager = customerManager;
                this.creditCertification = creditCertification;
                this.city = city;
                this.area = area;
                this.creditBalance = creditBalance;
                this.workTime = workTime;
                this.ticketRate = ticketRate;
                this.tempAmount = tempAmount;
                this.liabilities = liabilities;
                this.factorageRate = factorageRate;
                this.maxFactorageRate = maxFactorageRate;
                this.effected = effected;
                this.autoPaying = autoPaying;
                this.ticketRemindEmail = ticketRemindEmail;
                this.invalid_ticket_worktime = invalid_ticket_worktime;
                this.service_ticket_begintime = service_ticket_begintime;
                this.service_ticket_endtime = service_ticket_endtime;
                this.remark = remark;
                this.freezeStatus = freezeStatus;
        }

        public String getUser_name() {
                return user_name;
        }

        public void setUser_name(String user_name) {
                this.user_name = user_name;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getConfirm_password() {
                return confirm_password;
        }

        public void setConfirm_password(String confirm_password) {
                this.confirm_password = confirm_password;
        }

        public String getBuyers_name() {
                return buyers_name;
        }

        public void setBuyers_name(String buyers_name) {
                this.buyers_name = buyers_name;
        }

        public String getOffice_sn() {
                return office_sn;
        }

        public void setOffice_sn(String office_sn) {
                this.office_sn = office_sn;
        }

        public String getCompany_name() {
                return company_name;
        }

        public void setCompany_name(String company_name) {
                this.company_name = company_name;
        }

        public String getCompany_address() {
                return company_address;
        }

        public void setCompany_address(String company_address) {
                this.company_address = company_address;
        }

        public String getContact_person() {
                return contact_person;
        }

        public void setContact_person(String contact_person) {
                this.contact_person = contact_person;
        }

        public String getMobile() {
                return mobile;
        }

        public void setMobile(String mobile) {
                this.mobile = mobile;
        }

        public String getFax_number() {
                return fax_number;
        }

        public void setFax_number(String fax_number) {
                this.fax_number = fax_number;
        }

        public String getServerTime() {
                return serverTime;
        }

        public void setServerTime(String serverTime) {
                this.serverTime = serverTime;
        }

        public String getCompany_url() {
                return company_url;
        }

        public void setCompany_url(String company_url) {
                this.company_url = company_url;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getTelephone() {
                return telephone;
        }

        public void setTelephone(String telephone) {
                this.telephone = telephone;
        }

        public String getServer_email() {
                return server_email;
        }

        public void setServer_email(String server_email) {
                this.server_email = server_email;
        }

        public Integer getSettlementType() {
                return settlementType;
        }

        public void setSettlementType(Integer settlementType) {
                this.settlementType = settlementType;
        }

        public String getQq() {
                return qq;
        }

        public void setQq(String qq) {
                this.qq = qq;
        }

        public String getCustomerManager() {
                return customerManager;
        }

        public void setCustomerManager(String customerManager) {
                this.customerManager = customerManager;
        }

        public Integer getCreditCertification() {
                return creditCertification;
        }

        public void setCreditCertification(Integer creditCertification) {
                this.creditCertification = creditCertification;
        }

        public String getCity() {
                return city;
        }

        public void setCity(String city) {
                this.city = city;
        }

        public String getArea() {
                return area;
        }

        public void setArea(String area) {
                this.area = area;
        }

        public Float getCreditBalance() {
                return creditBalance;
        }

        public void setCreditBalance(Float creditBalance) {
                this.creditBalance = creditBalance;
        }

        public Date getWorkTime() {
                return workTime;
        }

        public void setWorkTime(Date workTime) {
                this.workTime = workTime;
        }

        public String getTicketRate() {
                return ticketRate;
        }

        public void setTicketRate(String ticketRate) {
                this.ticketRate = ticketRate;
        }

        public Float getTempAmount() {
                return tempAmount;
        }

        public void setTempAmount(Float tempAmount) {
                this.tempAmount = tempAmount;
        }

        public String getLiabilities() {
                return liabilities;
        }

        public void setLiabilities(String liabilities) {
                this.liabilities = liabilities;
        }

        public Float getFactorageRate() {
                return factorageRate;
        }

        public void setFactorageRate(Float factorageRate) {
                this.factorageRate = factorageRate;
        }

        public Float getMaxFactorageRate() {
                return maxFactorageRate;
        }

        public void setMaxFactorageRate(Float maxFactorageRate) {
                this.maxFactorageRate = maxFactorageRate;
        }

        public boolean isEffected() {
                return effected;
        }

        public void setEffected(boolean effected) {
                this.effected = effected;
        }

        public Integer getAutoPaying() {
                return autoPaying;
        }

        public void setAutoPaying(Integer autoPaying) {
                this.autoPaying = autoPaying;
        }

        public String getTicketRemindEmail() {
                return ticketRemindEmail;
        }

        public void setTicketRemindEmail(String ticketRemindEmail) {
                this.ticketRemindEmail = ticketRemindEmail;
        }

        public String getInvalid_ticket_worktime() {
                return invalid_ticket_worktime;
        }

        public void setInvalid_ticket_worktime(String invalid_ticket_worktime) {
                this.invalid_ticket_worktime = invalid_ticket_worktime;
        }

        public String getService_ticket_begintime() {
                return service_ticket_begintime;
        }

        public void setService_ticket_begintime(String service_ticket_begintime) {
                this.service_ticket_begintime = service_ticket_begintime;
        }

        public String getService_ticket_endtime() {
                return service_ticket_endtime;
        }

        public void setService_ticket_endtime(String service_ticket_endtime) {
                this.service_ticket_endtime = service_ticket_endtime;
        }

        public String getRemark() {
                return remark;
        }

        public void setRemark(String remark) {
                this.remark = remark;
        }

        public Integer getFreezeStatus() {
                return freezeStatus;
        }

        public void setFreezeStatus(Integer freezeStatus) {
                this.freezeStatus = freezeStatus;
        }
}
