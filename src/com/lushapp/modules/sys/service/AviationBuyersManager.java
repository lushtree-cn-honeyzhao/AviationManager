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
import com.lushapp.modules.sys.entity.AviationBuyers;
import com.lushapp.utils.CacheConstants;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 采购商管理  AviationBuyersManager 层实现类.
 * @author honey.zhao@aliyun.com  
 * @date 2015-10-20 上午11:22:13
 *
 */
@Service
public class AviationBuyersManager extends EntityManager<AviationBuyers, Long> {

	private HibernateDao<AviationBuyers, Long> aviationBuyersDao;


	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		aviationBuyersDao = new HibernateDao<AviationBuyers, Long>(sessionFactory, AviationBuyers.class);
	}

	@Override
	protected HibernateDao<AviationBuyers, Long> getEntityDao() {
		return aviationBuyersDao;
	}

	@CacheEvict(value = { CacheConstants.AVIATION_BUYERS_ALL_CACHE},allEntries = true)
	public void saveOrUpdate(AviationBuyers entity) throws DaoException,
			SystemException, ServiceException {
		logger.debug("清空缓存:{}", CacheConstants.AVIATION_BUYERS_ALL_CACHE);
		Assert.notNull(entity, "参数[entity]为空!");
		aviationBuyersDao.saveOrUpdate(entity);
	}

	@CacheEvict(value = { CacheConstants.AVIATION_BUYERS_ALL_CACHE},allEntries = true)
	public void deleteByIds(List<Long> ids) throws DaoException, SystemException,
			ServiceException {
		logger.debug("清空缓存:{}", CacheConstants.AVIATION_BUYERS_ALL_CACHE);
		if(!Collections3.isEmpty(ids)){
			for (Long id:ids) {
				AviationBuyers AviationBuyers = this.getById(id);

				aviationBuyersDao.delete(AviationBuyers);
			}
		}else{
			logger.warn("参数[ids]为空.");
		}
	}


	@CacheEvict(value = { CacheConstants.AVIATION_BUYERS_ALL_CACHE},allEntries = true)
	@Override
	public void saveEntity(AviationBuyers entity) throws DaoException, SystemException, ServiceException {
		logger.debug("清空缓存:{}", CacheConstants.AVIATION_BUYERS_ALL_CACHE);
		super.saveEntity(entity);
	}



}
