/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
package com.youyun.modules.sys.utils;

import com.youyun.common.spring.SpringContextHolder;
import com.youyun.modules.sys.entity.Dictionary;
import com.youyun.modules.sys.service.DictionaryManager;

/**
 * 数据字典工具类
 * @author : honey.zhao@aliyun.com  
 * @date : 2014-10-17 21:22
 */
public class DictionaryUtils {

    public static final String DIC_BUG = "bug";

    /**
     * 根据字典逼编码得到字典名称
     * @param dictionaryCode 字典编码
     * @return
     */
    public static String getDictionaryNameByCode(String dictionaryCode){
        DictionaryManager dictionaryManager = SpringContextHolder.getBean(DictionaryManager.class);
        Dictionary dictionary = dictionaryManager.getByCode(dictionaryCode);
        if(dictionary != null){
            return dictionary.getName();
        }
        return null;
    }

    /**
     *
     * @param dictionaryTypeCode 字典类型编码
     * @param dictionaryCode 字典项编码
     * @return
     */
    public static String getDictionaryNameByDC(String dictionaryTypeCode,String dictionaryCode){
        DictionaryManager dictionaryManager = SpringContextHolder.getBean(DictionaryManager.class);
        Dictionary dictionary = dictionaryManager.getDictionaryByDC(dictionaryTypeCode,dictionaryCode);
        if(dictionary != null){
            return dictionary.getName();
        }
        return null;
    }

    /**
     *
     * @param dictionaryTypeCode 字典类型编码
     * @param dictionaryCode 字典项编码
     * @return
     */
    public static String getDictionaryNameByDV(String dictionaryTypeCode,String dictionaryCode){
        DictionaryManager dictionaryManager = SpringContextHolder.getBean(DictionaryManager.class);
        Dictionary dictionary = dictionaryManager.getDictionaryByDV(dictionaryTypeCode,dictionaryCode);
        if(dictionary != null){
            return dictionary.getName();
        }
        return null;
    }

}