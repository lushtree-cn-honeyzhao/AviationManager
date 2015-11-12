/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.core;

import com.youyun.core.security.SecurityUtils;
import com.youyun.core.security.SessionInfo;

import org.apache.log4j.Logger;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Date;

/**
 * Hibernate拦截器 实现修改人自动注入.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-21 上午12:30:54
 * 
 */
public class HibernateAspectInterceptor extends EmptyInterceptor {
	private static final Logger logger = Logger
			.getLogger(HibernateAspectInterceptor.class);
	private static final long serialVersionUID = 1L;

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		logger.debug("onSave");
		SessionInfo sessionInfo = null;
		try {
            sessionInfo = SecurityUtils.getCurrentSessionInfo();
			if (sessionInfo == null) {
				logger.warn("session中未获取到用户.");
				return true;
			}
		} catch (Exception e) {
			return true;
		}
		try {
			// 添加数据
			for (int index = 0; index < propertyNames.length; index++) {
				if ("createUser".equals(propertyNames[index])) {
					/* 使用拦截器将对象的"创建人名称"属性赋上值 */
					if (state[index] == null) {
						state[index] = sessionInfo.getLoginName();
					}
					continue;
				}
				if ("createTime".equals(propertyNames[index])) {
					/* 使用拦截器将对象的"创建时间"属性赋上值 */
					if (state[index] == null) {
						state[index] = new Date();
					}
					continue;
				}
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		logger.debug("onFlushDirty");
        SessionInfo sessionInfo = null;
        try {
            sessionInfo = SecurityUtils.getCurrentSessionInfo();
			if (sessionInfo == null) {
				logger.warn("session中未获取到用户.");
				return true;
			}
		} catch (Exception e) {
			return true;
		}
		try {
			// 修改或添加数据
			for (int index = 0; index < propertyNames.length; index++) {
				if ("updateUser".equals(propertyNames[index])) {
					/* 使用拦截器将对象的"修改人名称"属性赋上值 */
					currentState[index] = sessionInfo.getLoginName();
					continue;
				}
				if ("updateTime".equals(propertyNames[index])) {
					/* 使用拦截器将对象的"修改时间"属性赋上值 */
					currentState[index] = new Date();
					continue;
				}
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
