/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.web;

import com.google.common.collect.Lists;
import com.lushapp.common.model.Combobox;
import com.lushapp.common.model.Datagrid;
import com.lushapp.common.model.Result;
import com.lushapp.common.orm.PropertyFilter;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.utils.StringUtils;
import com.lushapp.common.web.springmvc.BaseController;
import com.lushapp.modules.sys.entity.DictionaryType;
import com.lushapp.modules.sys.service.DictionaryTypeManager;
import com.lushapp.utils.SelectType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 数据字典类型DictionaryType管理 Controller层.
 *
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-11 下午4:36:24
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping(value = "/sys/dictionary-type")
public class DictionaryTypeController
        extends BaseController<DictionaryType,Long> {


    @Autowired
    private DictionaryTypeManager dictionaryTypeManager;

    @Override
    public EntityManager<DictionaryType, Long> getEntityManager() {
        return dictionaryTypeManager;
    }

    @RequestMapping(value = {""})
    public String list() {
        return "modules/sys/dictionary-type";
    }

    @RequestMapping(value = {"input"})
    public String input(@ModelAttribute("model")DictionaryType dictionaryType) {
        return "modules/sys/dictionary-type-input";
    }


    @RequestMapping(value = {"save"})
    @ResponseBody
    public Result save(@ModelAttribute("model") DictionaryType dictionaryType) {
        Result result = null;
        dictionaryType.setGroupDictionaryType(null);
        DictionaryType groupDictionaryType = dictionaryTypeManager.getByCode(dictionaryType.getGroupDictionaryTypeCode());
        dictionaryType.setGroupDictionaryType(groupDictionaryType);

        // 名称是否重复校验
        DictionaryType checkDictionaryType1 = dictionaryTypeManager
                .getByGroupCode_Name(dictionaryType.getGroupDictionaryTypeCode(), dictionaryType.getName());
        if (checkDictionaryType1 != null
                && !checkDictionaryType1.getId().equals(dictionaryType.getId())) {
            result = new Result(Result.WARN, "分组[" + groupDictionaryType.getName() + "]名称为["
                    + dictionaryType.getName() + "]已存在,请修正!", "name");
            return result;
        }

        // 编码是否重复校验
        DictionaryType checkDictionaryType3 = dictionaryTypeManager
                .getByCode(dictionaryType.getCode());
        if (checkDictionaryType3 != null
                && !checkDictionaryType3.getId().equals(dictionaryType.getId())) {
            result = new Result(Result.WARN, "编码为["
                    + dictionaryType.getCode() + "]已存在,请修正!", "code");
            logger.debug(result.toString());
            return result;
        }
        //修改操作 避免自关联数据的产生
        if (dictionaryType.getId() != null) {
            if (dictionaryType.getCode().equals(dictionaryType.getGroupDictionaryTypeCode())) {
                result = new Result(Result.ERROR, "不允许发生自关联.",
                        null);
                logger.debug(result.toString());
                return result;
            }
        }

        dictionaryTypeManager.saveEntity(dictionaryType);
        result = Result.successResult();
        logger.debug(result.toString());
        return result;
    }


    /**
     * 下拉列表
     */
    @RequestMapping(value = {"combobox"})
    @ResponseBody
    public List<Combobox> combobox(String selectType) throws Exception {
        List<DictionaryType> list = dictionaryTypeManager.getGroupDictionaryTypes();
        List<Combobox> cList = Lists.newArrayList();

        //为combobox添加  "---全部---"、"---请选择---"
        if (!StringUtils.isBlank(selectType)) {
            SelectType s = SelectType.getSelectTypeValue(selectType);
            if (s != null) {
                Combobox selectCombobox = new Combobox("", s.getDescription());
                cList.add(selectCombobox);
            }
        }
        for (DictionaryType d : list) {
            List<DictionaryType> subDictionaryTypes = d.getSubDictionaryTypes();
            for (DictionaryType subDictionaryType : subDictionaryTypes) {
                Combobox combobox = new Combobox(subDictionaryType.getCode(), subDictionaryType.getName(), d.getName());
                cList.add(combobox);
            }

        }
        return cList;
    }

    /**
     * 分组下拉列表
     */
    @RequestMapping(value = {"group_combobox"})
    @ResponseBody
    public List<Combobox> group_combobox(String selectType) throws Exception {
        List<DictionaryType> list = dictionaryTypeManager.getGroupDictionaryTypes();
        List<Combobox> cList = Lists.newArrayList();

        //为combobox添加  "---全部---"、"---请选择---"
        if (!StringUtils.isBlank(selectType)) {
            SelectType s = SelectType.getSelectTypeValue(selectType);
            if (s != null) {
                Combobox selectCombobox = new Combobox("", s.getDescription());
                cList.add(selectCombobox);
            }
        }
        for (DictionaryType d : list) {
            Combobox combobox = new Combobox(d.getCode(), d.getName());
            cList.add(combobox);
        }
        return cList;
    }

    /**
     * 排序最大值.
     */
    @RequestMapping(value = {"maxSort"})
    @ResponseBody
    public Result maxSort() throws Exception {
        Integer maxSort = dictionaryTypeManager.getMaxSort();
        Result result = new Result(Result.SUCCESS, null, maxSort);
        return result;
    }

    /**
     * 数据列表. 无分页.
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"treegrid"})
    @ResponseBody
    public Datagrid<DictionaryType> treegrid(String sort, String order) throws Exception {
        List<PropertyFilter> filters = Lists.newArrayList();
        List<DictionaryType> p = getEntityManager().find(filters, sort, order);
        Datagrid<DictionaryType> dg = new Datagrid<DictionaryType>(p.size(), p);
        return dg;
    }


}
