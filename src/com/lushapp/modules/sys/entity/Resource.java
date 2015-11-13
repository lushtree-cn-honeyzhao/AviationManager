/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.lushapp.common.orm.entity.BaseEntity;
import com.lushapp.common.utils.ConvertUtils;
import com.lushapp.modules.sys._enum.ResourceType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 受保护的资源菜案Resource.
 * Author honey.zhao@aliyun.com  
 * Date 2014-3-21 上午12:27:49
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_SYS_RESOURCE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//jackson标记不生成json对象的属性 
@JsonIgnoreProperties (value = { "hibernateLazyInitializer" , "handler","fieldHandler" ,  "parentResource",
        "roles","roleNames", "users", "subResources", "navigation" })
public class Resource
        extends BaseEntity
        implements Serializable {

    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源编码
     */
    private String code;
    /**
     * 资源url地址
     */
    private String url;
    /**
     * 排序
     */
    private Integer orderNo;
    /**
     * 图标
     */
    private String iconCls;
    /**
     * 应用程序图标地址
     */
    private String icon;
    /**
     * 父级Resource
     */
    private Resource parentResource;
    /**
     * 父级ResourceId @Transient
     */
    private Long _parentId;
    /**
     * 标记url
     */
    private String markUrl;
    /**
     * 资源类型 资源(0) 功能(1)
     */
    private Integer type = ResourceType.menu.getValue();
    /**
     * 有序的关联对象集合
     */
    private List<Role> roles = Lists.newArrayList();
    /**
     * 有序的关联对象集合
     */
    private List<User> users = Lists.newArrayList();
    /**
     * 子Resource集合
     */
    private List<Resource> subResources = Lists.newArrayList();

    public Resource() {
    }

    @Column(name = "MARK_URL",length = 2000)
    public String getMarkUrl() {
        return markUrl;
    }

    public void setMarkUrl(String markUrl) {
        this.markUrl = markUrl;
    }

    @NotBlank(message = "{resource_name.notblank}")
    @Length(max = 20, message = "{resource_name.length}")
    @Column(name = "NAME",length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CODE",length = 36)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "URL",length = 255)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "ORDER_NO")
    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    @Column(name="ICON_CLS",length = 255)
    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    @Column(name="ICON",length = 255)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name="TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "PARENT_ID")
    public Resource getParentResource() {
        return parentResource;
    }

    public void setParentResource(Resource parentResource) {
        this.parentResource = parentResource;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "T_SYS_ROLE_RESOURCE", joinColumns = {@JoinColumn(name = "RESOURCE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * 角色拥有的资源字符串,多个之间以","分割
     *
     * @return
     */
    @Transient
    public String getRoleNames() {
        return ConvertUtils.convertElementPropertyToString(roles, "name",
                ", ");
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "T_SYS_USER_RESOURCE", joinColumns = {@JoinColumn(name = "RESOURCE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @OneToMany(mappedBy = "parentResource",cascade = {CascadeType.REMOVE})
    @OrderBy("orderNo asc")
    public List<Resource> getSubResources() {
        return subResources;
    }

    public void setSubResources(List<Resource> subResources) {
        this.subResources = subResources;
    }

    @Transient
    public List<Resource> getNavigation() {
        ArrayList<Resource> arrayList = new ArrayList<Resource>();
        Resource resource = this;
        arrayList.add(resource);
        while (null != resource.parentResource) {
            resource = resource.parentResource;
            arrayList.add(0, resource);
        }
        return arrayList;
    }

    @Transient
    public Long get_parentId() {
        if (parentResource != null) {
            _parentId = parentResource.getId();
        }
        return _parentId;
    }

    public void set_parentId(Long _parentId) {
        this._parentId = _parentId;
    }


    /**
     * 资源类型描述
     */
    @Transient
    public String getTypeView() {
        ResourceType r = ResourceType.getResourceType(type);
        String str = "";
        if(r != null){
            str = r.getDescription();
        }
        return str;
    }

}