package com.youyun.common.orm.core.spring.data.jpa.interceptor;

import com.youyun.common.orm.annotation.SecurityCode;
import com.youyun.common.orm.core.exception.SecurityCodeNotEqualException;
import com.youyun.common.orm.core.spring.data.jpa.repository.support.JpaSupportRepository;
import com.youyun.common.orm.interceptor.OrmSaveInterceptor;
import com.youyun.common.utils.reflection.ReflectionUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 安全码拦截器
 * 
 * @author maurice
 *
 * @param <E> 持久化对象类型
 * @param <ID> id主键类型
 */
@SuppressWarnings("unchecked")
public class SecurityCodeInterceptor<E,ID extends Serializable> implements
        OrmSaveInterceptor<E, JpaSupportRepository<E,ID>> {

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.interceptor.OrmSaveInterceptor#onSave(java.lang.Object, java.lang.Object, java.io.Serializable)
	 */
	
	public boolean onSave(E entity, JpaSupportRepository<E, ID> persistentContext,Serializable id) {
		
		Class<?> entityClass = ReflectionUtils.getTargetClass(entity);
		SecurityCode securityCode = ReflectionUtils.getAnnotation(entityClass,SecurityCode.class);
		
		if (persistentContext.getEntityInformation().isNew(entity)) {
			String code = generateSecurityCode(entity,securityCode);
			ReflectionUtils.invokeSetter(entity, securityCode.value(), code);
		} else {
			E e = persistentContext.findOne((ID) id);
			String originalCode = ReflectionUtils.invokeGetter(e, securityCode.value());
			String currentCode = generateSecurityCode(e, securityCode);
			
			if (!StringUtils.equals(originalCode, currentCode)) {
				throw new SecurityCodeNotEqualException("安全码不正确,原始码为:" + originalCode + "当前对象的安全码为:" + currentCode);
			}
			
			ReflectionUtils.invokeSetter(entity, securityCode.value(), currentCode);
		}
		
		return Boolean.TRUE;
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.interceptor.OrmSaveInterceptor#onPostSave(java.lang.Object, java.lang.Object, java.io.Serializable)
	 */
	
	public void onPostSave(E entity,JpaSupportRepository<E, ID> persistentContext, Serializable id) {
		
	}

	/**
	 * 生成安全码,返回一个md5字符串
	 * 
	 * @param entity 实体
	 * @param securityCode 安全码注解
	 * 
	 * @return String
	 */
	private String generateSecurityCode(E entity, SecurityCode securityCode) {
		StringBuffer sb = new StringBuffer();
		
		String idProerty = securityCode.idProperty();
		Object idValue = ReflectionUtils.invokeGetter(entity, idProerty);
		
		sb.append(idValue);
		
		for (String s : securityCode.properties()) {
			Object value = ReflectionUtils.invokeGetter(entity, s);
			sb.append(value);
		}
		
		return DigestUtils.md5Hex(sb.toString().getBytes());
	}
	
}
