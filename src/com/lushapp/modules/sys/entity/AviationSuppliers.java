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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
         * url
         */
        private String companyUrl;
        /**
         * QQ
         */
        private String qq;

        /**
         * 出票评级
         */
        private  String ticketRate;

        /**
         * 负债
         */
        private Float liabilities;

        /**
         * 出票提醒邮箱
         */
        private String ticketRemindEmail;

        /**
         *email
         */
        private String email;

        /**
         *telephone
         */
        private String telephone;

        /**
         *service_email
         */
        private String service_email;

        /**
         *service_time
         */
        private String service_time;

        /**
         *custom_handle
         */
        private String custom_handle;

        /**
         *city
         */
        private String city;

        /**
         *area
         */
        private String area;

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

        public AviationSuppliers() {
        }

        public AviationSuppliers(String userName, String password, String confirmPassword, String suppliersType, String suppliersName, String officeSn, boolean effected, Integer autoPaying, Date workTime, String contactPerson, String mobile, String faxNumber, String companyName, String companAaddress, String companyUrl, String qq, String ticketRate, float liabilities, String ticketRemindEmail, String email, String telephone, String service_email, String service_time, String custom_handle, String city, String area, String invalid_ticket_worktime, String service_ticket_begintime, String service_ticket_endtime) {
                this.userName = userName;
                this.password = password;
                this.confirmPassword = confirmPassword;
                this.suppliersType = suppliersType;
                this.suppliersName = suppliersName;
                this.officeSn = officeSn;
                this.effected = effected;
                this.autoPaying = autoPaying;
                this.workTime = workTime;
                this.contactPerson = contactPerson;
                this.mobile = mobile;
                this.faxNumber = faxNumber;
                this.companyName = companyName;
                this.companAaddress = companAaddress;
                this.companyUrl = companyUrl;
                this.qq = qq;
                this.ticketRate = ticketRate;
                this.liabilities = liabilities;
                this.ticketRemindEmail = ticketRemindEmail;
                this.email = email;
                this.telephone = telephone;
                this.service_email = service_email;
                this.service_time = service_time;
                this.custom_handle = custom_handle;
                this.city = city;
                this.area = area;
                this.invalid_ticket_worktime = invalid_ticket_worktime;
                this.service_ticket_begintime = service_ticket_begintime;
                this.service_ticket_endtime = service_ticket_endtime;
        }


        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getConfirmPassword() {
                return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
                this.confirmPassword = confirmPassword;
        }

        public String getSuppliersType() {
                return suppliersType;
        }

        public void setSuppliersType(String suppliersType) {
                this.suppliersType = suppliersType;
        }

        public String getSuppliersName() {
                return suppliersName;
        }

        public void setSuppliersName(String suppliersName) {
                this.suppliersName = suppliersName;
        }

        public String getOfficeSn() {
                return officeSn;
        }

        public void setOfficeSn(String officeSn) {
                this.officeSn = officeSn;
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

        public Date getWorkTime() {
                return workTime;
        }

        public void setWorkTime(Date workTime) {
                this.workTime = workTime;
        }

        public String getContactPerson() {
                return contactPerson;
        }

        public void setContactPerson(String contactPerson) {
                this.contactPerson = contactPerson;
        }

        public String getMobile() {
                return mobile;
        }

        public void setMobile(String mobile) {
                this.mobile = mobile;
        }

        public String getFaxNumber() {
                return faxNumber;
        }

        public void setFaxNumber(String faxNumber) {
                this.faxNumber = faxNumber;
        }

        public String getCompanyName() {
                return companyName;
        }

        public void setCompanyName(String companyName) {
                this.companyName = companyName;
        }

        public String getCompanAaddress() {
                return companAaddress;
        }

        public void setCompanAaddress(String companAaddress) {
                this.companAaddress = companAaddress;
        }

        public String getCompanyUrl() {
                return companyUrl;
        }

        public void setCompanyUrl(String companyUrl) {
                this.companyUrl = companyUrl;
        }

        public String getQq() {
                return qq;
        }

        public void setQq(String qq) {
                this.qq = qq;
        }

        public String getTicketRate() {
                return ticketRate;
        }

        public void setTicketRate(String ticketRate) {
                this.ticketRate = ticketRate;
        }

        public float getLiabilities() {
                return liabilities;
        }

        public void setLiabilities(float liabilities) {
                this.liabilities = liabilities;
        }

        public String getTicketRemindEmail() {
                return ticketRemindEmail;
        }

        public void setTicketRemindEmail(String ticketRemindEmail) {
                this.ticketRemindEmail = ticketRemindEmail;
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

        public String getService_email() {
                return service_email;
        }

        public void setService_email(String service_email) {
                this.service_email = service_email;
        }

        public String getService_time() {
                return service_time;
        }

        public void setService_time(String service_time) {
                this.service_time = service_time;
        }

        public String getCustom_handle() {
                return custom_handle;
        }

        public void setCustom_handle(String custom_handle) {
                this.custom_handle = custom_handle;
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
}
