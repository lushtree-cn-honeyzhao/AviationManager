/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 */
package com.lushapp.modules.sys.web;

import com.google.common.collect.Lists;
import com.lushapp.common.model.Datagrid;
import com.lushapp.common.model.Menu;
import com.lushapp.common.model.Result;
import com.lushapp.common.model.TreeNode;
import com.lushapp.common.orm.Page;
import com.lushapp.common.orm.entity.StatusState;
import com.lushapp.common.orm.hibernate.DefaultEntityManager;
import com.lushapp.common.utils.IpUtils;
import com.lushapp.common.utils.StringUtils;
import com.lushapp.common.utils.encode.Encrypt;
import com.lushapp.common.web.springmvc.SimpleController;
import com.lushapp.common.web.springmvc.SpringMVCHolder;
import com.lushapp.core.security.SecurityConstants;
import com.lushapp.core.security.SecurityUtils;
import com.lushapp.core.security.SessionInfo;
import com.lushapp.modules.sys._enum.ResourceType;
import com.lushapp.modules.sys.entity.Resource;
import com.lushapp.modules.sys.entity.User;
import com.lushapp.modules.sys.service.ResourceManager;
import com.lushapp.modules.sys.service.UserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

/**
 * 用户登录/注销等前端交互入口
 * @author : honey.zhao@aliyun.com  
 * @date : 2014-05-02 19:50
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends SimpleController {

    @Autowired
    private UserManager userManager;
    @Autowired
    private ResourceManager resourceManager;
    @Autowired
    private DefaultEntityManager defaultEntityManager;

    @RequestMapping(value = {"welcome", ""})
    public String welcome() throws Exception {
        return "login";
    }

    /**
     * 登录验证
     *
     * @param loginName 用户名
     * @param password  密码
     * @param theme     主题
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"login"})
    public Result login(@RequestParam(required = true) String loginName, @RequestParam(required = true) String password,
                        String theme, HttpServletRequest request) {
        Result result = null;
        String msg = null;
        // 获取用户信息
        User user = userManager.getUserByLP(loginName, Encrypt.e(password));
        if (user == null) {
            msg = "用户名或密码不正确!";
        } else if (user.getStatus().intValue() == StatusState.lock.getValue()) {
            msg = "该用户已被锁定，暂不允许登陆!";
        }
        if (msg != null) {
            result = new Result(Result.ERROR, msg, null);
        } else {
            //将用户信息放入session中
            SecurityUtils.putUserToSession(request, user);
            logger.info("用户{}登录系统,IP:{}.", user.getLoginName(), IpUtils.getIpAddr(request));

            //设置调整URL 如果session中包含未被授权的URL 则跳转到该页面
            String resultUrl = request.getContextPath() + "/login/index?theme=" + theme;
            Object unAuthorityUrl = request.getSession().getAttribute(SecurityConstants.SESSION_UNAUTHORITY_URL);
            if (unAuthorityUrl != null) {
                resultUrl = unAuthorityUrl.toString();
                //清空未被授权的URL
                request.getSession().setAttribute(SecurityConstants.SESSION_UNAUTHORITY_URL, null);
            }
            //返回
            result = new Result(Result.SUCCESS, "用户验证通过!", resultUrl);
        }

        return result;
    }


    /**
     * 用户注销
     * @param request
     * @return
     */
    @RequestMapping(value = {"logout"})
    public String logout(HttpServletRequest request) {
        SessionInfo sessionInfo = SecurityUtils.getCurrentSessionInfo();
        if (sessionInfo != null) {
            // 退出时清空session中的内容
            String sessionId = request.getSession().getId();
            //由监听器更新在线用户列表
            SecurityUtils.removeUserFromSession(sessionId, false);
            SpringMVCHolder.getSession().setAttribute(SecurityConstants.SESSION_SESSIONINFO,null);
            logger.info("用户{}退出系统.", sessionInfo.getLoginName());
        }
        return "redirect:/";
    }

    @RequestMapping(value = {"index"})
    public String index(String theme) {
        //根据客户端指定的参数跳转至 不同的主题 如果未指定 默认:index
        if (StringUtils.isNotBlank(theme) && (theme.equals("app") || theme.equals("index"))) {
            return "layout/" + theme;
        } else {
            return "layout/index";
        }
    }


    /**
     * 导航菜单.
     */
    @ResponseBody
    @RequestMapping(value = {"navTree"})
    public List<TreeNode> navTree() throws Exception {
        List<TreeNode> treeNodes = Lists.newArrayList();
        SessionInfo sessionInfo = SecurityUtils.getCurrentSessionInfo();
        if (sessionInfo != null) {
            treeNodes = resourceManager.getNavMenuTreeByUserId(sessionInfo.getUserId());
        }
        return treeNodes;
    }

    /**
     * 当前在线用户
     *
     * @throws Exception
     */
    @RequestMapping(value = {"onlineDatagrid"})
    @ResponseBody
    public Datagrid<SessionInfo> onlineDatagrid() throws Exception {
        return SecurityUtils.getSessionUser();
    }


    /**
     * 桌面版 开始菜单
     */
    @RequestMapping(value = {"startMenu"})
    @ResponseBody
    public List<Menu> startMenu() throws Exception {
        List<Menu> menus = Lists.newArrayList();
        SessionInfo sessionInfo = SecurityUtils.getCurrentSessionInfo();
        if (sessionInfo != null) {
            List<Resource> rootResources = Lists.newArrayList();
            User superUser = userManager.getSuperUser();
            if (sessionInfo != null && superUser != null
                    && sessionInfo.getUserId().equals(superUser.getId())) {// 超级用户
                rootResources = resourceManager.getByParentId(null, StatusState.normal.getValue());
            } else if (sessionInfo != null) {
                rootResources = resourceManager.getResourcesByUserId(sessionInfo.getUserId(), null);
                //去除非菜单资源
                Iterator<Resource> iterator = rootResources.iterator();
                while (iterator.hasNext()) {
                    if (!ResourceType.menu.getValue().equals(iterator.next().getType())) {
                        iterator.remove();
                    }
                }
            }
            for (Resource parentResource : rootResources) {
                Menu menu = this.resourceToMenu(parentResource, true);
                if (menu != null) {
                    menus.add(menu);
                }
            }
        }
        return menus;
    }


    /**
     * 桌面版 桌面应用程序列表
     */
    @RequestMapping(value = {"apps"})
    @ResponseBody
    public List<Menu> apps() throws Exception {
        HttpServletRequest request = SpringMVCHolder.getRequest();
        List<Menu> menus = Lists.newArrayList();
        String head = this.getHeadFromUrl(request.getRequestURL().toString());
        SessionInfo sessionInfo = SecurityUtils.getCurrentSessionInfo();
        if (sessionInfo != null) {
            List<Resource> resources = Lists.newArrayList();
            User superUser = userManager.getSuperUser();
            if (sessionInfo != null && superUser != null
                    && sessionInfo.getUserId().equals(superUser.getId())) {// 超级用户
                resources = resourceManager.getAll("orderNo", Page.ASC);
            } else if (sessionInfo != null) {
                resources = resourceManager.getResourcesByUserId(sessionInfo.getUserId());
            }
            for (Resource resource : resources) {
                if (resource != null && StringUtils.isNotBlank(resource.getUrl())) {
                    if (ResourceType.menu.getValue().equals(resource.getType())) {
                        Menu menu = new Menu();
                        menu.setId(resource.getId().toString());
                        menu.setText(resource.getName());
                        String url = resource.getUrl();
                        if (url.startsWith("http")) {
                            url = resource.getUrl();
                        } else if (url.startsWith("/")) {
                            url = head + request.getContextPath() + url;
                        } else {
                            url = head + request.getContextPath() + "/" + url;
                        }
                        menu.setHref(url);
                        menu.setIconCls(resource.getIconCls());
                        menus.add(menu);
                    }
                }

            }
        }
        return menus;
    }

    /**
     * 资源转M
     *
     * @param resource  资源
     * @param isCascade 是否级联
     * @return
     */
    private Menu resourceToMenu(Resource resource, boolean isCascade) {
        HttpServletRequest request = SpringMVCHolder.getRequest();
        Assert.notNull(resource, "参数resource不能为空");
        String head = this.getHeadFromUrl(request.getRequestURL().toString());
        if (ResourceType.menu.getValue().equals(resource.getType())) {
            Menu menu = new Menu();
            menu.setId(resource.getId().toString());
            menu.setText(resource.getName());
            menu.setHref(head + request.getContextPath() + resource.getUrl());
            if (isCascade) {
                List<Menu> childrenMenus = Lists.newArrayList();
                for (Resource subResource : resource.getSubResources()) {
                    if (ResourceType.menu.getValue().equals(subResource.getType())) {
                        childrenMenus.add(resourceToMenu(subResource, true));
                    }
                }
                menu.setChildren(childrenMenus);
            }
            return menu;
        }
        return null;
    }

    /**
     * 根据URL地址获取请求地址前面部分信息
     *
     * @param url
     * @return
     */
    private String getHeadFromUrl(String url) {
        int firSplit = url.indexOf("//");
        String proto = url.substring(0, firSplit + 2);
        int webSplit = url.indexOf("/", firSplit + 2);
        int portIndex = url.indexOf(":", firSplit);
        String webUrl = url.substring(firSplit + 2, webSplit);
        String port = "";
        if (portIndex >= 0) {
            webUrl = webUrl.substring(0, webUrl.indexOf(":"));
            port = url.substring(portIndex + 1, webSplit);
        } else {
            port = "80";
        }
        return proto + webUrl + ":" + port;
    }


    /**
     * 异步方式返回session信息
     */
    @RequestMapping(value = {"sessionInfo"})
    @ResponseBody
    public Result sessionInfo() {
        Result result = Result.successResult();
        SessionInfo sessionInfo = SecurityUtils.getCurrentSessionInfo();
        result.setObj(sessionInfo);
        if (logger.isDebugEnabled()) {
            logger.debug(result.toString());
        }
        return result;
    }


}
