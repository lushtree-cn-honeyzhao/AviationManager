package com.lushapp.common.orm.core.exception;

import org.hibernate.HibernateException;

/**
 * 安全码不匹配异常,才使用SecurityCode注解时，如果匹配安全码不正确，会丢出该异常
 * 
 * @author maurice
 *
 */
public class SecurityCodeNotEqualException extends HibernateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public SecurityCodeNotEqualException(String message) {
		super( message );
	}

	public SecurityCodeNotEqualException(Throwable root) {
		super( root );
	}

	public SecurityCodeNotEqualException(String message, Throwable root) {
		super( message, root );
	}

}
