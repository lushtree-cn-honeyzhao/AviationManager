/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.common.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 为Response设置Expires等Header的Filter.
 * <p/>
 * eg.在web.xml中设置
 * <filter>
 * <filter-name>expiresHeaderFilter</filter-name>
 * <filter-class>com.lushapp.common.web.ResponseHeaderFilter</filter-class>
 * <init-param>
 * <param-name>Cache-Control</param-name>
 * <param-value>public, max-age=31536000</param-value>
 * </init-param>
 * </filter>
 * <filter-mapping>
 * <filter-name>expiresHeaderFilter</filter-name>
 * <url-pattern>/img/*</url-pattern>
 * </filter-mapping>
 *
 * @author honey.zhao@aliyun.com  
 */
public class ResponseHeaderFilter implements Filter {

    private FilterConfig fc;

    /**
     * 设置Filter在web.xml中定义的所有参数到Response.
     */
    @SuppressWarnings("rawtypes")
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletResponse response = (HttpServletResponse) res;

        // set the provided HTTP response parameters
        for (Enumeration e = fc.getInitParameterNames(); e.hasMoreElements(); ) {
            String headerName = (String) e.nextElement();
            response.addHeader(headerName, fc.getInitParameter(headerName));
        }
        // pass the request/response on
        chain.doFilter(req, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig filterConfig) {
        this.fc = filterConfig;
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }
}
