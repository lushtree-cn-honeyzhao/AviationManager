/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.core.aop;

import com.lushapp.common.orm.hibernate.DefaultEntityManager;
import com.lushapp.common.utils.StringUtils;
import com.lushapp.common.utils.browser.BrowserType;
import com.lushapp.common.utils.browser.BrowserUtils;
import com.lushapp.common.web.springmvc.SpringMVCHolder;
import com.lushapp.core.security.SecurityConstants;
import com.lushapp.core.security.SecurityUtils;
import com.lushapp.core.security.SessionInfo;
import com.lushapp.modules.sys._enum.LogType;
import com.lushapp.modules.sys.entity.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 日志拦截
 */
// 使用@Aspect 定义一个切面类
@Aspect
@Component(value = SecurityConstants.SERVICE_SECURITY_LOGINASPECT)
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private DefaultEntityManager defaultEntityManager;

    /**
     * @param point 切入点
     */

    @Around("execution(* com.lushapp.modules.*.service..*Manager.*(..))")
    public Object logAll(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        // 执行方法名
        String methodName = point.getSignature().getName();
        String className = point.getTarget().getClass().getSimpleName();
        String userName = null;
        Long start = 0L;
        Long end = 0L;
        String ip = null;
        // 当前用户
        try {
            // 执行方法所消耗的时间
            start = System.currentTimeMillis();
            result = point.proceed();
            end = System.currentTimeMillis();

            // 登录名
            SessionInfo sessionInfo = null;
            try {
                sessionInfo = SecurityUtils.getCurrentSessionInfo();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            if (sessionInfo != null) {
                userName = sessionInfo.getLoginName();
                ip = sessionInfo.getIp();
            } else {
                userName = "系统";
                ip = "127.0.0.1";
                logger.warn("sessionInfo为空.");
            }
        } catch (Throwable e) {
            logger.error(e.getMessage(),e);
            throw e;
        }
        String name = null;
        // 操作范围
        if (className.indexOf("Resource") > -1) {
            name = "资源管理";
        } else if (className.indexOf("Role") > -1) {
            name = "角色管理";
        } else if (className.indexOf("User") > -1) {
            name = "用户管理";
        } else if (className.indexOf("Organ") > -1) {
            name = "机构管理";
        } else {
            name = className;
        }
        // 操作类型
        String opertype = methodName;
        if (StringUtils.isNotBlank(opertype) && (opertype.indexOf("save") > -1 || opertype.indexOf("update") > -1 ||
                opertype.indexOf("delete") > -1 || opertype.indexOf("merge") > -1)) {
            Long time = end - start;
            Log log = new Log();
            log.setType(LogType.operate.getValue());
            log.setLoginName(userName);
            log.setModule(name);
            log.setAction(opertype);
            log.setOperTime(new Date(start));
            log.setActionTime(time.toString());
            log.setIp(ip);
            BrowserType browserType = BrowserUtils.getBrowserType(SpringMVCHolder.getRequest());
            log.setBrowserType(browserType == null ? null : browserType.toString());
            defaultEntityManager.save(log);
        }
        if(logger.isDebugEnabled()){
            logger.debug("用户:{},操作类：{},操作方法：{},耗时：{}ms.",new Object[]{userName,className,methodName,end - start});
        }
        return result;
    }


}
