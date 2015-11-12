/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.listener;

import com.youyun.common.web.listener.DefaultSystemInitListener;
import com.youyun.core.security.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;

/**
 * 系统初始化监听 继承默认系统启动监听器.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-11 下午4:56:54
 */
public class SystemInitListener extends DefaultSystemInitListener{

	private static final Logger logger = LoggerFactory
			.getLogger(SystemInitListener.class);

	public SystemInitListener() {
	}

	/**
	 * session销毁
	 */
	public void sessionDestroyed(HttpSessionEvent evt) {
		logger.debug("sessionDestroyed");
		String sessionId = evt.getSession().getId();
		SecurityUtils.removeUserFromSession(sessionId,true);
	}

}
