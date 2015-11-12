/*
 * Copyright 2014-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.youyun.common.orm.core.spring.data.jpa.repository.support;

import com.youyun.common.orm.core.PropertyFilter;
import com.youyun.common.orm.core.RestrictionNames;
import com.youyun.common.orm.core.spring.data.jpa.interceptor.SecurityCodeInterceptor;
import com.youyun.common.orm.core.spring.data.jpa.interceptor.StateDeleteInterceptor;
import com.youyun.common.orm.core.spring.data.jpa.interceptor.TreeEntityInterceptor;
import com.youyun.common.orm.core.spring.data.jpa.repository.BasicJpaRepository;
import com.youyun.common.orm.core.spring.data.jpa.specification.Specifications;
import com.youyun.common.orm.interceptor.OrmDeleteInterceptor;
import com.youyun.common.orm.interceptor.OrmSaveInterceptor;
import com.youyun.common.utils.reflection.ReflectionUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link BasicJpaRepository}接口实现类，并在{@link SimpleJpaRepository}基础上扩展,包含对{@link PropertyFilter}的支持。或其他查询的支持,
 * 重写了{@link SimpleJpaRepository#save(Object)}和{@link SimpleJpaRepository#delete(Object)}方法，支持@StateDelete注解和@ConvertProperty注解
 * 
 * @author maurice
 *
 * @param <T> ORM对象
 * @param <ID> 主键Id类型
 */
@SuppressWarnings("unchecked")
public class JpaSupportRepository<T, ID extends Serializable>  extends SimpleJpaRepository<T, ID> implements BasicJpaRepository<T, ID> {
	
	private EntityManager entityManager;
	private JpaEntityInformation<T, ?> entityInformation;
	
	//当删除对象时的拦截器
	private List<OrmDeleteInterceptor<T, JpaSupportRepository<T, ID>>> deleteInterceptors;
	//当保存或更新对象时的拦截器
	private List<OrmSaveInterceptor<T, JpaSupportRepository<T, ID>>> saveInterceptors;
	
	public JpaSupportRepository(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
		installInterceptors();
	}
	
	public JpaSupportRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.entityInformation = entityInformation;
		this.entityManager = em;
		installInterceptors();
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public JpaEntityInformation<T, ?> getEntityInformation() {
		return entityInformation;
	}
	
	public List<OrmDeleteInterceptor<T, JpaSupportRepository<T, ID>>> getDeleteInterceptors() {
		return deleteInterceptors;
	}

	public List<OrmSaveInterceptor<T, JpaSupportRepository<T, ID>>> getSaveInterceptors() {
		return saveInterceptors;
	}

	/**
	 * 初始化所有拦截器
	 */
	private void installInterceptors() {
		
		//----初始化删除需要的所有拦截器----//
		deleteInterceptors = new ArrayList<OrmDeleteInterceptor<T,JpaSupportRepository<T,ID>>>();
		deleteInterceptors.add(new StateDeleteInterceptor<T, ID>());
		deleteInterceptors.add(new TreeEntityInterceptor<T, ID>());
		
		//----初始化保存或更新需要的所有拦截器----//
		saveInterceptors = new ArrayList<OrmSaveInterceptor<T,JpaSupportRepository<T,ID>>>();
		saveInterceptors.add(new TreeEntityInterceptor<T, ID>());
		saveInterceptors.add(new SecurityCodeInterceptor<T, ID>());
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#save(S)
	 */
	
	@Transactional
	public <S extends T> S save(S entity) {
		
		for (OrmSaveInterceptor<T,JpaSupportRepository<T, ID>> interceptor : saveInterceptors) {
			ID id = ReflectionUtils.invokeGetter(entity, getIdName());
			if (!interceptor.onSave(entity, this, id)) {
				return null;
			}
		}
		
		S result = null;
		
		if (entityInformation.isNew(entity)) {
			entityManager.persist(entity);
			result = entity;
		} else {
			result = entityManager.merge(entity);
		}
		
		for (OrmSaveInterceptor<T,JpaSupportRepository<T, ID>> interceptor : saveInterceptors) {
			ID id = ReflectionUtils.invokeGetter(entity, getIdName());
			interceptor.onPostSave(entity, this, id);
		}
		
		return result;
	}
	
	 /*
	  * (non-Javadoc)
	  * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#delete(java.lang.Object)
	  */
	
	@Transactional
	public void delete(T entity) {
		for (OrmDeleteInterceptor<T,JpaSupportRepository<T, ID>> interceptor : deleteInterceptors) {
			ID id = ReflectionUtils.invokeGetter(entity, getIdName());
			if (!interceptor.onDelete(id, entity, this)) {
				return ;
			}
		}
		
		super.delete(entity);
		
		for (OrmDeleteInterceptor<T,JpaSupportRepository<T, ID>> interceptor : deleteInterceptors) {
			ID id = ReflectionUtils.invokeGetter(entity, getIdName());
			interceptor.onPostDelete(id, entity, this);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.util.List)
	 */
	
	public List<T> findAll(List<PropertyFilter> filters) {
		return findAll(filters,(Sort)null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.util.List, org.springframework.data.domain.Sort)
	 */
	
	public List<T> findAll(List<PropertyFilter> filters, Sort sort) {
		
		return findAll(Specifications.get(filters), sort);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(org.springframework.data.domain.Pageable, java.util.List)
	 */
	
	public Page<T> findAll(Pageable pageable, List<PropertyFilter> filters) {
		
		return findAll(Specifications.get(filters),pageable);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.lang.String, java.lang.Object)
	 */
	
	public List<T> findAll(String propertyName, Object value) {
		
		return findAll(propertyName, value, (Sort)null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.lang.String, java.lang.Object, org.springframework.data.domain.Sort)
	 */
	
	public List<T> findAll(String propertyName, Object value, Sort sort) {
		return findAll(propertyName, value, sort, RestrictionNames.EQ);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.lang.String, java.lang.Object, java.lang.String)
	 */
	
	public List<T> findAll(String propertyName, Object value,String restrictionName) {
		return findAll(propertyName, value, (Sort)null, restrictionName);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.lang.String, java.lang.Object, org.springframework.data.domain.Sort, java.lang.String)
	 */
	
	public List<T> findAll(String propertyName, Object value, Sort sort,String restrictionName) {
		
		return findAll(Specifications.get(propertyName, value, restrictionName),sort);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findOne(java.util.List)
	 */
	
	public T findOne(List<PropertyFilter> filters) {
		return findOne(Specifications.get(filters));
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findOne(java.lang.String, java.lang.Object)
	 */
	
	public T findOne(String propertyName, Object value) {
		return findOne(propertyName, value, RestrictionNames.EQ);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findOne(java.lang.String, java.lang.Object, java.lang.String)
	 */
	
	public T findOne(String propertyName, Object value, String restrictionName) {
		return findOne(Specifications.get(propertyName, value, restrictionName));
	}

	public String getEntityName() {
		return entityInformation.getEntityName();
	}

	@SuppressWarnings("rawtypes")
	public String getIdName() {
		JpaMetamodelEntityInformation information = (JpaMetamodelEntityInformation) entityInformation;
		return information.getIdAttributeNames().iterator().next().toString();
	}
	
	
}
