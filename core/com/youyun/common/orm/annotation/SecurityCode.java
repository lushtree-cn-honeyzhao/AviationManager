package com.youyun.common.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 安全码注解，如果在orm实体配置该注解，会根据注解的properties指定的属性，
 * 顺序将所有值拼接成一串字符串，在把拼接好的字符串加密，赋值到该注解的value字段中。
 * 当出现更新操作时，会通过id去数据库查询一次数据，再次加密来与value字段比较，如果
 * 不对，表示该数据已经被改动。会丢出SecurityCodeNotEqualException异常。
 * 
 * @author maurice
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurityCode {
	
	/**
	 * 需要加密的属性
	 * 
	 * @return String[]
	 */
	public String[] properties();
	
	/**
	 * id属性名，默认为"id"
	 * 
	 * @return String
	 */
	public String idProperty() default "id";
	
	/**
	 * 经过属性加密后，将加密串赋值到哪个属性中
	 * 
	 * @return String
	 */
	public String value();
	
}
