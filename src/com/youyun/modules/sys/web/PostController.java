/**
 *  Copyright (c) 2014 http://www.lushapplicatioin.com
 *
 *                    
 */
package com.youyun.modules.sys.web;

import com.google.common.collect.Lists;
import com.youyun.common.model.Combobox;
import com.youyun.common.model.Datagrid;
import com.youyun.common.model.Result;
import com.youyun.common.orm.hibernate.EntityManager;
import com.youyun.common.utils.StringUtils;
import com.youyun.common.utils.collections.Collections3;
import com.youyun.common.utils.mapper.JsonMapper;
import com.youyun.common.web.springmvc.BaseController;
import com.youyun.modules.sys.entity.Organ;
import com.youyun.modules.sys.entity.Post;
import com.youyun.modules.sys.entity.User;
import com.youyun.modules.sys.service.OrganManager;
import com.youyun.modules.sys.service.PostManager;
import com.youyun.modules.sys.service.UserManager;
import com.youyun.utils.SelectType;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位管理 Controller
 * @author : 温春平 wencp@jx.tobacco.gov.cn
 * @date 2014-10-09 14:07
 */
@Controller
@RequestMapping(value = "/sys/post")
public class PostController
        extends BaseController<Post,Long> {

    @Autowired
    private PostManager postManager;
    @Autowired
    private OrganManager organManager;
    @Autowired
    private UserManager userManager;

    @Override
    public EntityManager<Post, Long> getEntityManager() {
        return postManager;
    }

    @RequestMapping(value = {""})
    public String list() {
        return "modules/sys/post";
    }

    /**
     * @param post
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"input"})
    public String input(@ModelAttribute("model") Post post) throws Exception {
        return "modules/sys/post-input";
    }

    @RequestMapping(value = {"_save"})
    @ResponseBody
    public Result save(@ModelAttribute("model")Post model,Long organId) {
        Result result;

        Validate.notNull(organId, "参数[organId]不能为null");
        // 名称重复校验
        Post nameCheckPost = postManager.getPostByON(organId,model.getName());
        if (nameCheckPost != null && !nameCheckPost.getId().equals(model.getId())) {
            result = new Result(Result.WARN, "名称为[" + model.getName() + "]已存在,请修正!", "name");
            logger.debug(result.toString());
            return result;
        }

        // 编码重复校验
        if (StringUtils.isNotBlank(model.getCode())) {
            Post checkPost = postManager.getPostByOC(organId, model.getCode());
            if (checkPost != null && !checkPost.getId().equals(model.getId())) {
                result = new Result(Result.WARN, "编码为[" + model.getCode() + "]已存在,请修正!", "code");
                logger.debug(result.toString());
                return result;
            }
        }

        //设置岗位部门信息
        Organ organ = organManager.loadById(organId);
        model.setOrgan(organ);

        getEntityManager().saveEntity(model);
        result = Result.successResult();
        return result;
    }



    /**
     * 设置岗位用户页面.
     */
    @RequestMapping(value = {"user"})
    public String user(@ModelAttribute("model")Post post,Model model) throws Exception {
        model.addAttribute("organId", post.getOrganId());
        return "modules/sys/post-user";
    }

    /**
     * 修改岗位用户.
     */
    @RequestMapping(value = {"updatePostUser"})
    @ResponseBody
    public Result updatePostUser(@ModelAttribute("model") Post model, @RequestParam(value = "userIds", required = false)List<Long> userIds) throws Exception {
        Result result;
        List<User> us = Lists.newArrayList();
        if(Collections3.isNotEmpty(userIds)){
            for (Long id : userIds) {
                User user = userManager.loadById(id);
                us.add(user);
            }
        }

        model.setUsers(us);
        getEntityManager().saveEntity(model);
        result = Result.successResult();
        return result;
    }


    /**
     * 岗位所在部门下的人员信息
     * @param postId
     * @return
     */
    @RequestMapping(value = {"postOrganUsers/{postId}"})
    @ResponseBody
    public Datagrid<User> postOrganUsers(@PathVariable Long postId) {
        List<User> users = postManager.getPostOrganUsersByPostId(postId);
        Datagrid<User> dg;
        if(Collections3.isEmpty(users)){
           dg = new Datagrid(0, Lists.newArrayList());
        }else{
           dg = new Datagrid<User>(users.size(), users);
        }
        return dg;
    }

    /**
     * 用户可选岗位列表
     * @param selectType {@link SelectType}
     * @param userId 用户ID
     * @return
     */
    @RequestMapping(value = {"combobox"})
    @ResponseBody
    public List<Combobox> combobox(String selectType,Long userId){
        List<Post> list = postManager.getSelectablePostsByUserId(userId);
        List<Combobox> cList = Lists.newArrayList();

        //为combobox添加  "---全部---"、"---请选择---"
        if (!StringUtils.isBlank(selectType)) {
            SelectType s = SelectType.getSelectTypeValue(selectType);
            if (s != null) {
                Combobox selectCombobox = new Combobox("", s.getDescription());
                cList.add(selectCombobox);
            }
        }
        for (Post r : list) {
            Combobox combobox = new Combobox(r.getId() + "", r.getName());
            cList.add(combobox);
        }
        return cList;
    }
}
