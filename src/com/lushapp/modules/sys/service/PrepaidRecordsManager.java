/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.service;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.lushapp.common.exception.DaoException;
import com.lushapp.common.exception.ServiceException;
import com.lushapp.common.exception.SystemException;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.orm.hibernate.HibernateDao;
import com.lushapp.common.utils.StringUtils;
import com.lushapp.common.utils.collections.Collections3;
import com.lushapp.modules.sys.entity.PrepaidRecords;
import com.lushapp.utils.CacheConstants;

/**
 * 充值记录实现类.
 * 
 * @author : honey.zhao@aliyun.com  
 * @date 2014-10-24 下午3:01:27
 */
@Service
public class PrepaidRecordsManager extends
		EntityManager<PrepaidRecords, Long> {

	private HibernateDao<PrepaidRecords, Long> prepaidRecordsDao;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		prepaidRecordsDao = new HibernateDao<PrepaidRecords, Long>(
				sessionFactory, PrepaidRecords.class);
	}

	@Override
	protected HibernateDao<PrepaidRecords, Long> getEntityDao() {
		return prepaidRecordsDao;
	}

    @CacheEvict(value = { CacheConstants.PREPAID_RECORDS_ALL_CACHE},allEntries = true)
	public void saveOrUpdate(PrepaidRecords entity) throws DaoException,
			SystemException, ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.PREPAID_RECORDS_ALL_CACHE);
        Assert.notNull(entity, "参数[entity]为空!");
        prepaidRecordsDao.saveOrUpdate(entity);
	}

    @CacheEvict(value = { CacheConstants.PREPAID_RECORDS_ALL_CACHE},allEntries = true)
	public void deleteByIds(List<Long> ids) throws DaoException, SystemException,
			ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.PREPAID_RECORDS_ALL_CACHE);
        if(!Collections3.isEmpty(ids)){
            for (Long id:ids) {
                PrepaidRecords PrepaidRecords = this.getById(id);
               
                prepaidRecordsDao.delete(PrepaidRecords);
            }
        }else{
            logger.warn("参数[ids]为空.");
        }
	}


    @CacheEvict(value = { CacheConstants.PREPAID_RECORDS_ALL_CACHE},allEntries = true)
    @Override
    public void saveEntity(PrepaidRecords entity) throws DaoException, SystemException, ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.PREPAID_RECORDS_ALL_CACHE);
        super.saveEntity(entity);
    }


	/**
	 * 根据 附属油卡tradeSn 得到油卡分配记录对象.
	 * 
	 * @param tradeSn 
	 * @return
	 * @throws com.lushapp.common.exception.DaoException
	 * @throws com.lushapp.common.exception.SystemException
	 * @throws com.lushapp.common.exception.ServiceException
	 */
	@SuppressWarnings("unchecked")
	public PrepaidRecords getByCode(String tradeSn) throws DaoException,
			SystemException, ServiceException {
		if (StringUtils.isBlank(tradeSn)) {
			return null;
		}
		tradeSn = StringUtils.strip(tradeSn);// 去除两边空格
		List<PrepaidRecords> list = prepaidRecordsDao
				.createQuery("from PrepaidRecords d where d.tradeSn = ?",
						new Object[] { tradeSn }).list();
		return list.isEmpty() ? null : list.get(0);
	}


    @Cacheable(value = { CacheConstants.PREPAID_RECORDS_ALL_CACHE})
	public List<PrepaidRecords> getAll() throws DaoException, SystemException,
			ServiceException {
		logger.debug("缓存:{}", CacheConstants.PREPAID_RECORDS_ALL_CACHE);
		return prepaidRecordsDao.getAll();
	}

	/**
	 * 得到排序字段的最大值.
	 *
	 * @return 返回排序字段的最大值
	 * @throws com.lushapp.common.exception.DaoException
	 * @throws com.lushapp.common.exception.SystemException
	 * @throws com.lushapp.common.exception.ServiceException
	 */
	public Integer getMaxSort() throws DaoException, SystemException,
			ServiceException {
		Iterator<?> iterator = prepaidRecordsDao.createQuery(
				"select max(d.id)from PrepaidRecords d ").iterate();
		Integer max = null;
		while (iterator.hasNext()) {
			// Object[] row = (Object[]) iterator.next();
			max = (Integer) iterator.next();
		}
		if (max == null) {
			max = 0;
		}
		return max;
	}

}
