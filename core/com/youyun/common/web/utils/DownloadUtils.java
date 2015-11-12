/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
package com.youyun.common.web.utils;

import com.youyun.common.utils.StringUtils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 附件下载工具类
 * @author : honey.zhao@aliyun.com  
 * @date : 2014-05-05 22:50
 */
public class DownloadUtils {

    public static void download(HttpServletRequest request, HttpServletResponse response, String filePath) throws IOException {
        download(request, response, filePath, "");
    }

    public static void download(HttpServletRequest request, HttpServletResponse response, String filePath, String displayName) throws IOException {
        File file = new File(filePath);

        if(StringUtils.isEmpty(displayName)) {
            displayName = file.getName();
        }

        if (!file.exists() || !file.canRead()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("您下载的文件不存在！");
            return;
        }

        response.reset();
        WebUtils.setNoCacheHeader(response);

        response.setContentType("application/x-download");
        response.setContentLength((int) file.length());

        String displayFilename = displayName.substring(displayName.lastIndexOf("_") + 1);
        displayFilename = displayFilename.replace(" ", "_");
        WebUtils.setDownloadableHeader(request,response,displayFilename);
        BufferedInputStream is = null;
        OutputStream os = null;
        try {

            os = response.getOutputStream();
            is = new BufferedInputStream(new FileInputStream(file));
            IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }


    public static void download(HttpServletRequest request, HttpServletResponse response, String displayName, byte[] bytes) throws IOException {

        if (ArrayUtils.isEmpty(bytes)) {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("您下载的文件不存在！");
            return;
        }


        response.reset();
        WebUtils.setNoCacheHeader(response);

        response.setContentType("application/x-download");
        response.setContentLength((int) bytes.length);

        String displayFilename = displayName.substring(displayName.lastIndexOf("_") + 1);
        displayFilename = displayFilename.replace(" ", "_");
        WebUtils.setDownloadableHeader(request,response,displayFilename);
        BufferedInputStream is = null;
        OutputStream os = null;
        try {

            os = response.getOutputStream();
            is = new BufferedInputStream(new ByteArrayInputStream(bytes));
            IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
}