/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
package test.json;

import com.google.common.collect.Lists;
import com.lushapp.common.utils.mapper.JsonMapper;

import java.util.Date;
import java.util.List;

/**
 * @author : honey.zhao@aliyun.com  
 * @date : 2014-04-29 19:37
 */
public class JsonMaperTest {
    public static void main(String[] args) {
        JsonMapper jsonMapper = JsonMapper.getInstance();
        Javabean javabean = new Javabean("name1",100,new Date());
        List<Javabean> list = Lists.newArrayList();
        list.add(javabean);
//        传统模式 转换所有属性  需要在bean上加上注解 @JsonFilter(" ")
        System.out.println(jsonMapper.toJson(javabean));
//        排除属性
        System.out.println(jsonMapper.toJsonWithExcludeProperties(javabean,new String[]{"name"}));
        //转换指定属性
        Javabean javabean2 = new Javabean("name2",101,new Date());
        list.add(javabean2);
        System.out.println(jsonMapper.toJson(javabean));

        //集合属性过滤
        System.out.println(jsonMapper.toJson(list, Javabean.class,new String[]{"name"}));
    }
}
