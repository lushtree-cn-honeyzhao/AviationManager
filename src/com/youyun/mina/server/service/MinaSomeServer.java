/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.mina.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @Description: Mina Socket服务
 * 
 * @author honey.zhao@aliyun.com
 * 
 * @date 2011-9-29 上午09:02:46
 * 
 */
public class MinaSomeServer {

    private static final Logger logger = LoggerFactory.getLogger(MinaSomeServer.class);

    public String doSome(String msg) {
        logger.info("msg = " + msg);
		return msg;
    }

}
