/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.common.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Properties;

/**
 * 系统初始化监听.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-11 下午4:56:54
 */
public class DefaultSystemInitListener implements ServletContextListener,
		HttpSessionListener, HttpSessionAttributeListener {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultSystemInitListener.class);

	public DefaultSystemInitListener() {
	}

	public void contextInitialized(ServletContextEvent sce) {
		Properties props=System.getProperties();   
		logger.info(props.toString());  
        logger.info("系统服务启动.");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("系统服务关闭.");
	}

	/**
	 * session创建
	 */
	public void sessionCreated(HttpSessionEvent se) {
		logger.debug("sessionCreated");
	}

	/**
	 * session销毁
	 */
	public void sessionDestroyed(HttpSessionEvent evt) {
		logger.debug("sessionDestroyed");
	}

	public void attributeAdded(HttpSessionBindingEvent sbe) {
		logger.debug("attributeAdded");
	}

	public void attributeRemoved(HttpSessionBindingEvent sbe) {
		logger.debug("attributeRemoved");
	}

	public void attributeReplaced(HttpSessionBindingEvent sbe) {
		logger.debug("attributeReplaced");
	}

}
