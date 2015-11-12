/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
package com.youyun.core.security.annotation;

import com.youyun.core.security._enum.Logical;

import java.lang.annotation.*;

/**
 * 需要的权限
 * @author : honey.zhao@aliyun.com  
 * @date 2014-10-11 20:06
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissions {

    String[] value();

    Logical logical() default Logical.AND;

}