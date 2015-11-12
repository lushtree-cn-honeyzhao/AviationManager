/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.modules.sys.service;

import com.youyun.common.exception.DaoException;
import com.youyun.common.exception.ServiceException;
import com.youyun.common.exception.SystemException;
import com.youyun.common.orm.hibernate.EntityManager;
import com.youyun.common.orm.hibernate.HibernateDao;
import com.youyun.common.utils.collections.Collections3;
import com.youyun.modules.sys.entity.Role;
import com.youyun.utils.CacheConstants;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 角色Role管理 Service层实现类.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-11 下午4:15:26
 */
@Service
public class RoleManager extends EntityManager<Role, Long> {

	private HibernateDao<Role, Long> roleDao;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		roleDao = new HibernateDao<Role, Long>(sessionFactory,
				Role.class);
	}
	
	@Override
	protected HibernateDao<Role, Long> getEntityDao() {
		return roleDao;
	}
	
	/**
	 * 删除角色.
	 * <br>删除角色的时候 会给角色重新授权菜单 更新导航菜单缓存.
	 */
    @CacheEvict(value = {  CacheConstants.ROLE_ALL_CACHE,
            CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE,
            CacheConstants.RESOURCE_USER_MENU_TREE_CACHE,
            CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE},allEntries = true)
	@Override
	public void deleteByIds(List<Long> ids) throws DaoException,
			SystemException, ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.ROLE_ALL_CACHE
                +","+CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE
                +","+CacheConstants.RESOURCE_USER_MENU_TREE_CACHE
                +","+CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE);
        super.deleteByIds(ids);
	}
	/**
	 * 新增或修改角色.
	 * <br>修改角色的时候 会给角色重新授权菜单 更新导航菜单缓存.
	 */
    @CacheEvict(value = {  CacheConstants.ROLE_ALL_CACHE,
            CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE,
            CacheConstants.RESOURCE_USER_MENU_TREE_CACHE,
            CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE},allEntries = true)
    public void saveOrUpdate(Role entity) throws DaoException,SystemException,ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.ROLE_ALL_CACHE
                +","+CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE
                +","+CacheConstants.RESOURCE_USER_MENU_TREE_CACHE
                +","+CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE);
		Assert.notNull(entity, "参数[entity]为空!");
		roleDao.saveOrUpdate(entity);
		logger.warn("保存色Role:{}",entity.getId());
	}
	
	/**
	 * 新增或修改角色.
	 * <br>修改角色的时候 会给角色重新授权菜单 更新导航菜单缓存.
	 */
    @CacheEvict(value = {  CacheConstants.ROLE_ALL_CACHE,
            CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE,
            CacheConstants.RESOURCE_USER_MENU_TREE_CACHE,
            CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE},allEntries = true)
    public void merge(Role entity) throws DaoException,SystemException,ServiceException {
		Assert.notNull(entity, "参数[entity]为空!");
		roleDao.merge(entity);
		logger.warn("保存色Role:{}",entity.getId());
	}

    /**
     * 新增或修改角色.
     * @param entity
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    @CacheEvict(value = {  CacheConstants.ROLE_ALL_CACHE,
            CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE,
            CacheConstants.RESOURCE_USER_MENU_TREE_CACHE,
            CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE},allEntries = true)
    @Override
    public void saveEntity(Role entity) throws DaoException, SystemException, ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.ROLE_ALL_CACHE
                +","+CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE
                +","+CacheConstants.RESOURCE_USER_MENU_TREE_CACHE
                +","+CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE);
        super.saveEntity(entity);
    }

    /**
     * 根据角色编码查找
     * @param code 角色编
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    public Role getByCode(String code) throws DaoException,SystemException,ServiceException {
        return this.findUniqueBy("code",code);
    }

    /**
     * 查找所有
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    @Cacheable(value = { CacheConstants.ROLE_ALL_CACHE})
    public List<Role> getAll() throws DaoException,SystemException,ServiceException {
        List<Role> list = super.getAll();
		logger.debug("缓存:{}",CacheConstants.ROLE_ALL_CACHE);
		return list;
	}

}
