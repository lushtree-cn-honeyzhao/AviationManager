/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.common.web.filter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 特殊字符输入过滤拦截器.
 * 
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-22 下午1:51:52
 */
public class InputReplaceFilter implements Filter {

	private Properties pp = new Properties();

	// 非法词、敏感词、特殊字符、配置在初始化参数中
	public void init(FilterConfig config) throws ServletException {
		// 配置文件位置
		String file = config.getInitParameter("file");
		// 文件实际位置
		String realPath = config.getServletContext().getRealPath(file);
		try {
			// 加载非法词
			pp.load(new FileInputStream(realPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		// 过滤编码
		if (rq.getMethod().equalsIgnoreCase("post")) {
			rq.setCharacterEncoding("utf-8");
		} else {
			Iterator its = rq.getParameterMap().values().iterator();
			while (its.hasNext()) {
				String[] params = (String[]) its.next();
				int len = params.length;
				for (int i = 0; i < len; i++) {
					params[i] = new String(params[i].getBytes("utf-8"), "utf-8");
				}
			}
		}
		// 过滤客户端提交表单中特殊字符
		Iterator its = rq.getParameterMap().values().iterator();
		while (its.hasNext()) {
			String[] params = (String[]) its.next();
			for (int i = 0; i < params.length; i++) {
				//特殊字符Html转译
//				params[i] = params[i].replaceAll(params[i], EncodeUtils.htmlEscape(params[i]));
				for (Object oj : pp.keySet()) {
					String key = (String) oj;
					params[i] = params[i].replace(key, pp.getProperty(key));
				}
			}
		}

		chain.doFilter(request, response);
		// 过滤服务器端的特殊字符（服务器端response输出到客户端的特殊汉字（色情、情色、赌博等））
		/*response.setCharacterEncoding("utf-8");
		HttpCharacterResponseWrapper rs = new HttpCharacterResponseWrapper(rep);
//		chain.doFilter(request, response);
		// 得到response输出内容
		String output = rs.getCharArrayWriter().toString();
		// 遍历所有敏感词
		for (Object oj : pp.keySet()) {
			String key = (String) oj;
			// 替换敏感词
			output = output.replace(key, pp.getProperty(key));
		}
		// 通过原来的response输出内容
		response.getWriter().print(output);*/
	}

	public void destroy() {

	}
}