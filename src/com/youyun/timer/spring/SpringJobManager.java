/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.timer.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spring3中使用注解(@Scheduled)创建计划任务.
 * <br>使用注解方式.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-21 上午09:08:18
 */
//@Component
public class SpringJobManager {

	private static final Logger logger = LoggerFactory
            .getLogger(SpringJobManager.class);
	
//    @Scheduled(cron="*/10 * * * * *") 
    public void s10(){
    	logger.info("==== 十秒执行一次=======10s");
    }
    
    public void xmlTest(){
    	logger.info("xmlTest");
    }
    
}