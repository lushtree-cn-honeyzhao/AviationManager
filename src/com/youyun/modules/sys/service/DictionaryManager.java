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
import com.youyun.common.model.Combobox;
import com.youyun.common.model.TreeNode;
import com.youyun.common.orm.entity.StatusState;
import com.youyun.common.orm.hibernate.EntityManager;
import com.youyun.common.orm.hibernate.HibernateDao;
import com.youyun.common.utils.StringUtils;
import com.youyun.modules.sys.entity.Dictionary;
import com.youyun.modules.sys.entity.DictionaryType;
import com.youyun.utils.CacheConstants;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据字典实现类.
 * 
 * @author : honey.zhao@aliyun.com  
 * @date 2014-10-24 下午3:01:27
 */
@Service
public class DictionaryManager extends EntityManager<Dictionary, Long> {

	private HibernateDao<Dictionary, Long> dictionaryDao;
    @Autowired
    private DictionaryTypeManager dictionaryTypeManager;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		dictionaryDao = new HibernateDao<Dictionary, Long>(sessionFactory,
				Dictionary.class);
	}

	@Override
	protected HibernateDao<Dictionary, Long> getEntityDao() {
		return dictionaryDao;
	}

	/**
	 * 新增或修改 保存对象.
	 */
    @CacheEvict(value = { CacheConstants.DICTIONARYS_BY_TYPE_CACHE,
            CacheConstants.DICTIONARYS_CONBOTREE_BY_TYPE_CACHE,
            CacheConstants.DICTIONARYS_CONBOBOX_BY_TYPE_CACHE},allEntries = true)
	public void saveOrUpdate(Dictionary entity) throws DaoException, SystemException,
			ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.DICTIONARYS_BY_TYPE_CACHE
                +","+CacheConstants.DICTIONARYS_CONBOTREE_BY_TYPE_CACHE
                +","+CacheConstants.DICTIONARYS_CONBOBOX_BY_TYPE_CACHE);
        Assert.notNull(entity, "参数[entity]为空!");
		dictionaryDao.saveOrUpdate(entity);
	}
	
	/**
	 * 新增或修改 保存对象.
	 */
    @CacheEvict(value = { CacheConstants.DICTIONARYS_BY_TYPE_CACHE,
            CacheConstants.DICTIONARYS_CONBOTREE_BY_TYPE_CACHE,
            CacheConstants.DICTIONARYS_CONBOBOX_BY_TYPE_CACHE},allEntries = true)
	public void merge(Dictionary entity) throws DaoException, SystemException,
			ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.DICTIONARYS_BY_TYPE_CACHE
                +","+CacheConstants.DICTIONARYS_CONBOTREE_BY_TYPE_CACHE
                +","+CacheConstants.DICTIONARYS_CONBOBOX_BY_TYPE_CACHE);
        Assert.notNull(entity, "参数[entity]为空!");
		dictionaryDao.merge(entity);
	}

    @CacheEvict(value = { CacheConstants.DICTIONARYS_BY_TYPE_CACHE,
            CacheConstants.DICTIONARYS_CONBOTREE_BY_TYPE_CACHE,
            CacheConstants.DICTIONARYS_CONBOBOX_BY_TYPE_CACHE},allEntries = true)
    @Override
    public void saveEntity(Dictionary entity) throws DaoException, SystemException, ServiceException {
        logger.debug("清空缓存:{}", CacheConstants.DICTIONARYS_BY_TYPE_CACHE
                +","+CacheConstants.DICTIONARYS_CONBOTREE_BY_TYPE_CACHE
                +","+CacheConstants.DICTIONARYS_CONBOBOX_BY_TYPE_CACHE);
        super.saveEntity(entity);
    }

	/**
	 * 根据编码code得到对象.
	 * 
	 * @param code
	 *            数据字典编码
	 * @return
	 * @throws com.youyun.common.exception.DaoException
	 * @throws com.youyun.common.exception.SystemException
	 * @throws com.youyun.common.exception.ServiceException
	 */
	@SuppressWarnings("unchecked")
	public Dictionary getByCode(String code) throws DaoException,
			SystemException, ServiceException {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		code = StringUtils.strip(code);// 去除两边空格
		List<Dictionary> list = dictionaryDao.createQuery(
				"from Dictionary d where d.code = ?", new Object[] { code })
				.list();
		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * 根据数据字典类型编码dictionaryTypeCode得到List<TreeNode>对象. <br>
	 * 当id不为空的时候根据id排除自身节点.
	 *
	 * @param entity
	 *            数据字典对象
	 * @param id
	 *            数据字典ID
	 * @param isCascade
	 *            是否级联加载
	 * @return List<TreeNode> 映射关系： TreeNode.text-->Dicitonary.text;TreeNode.id-->Dicitonary.code;
	 * @throws com.youyun.common.exception.DaoException
	 * @throws com.youyun.common.exception.SystemException
	 * @throws com.youyun.common.exception.ServiceException
	 */
    @Cacheable(value = { CacheConstants.DICTIONARYS_CONBOTREE_BY_TYPE_CACHE} )
	@SuppressWarnings("unchecked")
	public List<TreeNode> getByDictionaryTypeCode(Dictionary entity,
			String dictionaryTypeCode, Long id, boolean isCascade)
			throws DaoException, SystemException, ServiceException {
        Assert.notNull(entity, "参数[entity]为空!");
        List<Dictionary> list = Lists.newArrayList();
		List<TreeNode> treeNodes = Lists.newArrayList();
		if (StringUtils.isBlank(dictionaryTypeCode)) {
			return treeNodes;
		}
		StringBuilder sb = new StringBuilder();
		Object[] objs;
		sb.append("from Dictionary d where d.status = ? and d.dictionaryType.code = ? ");
		if (StringUtils.isBlank(entity.getCode())) {
			sb.append(" and d.parentDictionary is null ");
			objs = new Object[] { StatusState.normal.getValue(),
					dictionaryTypeCode };
		} else {
			sb.append(" and d.parentDictionary.code = ? ");
			objs = new Object[] { StatusState.normal.getValue(),
					dictionaryTypeCode, entity.getCode() };
		}
		sb.append(" order by d.id asc");
		logger.debug(sb.toString());
		list = dictionaryDao.createQuery(sb.toString(), objs).list();
		for (Dictionary d : list) {
			// 排除自身
			if (!d.getId().equals(entity.getId())) {
				TreeNode t = getTreeNode(d, id, isCascade);
				if (t != null) {
					treeNodes.add(t);
				}
			}

		}
        logger.debug("缓存:{}", CacheConstants.DICTIONARYS_CONBOTREE_BY_TYPE_CACHE);
		return treeNodes;
	}

	/**
	 * /** 根据数据字典类型编码dictionaryTypeCode得到List<TreeNode>对象. <br>
	 * 当id不为空的时候根据id排除自身节点.
	 *
	 * @param entity
	 *            数据字典对象
	 * @param id
	 *            数据字ID
	 * @param isCascade
	 *            是否级联加载
	 * @return
	 * @throws com.youyun.common.exception.DaoException
	 * @throws com.youyun.common.exception.SystemException
	 * @throws com.youyun.common.exception.ServiceException
	 */
	public TreeNode getTreeNode(Dictionary entity, Long id, boolean isCascade)
			throws DaoException, SystemException, ServiceException {
		TreeNode node = new TreeNode(entity.getCode(), entity.getName());
//        node.getAttributes().put("code",entity.getCode());
		// Map<String, Object> attributes = new HashMap<String, Object>();
		// node.setAttributes(attributes);
		List<Dictionary> subDictionaries = getByParentCode(entity.getCode());
		if (subDictionaries.size() > 0) {
			if (isCascade) {// 递归查询子节点
				List<TreeNode> children = Lists.newArrayList();
				for (Dictionary d : subDictionaries) {
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
     * @param dictionaryTypeCode 字典类型编码
     * @param dictionaryCode 字典项编码
     * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
    public Dictionary getDictionaryByDC(
            String dictionaryTypeCode,String dictionaryCode) throws DaoException, SystemException,
            ServiceException {
        Assert.notNull(dictionaryTypeCode, "参数[dictionaryTypeCode]为空!");
        Assert.notNull(dictionaryCode, "参数[dictionaryCode]为空!");
        List<Dictionary> list = dictionaryDao.createQuery(
                "from Dictionary d where d.dictionaryType.code = ? and d.dictionaryCode = ? ",
                new Object[] { dictionaryTypeCode,dictionaryCode}).list();
        return list.isEmpty() ? null:list.get(0);
    }

    /**
     * @param dictionaryTypeCode 字典类型编码
     * @param dictionaryValue 字典项值
     * @return
     * @throws DaoException
     * @throws SystemException
     * @throws ServiceException
     */
    public Dictionary getDictionaryByDV(
            String dictionaryTypeCode,String dictionaryValue) throws DaoException, SystemException,
            ServiceException {
        Assert.notNull(dictionaryTypeCode, "参数[dictionaryTypeCode]为空!");
        Assert.notNull(dictionaryValue, "参数[dictionaryValue]为空!");
        List<Dictionary> list = dictionaryDao.createQuery(
                "from Dictionary d where d.dictionaryType.code = ? and d.value = ? ",
                new Object[] { dictionaryTypeCode,dictionaryValue}).list();
        return list.isEmpty() ? null:list.get(0);
    }

	/**
	 * 根据数据字典类型编码得到数据字典列表.
	 *
	 * @param dictionaryTypeCode 字典分类编码
	 * @return
	 * @throws com.youyun.common.exception.DaoException
	 *             ,SystemException,ServiceException
	 */
    @Cacheable(value = { CacheConstants.DICTIONARYS_BY_TYPE_CACHE})
	@SuppressWarnings("unchecked")
	public List<Dictionary> getDictionarysByDictionaryTypeCode(
			String dictionaryTypeCode) throws DaoException, SystemException,
			ServiceException {
        Assert.notNull(dictionaryTypeCode, "参数[dictionaryTypeCode]为空!");
        List<Dictionary> list = dictionaryDao.createQuery(
				"from Dictionary d where d.dictionaryType.code = ? ",
				new Object[] { dictionaryTypeCode }).list();
        logger.debug("缓存:{}", CacheConstants.DICTIONARYS_BY_TYPE_CACHE+" 参数：dictionaryTypeCode="+dictionaryTypeCode);
        return list;
	}

	/**
	 * Combobox下拉框数据.
	 *
	 * @param dictionaryTypeCode
	 *            数据字典类型编码
	 * @return List<Combobox> 映射关系： Combobox.text-->Dicitonary.text;Combobox.value-->Dicitonary.value;
	 * @throws com.youyun.common.exception.DaoException
	 * @throws com.youyun.common.exception.SystemException
	 * @throws com.youyun.common.exception.ServiceException
	 */
    @Cacheable(value = { CacheConstants.DICTIONARYS_CONBOBOX_BY_TYPE_CACHE})
	public List<Combobox> getByDictionaryTypeCode(String dictionaryTypeCode)
			throws DaoException, SystemException, ServiceException {
		List<Dictionary> list = getDictionarysByDictionaryTypeCode(dictionaryTypeCode);
        List<Combobox> cList = Lists.newArrayList();
        for (Dictionary d : list) {
            Combobox c = new Combobox(d.getValue(), d.getName());
            cList.add(c);
        }
        logger.debug("缓存:{}", CacheConstants.DICTIONARYS_CONBOBOX_BY_TYPE_CACHE+" 参数：dictionaryTypeCode="+dictionaryTypeCode);
        return cList;

	}

	/**
	 * 根据父ID得到list对象.
	 *
	 * @param parentCode
	 *            父级编码
	 * @return
	 * @throws com.youyun.common.exception.DaoException
	 * @throws com.youyun.common.exception.SystemException
	 * @throws com.youyun.common.exception.ServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getByParentCode(String parentCode)
			throws DaoException, SystemException, ServiceException {
		StringBuilder sb = new StringBuilder();
		Object[] objs;
		sb.append("from Dictionary d where d.status = ? ");
		if (parentCode == null) {
			sb.append(" and d.parentDictionary is null ");
			objs = new Object[] { StatusState.normal.getValue() };
		} else {
			sb.append(" and d.parentDictionary.code  = ? ");
			objs = new Object[] { StatusState.normal.getValue(), parentCode };
		}
		sb.append(" order by d.id asc");
		List<Dictionary> list = dictionaryDao.createQuery(sb.toString(), objs)
				.list();
		return list;
	}

	/**
	 * 得到排序字段的最大值.
	 *
	 * @return 返回排序字段的最大值
	 * @throws com.youyun.common.exception.DaoException
	 * @throws com.youyun.common.exception.SystemException
	 * @throws com.youyun.common.exception.ServiceException
	 */
	public Integer getMaxSort() throws DaoException, SystemException,
			ServiceException {
		Iterator<?> iterator = dictionaryDao.createQuery(
				"select max(d.orderNo)from Dictionary d ").iterate();
		Integer max = null;
		while (iterator.hasNext()) {
			// Object[] row = (Object[]) iterator.next();
			max = (Integer) iterator.next();
		}
		if (max == null) {
			max = 0;
		}
		return max;
	}

    /**
     * 根据字典类型编码 查找
     * @param groupDictionaryTypeCode 字典分类分组编码
     * @return Map<String, List<Dictionary>> key:分类编码（即子类编码） value: 数据字典项集合List<Dictionary>
     * @throws com.youyun.common.exception.DaoException
     * @throws com.youyun.common.exception.SystemException
     * @throws com.youyun.common.exception.ServiceException
     */
    public Map<String, List<Dictionary>> getDictionaryTypesByGroupDictionaryTypeCode(String groupDictionaryTypeCode)
            throws DaoException, SystemException,ServiceException {
        Map<String, List<Dictionary>> map = Maps.newHashMap();
        DictionaryType dictionaryType = dictionaryTypeManager.getByCode(groupDictionaryTypeCode);
        for (DictionaryType subDictionaryType : dictionaryType.getSubDictionaryTypes()) {
            List<Dictionary> dictionaries = this.getDictionarysByDictionaryTypeCode(subDictionaryType.getCode());
            map.put(subDictionaryType.getCode(), dictionaries);
        }
        return map;
    }
}
