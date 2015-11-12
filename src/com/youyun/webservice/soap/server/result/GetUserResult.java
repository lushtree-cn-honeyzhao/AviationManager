/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.webservice.soap.server.result;

import com.youyun.modules.sys.entity.User;
import com.youyun.webservice.soap.server.WsConstants;

import javax.xml.bind.annotation.XmlType;


/**
 * GetUser方法的返回结果.
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-11 下午9:28:05 
 *
 */
@XmlType(name = "GetUserResult", namespace = WsConstants.NS)
public class GetUserResult extends WSResult {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
