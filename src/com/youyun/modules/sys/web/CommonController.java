/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.modules.sys.web;

import com.youyun.common.model.Result;
import com.youyun.common.orm.hibernate.EntityManager;
import com.youyun.common.utils.mapper.JsonMapper;
import com.youyun.common.web.springmvc.SimpleController;
import com.youyun.common.web.utils.MediaTypes;
import com.youyun.modules.sys.service.CommonManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

/**
 * 提供公共方法的Controller.
 *
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-25 下午1:59:38
 */
@Controller
@RequestMapping("/common")
public class CommonController extends SimpleController {

    @Autowired
    private CommonManager commonManager;

    /**
     * 字段校验
     *
     * @param entityName 实体类名称 例如: "Resource"
     * @param fieldName  属性名称
     * @param fieldValue 属性值
     * @param rowId      主键ID
     * @return
     */
    @RequestMapping("fieldCheck")
    @ResponseBody
    public Result fieldCheck(String entityName, String fieldName, String fieldValue, Long rowId) {
        Long entityId = commonManager.getIdByProperty(entityName, fieldName,
                fieldValue);
        boolean isCheck = true;// 是否通过检查
        if (entityId != null) {
            if (rowId != null) {
                if (!rowId.equals(entityId)) {
                    isCheck = false;
                }
            } else {
                isCheck = false;
            }

        }
        return new Result(Result.SUCCESS, null, isCheck);
    }

    /**
     * JsonP跨域输出示例
     * @param callbackName 回调方法
     * @return
     */
    @RequestMapping(value = "mashup", produces = MediaTypes.JAVASCRIPT_UTF_8)
    @ResponseBody
    public String mashup(@RequestParam("callback") String callbackName) {

        // 设置需要被格式化为JSON字符串的内容.
        Map<String, String> map = Collections.singletonMap("content", "<p>你好，世界！</p>");

        // 渲染返回结果.
        return JsonMapper.getInstance().toJsonP(callbackName, map);
    }

}
