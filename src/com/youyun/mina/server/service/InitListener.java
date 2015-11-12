/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.mina.server.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * 在web.xml中注册该监听器即可启动mina服务 （推荐使用Spring配置启动mina服务）
 * @author honey.zhao@aliyun.com  
 * @date   2014-3-20 下午9:11:30
 */
public class InitListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent arg0) {
    }

    public void contextInitialized(ServletContextEvent sce) {
        new ClassPathXmlApplicationContext("applicationContext-minaServer.xml");
        //在tomcat的启动过程中,会看到控制台打印此语句.
        System.out.println("********mina server 启动完毕*********");
    }
}