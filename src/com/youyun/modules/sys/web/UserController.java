/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Service;
import com.youyun.common.exception.ServiceException;
import com.youyun.common.model.Combobox;
import com.youyun.common.model.Datagrid;
import com.youyun.common.model.Result;
import com.youyun.common.model.TreeNode;
import com.youyun.common.orm.Page;
import com.youyun.common.orm.PropertyFilter;
import com.youyun.common.orm.entity.StatusState;
import com.youyun.common.orm.hibernate.EntityManager;
import com.youyun.common.utils.StringUtils;
import com.youyun.common.utils.collections.Collections3;
import com.youyun.common.utils.encode.Encrypt;
import com.youyun.common.utils.mapper.JsonMapper;
import com.youyun.common.web.springmvc.BaseController;
import com.youyun.core.security.SecurityUtils;
import com.youyun.core.security.SessionInfo;
import com.youyun.modules.sys._enum.SexType;
import com.youyun.modules.sys.entity.*;
import com.youyun.modules.sys.service.*;
import com.youyun.utils.AppConstants;
import com.youyun.utils.SelectType;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.SystemException;
import java.util.Iterator;
import java.util.List;

/**
 * 用户User管理 Controller层.
 *
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-21 上午12:20:13
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController<User,Long> {


    @Autowired
    private UserManager userManager;
    @Autowired
    private OrganManager organManager;
    @Autowired
    private RoleManager roleManager;
    @Autowired
    private ResourceManager resourceManager;
    @Autowired
    private PostManager postManager;

    @Override
    public EntityManager<User, Long> getEntityManager() {
        return userManager;
    }


    @RequestMapping(value = {""})
    public String list() {
        return "modules/sys/user";
    }

    @RequestMapping(value = {"select"})
    public String selectPage(String userIds,Model model) {
        List<User> users = Lists.newArrayList();
        if (StringUtils.isNotBlank(userIds)) {
            String[] userIdss = userIds.split(",");
            List<Long> userIdLs = Lists.newArrayList();
            for (String userId : userIdss) {
                userIdLs.add(Long.valueOf(userId));
            }
            Criterion inUserCriterion = Restrictions.in("id",userIdLs);
            users = userManager.findByCriteria(inUserCriterion);
        }
        model.addAttribute("users", users);

        return "modules/sys/user-select";
    }

    @RequestMapping(value = {"combogridSelectUser"})
    @ResponseBody
    public String combogridSelectUser(Long organId, Long roleId,String loginNameOrName,String sort, String order) {
        List<User> users = userManager.getUsersByOrgOrRole(organId, roleId,loginNameOrName, sort, order);
        Datagrid<User> dg = new Datagrid<User>(users.size(), users);
        return JsonMapper.getInstance().toJson(dg,User.class,new String[]{"id","loginName","name","sexView"});
    }

    /**
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"input"})
    public String input(@ModelAttribute("model") User user) throws Exception {
        return "modules/sys/user-input";
    }


    @RequestMapping(value = {"_remove"})
    @ResponseBody
    @Override
    public Result remove(@RequestParam(value = "ids", required = false) List<Long> ids) {
        Result result;
        userManager.deleteByIds(ids);
        result = Result.successResult();
        logger.debug(result.toString());
        return result;
    }

    /**
     * 自定义查询
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"userDatagrid"})
    @ResponseBody
    public Datagrid<User> userDatagrid(Long organId, String loginNameOrName,
                                       Integer page, Integer rows, String sort, String order) throws Exception {
        Page<User> p = userManager.getUsersByQuery(organId, loginNameOrName, page, rows, sort, order);
        Datagrid<User> dg = new Datagrid<User>(p.getTotalCount(), p.getResult());
        return dg;
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
        List<User> users = userManager.find(filters, "id", "asc");
        Datagrid<User> dg = new Datagrid<User>(users.size(), users);
        return JsonMapper.getInstance().toJson(dg,User.class,new String[]{"id","loginName","name","sexView"});
    }

    /**
     * 获取机构用户
     * @param organId 机构ID
     * @return
     */
    @RequestMapping(value = {"combogridOrganUser"})
    @ResponseBody
    public String combogridOrganUser(@RequestParam(value = "organId", required = true)Long organId) {
        List<User> users = userManager.getUsersByOrganId(organId);
        Datagrid dg = new Datagrid(users.size(),users);
        return JsonMapper.getInstance().toJson(dg,User.class,new String[]{"id","loginName","name"});
    }


    /**
     * combogrid
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"combogrid"})
    @ResponseBody
    public Datagrid<User> combogrid(@RequestParam(value = "ids", required = false)List<Long> ids, String loginNameOrName, Integer rows) throws Exception {
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
        Page<User> p = new Page<User>(rows);//分页对象
        p = userManager.findByCriteria(p, criterions);
        Datagrid<User> dg = new Datagrid<User>(p.getTotalCount(), p.getResult());
        return dg;
    }

    /**
     * 保存.
     */
    @RequestMapping(value = {"save"})
    @ResponseBody
    public Result save(@ModelAttribute("model") User user) {
        Result result = null;
        // 名称重复校验
        User nameCheckUser = userManager.getUserByLoginName(user.getLoginName());
        if (nameCheckUser != null && !nameCheckUser.getId().equals(user.getId())) {
            result = new Result(Result.WARN, "登录名为[" + user.getLoginName() + "]已存在,请修正!", "loginName");
            logger.debug(result.toString());
            return result;
        }

        if (user.getId() == null) {// 新增
            user.setPassword(Encrypt.e(user.getPassword()));
        } else {// 修改
            User superUser = userManager.getSuperUser();
            User sessionUser = userManager.getCurrentUser();
            if (!sessionUser.getId().equals(superUser.getId())) {
                result = new Result(Result.ERROR, "超级用户信息仅允许自己修改!",null);
                logger.debug(result.toString());
                return result;
            }
        }
        userManager.saveEntity(user);
        result = Result.successResult();
        logger.debug(result.toString());
        return result;
    }

    /**
     * 修改用户密码页面.
     */
    @RequestMapping(value = {"password"})
    public String password() throws Exception {
        return "modules/sys/user-password";

    }

    /**
     * 修改用户密码.
     * <br>参数upateOperate 需要密码"1" 不需要密码"0".
     */
    @RequestMapping(value = {"updateUserPassword"})
    @ResponseBody
    public Result updateUserPassword(@ModelAttribute("model") User user, String upateOperate, String newPassword) throws Exception {
        Result result;
        if (!StringUtils.isEmpty(upateOperate)) {
            User u = userManager.loadById(user.getId());
            if (u != null) {
                boolean isCheck = true;
                //需要输入原始密码
                if (AppConstants.USER_UPDATE_PASSWORD_YES.equals(upateOperate)) {
                    String originalPassword = u.getPassword(); //数据库存储的原始密码
                    String pagePassword = u.getPassword(); //页面输入的原始密码（未加密）
                    if (!originalPassword.equals(Encrypt.e(pagePassword))) {
                        isCheck = false;
                    }
                }
                //不需要输入原始密码
                if (AppConstants.USER_UPDATE_PASSWORD_NO.equals(upateOperate)) {
                    isCheck = true;
                }
                if (isCheck) {
                    u.setPassword(Encrypt.e(newPassword));
                    userManager.saveEntity(u);
                    result = Result.successResult();
                } else {
                    result = new Result(Result.WARN, "原始密码输入错误.", "password");
                }
            } else {
                result = new Result(Result.ERROR, "修改的用户不存在或已被删除.", null);
            }
        } else {
            result = Result.errorResult();
            logger.warn("请求参数错误,未设置参数[upateOperate].");
        }
        logger.debug(result.toString());
        return result;
    }


    /**
     * 修改用户角色页面.
     */
    @RequestMapping(value = {"role"})
    public String role() throws Exception {
        return "modules/sys/user-role";
    }

    /**
     * 修改用户角色.
     */
    @RequestMapping(value = {"updateUserRole"})
    @ResponseBody
    public Result updateUserRole(@ModelAttribute("model") User user, @RequestParam(value = "roleIds", required = false)List<Long> roleIds) throws Exception {
        Result result = null;
        List<Role> rs = Lists.newArrayList();
        if(Collections3.isNotEmpty(roleIds)){
            for (Long id : roleIds) {
                Role role = roleManager.loadById(id);
                rs.add(role);
            }
        }

        user.setRoles(rs);
        userManager.saveEntity(user);
        result = Result.successResult();
        return result;
    }

    /**
     * 设置组织机构页面.
     */
    @RequestMapping(value = {"organ"})
    public String organ(@ModelAttribute("model") User user, Model model) throws Exception {
        //设置默认组织机构初始值
        List<Combobox> defaultOrganCombobox = Lists.newArrayList();
        if (user.getId() != null) {
            List<Organ> organs = user.getOrgans();
            Combobox combobox;
            if (!Collections3.isEmpty(organs)) {
                for (Organ organ : organs) {
                    combobox = new Combobox(organ.getId().toString(), organ.getName());
                    defaultOrganCombobox.add(combobox);
                }
            }
        }
        String defaultOrganComboboxData = JsonMapper.nonDefaultMapper().toJson(defaultOrganCombobox);
        logger.debug(defaultOrganComboboxData);
        model.addAttribute("defaultOrganComboboxData", defaultOrganComboboxData);
        return "modules/sys/user-organ";
    }

    /**
     * 设置用户组织机构.
     */
    @RequestMapping(value = {"updateUserOrgan"})
    @ResponseBody
    public Result updateUserOrgan(@ModelAttribute("model") User model, @RequestParam(value = "organIds", required = false)List<Long> organIds, String defaultOrganId) throws Exception {
        Result result = null;
        List<Organ> oldOrgans = model.getOrgans();
        //绑定组织机构
        model.setOrgans(null);
        List<Organ> organs = Lists.newArrayList();
        if(Collections3.isNotEmpty(organIds)){
            for (Long organId : organIds) {
                Organ organ = organManager.loadById(organId);
                organs.add(organ);
                if(Collections3.isNotEmpty(oldOrgans)){
                    Iterator<Organ> iterator = oldOrgans.iterator();
                    while (iterator.hasNext()){
                        Organ oldOrgan = iterator.next();
                        if(oldOrgan.getId().equals(organ.getId())){
                            iterator.remove();
                        }
                    }

                }
            }
        }


        //去除用户已删除机构下的岗位信息
        List<Post> userPosts = model.getPosts();
        if(Collections3.isNotEmpty(oldOrgans)){//已删除的机构
            Iterator<Organ> iterator = oldOrgans.iterator();
            while (iterator.hasNext()){
                Organ oldOrgan = iterator.next();
                List<Post> organPosts = oldOrgan.getPosts();
                for(Post organPost:organPosts){
                    if(Collections3.isNotEmpty(userPosts)){
                        Iterator<Post> iteratorPost = userPosts.iterator();
                        while (iteratorPost.hasNext()){
                            Post userPost = iteratorPost.next();
                            if(userPost.getId().equals(organPost.getId())){
                                iteratorPost.remove();
                            }
                        }
                    }
                }
            }

        }


        model.setOrgans(organs);

        //绑定默认组织机构
        model.setDefaultOrgan(null);
        Organ defaultOrgan = null;
        if (defaultOrganId != null) {
            defaultOrgan = organManager.loadById(model.getDefaultOrganId());
        }
        model.setDefaultOrgan(defaultOrgan);

        userManager.saveEntity(model);
        result = Result.successResult();
        return result;
    }

    /**
     * 设置用户岗位页面.
     */
    @RequestMapping(value = {"post"})
    public String post() throws Exception {
        return "modules/sys/user-post";
    }

    /**
     * 修改用户岗位.
     */
    @RequestMapping(value = {"updateUserPost"})
    @ResponseBody
    public Result updateUserPost(@ModelAttribute("model") User model, @RequestParam(value = "postIds", required = false)List<Long> postIds) throws Exception {
        Result result = null;
        List<Post> ps = Lists.newArrayList();
        if(Collections3.isNotEmpty(postIds)){
            for (Long id : postIds) {
                Post post = postManager.loadById(id);
                ps.add(post);
            }
        }

        model.setPosts(ps);

        getEntityManager().saveEntity(model);
        result = Result.successResult();
        return result;
    }

    /**
     * 修改用户资源页面.
     */
    @RequestMapping(value = {"resource"})
    public String resource(Model model) throws Exception {
        List<TreeNode> treeNodes = resourceManager.getResourceTree(null, false);
        String resourceComboboxData = JsonMapper.nonDefaultMapper().toJson(treeNodes);
        logger.debug(resourceComboboxData);
        model.addAttribute("resourceComboboxData", resourceComboboxData);
        return "modules/sys/user-resource";
    }

    /**
     * 修改用户资源.
     */
    @RequestMapping(value = {"updateUserResource"})
    @ResponseBody
    public Result updateUserResource(@ModelAttribute("model") User user, @RequestParam(value = "resourceIds", required = false)List<Long> resourceIds) throws Exception {
        Result result = null;
        List<Resource> rs = Lists.newArrayList();
        if(Collections3.isNotEmpty(resourceIds)){
            for (Long id : resourceIds) {
                Resource resource = resourceManager.loadById(id);
                rs.add(resource);
            }
        }

        user.setResources(rs);
        userManager.saveEntity(user);
        result = Result.successResult();
        return result;

    }

    /**
     * 性别下拉框
     *
     * @throws Exception
     */
    @RequestMapping(value = {"sexTypeCombobox"})
    @ResponseBody
    public List<Combobox> sexTypeCombobox(String selectType) throws Exception {
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
