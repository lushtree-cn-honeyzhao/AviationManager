/**
 *  Copyright (c) 2014 http://www.lushapp.wang
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youyun.modules.sys.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.youyun.common.exception.DaoException;
import com.youyun.common.exception.ServiceException;
import com.youyun.common.exception.SystemException;
import com.youyun.common.model.Menu;
import com.youyun.common.model.TreeNode;
import com.youyun.common.orm.entity.StatusState;
import com.youyun.common.orm.hibernate.EntityManager;
import com.youyun.common.orm.hibernate.HibernateDao;
import com.youyun.common.utils.StringUtils;
import com.youyun.common.utils.collections.Collections3;
import com.youyun.modules.sys._enum.ResourceType;
import com.youyun.modules.sys.entity.Resource;
import com.youyun.modules.sys.entity.User;
import com.youyun.utils.CacheConstants;

import org.apache.commons.collections.ListUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 资源Resource管理 Service层实现类.
 * <br>树形资源使用缓存 当保存、删除操作时清除缓存
 * @author honey.zhao@aliyun.com  
 * @date 2014-10-11 下午4:26:46
 */
@Service
public class ResourceManager extends EntityManager<Resource, Long> {


    @Autowired
	private UserManager userManager;

	private HibernateDao<Resource, Long> resourceDao;// 默认的泛型DAO成员变量.

