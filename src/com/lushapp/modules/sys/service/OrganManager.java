/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lushapp.common.exception.DaoException;
import com.lushapp.common.exception.ServiceException;
import com.lushapp.common.exception.SystemException;
import com.lushapp.common.model.TreeNode;
import com.lushapp.common.orm.entity.StatusState;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.orm.hibernate.HibernateDao;
import com.lushapp.common.utils.StringUtils;
import com.lushapp.common.utils.collections.Collections3;
import com.lushapp.modules.sys._enum.OrganType;
import com.lushapp.modules.sys.entity.Organ;

import org.apache.commons.collections.ListUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 机构Organ管理 Service层实现类.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-09 下午21:26:46
 */
@Service
public class OrganManager extends EntityManager<Organ, Long> {


    private HibernateDao<Organ, Long> organDao;// 默认的泛型DAO成员变量.

    /**
     * 通过注入的sessionFactory初始化默认的泛型DAO成员变量.
     */
    @Autowired
    public void setSessionFactory(final SessionFactory sessionFactory) {
        organDao = new HibernateDao<Organ, Long>(sessionFactory, Organ.class);
    }

    @Override
    protected HibernateDao<Organ, Long> getEntityDao() {
        return organDao;
    }

    /**
     * 保存或修改.
     */
    public void saveOrUpdate(Organ entity) throws DaoException, SystemException,
            ServiceException {
        logger.debug("清空缓存:{}");
        Assert.notNull(entity, "参数[entity]为空!");
        organDao.saveOrUpdate(entity);
    }

    /**
     * 保存或修改.
     */
    public void merge(Organ entity) throws DaoException, SystemException,
            ServiceException {
        Assert.notNull(entity, "参数[entity]为空!");
        organDao.merge(entity);
    }

    @Override
    public void saveEntity(Organ entity) throws DaoException, SystemException, ServiceException {
        super.saveEntity(entity);
    }

    /**
     * 自定义保存机构.
     * <br/>说明：如果保存的机构类型为“功能” 则将所有子机构都设置为“功能”类型
     * @param entity 机构对象
     * @throws com.lushapp.common.exception.DaoException
     * @throws com.lushapp.common.exception.SystemException
     * @throws com.lushapp.common.exception.ServiceException
     */
    public void saveOrgan(Organ entity) throws DaoException, SystemException, ServiceException {
        Assert.notNull(entity,"参数[entity]为空!");
        this.saveEntity(entity);
        if(entity.getType() !=null && OrganType.department.getValue().equals(entity.getType())){
            List<Organ> subOrgans = entity.getSubOrgans();
            while (!Collections3.isEmpty(subOrgans)){
                Iterator<Organ> iterator = subOrgans.iterator();
                while(iterator.hasNext()){
                    Organ subOrgan = iterator.next();
                    subOrgan.setType(OrganType.department.getValue());
                    iterator.remove();
                    subOrgans = ListUtils.union(subOrgans,subOrgan.getSubOrgans());
                    super.update(subOrgan);
                }
            }
        }
    }


    /**
     * 自定义删除方法.
     */
    public void deleteByIds(List<Long> ids) throws DaoException, SystemException,
            ServiceException {
        super.deleteByIds(ids);
    }

    /**
     * Organ转TreeNode
     * @param organ 机构
     * @param organType 机构类型
     * @param isCascade       是否级联
     * @return
     * @throws com.lushapp.common.exception.DaoException
     * @throws com.lushapp.common.exception.SystemException
     * @throws com.lushapp.common.exception.ServiceException
     */
    private TreeNode organToTreeNode(Organ organ,Integer organType,boolean isCascade) throws DaoException, SystemException,
            ServiceException {
        if(organType!=null){
            if(!organType.equals(organ.getType())){
                return null;
            }
        }
        TreeNode treeNode = new TreeNode(organ.getId().toString(),
                organ.getName());
        // 自定义属性 url
        Map<String, Object> attributes = Maps.newHashMap();
        attributes.put("code", organ.getCode());
        attributes.put("type", organ.getType());
        treeNode.setAttributes(attributes);
        if(isCascade){
            List<TreeNode> childrenTreeNodes = Lists.newArrayList();
            for(Organ subOrgan:organ.getSubOrgans()){
                TreeNode node = organToTreeNode(subOrgan,organType,isCascade);
                if(node !=null){
                    childrenTreeNodes.add(node);
                }
            }
            treeNode.setChildren(childrenTreeNodes);
        }

        return treeNode;
    }


