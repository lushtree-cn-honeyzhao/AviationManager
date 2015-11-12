/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.modules.sys.dao;

import com.youyun.common.orm.mybatis.MyBatisDao;
import com.youyun.modules.sys.entity.Dictionary;

import java.util.List;

/**
 * MyBatis字典DAO接口
 * @author honey.zhao@aliyun.com  
 * @version 2014-7-16
 */
@MyBatisDao
public interface MyBatisDictionaryDao {
	
    Dictionary get(String id);
    
    List<Dictionary> find(Dictionary dictionary);
    
}
