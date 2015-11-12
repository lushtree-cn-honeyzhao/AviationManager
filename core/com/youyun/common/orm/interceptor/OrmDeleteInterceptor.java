package com.youyun.common.orm.interceptor;

import java.io.Serializable;

/**
 * orm 删除拦截器
 * 
 * @author maurice
 *
 * @param <E> 持久化对象类型
 * @param <P> 持久化上下文，如SessionFactory等对象
 */
public interface OrmDeleteInterceptor<E,P> {

	/**
	 * 当持久化框架执行删除之前，会触发该方法，当该方法返回false时，将中断执行删除
	 * 
	 * @param id 删除的对象id
	 * @param entity 持久化对象
	 * @param persistentContext 持久化上下文
	 * 
	 * @return boolean
	 */
	public boolean onDelete(Serializable id, E entity, P persistentContext);
	
	/**
	 * 当持久化框架执行删除之后，会触发该方法
	 * 
	 * @param id 删除的对象id
	 * @param entity 持久化对象
	 * @param persistentContext 持久化上下文
	 * 
	 */
	public void onPostDelete(Serializable id, E entity, P persistentContext);
	
}
