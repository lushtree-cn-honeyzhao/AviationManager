/**
 *  Copyright (c) 2014 http://www.lushapplicatioin.com
 *
 *                    
 */
package com.lushapp.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.lushapp.common.orm.entity.BaseEntity;
import com.lushapp.common.utils.ConvertUtils;
import com.lushapp.common.utils.collections.Collections3;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * 岗位 entity
 * @author : 温春平 wencp@jx.tobacco.gov.cn
 * @date 2014-10-09 14:04
 */
@Entity
@Table(name = "T_SYS_POST")
// jackson标记不生成json对象的属性
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler",
        "organ","users"})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Post extends BaseEntity {

    /**
     * 岗位名称
     */
    private String name;
    /**
     * 岗位编码
     */
    private String code;
    /**
     * 备注
     */
    private String remark;
    /**
     * 所属部门
     */
    private Organ organ;
    /**
     * 所属部门ID
     * @Transient
     */
    private Long organId;

    /**
     * 岗位用户
     */
    private List<User> users = Lists.newArrayList();

    public Post() {
    }

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

    @Column(name = "REMARK",length = 36)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ORGAN_ID")
    public Organ getOrgan() {
        return organ;
    }

    public void setOrgan(Organ organ) {
        this.organ = organ;
    }


    /**
     * 机构ID VIEW
     * @return
     */
    @Transient
    public Long getOrganId() {
        if(this.getOrgan() != null){
            return this.organ.getId();
        }
        return null;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    /**
     * 机构名称 VIEW
     * @return
     */
    @Transient
    public String getOrganName() {
        if(this.getOrgan() != null){
            return this.organ.getName();
        }
        return null;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "T_SYS_USER_POST", joinColumns = {@JoinColumn(name = "POST_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    @Where(clause = "status = 0")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    /**
     * 岗位用户名称 VIEW 多个之间以"，"分割
     * @return
     */
    @Transient
    public String getUserNames() {
        return ConvertUtils.convertElementPropertyToString(users, "name", ",");
    }


    @Transient
    public List<Long> getUserIds() {
        if(Collections3.isNotEmpty(users)){
            return ConvertUtils.convertElementPropertyToList(users, "id");
        }
        return Lists.newArrayList();
    }
}