/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.modules.sys.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.youyun.common.orm.entity.BaseEntity;
import com.youyun.common.orm.entity.StatusState;
import com.youyun.common.utils.ConvertUtils;
import com.youyun.common.utils.collections.Collections3;
import com.youyun.modules.sys._enum.OrganType;
import com.youyun.utils.CacheConstants;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * 组织组机构实体类.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_SYS_ORGAN")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" , "handler","fieldHandler" ,
        "parentOrgan","subOrgans", "users","superManagerUser"})
public class Organ extends BaseEntity implements Serializable {

    /**
     * 机构名称
     */
    private String name;
    /**
     * 机构编码
     */
    private String code;
    /**
     * 机构系统编码
     */
    private String sysCode;
    /**
     * 机构类型 {@link OrganType}
     */
    private Integer type = OrganType.organ.getValue();
    /**
     * 地址
     */
    private String address;
    /**
     * 父级组织机构
     */
    private Organ parentOrgan;
    /**
     * 父级OrganId @Transient
     */
    private Long _parentId;
    /**
     * 子级组织机构
     */
    private List<Organ> subOrgans = Lists.newArrayList();
    /**
     * 分管领导
     */
    private User superManagerUser;
    /**
     * 机构负责人
     */
    private Long managerUserId;

    /**
     * 机构负责人登录名 @Transient
     */
    private String managerUserLoginName;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 传真号
     */
    private String fax;
    /**
     * 排序
     */
    private Integer orderNo;
    /**
     * 机构用户ID @Transient
     */
    private List<User> users = Lists.newArrayList();

    /**
     * 机构用户
     */
    private List<Long> userIds = Lists.newArrayList();
    /**
     * 部门岗位
     */
    private List<Post> posts = Lists.newArrayList();


    public Organ() {
    }

    @Column(name = "NAME",nullable = false, length = 255, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "SYS_CODE",length = 36)
    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    @Column(name = "CODE",length = 36)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 资源类型 显示
     */
    @Transient
    public String getTypeView() {
        OrganType r = OrganType.getOrganType(type);
        String str = "";
        if(r != null){
            str = r.getDescription();
        }
        return str;
    }

    @Column(name = "ADDRESS",length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "PHONE",length = 64)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "FAX",length = 64)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "ORDER_NO")
    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name = "MANAGER_USER_ID")
    public Long getManagerUserId() {
        return managerUserId;
    }

    public void setManagerUserId(Long managerUserId) {
        this.managerUserId = managerUserId;
    }

    @Transient
    public String getManagerUserLoginName() {
        if(!Collections3.isEmpty(users)) {
            for(User user:users){
                 if(managerUserId != null && user.getId().equals(managerUserId)){
                     managerUserLoginName = user.getLoginName();
                 }
            }
        }

        return managerUserLoginName;
    }

    public void setManagerUserLoginName(String managerUserLoginName) {
        this.managerUserLoginName = managerUserLoginName;
    }

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "SUPER_MANAGER_USER_ID")
    public User getSuperManagerUser() {
        return superManagerUser;
    }

    public void setSuperManagerUser(User superManagerUser) {
        this.superManagerUser = superManagerUser;
    }

    /**
     * 分管领导名称
     * @return
     */
    @Transient
    public String getSuperManagerUserName() {
        if (superManagerUser != null) {
            return superManagerUser.getName();
        }
        return null;
    }

    /**
     * 分管领导名称
     * @return
     */
    @Transient
    public String getSuperManagerUserLoginName() {
        if (superManagerUser != null) {
            return superManagerUser.getLoginName();
        }
        return null;
    }

    /**
     * 分管领导ID
     * @return
     */
    @Transient
    public Long getSuperManagerUserId() {
        if (superManagerUser != null) {
            return superManagerUser.getId();
        }
        return null;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "T_SYS_USER_ORGAN", joinColumns = {@JoinColumn(name = "ORGAN_ID")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    @Fetch(FetchMode.SUBSELECT)
    @Where(clause = "status = 0")
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    /**
     * 组织机构拥有的用户id字符串，多个用户id以","分割
     *
     * @return
     */
    @Transient
    @SuppressWarnings("unchecked")
    public List<Long> getUserIds() {
        if(!Collections3.isEmpty(users)){
            userIds =  ConvertUtils.convertElementPropertyToList(users, "id");
        }
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    /**
     * 组织机构拥有的用户登录名字符串，多个用户登录名以","分割
     *
     * @return
     */
    @Transient
    public String getUserLoginNames() {
        return ConvertUtils.convertElementPropertyToString(users, "loginName", ", ");
    }

    /**
     * 组织机构拥有的用户姓名字符串，多个用户登录名以","分割
     *
     * @return
     */
    @Transient
    public String getUserNames() {
        return ConvertUtils.convertElementPropertyToString(users, "name", ", ");
    }


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "PARENT_ID")
    public Organ getParentOrgan() {
        return parentOrgan;
    }

    public void setParentOrgan(Organ parentOrgan) {
        this.parentOrgan = parentOrgan;
    }

    @Transient
    public Long get_parentId() {
        if(parentOrgan != null){
            _parentId = parentOrgan.getId();
        }
        return _parentId;
    }

    public void set_parentId(Long _parentId) {
        this._parentId = _parentId;
    }

    @OneToMany(mappedBy = "parentOrgan", cascade = {CascadeType.REMOVE})
    public List<Organ> getSubOrgans() {
        return subOrgans;
    }

    public void setSubOrgans(List<Organ> subOrgans) {
        this.subOrgans = subOrgans;
    }

    @OneToMany(mappedBy = "organ", cascade = {CascadeType.REMOVE})
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}