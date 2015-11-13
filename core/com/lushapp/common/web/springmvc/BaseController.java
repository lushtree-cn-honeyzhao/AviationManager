/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.common.web.springmvc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lushapp.common.exception.ActionException;
import com.lushapp.common.model.Datagrid;
import com.lushapp.common.model.Result;
import com.lushapp.common.orm.Page;
import com.lushapp.common.orm.PropertyFilter;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.orm.hibernate.HibernateWebUtils;
import com.lushapp.common.utils.reflection.MyBeanUtils;
import com.lushapp.common.utils.reflection.ReflectionUtils;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;


/**
 * 控制器支持类基类
 * @param <T> 实体类
 * @param <PK> 主键
 */
public abstract class BaseController<T, PK extends Serializable> extends SimpleController {

    protected Class<T> entityClass;

    protected BaseController() {
        this.entityClass = ReflectionUtils.getClassGenricType(getClass());
    }

    /**
     * EntityManager.
     */
    public abstract EntityManager<T, PK> getEntityManager();

    @ModelAttribute
    public T getModel(@RequestParam(value = "id", required = false) PK id, Model model) {
        T cloneEntity = null;
        if (id != null) {
            T entity = getEntityManager().getById(id);
            //对象拷贝
            if(entity != null){
                try {
                    cloneEntity = (T) MyBeanUtils.cloneBean(entity);
                } catch (Exception e) {
                    cloneEntity = entity;
                    logger.error(e.getMessage(),e);
                }
            }else{
                throw new ActionException("ID为["+id+"]的记录不存在或已被其它用户删除！");
            }
            model.addAttribute("model", cloneEntity);
        }
        return cloneEntity;
    }


    /**
     * 新增或修改.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"save"})
    @ResponseBody
    public Result save(T model) {
        getEntityManager().saveEntity(model);
        return Result.successResult();
    }

    /**
     * 根据ID删除
     *
     * @param id 主键ID
     * @return
     */
    @RequestMapping(value = {"delete/{id}"})
    @ResponseBody
    public Result delete(@PathVariable PK id) {
        getEntityManager().deleteById(id);
        return Result.successResult();
    }

    /**
     * 根据ID集合批量删除.
     *
     * @param ids 主键ID集合
     * @return
     */
    @RequestMapping(value = {"remove"})
    @ResponseBody
    public Result remove(@RequestParam(value = "ids", required = false) List<PK> ids) {
        getEntityManager().deleteByIds(ids);
        return Result.successResult();
    }


    /**
     * EasyUI 列表数据
     *
     * @param page  第几页
     * @param rows  页大小
     * @param sort  排序字段
     * @param order 排序方式 增序:'asc',降序:'desc'
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"datagrid"})
    @ResponseBody
    public Datagrid datagrid(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "rows", required = false, defaultValue = Page.DEFAULT_PAGESIZE + "") int rows,
                             String sort, String order) {
        // 自动构造属性过滤器
        List<PropertyFilter> filters = HibernateWebUtils.buildPropertyFilters(SpringMVCHolder.getRequest());
        Page<T> p = getEntityManager().find(page, rows, sort, order, filters);
        Datagrid<T> datagrid = new Datagrid<T>(p.getTotalCount(), p.getResult());
        return datagrid;
    }

    /**
     * 初始化数据绑定
     * 1. 设置被排除的属性 不自动绑定
     * 2. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 3. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        super.initBinder(binder);
        //设置被排除的属性 不自动绑定
        Object annotationValue = AnnotationUtils.getValue(AnnotationUtils.findAnnotation(entityClass, JsonIgnoreProperties.class));
        if (annotationValue != null) {
            String[] jsonIgnoreProperties = (String[]) annotationValue;
            binder.setDisallowedFields(jsonIgnoreProperties);
        }
    }

}