    /**
     *
     * @param entity
     * @param parentId
     * @param isCascade 是否级联
     * @return
     * @throws com.lushapp.common.exception.DaoException
     * @throws com.lushapp.common.exception.SystemException
     * @throws com.lushapp.common.exception.ServiceException
     */
    public TreeNode getTreeNode(Organ entity, Long parentId, boolean isCascade)
            throws DaoException, SystemException, ServiceException {
        TreeNode node = this.organToTreeNode(entity,null,true);
        List<Organ> subOrgans = this.getByParentId(entity.getId(),StatusState.normal.getValue());
        if (subOrgans.size() > 0) {
            if (isCascade) {// 递归查询子节点
                List<TreeNode> children = Lists.newArrayList();
                for (Organ d : subOrgans) {
                    boolean isInclude = true;// 是否包含到节点树
                    TreeNode treeNode = null;
                    treeNode = getTreeNode(d, parentId, true);
                    // 排除自身
                    if (parentId != null) {
                        if (!d.getId().equals(parentId)) {
                            treeNode = getTreeNode(d, parentId, true);
                        } else {
                            isInclude = false;
                        }
                    } else {
                        treeNode = getTreeNode(d, parentId, true);
                    }
                    if (isInclude) {
                        children.add(treeNode);
                        node.setState(TreeNode.STATE_CLOASED);
                    } else {
                        node.setState(TreeNode.STATE_OPEN);
                    }
                }

                node.setChildren(children);
            }
        }
        return node;
    }

    /**
     * 获取所有导航机构（无权限限制）.
     * @param excludeOrganId 需要排除的机构ID 子级也会被排除
     * @param isCascade 是否级联
     * @return
     * @throws com.lushapp.common.exception.DaoException
     * @throws com.lushapp.common.exception.SystemException
     * @throws com.lushapp.common.exception.ServiceException
     */
    public List<TreeNode> getOrganTree(Long excludeOrganId,boolean isCascade) throws DaoException, SystemException,
            ServiceException {
        List<TreeNode> treeNodes = Lists.newArrayList();
        // 顶级机构
        List<Organ> organs = getByParentId(null,
                StatusState.normal.getValue());
        for (Organ rs:organs) {
            TreeNode rootNode = getTreeNode(rs, excludeOrganId, isCascade);
            treeNodes.add(rootNode);
        }
        return treeNodes;

    }



    /**
     *
     * 根据name得到Organ.
     *
     * @param name
     *            机构名称
     * @return
     * @throws com.lushapp.common.exception.DaoException
     * @throws com.lushapp.common.exception.SystemException
     * @throws com.lushapp.common.exception.ServiceException
     */
    public Organ getByName(String name) throws DaoException, SystemException,
            ServiceException {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        name = StringUtils.strip(name);// 去除两边空格
        return organDao.findUniqueBy("name", name);
    }
    /**
     *
     * 根据系统编码得到Organ.
     *
     * @param sysCode
     *            机构系统编码
     * @return
     * @throws com.lushapp.common.exception.DaoException
     * @throws com.lushapp.common.exception.SystemException
     * @throws com.lushapp.common.exception.ServiceException
     */
    public Organ getBySysCode(String sysCode) throws DaoException, SystemException,
            ServiceException {
        if (StringUtils.isBlank(sysCode)) {
            return null;
        }
        return organDao.findUniqueBy("sysCode", sysCode);
    }

    /**
     *
     * 根据编码得到Organ.
     *
     * @param code
     *            机构编码
     * @return
     * @throws com.lushapp.common.exception.DaoException
     * @throws com.lushapp.common.exception.SystemException
     * @throws com.lushapp.common.exception.ServiceException
     */
    public Organ getByCode(String code) throws DaoException, SystemException,
            ServiceException {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return organDao.findUniqueBy("code", code);
    }

    /**
     *
     * 根据父ID得到 Organ. <br>
     * 默认按 orderNo asc,id asc排序.
     *
     * @param parentId
     *            父节点ID(当该参数为null的时候查询顶级机构列表)
     * @param status
     *            数据状态 @see com.lushapp.common.orm.entity.StatusState
     *            <br>status传null则使用默认值 默认值:StatusState.normal.getValue()
     * @return
     * @throws com.lushapp.common.exception.DaoException
     * @throws com.lushapp.common.exception.SystemException
     * @throws com.lushapp.common.exception.ServiceException
     */
    @SuppressWarnings("unchecked")
    public List<Organ> getByParentId(Long parentId, Integer status)
            throws DaoException, SystemException, ServiceException {
        //默认值 正常
        if(status == null){
            status = StatusState.normal.getValue();
        }
        StringBuilder sb = new StringBuilder();
        Object[] objs;
        sb.append("from Organ o where o.status = ?  ");
        sb.append(" and o.parentOrgan.id ");
        if (parentId == null) {
            sb.append(" is null ");
            objs = new Object[] { status };
        } else {
            sb.append(" = ? ");
            objs = new Object[] { status, parentId };
        }
        sb.append(" order by o.orderNo asc,o.id asc");

        List<Organ> list = organDao.createQuery(sb.toString(), objs).list();
        return list;
    }


    /**
     * 得到排序字段的最大值.
     *
     * @return 返回排序字段的最大值
     */
    public Integer getMaxSort() throws DaoException, SystemException,
            ServiceException {
        Iterator<?> iterator = organDao.createQuery(
                "select max(o.orderNo)from Organ o ").iterate();
        Integer max = 0;
        while (iterator.hasNext()) {
            max = (Integer) iterator.next();
            if (max == null) {
                max = 0;
            }
        }
        return max;
    }
}