	/**
	 * 通过注入的sessionFactory初始化默认的泛型DAO成员变量.
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		resourceDao = new HibernateDao<Resource, Long>(sessionFactory, Resource.class);
	}

	@Override
	protected HibernateDao<Resource, Long> getEntityDao() {
		return resourceDao;
	}

	/**
	 * 保存或修改.
	 */
	//清除缓存
	@CacheEvict(value = { CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE,
            CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE,
            CacheConstants.RESOURCE_USER_MENU_TREE_CACHE},allEntries = true)
	public void saveOrUpdate(Resource entity) throws DaoException, SystemException,
			ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE
                +","+CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE
                +","+CacheConstants.RESOURCE_USER_MENU_TREE_CACHE);
		Assert.notNull(entity, "参数[entity]为空!");
		resourceDao.saveOrUpdate(entity);
	}

	/**
	 * 保存或修改.
	 */
	//清除缓存
    @CacheEvict(value = { CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE,
            CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE,
            CacheConstants.RESOURCE_USER_MENU_TREE_CACHE},allEntries = true)
	public void merge(Resource entity) throws DaoException, SystemException,
			ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE
                +","+CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE
                +","+CacheConstants.RESOURCE_USER_MENU_TREE_CACHE);
		Assert.notNull(entity, "参数[entity]为空!");
		resourceDao.merge(entity);
	}

    @CacheEvict(value = { CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE,
            CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE,
            CacheConstants.RESOURCE_USER_MENU_TREE_CACHE},allEntries = true)
    @Override
    public void saveEntity(Resource entity) throws DaoException, SystemException, ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE
                +","+CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE
                +","+CacheConstants.RESOURCE_USER_MENU_TREE_CACHE);
        super.saveEntity(entity);
    }

    /**
     * 自定义保存资源.
     * <br/>说明：如果保存的资源类型为“功能” 则将所有子资源都设置为“功能”类型
     * @param entity 资源对象
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    @CacheEvict(value = { CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE,
            CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE,
            CacheConstants.RESOURCE_USER_MENU_TREE_CACHE},allEntries = true)
    public void saveResource(Resource entity) throws DaoException, SystemException, ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE
                +","+CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE
                +","+CacheConstants.RESOURCE_USER_MENU_TREE_CACHE);
        Assert.notNull(entity,"参数[entity]为空!");
        this.saveEntity(entity);
        if(entity.getType() !=null && ResourceType.function.getValue().equals(entity.getType())){
            List<Resource> subResources = entity.getSubResources();
            while (!Collections3.isEmpty(subResources)){
                Iterator<Resource> iterator = subResources.iterator();
                while(iterator.hasNext()){
                     Resource subResource = iterator.next();
                     subResource.setType(ResourceType.function.getValue());
                     iterator.remove();
                     subResources = ListUtils.union(subResources,subResource.getSubResources());
                     super.update(subResource);
                 }
            }
        }
    }


    /**
	 * 自定义删除方法.
	 */
	//清除缓存
    @CacheEvict(value = { CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE,
            CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE,
            CacheConstants.RESOURCE_USER_MENU_TREE_CACHE},allEntries = true)
	public void deleteByIds(List<Long> ids) throws DaoException, SystemException,
			ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE
                +","+CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE
                +","+CacheConstants.RESOURCE_USER_MENU_TREE_CACHE);
        if(!Collections3.isEmpty(ids)){
            for(Long id :ids){
                Resource resource = getEntityDao().load(id);
                resource.setRoles(null);
                resource.setUsers(null);
                getEntityDao().delete(resource);
            }
        }else{
            logger.warn("参数[ids]为空.");
        }

	}

    /**
     * 根据资源编码获取对象
     * @param resourceCode 资源编码
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    public Resource getResourceByCode(String resourceCode) throws DaoException, SystemException,
            ServiceException {
        return getEntityDao().findUniqueBy("code",resourceCode);
    }

    /**
     * 检查用户是否具有某个资源编码的权限
     * @param userId 用户ID
     * @param resourceCode 资源编码
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    public boolean isUserPermittedResourceCode(Long userId, String resourceCode) throws DaoException, SystemException,
            ServiceException {
        Assert.notNull(userId, "参数[userId]为空!");
        Assert.notNull(resourceCode, "参数[resourceCode]为空!");
        List<Resource> list = this.getResourcesByUserId(userId);
        boolean flag = false;
        for (Resource resource : list) {
            if (resource != null && StringUtils.isNotBlank(resource.getCode()) && resource.getCode().equalsIgnoreCase(resourceCode)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 用户导航菜单(排除非菜单资源).
     * @param userId 用户ID
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    @Cacheable(value = { CacheConstants.RESOURCE_USER_MENU_TREE_CACHE},key = "#userId +'getNavMenuTreeByUserId'")
    public List<TreeNode> getNavMenuTreeByUserId(Long userId) throws DaoException,
            SystemException, ServiceException {
        List<TreeNode> nodes = Lists.newArrayList();
        List<Resource> userResources = Lists.newArrayList();
        User user = userManager.loadById(userId);
        User superUser = userManager.getSuperUser();
        boolean isSuperUser = false; //是否是超级管理员
        if (user != null && superUser != null
                && user.getId().equals(superUser.getId())) {// 超级用户
            isSuperUser = true;
            userResources = this.getByParentId(null,StatusState.normal.getValue());
        } else if (user != null) {
           userResources = this.getResourcesByUserId(userId);
        }
        for(Resource resource:userResources){
            if(isSuperUser){
                TreeNode node =  this.resourceToTreeNode(resource, ResourceType.menu.getValue(),true);
                if(node !=null){
                    nodes.add(node);
                }
            } else{
                if(resource != null && resource.getParentResource() == null){
                    TreeNode node =  this.resourceToTreeNode(userResources,resource, ResourceType.menu.getValue(),true);
                    if(node !=null){
                        nodes.add(node);
                    }
                }
            }
        }

        logger.debug("缓存:{}", CacheConstants.RESOURCE_USER_MENU_TREE_CACHE +" 参数：userId="+userId);
        return nodes;
    }

    public List<Resource> getResourcesByUserId(Long userId) throws DaoException,
            SystemException, ServiceException {
        Assert.notNull(userId, "userId不能为空");
        //角色权限
        List<Resource> roleResources = resourceDao.distinct(resourceDao.createQuery("select ms from User u left join u.roles rs left join rs.resources ms where u.id= ? order by ms.orderNo asc", userId)).list();
        //用户直接权限
        User user = userManager.loadById(userId);
        List<Resource> userResources = user.getResources();
        //去除空对象
        Iterator<Resource> roleIterator  = roleResources.iterator();
        while (roleIterator.hasNext()){
            Resource roleResource = roleIterator.next();
            if(roleResource == null){
                roleIterator.remove();
            }
        }
        List<Resource> rs = Collections3.aggregate(roleResources,userResources);
        Collections.sort(rs, new Comparator<Resource>() {
           
            public int compare(Resource o1, Resource o2) {
                if (o1.getOrderNo() != null && o2.getOrderNo() != null) {
                    return o1.getOrderNo().compareTo(o2.getOrderNo());
                }
                return 0;
            }
        });
        return rs;
    }

    public List<Resource> getResourcesByUserId(Long userId, Resource parentResource) throws DaoException,
            SystemException, ServiceException {
        List<Resource> list = new ArrayList<Resource>();
        List<Resource> resources =   this.getResourcesByUserId(userId);
        if (null == parentResource){
               for(Resource resource:resources){
                   if(resource != null && resource.getParentResource() == null
                           && StatusState.normal.getValue().equals(resource.getStatus())){
                       list.add(resource);
                   }
               }
        }else{
            for(Resource resource:resources){
                if(resource != null && resource.getParentResource() != null && resource.getParentResource().getId().equals(parentResource.getId())
                        && StatusState.normal.getValue().equals(resource.getStatus())){
                    list.add(resource);
                }
            }
        }
        return list;
    }

    /**
     * 根据用户ID得到导航栏资源（权限控制）.
     * @param userId 用户ID
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    //使用缓存
   @Cacheable(value = { CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE},key = "#userId +'getResourceTreeByUserId'")
    public List<TreeNode> getResourceTreeByUserId(Long userId) throws DaoException,
            SystemException, ServiceException {
        // Assert.notNull(userId, "参数[userId]为空!");
        List<TreeNode> nodes = Lists.newArrayList();
        List<Resource> userResources = Lists.newArrayList();
        User user = userManager.loadById(userId);
        User superUser = userManager.getSuperUser();
        boolean isSuperUser = false; //是否是超级管理员
        if (user != null && superUser != null
                && user.getId().equals(superUser.getId())) {// 超级用户
            userResources = this.getByParentId(null,StatusState.normal.getValue());
        } else if (user != null) {
            userResources = this.getResourcesByUserId(userId);
        }
        for(Resource resource:userResources){
            if(isSuperUser){
                TreeNode node =  this.resourceToTreeNode(resource, null,true);
                if(node !=null){
                    nodes.add(node);
                }
            } else{
                TreeNode node =  this.resourceToTreeNode(userResources,resource, null,true);
                if(node !=null){
                    nodes.add(node);
                }
            }

        }

        logger.debug("缓存:{}", CacheConstants.RESOURCE_USER_RESOURCE_TREE_CACHE + " 参数：userId=" + userId);
        return nodes;
    }

    /**
     * Resource转TreeNode
     * @param resource 资源
     * @param resourceType 资源类型
     * @param isCascade       是否级联
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    private TreeNode resourceToTreeNode(Resource resource,Integer resourceType,boolean isCascade) throws DaoException, SystemException,
            ServiceException {
        if(resourceType!=null){
            if(!resourceType.equals(resource.getType())){
                return null;
            }
        }
        TreeNode treeNode = new TreeNode(resource.getId().toString(),
                resource.getName(), resource.getIconCls());
        // 自定义属性 url
        Map<String, Object> attributes = Maps.newHashMap();
        attributes.put("url", resource.getUrl());
        attributes.put("markUrl", resource.getMarkUrl());
        attributes.put("code", resource.getCode());
        attributes.put("type", resource.getType());
        treeNode.setAttributes(attributes);
        if(isCascade){
            List<TreeNode> childrenTreeNodes = Lists.newArrayList();
            for(Resource subResource:resource.getSubResources()){
                TreeNode node = resourceToTreeNode(subResource,resourceType,isCascade);
                if(node !=null){
                    childrenTreeNodes.add(node);
                }
            }
            treeNode.setChildren(childrenTreeNodes);
        }

        return treeNode;
    }

    /**
     * Resource转TreeNode
     * @param repositoryResources 资源库
     * @param resource 资源
     * @param resourceType 资源类型
     * @param isCascade       是否级联
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    private TreeNode resourceToTreeNode(List<Resource> repositoryResources,Resource resource,Integer resourceType,boolean isCascade) throws DaoException, SystemException,
            ServiceException {
        if(resource==null || !repositoryResources.contains(resource)){
            return null;
        }
        if(resourceType!=null){
            if(!resourceType.equals(resource.getType())){
                return null;
            }
        }
        TreeNode treeNode = new TreeNode(resource.getId().toString(),
                resource.getName(), resource.getIconCls());
        // 自定义属性 url
        Map<String, Object> attributes = Maps.newHashMap();
        attributes.put("url", resource.getUrl());
        attributes.put("markUrl", resource.getMarkUrl());
        attributes.put("code", resource.getCode());
        attributes.put("type", resource.getType());
        treeNode.setAttributes(attributes);
        if(isCascade){
            List<TreeNode> childrenTreeNodes = Lists.newArrayList();
            for(Resource subResource:resource.getSubResources()){
                TreeNode node = resourceToTreeNode(repositoryResources,subResource,resourceType,isCascade);
                if(node !=null){
                    childrenTreeNodes.add(node);
                }
            }
            treeNode.setChildren(childrenTreeNodes);
        }

        return treeNode;
    }


    /**
     * Resource转Easy UI Menu
     * @param resource
     * @param isCascade 是否递归遍历子节点
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    public Menu resourceToMenu(Resource resource,boolean isCascade) throws DaoException, SystemException,
            ServiceException {
        Assert.notNull(resource,"参数resource不能为空");
        if(ResourceType.menu.getValue().equals(resource.getType())){
            Menu menu = new Menu();
            menu.setId(resource.getId().toString());
            menu.setText(resource.getName());
            menu.setHref(resource.getUrl());
            if(isCascade){
                List<Menu> childrenMenus = Lists.newArrayList();
                for(Resource subResource:resource.getSubResources()){
                    if(ResourceType.menu.getValue().equals(subResource.getType())){
                        childrenMenus.add(resourceToMenu(subResource,true));
                    }
                }
                menu.setChildren(childrenMenus);
            }
            return menu;
        }
        return null;
    }


    public List<Menu> getAppMenusByUserId(Long userId){
        List<Menu> menus = Lists.newArrayList();
        List<Resource> resources = Lists.newArrayList();
        User user = userManager.loadById(userId);
        User superUser = userManager.getSuperUser();
        if (user != null && superUser != null
                && user.getId().equals(superUser.getId())) {// 超级用户
            resources = super.getAll();
        } else if (user != null) {
            resources = getResourcesByUserId(userId);
        }
        for(Resource resource:resources){
            if(StringUtils.isNotBlank(resource.getUrl())){
                if(ResourceType.menu.getValue().equals(resource.getType())) {
                    Menu menu = new Menu();
                    menu.setId(resource.getId().toString());
                    menu.setText(resource.getName());
                    menu.setHref(resource.getUrl());
                    menus.add(menu);
                }
            }

        }
        return menus;
    }
    /**
     * 得到开始菜单.
     * @param userId 用户ID
     */
    public List<Menu> getMenusByUserId(Long userId){
        List<Menu> menus = Lists.newArrayList();
        List<Resource> rootResources = Lists.newArrayList();
        User user = userManager.loadById(userId);
        User superUser = userManager.getSuperUser();
        if (user != null && superUser != null
                && user.getId().equals(superUser.getId())) {// 超级用户
           rootResources = getByParentId(null,StatusState.normal.getValue());
        } else if (user != null) {
            rootResources = getResourcesByUserId(userId,null);
            //去除非菜单资源
            Iterator<Resource> iterator = rootResources.iterator();
            while (iterator.hasNext()){
                if(!ResourceType.menu.getValue().equals(iterator.next().getType())) {
                    iterator.remove();
                }
            }
        }
        for(Resource parentResource:rootResources){
            Menu menu = resourceToMenu(parentResource,true);
            if(menu!=null){
                menus.add(menu);
            }
        }
        return menus;
    }

    /**
     *
     * @param entity
     * @param id
     * @param isCascade 是否级联
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    public TreeNode getTreeNode(Resource entity, Long id, boolean isCascade)
            throws DaoException, SystemException, ServiceException {
        TreeNode node = this.resourceToTreeNode(entity,null,true);
        List<Resource> subResources = this.getByParentId(entity.getId(),StatusState.normal.getValue());
        if (subResources.size() > 0) {
            if (isCascade) {// 递归查询子节点
                List<TreeNode> children = Lists.newArrayList();
                for (Resource d : subResources) {
                    boolean isInclude = true;// 是否包含到节点树
                    TreeNode treeNode = null;
                    treeNode = getTreeNode(d, id, true);
                    // 排除自身
                    if (id != null) {
                        if (!d.getId().equals(id)) {
                            treeNode = getTreeNode(d, id, true);
                        } else {
                            isInclude = false;
                        }
                    } else {
                        treeNode = getTreeNode(d, id, true);
                    }
                    if (isInclude) {
                        children.add(treeNode);
                        node.setState(TreeNode.STATE_CLOASED);
                    } else {
                        node.setState(TreeNode.STATE_OPEN);
                    }
                }

                node.setChildren(children);
            }
        }
        return node;
    }

    /**
     * 获取所有导航资源（无权限限制）.
     * @param excludeResourceId 需要排除的资源ID 子级也会被排除
     * @param isCascade 是否级联
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    public List<TreeNode> getResourceTree(Long excludeResourceId,boolean isCascade) throws DaoException, SystemException,
            ServiceException {
        List<TreeNode> treeNodes = Lists.newArrayList();
        // 顶级资源
        List<Resource> resources = getByParentId(null,
                StatusState.normal.getValue());
        for (Resource rs:resources) {
            TreeNode rootNode = getTreeNode(rs, excludeResourceId, isCascade);
            treeNodes.add(rootNode);
        }
        return treeNodes;

    }



    /**
	 *
	 * 根据name得到Resource.
	 *
	 * @param name
	 *            资源名称
	 * @return
	 * @throws com.youyun.common.exception.DaoException
	 * @throws com.youyun.common.exception.SystemException
	 * @throws com.youyun.common.exception.ServiceException
	 */
	public Resource getByName(String name) throws DaoException, SystemException,
			ServiceException {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		name = StringUtils.strip(name);// 去除两边空格
		return resourceDao.findUniqueBy("name", name);
	}


    /**
     *
     * 根据编码得到Resource.
     *
     * @param code
     *            资源编码
     * @return
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    public Resource getByCode(String code) throws DaoException, SystemException,
            ServiceException {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return resourceDao.findUniqueBy("code", code);
    }

	/**
	 *
	 * 根据父ID得到 Resource. <br>
	 * 默认按 orderNo asc,id asc排序.
	 *
	 * @param parentId
	 *            父节点ID(当该参数为null的时候查询顶级资源列表)
	 * @param status
	 *            数据状态 @see com.youyun.common.orm.entity.StatusState
	 *            <br>status传null则使用默认值 默认值:StatusState.normal.getValue()
	 * @return
	 * @throws com.youyun.common.exception.DaoException
	 * @throws com.youyun.common.exception.SystemException
	 * @throws com.youyun.common.exception.ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> getByParentId(Long parentId, Integer status)
			throws DaoException, SystemException, ServiceException {
		//默认值 正常
		if(status == null){
			status = StatusState.normal.getValue();
		}
		StringBuilder sb = new StringBuilder();
		Object[] objs;
		sb.append("from Resource r where r.status = ?  ");
        sb.append(" and r.parentResource.id ");
        if (parentId == null) {
			sb.append(" is null ");
			objs = new Object[] { status };
		} else {
			sb.append(" = ? ");
			objs = new Object[] { status, parentId };
		}
		sb.append(" order by r.orderNo asc,r.id asc");

		List<Resource> list = resourceDao.createQuery(sb.toString(), objs).list();
		return list;
	}



    /**
     * 根据请求地址判断用户是否有权访问该url
     * @param requestUrl 请求URL地址
     * @param userId 用户ID
     * @return
     */
    @Cacheable(value = {CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE},key = "#requestUrl + #userId +'isAuthority'")
    public boolean isAuthority(String requestUrl,Long userId)
            throws DaoException,SystemException,ServiceException{
        //如果是超级管理员 直接允许被授权
        if(userManager.getSuperUser().getId().equals(userId)) {
            return true;
        }
        //检查该URL是否需要拦截
        boolean isInterceptorUrl = this.isInterceptorUrl(requestUrl);
        if (isInterceptorUrl){
            //用户权限Lo
            List<String> userAuthoritys = this.getUserAuthoritysByUserId(userId);
            for(String markUrl :userAuthoritys){
                String[] markUrls = markUrl.split(";");
                for(int i=0;i<markUrls.length;i++){
                    if(StringUtils.isNotBlank(markUrls[i]) && StringUtils.simpleWildcardMatch(markUrls[i],requestUrl)){
                        return true;
                    }
                }
            }
            return false;
        }
        logger.debug("缓存:{}", CacheConstants.RESOURCE_USER_AUTHORITY_URLS_CACHE +"参数：requestUrl="+requestUrl+",userId="+userId);
        return true;
    }


    /**
     * 查找需要拦截的所有url规则
     * @return
     */
    public List<String> getAllInterceptorUrls()
            throws DaoException,SystemException,ServiceException{
        List<String> markUrls = Lists.newArrayList();
        //查找所有资源
//        List<Resource> resources = this.findBy("NEI_status",StatusState.delete.getValue());
        List<Resource> resources = this.getAll();
        for(Resource resource : resources){
            if(StringUtils.isNotBlank(resource.getMarkUrl())){
                markUrls.add(resource.getMarkUrl());
            }
        }
        return markUrls;
    }

    /**
     * 检查某个URL是都需要拦截
     * @param requestUrl 检查的URL地址
     * @return
     */
    public boolean isInterceptorUrl(String requestUrl)
            throws DaoException,SystemException,ServiceException{
        List<String> markUrlList = this.getAllInterceptorUrls();
        for(String markUrl :markUrlList){
            String[] markUrls = markUrl.split(";");
            for(int i=0;i<markUrls.length;i++){
                if(StringUtils.isNotBlank(markUrls[i]) && StringUtils.simpleWildcardMatch(markUrls[i],requestUrl)){
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 根据用户ID查找用户拥有的URL权限
     * @param userId   用户ID
     * @return    List<String> 用户拥有的markUrl地址
     */
    public List<String> getUserAuthoritysByUserId(Long userId)
            throws DaoException,SystemException,ServiceException{
        List<String> userAuthoritys = Lists.newArrayList();
        List<TreeNode> treeNodes = this.getResourceTreeByUserId(userId);
        for(TreeNode node : treeNodes){
            Object obj = node.getAttributes().get("markUrl");
            if(obj != null){
                String markUrl = (String)obj ;
                if(StringUtils.isNotBlank(markUrl)){
                    userAuthoritys.add(markUrl);
                }
            }
            //二级目录
            List<TreeNode> childrenNodes =  node.getChildren();
            for(TreeNode childrenNode : childrenNodes){
                Object childrenObj = childrenNode.getAttributes().get("markUrl");
                if(childrenObj != null){
                    String markUrl = (String)childrenObj ;
                    if(StringUtils.isNotBlank(markUrl)){
                        userAuthoritys.add(markUrl);
                    }
                }
            }
        }
        return  userAuthoritys;
    }


    /**
	 * 得到排序字段的最大值.
	 * 
	 * @return 返回排序字段的最大值
	 */
	public Integer getMaxSort() throws DaoException, SystemException,
			ServiceException {
		Iterator<?> iterator = resourceDao.createQuery(
				"select max(m.orderNo)from Resource m ").iterate();
		Integer max = 0;
		while (iterator.hasNext()) {
			// Object[] row = (Object[]) iterator.next();
			max = (Integer) iterator.next();
			if (max == null) {
				max = 0;
			}
		}
		return max;
	}
}
