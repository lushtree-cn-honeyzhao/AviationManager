/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.service;

import com.lushapp.common.exception.DaoException;
import com.lushapp.common.exception.ServiceException;
import com.lushapp.common.exception.SystemException;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.orm.hibernate.HibernateDao;
import com.lushapp.common.utils.collections.Collections3;
import com.lushapp.modules.sys.entity.AviationSuppliers;
import com.lushapp.utils.CacheConstants;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 供应商管理  AviationSuppliersManager 层实现类.
 * @author honey.zhao@aliyun.com  
 * @date 2015-10-20 上午11:22:13
 *
 */
@Service
public class AviationSuppliersManager extends EntityManager<AviationSuppliers, Long> {

	private HibernateDao<AviationSuppliers, Long> aviationSuppliersDao;


	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		aviationSuppliersDao = new HibernateDao<AviationSuppliers, Long>(sessionFactory, AviationSuppliers.class);
	}

	@Override
	protected HibernateDao<AviationSuppliers, Long> getEntityDao() {
		return aviationSuppliersDao;
	}

	@CacheEvict(value = { CacheConstants.AVIATION_SUPPLIERS_ALL_CACHEE},allEntries = true)
	public void saveOrUpdate(AviationSuppliers entity) throws DaoException,
			SystemException, ServiceException {
		logger.debug("清空缓存:{}", CacheConstants.AVIATION_SUPPLIERS_ALL_CACHEE);
		Assert.notNull(entity, "参数[entity]为空!");
		aviationSuppliersDao.saveOrUpdate(entity);
	}

	@CacheEvict(value = { CacheConstants.AVIATION_SUPPLIERS_ALL_CACHEE},allEntries = true)
	public void deleteByIds(List<Long> ids) throws DaoException, SystemException,
			ServiceException {
		logger.debug("清空缓存:{}", CacheConstants.AVIATION_SUPPLIERS_ALL_CACHEE);
		if(!Collections3.isEmpty(ids)){
			for (Long id:ids) {
				AviationSuppliers AviationSuppliers = this.getById(id);

				aviationSuppliersDao.delete(AviationSuppliers);
			}
		}else{
			logger.warn("参数[ids]为空.");
		}
	}


	@CacheEvict(value = { CacheConstants.AVIATION_SUPPLIERS_ALL_CACHEE},allEntries = true)
	@Override
	public void saveEntity(AviationSuppliers entity) throws DaoException, SystemException, ServiceException {
		logger.debug("清空缓存:{}", CacheConstants.AVIATION_SUPPLIERS_ALL_CACHEE);
		super.saveEntity(entity);
	}



}
