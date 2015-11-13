/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.core.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 项目中用到的静态变量.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-8-20 上午11:40:56
 */
public class SecurityConstants {
    /**
     * session 登录用户key
     */
    public static final String SESSION_SESSIONINFO = "sessionInfo";

    /**
     * session 未授权的URL
     */
    public static final String SESSION_UNAUTHORITY_URL = "UNAUTHORITY_URL";

    /**
     * session 未授权的页面
     */
    public static final String SESSION_UNAUTHORITY_PAGE = "/common/403.jsp";
    public static final String SESSION_UNAUTHORITY_LOGIN_PAGE = "/jump.jsp";

    /**
     * 超级管理员
     */
    public static final String ROLE_SUPERADMIN = "超级管理员";

    /**
     * 在线用户列表.
     */
    public static final Map<String,SessionInfo> sessionInfoMap = new ConcurrentHashMap<String, SessionInfo>();

    /**
     * 安全日志拦截bean名称
     */
    public static final String SERVICE_SECURITY_LOGINASPECT = "securityLogAspect";

}
