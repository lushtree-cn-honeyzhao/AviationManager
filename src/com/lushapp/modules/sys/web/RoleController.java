/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.lushapp.modules.sys.web;

import com.google.common.collect.Lists;
import com.lushapp.common.model.Combobox;
import com.lushapp.common.model.Result;
import com.lushapp.common.model.TreeNode;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.utils.StringUtils;
import com.lushapp.common.utils.collections.Collections3;
import com.lushapp.common.utils.mapper.JsonMapper;
import com.lushapp.common.web.springmvc.BaseController;
import com.lushapp.modules.sys.entity.Resource;
import com.lushapp.modules.sys.entity.Role;
import com.lushapp.modules.sys.entity.User;
import com.lushapp.modules.sys.service.ResourceManager;
import com.lushapp.modules.sys.service.RoleManager;
import com.lushapp.modules.sys.service.UserManager;
import com.lushapp.utils.SelectType;

import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色Role管理 Controller层.
 *
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-11 下午4:36:24
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController<Role,Long> {

    @Autowired
    private RoleManager roleManager;
    @Autowired
    private ResourceManager resourceManager;
    @Autowired
    private UserManager userManager;

    @Override
    public EntityManager<Role, Long> getEntityManager() {
        return roleManager;
    }


    @RequestMapping(value = {""})
    public String list() {
        return "modules/sys/role";
    }

    /**
     * @param role
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"input"})
    public String input(@ModelAttribute("model") Role role) throws Exception {
        return "modules/sys/role-input";
    }

    /**
     * 删除.
     */
    @RequestMapping(value = {"_remove"})
    @ResponseBody
    public Result _remove(@RequestParam(value = "ids", required = false) List<Long> ids) {
        Result result;
        roleManager.deleteByIds(ids);
        result = Result.successResult();
        logger.debug(result.toString());
        return result;
    }

    /**
     * 保存.
     */
    @RequestMapping(value = {"save"})
    @ResponseBody
    public Result save(@ModelAttribute("model") Role role) {
        Result result;
        // 名称重复校验
        Role nameCheckRole = roleManager.findUniqueBy("name", role.getName());
        if (nameCheckRole != null && !nameCheckRole.getId().equals(role.getId())) {
            result = new Result(Result.WARN, "名称为[" + role.getName() + "]已存在,请修正!", "name");
            logger.debug(result.toString());
            return result;
        }

        // 编码重复校验
        if (StringUtils.isNotBlank(role.getCode())) {
            Role checkRole = roleManager.findUniqueBy("code", role.getCode());
            if (checkRole != null && !checkRole.getId().equals(role.getId())) {
                result = new Result(Result.WARN, "编码为[" + role.getCode() + "]已存在,请修正!", "code");
                logger.debug(result.toString());
                return result;
            }
        }

        roleManager.saveEntity(role);
        result = Result.successResult();
        return result;
    }

    /**
     * 设置资源 页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"resource"})
    public String resource(Model model) throws Exception {
        List<TreeNode> treeNodes = resourceManager.getResourceTree(null, false);
        String resourceComboboxData = JsonMapper.nonDefaultMapper().toJson(treeNodes);
        logger.debug(resourceComboboxData);
        model.addAttribute("resourceComboboxData", resourceComboboxData);
        return "modules/sys/role-resource";
    }

    /**
     * 设置角色资源
     *
     * @return
     */
    @RequestMapping(value = {"updateRoleResource"})
    @ResponseBody
    public Result updateRoleResource(@RequestParam(value = "resourceIds", required = false) List<Long> resourceIds, @ModelAttribute("model") Role role) {
        Result result;
        //设置用户角色信息
        List<Resource> resourceList = Lists.newArrayList();
        if (Collections3.isNotEmpty(resourceIds)) {
            for (Long resourceId : resourceIds) {
                Resource resource = resourceManager.loadById(resourceId);
                resourceList.add(resource);
            }
        }
        role.setResources(resourceList);

        roleManager.saveEntity(role);
        result = Result.successResult();
        logger.debug(result.toString());
        return result;
    }

    /**
     * 设置角色用户 页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"user"})
    public String user(@ModelAttribute("model")Role role,Model model) throws Exception {
        model.addAttribute("userIds",JsonMapper.getInstance().toJson(role.getUserIds()));
        return "modules/sys/role-user";
    }

    /**
     * 设置机构用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"updateRoleUser"})
    @ResponseBody
    public Result updateRoleUser(@RequestParam(value = "userIds", required = false) List<Long> userIds, @ModelAttribute("model") Role role) throws Exception {
        Result result;
        //设置用户角色信息
        List<User> userList = Lists.newArrayList();
        if (Collections3.isNotEmpty(userIds)) {
            for (Long userId : userIds) {
                User user = userManager.loadById(userId);
                userList.add(user);
            }
        }

        role.setUsers(userList);

        roleManager.saveEntity(role);
        result = Result.successResult();
        logger.debug(result.toString());
        return result;
    }


    /**
     * 角色下拉框列表.
     */
    @RequestMapping(value = {"combobox"})
    @ResponseBody
    public List<Combobox> combobox(String selectType) throws Exception {
        List<Role> list = roleManager.getAll();
        List<Combobox> cList = Lists.newArrayList();

        //为combobox添加  "---全部---"、"---请选择---"
        if (!StringUtils.isBlank(selectType)) {
            SelectType s = SelectType.getSelectTypeValue(selectType);
            if (s != null) {
                Combobox selectCombobox = new Combobox("", s.getDescription());
                cList.add(selectCombobox);
            }
        }
        for (Role r : list) {
            Combobox combobox = new Combobox(r.getId() + "", r.getName());
            cList.add(combobox);
        }
        return cList;
    }

    /**
     * 机构树.
     */
    @RequestMapping(value = {"tree"})
    @ResponseBody
    public List<TreeNode> tree(String selectType) throws Exception {
        List<TreeNode> treeNodes = Lists.newArrayList();
        List<TreeNode> titleList = Lists.newArrayList();
        List<Role> list = roleManager.getAll();

        // 添加 "---全部---"、"---请选择---"
        if (!StringUtils.isBlank(selectType)) {
            SelectType s = SelectType.getSelectTypeValue(selectType);
            if (s != null) {
                TreeNode selectTreeNode = new TreeNode("",
                        s.getDescription());
                titleList.add(selectTreeNode);
            }
        }
        for(Role r:list){
            TreeNode treeNode = new TreeNode(r.getId().toString(),
                    r.getName());
            treeNodes.add(treeNode);
        }
        List<TreeNode> unionList = ListUtils.union(titleList, treeNodes);
        return unionList;
    }
}
