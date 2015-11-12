/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.common.orm.hibernate;

import org.hibernate.criterion.MatchMode;

/**
 * QBC like查询表达式.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-23 下午10:24:33
 * 
 */
@SuppressWarnings("serial")
public class LikeExpression extends org.hibernate.criterion.LikeExpression {

	protected LikeExpression(String propertyName, String value,
			MatchMode matchMode, Character escapeChar, boolean ignoreCase) {
		super(propertyName, value, matchMode, escapeChar, ignoreCase);
	}

}