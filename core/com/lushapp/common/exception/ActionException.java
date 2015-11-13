/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.common.exception;

/**
 * Action层异常, 继承自BaseException.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-11-10 上午12:08:55
 */
@SuppressWarnings("serial")
public class ActionException extends BaseException {

	public ActionException() {
		super();
	}

	public ActionException(String message) {
		super(message);
	}

	public ActionException(Throwable cause) {
		super(cause);
	}

	public ActionException(String message, Throwable cause) {
		super(message, cause);
	}
}
