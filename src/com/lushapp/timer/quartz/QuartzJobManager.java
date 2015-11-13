/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.timer.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz定时任务.
 * <br>使用配置文件方式.
 * @author honey.zhao@aliyun.com  
 * @date 2014-4-30 上午12:01:18
 */
public class QuartzJobManager {

    private static final Logger logger = LoggerFactory
            .getLogger(QuartzJobManager.class);

    /**
     * 定时器调度的方法.
     * <br>测试方法.
     */
    public void testTask() {
        try {
            logger.info("测试定时任务开始...");
            logger.info("测试定时任务结束.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
