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
import com.lushapp.common.orm.Page;
import com.lushapp.common.orm.PropertyFilter;
import com.lushapp.common.orm.entity.StatusState;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.utils.StringUtils;
import com.lushapp.common.utils.collections.Collections3;
import com.lushapp.common.utils.mapper.JsonMapper;
import com.lushapp.common.web.springmvc.BaseController;
import com.lushapp.modules.sys._enum.SexType;
import com.lushapp.modules.sys.entity.AviationBuyers;
import com.lushapp.modules.sys.entity.AviationOrder;
import com.lushapp.modules.sys.entity.User;
import com.lushapp.modules.sys.service.AviationBuyersManager;
import com.lushapp.modules.sys.service.AviationOrderManager;
import com.lushapp.modules.sys.service.AviationSuppliersManager;
import com.lushapp.utils.SelectType;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单AviatioinOrder管理 Controller层.
 *
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-21 上午12:20:13
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping(value = "/sys/aviationOrder")
public class AviationOrderController extends BaseController<AviationOrder,Long> {


    @Autowired
    private AviationOrderManager aviationOrderManager;

    @Autowired
    private AviationBuyersManager aviationBuyersManager;

    @Autowired
    private AviationSuppliersManager aviationSuppliersManager;

    @Override
    public EntityManager<AviationOrder, Long> getEntityManager() {
        return aviationOrderManager;
    }


    @RequestMapping(value = {""})
    public String list() {
        return "modules/sys/aviationorder";
    }

    /**
     * @param aviationOrder
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"_input"})
    public String input(@ModelAttribute("model") AviationOrder aviationOrder) throws Exception {
        return "modules/sys/aviationorder-input";
    }

    /**
     *
     * @param aviationOrder
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"_view"})
    public String view(@ModelAttribute("model") AviationOrder aviationOrder) throws Exception {
        return "modules/sys/aviationorder-view";
    }


    @RequestMapping(value = {"_remove"})
    @ResponseBody
    @Override
    public Result remove(@RequestParam(value = "ids", required = false) List<Long> ids) {
        Result result;
        aviationOrderManager.deleteByIds(ids);
        result = Result.successResult();
        logger.debug(result.toString());
        return result;
    }

    /**
     * 保存.
     */
    @RequestMapping(value = {"save"})
    @ResponseBody
    public Result save(@ModelAttribute("model") AviationOrder aviationOrder) {
        Result result = null;
        //-------校验-begin-----


        //-------校验-end-------
        aviationOrderManager.saveEntity(aviationOrder);
        result = Result.successResult();
        logger.debug(result.toString());
        return result;
    }

    /**
     * 用户combogrid所有
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"combogridAll"})
    @ResponseBody
    public String combogridAll() {
        List<PropertyFilter> filters = Lists.newArrayList();
        filters.add(new PropertyFilter("EQI_status", StatusState.normal.getValue().toString()));
        List<AviationOrder> AviationOrders = aviationOrderManager.find(filters, "id", "asc");
        Datagrid<AviationOrder> dg = new Datagrid<AviationOrder>(AviationOrders.size(), AviationOrders);
        return JsonMapper.getInstance().toJson(dg,AviationOrder.class,new String[]{"id","loginName","name","sexView"});
    }


    /**
     * combogrid
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"combogrid"})
    @ResponseBody
    public Datagrid<AviationOrder> combogrid(@RequestParam(value = "ids", required = false)List<Long> ids, String loginNameOrName, Integer rows) throws Exception {
        Criterion statusCriterion = Restrictions.eq("status", StatusState.normal.getValue());
        Criterion[] criterions = new Criterion[0];
        criterions = (Criterion[]) ArrayUtils.add(criterions, 0, statusCriterion);
        Criterion criterion = null;
        if (Collections3.isNotEmpty(ids)) {
            //in条件
            Criterion inCriterion = Restrictions.in("id", ids);

            if (StringUtils.isNotBlank(loginNameOrName)) {
                Criterion loginNameCriterion = Restrictions.like("loginName", loginNameOrName, MatchMode.ANYWHERE);
                Criterion nameCriterion = Restrictions.like("name", loginNameOrName, MatchMode.ANYWHERE);
                Criterion criterion1 = Restrictions.or(loginNameCriterion, nameCriterion);
                criterion = Restrictions.or(inCriterion, criterion1);
            } else {
                criterion = inCriterion;
            }
            //合并查询条件
            criterions = (Criterion[]) ArrayUtils.add(criterions, 0, criterion);
        } else {
            if (StringUtils.isNotBlank(loginNameOrName)) {
                Criterion loginNameCriterion = Restrictions.like("loginName", loginNameOrName, MatchMode.ANYWHERE);
                Criterion nameCriterion = Restrictions.like("name", loginNameOrName, MatchMode.ANYWHERE);
                criterion = Restrictions.or(loginNameCriterion, nameCriterion);
                //合并查询条件
                criterions = (Criterion[]) ArrayUtils.add(criterions, 0, criterion);
            }
        }

        //分页查询
        Page<AviationOrder> p = new Page<AviationOrder>(rows);//分页对象
        p = aviationOrderManager.findByCriteria(p, criterions);
        Datagrid<AviationOrder> dg = new Datagrid<AviationOrder>(p.getTotalCount(), p.getResult());
        return dg;
    }


    /**
     * 客户下拉框
     * 采购商
     *
     * @throws Exception
     */
    @RequestMapping(value = {"customCombobox"})
    @ResponseBody
    public List<Combobox> customCombobox(String selectType) throws Exception {
        List<Combobox> cList = Lists.newArrayList();

        //为combobox添加  "---全部---"、"---请选择---"
        if (!StringUtils.isBlank(selectType)) {
            SelectType s = SelectType.getSelectTypeValue(selectType);
            if (s != null) {
                Combobox selectCombobox = new Combobox("", s.getDescription());
                cList.add(selectCombobox);
            }
        }
        List<AviationBuyers> aviationBuyersList = new ArrayList<AviationBuyers>();

        aviationBuyersList =aviationBuyersManager.findByCriteria(Restrictions.ne("status", StatusState.delete.getValue()));

        for (int i = 0; i < aviationBuyersList.size(); i++) {
            //Combobox combobox = new Combobox(aviationBuyersList[i].getValue().toString(), aviationBuyersList[i].getDescription());
            //cList.add(combobox);
        }
        return cList;
    }

    /**
     * 平台下拉框
     *供应商
     *
     * @throws Exception
     */
    @RequestMapping(value = {"supplierCombobox"})
    @ResponseBody
    public List<Combobox> supplierCombobox(String selectType) throws Exception {
        List<Combobox> cList = Lists.newArrayList();

        //为combobox添加  "---全部---"、"---请选择---"
        if (!StringUtils.isBlank(selectType)) {
            SelectType s = SelectType.getSelectTypeValue(selectType);
            if (s != null) {
                Combobox selectCombobox = new Combobox("", s.getDescription());
                cList.add(selectCombobox);
            }
        }
        SexType[] _enums = SexType.values();
        for (int i = 0; i < _enums.length; i++) {
            Combobox combobox = new Combobox(_enums[i].getValue().toString(), _enums[i].getDescription());
            cList.add(combobox);
        }
        return cList;
    }




}
