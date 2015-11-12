/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.core.aop;

import com.youyun.common.orm.hibernate.DefaultEntityManager;
import com.youyun.common.utils.browser.BrowserType;
import com.youyun.common.utils.browser.BrowserUtils;
import com.youyun.common.web.springmvc.SpringMVCHolder;
import com.youyun.core.security.SecurityType;
import com.youyun.core.security.SecurityUtils;
import com.youyun.core.security.SessionInfo;
import com.youyun.modules.sys._enum.LogType;
import com.youyun.modules.sys.entity.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 使用AspectJ实现登录登出日志AOP
 * @author honey.zhao@aliyun.com
 */
@Aspect
public class SecurityLogAspect {

    private static Logger logger = LoggerFactory.getLogger(SecurityLogAspect.class);

    @Autowired
    private DefaultEntityManager defaultEntityManager;

    /**
     * 登录增强
     * @param joinPoint 切入点
     */
    @After("execution(* com.youyun.modules.sys.web.LoginController.login(..))")
    public void afterLoginLog(JoinPoint joinPoint) throws Throwable{
        SessionInfo sessionInfo = SecurityUtils.getCurrentSessionInfo();
        if(sessionInfo != null){
            saveLog(sessionInfo,joinPoint,SecurityType.login); //保存日志
        }
    }

    /**
     * 登出增强
     * @param joinPoint 切入点
     */
    @Before("execution(* com.youyun.modules.sys.web.LoginController.logout(..))")
    public void beforeLogoutLog(JoinPoint joinPoint) throws Throwable{
        SessionInfo sessionInfo = SecurityUtils.getCurrentSessionInfo();
        if(sessionInfo != null){
            saveLog(sessionInfo,joinPoint,SecurityType.logout); //保存日志
        }
    }

    /**
     * 保存日志
     * @param sessionInfo 登录用户session信息
     * @param joinPoint 切入点
     * @param securityType 是否是登录操作
     *                     @see SecurityType
     */
    public void saveLog(SessionInfo sessionInfo, JoinPoint joinPoint,SecurityType securityType){
        Object result = null;
        // 执行方法名

        String methodName = null;
        String className = null;
        if(joinPoint != null){
            methodName = joinPoint.getSignature().getName();
            className = joinPoint.getTarget().getClass().getSimpleName();
        }else{
            className = "LoginAction";
            methodName = "logout";
        }
        String user = null;
        Long start = 0L;
        Long end = 0L;
        // 执行方法所消耗的时间
        try {
            start = System.currentTimeMillis();
            end = System.currentTimeMillis();
            Long opTime = end - start;
            Log log = new Log();
            log.setType(LogType.security.getValue());
            log.setLoginName(sessionInfo.getLoginName());
            log.setModule(className + "-" + methodName);
            log.setActionTime(opTime.toString());
            log.setIp(sessionInfo.getIp());
            log.setAction(securityType.getDescription());
            BrowserType browserType = BrowserUtils.getBrowserType(SpringMVCHolder.getRequest());
            log.setBrowserType(browserType == null ? null : browserType.toString());
            if(logger.isDebugEnabled()){
                logger.debug("用户:{},操作类：{},操作方法：{},耗时：{}ms.",new Object[]{user,className,methodName,end - start});
            }
            log.setOperTime(new Date());
            defaultEntityManager.save(log);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
