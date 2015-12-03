/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.service;

import com.google.common.collect.Lists;
import com.lushapp.common.exception.DaoException;
import com.lushapp.common.exception.ServiceException;
import com.lushapp.common.exception.SystemException;
import com.lushapp.common.orm.Page;
import com.lushapp.common.orm.PropertyFilter;
import com.lushapp.common.orm.entity.StatusState;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.orm.hibernate.HibernateDao;
import com.lushapp.common.utils.StringUtils;
import com.lushapp.common.utils.collections.Collections3;
import com.lushapp.core.security.SecurityUtils;
import com.lushapp.core.security.SessionInfo;
import com.lushapp.modules.sys.entity.AviationOrder;
import com.lushapp.modules.sys.entity.Organ;
import com.lushapp.modules.sys.entity.Role;
import com.lushapp.modules.sys.entity.User;
import com.lushapp.utils.CacheConstants;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户管理User Service层实现类.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-20 上午11:22:13 
 *
 */
@Service
public class AviationOrderManager extends EntityManager<AviationOrder, Long> {

	private HibernateDao<AviationOrder, Long> aviationOrderDao;


	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
        aviationOrderDao = new HibernateDao<AviationOrder, Long>(sessionFactory, AviationOrder.class);
	}

	@Override
	protected HibernateDao<AviationOrder, Long> getEntityDao() {
		return aviationOrderDao;
	}


}
