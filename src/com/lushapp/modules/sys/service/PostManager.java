/**
 *  Copyright (c) 2014 http://www.lushapplicatioin.com
 *
 *                    
 */
package com.lushapp.modules.sys.service;

import com.lushapp.common.orm.entity.StatusState;
import com.lushapp.common.orm.hibernate.EntityManager;
import com.lushapp.common.orm.hibernate.HibernateDao;
import com.lushapp.common.utils.collections.Collections3;
import com.lushapp.modules.sys.entity.Post;
import com.lushapp.modules.sys.entity.User;

import org.apache.commons.lang3.Validate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位管理 Service
 * @author : 温春平 wencp@jx.tobacco.gov.cn
 * @date 2014-10-09 14:07
 */
@Service
public class PostManager extends
        EntityManager<Post, Long> {

    private HibernateDao<Post, Long> postDao;

    @Autowired
    private UserManager userManager;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        postDao = new HibernateDao<Post, Long>(
                sessionFactory, Post.class);
    }

    @Override
    protected HibernateDao<Post, Long> getEntityDao() {
        return postDao;
    }

    /**
     * 根据机构ID以及岗位名称查找
     * @param organId 机构ID
     * @param postName 岗位名称
     */
    public Post getPostByON(Long organId,String postName){
        Validate.notNull(organId, "参数[organId]不能为null");
        Validate.notNull(postName, "参数[postName]不能为null或空");
        List<Post> list = getEntityDao().createQuery("from Post p where p.organ.id = ? and p.name = ?",new Object[]{organId,postName}).list();
        return list.isEmpty() ? null:list.get(0);
    }


    /**
     * 根据机构ID以及岗位编码查找
     * @param organId 机构ID
     * @param postCode 岗位编码
     */
    public Post getPostByOC(Long organId,String postCode){
        Validate.notNull(organId, "参数[organId]不能为null");
        Validate.notNull(postCode, "参数[postCode]不能为null或空");
        List<Post> list = getEntityDao().createQuery("from Post p where p.organ.id = ? and p.name = ?",new Object[]{organId,postCode}).list();
        return list.isEmpty() ? null:list.get(0);
    }


    /**
     * 得到岗位所在部门的所有用户
     * @param postId 岗位ID
     * @return
     */
    public List<User> getPostOrganUsersByPostId(Long postId){
        Validate.notNull(postId, "参数[postId]不能为null");
        Post post = super.loadById(postId);
        if(post == null){
            return null;
        }
        return post.getOrgan().getUsers();
    }


    /**
     * 用户可选岗位列表
     * @param userId 用户ID 如果用户为null 则返回所有
     * @return
     */
    public List<Post> getSelectablePostsByUserId(Long userId) {
        List<Post> list = null;
        if (userId != null) {
            User user = userManager.loadById(userId);
            List<Long> userOrganIds = user.getOrganIds();
            StringBuffer hql = new StringBuffer();
            hql.append("from Post p where p.status = ? ");
            if(Collections3.isNotEmpty(userOrganIds)){
                hql.append(" and  p.organ.id in (:userOrganIds)");
            }else{
                logger.warn("用户[{}]未设置部门.",new Object[]{user.getLoginName()});
            }

            Query query = getEntityDao().createQuery(hql.toString(),
                    new Object[]{StatusState.normal.getValue()});
            if(Collections3.isNotEmpty(userOrganIds)){
                query.setParameterList("userOrganIds", userOrganIds);
            }
            list = query.list();
        } else {
            list = getEntityDao().createQuery("from Post p where p.status = ? ",
                    new Object[]{StatusState.normal.getValue()}).list();
        }
        return list;
    }
}