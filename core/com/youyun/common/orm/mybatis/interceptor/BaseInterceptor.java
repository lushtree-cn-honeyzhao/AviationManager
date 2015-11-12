/**
 * Copyright &copy; 2014-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.common.orm.mybatis.interceptor;

import com.youyun.common.orm.Page;
import com.youyun.common.orm.mybatis.dialect.Dialect;
import com.youyun.common.orm.mybatis.dialect.db.*;
import com.youyun.common.utils.SysConstants;
import com.youyun.common.utils.reflection.ReflectionUtils;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;

import java.io.Serializable;
import java.util.Properties;

/**
 * Mybatis分页拦截器基类
 * @author honey.zhao@aliyun.com  
 * @version 2014-7-16
 */
public abstract class BaseInterceptor implements Interceptor, Serializable {
	
	private static final long serialVersionUID = 1L;

    protected static final String PAGE = "page";
    
    protected static final String DELEGATE = "delegate";

    protected static final String MAPPED_STATEMENT = "mappedStatement";

    protected Log log = LogFactory.getLog(this.getClass());

    protected Dialect DIALECT;

//    /**
//     * 拦截的ID，在mapper中的id，可以匹配正则
//     */
//    protected String _SQL_PATTERN = "";

    /**
     * 对参数进行转换和检查
     * @param parameterObject 参数对象
     * @param page            分页对象
     * @return 分页对象
     * @throws NoSuchFieldException 无法找到参数
     */
    @SuppressWarnings("unchecked")
	protected static Page<Object> convertParameter(Object parameterObject, Page<Object> page) {
    	try{
            if (parameterObject instanceof Page) {
                return (Page<Object>) parameterObject;
            } else {

                return ReflectionUtils.getFieldValue(parameterObject, PAGE);
            }
    	}catch (Exception e) {
			return null;
		}
    }

    /**
     * 设置属性，支持自定义方言类和制定数据库的方式
     * <code>dialectClass</code>,自定义方言类。可以不配置这项
     * <ode>dbms</ode> 数据库类型，插件支持的数据库
     * <code>sqlPattern</code> 需要拦截的SQL ID
     * @param p 属性
     */
    protected void initProperties(Properties p) {
    	Dialect dialect = null;
        String dbType = SysConstants.getJdbcType();
        if ("db2".equalsIgnoreCase(dbType)){
        	dialect = new DB2Dialect();
        }else if("derby".equalsIgnoreCase(dbType)){
        	dialect = new DerbyDialect();
        }else if("h2".equalsIgnoreCase(dbType)){
        	dialect = new H2Dialect();
        }else if("hsql".equalsIgnoreCase(dbType)){
        	dialect = new HSQLDialect();
        }else if("mysql".equalsIgnoreCase(dbType)){
        	dialect = new MySQLDialect();
        }else if("oracle".equalsIgnoreCase(dbType)){
        	dialect = new OracleDialect();
        }else if("postgre".equalsIgnoreCase(dbType)){
        	dialect = new PostgreSQLDialect();
        }else if("mssql".equalsIgnoreCase(dbType) || "sqlserver".equals(dbType)){
        	dialect = new SQLServer2005Dialect();
        }else if("sybase".equalsIgnoreCase(dbType)){
        	dialect = new SybaseDialect();
        }
        if (dialect == null) {
            throw new RuntimeException("mybatis dialect error.");
        }
        DIALECT = dialect;
//        _SQL_PATTERN = p.getProperty("sqlPattern");
//        _SQL_PATTERN = Global.getConfig("mybatis.pagePattern");
//        if (StringUtils.isEmpty(_SQL_PATTERN)) {
//            throw new RuntimeException("sqlPattern property is not found!");
//        }
    }
}
