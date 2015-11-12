package com.youyun.common.orm.interceptor;

import java.io.Serializable;

/**
 * orm 保存或更新拦截器
 * 
 * @author maurice
 *
 * @param <E> 持久化对象类型
 * @param <P> 持久化上下文，如SessionFactory等对象,或封装的orm操作类，如：BasicHibernateDao
 */
public interface OrmSaveInterceptor<E,P> {

	/**
	 * 当持久化框架执行保存或更新之前，会触发该方法，当该方法返回false时，将中断执行保存或更新
	 * 
	 * @param entity 持久化对象
	 * @param persistentContex 持久化上下文
	 * @param className 对象类名称
	 * @param idProperty id属性名称
	 * @param id 主键id值
	 * 
	 * @return boolean
	 */
	public boolean onSave(E entity, P persistentContext, Serializable id);
	
	/**
	 * 当持久化框架执行保存或更新之后，会触发该方法
	 * 
	 * @param id 保存或更新的对象id
	 * @param entity 持久化对象
	 * @param persistentContex 持久化上下文
	 * @param className 对象类名称
	 * @param idProperty id属性名称
	 * @param id 主键id值
	 * 
	 */
	public void onPostSave(E entity, P persistentContext, Serializable id);
	
}
