/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.common.orm.mybatis.dialect.db;

import com.lushapp.common.orm.mybatis.dialect.Dialect;

/**
 * @author honey.zhao@aliyun.com  
 * @version 2014-7-16
 */
public class DerbyDialect implements Dialect {
   
    public boolean supportsLimit() {
        return false;
	}

   
    public String getLimitString(String sql, int offset, int limit) {
//        return getLimitString(sql,offset,Integer.toString(offset),limit,Integer.toString(limit));
        throw new UnsupportedOperationException("paged queries not supported");
    }

    /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符号(placeholder)替换.
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
     * select * from user limit :offset,:limit
     * </pre>
     *
     * @param sql               实际SQL语句
     * @param offset            分页开始纪录条数
     * @param offsetPlaceholder 分页开始纪录条数－占位符号
     * @param limit             分页每页显示纪录条数
     * @param limitPlaceholder  分页纪录条数占位符号
     * @return 包含占位符的分页sql
     */
	public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
		throw new UnsupportedOperationException( "paged queries not supported" );
	}

}
