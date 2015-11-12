package com.youyun.common.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 树形实体，如果在orm实体配置该注解，会根据该配置来进行对orm实体的自动更新树形状态
 * 
 * @author maurice
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeEntity {

	/**
	 * 是否包含叶子的标记属性名
	 * 
	 * @return String
	 */
	public String leafProperty() default "leaf";
	
	/**
	 * tree实体的父类属性名
	 * 
	 * @return String
	 */
	public String parentProperty() default "parent";
	
	/**
	 * 刷新的hql语句
	 * 
	 * @return String
	 */
	public String refreshHql() default "from {0} tree where tree.{1} = {2} and (select count(c) from {0} c where c.{3}.{4} = tree.{4}) = {5}";
	
	/**
	 * 是否包含叶子的标记属性类型
	 * 
	 * @return Class
	 */
	public Class<?> leafClass() default Boolean.class;
	
	/**
	 * 如果是包含叶子节点需要设置的值
	 * 
	 * @return String
	 */
	public String leafValue() default "1";
	
	/**
	 * 如果不是包含叶子节点需要设置的值
	 * 
	 * @return String
	 */
	public String unleafValue() default "0";
	
}
