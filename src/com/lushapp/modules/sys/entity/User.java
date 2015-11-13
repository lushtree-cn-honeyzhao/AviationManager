/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.sun.istack.internal.NotNull;
import com.lushapp.common.orm.PropertyType;
import com.lushapp.common.orm.annotation.Delete;
import com.lushapp.common.orm.entity.BaseEntity;
import com.lushapp.common.utils.ConvertUtils;
import com.lushapp.common.utils.collections.Collections3;
import com.lushapp.core.security.SecurityConstants;
import com.lushapp.modules.sys._enum.SexType;

/**
 * 用户管理User.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-21 上午12:28:04 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_SYS_USER")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties (value = { "hibernateLazyInitializer" , "handler","fieldHandler",
        "resources","roles","defaultOrgan","organs"})
//逻辑删除注解标记 propertyName:字段名 value:删除标记的值（使用默认值"1"） type:属性类型
@Delete(propertyName = "status",type = PropertyType.I)
@JsonFilter(" ")
public class User
        extends BaseEntity
        implements Serializable {
	
    /**
     * 登录用户
     */
    private String loginName;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 性别 女(0) 男(1) 保密(2) 默认：保密
     */
    private Integer sex = SexType.secrecy.getValue();

    /**
     * 邮件 以 ","分割
     */
    private String email;
    /**
     * 住址
     */
    private String address;
    /**
     * 住宅电话 以 ","分割
     */
    private String tel;
    /**
     * 手机号 以 ","分割
     */
    private String mobilephone;
    
    /**
     * 1:个人用户 2：企业用户
     */
    private Integer userType;
    /**
     * 有序的关联对象集合
     */
    private List<Role> roles = Lists.newArrayList();
    /**
     * 有序的关联Role对象id集合
     */
    private List<Long> roleIds = Lists.newArrayList();

    /**
     * 资源 有序的关联对象集合
     */
    private List<Resource> resources = Lists.newArrayList();
    /**
     * 资源 id集合  @Transient
     */
    private List<Long> resourceIds = Lists.newArrayList();

    /**
     * 默认组织机构
     */
    private Organ defaultOrgan;
    /**
     * 默认组织机构 @Transient
     */
    private Long defaultOrganId;

    /**
     * 组织机构
     */
    private List<Organ> organs = Lists.newArrayList();

    /**
     * 组织机构ID集合 @Transient
     */
    private List<Long> organIds  = Lists.newArrayList();

    /**
     * 组织机构名称  @Transient
     */
    private String organNames;

    /**
     * 用户岗位信息
     */
    private List<Post> posts = Lists.newArrayList();
    
    /**
     * 可用余额
     */
    private Float amount;
    
    /**
     * 冻结金额 
     */
    private Float frozenAmount;
    

    public User() {

    }

    @Column(name = "LOGIN_NAME",length = 36, nullable = false)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(name = "NAME",length = 36)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 多对多定义
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    // 中间表定义,表名采用默认命名规则
    @JoinTable(name = "T_SYS_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    // Fecth策略定义
//   @Fetch(FetchMode.SUBSELECT)
    // 集合按id排序.
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Column(name = "PASSWORD",length = 64, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Column(name = "SEX")
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 性别描述.
     */
    @Transient
    public String getSexView() {
    	SexType ss = SexType.getSexType(sex);
    	String str = "";
    	if(ss != null){
    		str = ss.getDescription();
    	}
        return str;
    }
    @Column(name = "EMAIL",length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "ADDRESS",length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Column(name = "TEL",length = 36)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    @Column(name = "MOBILEPHONE",length = 36)
    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    /**
     * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
     * <br>如果是超级管理员 直接返回 "超级管理员" AppConstants.ROLE_SUPERADMIN
     */
    @Transient
    // 非持久化属性.
    public String getRoleNames() {
    	Long superId = 1L;
	    if(superId.equals(this.getId())){
	        return SecurityConstants.ROLE_SUPERADMIN;
	    }
        return ConvertUtils.convertElementPropertyToString(roles, "name",
                ", ");
    }

    @SuppressWarnings("unchecked")
    @Transient
    public List<Long> getRoleIds() {
        if (!Collections3.isEmpty(roles)) {
            roleIds = ConvertUtils.convertElementPropertyToList(roles, "id");
        }
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "T_SYS_USER_RESOURCE", joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "RESOURCE_ID")})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Transient
    public List<Long> getResourceIds() {
        if (!Collections3.isEmpty(resources)) {
            resourceIds = ConvertUtils.convertElementPropertyToList(resources, "id");
        }
        return resourceIds;
    }

    public void setResourceIds(List<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_ORGANID")
    public Organ getDefaultOrgan() {
        return defaultOrgan;
    }

    public void setDefaultOrgan(Organ defaultOrgan) {
        this.defaultOrgan = defaultOrgan;
    }

    @Transient
    public Long getDefaultOrganId() {
        if(defaultOrgan != null){
            defaultOrganId = defaultOrgan.getId();
        }
        return defaultOrganId;
    }

    public void setDefaultOrganId(Long defaultOrganId) {
        this.defaultOrganId = defaultOrganId;
    }

    @Transient
    public String getDefaultOrganName(){
        String doName = null;
        if(defaultOrgan != null){
            doName = defaultOrgan.getName();
        }
        return doName;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "T_SYS_USER_ORGAN", joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ORGAN_ID")})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<Organ> getOrgans() {
        return organs;
    }

    public void setOrgans(List<Organ> organs) {
        this.organs = organs;
    }


    @Transient
    public List<Long> getOrganIds() {
        if(!Collections3.isEmpty(organs)){
            organIds =  ConvertUtils.convertElementPropertyToList(organs, "id");
        }
        return organIds;
    }

    public void setOrganIds(List<Long> organIds) {
        this.organIds = organIds;
    }

    @Transient
    public String getOrganNames() {
        return ConvertUtils.convertElementPropertyToString(organs, "name", ", ");
    }
    public void setOrganNames(String organNames) {
		this.organNames = organNames;
	}

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "T_SYS_USER_POST", joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "POST_ID")})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


    /**
     * 用户岗位名称 VIEW 多个之间以","分割
     * @return
     */
    @Transient
    public String getPostNames() {
        return ConvertUtils.convertElementPropertyToString(posts,"name",",");
    }


    @Transient
    public List<Long> getPostIds() {
        if(Collections3.isNotEmpty(posts)){
            return ConvertUtils.convertElementPropertyToList(posts, "id");
        }
        return Lists.newArrayList();
    }
    @Column(name = "user_type")
	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	@Column(name="amount",columnDefinition="FLOAT default 0")
    @NotNull
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Float getFrozenAmount() {
		return frozenAmount;
	}
	@Column(name="frozen_amount",columnDefinition="FLOAT default 0")
    @NotNull
	public void setFrozenAmount(Float frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

}
