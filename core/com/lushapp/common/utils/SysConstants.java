/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.common.utils;

import com.lushapp.common.utils.io.PropertiesLoader;


/**
 * 项目中用到的静态变量.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-8-20 上午11:40:56
 */
public class SysConstants {
    /**
     * session 验证码key
     */
    public static final String SESSION_VALIDATE_CODE = "validateCode";
    
    private static PropertiesLoader appconfig = null;
    private static PropertiesLoader sensitive = null;
    private static PropertiesLoader sqlfilter = null;
    
    /**
     * 配置文件(appconfig.properties)
     */
    public static PropertiesLoader getAppConfig() {
    	if(appconfig == null){
    		appconfig = new PropertiesLoader("appconfig.properties");
    	}
        return appconfig;
    }

    /**
     * 修改配置文件
     * @param key
     * @param value
     */
    public static void modifyAppConfig(String key,String value) {
        String filePath = "appconfig.properties";
        if(appconfig == null){
            appconfig = new PropertiesLoader(filePath);
        }
        appconfig.modifyProperties(filePath,key,value);
    }
    
    /**
     * 配置文件(sensitive.properties)
     */
    public static PropertiesLoader getSensitive() {
    	if(sensitive == null){
    		sensitive = new PropertiesLoader("sensitive.properties");
    	}
        return sensitive;
    }
    
    /**
     * SQL参数过滤配置文件(sqlfilter.properties)
     */
    public static PropertiesLoader getSqlfilter() {
    	if(sqlfilter == null){
    		sqlfilter = new PropertiesLoader("sqlfilter.properties");
    	}
        return sqlfilter;
    }

    /**
     * jdbc type连接参数(默认:"").
     */
    public static String getJdbcType(){
        return SysConstants.getAppConfig().getProperty("jdbc.type","");
    }
    
    /**
     * jdbc url连接参数(默认:"").
     */
    public static String getJdbcUrl(){
    	return SysConstants.getAppConfig().getProperty("jdbc.url","");
    }

    /**
     * jdbc 驱动类
     * @return
     */
    public static String getJdbcDriverClassName(){
        return SysConstants.getAppConfig().getProperty("jdbc.driverClassName","");
    }

    /**
     * jdbc 用户名
     * @return
     */
    public static String getJdbcUserName(){
        return SysConstants.getAppConfig().getProperty("jdbc.username","");
    }

    /**
     * jdbc 密码
     * @return
     */
    public static String getJdbcPassword(){
        return SysConstants.getAppConfig().getProperty("jdbc.password","");
    }


    /**
     * 获取是否是开发模式(默认:false).
     */
    public static boolean isdevMode(){
    	return getAppConfig().getBoolean("devMode",false);
    }
    
    
    
	/**
	 * 获得上传表单域的名称
	 * 
	 * @return
	 */
	public static final String getUploadFieldName() {
		return getAppConfig().getProperty("uploadFieldName", "filedata");
	}

	/**
	 * 获得上传文件的最大大小限制
	 * 
	 * @return
	 */
	public static final long getUploadFileMaxSize() {
		 String uploadFileMaxSize = getAppConfig().getProperty("uploadFileMaxSize", "20971520");
		 return Long.valueOf(uploadFileMaxSize);
	}

	/**
	 * 获得允许上传文件的扩展名
	 * 
	 * @return
	 */
	public static final String getUploadFileExts() {
		return getAppConfig().getProperty("uploadFileExts","txt,rar,zip,doc,docx,xls,xlsx,jpg,jpeg,gif,png,swf,wmv,avi,wma,mp3,mid");
	}

	/**
	 * 获得上传文件要放到那个目录
	 * 
	 * @return
	 */
	public static final String getUploadDirectory() {
		return getAppConfig().getProperty("uploadDirectory", "attached");
	}


}
