/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.utils;

/**
 * 缓存静态变量.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-19 下午6:45:50 
 *
 */
public class CacheConstants {
	
	//Spring Ehcache Annoction
	/**
     * ===================================================
     * 系统设置
     */
	/**
	 * 用户导航菜单(根据用户权限缓存).
	 */
	public static final String RESOURCE_USER_MENU_TREE_CACHE = "resource_user_menu_Tree_cache";
    /**
     * 用户资源树(根据用户权限缓存).
     */
    public static final String RESOURCE_USER_RESOURCE_TREE_CACHE = "resource_user_resource_Tree_cache";

    /**
     * 某个url对应的是否授权给某个用户.
     */
    public static final String RESOURCE_USER_AUTHORITY_URLS_CACHE = "resource_user_authority_urls_cache";
	
	/**
	 * 角色(无).
	 */
	public static final String ROLE_ALL_CACHE = "role_all_cache";

    /**
     * 数据字典类型下的数据(根据数据字典类型缓存).
     */
    public static final String DICTIONARYS_BY_TYPE_CACHE = "dictionarys_byType_cache";
	/**
	 * 数据字典类型下的数据(根据数据字典类型缓存).
	 */
	public static final String DICTIONARYS_CONBOTREE_BY_TYPE_CACHE = "dictionarys_conbotree_byType_cache";
    /**
     * 数据字典类型下的数据(根据数据字典类型缓存).
     */
    public static final String DICTIONARYS_CONBOBOX_BY_TYPE_CACHE = "dictionarys_conbobox_byType_cache";
	/**
	 * 所有数据字典类型.
	 */
	public static final String DICTIONARY_TYPE_ALL_CACHE = "dictionaryType_all_cache";
    /**
     * 所有数据字典类型组（即第一层）.
     */
    public static final String DICTIONARY_TYPE_GROUPS_CACHE = "dictionaryType_groups_cache";

    /**
     * 通知管理
     */
    public static final String MESSAGE_ALL_CACHE = "message_all_cache";

    /**
     * ===================================================
     * 销售订单管理
     */
    /**
     * 机票订单
     */
    public static final String AVIATION_ORDER_ALL_CACHE = "aviation_order_all_cache";
    
    /**
     * 航班信息
     */
    public static final String AVIATION_FLIGHT_INFO_ALL_CACHE = "aviation_flight_info_all_cache";
    
    /**
     * 乘客信息
     */
    public static final String AVIATION_PASSENGER_INFO_ALL_CACHE = "aviation_passenger_info_all_cache";
	
    /**
     * 供应商信息
     */
    public static final String AVIATION_SUPPLIERS_ALL_CACHEE = "aviation_suppliers_all_cache";
    
    
    /**
     * 采购商信息
     */
    public static final String AVIATION_BUYERS_ALL_CACHE = "aviation_buyers_all_cache";
    
    
}
