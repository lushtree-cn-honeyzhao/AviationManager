/**
 *  Copyright (c) 2014-2013 http://www.lushapplication.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.web;

import com.google.common.collect.Lists;
import com.lushapp.common.model.Combobox;
import com.lushapp.common.model.Result;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.utils.StringUtils;
import com.lushapp.common.utils.io.PropertiesLoader;
import com.lushapp.common.web.springmvc.BaseController;
import com.lushapp.modules.sys._enum.LogType;
import com.lushapp.modules.sys.entity.Log;
import com.lushapp.modules.sys.service.LogManager;
import com.lushapp.utils.AppConstants;
import com.lushapp.utils.SelectType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 日志
 *
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-8 下午5:13
 */
@Controller
@RequestMapping(value = "/sys/log")
public class LogController
        extends BaseController<Log,Long> {

    @Autowired
    private LogManager logManager;

    @Override
    public EntityManager<Log, Long> getEntityManager() {
        return logManager;
    }

    @RequestMapping(value = {""})
    public String list() {
        return "modules/sys/log";
    }

    /**
     * 清空所有日志
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"removeAll"})
    @ResponseBody
    public Result removeAll() throws Exception {
        logManager.removeAll();
        Result result = Result.successResult();
        return result;
    }

    /**
     * 日志类型下拉列表.
     */
    @RequestMapping(value = {"logTypeCombobox"})
    @ResponseBody
    public List<Combobox> logTypeCombobox(String selectType) throws Exception {
        List<Combobox> cList = Lists.newArrayList();
        //为combobox添加  "---全部---"、"---请选择---"
        if (!StringUtils.isBlank(selectType)) {
            SelectType s = SelectType.getSelectTypeValue(selectType);
            if (s != null) {
                Combobox selectCombobox = new Combobox("", s.getDescription());
                cList.add(selectCombobox);
            }
        }

        LogType[] lts = LogType.values();
        for (int i = 0; i < lts.length; i++) {
            Combobox combobox = new Combobox();
            combobox.setValue(lts[i].getValue().toString());
            combobox.setText(lts[i].getDescription());
            cList.add(combobox);
        }
        return cList;
    }

    /**
     * 设置日志保留时间 页面
     *
     * @return
     */
    @RequestMapping(value = {"time"})
    public String time(Model model) throws Exception {
        model.addAttribute("keepTime", AppConstants.getLogKeepTime());
        return "modules/sys/log-time";
    }

    /**
     * 更新日志保留时间
     *
     * @throws Exception
     */
    @RequestMapping(value = {"updateKeepTime"})
    @ResponseBody
    public Result updateKeepTime(Integer keepTime) throws Exception {
        Result reslut = null;
        if (keepTime != null) {
            PropertiesLoader propertiesLoader = AppConstants.getConfig();
            AppConstants.getConfig().modifyProperties(AppConstants.CONFIG_FILE_PATH, AppConstants.CONFIG_LOGKEEPTIME, keepTime.toString());
            reslut = Result.successResult();
        } else {
//            throw new ActionException("未设置参数[keepTime].");
            reslut = new Result(Result.WARN, "未设置参数[keepTime].", null);
        }
        return reslut;
    }

}
