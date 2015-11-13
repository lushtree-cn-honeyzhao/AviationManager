/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.core.tag;


import com.lushapp.core.security.SecurityUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * 判断是否具有某个角色编码权限
 * User: honey.zhao@aliyun.com  
 * Date: 13-11-17 下午7:54
 */
@SuppressWarnings("serial")
public class HasRoleTag extends TagSupport {

    /**
     * 角色编码访问权限字符串 多个以";"分割
     */
    private String name = "";

    @Override
    public int doStartTag() throws JspException {
        if (!"".equals(this.name)) {
            String[] permissonNames = name.split(";");
            for (int i = 0; i < permissonNames.length; i++) {
                if (SecurityUtils.isPermittedRole(permissonNames[i])) {
                    return TagSupport.EVAL_BODY_INCLUDE;
                }
            }

        }
        return SKIP_BODY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}