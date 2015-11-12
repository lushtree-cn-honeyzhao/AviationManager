/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.modules.sys.service;

import com.youyun.common.orm.hibernate.EntityManager;
import com.youyun.common.orm.hibernate.HibernateDao;
import com.youyun.modules.sys.entity.Bug;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * bug管理Service层.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-27 下午8:00:22 
 *
 */
@Service
public class BugManager extends EntityManager<Bug, Long> {

	private HibernateDao<Bug, Long> bugDao;
	
	
	/**
	 * 通过注入的sessionFactory初始化默认的泛型DAO成员变量.
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		bugDao = new HibernateDao<Bug, Long>(sessionFactory, Bug.class);
	}
	
	@Override
	protected HibernateDao<Bug, Long> getEntityDao() {
		return bugDao;
	}

}
